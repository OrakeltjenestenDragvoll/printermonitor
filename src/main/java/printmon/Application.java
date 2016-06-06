package printmon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.apache.log4j.Logger;
import printmon.service.WebScraper;

@SpringBootApplication
@EnableScheduling
public class Application {

    static Logger log = Logger.getLogger(Application.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        if(args.length >0) {
            if("debug".equals(args[0])) {
                WebScraper.debug = true;
                log.info("Running printmon in debug mode");
            }
            else if("production".equals(args[0])){
                WebScraper.debug = false;
                log.info("Running printmon in production mode");
            }
        }
        else {
            WebScraper.debug = true;
            log.info("Running printmon in debug mode");
        }
    }
}
