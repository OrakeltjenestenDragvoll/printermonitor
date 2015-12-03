package printmon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import printmon.service.WebScraper;

@SpringBootApplication
@EnableScheduling
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        if(args.length >0) {
            if("debug".equals(args[0])) {
                WebScraper.debug = true;
                System.out.println("Running printmon in debug mode");
            }
            else if("production".equals(args[0])){
                WebScraper.debug = false;
                System.out.println("Running printmon in production mode");
            }
        }
        else
            System.out.println("Running printmon in debug mode");
    }
}
