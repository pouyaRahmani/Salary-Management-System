import java.io.Serializable;

public class Date implements Serializable {
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public String dateToString() {
        return day + "/" + month + "/" + year;
    }
}
