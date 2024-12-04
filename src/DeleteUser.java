import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class DeleteUser extends JFrame {
    private JTextField userIdField;

    public DeleteUser() {
        setTitle("Delete a User");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(0x1B1931));
        
        ImageIcon icon = new ImageIcon("icons/DeleteUser.png");
        setIconImage(icon.getImage());

        JLabel titleLabel = new JLabel("Delete a User");
        titleLabel.setForeground(Color.RED);
        titleLabel.setBounds(280, 50, 250, 50);
        titleLabel.setFont(new Font("Dubai", Font.BOLD, 40));
        add(titleLabel);

        JLabel userIdLabel = new JLabel("User ID:");
        userIdLabel.setForeground(Color.WHITE);
        userIdLabel.setBounds(120, 150, 200, 50);
        userIdLabel.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(userIdLabel);

        userIdField = new JTextField();
        userIdField.setBounds(300, 150, 300, 50);
        userIdField.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(userIdField);

        JButton deleteUserButton = new JButton("Delete User");
        deleteUserButton.setBounds(200, 250, 150, 50);
        deleteUserButton.setFont(new Font("Dubai", Font.PLAIN, 20));
        add(deleteUserButton);

        JButton backButton = new JButton("Back");
        backButton.setBounds(400, 250, 150, 50);
        backButton.setFont(new Font("Dubai", Font.PLAIN, 20));
        add(backButton);

        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userIdText = userIdField.getText();
                if (userIdText.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "User ID cannot be empty");
                    return;
                }
                int userId = Integer.parseInt(userIdText);
                try {
                    User.deleteUserById(userId);
                    JOptionPane.showMessageDialog(null, "User deleted successfully");
                    userIdField.setText("");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Failed to delete user");
                }
            }
        });

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminDashboard();
                dispose();
            }
        });

        setVisible(true);
    }
}
