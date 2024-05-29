import java.io.Serializable;
import java.util.ArrayList;

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

    public ArrayList<Salary> getPaymentHistory(int id) {
        return salaries;
    }

    public static Employee findById(ArrayList<Employee> employees, int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    public double calculateEarnings() {
        double totalEarnings = 0;
        for (Salary salary : salaries) {
            totalEarnings += salary.getAmount();
        }
        return totalEarnings;
    }

    // Getters and setters for all attributes...

    public int getId() {
        return id;
    }

    // getter username
    public String getUserName() {
        return userName;
    }

    // getter password
    public String getPassword() {
        return password;
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
                '}';
    }
}


