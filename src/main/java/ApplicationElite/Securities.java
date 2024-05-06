package ApplicationElite;

import java.util.ArrayList;
import java.util.HashMap;
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
public void Add(String Name, int number, float price, String dataPath, HashMap<String,Float> securities, ArrayList <Integer> NumberOfSecurities)
{ 
    String updatedAmount="";
    try {
         if(securities.containsKey(Name)) {
             int index = 0;
             for (String key :securities.keySet())
            {
                if (key.equals(Name))
                {
                NumberOfSecurities.set(index,NumberOfSecurities.get(index)+number);
                updatedAmount=""+NumberOfSecurities.get(index);
                System.out.println(updatedAmount);
                break;
                }
                index++;
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
            securities.put(Name,price);
            NumberOfSecurities.add(number);
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
    public void RestoreData(String dataPath,HashMap<String,Float> securities,ArrayList <Integer> NumberOfSecurities,ObservableList<DataShow> stockData)
    {
       securities.clear();
        NumberOfSecurities.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                         securities.put(values[0],Float.parseFloat(values[2]));
                         NumberOfSecurities.add(Integer.parseInt(values[1]));

                                  
                    
                }
                stockData.clear();
            int i=0;
            for (String key :securities.keySet()) {
                stockData.add(new DataShow(key, NumberOfSecurities.get(i), securities.get(key)));
                i++;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
   
    public void Delete(String Name ,String dataPath,HashMap<String,Float>securities,ArrayList <Integer> NumberOfSecurities)
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
                securities.remove(Name);
                NumberOfSecurities.remove(index);

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

    }



    
