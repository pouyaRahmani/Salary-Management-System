import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private Login login;

    public LoginFrame() {
        login = new Login();

        setTitle("Login");
        setSize(350, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.DARK_GRAY);  // Apply dark theme
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);  // Set text color to white
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(usernameLabel, gbc);

        JTextField usernameField = new JTextField(20);
        gbc.gridx = 1;
        add(usernameField, gbc);

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);  // Set text color to white
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(loginButton, gbc);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            Employee employee = login.authenticateEmployee(username, password, "Employees.dat");

            if (employee != null) {
                JOptionPane.showMessageDialog(null, "Logged in successfully");
                dispose();
                // Call the dashboard
                if (employee.isManager()) {
                    ManagerDashboard managerDashboard = new ManagerDashboard(employee);
                    managerDashboard.showMenu();
                } else {
                    EmployeeDashboard employeeDashboard = new EmployeeDashboard(employee);
                    employeeDashboard.showMenu();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password. Please try again.");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginFrame::new);
    }
}
