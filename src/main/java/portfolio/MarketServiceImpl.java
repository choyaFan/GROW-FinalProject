package portfolio;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MarketServiceImpl implements MarketService {
    private final Map<String,NetWorth> netWorthMap;

    Map<String, Double> sortedYieldMap = new LinkedHashMap<>();
    Map<String, Double> priceMap = new LinkedHashMap<>();
    JSONObject index;

    @Autowired
    public MarketServiceImpl(Map<String, NetWorth> netWorthMap) {
        this.netWorthMap = netWorthMap;
    }

    @Autowired
    private NetWorthService worthService;
    @Override
    public String getIndicesPercent() {
        JSONObject indices = new JSONObject();
        indices.put("DOW JONES", getChangeByName("DOW JONES"));
        indices.put("S&P 500", getChangeByName("S&P 500"));
        indices.put("NASDAQ", getChangeByName("NASDAQ"));
        indices.put("SSE Composite Index", getChangeByName("SSE Composite Index"));
        return JSON.toJSONString(indices);
    }

    @Override
    public String getGainersPercent() {
        Map<String, Double> gainerMap = new LinkedHashMap<>();
        for(String name : sortedYieldMap.keySet()){
            if(sortedYieldMap.get(name) >= 0 && gainerMap.size() <= 5)
                gainerMap.put(name, sortedYieldMap.get(name));
            else break;
        }
        return JSON.toJSONString(gainerMap);
    }

    @Override
    public String getLosersPercent() {
        Map<String, Double> loserMap = new LinkedHashMap<>();
        for(String name : sortedYieldMap.keySet()){
            if(sortedYieldMap.get(name) <= 0 && loserMap.size() < 5)
                loserMap.put(name, sortedYieldMap.get(name));
        }
        return JSON.toJSONString(loserMap);
    }

    @Override
    public String getIndicesValue() {

        return null;
    }

    @Override
    public String getGainersValue() {
        return null;
    }

    @Override
    public String getLosersValue() {
        return null;
    }

    @Override
    public double getHoldings() {
        double holdingYield = 0;
        for(NetWorth netWorth : worthService.getNetWorthList()){
            if(netWorth instanceof Investment){
                double changeValue = (priceMap.get(netWorth.getName()) - ((Investment) netWorth).getPurchasePrice()) * ((Investment) netWorth).getShares();
                holdingYield += changeValue / netWorth.getValue();
            }
        }
        return Double.parseDouble(String.format("%.2f", holdingYield));
    }

    public String getChangeByName(String name){
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("DOW JONES", "%255EDJI");
        nameMap.put("S&P 500", "%255EGSPC");
        nameMap.put("NASDAQ", "%255EIXIC");
        nameMap.put("SSE Composite Index", "000001.SS");
        HttpResponse<String> response = null;
        try {
            response = Unirest.get("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/get-detail?region=US&symbol=" + nameMap.get(name))
                    .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                    .header("x-rapidapi-key", "6d624fa3a5mshb585e5b08e906b4p11c6e4jsnd0528688de63")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        JSONObject json = JSON.parseObject(response.getBody());
        JSONObject price = json.getJSONObject("price");
        return price.getJSONObject("regularMarketChangePercent").getString("fmt");
    }

    public String getPriceBySymbol(String symbol){
        HttpResponse<String> response = null;
        try {
            response = Unirest.get("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-financials?region=US&symbol=" + symbol)
                    .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                    .header("x-rapidapi-key", "6d624fa3a5mshb585e5b08e906b4p11c6e4jsnd0528688de63")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        JSONObject json = JSON.parseObject(response.getBody());
        JSONObject price = json.getJSONObject("price");
        return price.getJSONObject("regularMarketPrice").getString("fmt");
    }

    @Override
    public void initData() {
        Map<String, Double> yieldMap = new TreeMap<>();
        for(NetWorth netWorth : worthService.getNetWorthList()){
            if(netWorth instanceof Investment){
                double purchasePrice = ((Investment) netWorth).getPurchasePrice();
                double marketPrice = Double.parseDouble(getPriceBySymbol(((Investment) netWorth).getSymbol()));
                double gainRate = (marketPrice - purchasePrice) / purchasePrice;
                yieldMap.put(netWorth.getName(), Double.valueOf(String.format("%.2f", gainRate)));
                priceMap.put(netWorth.getName(), marketPrice);
            }
        }
        List<Map.Entry<String, Double>> list = new ArrayList<>(yieldMap.entrySet());
        list.sort((o1, o2) -> {
            if (o1.getValue() < 0 && o2.getValue() < 0){
                return o1.getValue().compareTo(o2.getValue());
            }
            return o2.getValue().compareTo(o1.getValue());
        });
        for(Map.Entry<String, Double> map : list) {
            sortedYieldMap.put(map.getKey(), map.getValue());
        }
    }
}
