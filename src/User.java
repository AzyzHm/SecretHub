import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class User {
    private int id;
    private String username;
    private String email;
    private String hashedPassword;

    public User(int id, String username, String email, String hashedPassword) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.hashedPassword = hashedPassword;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public static User getByUserId(int userId) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM users WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            User user = new User(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("password")
            );
            resultSet.close();
            statement.close();
            connection.close();
            return user;
        } else {
            resultSet.close();
            statement.close();
            connection.close();
            return null;
        }
    }

    public static User getByUsername(String username) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM users WHERE username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            User user = new User(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("password")
            );
            resultSet.close();
            statement.close();
            connection.close();
            return user;
        } else {
            resultSet.close();
            statement.close();
            connection.close();
            return null;
        }
    }

    public static void save(User user) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getHashedPassword());
        statement.executeUpdate();
        
        statement.close();
        connection.close();
    }

    public boolean verifyPassword(String password) {
        return EncryptionUtils.verifyPassword(password, this.hashedPassword);
    }

    public static int getUserCount() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT COUNT(*) AS total FROM users";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        int totalUsers = 0;
        if (resultSet.next()) {
            totalUsers = resultSet.getInt("total");
        }
        resultSet.close();
        statement.close();
        connection.close();
        return totalUsers;
    }

    public static void deleteUserById(int userId) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "DELETE FROM users WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    public static List<User> getAllUsers() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM users";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User(
                resultSet.getInt("id"),
                resultSet.getString("username"),
                resultSet.getString("email"),
                resultSet.getString("password")
            );
            users.add(user);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return users;
    }
}
