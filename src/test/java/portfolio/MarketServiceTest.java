package portfolio;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import portfolio.dao.MarketDataDao;
import portfolio.dao.NetWorthDao;
import portfolio.entity.Investment;
import portfolio.service.MarketService;
import portfolio.service.MarketServiceImpl;
import portfolio.service.NetWorthService;
import portfolio.service.NetWorthServiceImpl;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.after;
import static org.mockito.Mockito.when;


public class MarketServiceTest {
    private static final List<Investment> investmentList = new ArrayList<>();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    private static final Map<String, Double> DEFAULT_YIELD;
    private static final Map<String, Double> DEFAULT_PRICE;
    MarketService service;
    static {
        DEFAULT_YIELD = new LinkedHashMap<>();
        DEFAULT_YIELD.put("Cadence", 7.77);
        DEFAULT_YIELD.put("Ares", 7.38);
        DEFAULT_YIELD.put("Diageo plc", 7.19);
        DEFAULT_YIELD.put("NexPoint", -0.62);
        DEFAULT_YIELD.put("Itau", -0.59);
        DEFAULT_YIELD.put("Silicon", -0.32);
        DEFAULT_YIELD.put("Valley", -0.27);
        DEFAULT_YIELD.put("Echo", -0.17);
        DEFAULT_YIELD.put("Guggenheim", -0.16);
        DEFAULT_YIELD.put("Vanda", -0.11);
        DEFAULT_PRICE = new LinkedHashMap<>();
        DEFAULT_PRICE.put("Valley", 7.2);
        DEFAULT_PRICE.put("Ares", 40.17);
        DEFAULT_PRICE.put("Diageo plc", 136.59);
        DEFAULT_PRICE.put("Vanda", 9.63);
        DEFAULT_PRICE.put("Itau", 4.68);
        DEFAULT_PRICE.put("Silicon", 36.61);
        DEFAULT_PRICE.put("NexPoint", 9.23);
        DEFAULT_PRICE.put("Cadence", 105.23);
        DEFAULT_PRICE.put("Echo", 26.75);
        DEFAULT_PRICE.put("Guggenheim", 18.22);
    }
    @Mock
    NetWorthDao netWorthDao;
    @Mock
    MarketDataDao marketDataDao;

    @Before
    public void init() throws ParseException, NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);

        when(marketDataDao.getPriceMap()).thenReturn(DEFAULT_PRICE);
        when(marketDataDao.getYieldMap()).thenReturn(DEFAULT_YIELD);
        NetWorthService netWorthService = new NetWorthServiceImpl(netWorthDao);
        service = new MarketServiceImpl(netWorthService, marketDataDao);
        Field field = MarketServiceImpl.class.getDeclaredField("worthService");
        field.setAccessible(true);
        field.set(service,netWorthService);
        service.updateData();
    }

    @Test
    public void testIndices(){
//        service.initData();
        System.out.println(service.getIndices());
    }

    @Test
    public void testGainer(){
        System.out.println(service.getGainers());
    }

    @Test
    public void testLoser() {
        System.out.println(service.getLosers());
    }

    @Test
    public void testHolding(){
        System.out.println(service.getHoldings() == 0.88);
    }

    @Test
    public void testSort(){
        List<Double> list = new ArrayList<>();
        list.add(101.51);
        list.add(134.62);
        list.add(9.25);
        list.add(35.57);
        list.add(9.74);
        list.sort(Comparator.reverseOrder());
        System.out.println(list);
    }
}
