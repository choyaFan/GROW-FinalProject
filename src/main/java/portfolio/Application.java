package portfolio;

import com.mongodb.MongoException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
public class Application {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
        NetWorthDao netWorthDao = context.getBean(NetWorthDaoImpl.class);
        System.out.println("====LINK TEST====");
        netWorthDao.getAllCash().forEach(System.out::println);
        try{
            Date start = sdf.parse("7/16/2014");
            Date end = sdf.parse("6/1/2020");
            netWorthDao.getNetWorthByTime2(start,end);
        } catch (MongoException | ParseException ex){
            System.err.println(ex.getMessage());
        }
    }
}
