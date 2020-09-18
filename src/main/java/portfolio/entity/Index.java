package portfolio.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Index")
public class Index {
    private String name;
    private double value;
    private double percent;

    public Index(String name, double value, double percent) {
        this.name = name;
        this.value = value;
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
