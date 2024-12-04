import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Message {
    private int id;
    private int userId;
    private String message;
    private String encryptionType;
    private String encryptionKey;

    public Message(int id, int userId, String message, String encryptionType, String encryptionKey) {
        this.id = id;
        this.userId = userId;
        this.message = message;
        this.encryptionType = encryptionType;
        this.encryptionKey = encryptionKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEncryptionType() {
        return encryptionType;
    }

    public void setEncryptionType(String encryptionType) {
        this.encryptionType = encryptionType;
    }

    public String getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(String encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public static void save(Message message) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "INSERT INTO messages (user_id, message, encryption_type, encryption_key) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, message.getUserId());
        statement.setString(2, message.getMessage());
        statement.setString(3, message.getEncryptionType());
        statement.setString(4, message.getEncryptionKey());
        statement.executeUpdate();
        statement.close();
        connection.close();
    }

    public static int getTotalSecrets(int userId) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT COUNT(*) FROM messages WHERE user_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();

        int totalSecrets = 0;
        if (resultSet.next()) {
            totalSecrets = resultSet.getInt(1);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return totalSecrets;
    }

    public static String getMostUsedEncryptionType(int userId) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT encryption_type, COUNT(encryption_type) AS count " +
                       "FROM messages WHERE user_id = ? " +
                       "GROUP BY encryption_type " +
                       "ORDER BY count DESC LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();

        String mostUsedType = "No secrets yet";
        if (resultSet.next()) {
            mostUsedType = resultSet.getString("encryption_type");
        }
        resultSet.close();
        statement.close();
        connection.close();
        return mostUsedType;
    }

    public static List<Message> getUserSecrets(int userId) throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT * FROM messages WHERE user_id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, userId);
        ResultSet resultSet = statement.executeQuery();

        List<Message> messages = new ArrayList<>();
        while (resultSet.next()) {
            Message message = new Message(
                resultSet.getInt("id"),
                resultSet.getInt("user_id"),
                resultSet.getString("message"),
                resultSet.getString("encryption_type"),
                resultSet.getString("encryption_key")
            );
            messages.add(message);
        }
        resultSet.close();
        statement.close();
        connection.close();
        return messages;
    }

    public static int getTotalMessages() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT COUNT(*) AS total FROM messages";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();
        int totalMessages = 0;
        if (resultSet.next()) {
            totalMessages = resultSet.getInt("total");
        }
        resultSet.close();
        statement.close();
        connection.close();
        return totalMessages;
    }

    public static String getMostUsedEncryptionTypeGlobally() throws SQLException {
        Connection connection = DatabaseConnection.getConnection();
        String query = "SELECT encryption_type, COUNT(encryption_type) AS count " +
                       "FROM messages " +
                       "GROUP BY encryption_type " +
                       "ORDER BY count DESC LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(query);
        ResultSet resultSet = statement.executeQuery();

        String mostUsedType = "No secrets yet";
        if (resultSet.next()) {
            mostUsedType = resultSet.getString("encryption_type");
        }
        resultSet.close();
        statement.close();
        connection.close();
        return mostUsedType;
    }
}
