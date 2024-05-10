package ApplicationElite;

public class DataShow {
    private String requests;
    private String company;
    private float price;
    private int number;
    private String history;

    public DataShow(String requests) {
        this.requests = requests;
    }

    public DataShow(String history, float x) {
        this.history = history;
    }

    public DataShow(String company, int number, float price) {
        this.company = company;
        this.price = price;
        this.number = number;
    }

    public String getCompany() {
        return company;
    }

    public Float getPrice() {
        return price;
    }

    public int getNumber() {
        return number;
    }

    public String getRequests() {
        return requests;
    }

    public String getHistory() {
        return history;
    }

}
