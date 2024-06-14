import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class SignupFrame extends JFrame {
    private SignUp signUp;

    public SignupFrame() {
        signUp = new SignUp();

        setTitle("Sign Up");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.DARK_GRAY);  // Apply dark theme

        // Panel for form elements
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.DARK_GRAY);  // Apply dark theme
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input fields with labels
        addFieldWithLabel("First Name:", 0, formPanel, gbc);
        JTextField firstNameField = new JTextField(20);
        addComponent(firstNameField, 1, formPanel, gbc);

        addFieldWithLabel("Last Name:", 1, formPanel, gbc);
        JTextField lastNameField = new JTextField(20);
        addComponent(lastNameField, 1, formPanel, gbc);

        addFieldWithLabel("SSN:", 2, formPanel, gbc);
        JTextField ssnField = new JTextField(20);
        addComponent(ssnField, 1, formPanel, gbc);

        addFieldWithLabel("Birth Date (yyyy-mm-dd):", 3, formPanel, gbc);
        JTextField birthDateField = new JTextField(20);
        addComponent(birthDateField, 1, formPanel, gbc);

        addFieldWithLabel("Username:", 4, formPanel, gbc);
        JTextField usernameField = new JTextField(20);
        addComponent(usernameField, 1, formPanel, gbc);

        addFieldWithLabel("Password:", 5, formPanel, gbc);
        JPasswordField passwordField = new JPasswordField(20);
        addComponent(passwordField, 1, formPanel, gbc);

        addFieldWithLabel("Department ID:", 6, formPanel, gbc);
        JTextField departmentIdField = new JTextField(20);
        addComponent(departmentIdField, 1, formPanel, gbc);

        // Manager specific fields
        JLabel isManagerLabel = new JLabel("Is Manager:");
        isManagerLabel.setToolTipText("Check if the employee is a manager");
        isManagerLabel.setForeground(Color.WHITE);  // Set text color to white
        gbc.gridx = 0;
        gbc.gridy = 7;
        formPanel.add(isManagerLabel, gbc);

        JCheckBox isManagerCheckBox = new JCheckBox();
        isManagerCheckBox.setBackground(Color.DARK_GRAY);
        isManagerCheckBox.setForeground(Color.WHITE);  // Set text color to white
        gbc.gridx = 1;
        formPanel.add(isManagerCheckBox, gbc);

        JLabel managerSalaryLabel = new JLabel("Manager Base Salary:");
        managerSalaryLabel.setToolTipText("Enter base salary if the employee is a manager");
        managerSalaryLabel.setForeground(Color.WHITE);  // Set text color to white
        gbc.gridx = 0;
        gbc.gridy = 8;
        formPanel.add(managerSalaryLabel, gbc);

        JTextField managerSalaryField = new JTextField(20);
        managerSalaryField.setEnabled(false);  // Initially disabled
        gbc.gridx = 1;
        formPanel.add(managerSalaryField, gbc);

        isManagerCheckBox.addActionListener(e -> {
            managerSalaryField.setEnabled(isManagerCheckBox.isSelected());
            if (!isManagerCheckBox.isSelected()) {
                managerSalaryField.setText("");
            }
        });

        // Salary details
        addFieldWithLabel("Salary Start Date:", 9, formPanel, gbc);
        JTextField salaryStartField = new JTextField(20);
        addComponent(salaryStartField, 1, formPanel, gbc);

        addFieldWithLabel("Salary End Date:", 10, formPanel, gbc);
        JTextField salaryEndField = new JTextField(20);
        addComponent(salaryEndField, 1, formPanel, gbc);

        addFieldWithLabel("Is Salary Active:", 11, formPanel, gbc);
        JCheckBox salaryActiveCheckBox = new JCheckBox();
        salaryActiveCheckBox.setBackground(Color.DARK_GRAY);
        salaryActiveCheckBox.setForeground(Color.WHITE);  // Set text color to white
        addComponent(salaryActiveCheckBox, 1, formPanel, gbc);

        addFieldWithLabel("Salary Type:", 12, formPanel, gbc);
        JComboBox<String> salaryTypeComboBox = new JComboBox<>(new String[]{"Fixed", "Hourly", "Commission", "Base Plus Commission"});
        addComponent(salaryTypeComboBox, 1, formPanel, gbc);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setToolTipText("Click to register the employee");
        signUpButton.setBackground(Color.BLACK);
        signUpButton.setForeground(Color.WHITE);  // Set button text color to white
        gbc.gridx = 1;
        gbc.gridy = 13;
        formPanel.add(signUpButton, gbc);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Collect data from fields
                    String firstName = firstNameField.getText();
                    String lastName = lastNameField.getText();
                    String ssn = ssnField.getText();
                    Date birthDate = Date.valueOf(birthDateField.getText());
                    String userName = usernameField.getText();
                    String password = new String(passwordField.getPassword());
                    int departmentId = Integer.parseInt(departmentIdField.getText());
                    boolean isManager = isManagerCheckBox.isSelected();
                    double managerBaseSalary = isManager ? Double.parseDouble(managerSalaryField.getText()) : 0.0;
                    Date salaryStartDate = Date.valueOf(salaryStartField.getText());
                    Date salaryEndDate = Date.valueOf(salaryEndField.getText());
                    boolean activeSalary = salaryActiveCheckBox.isSelected();
                    String salaryType = (String) salaryTypeComboBox.getSelectedItem();

                    // Perform validation checks
                    Set<Employee> employees = SignUp.readEmployeesFromFile("Employees.dat");

                    if (SignUp.isUsernameExists(userName, "Employees.dat")) {
                        JOptionPane.showMessageDialog(null, "Username already exists. Please enter a different username.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (!Organization.isValidDepartmentId(departmentId)) {
                        JOptionPane.showMessageDialog(null, "Invalid Department ID. Please enter a valid Department ID.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int id = readIntInput("Enter employee ID:");
                    if (SignUp.isEmployeeIdExists(id, "Employees.dat")) {
                        JOptionPane.showMessageDialog(null, "Employee ID already exists. Please enter a different ID.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (isManager && signUp.isDepartmentHasManager(departmentId, employees)) {
                        JOptionPane.showMessageDialog(null, "This department already has a manager. You cannot add another manager.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Create salary based on type
                    Salary salary = null;
                    switch (salaryType) {
                        case "Fixed":
                            double monthlySalary = readDoubleInput("Enter monthly salary:");
                            salary = new Fixed(salaryStartDate, salaryEndDate, activeSalary, null, monthlySalary);
                            break;
                        case "Hourly":
                            double hourlyWage = readDoubleInput("Enter hourly wage:");
                            double hoursWorked = readDoubleInput("Enter hours worked:");
                            salary = new HourlyWage(salaryStartDate, salaryEndDate, activeSalary, null, hourlyWage, hoursWorked);
                            break;
                        case "Commission":
                            double grossSales = readDoubleInput("Enter gross sales:");
                            double commissionRate = readDoubleInput("Enter commission rate:");
                            salary = new Commission(salaryStartDate, salaryEndDate, activeSalary, null, grossSales, commissionRate);
                            break;
                        case "Base Plus Commission":
                            double baseSalary = readDoubleInput("Enter base salary:");
                            double grossSalesBase = readDoubleInput("Enter gross sales:");
                            double commissionRateBase = readDoubleInput("Enter commission rate:");
                            salary = new BasePlusCommission(salaryStartDate, salaryEndDate, activeSalary, null, baseSalary, grossSalesBase, commissionRateBase);
                            break;
                    }

                    Activity activityStatus = activeSalary ? Activity.ACTIVE : selectActivityStatus("Select reason for inactive salary:");

                    Employee newEmployee = new Employee(firstName, lastName, ssn, birthDate, userName, password, id, departmentId, isManager, false, activityStatus, managerBaseSalary);
                    salary.setEmployee(newEmployee);
                    newEmployee.addSalary(salary);
                    newEmployee.addDepartmentHistory(departmentId); // Add department to history

                    signUp.saveEmployeeToFile(newEmployee, "Employees.dat");

                    JOptionPane.showMessageDialog(null, "Employee created successfully");
                    dispose();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Scroll pane for form
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    private void addFieldWithLabel(String label, int yPos, JPanel panel, GridBagConstraints gbc) {
        JLabel fieldLabel = new JLabel(label);
        fieldLabel.setForeground(Color.WHITE);  // Set text color to white
        fieldLabel.setToolTipText(label);
        gbc.gridx = 0;
        gbc.gridy = yPos;
        panel.add(fieldLabel, gbc);
    }

    private void addComponent(JComponent component, int xPos, JPanel panel, GridBagConstraints gbc) {
        gbc.gridx = xPos;
        panel.add(component, gbc);
    }

    private double readDoubleInput(String prompt) {
        String input = JOptionPane.showInputDialog(this, prompt);
        return Double.parseDouble(input);
    }

    private int readIntInput(String prompt) {
        String input = JOptionPane.showInputDialog(this, prompt);
        return Integer.parseInt(input);
    }

    private Activity selectActivityStatus(String prompt) {
        String[] options = {"NO_PAYOFF", "FIRED", "STOPPED_COOPERATING", "RETIREMENT"};
        int choice = JOptionPane.showOptionDialog(this, prompt, "Select Activity Status", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        switch (choice) {
            case 0:
                return Activity.NO_PAYOFF;
            case 1:
                return Activity.FIRED;
            case 2:
                return Activity.STOPPED_COOPERATING;
            case 3:
                return Activity.RETIREMENT;
            default:
                throw new IllegalArgumentException("Invalid selection");
        }
    }
}
