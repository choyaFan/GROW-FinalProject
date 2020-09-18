package portfolio.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Service;
import portfolio.dao.MarketDataDao;
import portfolio.entity.Index;
import portfolio.entity.Investment;
import portfolio.entity.NetWorth;

import javax.annotation.PostConstruct;
import javax.lang.model.type.UnionType;
import java.util.*;
import java.util.concurrent.*;

@Service
public class MarketServiceImpl implements MarketService {
//    private static final Map<String, Double> DEFAULT_YIELD;
//    private static final Map<String, Double> DEFAULT_PRICE;
//    static {
//            DEFAULT_YIELD = new LinkedHashMap<>();
//            DEFAULT_YIELD.put("Cadence", 7.77);
//            DEFAULT_YIELD.put("Ares", 7.38);
//            DEFAULT_YIELD.put("Diageo plc", 7.19);
//            DEFAULT_YIELD.put("NexPoint", -0.62);
//            DEFAULT_YIELD.put("Itau", -0.59);
//            DEFAULT_YIELD.put("Silicon", -0.32);
//            DEFAULT_YIELD.put("Valley", -0.27);
//            DEFAULT_YIELD.put("Echo", -0.17);
//            DEFAULT_YIELD.put("Guggenheim", -0.16);
//            DEFAULT_YIELD.put("Vanda", -0.11);
//            DEFAULT_PRICE = new LinkedHashMap<>();
//            DEFAULT_PRICE.put("Valley", 7.2);
//            DEFAULT_PRICE.put("Ares", 40.17);
//            DEFAULT_PRICE.put("Diageo plc", 136.59);
//            DEFAULT_PRICE.put("Vanda", 9.63);
//            DEFAULT_PRICE.put("Itau", 4.68);
//            DEFAULT_PRICE.put("Silicon", 36.61);
//            DEFAULT_PRICE.put("NexPoint", 9.23);
//            DEFAULT_PRICE.put("Cadence", 105.23);
//            DEFAULT_PRICE.put("Echo", 26.75);
//            DEFAULT_PRICE.put("Guggenheim", 18.22);
//    }

    private final NetWorthService worthService;
    private final MarketDataDao marketDataDao;
    Map<String, Double> sortedYieldMap = new LinkedHashMap<>();
    Map<String, Double> priceMap = new LinkedHashMap<>();
    JSONArray indexArray = new JSONArray();

    @Autowired
    public MarketServiceImpl(NetWorthService netWorthService, MarketDataDao marketDataDao) {
        this.worthService = netWorthService;
        this.marketDataDao = marketDataDao;
    }

    @Override
    public String getIndices() {
        List<Future<HttpResponse<String>>> futures = new LinkedList<>();
        indexArray = new JSONArray();
        futures.add(getIndexAsync("DOW JONES"));
        futures.add(getIndexAsync("S&P 500"));
        futures.add(getIndexAsync("NASDAQ"));
        futures.add(getIndexAsync("SSE Composite Index"));
        for(Future<HttpResponse<String>> f : futures) {
            try {
                f.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        Unirest.shutDown();
//        indexArray.toJavaList(Index.class);
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

    public void getPriceMap(){
        priceMap = marketDataDao.getPriceMap();
    }

    public void getYieldMap(){
        sortedYieldMap = marketDataDao.getYieldMap();
    }

    @PostConstruct
    @Override
    public void updateData(){
        getYieldMap();
        getPriceMap();
    }

    @Async
    Future<HttpResponse<String>> getIndexAsync(String index) {
        JSONObject indexJson = new JSONObject();
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("DOW JONES", "%255EDJI");
        nameMap.put("S&P 500", "%255EGSPC");
        nameMap.put("NASDAQ", "%255EIXIC");
        nameMap.put("SSE Composite Index", "000001.SS");
        CompletableFuture<HttpResponse<String>> future = Unirest.get("https://apidojo-yahoo-finance-v1.p.rapidapi.com/stock/v2/get-financials?region=US&symbol=" + nameMap.get(index))
                .header("x-rapidapi-host", "apidojo-yahoo-finance-v1.p.rapidapi.com")
                .header("x-rapidapi-key", "7d74e11133msh9115d26e2930f03p1ba38fjsn5065c1dc1f0f")
                .asStringAsync(response -> {
                    JSONObject jsonObject = JSON.parseObject(response.getBody());
                    JSONObject price = jsonObject.getJSONObject("price");
                    indexJson.put("name", index);
                    indexJson.put("percent",price.getJSONObject("regularMarketChangePercent").getString("fmt"));
                    indexJson.put("value",price.getJSONObject("regularMarketPrice").getString("fmt"));
                });
        indexArray.add(indexJson);
        return future;
    }
}
