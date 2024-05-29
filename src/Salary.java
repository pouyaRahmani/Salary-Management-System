import java.io.Serializable;

public abstract class Salary implements Serializable {
    protected Date startDate;
    protected Date endDate;
    protected boolean activeSalary;
    protected Employee employee;

    public Salary(Date startDate, Date endDate, boolean activeSalary, Employee employee) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.activeSalary = activeSalary;
        this.employee = employee;
    }

    public abstract double getAmount();

    @Override
    public abstract String toString();
}

class Fixed extends Salary {
    private double monthlySalary;

    public Fixed(Date startDate, Date endDate, boolean activeSalary, Employee employee, double monthlySalary) {
        super(startDate, endDate, activeSalary, employee);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double getAmount() {
        return monthlySalary;
    }

    @Override
    public String toString() {
        return "Fixed Salary: " + monthlySalary;
    }
}

class HourlyWage extends Salary {
    private double hourlyWage;
    private double hoursWorked;

    public HourlyWage(Date startDate, Date endDate, boolean activeSalary, Employee employee, double hourlyWage, double hoursWorked) {
        super(startDate, endDate, activeSalary, employee);
        this.hourlyWage = hourlyWage;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double getAmount() {
        return hourlyWage * hoursWorked;
    }

    @Override
    public String toString() {
        return "Hourly Wage: " + hourlyWage + ", Hours Worked: " + hoursWorked + ", Total: " + getAmount();
    }
}

class Commission extends Salary {
    private double grossSales;
    private double commissionRate;

    public Commission(Date startDate, Date endDate, boolean activeSalary, Employee employee, double grossSales, double commissionRate) {
        super(startDate, endDate, activeSalary, employee);
        this.grossSales = grossSales;
        this.commissionRate = commissionRate;
    }

    @Override
    public double getAmount() {
        return grossSales * commissionRate;
    }

    @Override
    public String toString() {
        return "Commission: " + grossSales + " at " + commissionRate + ", Total: " + getAmount();
    }
}

class BasePlusCommission extends Salary {
    private double baseSalary;
    private double grossSales;
    private double commissionRate;

    public BasePlusCommission(Date startDate, Date endDate, boolean activeSalary, Employee employee, double baseSalary, double grossSales, double commissionRate) {
        super(startDate, endDate, activeSalary, employee);
        this.baseSalary = baseSalary;
        this.grossSales = grossSales;
        this.commissionRate = commissionRate;
    }

    @Override
    public double getAmount() {
        return baseSalary + (grossSales * commissionRate);
    }

    @Override
    public String toString() {
        return "Base Plus Commission: " + baseSalary + " + " + grossSales + " at " + commissionRate + ", Total: " + getAmount();
    }
}

