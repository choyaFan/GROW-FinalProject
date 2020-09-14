package portfolio;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface NetWorthService {

    // cash service
    List<NetWorth> getAllCash();
    double getCashTotalValue();
    Map<Date,Double> getCashByTime(Date start, Date end);
    // investment service
    List<NetWorth> getAllInvestments();
    double getInvestmentTotalValue();
    Map<Date,Double> getInvestmentsByTime(Date start, Date end);
    // net worth service
    Map<Date,Double> getNetWorthByTime(Date start, Date end);
    CashFlow getIncome(Date start, Date end);
    CashFlow getSpending(Date start, Date end);


}
