package ApplicationElite;

public class DataShow {
    private String requests;
   private String company;
  private float price;
private int number;
private String history;
private float yield;
private String date;
private String company2;
 private  float max;
    private float min;
    private float start;
    private float end;
    private int exp;
    private String col4Data;
    public  DataShow(String date,float start,float min, float max, float end){
        this.date=date;
        this.start=start;
        this.min=min;
        this.max=max;
        this.end=end;
    }
public DataShow (String date,int a)
{
    this.date=date;
}
public DataShow(String requests)
{
this.requests=requests;
}
public DataShow(String history,float x)
{
this.history=history;
}
    public DataShow(String company,int number, float price)
    {
this.company=company;
this.price=price;
this.number=number;
    }
    public DataShow(String company,int number, float price,int exp)
    {
        this.company=company;
        this.price=price;
        this.number=number;
        this.exp=exp;
    }
    public DataShow(String company, Float yield, Float price,int number,int exp,int x) {
    this.company=company;
    this.yield=yield;
    this.price=price;
    this.number=number;
    this.exp=exp;
    }
    public DataShow(String company2,boolean x) {
        this.company2=company2;
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
    public String getRequests()
    {
        return requests;
    }
    public String getHistory()
    {
        return history;
    }
    public float getYield(){return yield;}
    public String getDate() {
        return date;
    }

    public float getEnd() {
        return end;
    }

    public float getMax() {
        return max;
    }

    public float getMin() {
        return min;
    }

    public float getStart() {
        return start;
    }
    public String getCompany2() {
        return company2;
    }
    public int getExp(){
        return exp;
    }
    @Override
    public String toString() {
        return company + "  |   " + price +" $";
    }

    public String getCol4Data() {
        return col4Data;
    }
}
