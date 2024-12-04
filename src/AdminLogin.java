import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLogin extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    private static final String ADMIN_USERNAME = "topadmin";
    private static final String ADMIN_PASSWORD = "topadmin";

    public AdminLogin() {
        setTitle("Admin Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(0x1B1931));

        ImageIcon icon = new ImageIcon("icons/Login.png");
        setIconImage(icon.getImage());

        JLabel titleLabel = new JLabel("Admin Login");
        titleLabel.setForeground(Color.RED);
        titleLabel.setBounds(280, 20, 250, 70);
        titleLabel.setFont(new Font("Dubai", Font.BOLD, 40));
        add(titleLabel);

        JLabel usernameLabel = new JLabel("Pseudo:");
        usernameLabel.setForeground(Color.white);
        usernameLabel.setBounds(120, 130, 200, 50);
        usernameLabel.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(usernameLabel);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(120, 230, 200, 50);
        passwordLabel.setForeground(Color.white);
        passwordLabel.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(passwordLabel);

        usernameField = new JTextField();
        usernameField.setBounds(300, 130, 300, 50);
        usernameField.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(usernameField);

        passwordField = new JPasswordField();
        passwordField.setBounds(300, 230, 300, 50);
        passwordField.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(180, 330, 150, 50);
        loginButton.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(loginButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(380, 330, 150, 50);
        backButton.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(backButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (usernameField.getText().isEmpty() && new String(passwordField.getPassword()).isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Pseudo and Password cannot be empty");
                    return;
                } else if (usernameField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Pseudo cannot be empty");
                    return;
                } else if (new String(passwordField.getPassword()).isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Password cannot be empty");
                    return;
                }
                if (authenticate(usernameField.getText(), new String(passwordField.getPassword()))) {
                    JOptionPane.showMessageDialog(null, "Welcome Admin", "Success", JOptionPane.INFORMATION_MESSAGE);
                    new Auth(0,true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed");
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

    private boolean authenticate(String username, String password) {
        return ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password);
    }
}
