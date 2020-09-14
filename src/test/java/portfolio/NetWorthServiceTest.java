package portfolio;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetWorthServiceTest {

    private static final NetWorthService service = new NetWorthServiceImpl();
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


    @BeforeAll
    public static void init() throws ParseException, NoSuchFieldException, IllegalAccessException {
        day1 = sdf.parse("5/4/2015");
        day2 = sdf.parse("7/27/2015");
        day3 = sdf.parse("3/1/2018");
        day4 = sdf.parse("5/4/2018");
        day5 = sdf.parse("6/13/2017");
        day6 = sdf.parse("12/10/2014");
        day7 = sdf.parse("6/16/2016");
        day8 = sdf.parse("7/16/2014");
        day9 = sdf.parse("7/26/2018");
        day10 = sdf.parse("6/1/2020");
        day11 = sdf.parse("1/1/2020");


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
        netWorthMap.put("c1",new Cash("c1","Citi","checking",1100,day4));
        netWorthMap.put("c2",new Cash("c2","ABC","checking",500,day4));
        netWorthMap.put("c3",new Cash("c3","Citi","saving",10000,day10));
        netWorthMap.put("c4",new Cash("c4","AAA","saving",8888,day11));

        Field field = NetWorthServiceImpl.class.getField("netWorthMap");
        field.setAccessible(true);
        field.set(service,netWorthMap);

    }
    @Test
    public void test_getAllCash(){
        List<Cash> cashList = service.getAllCash();
        cashList.forEach(System.out::println);
    }

    @Test
    public void test_getCashTotalValue(){
        assertEquals(1100+500+10000+8888,service.getCashTotalValue());
    }

    @Test
    public void test_getCashByTime(){
        Map<Date,Double> cashMap = service.getCashByTime(day4,day10);
        cashMap.keySet().forEach(p->System.out.println(p+":"+cashMap.get(p)));
        assertEquals(3,cashMap.size());
        assertEquals(1600,cashMap.get(day4));
        assertEquals(1,service.getCashByTime(day4,day4).size());
        assertEquals(0,service.getCashByTime(day10,day11).size());
    }

    @Test
    public void test_getAllInvestments(){
        List<Investment> investments = service.getAllInvestments();
        investments.forEach(System.out::println);
    }

    @Test
    public void test_getInvestmentTotalValue(){
        System.out.println(service.getInvestmentTotalValue());
    }

    @Test
    public void test_getInvestmentsByTime(){
        Map<Date,Double> investmentMap = service.getInvestmentsByTime(day8,day9);
        investmentMap.keySet().forEach(p->System.out.println(p+":"+investmentMap.get(p)));
        assertEquals(9,investmentMap.size());
        assertEquals(5,service.getInvestmentsByTime(day8,day7).size());
        assertEquals(netWorthMap.get("i10005").getValue(),investmentMap.get(day5));
        assertEquals(netWorthMap.get("i10007").getValue(),investmentMap.get(day7));
        assertEquals(netWorthMap.get("i10009").getValue()+netWorthMap.get("i10010").getValue(),investmentMap.get(day9));
    }

    @Test
    public void test_getNetWorthByTime(){
        Map<Date,Double> nwMap = service.getNetWorthByTime(day8,day10);
        nwMap.keySet().forEach(p->System.out.println(p+":"+nwMap.get(p)));
        assertEquals(11,nwMap.size());
        assertEquals(netWorthMap.get("i10007").getValue(),nwMap.get(day7));
        assertEquals(netWorthMap.get("i10004").getValue()
                +netWorthMap.get("c1").getValue()
                +netWorthMap.get("c2").getValue()
                ,nwMap.get(day4));
        assertEquals(5,service.getNetWorthByTime(day8,day7).size());
    }

    @Test
    public void test_getIncome(){
        CashFlow cf = service.getIncome(day8,day7);
        System.out.println(cf.getTotalValue());
    }

    @Test
    public void test_getSpending(){
        CashFlow cf = service.getSpending(day8,day7);
        System.out.println(cf.getTotalValue());
    }
}
