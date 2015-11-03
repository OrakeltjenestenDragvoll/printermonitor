package printmon.service;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import printmon.model.Printer;
import printmon.repository.PrinterRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PrinterListReader {

    @Autowired
    PrinterRepository printerRepository;

    public List<Printer> readPrinterList() {
        Gson gson = new Gson();
        ArrayList<Printer> printers;
        Printer[] printerArray;

        try {
            JsonReader jsonReader = new JsonReader(new FileReader("C:\\Users\\Lars Erik\\workspace\\printmon\\src\\main\\resources\\printer.json"));
            printerArray = gson.fromJson(jsonReader, Printer[].class);
            printers = new ArrayList(Arrays.asList(printerArray));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return printers;
    }

    public void writePrinterList() {
        Gson gson = new Gson();
    }

    public Printer getPrinter() {
        Gson gson = new Gson();
        Printer printer = null;

        try {
            JsonReader jsonReader = new JsonReader(new FileReader("C:\\Users\\Lars Erik\\workspace\\printmon\\src\\main\\resources\\printer.json"));
            printer = gson.fromJson(jsonReader, Printer.class);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return printer;
    }

    public void writePrinter() {
        Gson gson = new Gson();
        Printer printer = new Printer(1, "testprinter");
        printer.setInterfaceLanguage("en");
        printer.setModel("richo");
        printer.setStatus("Alert");
        printer.setPaperCounter(10000);
        printerRepository.save(printer);

        try {
            JsonWriter writer = new JsonWriter(new FileWriter("C:\\Users\\Lars Erik\\workspace\\printmon\\src\\main\\resources\\printer.json"));
            gson.toJson(printerRepository.getAll(), ArrayList.class, writer);
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
