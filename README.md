# SECRETHUB

SECRETHUB is a Java-based application designed to manage and secure user secrets. This project includes functionalities for user authentication, encryption, and administrative management.

## Project Structure

- **.vscode/**: Contains Visual Studio Code settings.
- **bin/**: Compiled binary files.
- **icons/**: Icons used in the application.
- **lib/**: External libraries.
- **src/**: Source code files.
  - `AddSecret.java`: Handles adding new secrets.
  - `AdminDashboard.java`: Admin dashboard functionalities.
  - `AdminLogin.java`: Admin login functionalities.
  - `App.java`: Main application entry point.
  - `CaesarEncryption.java`: Implements Caesar cipher encryption.
  - `CreateAccount.java`: Handles user account creation.
  - `Dashboard.java`: User dashboard functionalities.
  - `DeleteUser.java`: Handles user deletion.
  - `EncryptionUtils.java`: Utility functions for encryption.
  - `Login.java`: User login functionalities.
  - `Message.java`: Handles messaging functionalities.
  - `Overview.java`: Provides an overview of user activities.
  - `User.java`: User-related functionalities.
  - `DatabaseConnection.java`: **(To be created)** Handles database connections.
  - `Auth.java`: Simulates CAPTCHA verification.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- A database system (e.g., MySQL, PostgreSQL)

### Installation

1. Clone the repository:
   ```sh
   git clone https://github.com/AzyzHm/SecretHub.git
2. Navigate to the project directory:
    ```sh
    java -cp bin App
3. Compile the project:
    ```sh
    javac -d bin src/*.java
### Usage:
1. Run the application:
    ```sh
    java -cp bin App

### Database Connection:
You need to create a <code>DatabaseConnection</code> class to handle the database connections. This class should manage the connection to your database, including opening and closing connections, and executing queries.

### Example `DatabaseConnection` Class:
```java
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL ="jdbc:yourdatabaseurl";
    private static final String USER = "yourusername";
    private static final String PASSWORD = "yourpassword";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```
### License:
This project is licensed under the [MIT License](LICENSE) file for details. 



