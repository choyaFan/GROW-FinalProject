package portfolio;

import java.util.Date;
import java.util.List;

public interface NetWorthService {
    // cash service
    List<Cash> getAllCash();
    double getCashTotalValue();
    List<CashValue> getCash_preWeek();
    List<CashValue> getCash_preMonth();
    List<CashValue> getCash_preQuarter();
    List<CashValue> getCash_preYear();

    // investment service
    List<Investment> getAllInvestments();
    double getInvestmentTotalValue();
    List<InvestmentValue> getInvestment_preWeek();
    List<InvestmentValue> getInvestment_preMonth();
    List<InvestmentValue> getInvestment_preQuarter();
    List<InvestmentValue> getInvestment_preYear();
    // net worth service
    List<NetWorthValue> getNetWorth_preWeek();
    List<NetWorthValue> getNetWorth_preMonth();
    List<NetWorthValue> getNetWorth_preQuarter();
    List<NetWorthValue> getNetWorth_preYear();
    CashFlow getIncome(Date start, Date end);
    CashFlow getSpending(Date start, Date end);

    List<NetWorth> getNetWorthList();
}
