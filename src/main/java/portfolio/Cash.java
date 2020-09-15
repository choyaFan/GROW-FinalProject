package portfolio;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "cash")
public class Cash extends NetWorth{

    public Cash(String ID, String name, String type, double value, Date created) {
        this.ID = ID;
        this.name = name;
        this.type = type;
        this.value = value;
        this.created = created;
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

    public Double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String toString(){
        return String.format("ID:%s, name:%s, type:%s, value:%.2f, created:%s.",ID,name,type,value,created);
    }
}
