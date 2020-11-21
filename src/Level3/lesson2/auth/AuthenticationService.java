package Lesson7.chat.auth;

import Lesson7.chat.entity.User;

import java.util.Optional;

public interface AuthenticationService {
    Optional<User> doAuth(String login, String password);
}
