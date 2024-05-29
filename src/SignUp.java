import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class SignUp {

    // Method to create a new employee
    public void createNewEmployee() {
        Scanner scanner = new Scanner(System.in);

        // Collect employee details from user input
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter social security number: ");
        String ssn = scanner.nextLine();

        System.out.print("Enter birth date (dd/mm/yyyy): ");
        String[] birthDateParts = scanner.nextLine().split("/");
        Date birthDate = new Date(Integer.parseInt(birthDateParts[0]), Integer.parseInt(birthDateParts[1]), Integer.parseInt(birthDateParts[2]));

        System.out.print("Enter username: ");
        String userName = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        System.out.print("Enter department ID: ");
        int departmentId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Is this employee a manager? (true/false): ");
        boolean isManager = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        // Collect salary details
        System.out.print("Enter salary start date (dd/mm/yyyy): ");
        String[] salaryStartDateParts = scanner.nextLine().split("/");
        Date salaryStartDate = new Date(Integer.parseInt(salaryStartDateParts[0]), Integer.parseInt(salaryStartDateParts[1]), Integer.parseInt(salaryStartDateParts[2]));

        System.out.print("Enter salary end date (dd/mm/yyyy): ");
        String[] salaryEndDateParts = scanner.nextLine().split("/");
        Date salaryEndDate = new Date(Integer.parseInt(salaryEndDateParts[0]), Integer.parseInt(salaryEndDateParts[1]), Integer.parseInt(salaryEndDateParts[2]));

        // Check if the salary is active
        System.out.print("Is the salary currently active? (true/false): ");
        boolean activeSalary = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        // Variable to hold the employee's activity status
        Activity activityStatus = Activity.ACTIVE;

        // If the salary is not active, prompt for a reason
        if (!activeSalary) {
            System.out.println("Select reason for inactive salary:");
            System.out.println("1. NO_PAYOFF");
            System.out.println("2. FIRED");
            System.out.println("3. STOPPED_COOPERATING");
            System.out.println("4. RETIREMENT");
            System.out.print("Enter your choice: ");
            int reasonChoice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            // Set the activity status based on user input
            switch (reasonChoice) {
                case 1:
                    activityStatus = Activity.NO_PAYOFF;
                    break;
                case 2:
                    activityStatus = Activity.FIRED;
                    break;
                case 3:
                    activityStatus = Activity.STOPPED_COOPERATING;
                    break;
                case 4:
                    activityStatus = Activity.RETIREMENT;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid reason choice");
            }
        }

        // Collect salary type and details
        System.out.print("Enter salary type (1: Fixed, 2: Hourly, 3: Commission, 4: Base Plus Commission): ");
        int salaryType = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Salary salary = null;
        switch (salaryType) {
            case 1:
                System.out.print("Enter monthly salary: ");
                double monthlySalary = scanner.nextDouble();
                salary = new Fixed(salaryStartDate, salaryEndDate, activeSalary, null, monthlySalary);
                break;
            case 2:
                System.out.print("Enter hourly wage: ");
                double hourlyWage = scanner.nextDouble();
                System.out.print("Enter hours worked: ");
                double hoursWorked = scanner.nextDouble();
                salary = new HourlyWage(salaryStartDate, salaryEndDate, activeSalary, null, hourlyWage, hoursWorked);
                break;
            case 3:
                System.out.print("Enter gross sales: ");
                double grossSales = scanner.nextDouble();
                System.out.print("Enter commission rate: ");
                double commissionRate = scanner.nextDouble();
                salary = new Commission(salaryStartDate, salaryEndDate, activeSalary, null, grossSales, commissionRate);
                break;
            case 4:
                System.out.print("Enter base salary: ");
                double baseSalary = scanner.nextDouble();
                System.out.print("Enter gross sales: ");
                double grossSalesBase = scanner.nextDouble();
                System.out.print("Enter commission rate: ");
                double commissionRateBase = scanner.nextDouble();
                salary = new BasePlusCommission(salaryStartDate, salaryEndDate, activeSalary, null, baseSalary, grossSalesBase, commissionRateBase);
                break;
            default:
                throw new IllegalArgumentException("Invalid salary type");
        }

        // Collect employee ID
        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();

        // Create a new employee with the collected information
        Employee newEmployee = new Employee(firstName, lastName, ssn, birthDate, userName, password, id, departmentId, isManager, false, activityStatus);
        salary.employee = newEmployee; // Set the employee reference in salary

        // Add the salary to the employee's salary history
        newEmployee.addSalary(salary);

        // Save the employee to the file
        saveEmployeeToFile(newEmployee, "Employees.dat");

        System.out.println("Employee created:");
        System.out.println(newEmployee);
    }

    // Method to save an employee to a file
    private void saveEmployeeToFile(Employee employee, String filename) {
        Set<Employee> employees = readEmployeesFromFile(filename);
        employees.add(employee);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(employees);
            System.out.println("Employee information saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to read employees from a file
    private Set<Employee> readEmployeesFromFile(String filename) {
        Set<Employee> employees = new HashSet<>();
        File file = new File(filename);

        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
                employees = (Set<Employee>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return employees;
    }
}
