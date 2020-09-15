package portfolio;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface NetWorthDao {
    List<Cash> getAllCash();
    List<Investment> getAllInvestments();
    Map<Date, Double> getNetWorthByTime2(Date start, Date end);
}
