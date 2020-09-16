package portfolio.entity;

import java.util.Map;

public class CashFlow {
    private Map<String,Double> cashFlow;
    private double totalValue;

    public CashFlow(Map<String, Double> cashFlow, double totalValue) {
        this.cashFlow = cashFlow;
        this.totalValue = totalValue;
    }

    public Map<String, Double> getCashFlow() {
        return cashFlow;
    }

    public void setCashFlow(Map<String, Double> cashFlow) {
        this.cashFlow = cashFlow;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }
}
