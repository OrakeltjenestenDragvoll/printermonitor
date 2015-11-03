package printmon.model;

public class Printer {

    private long id;
    private String name;
    private String model;
    private String location;
    private String interfaceLanguage;
    private String url;
    private String status;
    private int paperCounter;

    public Printer(long id, String name) {
        this.id = id;
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getInterfaceLanguage() {
        return interfaceLanguage;
    }

    public void setInterfaceLanguage(String interfaceLanguage) {
        this.interfaceLanguage = interfaceLanguage;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPaperCounter() {
        return paperCounter;
    }

    public void setPaperCounter(int paperCounter) {
        this.paperCounter = paperCounter;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Printer)
            return false;
        Printer pObj = (Printer) obj;
        if(pObj.getId() == this.getId())
            return true;
        else
            return false;
    }
}
