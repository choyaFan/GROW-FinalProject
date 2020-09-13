package portfolio;

import java.util.Date;

public class Cash extends NetWorth{
    private String name;
    private String type;
    private double value;

    public Cash(String ID, String name, String type, double value, Date date) {
        this.ID = ID;
        this.name = name;
        this.type = type;
        this.value = value;
        this.created = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String toString(){
        return String.format("ID:%s, name:%s, type:%s, value:%.2f, created:%s.",ID,name,type,value,created);
    }
}
