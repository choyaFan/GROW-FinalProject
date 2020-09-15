package portfolio;

import java.util.Date;

public class DateValue {
    private Date created;
    private double value;

    public DateValue(Date created, double value) {
        this.created = created;
        this.value = value;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String toString(){
        return String.format("Date:%s, Value:%s.",getCreated(),getValue());
    }
}
