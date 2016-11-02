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

    private String filePath = new File("/webapps/printmon/resources/printer.json").getPath();

    public List<Printer> readPrinterList() {
        Gson gson = new Gson();
        ArrayList<Printer> printers;
        Printer[] printerArray;

        try {
            JsonReader jsonReader = new JsonReader(new FileReader(filePath));
            printerArray = gson.fromJson(jsonReader, Printer[].class);
            printers = new ArrayList(Arrays.asList(printerArray));
        }
        catch (FileNotFoundException e) {
            return null;
        }
        return printers;
    }

    public boolean writePrinterList() {
        throw new NotImplementedException();
    }
}
