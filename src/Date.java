import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Date implements Serializable {
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    // Converts the Date object to a string in the format "dd/MM/yyyy"
    public String dateToString() {
        return String.format("%02d/%02d/%04d", day, month, year);
    }

    // Parses a date string in the format "yyyy-MM-dd" and returns a Date object
    public static Date valueOf(String dateStr) {
        String pattern = "(\\d{4})-(\\d{2})-(\\d{2})";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(dateStr);
        if (m.matches()) {
            int year = Integer.parseInt(m.group(1));
            int month = Integer.parseInt(m.group(2));
            int day = Integer.parseInt(m.group(3));
            return new Date(day, month, year);
        } else {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd.");
        }
    }

    // Getters and setters for day, month, and year
    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    // Compares two Date objects
    public int compareTo(Date other) {
        if (this.year != other.year) {
            return this.year - other.year;
        } else if (this.month != other.month) {
            return this.month - other.month;
        } else {
            return this.day - other.day;
        }
    }

    @Override
    public String toString() {
        return dateToString();
    }
}
