package printmon.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import printmon.model.Printer;
import printmon.model.StringResponse;
import printmon.service.PrinterListReader;
import printmon.service.PrinterService;
import printmon.service.WebScraper;

@RestController
public class ApiController {

    @Autowired
    WebScraper webScraper;
    @Autowired
    PrinterListReader printerListReader;
    @Autowired
    PrinterService printerService;


    @RequestMapping("/")
    public String root() {
        return "Printmon version 0.7. See https://github.com/OrakeltjenestenDragvoll/printmon for documentation";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET, produces = "application/json")
    public List<Printer> registeredPrinters() {
        return printerService.getAll();
    }

    @RequestMapping("/document")
    public String document(@RequestParam(value="url", defaultValue="http://www.google.com") String url) {
        return webScraper.getDocument(url).text();
    }

    @RequestMapping(value = "/last_update", method = RequestMethod.GET)
    public long lastUpdate() {
        return printerService.lastUpdate;
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

    @RequestMapping(value = "/update_printer", method = RequestMethod.GET, produces = "application/json")
    public StringResponse updatePrinter(@RequestParam(value="id") int id) {
        if(printerService.updateFromWebInterface(id))
            return new StringResponse("Action successful");
        else
            return new StringResponse("Action failed");
    }

    @RequestMapping(value = "/update_all_printers", method = RequestMethod.GET, produces = "application/json")
    public StringResponse updateAllPrinters() {
        if(printerService.updateAllFromWebInterface())
            return new StringResponse("Action successful");
        else
            return new StringResponse("Action failed");
    }
}
