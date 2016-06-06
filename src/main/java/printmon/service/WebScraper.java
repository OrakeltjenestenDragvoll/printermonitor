package printmon.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;
import printmon.model.Printer;
import printmon.repository.PrinterRepository;

import java.net.SocketTimeoutException;

@Service
public class WebScraper {

    @Autowired
    PrinterRepository printerRepository;

    static Logger log = Logger.getLogger(WebScraper.class.getName());
    private int connectionTimeout = 15000;
    public static boolean debug;


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
            document = getDocument(printer.getUrl() + "/web/guest/en/websys/webArch/topPage.cgi");

        if("en".equals(printer.getInterfaceLanguage())) {
            String text = document.text();
            String status;
            String preProcessed = text.substring(text.indexOf("Host Name"));
            if("Aficio SP 8200DN".equals(printer.getModel()))
                status = preProcessed.substring(preProcessed.indexOf("Printer")+8,preProcessed.indexOf("Toner")-1);
            else
                status = preProcessed.substring(preProcessed.indexOf("Printer")+8,preProcessed.indexOf("Copier")-1);
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
            document = getDocument(printer.getUrl() + "/web/guest/en/websys/status/getUnificationCounter.cgi");

        if("en".equals(printer.getInterfaceLanguage())) {
            String text = document.text();
            String status;
            if("Aficio SP 8200DN".equals(printer.getModel()))
                status = text.substring(text.indexOf("Total :")+8,text.indexOf("Printer")-1);
            else
                status = text.substring(text.indexOf("Total :")+8,text.indexOf("Copier")-1);

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
        } catch (SocketTimeoutException e) {
            log.info("Connection to " + url + " timed out");
            return null;
        } catch (Exception e) {
            log.info("Connection to " + url + " failed");
            return null;
        }
    }
}