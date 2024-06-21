import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeDashboard implements EmployeeDashboardInterface {
    private Employee employee;
    private static final String FILENAME = "Employees.dat";

    public EmployeeDashboard(Employee employee) {
        this.employee = employee;
    }

    @Override
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nEmployee Menu:");
            System.out.println("1. View total earnings by ID");
            System.out.println("2. View payment history");
            System.out.println("3. Search user by ID");
            System.out.println("4. Search user by Salary Type");
            System.out.println("5. Show all employees");
            System.out.println("6. Show all managers");
            System.out.println("7. Update profile");
            System.out.println("8. View department earnings");
            System.out.println("9. View all employees' earnings");
            System.out.println("10. Show all archived employees");
            System.out.println("11. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    calculateEarnings();
                    break;
                case 2:
                    Employee.showPaymentHistory(FILENAME);
                    break;
                case 3:
                    searchUserById();
                    break;
                case 4:
                    searchUserBySalaryType();
                    break;
                case 5:
                    Employee.showAllEmployees();
                    break;
                case 6:
                    Employee.showAllManagers();
                    break;
                case 7:
                    Employee.updateProfile(FILENAME);
                    break;
                case 8:
                    viewDepartmentEarnings();
                    break;
                case 9:
                    viewAllEmployeesEarnings();
                    break;
                case 10:
                    Employee.showAllArchivedEmployees(FILENAME);
                    break;
                case 11:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 10);
    }

    @Override
    public void calculateEarnings() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID: ");
        int id = scanner.nextInt();
        double earnings = Employee.calculateEarnings(id, FILENAME);
        System.out.println("Total earnings: " + earnings);
    }

    @Override
    public void searchUserById() {
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

    @Override
    public void searchUserBySalaryType() {
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

    @Override
    public void viewDepartmentEarnings() {
        double departmentEarnings = Employee.calculateDepartmentEarnings(FILENAME);
        if (departmentEarnings == 0) {
            System.out.println("No earnings for the department.");
        } else {
            System.out.println("Total department earnings: " + departmentEarnings);
        }
    }

    @Override
    public void viewAllEmployeesEarnings() {
        double totalEarnings = Employee.calculateAllEmployeesEarnings(FILENAME);
        System.out.println("Total earnings of all employees: " + totalEarnings);
    }
}
