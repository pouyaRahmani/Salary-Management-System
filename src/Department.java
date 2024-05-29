import java.io.Serializable;
import java.util.ArrayList;

public class Department implements Serializable {
    private String departmentName;
    private ArrayList<Employee> employees;
    private int managerId;
    private int managerBaseSalary;

    public Department(String departmentName, int managerId, int managerBaseSalary) {
        this.departmentName = departmentName;
        this.managerId = managerId;
        this.managerBaseSalary = managerBaseSalary;
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    public int numberOfEmployees() {
        return employees.size();
    }

    public Employee getManager() {
        for (Employee e : employees) {
            if (e.getId() == managerId) {
                return e;
            }
        }
        return null;
    }

    public void setManager(int managerId) {
        this.managerId = managerId;
    }

    // Getters and setters for all attributes...

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getManagerBaseSalary() {
        return managerBaseSalary;
    }

    public void setManagerBaseSalary(int managerBaseSalary) {
        this.managerBaseSalary = managerBaseSalary;
    }
}

