package Level3.lesson3.server;

import Level3.lesson3.auth.AuthenticationService;
import Level3.lesson3.auth.BasicAuthenticationService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ChatServer implements Server {
    private Set<ClientHandler> clients;
    private AuthenticationService authenticationService;


    public ChatServer() {
        try {
            System.out.println("Server is starting up...");
            ServerSocket serverSocket = new ServerSocket(8888);
            clients = new HashSet<>();
            authenticationService = new BasicAuthenticationService();
            System.out.println("Server is started up...");

            while (true) {
                System.out.println("Server is listening for clients...");
                Socket socket = serverSocket.accept();
                System.out.println("Client accepted: " + socket);
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            throw new RuntimeException("SWW", e);
        }
    }

    @Override
    public synchronized void broadcastMessage(String message) {
        clients.forEach(client -> client.sendMessage(message));
        saveHistoryMassage(message);
    }

    @Override
    public synchronized void sendPrivateMessage(String nickname, String message) {
        for (ClientHandler c : clients) {
            if (c.getName().equals(nickname)) {
                c.sendMessage(message);
            }

        }

    }

    @Override
    public synchronized boolean isLoggedIn(String nickname) {
        return clients.stream()
                .filter(clientHandler -> clientHandler.getName().equals(nickname))
                .findFirst()
                .isPresent();
    }

    @Override
    public synchronized void subscribe(ClientHandler client) {
        clients.add(client);
    }

    @Override
    public synchronized void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

    @Override
    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }

    @Override
    public void saveHistoryMassage(String message) {
        try {
            BufferedWriter bw = new BufferedWriter(
                    new FileWriter(
                            new File("./src/Level3/lesson3/Log/Log.txt"),
                            true
                    )
            );

            bw.newLine();
            bw.write(String.format(
                    "%s ",
                    new Date()
            )+" : "+message);
            bw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showHistory(ClientHandler clientHandler) {
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader(
                            new File("./src/Level3/lesson3/Log/Log.txt")
                    )
            );
            int startPosition;
            String line;
            List<String> tmp = new ArrayList<String>();
            while ((line = br.readLine()) != null) {
                tmp.add(line);
            }
            if (tmp.size() > 100) {
                startPosition = tmp.size() - 101;
            } else {
                startPosition = 0;
            }
            for (int i = startPosition; i < tmp.size(); i++) {
                clientHandler.sendHistory(tmp.get(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
