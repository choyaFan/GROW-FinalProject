package portfolio;

import java.util.List;

public interface NetWorthDao {
    List<Cash> getAllCash();
    List<Investment> getAllInvestments();
}
