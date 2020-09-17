package portfolio.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public abstract class NetWorth {
    String ID;
    String name;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    Date created;
    double value;
    String type;

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
