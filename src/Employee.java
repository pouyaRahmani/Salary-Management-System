import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

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
                if (salaryType.isInstance(salary)) {
                    result.add(employee);
                    break;
                }
            }
        }
        return result;
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

    @Override
    public String toString() {
        return "Employee{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", id=" + id +
                ", departmentId=" + departmentId +
                ", isManager=" + isManager +
                ", status=" + status +
                ", salaries=" + salaries +
                ", isArchived=" + isArchived +
                '}';
    }
}
