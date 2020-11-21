package Level3.lesson2.server;

import Level3.lesson2.auth.AuthenticationService;

public interface Server {
    void broadcastMessage(String message);
    void sendPrivateMessage(String nickname, String message);
    boolean isLoggedIn(String nickname);
    void subscribe(ClientHandler client);
    void unsubscribe(ClientHandler client);
    AuthenticationService getAuthenticationService();
}
