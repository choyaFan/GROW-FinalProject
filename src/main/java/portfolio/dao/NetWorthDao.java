package portfolio.dao;

import portfolio.entity.Cash;
import portfolio.entity.CashValue;
import portfolio.entity.Investment;
import portfolio.entity.InvestmentValue;

import java.util.List;

public interface NetWorthDao {
    List<Cash> getAllCash();
    List<Investment> getAllInvestments();
    List<CashValue> getCash_preWeek();
    List<CashValue> getCash_preMonth();
    List<CashValue> getCash_preQuarter();
    List<CashValue> getCash_preYear();
    List<InvestmentValue> getInvestment_preWeek();
    List<InvestmentValue> getInvestment_preMonth();
    List<InvestmentValue> getInvestment_preQuarter();
    List<InvestmentValue> getInvestment_preYear();
    void init();
}
