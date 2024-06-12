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

            if (employee.isManager()) {
                ManagerDashboard managerDashboard = new ManagerDashboard(employee);
                managerDashboard.showMenu();
            } else {
                EmployeeDashboard employeeDashboard = new EmployeeDashboard(employee);
                employeeDashboard.showMenu();
            }

        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    public Employee authenticateEmployee(String username, String password, String filename) {
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
}
