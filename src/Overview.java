import javax.swing.*;
import java.awt.*;

public class Overview extends JFrame {
    public Overview() {
        setTitle("Overview");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(0x1B1931));

        ImageIcon icon = new ImageIcon("icons/logo.png");
        setIconImage(icon.getImage());

        JLabel titleLabel = new JLabel("SecretHub");
        titleLabel.setForeground(Color.red);
        titleLabel.setFont(new Font("Dubai", Font.BOLD, 40));
        titleLabel.setBounds(300, 30, 200, 50);
        add(titleLabel);

        JButton loginButton = new JButton("Login");
        JButton createanaccountButton = new JButton("Create an account");
        JButton adminLoginButton = new JButton("AdminSpace");

        loginButton.setBounds(250, 120, 300, 75);
        createanaccountButton.setBounds(250, 240, 300, 75);
        adminLoginButton.setBounds(250, 360, 300, 75);

        loginButton.setFont(new Font("Dubai", Font.PLAIN, 30));
        createanaccountButton.setFont(new Font("Dubai", Font.PLAIN, 30));
        adminLoginButton.setFont(new Font("Dubai", Font.PLAIN, 30));

        add(loginButton);
        add(createanaccountButton);
        add(adminLoginButton);

        loginButton.addActionListener(e -> {
            new Login();
            dispose();
        });

        createanaccountButton.addActionListener(e -> {
            new CreateAccount();
            dispose();
        });

        adminLoginButton.addActionListener(e -> {
            new AdminLogin();
            dispose();
        });

        setVisible(true);
    }
}