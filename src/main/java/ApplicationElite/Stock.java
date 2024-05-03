package ApplicationElite;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

    public class Stock extends Securities  {
    private String csvFile ="src/main/java/data/Market.csv";
    private static ArrayList <String> StockName=new ArrayList<>();
    private  static ArrayList <Integer> NumberOfStocks=new ArrayList<>();
    private  static ArrayList <Float> Prices=new ArrayList<>();
    public static String[] stockList=DatakList;
     static private ObservableList<DataShow> stockData = FXCollections.observableArrayList();

 public void addStock(String name,int number,float f)
 {
    Add(name,number,f,csvFile,StockName,NumberOfStocks,Prices);
 }
public void RestoreData()
{
    RestoreData(csvFile, StockName, NumberOfStocks,Prices,stockData);
}
public void RefreshData()
{
    RestoreData2(csvFile, StockName, NumberOfStocks,Prices,stockData);
}
public void DeleteStock(String name)
{
    Delete(name,csvFile, StockName, NumberOfStocks,Prices);
}

public ObservableList<DataShow> returnList()
{
    System.out.println(stockData.size());
    return stockData;
}

}
