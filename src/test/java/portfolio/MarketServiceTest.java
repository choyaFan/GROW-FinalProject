package portfolio;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MarketServiceTest {
    private static final MarketService service = new MarketServiceImpl();
    private static final Map<String, NetWorth> netWorthMap = new HashMap<>();
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    private static Date day1;
    private static Date day2;
    private static Date day3;
    private static Date day4;
    private static Date day5;
    private static Date day6;
    private static Date day7;
    private static Date day8;
    private static Date day9;
    private static Date day10;
    private static Date day11;

    @Before
    public void init() throws ParseException, NoSuchFieldException, IllegalAccessException {
//        day1 = sdf.parse("5/4/2015");
//        day2 = sdf.parse("7/27/2015");
//        day3 = sdf.parse("3/1/2018");
//        day4 = sdf.parse("5/4/2018");
//        day5 = sdf.parse("6/13/2017");
//        day6 = sdf.parse("12/10/2014");
//        day7 = sdf.parse("6/16/2016");
//        day8 = sdf.parse("7/16/2014");
//        day9 = sdf.parse("7/26/2018");
//        day10 = sdf.parse("6/1/2020");
//        day11 = sdf.parse("1/1/2020");

        netWorthMap.put("i10001",new Investment("i10001","Diageo plc","DEO", day1,114.83,130));
        netWorthMap.put("i10002",new Investment("i10002","Vanda","VNDA",day2,10.80,240));
        netWorthMap.put("i10003",new Investment("i10003","NexPoint","NHF", day3,24.16,125));
//        netWorthMap.put("i10004",new Investment("i10004","Cadence","CDNS", day4,38.02,70));
//        netWorthMap.put("i10005",new Investment("i10005","Silicon","SIMO", day5,53.64,110));
//        netWorthMap.put("i10006",new Investment("i10006","Ares","ARES", day6,16.87,65));
//        netWorthMap.put("i10007",new Investment("i10007","Itau","ITCB", day7,11.45,50));
//        netWorthMap.put("i10008",new Investment("i10008","Valley","VLY", day8,9.8,200));
//        netWorthMap.put("i10009",new Investment("i10009","Echo","ECHO", day9,32.4,60));
//        netWorthMap.put("i10010",new Investment("i10010","Guggenheim","GGM", day9,21.78,80));

//        netWorthMap.put("c1",new Cash("c1","Citi","checking",1100,day4));
//        netWorthMap.put("c2",new Cash("c2","ABC","checking",500,day4));
//        netWorthMap.put("c3",new Cash("c3","Citi","saving",10000,day10));
//        netWorthMap.put("c4",new Cash("c4","AAA","saving",8888,day11));

        Field field = MarketServiceImpl.class.getField("netWorthMap");
        field.setAccessible(true);
        field.set(service,netWorthMap);

        service.initData();
    }

    @Test
    public void testCrawler(){
        new MarketServiceImpl().getIndices();
    }

    @Test
    public void testGainer(){
        System.out.println(service.getGainers());
    }

    @Test
    public void testLoser(){
        System.out.println(service.getLosers());
    }

    @Test
    public void testHolding(){
        System.out.println(service.getHoldings());
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
