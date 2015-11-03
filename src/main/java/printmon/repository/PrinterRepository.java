package printmon.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import printmon.model.Printer;
import printmon.service.PrinterListReader;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PrinterRepository {

    @Autowired
    PrinterListReader printerListReader;

    private List<Printer> printers = new ArrayList<>();

    public void save(Printer printer) {
        printers.add(printer);
    }

    public void delete(Printer printer) {
        printers.remove(printer);
    }

    public void update(Printer printer) {
        delete(findById(printer.getId()));
        save(printer);
    }

    public List<Printer> getAll() {
        return printers;
    }

    public Printer findById(int id) {
        for(Printer printer : printers)
            if(printer.getId() == id)
                return printer;
        return null;
    }

    public void loadFromConfiguration() {
        this.printers = printerListReader.readPrinterList();
    }
}
