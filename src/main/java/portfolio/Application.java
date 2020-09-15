package portfolio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.text.SimpleDateFormat;

@SpringBootApplication
public class Application {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);
    }
}
