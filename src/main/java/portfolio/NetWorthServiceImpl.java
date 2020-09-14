package portfolio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class NetWorthServiceImpl implements NetWorthService{

    @Autowired
    public Map<String,NetWorth> netWorthMap;

    @Override
    public List<NetWorth> getAllCash() {
        return netWorthMap.values()
                .stream()
                .filter(netWorth -> netWorth instanceof Cash)
                .sorted(Comparator.comparing(NetWorth::getCreated))
                .collect(Collectors.toList());
    }

    @Override
    public double getCashTotalValue() {
        return netWorthMap.values()
                .stream()
                .filter(netWorth -> netWorth instanceof Cash)
                .mapToDouble(NetWorth::getValue)
                .sum();
    }

    @Override
    public Map<Date,Double> getCashByTime(Date start, Date end) {
        return netWorthMap.values()
                .stream()
                .filter(netWorth -> netWorth instanceof Cash)
                .filter(netWorth -> !netWorth.getCreated().after(end) && !netWorth.getCreated().before(start))
                .collect(Collectors.groupingBy(NetWorth::getCreated,Collectors.summingDouble(NetWorth::getValue)));
    }

    @Override
    public List<NetWorth> getAllInvestments() {
        return netWorthMap.values()
                .stream()
                .filter(netWorth -> netWorth instanceof Investment)
                .sorted(Comparator.comparing(NetWorth::getCreated))
                .collect(Collectors.toList());
    }

    @Override
    public double getInvestmentTotalValue() {
        return netWorthMap.values()
                .stream()
                .filter(netWorth -> netWorth instanceof Investment)
                .mapToDouble(NetWorth::getValue)
                .sum();
    }

    @Override
    public Map<Date,Double> getInvestmentsByTime(Date start, Date end) {
        return netWorthMap.values()
                .stream()
                .filter(netWorth -> netWorth instanceof Investment)
                .filter(netWorth -> !netWorth.getCreated().after(end) && !netWorth.getCreated().before(start))
                .collect(Collectors.groupingBy(NetWorth::getCreated,Collectors.summingDouble(NetWorth::getValue)));
    }

    @Override
    public Map<Date,Double> getNetWorthByTime(Date start, Date end) {
        return netWorthMap.values()
                .stream()
                .filter(netWorth -> !netWorth.getCreated().after(end) && !netWorth.getCreated().before(start))
                .collect(Collectors.groupingBy(NetWorth::getCreated,Collectors.summingDouble(NetWorth::getValue)));
    }

    @Override
    public CashFlow getIncome(Date start, Date end) {
        Map<String,Double> cashFlow = netWorthMap.values()
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
        Map<String,Double> cashFlow = netWorthMap.values()
                .stream()
                .filter(netWorth -> netWorth instanceof Investment)
                .filter(netWorth -> !netWorth.getCreated().after(end) && !netWorth.getCreated().before(start))
                .map(netWorth -> (Investment) netWorth)
                .collect(Collectors.groupingBy(Investment::getType,Collectors.summingDouble(Investment::getSpending)));
        double totalValue = cashFlow.values().stream().mapToDouble(value -> value).sum();
        return new CashFlow(cashFlow,totalValue);
    }

}
