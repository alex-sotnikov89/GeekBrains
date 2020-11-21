package Lesson7.chat.server;

import Lesson7.chat.auth.AuthenticationService;

public interface Server {
    void broadcastMessage(String message);
    void sendPrivateMessage(String nickname, String message);
    boolean isLoggedIn(String nickname);
    void subscribe(ClientHandler client);
    void unsubscribe(ClientHandler client);
    AuthenticationService getAuthenticationService();
}
