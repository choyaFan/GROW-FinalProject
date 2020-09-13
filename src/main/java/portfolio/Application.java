package portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        // Get profile-specific properties.
        ResourcesBean resourcesBean = context.getBean(ResourcesBean.class);
        System.out.println("Profile-specific properties: " + resourcesBean);

    }

    @Bean
    public Map<String, NetWorth> netWorthMap() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Map<String, NetWorth> netWorthMap = new HashMap<>();
        netWorthMap.put("i10001",new Investment("i10001","Diageo plc","DEO", sdf.parse("5/4/2015"),114.83,130));
        netWorthMap.put("i10002",new Investment("i10002","Vanda","VNDA", sdf.parse("7/27/2015"),10.80,240));
        netWorthMap.put("i10003",new Investment("i10003","NexPoint","NHF", sdf.parse("3/1/2018"),24.16,125));
        netWorthMap.put("i10004",new Investment("i10004","Cadence","CDNS", sdf.parse("5/4/2018"),38.02,70));
        netWorthMap.put("i10005",new Investment("i10005","Silicon","SIMO", sdf.parse("6/13/2017"),53.64,110));
        netWorthMap.put("i10006",new Investment("i10006","Ares","ARES", sdf.parse("12/10/2014"),16.87,65));
        netWorthMap.put("i10007",new Investment("i10007","Itau","ITCB", sdf.parse("6/16/2016"),11.45,50));
        netWorthMap.put("i10008",new Investment("i10008","Valley","VLY", sdf.parse("7/16/2014"),9.8,200));
        netWorthMap.put("i10009",new Investment("i10009","Echo","ECHO", sdf.parse("7/26/2018"),32.4,60));
        netWorthMap.put("i10010",new Investment("i10010","Guggenheim","GGM", sdf.parse("7/26/2018"),21.78,80));
        netWorthMap.put("c1",new Cash("c1","Citi","checking",1100,sdf.parse("5/4/2018")));
        netWorthMap.put("c2",new Cash("c2","ABC","checking",500,sdf.parse("5/4/2018")));
        netWorthMap.put("c3",new Cash("c3","Citi","saving",10000,sdf.parse("6/1/2020")));
        netWorthMap.put("c4",new Cash("c4","AAA","saving",8888,sdf.parse("1/1/2020")));
        return netWorthMap;
    }
}
