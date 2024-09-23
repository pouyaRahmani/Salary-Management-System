import java.io.Serializable;


public class ArchiveHistory implements Serializable {
    private Date date;
    private boolean isArchived;

    public ArchiveHistory(Date date, boolean isArchived) {
        this.date = date;
        this.isArchived = isArchived;
    }

    @Override
    public String toString() {
        return "ArchiveHistory{" +
                "date=" + date +
                ", isArchived=" + isArchived +
                '}';
    }
}
