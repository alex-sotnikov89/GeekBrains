package Level3.lesson3.server;

import Level3.lesson3.auth.DBService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class ClientHandler {
    private Server server;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private String name;

    public ClientHandler(Server server, Socket socket) {
        try {
            this.server = server;
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                public void run() {
                    if (name == null || name.isEmpty()) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, 1200000);
            doListen();
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }

    public String getName() {
        return name;
    }

    private void doListen() {
        new Thread(() -> {
            try {
                doAuth();
                receiveMessage();
            } catch (Exception e) {
                throw new RuntimeException("SWW", e);
            } finally {
                server.unsubscribe(this);
            }
        }).start();
    }

    private void doAuth() {
        try {
            while (socket.isConnected()) {
                String credentials = in.readUTF();
                /**
                 * Input credentials sample
                 * "-auth n1@mail.com 1"
                 */
                if (credentials.startsWith("-auth")) {
                    /**
                     * After splitting sample
                     * array of ["-auth", "n1@mail.com", "1"]
                     */
                    String[] credentialValues = credentials.split("\\s");
                    server.getAuthenticationService()
                            .doAuth(credentialValues[1], credentialValues[2])
                            .ifPresentOrElse(
                                    user -> {
                                        if (!server.isLoggedIn(user.getNickname())) {
                                            sendMessage("cmd auth: Status OK");
                                            name = user.getNickname();
                                            server.broadcastMessage(name + " is logged in.");
                                            server.subscribe(this);
                                        } else {
                                            sendMessage("Current user is already logged in.");
                                        }
                                    },
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            sendMessage("No a such user by email and password.");
                                        }
                                    }
                            );
                    if (server.isLoggedIn(name)) {
                        return;
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }

    /**
     * Receives input data from {@link ClientHandler#in} and then broadcast via {@link Server#broadcastMessage(String)}
     */
    private void receiveMessage() {
        try {
            while (true) {
                String message = in.readUTF();
                if (message.startsWith("/w")) {
                    String[] messageValues = message.split("\\s");
                    server.sendPrivateMessage(messageValues[1], messageValues[2]);
                } else if (message.equals("-exit")) {
                    return;
                } else if (message.startsWith("/nick")) {
                    String[] messageValues = message.split("\\s");
                    System.out.println("Change nickname: " + changeNickname(messageValues[1]));
                } else {
                    server.broadcastMessage(String.format("From %s: %s",this.getName(),message));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }

    public void sendMessage(String message) {
        try {
            out.writeUTF(message);
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }

    public boolean changeNickname(String newNickname) {
        int id;
        Connection connection = DBService.getConnection();

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE nickname = ?"
            );
            statement.setNString(1, name);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            id = resultSet.getInt("id");

            statement = connection.prepareStatement(
                    "UPDATE users SET nickname = ? WHERE id = ?"
            );
            statement.setNString(1, newNickname);
            statement.setInt(2, id);
            statement.executeUpdate();

            statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE nickname = ?"
            );
            statement.setNString(1, newNickname);
            resultSet = statement.executeQuery();
            resultSet.next();

            if (resultSet.getString("nickname").equals(newNickname)) {
                name = newNickname;
                connection.close();
                return true;
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientHandler that = (ClientHandler) o;
        return Objects.equals(server, that.server) &&
                Objects.equals(socket, that.socket) &&
                Objects.equals(in, that.in) &&
                Objects.equals(out, that.out) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(server, socket, in, out, name);
    }
}
