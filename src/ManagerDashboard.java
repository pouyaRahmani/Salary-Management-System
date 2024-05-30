import java.util.ArrayList;
import java.util.Scanner;

public class ManagerDashboard {
    private Employee employee;
    private static final String FILENAME = "Employees.dat";

    public ManagerDashboard(Employee employee) {
        this.employee = employee;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nManager Menu:");
            System.out.println("1. View total earnings");
            System.out.println("2. View payment history");
            System.out.println("3. Search user by ID");
            System.out.println("4. Search user by Salary Type");
            System.out.println("5. Archive user");
            System.out.println("6. Change salary");
            System.out.println("7. Log out");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Total earnings: " + employee.calculateEarnings());
                    break;
                case 2:
                    System.out.println("Payment history:");
                    for (Salary salary : employee.getPaymentHistory()) {
                        System.out.println(salary);
                    }
                    break;
                case 3:
                    searchUserById();
                    break;
                case 4:
                    searchUserBySalaryType();
                    break;
                case 5:
                    archiveUser();
                    break;
                case 6:
                    changeSalary();
                    break;
                case 7:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 7);
    }

    private void searchUserById() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID: ");
        int id = scanner.nextInt();
        Employee foundEmployee = Employee.findById(id, FILENAME);
        if (foundEmployee != null) {
            System.out.println("User found: " + foundEmployee);
        } else {
            System.out.println("User not found.");
        }
    }

    private void searchUserBySalaryType() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter salary type (1: Fixed, 2: Hourly, 3: Commission, 4: Base Plus Commission): ");
        int salaryTypeChoice = scanner.nextInt();
        Class<? extends Salary> salaryType = null;
        switch (salaryTypeChoice) {
            case 1:
                salaryType = Fixed.class;
                break;
            case 2:
                salaryType = HourlyWage.class;
                break;
            case 3:
                salaryType = Commission.class;
                break;
            case 4:
                salaryType = BasePlusCommission.class;
                break;
            default:
                System.out.println("Invalid salary type. Please try again.");
                return;
        }

        ArrayList<Employee> result = Employee.searchBySalaryType(salaryType, FILENAME);
        if (result.isEmpty()) {
            System.out.println("No employees found with the specified salary type.");
        } else {
            System.out.println("Employees with specified salary type:");
            for (Employee e : result) {
                System.out.println(e);
            }
        }
    }

    private void archiveUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID to archive: ");
        int id = scanner.nextInt();
        Employee.archiveEmployee(id, FILENAME);
        System.out.println("User archived.");
    }

    private void changeSalary() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID: ");
        int id = scanner.nextInt();
        Employee foundEmployee = Employee.findById(id, FILENAME);
        if (foundEmployee == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.print("Enter salary type (1: Fixed, 2: Hourly, 3: Commission, 4: Base Plus Commission): ");
        int salaryTypeChoice = scanner.nextInt();
        Salary newSalary = null;

        System.out.print("Enter start date (yyyy-mm-dd): ");
        String startDateStr = scanner.next();
        Date startDate = Date.valueOf(startDateStr);

        System.out.print("Enter end date (yyyy-mm-dd): ");
        String endDateStr = scanner.next();
        Date endDate = Date.valueOf(endDateStr);

        switch (salaryTypeChoice) {
            case 1:
                System.out.print("Enter monthly salary: ");
                double monthlySalary = scanner.nextDouble();
                newSalary = new Fixed(startDate, endDate, true, foundEmployee, monthlySalary);
                break;
            case 2:
                System.out.print("Enter hourly wage: ");
                double hourlyWage = scanner.nextDouble();
                System.out.print("Enter hours worked: ");
                double hoursWorked = scanner.nextDouble();
                newSalary = new HourlyWage(startDate, endDate, true, foundEmployee, hourlyWage, hoursWorked);
                break;
            case 3:
                System.out.print("Enter gross sales: ");
                double grossSales = scanner.nextDouble();
                System.out.print("Enter commission rate: ");
                double commissionRate = scanner.nextDouble();
                newSalary = new Commission(startDate, endDate, true, foundEmployee, grossSales, commissionRate);
                break;
            case 4:
                System.out.print("Enter base salary: ");
                double baseSalary = scanner.nextDouble();
                System.out.print("Enter gross sales: ");
                grossSales = scanner.nextDouble();
                System.out.print("Enter commission rate: ");
                commissionRate = scanner.nextDouble();
                newSalary = new BasePlusCommission(startDate, endDate, true, foundEmployee, baseSalary, grossSales, commissionRate);
                break;
            default:
                System.out.println("Invalid salary type. Please try again.");
                return;
        }

        Employee.changeSalary(id, newSalary, FILENAME);
        System.out.println("Salary updated for user ID " + id);
    }
}
