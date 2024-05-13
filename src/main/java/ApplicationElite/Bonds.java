package ApplicationElite;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Bonds extends Securities {
    private static HashMap<String,Float>bondList=new LinkedHashMap<>();
    private static ArrayList<Float> yields=new ArrayList<>();
    private static ArrayList<Integer> numberOfBonds=new ArrayList<>();
    private String csvFile ="src/main/java/data/Bonds.csv";
    private String csvFile2 ="src/main/java/data/UserBonds.csv";
    //private String csvFile2 ="src/main/java/data/stock.csv";
    private static HashMap<String,Integer> userBonds=new LinkedHashMap<>();
    static private ObservableList<DataShow> BondData = FXCollections.observableArrayList();
    static private ObservableList<DataShow> userBondList = FXCollections.observableArrayList();

    public void addBonds(String company,float price,int number,float yeild)
    {

        Add(company,number,price,csvFile,bondList,numberOfBonds,yields,yeild);
    }
    public void deleteBonds(String company)
    {
        Delete(company,csvFile,bondList,numberOfBonds,yields,1);
    }
public void RefreshBondList(){
        RestoreData(csvFile,bondList,numberOfBonds,BondData,yields,1);
}

    public static ObservableList<DataShow> getBondData() {
        return BondData;
    }
    public boolean buyBond(String company,int number)
    {
        refreshUserSecurities(csvFile2,userBonds,account.getUsername());
        if(buyCheck(csvFile,number,company)) {
            buyOrSell(company, number, csvFile2, userBonds, 0);
            updateAmountInMarket(csvFile, number, company, numberOfBonds, 0,true);
            return true;
        }
        else return  false;
    }
    public boolean SellBond(int amount,String company)
    {
        refreshUserSecurities(csvFile2,userBonds,account.getUsername());
        if(sellCheck(csvFile2,amount,company))
        {
            buyOrSell(company,amount,csvFile2,userBonds,1);
            updateAmountInMarket(csvFile,amount,company,numberOfBonds,1,true);
            return true;
        }
        else return  false;

    }
    public void refreshUserBondList(){refreshUserSecurities(userBondList,csvFile2);}
    public ObservableList<DataShow> returnUserList()
    {
        return userBondList;
    }

}
