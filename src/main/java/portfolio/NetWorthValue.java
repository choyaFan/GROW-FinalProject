package portfolio;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "netWorthValue")
public class NetWorthValue {
    LocalDate created;
    double value;

    public NetWorthValue(LocalDate created, double value) {
        this.created = created;
        this.value = value;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
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
