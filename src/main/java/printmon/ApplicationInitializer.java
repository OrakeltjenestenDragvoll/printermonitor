package printmon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import printmon.service.PrinterService;

@Component
public class ApplicationInitializer implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    PrinterService printerService;

    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        printerService.loadFromConfiguration();
    }
}