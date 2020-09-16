package portfolio.entity;

import java.util.List;

public class CashFlow {
    private List<Cash> cashList;
    private double totalValue;
    public CashFlow(List<Cash> cashList, double totalValue) {
        this.cashList = cashList;
        this.totalValue = totalValue;
    }
    public List<Cash> getCashList() {
        return cashList;
    }
    public void setCashList(List<Cash> cashList) {
        this.cashList = cashList;
    }
    public double getTotalValue() {
        return totalValue;
    }
    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }
}
