import java.io.Serializable;
import java.util.ArrayList;

public class Organization implements Serializable {
    private ArrayList<Department> departments;

    public Organization() {
        departments = new ArrayList<>();
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public Department getDepartment(int index) {
        return departments.get(index);
    }

    public ArrayList<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(ArrayList<Department> departments) {
        this.departments = departments;
    }


}