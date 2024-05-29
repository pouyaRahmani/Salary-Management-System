import java.util.ArrayList;
import java.util.Random;

public class RandomEmployee {
    private static final String[] randomNames = {"John", "Jane", "Chris", "Pat", "Alex", "Taylor"};
    private static final String[] randomUserNames = {"user1", "user2", "user3", "user4", "user5", "user6"};
    private static final Random random = new Random();

    public static Employee employeeGenerator() {
        String firstName = randomNames[random.nextInt(randomNames.length)];
        String lastName = randomNames[random.nextInt(randomNames.length)];
        String socialSecurityNumber = "123-45-6789";
        Date birthDate = new Date(1, 1, 2000); // Random date
        String userName = randomUserNames[random.nextInt(randomUserNames.length)];
        String password = "password";
        int id = random.nextInt(1000);
        int departmentId = random.nextInt(100);
        boolean isManager = random.nextBoolean();
        boolean isArchived = false;
        Activity status = Activity.ACTIVE;

        return new Employee(firstName, lastName, socialSecurityNumber, birthDate, userName, password, id, departmentId, isManager, isArchived, status);
    }

    public static ArrayList<Salary> salaryGenerator(Employee employee) {
        ArrayList<Salary> salaries = new ArrayList<>();
        Date startDate = new Date(1, 1, 2021);
        Date endDate = new Date(1, 1, 2022);
        boolean activeSalary = true;

        salaries.add(new Fixed(startDate, endDate, activeSalary, employee, 3000));
        salaries.add(new HourlyWage(startDate, endDate, activeSalary, employee, 20, 160));
        return salaries;
    }
}