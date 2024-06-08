import java.io.Serializable;

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

    @Override
    public String toString() {
        return "Department ID: " + id + ", Name: " + departmentName;
    }
}
