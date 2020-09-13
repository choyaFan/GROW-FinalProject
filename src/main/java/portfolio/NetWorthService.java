package portfolio;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface NetWorthService {

    // cash service
    public List<Cash> getAllCash();
    public double getCashTotalValue();
    public Map<Date,Double> getCashByTime(Date start, Date end);
    // investment service
    public List<Investment> getAllInvestments();
    public double getInvestmentTotalValue();
    public Map<Date,Double> getInvestmentsByTime(Date start, Date end);
    // net worth service
    public Map<Date,Double> getNetWorthByTime(Date start, Date end);
    public CashFlow getIncome(Date start, Date end);
    public CashFlow getSpending(Date start, Date end);


}
