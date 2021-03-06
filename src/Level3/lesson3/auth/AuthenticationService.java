package Level3.lesson3.auth;

import Level3.lesson3.entity.User;

import java.util.Optional;

public interface AuthenticationService {
    Optional<User> doAuth(String login, String password);
}
