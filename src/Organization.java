import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Organization implements OrganizationInterface {
    private static final String FILENAME = "DepartmentList.dat";
    private List<Department> departments;

    public Organization() {
        departments = new ArrayList<>();
        loadDepartments();
    }

    @Override
    public boolean addDepartment(int id, String name) {
        // Check for duplicate department ID
        for (Department department : departments) {
            if (department.getId() == id) {
                return false;
            }
        }

        Department department = new Department(id, name);
        departments.add(department);
        saveDepartments();
        return true;
    }

    @Override
    public List<String> showAllDepartments() {
        List<String> departmentList = new ArrayList<>();
        for (Department department : departments) {
            departmentList.add(department.toString());
        }
        return departmentList;
    }

    @Override
    public void loadDepartments() {
        File file = new File(FILENAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
                departments = (List<Department>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            // If file doesn't exist, create default departments
            for (int i = 1; i <= 30; i++) {
                departments.add(new Department(i, "Department " + i));
            }
            saveDepartments();
        }
    }

    @Override
    public void saveDepartments() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(departments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isValidDepartmentId(int id) {
        for (Department department : departments) {
            if (department.getId() == id) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean changeEmployeeDepartment(int employeeId, int newDepartmentId, String filename) {
        if (!isValidDepartmentId(newDepartmentId)) {
            System.out.println("Invalid department ID.");
            return false;
        }

        Set<Employee> employees = Employee.readEmployeesFromFile(filename);
        for (Employee employee : employees) {
            if (employee.getId() == employeeId) {
                if (employee.isManager()) {
                    System.out.println("Managers cannot change their departments.");
                    return false;
                }

                employee.addDepartmentHistory(newDepartmentId);
                employee.setDepartmentId(newDepartmentId);
                Employee.writeEmployeesToFile(employees, filename);
                System.out.println("Employee department changed successfully.");
                return true;
            }
        }
        System.out.println("Employee not found.");
        return false;
    }
}
