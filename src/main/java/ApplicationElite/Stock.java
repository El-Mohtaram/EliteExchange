package ApplicationElite;
import java.io.*;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

    public class Stock extends Securities  {
    private String csvFile ="src/main/java/data/Market.csv";
        private String csvFile2 ="src/main/java/data/stock.csv";
    private static HashMap<String,Float> stocks=new LinkedHashMap<>();
        private static HashMap<String,Integer> userStocks=new LinkedHashMap<>();
    private  static ArrayList <Integer> NumberOfStocks=new ArrayList<>();
    public static String[] stockList=DatakList;
     static private ObservableList<DataShow> stockData = FXCollections.observableArrayList();

 public void addStock(String name,int number,float f)
 {Add(name,number,f,csvFile,stocks,NumberOfStocks);}
public void RestoreData()
{
    RestoreData(csvFile, stocks, NumberOfStocks,stockData);
}
public void DeleteStock(String name)
{
    Delete(name,csvFile, stocks, NumberOfStocks);
}

public ObservableList<DataShow> returnList()
{
    System.out.println(stockData.size());
    return stockData;
}
public void UpdatePrices(){
     for(String key: stocks.keySet())
     {
         float minPrice = stocks.get(key) - 2.0f;
        float maxPrice = stocks.get(key) + 2.0f;

         // Generate a random price within the specified range
         Random random = new Random();
         stocks.replace(key,(minPrice + (maxPrice - minPrice) * random.nextFloat()));

     }
    try {
        String filePath = csvFile; // Replace with your actual file path
        int targetColumn = 2; // Example column number
        ArrayList<String> newValues = new ArrayList<>();

        for(String key: stocks.keySet())
        {

            newValues.add(""+stocks.get(key));

        }

        BufferedReader reader = new BufferedReader(new FileReader(csvFile));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            lines.add(line);

        }
        reader.close();

        // Update the specified column with different values for each row
        for (int i = 1; i < lines.size(); i++) {
            String[] columns = lines.get(i).split(","); // Assuming CSV columns are comma-separated
            columns[targetColumn] = newValues.get(i-1);
            lines.set(i, String.join(",", columns));

        }

        BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
        for (String updatedLine : lines) {
            writer.write(updatedLine);
            writer.newLine();

        }
        writer.flush();
        writer.close();

    } catch (IOException e) {
        e.printStackTrace();

}
}
public boolean BuyStock(int amount,String company)
{
    refreshUserSecurities(csvFile2,userStocks,account.getUsername());
    if(buyCheck(csvFile,amount,company))
    {
        buy(company,amount,csvFile2,userStocks);
        removeAmountInMarket(csvFile,amount,company,NumberOfStocks);
        return true;
    }
    else return  false;

}


}
