package printmon.model;

public class Printer {

    private int id;
    private String name;
    private String model;
    private String location;
    private String url;
    private String status;
    private String lastUpdateStatus;
    private int paperCounter;
    private String lastUpdatePaperCounter;

    public Printer() {

    }

    public Printer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStatus() {
        return status;
    }

    public String getLastUpdateStatus() {
        return lastUpdateStatus;
    }

    public void setLastUpdateStatus(String lastUpdateStatus) {
        this.lastUpdateStatus = lastUpdateStatus;
    }

    public String getLastUpdatePaperCounter() {
        return lastUpdatePaperCounter;
    }

    public void setLastUpdatePaperCounter(String lastUpdatePaperCounter) {
        this.lastUpdatePaperCounter = lastUpdatePaperCounter;
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

    @Override
    public String toString() {
        return this.name;
    }
}
