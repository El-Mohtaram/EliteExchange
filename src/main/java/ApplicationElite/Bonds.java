package ApplicationElite;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Bonds extends Securities {
    private static HashMap<String,Float>bondList=new LinkedHashMap<>();
    private static ArrayList<Integer> Expire =new ArrayList<>();
    private String csvFile ="src/main/java/data/Bonds.csv";
    private String csvFile2 ="src/main/java/data/UserBonds.csv";
    //private String csvFile2 ="src/main/java/data/stock.csv";
    private static HashMap<String,Integer> userBonds=new LinkedHashMap<>();
    static private ObservableList<DataShow> BondData = FXCollections.observableArrayList();
    static private ObservableList<DataShow> userBondList = FXCollections.observableArrayList();

    public void addBonds(String company,float price,int number,float yeild,int exp)
    {
        Add(company,number,price,csvFile,bondList,yeild,exp);

    }
    public void deleteBonds(String company)
    {
        Delete(company,csvFile,bondList,1);
    }
public void RefreshBondList(){
        RestoreData(csvFile,bondList,BondData,1);
}

    public static ObservableList<DataShow> getBondData() {
        return BondData;
    }
    public boolean buyBond(String company,int number,int exp,float yield)
    {
        refreshUserSecuritiesMap(csvFile2,userBonds,account.getUsername());
        if(buyCheck(csvFile,number,company)) {
            buyOrSell(company, number, csvFile2, userBonds, 0,true,exp,yield);
            updateAmountInMarket(csvFile, number, company, 0,true);
            return true;
        }
        else return  false;
    }
    public boolean SellBond(int amount,String company,int exp,float yield)
    {
        refreshUserSecuritiesMap(csvFile2,userBonds,account.getUsername());
        if(sellCheck(csvFile2,amount,company))
        {
            buyOrSell(company,amount,csvFile2,userBonds,1,true,exp,yield);
            updateAmountInMarket(csvFile,amount,company,1,true);
            return true;
        }
        else return  false;

    }
    public void refreshUserBondList(){refreshUserSecurities(userBondList,csvFile2,true);}
    public ObservableList<DataShow> returnUserList()
    {
        return userBondList;
    }
    public void returnYield()
    {
        ArrayList<String>lines=new ArrayList<>();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile2))) {
                String line = br.readLine();
                lines.add(line);
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    line = values[0];
                    for (int i = 1; i < values.length; i++) {
                        String[] values2 = values[i].split(">");
                        if (Integer.parseInt(values2[2]) > 0) {
                            line = line + "," + values2[0] + ">" + values2[1] + ">" + (Integer.parseInt(values2[2]) - 1) + ">" + values2[3];
                            account.updateBalance2(values[0], Float.parseFloat(values2[3])/100 *bondList.get(values2[1]) * Integer.parseInt(values2[0]));
                            System.out.println(values[0]);
                        }
                        else account.updateBalance2(values[0],bondList.get(values2[1]) * Integer.parseInt(values2[0]));
                    }
                    lines.add(line);
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile2));
                for (String updatedLine : lines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
                writer.flush();
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();

            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

}
