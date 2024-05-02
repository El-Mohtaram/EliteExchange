package ApplicationElite;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
public class Securities {
    Account account=new Account();
    private float balance;
    private final String csvFile = "src/main/java/data/stock.csv";
  private static ArrayList <String> StockName=new ArrayList<>();
 private  static ArrayList <Integer> NumberOfStocks=new ArrayList<>();
 public static String[] stockList1;



public void AddStock(String Name,int number)
{
   
    String dataOverwrite="";
    String  oldContent="";
StockName.add(Name);
NumberOfStocks.add(number);
System.out.println(StockName.get(0));
    

try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
    String line;
    br.readLine();
    while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        if (account.username1.equals(values[0])) {
            System.out.println("found");
             oldContent = line;
             dataOverwrite=values[0]+","+values[1];
         for(int i=0; i<StockName.size();i++)
         {
              dataOverwrite= dataOverwrite+","+NumberOfStocks.get(i)+"|"+StockName.get(i);
         }
        }
    }
}
        
 catch (IOException e) {
    e.printStackTrace();
}
    
    
    
        
        String newContent = dataOverwrite;
        
        try {
            List<String> fileContent = Files.readAllLines(Paths.get("src/main/java/data/stock.csv"));
            
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(oldContent)) {
                    fileContent.set(i, newContent);
                    break; // Assuming you want to replace the first occurrence only
                }
            }
            
            Files.write(Paths.get("src/main/java/data/stock.csv"), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void RestoreData()
    {

        
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/data/stock.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (account.username1.equals(values[0])) {
                    stockList1=values;
                    int j=0;
                    for(int i=2;i<values.length;i++)
                    {
                         String[] separatedValues=values[i].split("\\|");
                         StockName.add(j,separatedValues[1]);
                         NumberOfStocks.add(j,Integer.parseInt(separatedValues[0]));
                         j++;
                    }
                   
                    break;
                    
                }
               
              
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
   
    public void DeleteStock(String Name)
{
   
    String dataOverwrite="";
    String  oldContent="";

    
int index=0;
try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
    String line;
    br.readLine();
    while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
       
        if (account.username1.equals(values[0])) {
            dataOverwrite=values[0]+","+values[1];
            oldContent = line;
            for(int i=2;i<values.length;i++)
            {
                if(Name.equals(values[i]))
                {
                    System.out.println(StockName.get(i-2));
                    NumberOfStocks.remove(i-2);
                    StockName.remove(i-2);
                    System.out.println("done");
                }
                
               else dataOverwrite=dataOverwrite+","+values[i];
            }
        }
        index++;
       
    }
   
   
}
        
 catch (IOException e) {
    e.printStackTrace();
}
System.out.println(dataOverwrite);
    
    
    
        
        String newContent = dataOverwrite;
        
        try {
            List<String> fileContent = Files.readAllLines(Paths.get("src/main/java/data/stock.csv"));
            
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(oldContent)) {
                    fileContent.set(i, newContent);
                    break; // Assuming you want to replace the first occurrence only
                }
            }
            
            Files.write(Paths.get("src/main/java/data/stock.csv"), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void RestoreData2()
    {

        
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/data/stock.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (account.username1.equals(values[0])) {
                    stockList1=values;
                    int j=0;
                    for(int i=2;i<values.length;i++)
                    {
                         String[] separatedValues=values[i].split("\\|");
                         StockName.set(j,separatedValues[1]);
                         NumberOfStocks.set(j,Integer.parseInt(separatedValues[0]));
                         j++;
                    }
                   
                    break;
                    
                }
               
              
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}


    
