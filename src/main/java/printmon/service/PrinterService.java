package printmon.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import printmon.ApplicationInitializer;
import printmon.model.Printer;
import printmon.repository.PrinterRepository;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Service
public class PrinterService {

    @Autowired
    private PrinterRepository printerRepository;
    @Autowired
    private WebScraper webScraper;
    static Logger log = Logger.getLogger(ApplicationInitializer.class.getName());
    public long lastUpdate = System.currentTimeMillis();

    public void save(Printer printer) {
        printerRepository.save(printer);
    }

    public void delete(Printer printer) {
        printerRepository.delete(printer);
    }

    public void update(Printer printer) {
        printerRepository.update(printer);
    }

    public boolean loadFromConfiguration() {
        log.info("Printer configuration was loaded from resources");
        return printerRepository.loadFromConfiguration();
    }

    public Printer findById(int id) {
        return printerRepository.findById(id);
    }

    public List<Printer> getAll() {
        return printerRepository.getAll();
    }

    private void replacePrinterList(List<Printer> printerList) {
        printerRepository.replacePrinterList(printerList);
    }

    public boolean updateFromWebInterface(int id) {
        Printer printer;
        try {
            printer = printerRepository.findById(id);
            printer.setPaperCounter(webScraper.extractCounterStatus(id));
            printer.setStatus(webScraper.extractPrinterStatus(id));
        }catch (NullPointerException e) {
            return false;
        }
        update(printer);
        return true;
    }

    public boolean updateAllFromWebInterface() {
        //log.info("Updating printers...");
        List<Printer> printerList = printerRepository.getAll();
        for(Printer printer : printerList) {
            try {
                printer.setPaperCounter(webScraper.extractCounterStatus(printer.getId()));
                printer.setStatus(webScraper.extractPrinterStatus(printer.getId()));
                //log.info(printer.getName() + " updated with status " + printer.getStatus() + " and paper " + String.valueOf(printer.getPaperCounter()));
            }catch (NullPointerException e) {
                //return false;
            }
        }
        replacePrinterList(printerList);
        lastUpdate = System.currentTimeMillis();
        return true;
    }

    @Scheduled(fixedRate = 1*60*1000, initialDelay = 5000)
    public void performUpdate() {
        updateAllFromWebInterface();
    }
}
