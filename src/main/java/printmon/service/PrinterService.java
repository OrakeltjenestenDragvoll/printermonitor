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

    public boolean loadFromConfiguration() {
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
        List<Printer> printerList = printerRepository.getAll();
        for(Printer printer : printerList) {
            try {
                printer.setPaperCounter(webScraper.extractCounterStatus(printer.getId()));
                printer.setStatus(webScraper.extractPrinterStatus(printer.getId()));
            }catch (NullPointerException e) {
                //return false;
            }
        }
        replacePrinterList(printerList);
        return true;
    }
}
