package printmon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Extend this class to periodically update external systems directly
 * without having to access the API. Implementations of this class are
 * application specific and generally discouraged.
 */
@Component
public abstract class ExternalUpdater {

    @Autowired
    PrinterService printerService;

    @Scheduled(fixedRate = 5*60*1000)
    public abstract void updatePrinters();
}
