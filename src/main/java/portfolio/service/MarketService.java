package portfolio.service;


public interface MarketService {
    String getIndices();
    String getGainers();
    String getLosers();
    double getHoldings();
    void initData();
}
