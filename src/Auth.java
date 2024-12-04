import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Auth extends JFrame {
    private JTextField captchaField;
    private JLabel captchaLabel;
    private int expectedAnswer;
    private int userId;
    private boolean isAdmin;

    public Auth(int userId, boolean isAdmin) {
        this.userId = userId;
        this.isAdmin = isAdmin;

        setTitle("CAPTCHA Verification");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(new Color(0x1B1931));
        ImageIcon icon = new ImageIcon("icons/Auth.png");
        setIconImage(icon.getImage());

        JLabel titleLabel = new JLabel("Solve the CAPTCHA to Continue");
        titleLabel.setForeground(Color.RED);
        titleLabel.setBounds(150, 50, 500, 50);
        titleLabel.setFont(new Font("Dubai", Font.BOLD, 35));
        add(titleLabel);

        captchaLabel = new JLabel();
        captchaLabel.setForeground(Color.pink);
        captchaLabel.setBounds(320, 150, 300, 50);
        captchaLabel.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(captchaLabel);

        captchaField = new JTextField();
        captchaField.setBounds(250, 220, 300, 50);
        captchaField.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(captchaField);

        JButton verifyButton = new JButton("Verify");
        verifyButton.setBounds(325, 300, 150, 50);
        verifyButton.setFont(new Font("Dubai", Font.PLAIN, 30));
        add(verifyButton);

        verifyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleVerify();
            }
        });

        generateCaptcha();
        setVisible(true);
    }

    private void generateCaptcha() {
        Random rand = new Random();
        int num1 = rand.nextInt(10) + 1;
        int num2 = rand.nextInt(10) + 1;
        expectedAnswer = num1 + num2;
        captchaLabel.setText(num1 + " + " + num2 + " = ?");
    }

    private void handleVerify() {
        String userAnswer = captchaField.getText();
        try {
            int answer = Integer.parseInt(userAnswer);
            if (answer == expectedAnswer) {
                JOptionPane.showMessageDialog(this, "CAPTCHA Verified!");
                if (isAdmin) {
                    new AdminDashboard();
                } else {
                    new Dashboard(userId);
                }
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect CAPTCHA. Please try again.");
                captchaField.setText("");
                generateCaptcha();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number.");
            captchaField.setText("");
            generateCaptcha();
        }
    }
}