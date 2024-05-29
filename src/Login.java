
import java.io.*;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Login {

    public void loginEmployee() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Employee employee = authenticateEmployee(username, password, "Employees.dat");

        if (employee != null) {
            System.out.println("Logged in successfully:");
            System.out.println(employee);
            System.out.println("Total earnings: " + employee.calculateEarnings());
            showMenu(employee);
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    private Employee authenticateEmployee(String username, String password, String filename) {
        Set<Employee> employees = readEmployeesFromFile(filename);

        for (Employee employee : employees) {
            if (employee.getUserName().equals(username) && employee.getPassword().equals(password)) {
                return employee;
            }
        }
        return null;
    }

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

    private void showMenu(Employee employee) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. View total earnings");
            System.out.println("2. View payment history");
            System.out.println("3. Log out");
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
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 3);
    }
}
