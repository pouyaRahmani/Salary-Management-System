import java.io.*;
import java.util.*;

public class ManagerDashboard implements ManagerDashboardInterface {
    private Employee employee;
    private static final String FILENAME = "Employees.dat";
    private Organization organization = new Organization();

    public ManagerDashboard(Employee employee) {
        this.employee = employee;
    }

    @Override
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nManager Menu:");
            System.out.println("1. View total earnings by ID");
            System.out.println("2. View payment history");
            System.out.println("3. Search user by ID");
            System.out.println("4. Search user by Salary Type");
            System.out.println("5. Archive user");
            System.out.println("6. Change salary");
            System.out.println("7. Show all employees");
            System.out.println("8. Show all managers");
            System.out.println("9. Update profile");
            System.out.println("10. Generate random employee");
            System.out.println("11. View department earnings");
            System.out.println("12. View all employees' earnings");
            System.out.println("13. Add Department");
            System.out.println("14. Count Employees in Department");
            System.out.println("15. View All Departments");
            System.out.println("16. Change Employee Department");
            System.out.println("17. Log out");
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
                    archiveUser();
                    break;
                case 6:
                    changeSalary();
                    break;
                case 7:
                    Employee.showAllEmployees();
                    break;
                case 8:
                    Employee.showAllManagers();
                    break;
                case 9:
                    Employee.updateProfile(FILENAME);
                    break;
                case 10:
                    generateRandomEmployee();
                    break;
                case 11:
                    viewDepartmentEarnings();
                    break;
                case 12:
                    viewAllEmployeesEarnings();
                    break;
                case 13:
                    addDepartment();
                    break;
                case 14:
                    countEmployeesInDepartment();
                    break;
                case 15:
                    viewAllDepartments();
                    break;
                case 16:
                    changeEmployeeDepartment();
                    break;
                case 17:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 17);
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
    public void archiveUser() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter user ID to archive: ");
        int id = scanner.nextInt();
        Employee.archiveEmployee(id, FILENAME);
    }

    @Override
    public void changeSalary() {
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

    @Override
    public void generateRandomEmployee() {
        Employee newEmployee = RandomEmployee.employeeGenerator(FILENAME);
        ArrayList<Salary> salaries = RandomEmployee.salaryGenerator(newEmployee);
        for (Salary salary : salaries) {
            newEmployee.addSalary(salary);
        }
        saveEmployeeToFile(newEmployee, FILENAME);
        System.out.println("Random employee generated and added:");
        System.out.println(newEmployee);
    }

    // Method to read employees from a file
    private static Set<Employee> readEmployeesFromFile(String filename) {
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

    @Override
    public void addDepartment() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter department ID: ");
        int departmentId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter department name: ");
        String departmentName = scanner.nextLine();
        organization.addDepartment(departmentId, departmentName);
        System.out.println("Department added successfully.");
    }

    @Override
    public void countEmployeesInDepartment() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter department ID: ");
        int departmentId = scanner.nextInt();
        int count = Department.countEmployeesInDepartment(departmentId);
        System.out.println("Total employees in department " + departmentId + ": " + count);
    }

    @Override
    public void changeEmployeeDepartment() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter employee ID: ");
        int employeeId = scanner.nextInt();
        System.out.print("Enter new department ID: ");
        int newDepartmentId = scanner.nextInt();
        organization.changeEmployeeDepartment(employeeId, newDepartmentId, FILENAME);
    }

    @Override
    public void viewAllDepartments() {
        List<String> departments = organization.showAllDepartments();
        System.out.println("Departments:");
        for (String dept : departments) {
            System.out.println(dept);
        }
    }
}
