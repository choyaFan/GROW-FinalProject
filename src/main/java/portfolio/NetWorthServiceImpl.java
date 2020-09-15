package portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class NetWorthServiceImpl implements NetWorthService{
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
    public List<DateValue> getCashByTime(Date start, Date end) {
        Map<Date,Double> netWorthMap = netWorthDao.getAllCash()
                .stream()
                .filter(netWorth -> !netWorth.getCreated().after(end) && !netWorth.getCreated().before(start))
                .collect(Collectors.groupingBy(NetWorth::getCreated,Collectors.summingDouble(NetWorth::getValue)));
        List<DateValue> dateValueList = new ArrayList<>();
        netWorthMap.keySet().forEach(p->dateValueList.add(new DateValue(p,netWorthMap.get(p))));
        return dateValueList.stream().sorted(Comparator.comparing(DateValue::getCreated)).collect(Collectors.toList());
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
    public List<DateValue> getInvestmentsByTime(Date start, Date end) {
        Map<Date,Double> netWorthMap = netWorthDao.getAllInvestments()
                .stream()
                .filter(netWorth -> !netWorth.getCreated().after(end) && !netWorth.getCreated().before(start))
                .collect(Collectors.groupingBy(NetWorth::getCreated,Collectors.summingDouble(NetWorth::getValue)));
        List<DateValue> dateValueList = new ArrayList<>();
        netWorthMap.keySet().forEach(p->dateValueList.add(new DateValue(p,netWorthMap.get(p))));
        return dateValueList.stream().sorted(Comparator.comparing(DateValue::getCreated)).collect(Collectors.toList());
    }

    @Override
    public List<DateValue> getNetWorthByTime(Date start, Date end) {
        Map<Date,Double> netWorthMap = netWorthList
                .stream()
                .filter(netWorth -> !netWorth.getCreated().after(end) && !netWorth.getCreated().before(start))
                .collect(Collectors.groupingBy(NetWorth::getCreated,Collectors.summingDouble(NetWorth::getValue)));
        List<DateValue> dateValueList = new ArrayList<>();
        netWorthMap.keySet().forEach(p->dateValueList.add(new DateValue(p,netWorthMap.get(p))));
        return dateValueList.stream().sorted(Comparator.comparing(DateValue::getCreated)).collect(Collectors.toList());
    }

    @Override
    public Map<Date, Double> getNetWorthByTime2(Date start, Date end) {
        return null;
    }

    @Override
    public CashFlow getIncome(Date start, Date end) {
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
        Map<String,Double> cashFlow = netWorthList
                .stream()
                .filter(netWorth -> netWorth instanceof Investment)
                .filter(netWorth -> !netWorth.getCreated().after(end) && !netWorth.getCreated().before(start))
                .map(netWorth -> (Investment) netWorth)
                .collect(Collectors.groupingBy(Investment::getType,Collectors.summingDouble(Investment::getSpending)));
        double totalValue = cashFlow.values().stream().mapToDouble(value -> value).sum();
        return new CashFlow(cashFlow,totalValue);
    }
    @Override
    public List<NetWorth> getNetWorthList() {
        return netWorthList;
    }
}
