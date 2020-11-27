package Level3.lesson3.server;

import Level3.lesson3.auth.AuthenticationService;

public interface Server {
    void broadcastMessage(String message);
    void sendPrivateMessage(String nickname, String message);
    boolean isLoggedIn(String nickname);
    void subscribe(ClientHandler client);
    void unsubscribe(ClientHandler client);
    AuthenticationService getAuthenticationService();
    void saveHistoryMassage(String message);
    void showHistory(ClientHandler clientHandler);
}
