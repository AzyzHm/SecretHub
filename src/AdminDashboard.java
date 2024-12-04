import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AdminDashboard extends JFrame {
    private JTable usersTable;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(0x1B1931));
        
        ImageIcon icon = new ImageIcon("icons/AdminSpace.png");
        setIconImage(icon.getImage());

        JLabel titleLabel = new JLabel("Welcome Admin");
        titleLabel.setFont(new Font("Dubai", Font.BOLD, 40));
        titleLabel.setForeground(Color.RED);
        titleLabel.setBounds(0, 25, 800, 50);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(titleLabel);

        JLabel totalUsersLabel = new JLabel();
        totalUsersLabel.setFont(new Font("Dubai", Font.BOLD, 20));
        totalUsersLabel.setForeground(Color.CYAN);
        totalUsersLabel.setBounds(50, 100, 200, 30);
        add(totalUsersLabel);

        JLabel totalSecretsLabel = new JLabel();
        totalSecretsLabel.setFont(new Font("Dubai", Font.BOLD, 20));
        totalSecretsLabel.setForeground(Color.CYAN);
        totalSecretsLabel.setBounds(260, 100, 200, 30);
        add(totalSecretsLabel);

        JLabel mostUsedEncryptionLabel = new JLabel();
        mostUsedEncryptionLabel.setFont(new Font("Dubai", Font.BOLD, 20));
        mostUsedEncryptionLabel.setForeground(Color.CYAN);
        mostUsedEncryptionLabel.setBounds(480, 100, 300, 30);
        add(mostUsedEncryptionLabel);

        try {
            int totalUsers = User.getUserCount();
            int totalMessages = Message.getTotalMessages();
            String mostUsedEncryptionType = Message.getMostUsedEncryptionTypeGlobally();

            totalUsersLabel.setText("Total Users: " + totalUsers);
            totalSecretsLabel.setText("Total Secrets: " + totalMessages);
            mostUsedEncryptionLabel.setText("Most Used Encryption: " + mostUsedEncryptionType);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JSeparator separator = new JSeparator();
        separator.setBounds(0, 140, 800, 20);
        add(separator);

        JLabel usersLabel = new JLabel("Users List", SwingConstants.CENTER);
        usersLabel.setFont(new Font("Dubai", Font.PLAIN, 20));
        usersLabel.setForeground(Color.WHITE);
        usersLabel.setBounds(0, 160, 800, 30);
        add(usersLabel);

        usersTable = new JTable();
        usersTable.setModel(new DefaultTableModel(new Object[]{"ID", "Username", "Email"}, 0));
        JScrollPane scrollPane = new JScrollPane(usersTable);
        scrollPane.setBounds(25, 200, 725, 200);
        add(scrollPane);

        try {
            List<User> users = User.getAllUsers();
            DefaultTableModel model = (DefaultTableModel) usersTable.getModel();
            for (User user : users) {
                model.addRow(new Object[]{user.getId(), user.getUsername(), user.getEmail()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JButton deleteUserButton = new JButton("Delete User");
        deleteUserButton.setBounds(200, 440, 150, 50);
        deleteUserButton.setFont(new Font("Dubai", Font.PLAIN, 20));
        add(deleteUserButton);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setBounds(400, 440, 150, 50);
        logoutButton.setFont(new Font("Dubai", Font.PLAIN, 20));
        add(logoutButton);

        deleteUserButton.addActionListener(e -> {
            new DeleteUser();
            dispose();
        });

        logoutButton.addActionListener(e -> {
            new Overview();
            dispose();
        });

        setVisible(true);
    }
}