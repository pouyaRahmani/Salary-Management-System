import java.util.ArrayList;
import java.util.Scanner;

public class EmployeeDashboard {
    private Employee employee;

    public EmployeeDashboard(Employee employee) {
        this.employee = employee;
    }

    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nEmployee Menu:");
            System.out.println("1. View total earnings");
            System.out.println("2. View payment history");
            System.out.println("3. Search user by ID");
            System.out.println("4. Search user by Salary Type");
            System.out.println("5. Log out");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Total earnings: " + employee.calculateEarnings());
                    break;
                case 2:
                    System.out.println("Payment history:");
                    for (Salary salary : employee.getPaymentHistory(employee.getId())) {
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
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private void searchUserById() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID: ");
        int id = scanner.nextInt();
        Employee foundEmployee = Employee.findById(id);
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

        ArrayList<Employee> result = Employee.searchBySalaryType(salaryType);
        if (result.isEmpty()) {
            System.out.println("No employees found with the specified salary type.");
        } else {
            System.out.println("Employees with specified salary type:");
            for (Employee e : result) {
                System.out.println(e);
            }
        }
    }
}
