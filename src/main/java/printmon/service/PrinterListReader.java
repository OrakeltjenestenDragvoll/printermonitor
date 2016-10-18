package printmon.service;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import printmon.model.Printer;
import printmon.repository.PrinterRepository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PrinterListReader {
    @Autowired
    PrinterRepository printerRepository;

    private String filePath = new File("").getAbsolutePath();
    private String printerFile = filePath.concat("\\resources\\printer.json");

    public List<Printer> readPrinterList() {
        Gson gson = new Gson();
        ArrayList<Printer> printers;
        Printer[] printerArray;

        try {
            JsonReader jsonReader = new JsonReader(new FileReader(printerFile));
            printerArray = gson.fromJson(jsonReader, Printer[].class);
            printers = new ArrayList(Arrays.asList(printerArray));
        }
        catch (FileNotFoundException e) {
            createDummyDirectory();
            return null;
        }
        return printers;
    }

    public boolean writePrinterList() {
        throw new NotImplementedException();
    }

    private void writePrinter() {
        Gson gson = new Gson();
        Printer printer = new Printer(1, "examplePrinter");
        printer.setModel("Aficio MP 5001");
        printer.setLocation("Somewhere");
        printer.setUrl("http://folk.ntnu.no/grasdalk/printers/10.207.7.18/");
        printer.setStatus("Alert");
        printer.setPaperCounter(1000);
        printerRepository.save(printer);

        try {
            JsonWriter writer = new JsonWriter(new FileWriter(printerFile));
            gson.toJson(printerRepository.getAll(), ArrayList.class, writer);
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createDummyDirectory() {
        new File(filePath.concat("\\resources")).mkdir();
    }
}
