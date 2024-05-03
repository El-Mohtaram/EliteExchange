package ApplicationElite;

public class DataShow {
   private String company;
   // public int number;
  private float price;
private int number;
    public DataShow(String company,int number, float price)
    {
this.company=company;
this.price=price;
this.number=number;
    }
    public String getCompany() {
        return company;
    }
    public Float getPrice() {
        return price;
    }
    public int getNumber()
    {
        return number;
    }
    
}
