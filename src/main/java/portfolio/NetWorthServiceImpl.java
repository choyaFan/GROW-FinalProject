package portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class NetWorthServiceImpl implements NetWorthService{
    @Autowired
    private NetWorthDao netWorthDao;
    private static final List<NetWorth> netWorthList = new ArrayList<>();

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
    public Map<Date,Double> getCashByTime(Date start, Date end) {
        return netWorthDao.getAllCash()
                .stream()
                .filter(netWorth -> !netWorth.getCreated().after(end) && !netWorth.getCreated().before(start))
                .collect(Collectors.groupingBy(NetWorth::getCreated,Collectors.summingDouble(NetWorth::getValue)));
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
    public Map<Date,Double> getInvestmentsByTime(Date start, Date end) {
        return netWorthDao.getAllInvestments()
                .stream()
                .filter(netWorth -> !netWorth.getCreated().after(end) && !netWorth.getCreated().before(start))
                .collect(Collectors.groupingBy(NetWorth::getCreated,Collectors.summingDouble(NetWorth::getValue)));
    }

    @Override
    public Map<Date,Double> getNetWorthByTime(Date start, Date end) {
        netWorthList.addAll(this.getAllCash());
        netWorthList.addAll(this.getAllInvestments());
        return netWorthList
                .stream()
                .filter(netWorth -> !netWorth.getCreated().after(end) && !netWorth.getCreated().before(start))
                .collect(Collectors.groupingBy(NetWorth::getCreated,Collectors.summingDouble(NetWorth::getValue)));
    }

    @Override
    public CashFlow getIncome(Date start, Date end) {
        netWorthList.addAll(this.getAllCash());
        netWorthList.addAll(this.getAllInvestments());
        Map<String,Double> cashFlow = netWorthList
                .stream()
                .filter(netWorth -> netWorth instanceof Investment)
                .filter(netWorth -> !netWorth.getCreated().after(end) && !netWorth.getCreated().before(start))
                .map(netWorth -> (Investment) netWorth)
                .collect(Collectors.groupingBy(Investment::getType,Collectors.summingDouble(Investment::getIncome)));
        double totalValue = cashFlow.values().stream().mapToDouble(value -> value).sum();
        return new CashFlow(cashFlow,totalValue);
    }

    @Override
    public CashFlow getSpending(Date start, Date end) {
        netWorthList.addAll(this.getAllCash());
        netWorthList.addAll(this.getAllInvestments());
        Map<String,Double> cashFlow = netWorthList
                .stream()
                .filter(netWorth -> netWorth instanceof Investment)
                .filter(netWorth -> !netWorth.getCreated().after(end) && !netWorth.getCreated().before(start))
                .map(netWorth -> (Investment) netWorth)
                .collect(Collectors.groupingBy(Investment::getType,Collectors.summingDouble(Investment::getSpending)));
        double totalValue = cashFlow.values().stream().mapToDouble(value -> value).sum();
        return new CashFlow(cashFlow,totalValue);
    }
}
