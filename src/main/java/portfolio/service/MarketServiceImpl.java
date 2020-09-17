package portfolio.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import portfolio.entity.Investment;
import portfolio.entity.NetWorth;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
@EnableScheduling
public class MarketServiceImpl implements MarketService {
    Map<String, Double> DEFAULT_YIELD = new LinkedHashMap<>(){
        {
            put("Cadence", 7.77);
            put("Ares", 7.38);
            put("Diageo plc", 7.19);
            put("NexPoint", -0.62);
            put("Itau", -0.59);
            put("Silicon", -0.32);
            put("Valley", -0.27);
            put("Echo", -0.17);
            put("Guggenheim", -0.16);
            put("Vanda", -0.11);
        }
    };
    Map<String, Double> DEFAULT_PRICE = new LinkedHashMap<>(){
        {
            put("Valley", 7.2);
            put("Ares", 40.17);
            put("Diageo plc", 136.59);
            put("Vanda", 9.63);
            put("Itau", 4.68);
            put("Silicon", 36.61);
            put("NexPoint", 9.23);
            put("Cadence", 105.23);
            put("Echo", 26.75);
            put("Guggenheim", 18.22);
        }
    };

    private final NetWorthService worthService;
    Map<String, Double> sortedYieldMap = DEFAULT_YIELD;
    Map<String, Double> priceMap = DEFAULT_PRICE;

    @Autowired
    public MarketServiceImpl(NetWorthServiceImpl netWorthService) {
        this.worthService = netWorthService;
    }

    @Override
    public String getIndices() {
        JSONArray indexArray = new JSONArray();
        indexArray.add(getIndexByName("DOW JONES"));
        indexArray.add(getIndexByName("S&P 500"));
        indexArray.add(getIndexByName("NASDAQ"));
        indexArray.add(getIndexByName("SSE Composite Index"));
        return JSON.toJSONString(indexArray);
    }

    @Override
    public String getGainers() {
        JSONArray json = new JSONArray();
        int count = 0;
        for(String name : sortedYieldMap.keySet()){
            if(sortedYieldMap.get(name) >= 0 && count < 5){
                JSONObject temp = new JSONObject();
                count++;
                temp.put("name", name);
                temp.put("percent", sortedYieldMap.get(name));
                temp.put("value", priceMap.get(name));
                json.add(temp);
            }
            else break;
        }
        return JSON.toJSONString(json);
    }

    @Override
    public String getLosers() {
        JSONArray json = new JSONArray();
        int count = 0;
        for(String name : sortedYieldMap.keySet()){
            if(sortedYieldMap.get(name) < 0 && count < 5){
                JSONObject temp = new JSONObject();
                count++;
                temp.put("name", name);
                temp.put("percent", sortedYieldMap.get(name));
                temp.put("value", priceMap.get(name));
                json.add(temp);
            }
        }
        return JSON.toJSONString(json);
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

    public JSONObject getIndexByName(String name) {
        Map<String, String> nameMap = new HashMap<>();
        JSONObject indexJson = new JSONObject();
        nameMap.put("DOW JONES", "%255EDJI");
        nameMap.put("S&P 500", "%255EGSPC");
        nameMap.put("NASDAQ", "%255EIXIC");
        nameMap.put("SSE Composite Index", "000001.SS");
        HttpResponse<String> response = null;
        try {
            response = Unirest.get("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/get-detail?region=US&symbol=" + nameMap.get(name))
                    .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                    .header("x-rapidapi-key", "7d74e11133msh9115d26e2930f03p1ba38fjsn5065c1dc1f0f")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        JSONObject json = JSON.parseObject(response.getBody());
        JSONObject price = json.getJSONObject("price");
        indexJson.put("name", name);
        indexJson.put("percent",price.getJSONObject("regularMarketChangePercent").getString("fmt"));
        indexJson.put("value",price.getJSONObject("regularMarketPrice").getString("fmt"));
        return indexJson;
    }

    public String getPriceBySymbol(String symbol){
        HttpResponse<String> response = null;
        try {
            response = Unirest.get("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-financials?region=US&symbol=" + symbol)
                    .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                    .header("x-rapidapi-key", "7d74e11133msh9115d26e2930f03p1ba38fjsn5065c1dc1f0f")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        JSONObject json = JSON.parseObject(response.getBody());
        JSONObject price = json.getJSONObject("price");
        return price.getJSONObject("regularMarketPrice").getString("fmt");
    }

    @Scheduled(cron = "0 33 9 * * ?")
    @Override
    public void initData() {
        System.out.println("Preparing market data...");
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
        // sort the Yield Map
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
        System.out.println("Init market data success");
    }
}
