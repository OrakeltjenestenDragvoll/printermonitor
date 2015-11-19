package printmon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import printmon.model.Printer;
import printmon.service.PrinterListReader;
import printmon.service.PrinterService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminApiController {

    @Autowired
    PrinterService printerService;
    @Autowired
    PrinterListReader printerListReader;

    @RequestMapping("/load_printers")
    public List<Printer> getPrinter() {
        printerService.loadFromConfiguration();
        return printerListReader.readPrinterList();
    }

    @RequestMapping("/save_printers")
    public void setPrinter() {
        printerListReader.writePrinter();
    }
}
