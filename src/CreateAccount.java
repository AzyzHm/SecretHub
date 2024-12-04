import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class CreateAccount extends JFrame {
    private JTextField emailField;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public CreateAccount() {
        setTitle("Create Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(0x1B1931));

        ImageIcon icon = new ImageIcon("icons/AddClient.png");
        setIconImage(icon.getImage());

        JLabel titleLabel = new JLabel("Create an Account");
        titleLabel.setForeground(Color.red);
        titleLabel.setBounds(250, 20, 300, 50);
        titleLabel.setFont(new Font("Dubai", Font.BOLD, 30));
        add(titleLabel);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(100, 100, 150, 50);
        emailLabel.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(300, 100, 300, 50);
        emailField.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(emailField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(100, 200, 150, 50);
        usernameLabel.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(300, 200, 300, 50);
        usernameField.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(100, 300, 150, 50);
        passwordLabel.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(300, 300, 300, 50);
        passwordField.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(passwordField);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.setBounds(140, 400, 240, 50);
        createAccountButton.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(createAccountButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(430, 400, 150, 50);
        backButton.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(backButton);

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCreateAccount();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Overview();
                dispose();
            }
        });

        setVisible(true);
    }

    private void handleCreateAccount() {
        try {
            String email = emailField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format!");
                return;
            }

            if (!isValidPassword(password)) {
                JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long and contain an uppercase letter, a lowercase letter, a digit, and a special character.");
                return;
            }

            String hashedPassword = EncryptionUtils.hashPassword(password);
            User newUser = new User(0, username, email, hashedPassword);
            User.save(newUser);

            JOptionPane.showMessageDialog(this, "Account created successfully, Please login to continue");
            new Login();

            
            dispose();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Account creation failed");
        }
    }

    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return Pattern.matches(emailPattern, email);
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        return Pattern.matches(passwordPattern, password);
    }
}
