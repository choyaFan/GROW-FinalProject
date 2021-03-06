package portfolio.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Price")
public class Price {
    private double price;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Price(String name, double price){
        this.name = name;
        this.price = price;
    }
}
