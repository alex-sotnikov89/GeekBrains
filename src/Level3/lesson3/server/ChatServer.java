package Level3.lesson3.server;

import Level3.lesson3.Log.LogService;
import Level3.lesson3.auth.AuthenticationService;
import Level3.lesson3.auth.BasicAuthenticationService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ChatServer implements Server {
    private Set<ClientHandler> clients;
    private AuthenticationService authenticationService;
    private LogService logService;


    public ChatServer() {
        try {
            logService = new LogService(new File("./src/Level3/lesson3/Log/Log.txt"));
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
        logService.saveHistoryMassage(message);
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
        client.sendMessage(logService.showHistory(100));
    }

    @Override
    public synchronized void unsubscribe(ClientHandler client) {
        clients.remove(client);
    }

    @Override
    public AuthenticationService getAuthenticationService() {
        return authenticationService;
    }


}
