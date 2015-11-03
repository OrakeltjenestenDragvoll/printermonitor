package printmon.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import printmon.model.Printer;
import printmon.repository.PrinterRepository;

import java.util.List;

@Service
public class PrinterService {

    @Autowired
    private PrinterRepository printerRepository;
    @Autowired
    private WebScraper webScraper;

    public void save(Printer printer) {
        printerRepository.save(printer);
    }

    public void delete(Printer printer) {
        printerRepository.delete(printer);
    }

    public void update(Printer printer) {
        printerRepository.update(printer);
    }

    public boolean updateFromWebInterface(int id) {
        Printer printer = printerRepository.findById(id);
        printer.setPaperCounter(webScraper.extractCounterStatus(id));
        printer.setStatus(webScraper.extractPrinterStatus(id));
        update(printer);
        return true;
    }

    public boolean updateAllFromWebInterface() {
        List<Printer> printerList = printerRepository.getAll();
        for(Printer printer : printerList) {
            printer.setPaperCounter(webScraper.extractCounterStatus(printer.getId()));
            printer.setStatus(webScraper.extractPrinterStatus(printer.getId()));
            update(printer);
        }
        return true;
    }
}
