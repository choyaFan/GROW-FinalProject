package portfolio.service;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface MarketService {
    String getIndices();
    String getGainers();
    String getLosers();
    double getHoldings();
    void initData();
}
