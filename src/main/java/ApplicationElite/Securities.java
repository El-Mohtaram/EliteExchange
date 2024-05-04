package ApplicationElite;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
public class Securities {
    Account account=new Account();
   // private final String csvFile = "C:\Users\ram tech\Desktop\Elite\EliteExchange\src\main\java\data\Market.csv";
 protected static String[] DatakList;
public void Add(String Name,int number,float price,String dataPath,ArrayList <String> company,ArrayList <Integer> NumberOfSecurities,ArrayList <Float> Price)
{ 
    String updatedAmount="";
    try {
         if(company.contains(Name)) {
            for(int i=0;i<company.size();i++)
            {
                if(company.get(i).equals(Name))
                {
                NumberOfSecurities.set(i,NumberOfSecurities.get(i)+number);
                updatedAmount=""+NumberOfSecurities.get(i);
                System.out.println(updatedAmount);
                break;
                }
            }
                    String dataOverwrite="";
                    String  oldContent=""; 
                try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
                    String line;
                    br.readLine();
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");
                        if (Name.equals(values[0])) {
                            System.out.println("found");
                             oldContent = line;
                             System.out.println(line);
                              dataOverwrite= values[0]+","+updatedAmount+","+values[2];
                              System.out.println(dataOverwrite);
                         
                        }
                    }
                }
                        
                 catch (IOException e) {
                    e.printStackTrace();
                }  
                        try {
                            List<String> fileContent = Files.readAllLines(Paths.get(dataPath));
                            
                            for (int i = 0; i < fileContent.size(); i++) {
                                if (fileContent.get(i).equals(oldContent)) {
                                    fileContent.set(i, dataOverwrite);
                                    break; // Assuming you want to replace the first occurrence only
                                }
                            }
                            
                            Files.write(Paths.get(dataPath), fileContent);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    ////////////////////////////
        else{
            company.add(Name);
            NumberOfSecurities.add(number);
            Price.add(price);
        FileWriter fileWriter = new FileWriter(dataPath,true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(Name + "," + number+","+price);
        printWriter.close();
        }
        
    }
    catch(IOException e){
        e.printStackTrace();
    }
}
    public void RestoreData(String dataPath,ArrayList <String> company,ArrayList <Integer> NumberOfSecurities,ArrayList <Float> Prices,ObservableList<DataShow> stockData)
    {
        company.clear();
        Prices.clear();
        NumberOfSecurities.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                         company.add(values[0]);
                         NumberOfSecurities.add(Integer.parseInt(values[1]));
                         Prices.add(Float.parseFloat(values[2])); 
                                  
                    
                }
                stockData.clear();
                for (int i = 0; i < company.size(); i++) {
                    stockData.add(new DataShow(company.get(i),NumberOfSecurities.get(i),Prices.get(i)));
                }
                System.out.println(stockData.size());
                System.out.println(company.size());
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
   
    public void Delete(String Name ,String dataPath,ArrayList <String> company,ArrayList <Integer> NumberOfSecurities,ArrayList <Float> Prices)
{
    List<String> lines = new ArrayList<>();
    
int index=0;
try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
    String line=br.readLine();
    lines.add(line);
    while ((line = br.readLine()) != null) {
        String[] values = line.split(",");
        if (!Name.equals(values[0])) 
            lines.add(line);
            else{
                company.remove(index);
                NumberOfSecurities.remove(index);
                Prices.remove(index);
            }
            index++;
        }
      
       
    } 
 catch (IOException e) {
    e.printStackTrace();
}
      
        try {
         
            FileWriter writer = new FileWriter(dataPath);
            for (String updatedLine : lines) {
                writer.write(updatedLine + "\n");
            }
            writer.close();
            
                
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void RestoreData2(String dataPath,ArrayList <String> company,ArrayList <Integer> NumberOfSecurities,ArrayList<Float>Prices,ObservableList<DataShow> stockData)
    {

        try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                int j=0;
                    for(int i=0;i<values.length;i++)
                    {
                         company.set(j,values[0]);
                         NumberOfSecurities.set(j,Integer.parseInt(values[1]));
                         Prices.set(j,Float.parseFloat(values[2]));
                         j++;
                    }
                    
                }
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        stockData.clear();
        for (int i = 0; i < company.size(); i++) {
            stockData.set(i,new DataShow(company.get(i),NumberOfSecurities.get(i),Prices.get(i)));
        }

    }
}


    
