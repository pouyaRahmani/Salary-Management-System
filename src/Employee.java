import java.io.*;
import java.util.*;

public class Employee implements Serializable {
    private String firstName;
    private String lastName;
    private String socialSecurityNumber;
    private Date birthDate;
    private String userName;
    private String password;
    private int id;
    private int departmentId;
    private boolean isManager;
    private boolean isArchived;
    private Activity status;
    private ArrayList<Salary> salaries;

    public Employee(String firstName, String lastName, String socialSecurityNumber, Date birthDate, String userName,
                    String password, int id, int departmentId, boolean isManager, boolean isArchived, Activity status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.socialSecurityNumber = socialSecurityNumber;
        this.birthDate = birthDate;
        this.userName = userName;
        this.password = password;
        this.id = id;
        this.departmentId = departmentId;
        this.isManager = isManager;
        this.isArchived = isArchived;
        this.status = status;
        this.salaries = new ArrayList<>();
    }

    public void addSalary(Salary salary) {
        salaries.add(salary);
    }

    public ArrayList<Salary> getPaymentHistory() {
        return salaries;
    }

  public static double calculateEarnings(int id, String filename) {
        Set<Employee> employees = readEmployeesFromFile(filename);
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                double totalEarnings = 0;
                for (Salary salary : employee.getPaymentHistory()) {
                    totalEarnings += salary.getAmount();
                }
                return totalEarnings;
            }
        }
        return 0;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isManager() {
        return isManager;
    }

    public int getDepartmentId(){
        return departmentId;
    }

    public boolean isArchived() {
        return isArchived;
    }

    public Activity getStatus() {
        return status;
    }

    public static Employee findById(int id, String filename) {
        Set<Employee> employees = readEmployeesFromFile(filename);
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    public static ArrayList<Employee> searchBySalaryType(Class<? extends Salary> salaryType, String filename) {
        Set<Employee> employees = readEmployeesFromFile(filename);
        ArrayList<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            for (Salary salary : employee.getPaymentHistory()) {
                if (salaryType.isInstance(salary) && salary.activeSalary) {
                    result.add(employee);
                    break;
                }
            }
        }
        return result;
    }

    public static List<Employee> showAllEmployees() {
        Set<Employee> employees = readEmployeesFromFile("Employees.dat");
        List<Employee> employeeList = new ArrayList<>();
        for (Employee employee : employees) {
            if (!employee.isManager) {
                employeeList.add(employee);
            }
        }
        System.out.println("Employee list:");
        for (Employee employee : employeeList) {
            System.out.println(employee);
        }
        return employeeList;
    }

    public static List<Employee> showAllManagers() {
        Set<Employee> employees = readEmployeesFromFile("Employees.dat");
        List<Employee> managerList = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.isManager) {
                managerList.add(employee);
            }
        }
        System.out.println("Manager list:");
        for (Employee manager : managerList) {
            System.out.println(manager);
        }
        return managerList;
    }

    // Archive employee with given id and set status based on user input
    public static void archiveEmployee(int id, String filename) {
        Set<Employee> employees = readEmployeesFromFile(filename);
        Scanner scanner = new Scanner(System.in);

        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employee.toString();
                employee.isArchived = true;

                System.out.println("Select reason for archiving:");
                System.out.println("1. NO_PAYOFF");
                System.out.println("2. FIRED");
                System.out.println("3. STOPPED_COOPERATING");
                System.out.println("4. RETIREMENT");
                int reasonChoice = scanner.nextInt();

                switch (reasonChoice) {
                    case 1:
                        employee.status = Activity.NO_PAYOFF;
                        break;
                    case 2:
                        employee.status = Activity.FIRED;
                        break;
                    case 3:
                        employee.status = Activity.STOPPED_COOPERATING;
                        break;
                    case 4:
                        employee.status = Activity.RETIREMENT;
                        break;
                    default:
                        System.out.println("Invalid choice. Setting status to ACTIVE by default.");
                        employee.status = Activity.ACTIVE;
                }

                writeEmployeesToFile(employees, filename);
                System.out.println("Employee archived with status: " + employee.getStatus());
                break;
            }
        }
    }

    public static void changeSalary(int id, Salary newSalary, String filename) {
        Set<Employee> employees = readEmployeesFromFile(filename);
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                for (Salary salary : employee.getPaymentHistory()) {
                    salary.activeSalary = false;
                }
                newSalary.activeSalary = true;
                employee.addSalary(newSalary);
                writeEmployeesToFile(employees, filename);
                break;
            }
        }
    }

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

    private static void writeEmployeesToFile(Set<Employee> employees, String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Update profile for employees and managers based on Id and filename
    public static void updateProfile(String filename) {
        Scanner scanner = new Scanner(System.in);
        Set<Employee> employees = readEmployeesFromFile(filename);
        Employee employeeToUpdate = null;

        System.out.print("Enter employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        for (Employee employee : employees) {
            if (employee.getId() == id && employee.getUserName().equals(username)) {
                employeeToUpdate = employee;
                break;
            }
        }

        if (employeeToUpdate == null) {
            System.out.println("Employee with ID " + id + " and username " + username + " not found.");
            return;
        }

        System.out.println("Current details of the employee:");
        System.out.println(employeeToUpdate);



        System.out.println("Select field to update:");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Social Security Number");
        System.out.println("4. Birth Date");
        System.out.println("5. Back");

        int choice = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter new first name: ");
                String newFirstName = scanner.nextLine();
                employeeToUpdate.firstName = newFirstName;
                break;
            case 2:
                System.out.print("Enter new last name: ");
                String newLastName = scanner.nextLine();
                employeeToUpdate.lastName = newLastName;
                break;
            case 3:
                System.out.print("Enter new social security number: ");
                String newSSN = scanner.nextLine();
                employeeToUpdate.socialSecurityNumber = newSSN;
                break;
            case 4:
                System.out.print("Enter new birth date (yyyy-MM-dd): ");
                String newBirthDateStr = scanner.nextLine();
                Date newBirthDate = Date.valueOf(newBirthDateStr);
                employeeToUpdate.birthDate = newBirthDate;
                break;
            case 5:
                System.out.println("Going back to the main menu.");
                return;
            default:
                System.out.println("Invalid choice. Please try again.");
                return;
        }

        writeEmployeesToFile(employees, filename);
        System.out.println("Employee details updated successfully.");
    }

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                "\t\tlastName='" + lastName + '\'' +
                "\t\tuserName='" + userName + '\'' +
                "\t\tid=" + id +
                "\t\tdepartmentId=" + departmentId +
                "\t\tisManager=" + isManager +
                "\n salaries=" + salaries +
                "\t\tisArchived=" + isArchived +
                "\t\tstatus=" + status +
                "\t\tBirthDate=" + birthDate +
                "\t\tSSN='" + socialSecurityNumber + '\'' +
                '}';
    }
}
