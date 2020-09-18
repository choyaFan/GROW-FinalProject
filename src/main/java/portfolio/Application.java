package portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import portfolio.service.RefreshListener;

import java.text.SimpleDateFormat;

@SpringBootApplication
public class Application {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    }
}
