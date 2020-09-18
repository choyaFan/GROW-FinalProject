package portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import portfolio.dao.MarketDataDao;

@Component
public class RefreshListener {
    private final MarketDataDao dataDao;
    private final MarketService marketService;

    @Autowired
    public RefreshListener(MarketDataDao dataDao, MarketService marketService) {
        this.dataDao = dataDao;
        this.marketService = marketService;
    }

    @EventListener
    public void onApplicationEvent(RefreshFinishEvent refreshFinishEvent) {
        dataDao.insertPrice(refreshFinishEvent.getPriceMap());
        dataDao.insertYield(refreshFinishEvent.getYieldMap());
        marketService.updateData();
    }
}
