package portfolio;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface MarketService {
    String getIndicesPercent();
    String getGainersPercent();
    String getLosersPercent();
    String getIndicesValue();
    String getGainersValue();
    String getLosersValue();
    double getHoldings();
    void initData();
}
