package portfolio;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Component
public class MarketServiceImpl implements MarketService {
    @Autowired
    public Map<String,NetWorth> netWorthMap;

    @Override
    public String getIndices() {
        JSONObject indices = new JSONObject();
        indices.put("DOW JONES", getChangeByName("DOW JONES"));
        indices.put("S&P 500", getChangeByName("S&P 500"));
        indices.put("NASDAQ", getChangeByName("NASDAQ"));
        indices.put("SSE Composite Index", getChangeByName("SSE Composite Index"));
        System.out.println(indices);
        return JSON.toJSONString(indices);
    }

    @Override
    public String getGainers() {
        Map<String, String> gainerMap = new TreeMap<>();
        for(NetWorth netWorth : netWorthMap.values()){
            if(netWorth instanceof Investment)
                gainerMap.put(netWorth.getName(), getGainerBySymbol(((Investment) netWorth).getSymbol()));
        }
        List<Map.Entry<String, String>> list = new ArrayList<>(gainerMap.entrySet());
        list.sort((o1, o2) -> {
            Double f1 = Double.valueOf(o1.getValue());
            Double f2 = Double.valueOf(o2.getValue());
            System.out.println(f1 + "   " + f2);
            return f2.compareTo(f1);
        });
        Map<String, String> sortMap = new HashMap<>();
        for(Map.Entry<String, String> map : list) {
            sortMap.put(map.getKey(), map.getValue());
        }
        System.out.println(JSON.toJSONString(gainerMap));
        System.out.println(JSON.toJSONString(sortMap));
        return JSON.toJSONString(sortMap);
    }

    @Override
    public String getLosers() {
        return null;
    }

    @Override
    public double getHoldings() {
        return 0;
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

    public String getGainerBySymbol(String symbol){
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
}
