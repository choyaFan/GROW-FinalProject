package portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import portfolio.dao.NetWorthDao;
import portfolio.entity.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class NetWorthServiceImpl implements NetWorthService {
    @Autowired
    private final NetWorthDao netWorthDao;
    private final List<NetWorth> netWorthList = new ArrayList<>();

    public NetWorthServiceImpl(NetWorthDao netWorthDao) {
        this.netWorthDao = netWorthDao;
        this.netWorthList.addAll(this.getAllCash());
        this.netWorthList.addAll(this.getAllInvestments());
    }

    @Override
    public List<Cash> getAllCash() {
        return netWorthDao.getAllCash();
    }

    @Override
    public double getCashTotalValue() {
        return netWorthDao.getAllCash()
                .stream()
                .mapToDouble(Cash::getValue)
                .sum();
    }

    @Override
    public List<CashValue> getCash_preWeek() {
        return netWorthDao.getCash_preWeek();
    }

    @Override
    public List<CashValue> getCash_preMonth() {
        return netWorthDao.getCash_preMonth();
    }

    @Override
    public List<CashValue> getCash_preQuarter() {
        return netWorthDao.getCash_preQuarter();
    }

    @Override
    public List<CashValue> getCash_preYear() {
        return netWorthDao.getCash_preYear();
    }

    @Override
    public List<Investment> getAllInvestments() {
        return netWorthDao.getAllInvestments();
    }

    @Override
    public double getInvestmentTotalValue() {
        return netWorthDao.getAllInvestments()
                .stream()
                .mapToDouble(NetWorth::getValue)
                .sum();
    }

    @Override
    public List<InvestmentValue> getInvestment_preWeek() {
        return netWorthDao.getInvestment_preWeek();
    }

    @Override
    public List<InvestmentValue> getInvestment_preMonth() {
        return netWorthDao.getInvestment_preMonth();
    }

    @Override
    public List<InvestmentValue> getInvestment_preQuarter() {
        return netWorthDao.getInvestment_preQuarter();
    }

    @Override
    public List<InvestmentValue> getInvestment_preYear() {
        return netWorthDao.getInvestment_preYear();
    }

    @Override
    public List<NetWorthValue> getNetWorth_preWeek() {
        List<NetWorthValue> netWorthList1 = new ArrayList<>(netWorthDao.getCash_preWeek());
        List<NetWorthValue> netWorthList2 = new ArrayList<>(netWorthDao.getInvestment_preWeek());
        for(NetWorthValue n1:netWorthList1){
            for(NetWorthValue n2:netWorthList2){
                if(n1.getCreated().equals(n2.getCreated())){
                    n1.setValue(n1.getValue()+n2.getValue());
                    break;
                }
            }
        }
        return netWorthList1;
    }

    @Override
    public List<NetWorthValue> getNetWorth_preMonth() {
        List<NetWorthValue> netWorthList1 = new ArrayList<>(netWorthDao.getCash_preMonth());
        List<NetWorthValue> netWorthList2 = new ArrayList<>(netWorthDao.getInvestment_preMonth());
        for(NetWorthValue n1:netWorthList1){
            for(NetWorthValue n2:netWorthList2){
                if(n1.getCreated().equals(n2.getCreated())){
                    n1.setValue(n1.getValue()+n2.getValue());
                    break;
                }
            }
        }
        return netWorthList1;
    }

    @Override
    public List<NetWorthValue> getNetWorth_preQuarter() {
        List<NetWorthValue> netWorthList1 = new ArrayList<>(netWorthDao.getCash_preQuarter());
        List<NetWorthValue> netWorthList2 = new ArrayList<>(netWorthDao.getInvestment_preQuarter());
        for(NetWorthValue n1:netWorthList1){
            for(NetWorthValue n2:netWorthList2){
                if(n1.getCreated().equals(n2.getCreated())){
                    n1.setValue(n1.getValue()+n2.getValue());
                    break;
                }
            }
        }
        return netWorthList1;
    }

    @Override
    public List<NetWorthValue> getNetWorth_preYear() {
        List<NetWorthValue> netWorthList1 = new ArrayList<>(netWorthDao.getCash_preYear());
        List<NetWorthValue> netWorthList2 = new ArrayList<>(netWorthDao.getInvestment_preYear());
        for(NetWorthValue n1:netWorthList1){
            for(NetWorthValue n2:netWorthList2){
                if(n1.getCreated().equals(n2.getCreated())){
                    n1.setValue(n1.getValue()+n2.getValue());
                    break;
                }
            }
        }
        return netWorthList1;
    }

    @Override
    public CashFlow getIncome(Date start, Date end) {
        Map<String,Double> cashFlow = netWorthList
                .stream()
                .filter(netWorth -> netWorth instanceof Investment)
                .filter(netWorth -> !netWorth.getCreated().after(end) && !netWorth.getCreated().before(start))
                .map(netWorth -> (Investment) netWorth)
                .collect(Collectors.groupingBy(Investment::getType,Collectors.summingDouble(Investment::getIncome)));
        List<Cash> cashList = new ArrayList<>();
        cashFlow.keySet().forEach(p->cashList.add(new Cash(null,p,null,cashFlow.get(p),null)));
        double totalValue = cashFlow.values().stream().mapToDouble(value -> value).sum();
        List<Cash> positive = new ArrayList<>();
        List<Cash> negative = new ArrayList<>();
        for(Cash cash:cashList){
            if(cash.getValue()>=0){
                positive.add(cash);
            }else {
                negative.add(cash);
            }
        }
        return new CashFlow(positive,negative,totalValue);
    }

    @Override
    public CashFlow getSpending(Date start, Date end) {
        Map<String,Double> cashFlow = netWorthList
                .stream()
                .filter(netWorth -> netWorth instanceof Investment)
                .filter(netWorth -> !netWorth.getCreated().after(end) && !netWorth.getCreated().before(start))
                .map(netWorth -> (Investment) netWorth)
                .collect(Collectors.groupingBy(Investment::getType,Collectors.summingDouble(Investment::getSpending)));
        CashValue startValue = netWorthDao.getCashByDate(start);
        CashValue endValue = netWorthDao.getCashByDate(end);
        if(startValue!=null&&endValue!=null){
            if(startValue.getValue()>endValue.getValue()){
                cashFlow.put("cash",startValue.getValue()-endValue.getValue());
            }
        }
        List<Cash> cashList = new ArrayList<>();
        cashFlow.keySet().forEach(p->cashList.add(new Cash(null,p,null,cashFlow.get(p),null)));
        double totalValue = cashFlow.values().stream().mapToDouble(value -> value).sum();
        return new CashFlow(cashList,null,totalValue);
    }
    @Override
    public List<NetWorth> getNetWorthList() {
        return netWorthList;
    }
}
