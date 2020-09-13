package portfolio;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Investment extends NetWorth{
    private static final Map<String,Double> prices = new HashMap<>();
    static {
        prices.put("Diageo plc", 120.0);
        prices.put("Vanda", 10.5);
        prices.put("NexPoint", 22.1);
        prices.put("Cadence", 39.21);
        prices.put("Silicon",45.78);
        prices.put("Ares", 16.0);
        prices.put("Itau", 11.0);
        prices.put("Valley", 10.8);
        prices.put("Echo", 33.3);
        prices.put("Guggenheim", 21.7);

    }

    private String name;
    private String symbol;
    private Date purchaseDate;
    private double purchasePrice;
    private int shares;

    public Investment(String ID,String name, String symbol, Date purchaseDate, double purchasePrice, int shares) {
        this.ID = ID;
        this.name = name;
        this.symbol = symbol;
        this.purchaseDate = purchaseDate;
        this.purchasePrice = purchasePrice;
        this.shares = shares;
        this.created = purchaseDate;
        this.value = getValue();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    @Override
    public double getValue(){
        double price = prices.containsKey(name)? prices.get(name):0;
        return price * shares;
    }

    public double getIncome(){
        return (prices.get(name) - purchasePrice) * shares;
    }

    public double getSpending(){
        return purchasePrice * shares;
    }

    public Portfolio generatePortfolio(){
        return new Portfolio();
    }

    public String toString(){
        return String.format("ID:%s, name:%s, symbol:%s, created:%s, purchased price: %.2f, shares:%d",
                ID,name,symbol,created,purchasePrice,shares);
    }
}
