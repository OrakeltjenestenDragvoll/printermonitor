package printmon.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import printmon.model.Printer;
import printmon.repository.PrinterRepository;
import printmon.service.PrinterListReader;
import printmon.service.PrinterService;
import printmon.service.WebScraper;

@RestController
public class ApiController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    WebScraper webScraper;

    @Autowired
    PrinterRepository printerRepository;
    @Autowired
    PrinterListReader printerListReader;
    @Autowired
    PrinterService printerService;

    @RequestMapping("/index")
    public List<Printer> registeredPrinters() {
        return printerRepository.getAll();
    }

    @RequestMapping("/document")
    public String document(@RequestParam(value="url", defaultValue="http://www.google.com") String url) {
        return webScraper.getDocument(url).text();
    }

    @RequestMapping("/get_paper_status")
    public int paperCount(@RequestParam(value="id") int id) {
        return webScraper.extractCounterStatus(id);
    }

    @RequestMapping("/get_printer_status")
    public String printerStatus(@RequestParam(value="id") int id) {
        return webScraper.extractPrinterStatus(id);
    }

    @RequestMapping("/get_printer")
    public List<Printer> getPrinter() {
        printerRepository.loadFromConfiguration();
        return printerListReader.readPrinterList();
    }

    @RequestMapping("/set_printer")
    public void setPrinter() {
        printerListReader.writePrinter();
    }

    @RequestMapping("/update_printer")
    public boolean updatePrinter(@RequestParam(value="id") int id) {
        return printerService.updateFromWebInterface(id);
    }

    @RequestMapping("/update_all_printers")
    public boolean updateAllPrinters() {
        return printerService.updateAllFromWebInterface();
    }
}
