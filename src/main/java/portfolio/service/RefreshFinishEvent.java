package portfolio.service;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

public class RefreshFinishEvent extends ApplicationEvent {
    private final Map<String, Double> priceMap;
    private final Map<String, Double> yieldMap;
    public RefreshFinishEvent(Object source, Map<String, Double> priceMap, Map<String, Double> yieldMap) {
        super(source);
        this.priceMap = priceMap;
        this.yieldMap = yieldMap;
    }

    public Map<String, Double> getPriceMap(){
        return this.priceMap;
    }

    public Map<String, Double> getYieldMap(){
        return this.yieldMap;
    }
}
