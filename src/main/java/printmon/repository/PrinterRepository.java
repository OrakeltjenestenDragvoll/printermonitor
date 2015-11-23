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

    private List<Printer> printerList = new ArrayList<>();

    public void save(Printer printer) {
        printerList.add(printer);
    }

    public void delete(Printer printer) {
        printerList.remove(printer);
    }

    public void update(Printer printer) {
        delete(findById(printer.getId()));
        save(printer);
    }

    public List<Printer> getAll() {
        return printerList;
    }

    public Printer findById(int id) {
        for(Printer printer : printerList)
            if(printer.getId() == id)
                return printer;
        return null;
    }

    public boolean loadFromConfiguration() {
        this.printerList = printerListReader.readPrinterList();
        if(printerList != null)
            return true;
        else
            return false;
    }

    public void replacePrinterList(List<Printer> printerList) {
        this.printerList = printerList;
    }
}
