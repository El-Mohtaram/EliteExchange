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
    Account account = new Account();
    Admin admin = new Admin();
    static public String history = "";
    // private final String csvFile = "C:\Users\ram tech\Desktop\Elite\EliteExchange\src\main\java\data\Market.csv";
    protected static String[] DatakList;

    public void Add(String Name, int number, float price, String dataPath, HashMap<String, Float> securities, ArrayList<Integer> NumberOfSecurities,ArrayList<Float> yeilds,float yeild) {
        String updatedAmount = "";
        try {
            if (securities.containsKey(Name)) {
                int index = 0;
                for (String key : securities.keySet()) {
                    if (key.equals(Name)) {
                        NumberOfSecurities.set(index, NumberOfSecurities.get(index) + number);
                        updatedAmount = "" + NumberOfSecurities.get(index);
                        System.out.println(updatedAmount);
                        break;
                    }
                    index++;
                }
                String dataOverwrite = "";
                String oldContent = "";
                try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
                    String line;
                    br.readLine();
                    while ((line = br.readLine()) != null) {
                        String[] values = line.split(",");
                        if (Name.equals(values[0])) {
                            System.out.println("found");
                            oldContent = line;
                            System.out.println(line);
                        if(yeild==0)    dataOverwrite = values[0] + "," + updatedAmount + "," + values[2];
                        else dataOverwrite = values[0] + "," + updatedAmount + "," + values[2]+","+values[3];
                            System.out.println(dataOverwrite);

                        }
                    }
                } catch (IOException e) {
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
            else {
                securities.put(Name, price);
                NumberOfSecurities.add(number);
                FileWriter fileWriter = new FileWriter(dataPath, true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                if(yeild==0)
                printWriter.println(Name + "," + number + "," + price);
                else {
                    yeilds.add(yeild);
                    printWriter.println(Name + "," + number + "," + price + "," + yeild);
                }
                printWriter.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void RestoreData(String dataPath, HashMap<String, Float> securities, ArrayList<Integer> NumberOfSecurities, ObservableList<DataShow> stockData,ArrayList<Float>yeilds,int state) {
        securities.clear();
        NumberOfSecurities.clear();
        yeilds.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                securities.put(values[0], Float.parseFloat(values[2]));
                NumberOfSecurities.add(Integer.parseInt(values[1]));
if(state!=0) {yeilds.add(Float.parseFloat(values[3]));
//                System.out.println(yeilds.get(0));
}

            }
            stockData.clear();
            int i = 0;
            for (String key : securities.keySet()) {
                if(NumberOfSecurities.get(i)>0) {
                    if (state == 0) stockData.add(new DataShow(key, NumberOfSecurities.get(i), securities.get(key)));
                    else
                        stockData.add(new DataShow(key, yeilds.get(i), securities.get(key), NumberOfSecurities.get(i)));
                }
                i++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void Delete(String Name, String dataPath, HashMap<String, Float> securities, ArrayList<Integer> NumberOfSecurities,ArrayList<Float>yeilds,int state) {
        List<String> lines = new ArrayList<>();

        int index = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
            String line = br.readLine();
            lines.add(line);
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (!Name.equals(values[0]))
                    lines.add(line);
                else {
                    lines.add(values[0]+",0"+","+values[2]);
                    NumberOfSecurities.set(index,0);
if(state!=0) yeilds.remove(index);
                }
                index++;
            }


        } catch (IOException e) {
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
        ////////////////////////////////

    }

    public void refreshUserSecurities(String dataPath, HashMap<String, Integer> securities, String username) {
        securities.clear();

        try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (username.equals(values[0])) {
                    for (int i = 1; i < values.length; i++) {
                        String[] values2 = values[i].split(">");
                        securities.put(values2[1], Integer.parseInt(values2[0]));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void buyOrSell(String Name, int number, String dataPath, HashMap<String, Integer> securities,int state) {
        String updatedAmount = "";
        String dataOverwrite = "";
        String oldContent = "";

        if (securities.containsKey(Name)) {
            if(state==0)
            securities.replace(Name, securities.get(Name) + number);

            else
                securities.replace(Name,securities.get(Name) - number);
            updatedAmount = "" + securities.get(Name) + ">" + Name;
            try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if (account.getUsername().equals(values[0])) {
                        oldContent = line;
                        dataOverwrite = values[0];
                        for (int i = 1; i < values.length; i++) {
                            String[] values2 = values[i].split(">");
                            if (!values2[1].equals(Name))
                                dataOverwrite = dataOverwrite + "," + values2[0] + ">" + values2[1];
                            else if(securities.get(Name) ==0);
                              else  dataOverwrite = dataOverwrite + "," + updatedAmount;

                        }
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                List<String> fileContent = Files.readAllLines(Paths.get(dataPath));

                for (int i = 0; i < fileContent.size(); i++) {
                    if (fileContent.get(i).equals(oldContent)) {
                        fileContent.set(i, dataOverwrite);
                        break;
                    }
                }

                Files.write(Paths.get(dataPath), fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        ////////////////////////////
        else if(state==0) {
            try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
                String line;
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if (account.getUsername().equals(values[0])) {
                        oldContent = line;
                        dataOverwrite = values[0];
                        for (int i = 1; i < values.length; i++) {
                            dataOverwrite = dataOverwrite + "," + values[i];
                        }
                        dataOverwrite = dataOverwrite + "," + number + ">" + Name;
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                List<String> fileContent = Files.readAllLines(Paths.get(dataPath));

                for (int i = 0; i < fileContent.size(); i++) {
                    if (fileContent.get(i).equals(oldContent)) {
                        fileContent.set(i, dataOverwrite);
                        break;
                    }
                }

                Files.write(Paths.get(dataPath), fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void updateAmountInMarket(String dataPath, int amount, String Name, ArrayList<Integer> NumberOfSecurities,int state,boolean bond) {
        String oldContent = "";
        String dataOverwrite = "";
        try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (Name.equals(values[0])) {
                    oldContent = line;
                    int newAmount=0;
                    if(state==0)
                     newAmount = Integer.parseInt(values[1]) - amount;
                    else newAmount = Integer.parseInt(values[1]) + amount;
                if(bond)    dataOverwrite = values[0] + "," + newAmount + "," + values[2]+","+values[3];
                else dataOverwrite = values[0] + "," + newAmount + "," + values[2];
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            List<String> fileContent = Files.readAllLines(Paths.get(dataPath));

            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(oldContent)) {
                    fileContent.set(i, dataOverwrite);
                    break;
                }
            }

            Files.write(Paths.get(dataPath), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean buyCheck(String dataPath, int amount, String Name) {
        try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (Name.equals(values[0])) {
                    float totalPrice = Float.parseFloat(values[2]) * amount;
                    if (amount > Integer.parseInt(values[1]) || account.getBalance() < totalPrice)
                        return false;
                    else account.setBalance(account.getBalance() - totalPrice);
                    admin.transaction(account.getUsername() + " " + amount + " " + Name + " " + totalPrice, 2);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
    public boolean sellCheck(String dataPath, int amount, String Name) {
        try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (account.getUsername().equals(values[0])) {
                    for (int i = 1; i < values.length; i++) {
                        String values2[] = values[i].split(">");

                        if (values2[1].equals(Name) && amount <= Integer.parseInt(values2[0]))
                        {
                            admin.transaction(account.getUsername() + " " + amount + " " + Name + " " + amount*Stock.getPrice(Name), 3);
                            account.setBalance(account.getBalance()+amount*Stock.getPrice(Name));
                            return true;
                        }
                    }
                }
            }
            } catch(IOException e){
                e.printStackTrace();
            }
        return false;
        }


    public void refreshUserSecurities(ObservableList<DataShow> securitiesList,String dataPath) {
        securitiesList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(dataPath))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (account.username1.equals(values[0])) {
                    for (int i = 1; i < values.length; i++) {
                        String values2[] = values[i].split(">");
                        System.out.println(values[i]);
                        securitiesList.add( new DataShow(values2[1], Integer.parseInt(values2[0]),Integer.parseInt(values2[0])*Stock.getPrice(values2[1])));
                    }
                    break;
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    }






    
