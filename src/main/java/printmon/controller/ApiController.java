package printmon.controller;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import printmon.model.Printer;
import printmon.model.StringResponse;
import printmon.service.PrinterListReader;
import printmon.service.PrinterService;
import printmon.service.WebScraper;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
public class ApiController {

    @Autowired
    WebScraper webScraper;
    @Autowired
    PrinterListReader printerListReader;
    @Autowired
    PrinterService printerService;


    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public List<Printer> registeredPrinters() {
        return printerService.getAll();
    }

    @RequestMapping("/document")
    public String document(@RequestParam(value="url", defaultValue="http://www.google.com") String url) {
        return webScraper.getDocument(url).text();
    }

    @CrossOrigin()
    @RequestMapping(value = "/last_update", method = RequestMethod.GET)
    public String lastUpdate() {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("HH:mm, dd/MM/yyyy");
        return fmt.print(printerService.lastUpdate);
    }

    @RequestMapping(value = "/get_paper_status", method = RequestMethod.GET, produces = "application/json")
    public StringResponse paperCount(@RequestParam(value="id") int id) {
        return new StringResponse(String.valueOf(webScraper.extractCounterStatus(id)));
    }

    @RequestMapping(value = "/get_printer_status", method = RequestMethod.GET, produces = "application/json")
    public StringResponse getPrinterStatus(@RequestParam(value="id") int id) {
        return new StringResponse(webScraper.extractPrinterStatus(id));
    }

    @RequestMapping(value = "/get_printer", method = RequestMethod.GET, produces = "application/json")
    public Printer getPrinter(@RequestParam(value="id") int id) {
        return printerService.findById(id);
    }
}
