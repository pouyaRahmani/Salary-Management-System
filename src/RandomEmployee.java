import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;

public class RandomEmployee {
    private static final String[] randomNames = {"Sam", "Pouya", "Chris", "Pat", "Alex", "Taylor",
            "Jordan", "Charlie", "Jamie", "Casey", "Dakota", "Riley", "Jessie", "Morgan",
            "Rowan", "Sage", "Harley", "Marley", "Terry", "Shay", "Stevie", "Quinn", "Blair"};

    private static final String[] randomUserNames = {
            "user1", "user2", "user3", "user4", "user5", "user6",
            "user7", "user8", "user9", "user10", "user11", "user12",
            "user13", "user14", "user15", "user16", "user17", "user18",
            "user19", "user20", "user21", "user22", "user23", "user24",
            "user25", "user26", "user27", "user28", "user29", "user30",
            "user31", "user32", "user33", "user34", "user35", "user36",
            "user37", "user38", "user39", "user40", "user41", "user42",
            "user43", "user44", "user45", "user46", "user47", "user48",
            "user49", "user50"};

    private static final Random random = new Random();

    public static Employee employeeGenerator(String filename) {
        String firstName = randomNames[random.nextInt(randomNames.length)];
        String lastName = randomNames[random.nextInt(randomNames.length)];
        String socialSecurityNumber = random.nextInt(999) + "-" + random.nextInt(99) + "-" + random.nextInt(9999);
        Date birthDate = Date.valueOf("2000-01-01"); // Random date
        String userName;
        int id;

        do {
            userName = randomUserNames[random.nextInt(randomUserNames.length)];
        } while (SignUp.isUsernameExists(userName, filename));

        do {
            id = 10000 + random.nextInt(90000); // Generate a random ID between 10000 and 99999
        } while (SignUp.isEmployeeIdExists(id, filename));

        String password = "password";
        int departmentId = random.nextInt(30); // Random department ID between 0 and 29
        boolean isManager = false;
        boolean isArchived = false;
        Activity status = Activity.ACTIVE;
        double managerBaseSalary = 0;

        return new Employee(firstName, lastName, socialSecurityNumber, birthDate, userName, password, id, departmentId, isManager, isArchived, status, managerBaseSalary);
    }

    public static ArrayList<Salary> salaryGenerator(Employee employee) {
        ArrayList<Salary> salaries = new ArrayList<>();
        Date startDate = generateRandomDate(2020, 2022);

        // Generate between 1 and 5 salary records
        for (int i = 0; i < random.nextInt(5) + 1; i++) {
            Date endDate = generateRandomDate(startDate.getYear(), startDate.getYear() + 2);
            boolean activeSalary = (i == 0); // Only the first generated salary will be active

            double monthlySalary = 2000 + random.nextDouble() * 8000; // Between 2000 and 10000
            double hourlyWage = 10 + random.nextDouble() * 40; // Between 10 and 50
            double hoursWorked = 0 + random.nextDouble() * 24; // Between 0 and 24
            double grossSales = 100 + random.nextDouble() * 9000; // Between 100 and 9100
            double commissionRate = 0.01 + random.nextDouble() * 0.19; // Between 0.01 and 0.20
            double baseSalary = 100 + random.nextDouble() * 5000; // Between 100 and 5100

            int salaryType = random.nextInt(4);
            switch (salaryType) {
                case 0:
                    salaries.add(new Fixed(startDate, endDate, activeSalary, employee, monthlySalary));
                    break;
                case 1:
                    salaries.add(new HourlyWage(startDate, endDate, activeSalary, employee, hourlyWage, hoursWorked));
                    break;
                case 2:
                    salaries.add(new Commission(startDate, endDate, activeSalary, employee, grossSales, commissionRate));
                    break;
                case 3:
                    salaries.add(new BasePlusCommission(startDate, endDate, activeSalary, employee, baseSalary, grossSales, commissionRate));
                    break;
            }

            // Update startDate for the next record to ensure sequential history
            startDate = new Date(endDate.getDay(), endDate.getMonth(), endDate.getYear());
            // Move startDate forward by a random number of days (to simulate time passing before next salary record)
            startDate = addRandomDays(startDate, 30, 365);
        }

        return salaries;
    }

    private static Date generateRandomDate(int startYear, int endYear) {
        int year = random.nextInt(endYear - startYear + 1) + startYear;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1; // Simplified to 28 days to avoid dealing with different month lengths
        return new Date(day, month, year);
    }

    private static Date addRandomDays(Date date, int minDays, int maxDays) {
        LocalDate localDate = LocalDate.of(date.getYear(), date.getMonth(), date.getDay());
        int daysToAdd = minDays + random.nextInt(maxDays - minDays + 1);
        localDate = localDate.plusDays(daysToAdd);
        return new Date(localDate.getDayOfMonth(), localDate.getMonthValue(), localDate.getYear());
    }
}
