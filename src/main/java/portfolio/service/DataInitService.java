package portfolio.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import portfolio.entity.Investment;
import portfolio.entity.NetWorth;

import java.util.*;

@EnableScheduling
@Service
public class DataInitService {
    private final ApplicationContext context;
    private final NetWorthService worthService;

    Map<String, Double> sortedYieldMap = new LinkedHashMap<>();
    Map<String, Double> priceMap = new LinkedHashMap<>();

    @Autowired
    public DataInitService(ApplicationContext applicationContext, NetWorthService worthService) {
        this.context = applicationContext;
        this.worthService = worthService;
    }

    @Scheduled(cron = "0 12 19 * * ?")
    public void refreshMarketData() {
        System.out.println("Preparing market data...");
        Map<String, Double> yieldMap = new TreeMap<>();
        for(NetWorth netWorth : worthService.getNetWorthList()) {
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
        this.publishEvent();
    }

    public void publishEvent(){
        context.publishEvent(new RefreshFinishEvent(this, priceMap, sortedYieldMap));
    }

    public String getPriceBySymbol(String symbol) {
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
}
