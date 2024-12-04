import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.crypto.SecretKey;

public class Dashboard extends JFrame {
    @SuppressWarnings("unused")
    private int userId;
    private User user;

    public Dashboard(int userId) {
        this.userId = userId;

        setTitle("Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(0x1B1931));

        ImageIcon icon = new ImageIcon("icons/Dashboard.png");
        setIconImage(icon.getImage());

        try {
            user = User.getByUserId(userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        JLabel welcomeLabel = new JLabel("Welcome " + (user != null ? user.getUsername() : "User"));
        welcomeLabel.setForeground(Color.red);
        welcomeLabel.setFont(new Font("Dubai", Font.BOLD, 35));
        welcomeLabel.setBounds(260, 20, 500, 40);
        add(welcomeLabel);

        try {
            int totalSecrets = Message.getTotalSecrets(userId);
            String mostUsedType = Message.getMostUsedEncryptionType(userId);

            JLabel totalSecretsLabel = new JLabel("Total Secrets: " + totalSecrets);
            totalSecretsLabel.setForeground(Color.CYAN);
            totalSecretsLabel.setFont(new Font("Dubai", Font.PLAIN, 20));
            totalSecretsLabel.setBounds(100, 70, 300, 30);
            add(totalSecretsLabel);

            JLabel mostUsedTypeLabel = new JLabel("Most Used Encryption Type: " + mostUsedType);
            mostUsedTypeLabel.setForeground(Color.CYAN);
            mostUsedTypeLabel.setFont(new Font("Dubai", Font.PLAIN, 20));
            mostUsedTypeLabel.setBounds(100, 110, 600, 30);
            add(mostUsedTypeLabel);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        DefaultTableModel tableModel = new DefaultTableModel(new Object[]{"ID", "Secret"}, 0);
        JTable secretsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(secretsTable);
        scrollPane.setBounds(100, 150, 600, 200);

        try {
            List<Message> secrets = Message.getUserSecrets(userId);
            for (Message secret : secrets) {
                String decryptedMessage = decryptSecret(secret);
                tableModel.addRow(new Object[]{secret.getId(), decryptedMessage});
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        add(scrollPane);

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Dubai", Font.PLAIN, 20));
        logoutButton.setBounds(150, 400, 200, 50);
        add(logoutButton);

        JButton addSecretButton = new JButton("Add Secret");
        addSecretButton.setFont(new Font("Dubai", Font.PLAIN, 20));
        addSecretButton.setBounds(450, 400, 200, 50);
        add(addSecretButton);

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Overview();
                dispose();
            }
        });

        addSecretButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddSecret(userId);
                dispose();
            }
        });

        setVisible(true);
    }

    private String decryptSecret(Message message) {
        try {
            if ("Caesar".equals(message.getEncryptionType())) {
                return EncryptionUtils.decryptCaesar(message.getMessage(), Integer.parseInt(message.getEncryptionKey()));
            } else if ("AES".equals(message.getEncryptionType())) {
                SecretKey key = EncryptionUtils.getAESKeyFromString(message.getEncryptionKey());
                return EncryptionUtils.decryptAES(message.getMessage(), key);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
