import java.io.*;
import java.util.HashSet;
import java.util.InputMismatchException;
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

        Date birthDate = readDateInput(scanner, "Enter birth date (yyyy-MM-dd): ");

        System.out.print("Enter username: ");
        String userName = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        int departmentId = readIntInput(scanner, "Enter department ID: ");

        boolean isManager = readBooleanInput(scanner, "Is this employee a manager? (true/false): ");

        // Collect salary details
        Date salaryStartDate = readDateInput(scanner, "Enter salary start date (yyyy-MM-dd): ");
        Date salaryEndDate = readDateInput(scanner, "Enter salary end date (yyyy-MM-dd): ");

        boolean activeSalary = readBooleanInput(scanner, "Is the salary currently active? (true/false): ");

        // Variable to hold the employee's activity status
        Activity activityStatus = Activity.ACTIVE;

        // If the salary is not active, prompt for a reason
        if (!activeSalary) {
            activityStatus = selectActivityStatus(scanner);
        }

        int salaryType = readIntInput(scanner, "Enter salary type (1: Fixed, 2: Hourly, 3: Commission, 4: Base Plus Commission): ");

        Salary salary = null;
        switch (salaryType) {
            case 1:
                double monthlySalary = readDoubleInput(scanner, "Enter monthly salary: ");
                salary = new Fixed(salaryStartDate, salaryEndDate, activeSalary, null, monthlySalary);
                break;
            case 2:
                double hourlyWage = readDoubleInput(scanner, "Enter hourly wage: ");
                double hoursWorked = readDoubleInput(scanner, "Enter hours worked: ");
                salary = new HourlyWage(salaryStartDate, salaryEndDate, activeSalary, null, hourlyWage, hoursWorked);
                break;
            case 3:
                double grossSales = readDoubleInput(scanner, "Enter gross sales: ");
                double commissionRate = readDoubleInput(scanner, "Enter commission rate: ");
                salary = new Commission(salaryStartDate, salaryEndDate, activeSalary, null, grossSales, commissionRate);
                break;
            case 4:
                double baseSalary = readDoubleInput(scanner, "Enter base salary: ");
                double grossSalesBase = readDoubleInput(scanner, "Enter gross sales: ");
                double commissionRateBase = readDoubleInput(scanner, "Enter commission rate: ");
                salary = new BasePlusCommission(salaryStartDate, salaryEndDate, activeSalary, null, baseSalary, grossSalesBase, commissionRateBase);
                break;
            default:
                throw new IllegalArgumentException("Invalid salary type");
        }

        int id = readIntInput(scanner, "Enter employee ID: ");

        Employee newEmployee = new Employee(firstName, lastName, ssn, birthDate, userName, password, id, departmentId, isManager, false, activityStatus);
        salary.employee = newEmployee;

        newEmployee.addSalary(salary);

        saveEmployeeToFile(newEmployee, "Employees.dat");

        System.out.println("Employee created:");
        System.out.println(newEmployee);
    }

    private Date readDateInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Date.valueOf(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format. Please use the format yyyy-MM-dd.");
            }
        }
    }

    private int readIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    private double readDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    private boolean readBooleanInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return scanner.nextBoolean();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter true or false.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    private Activity selectActivityStatus(Scanner scanner) {
        System.out.println("Select reason for inactive salary:");
        System.out.println("1. NO_PAYOFF");
        System.out.println("2. FIRED");
        System.out.println("3. STOPPED_COOPERATING");
        System.out.println("4. RETIREMENT");

        int reasonChoice = readIntInput(scanner, "Enter your choice: ");
        switch (reasonChoice) {
            case 1:
                return Activity.NO_PAYOFF;
            case 2:
                return Activity.FIRED;
            case 3:
                return Activity.STOPPED_COOPERATING;
            case 4:
                return Activity.RETIREMENT;
            default:
                throw new IllegalArgumentException("Invalid reason choice");
        }
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
