//package portfolio;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.lang.reflect.Field;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//import static org.junit.Assert.*;
//import static org.mockito.Mockito.when;
//
//
//public class MarketServiceTest {
//    private static final MarketService service = new MarketServiceImpl();
//    private static final List<Investment> investmentList = new ArrayList<>();
//    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//    @Mock
//    NetWorthDao netWorthDao;
//
//    @BeforeClass
//    public static void init() throws ParseException, NoSuchFieldException, IllegalAccessException {
//        MockitoAnnotations.initMocks(this);
//        Date day1 = sdf.parse("5/4/2015");
//        Date day2 = sdf.parse("7/27/2015");
//        Date day3 = sdf.parse("3/1/2018");
//        Date day4 = sdf.parse("5/4/2018");
//        Date day5 = sdf.parse("6/13/2017");
//        Date day6 = sdf.parse("12/10/2014");
//        Date day7 = sdf.parse("6/16/2016");
//        Date day8 = sdf.parse("7/16/2014");
//        Date day9 = sdf.parse("7/26/2018");
//        Date day10 = sdf.parse("6/1/2020");
//
//        investmentList.add(new Investment("i10001","Diageo plc","DEO", "bond", day1,114.83,130));
//        investmentList.add(new Investment("i10002","Vanda","VNDA","bond", day2,10.80,240));
//        investmentList.add(new Investment("i10003","NexPoint","NHF", "bond", day3,24.16,125));
//        investmentList.add(new Investment("i10004","Cadence","CDNS", "bond", day4,38.02,70));
//        investmentList.add(new Investment("i10005","Silicon","SIMO", "bond", day5,53.64,110));
//        investmentList.add(new Investment("i10006","Ares","ARES", "bond", day6,16.87,65));
//        investmentList.add(new Investment("i10007","Itau","ITCB", "bond", day7,11.45,50));
//        investmentList.add(new Investment("i10008","Valley","VLY", "bond", day8,9.8,200));
//        investmentList.add(new Investment("i10009","Echo","ECHO", "bond", day9,32.4,60));
//        investmentList.add(new Investment("i10010","Guggenheim","GGM", "bond", day10,21.78,80));
//        when(netWorthDao.getAllInvestments()).thenReturn(investmentList);
//        NetWorthService netWorthService = new NetWorthServiceImpl(netWorthDao);
//        Field field = MarketServiceImpl.class.getDeclaredField("worthService");
//        field.setAccessible(true);
//        field.set(service,netWorthService);
//        service.initData();
//    }
//
//    @Test
//    public void testIndices(){
////        service.initData();
//        assertEquals("{\"NASDAQ\":\"-0.60%\",\"S&P 500\":\"0.05%\",\"DOW JONES\":\"0.48%\",\"SSE Composite Index\":\"0.57%\"}", service.getIndicesPercent());
//    }
//
//    @Test
//    public void testGainer(){
//        assertEquals("{\"Cadence\":1.67,\"Ares\":1.36,\"Diageo plc\":0.17}",service.getGainersPercent());
//    }
//
//    @Test
//    public void testLoser() {
//        assertEquals("{\"NexPoint\":-0.62,\"Itau\":-0.59,\"Silicon\":-0.34,\"Valley\":-0.26,\"Echo\":-0.17}", service.getLosersPercent());
//    }
//
//    @Test
//    public void testHolding(){
//        System.out.println(service.getHoldings() == 0.88);
//    }
//
//    @Test
//    public void testSort(){
//        List<Double> list = new ArrayList<>();
//        list.add(101.51);
//        list.add(134.62);
//        list.add(9.25);
//        list.add(35.57);
//        list.add(9.74);
//        list.sort(Comparator.reverseOrder());
//        System.out.println(list);
//    }
//}
