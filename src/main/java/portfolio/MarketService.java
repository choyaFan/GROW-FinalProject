package portfolio;

import com.mashape.unirest.http.exceptions.UnirestException;

public interface MarketService {
    public String getIndices();
    public String getGainers();
    public String getLosers();
    public double getHoldings();
    public void initData();
}
