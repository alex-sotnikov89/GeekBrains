package Level3.lesson2.auth;

import Level3.lesson2.entity.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class BasicAuthenticationService implements AuthenticationService {

    /**
     * Fake database with stubbed entities
     */
//    private static final List<User> users;
//
//    static {
//        users = List.of(
//                new User("n1", "n1@mail.com", "1"),
//                new User("n2", "n2@mail.com", "2"),
//                new User("n3", "n3@mail.com", "3")
//        );
//    }

    @Override
    public Optional<User> doAuth(String email, String password) {
        Connection connection = DBService.getConnection();

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "SELECT * FROM users WHERE email = ? AND password = ?"
            );
            statement.setNString(1, email);
            statement.setNString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user1 = new User(
                        resultSet.getString("nickname"),
                        resultSet.getString("email"),
                        resultSet.getString("password")
                );
                if (user1.getEmail().equals(email) && user1.getPassword().equals(password)) {
                    return Optional.of(user1);
                }
            }
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}
