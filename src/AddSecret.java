import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.SecureRandom;

public class AddSecret extends JFrame {
    private JTextField secretField;
    private JComboBox<String> encryptionTypeComboBox;
    private int userId;

    public AddSecret(int userId) {
        this.userId = userId;

        setTitle("Add Secret");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(0x1B1931));

        ImageIcon icon = new ImageIcon("icons/AddSecret.png");
        setIconImage(icon.getImage());

        JLabel titleLabel = new JLabel("Add a Secret");
        titleLabel.setForeground(Color.red);
        titleLabel.setBounds(280, 30, 200, 50);
        titleLabel.setFont(new Font("Dubai", Font.BOLD, 33));
        add(titleLabel);

        JLabel secretLabel = new JLabel("Secret:");
        secretLabel.setForeground(Color.WHITE);
        secretLabel.setBounds(100, 120, 150, 50);
        secretLabel.setFont(new Font("Dubai", Font.PLAIN, 25));
        add(secretLabel);

        secretField = new JTextField();
        secretField.setBounds(300, 120, 300, 50);
        secretField.setFont(new Font("Dubai", Font.PLAIN, 25));
        add(secretField);

        JLabel encryptionTypeLabel = new JLabel("Encryption Type:");
        encryptionTypeLabel.setForeground(Color.WHITE);
        encryptionTypeLabel.setBounds(100, 220, 250, 50);
        encryptionTypeLabel.setFont(new Font("Dubai", Font.PLAIN, 23));
        add(encryptionTypeLabel);

        String[] encryptionTypes = {"Caesar", "AES"};
        encryptionTypeComboBox = new JComboBox<>(encryptionTypes);
        encryptionTypeComboBox.setBounds(300, 220, 300, 50);
        encryptionTypeComboBox.setFont(new Font("Dubai", Font.PLAIN, 25));
        add(encryptionTypeComboBox);

        JButton addSecretButton = new JButton("Add Secret");
        addSecretButton.setBounds(160, 350, 200, 50);
        addSecretButton.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(addSecretButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(430, 350, 150, 50);
        backButton.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(backButton);

        addSecretButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleAddSecret();
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Dashboard(userId);
                dispose();
            }
        });

        setVisible(true);
    }

    private void handleAddSecret() {
        try {
            String secret = secretField.getText();
            String encryptionType = (String) encryptionTypeComboBox.getSelectedItem();
            String encryptedSecret = null;
            String encryptionKey = null;

            if (encryptionType.equals("Caesar")) {
                int shift = new SecureRandom().nextInt(25) + 1;
                encryptedSecret = EncryptionUtils.encryptCaesar(secret, shift);
                encryptionKey = String.valueOf(shift);
            } else if (encryptionType.equals("AES")) {
                SecretKey key = EncryptionUtils.generateAESKey();
                encryptedSecret = EncryptionUtils.encryptAES(secret, key);
                encryptionKey = EncryptionUtils.getStringFromAESKey(key);
            }

            Message newMessage = new Message(0, userId, encryptedSecret, encryptionType, encryptionKey);
            Message.save(newMessage);

            JOptionPane.showMessageDialog(this, "Secret added successfully!");
            secretField.setText("");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to add secret");
        }
    }
}
