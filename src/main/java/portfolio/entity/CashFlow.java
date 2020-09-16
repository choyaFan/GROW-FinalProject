package portfolio.entity;

import java.util.List;

public class CashFlow {
    private List<Cash> positiveList;
    private List<Cash> negativeList;
    private double totalValue;

    public CashFlow(List<Cash> positiveList, List<Cash> negativeList, double totalValue) {
        this.positiveList = positiveList;
        this.negativeList = negativeList;
        this.totalValue = totalValue;
    }

    public List<Cash> getPositiveList() {
        return positiveList;
    }

    public void setPositiveList(List<Cash> positiveList) {
        this.positiveList = positiveList;
    }

    public List<Cash> getNegativeList() {
        return negativeList;
    }

    public void setNegativeList(List<Cash> negativeList) {
        this.negativeList = negativeList;
    }

    public double getTotalValue() {
        return totalValue;
    }
    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }
}
