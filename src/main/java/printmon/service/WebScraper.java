package printmon.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import printmon.model.Printer;
import printmon.repository.PrinterRepository;

@Service
public class WebScraper {

    @Autowired
    PrinterRepository printerRepository;

    private int connectionTimeout = 5000;
    private boolean debug = true;


    public String extractPrinterStatus(int printerId) {
        Printer printer = printerRepository.findById(printerId);
        if(printer == null)
            return "No such printer";
        switch (printer.getModel()) {
            case "Aficio MP 5001":
            case "Aficio MP 7001":
            case "Aficio SP 8200DN":
                return statusExtractorV1(printer);
            default:
                return "Printer model not supported: " + printer.getModel();
        }
    }

    public int extractCounterStatus(int printerId) {
        Printer printer = printerRepository.findById(printerId);
        if(printer == null)
            return -1;
        switch (printer.getModel()) {
            case "Aficio MP 5001":
            case "Aficio MP 7001":
            case "Aficio SP 8200DN":
                return paperExtractorV1(printer);
            default:
                return -1;
        }
    }

    /**
     * Works on MP 5001, MP 7001 and SP 8200DN
     * @param printer
     * @return
     */
    private String statusExtractorV1(Printer printer) {
        Document document;
        if(debug)
            document = getDocument(printer.getUrl() + "Filer_for_main/topPage.htm");
        else
            document = getDocument(printer.getUrl());

        if("en".equals(printer.getInterfaceLanguage())) {
            String text = document.text();
            String preProcessed = text.substring(text.indexOf("Host Name"));
            String status = preProcessed.substring(preProcessed.indexOf("Printer")+8,preProcessed.indexOf("Copier")-1);
            return status;
        }
        else if("no".equals(printer.getInterfaceLanguage())){
            String text = document.text();
            String status;
            String preProcessed = text.substring(text.indexOf("Vertsnavn"));
            if(preProcessed.indexOf("Kopimaskin") > 0)
                status = preProcessed.substring(preProcessed.indexOf("Skriver")+8, preProcessed.indexOf("Kopimaskin")-1);
            else
                status = preProcessed.substring(preProcessed.indexOf("Skriver")+8, preProcessed.indexOf("Skriver:")-1);
            return status;
        }
        else
            return "Language not supported: " + printer.getInterfaceLanguage();
    }

    /**
     * Works on MP 5001, MP 7001 and SP 8200DN
     * @param printer
     * @return
     */
    private int paperExtractorV1(Printer printer) {
        Document document;
        if(debug)
            document = getDocument(printer.getUrl() + "Filer_for_counter/topPage.htm") ;
        else
            document = getDocument(printer.getUrl());

        if("en".equals(printer.getInterfaceLanguage())) {
            String text = document.text();
            String status = text.substring(text.indexOf("Total :")+8,text.indexOf("Copier")-1);
            try {
                int count  = Integer.parseInt(status);
                return count;
            }catch (NumberFormatException exception) {
                exception.printStackTrace();
                return -1;
            }
        }
        else if("no".equals(printer.getInterfaceLanguage())){
            String text = document.text();
            String status;
            if(text.indexOf("Kopimaskin") > 0)
                status = text.substring(text.indexOf("Totalt :")+9, text.indexOf("Kopimaskin")-1);
            else
                status = text.substring(text.indexOf("Totalt :")+9, text.indexOf("Skriver")-1);
            try {
                int count  = Integer.parseInt(status);
                return count;
            }catch (NumberFormatException exception) {
                exception.printStackTrace();
                return -1;
            }
        }
        else
            return -1;
    }

    public Document getDocument(String url) {
        try {
            Document doc = Jsoup.connect(url).timeout(connectionTimeout).get();
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}