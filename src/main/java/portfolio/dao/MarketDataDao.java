package portfolio.dao;

import portfolio.entity.Index;

import java.util.List;
import java.util.Map;

public interface MarketDataDao {
    void insertPrice(Map<String, Double> map);
    void insertYield(Map<String, Double> map);
    void insertIndex(List<Index> indexList);
    void updatePrice(String name, double price);
    void updateYield(String name, double yield);
    Map<String, Double> getPriceMap();
    Map<String, Double> getYieldMap();
    Index getIndexByName();
}
