import javax.swing.*;
import java.awt.*;

public class MainFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = new JFrame("Employee Management System");
            mainFrame.setSize(300, 200);
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setLocationRelativeTo(null);
            mainFrame.getContentPane().setBackground(Color.DARK_GRAY);
            mainFrame.setLayout(null);

            JButton loginButton = new JButton("Login");
            loginButton.setBounds(50, 30, 200, 30);
            loginButton.setBackground(Color.BLACK);
            loginButton.setForeground(Color.WHITE);
            loginButton.addActionListener(e -> new LoginFrame());
            mainFrame.add(loginButton);

            JButton signUpButton = new JButton("Sign Up");
            signUpButton.setBounds(50, 70, 200, 30);
            signUpButton.setBackground(Color.BLACK);
            signUpButton.setForeground(Color.WHITE);
            signUpButton.addActionListener(e -> new SignupFrame());
            mainFrame.add(signUpButton);

            JButton exitButton = new JButton("Exit");
            exitButton.setBounds(50, 110, 200, 30);
            exitButton.setBackground(Color.BLACK);
            exitButton.setForeground(Color.WHITE);
            exitButton.addActionListener(e -> System.exit(0));
            mainFrame.add(exitButton);

            mainFrame.setVisible(true);
        });
    }
}
