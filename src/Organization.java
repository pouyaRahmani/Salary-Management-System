import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Organization {
    private static final String FILENAME = "DepartmentList.dat";
    private List<Department> departments;

    public Organization() {
        departments = new ArrayList<>();
        loadDepartments();
    }

    public static void addDepartment(int id, String name) {
        Department department = new Department(id, name);
        List<Department> departments = loadDepartmentsFromFile();
        departments.add(department);
        saveDepartmentsToFile(departments);
    }

    public static void showAllDepartments() {
        List<Department> departments = loadDepartmentsFromFile();
        System.out.println("All Departments:");
        for (Department department : departments) {
            System.out.println(department);
        }
    }

    public List<Department> getDepartments() {
        return departments;
    }

    private void loadDepartments() {
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

    private static List<Department> loadDepartmentsFromFile() {
        List<Department> departments = new ArrayList<>();
        File file = new File(FILENAME);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME))) {
                departments = (List<Department>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            // If file doesn't exist, create default departments
            for (int i = 1; i <= 20; i++) {
                departments.add(new Department(i, "Department " + i));
            }
            saveDepartmentsToFile(departments);
        }
        return departments;
    }

    private void saveDepartments() {
        saveDepartmentsToFile(departments);
    }

    private static void saveDepartmentsToFile(List<Department> departments) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME))) {
            oos.writeObject(departments);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isValidDepartmentId(int id) {
        List<Department> departments = loadDepartmentsFromFile();
        for (Department department : departments) {
            if (department.getId() == id) {
                return true;
            }
        }
        return false;
    }

    public static void changeEmployeeDepartment(int employeeId, int newDepartmentId, String filename) {
        if (!isValidDepartmentId(newDepartmentId)) {
            System.out.println("Invalid department ID.");
            return;
        }

        Set<Employee> employees = Employee.readEmployeesFromFile(filename);
        for (Employee employee : employees) {
            if (employee.getId() == employeeId) {
                if (employee.isManager()) {
                    System.out.println("Managers cannot change their departments.");
                    return;
                }

                employee.addDepartmentHistory(newDepartmentId);
                employee.setDepartmentId(newDepartmentId);
                Employee.writeEmployeesToFile(employees, filename);
                System.out.println("Employee department changed successfully.");
                return;
            }
        }
        System.out.println("Employee not found.");
    }
}
