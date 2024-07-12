import java.util.List;

public interface OrganizationInterface {
    boolean addDepartment(int id, String name);
    List<String> showAllDepartments();
    void loadDepartments();
    void saveDepartments();
    boolean isValidDepartmentId(int id);
    boolean changeEmployeeDepartment(int employeeId, int newDepartmentId, String filename);
}
