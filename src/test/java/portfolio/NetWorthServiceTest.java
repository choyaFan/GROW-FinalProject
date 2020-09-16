package portfolio;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import portfolio.dao.NetWorthDao;
import portfolio.entity.Cash;
import portfolio.entity.CashFlow;
import portfolio.entity.Investment;
import portfolio.service.NetWorthService;
import portfolio.service.NetWorthServiceImpl;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class NetWorthServiceTest {

    private NetWorthService service;
    @Mock
    private NetWorthDao netWorthDao;
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
    List<Cash> cashList = new ArrayList<>();
    List<Investment> investmentList = new ArrayList<>();
    @Before
    public void init() throws ParseException{
        MockitoAnnotations.initMocks(this);
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
        investmentList.add(new Investment("i10001","Diageo plc","DEO","Bond", day1,114.83,130));
        investmentList.add(new Investment("i10002","Vanda","VNDA","Bond",day2,10.80,240));
        investmentList.add(new Investment("i10003","NexPoint","NHF","Stock", day3,24.16,125));
        investmentList.add(new Investment("i10004","Cadence","CDNS","Future", day4,38.02,70));
        investmentList.add(new Investment("i10005","Silicon","SIMO","ETF", day5,53.64,110));
        investmentList.add(new Investment("i10006","Ares","ARES","Future", day6,16.87,65));
        investmentList.add(new Investment("i10007","Itau","ITCB","Stock", day7,11.45,50));
        investmentList.add(new Investment("i10008","Valley","VLY","Stock", day8,9.8,200));
        investmentList.add(new Investment("i10009","Echo","ECHO","Bond", day9,32.4,60));
        investmentList.add(new Investment("i10010","Guggenheim","GGM","ETF", day9,21.78,80));
        cashList.add(new Cash("c1","Citi","checking",1100,day4));
        cashList.add(new Cash("c2","ABC","checking",500,day4));
        cashList.add(new Cash("c3","Citi","saving",10000,day10));
        cashList.add(new Cash("c4","AAA","saving",8888,day11));
        when(netWorthDao.getAllCash()).thenReturn(cashList);
        when(netWorthDao.getAllInvestments()).thenReturn(investmentList);
        service = new NetWorthServiceImpl(netWorthDao);


    }
    @Test
    public void test_getAllCash(){
        List<Cash> cashList = this.service.getAllCash();
        cashList.forEach(System.out::println);
    }

    @Test
    public void test_getCashTotalValue(){
        assertEquals(1100+500+10000+8888,service.getCashTotalValue());
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
    public void test_getIncome(){
        CashFlow cf = service.getIncome(day8,day7);
        List<Cash> positiveList = cf.getPositiveList();
        positiveList.forEach(System.out::println);
        List<Cash> negativeList = cf.getPositiveList();
        negativeList.forEach(System.out::println);
        System.out.println("Total:" + cf.getTotalValue());
    }

    @Test
    public void test_getSpending(){
        CashFlow cf = service.getSpending(day8,day7);
        List<Cash> cashList = cf.getPositiveList();
        cashList.forEach(System.out::println);
        System.out.println("Total:" + cf.getTotalValue());
    }
}
