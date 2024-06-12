import java.io.Serializable;
import java.util.Set;

public class Department implements Serializable {
    private int id;
    private String departmentName;

    public Department(int id, String departmentName) {
        this.id = id;
        this.departmentName = departmentName;
    }

    public int getId() {
        return id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public static int countEmployeesInDepartment(int departmentId) {
        Set<Employee> employees = Employee.readEmployeesFromFile("Employees.dat");
        int count = 0;
        for (Employee employee : employees) {
            if (employee.getDepartmentId() == departmentId) {
                count++;
            } else {
                System.out.println("No employees found in the department.");
            }
        }
        return count;
    }

    @Override
    public String toString() {
        return "Department ID: " + id + ", Name: " + departmentName;
    }
}
