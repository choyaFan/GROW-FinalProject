package portfolio;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class MarketServiceImpl implements MarketService {
    private final NetWorthService worthService;
    Map<String, Double> sortedYieldMap = new LinkedHashMap<>();
    Map<String, Double> priceMap = new LinkedHashMap<>();
    JSONObject index;

    @Autowired
    public MarketServiceImpl(NetWorthServiceImpl netWorthService) {
        this.worthService = netWorthService;
    }

    @Override
    public String getIndices() {
        JSONObject indices = new JSONObject();
        getIndexByName("DOW JONES");
        getIndexByName("S&P 500");
        getIndexByName("NASDAQ");
        getIndexByName("SSE Composite Index");
        return JSON.toJSONString(indices);
    }

    @Override
    public String getGainers() {
        Map<String, Double> gainerMap = new LinkedHashMap<>();
        for(String name : sortedYieldMap.keySet()){
            if(sortedYieldMap.get(name) >= 0 && gainerMap.size() <= 10){
                gainerMap.put(name + "(Percent)", sortedYieldMap.get(name));
                gainerMap.put(name + "(Value)", priceMap.get(name));
            }
            else break;
        }
        System.out.println(gainerMap);
        return JSON.toJSONString(gainerMap);
    }

    @Override
    public String getLosers() {
        Map<String, Double> loserMap = new LinkedHashMap<>();
        for(String name : sortedYieldMap.keySet()){
            if(sortedYieldMap.get(name) <= 0 && loserMap.size() < 10){
                loserMap.put(name + "(Percent)", sortedYieldMap.get(name));
                loserMap.put(name + "(Value)", priceMap.get(name));
            }
        }
        System.out.println(loserMap);
        return JSON.toJSONString(loserMap);
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

    public void getIndexByName(String name) {
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("DOW JONES", "%255EDJI");
        nameMap.put("S&P 500", "%255EGSPC");
        nameMap.put("NASDAQ", "%255EIXIC");
        nameMap.put("SSE Composite Index", "000001.SS");
        HttpResponse<String> response = null;
        try {
            response = Unirest.get("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/get-detail?region=US&symbol=" + nameMap.get(name))
                    .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                    .header("x-rapidapi-key", "cdba932e57mshd1152bf1d71ceb7p1910c3jsn7fe067c2634b")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        JSONObject json = JSON.parseObject(response.getBody());
        JSONObject price = json.getJSONObject("price");
        index.put("DOW JONES(PERCENT)",price.getJSONObject("regularMarketChangePercent").getString("fmt"));
        index.put("DOW JONES(VALUE)",price.getJSONObject("regularMarketPrice").getString("fmt"));
    }

    public String getPriceBySymbol(String symbol){
        HttpResponse<String> response = null;
        try {
            response = Unirest.get("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-financials?region=US&symbol=" + symbol)
                    .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                    .header("x-rapidapi-key", "cdba932e57mshd1152bf1d71ceb7p1910c3jsn7fe067c2634b")
                    .asString();
        } catch (UnirestException e) {
            e.printStackTrace();
        }
        JSONObject json = JSON.parseObject(response.getBody());
        JSONObject price = json.getJSONObject("price");
        return price.getJSONObject("regularMarketPrice").getString("fmt");
    }

    @PostConstruct
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
    }
}
