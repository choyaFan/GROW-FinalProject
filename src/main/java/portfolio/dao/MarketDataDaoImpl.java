package portfolio.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import portfolio.entity.Index;
import portfolio.entity.Price;
import portfolio.entity.Yield;

import javax.annotation.Resource;
import java.nio.channels.Pipe;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class MarketDataDaoImpl implements MarketDataDao {
    @Resource
    MongoTemplate mongoTemplate;



    @Override
    public void insertPrice(Map<String, Double> map) {
        System.out.println("inserting price data...");
        mongoTemplate.dropCollection("Price");
        List<Price> list = new ArrayList<>();
        for (String name : map.keySet()){
            list.add(new Price(name, map.get(name)));
        }
        mongoTemplate.insert(list, Price.class);
    }

    @Override
    public void insertYield(Map<String, Double> map) {
        System.out.println("inserting yield data...");
        mongoTemplate.dropCollection("Yield");
        List<Yield> list = new ArrayList<>();
        for (String name : map.keySet()){
            list.add(new Yield(name, map.get(name)));
        }
        mongoTemplate.insert(list, Yield.class);
    }

    @Override
    public void insertIndex(List<Index> indexList) {

    }

    @Override
    public void updatePrice(String name, double price) {

    }

    @Override
    public void updateYield(String name, double yield) {

    }

    @Override
    public Map<String, Double> getPriceMap() {
        List<Price> result = mongoTemplate.findAll(Price.class);
        Map<String, Double> map = new LinkedHashMap<>();
        for(Price price : result){
            map.put(price.getName(), price.getPrice());
        }
        return map;
    }

    @Override
    public Map<String, Double> getYieldMap() {
        List<Yield> result = mongoTemplate.findAll(Yield.class);
        Map<String, Double> map = new LinkedHashMap<>();
        for(Yield yield : result){
            map.put(yield.getName(), yield.getPrice());
        }
        return map;
    }

    @Override
    public Index getIndexByName() {
        return null;
    }
}
