package vo;

import java.util.List;

public class DateCount {
   private String date;
   private List<String> names;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    @Override
    public String toString() {
        return "DateCount{" +
                "date='" + date + '\'' +
                ", names=" + names +
                '}';
    }
}
