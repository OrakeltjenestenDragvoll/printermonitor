package printmon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import printmon.model.Printer;
import printmon.model.StringResponse;
import printmon.service.PrinterListReader;
import printmon.service.PrinterService;

@RestController
@RequestMapping("/admin")
public class AdminApiController {
    @Autowired
    PrinterService printerService;
    @Autowired
    PrinterListReader printerListReader;

    @RequestMapping("/load_printers")
    public StringResponse getPrinter() {
        if (printerService.loadFromConfiguration())
            return new StringResponse("Printers were loaded from configuration");
        else
            return new StringResponse("Could not load printers. Example files were created");
    }

    @RequestMapping("/save_printers")
    public StringResponse setPrinter() {
        if (printerListReader.writePrinterList())
            return new StringResponse("Printer list was written to configuration");
        else
            return new StringResponse("Action failed");
    }

    @RequestMapping(value = "/add_printer",method = RequestMethod.POST)
    public StringResponse addPrinter(@RequestBody Printer printer){
        printerService.save(printer);
        return new StringResponse("Printer added successfully");
    }

    @RequestMapping(value = "/delete_printer",method = RequestMethod.GET)
    public StringResponse deletePrinter(@RequestParam(value="id") int id){
        printerService.delete(printerService.findById(id));
        return new StringResponse("Printer deleted successfully");
    }
}
