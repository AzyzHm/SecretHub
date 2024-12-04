import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Login extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public Login() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(0x1B1931));

        ImageIcon icon = new ImageIcon("icons/Login.png");
        setIconImage(icon.getImage());

        JLabel titleLabel = new JLabel("Please Login");
        titleLabel.setForeground(Color.red);
        titleLabel.setBounds(270, 20, 300, 50);
        titleLabel.setFont(new Font("Dubai", Font.BOLD, 40));
        add(titleLabel);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(120, 110, 200, 50);
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(usernameLabel);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(120, 210, 200, 50);
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(passwordLabel);

        usernameField = new JTextField();
        usernameField.setBounds(300, 110, 300, 50);
        usernameField.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(300, 210, 300, 50);
        passwordField.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(180, 330, 150, 50);
        loginButton.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(loginButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(400, 330, 150, 50);
        backButton.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(backButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usernameField.getText().isEmpty() && new String(passwordField.getPassword()).isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username and Password cannot be empty");
                    return;
                } else if (usernameField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Username cannot be empty");
                    return;
                } else if (new String(passwordField.getPassword()).isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Password cannot be empty");
                    return;
                }
                try {
                    User user = authenticate(usernameField.getText(), new String(passwordField.getPassword()));
                    if (user != null) {
                        JOptionPane.showMessageDialog(null, "Logged in successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                        new Auth(user.getId(), false);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Login failed");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
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

    private User authenticate(String username, String password) throws SQLException {
        User user = User.getByUsername(username);
        if (user != null && user.verifyPassword(password)) {
            return user;
        }
        return null;
    }
}
