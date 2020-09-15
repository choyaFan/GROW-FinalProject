package portfolio;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface NetWorthService {
    // cash service
    List<Cash> getAllCash();
    double getCashTotalValue();
    List<DateValue> getCashByTime(Date start, Date end);
    // investment service
    List<Investment> getAllInvestments();
    double getInvestmentTotalValue();
    List<DateValue> getInvestmentsByTime(Date start, Date end);
    // net worth service
    List<DateValue> getNetWorthByTime(Date start, Date end);
    Map<Date,Double> getNetWorthByTime2(Date start, Date end);
    CashFlow getIncome(Date start, Date end);
    CashFlow getSpending(Date start, Date end);

    public List<NetWorth> getNetWorthList();
}
