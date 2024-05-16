package ApplicationElite;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

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
    public boolean buyBond(String company,int number,int exp)
    {
        refreshUserSecuritiesMap(csvFile2,userBonds,account.getUsername());
        if(buyCheck(csvFile,number,company)) {
            buyOrSell(company, number, csvFile2, userBonds, 0,true,exp);
            updateAmountInMarket(csvFile, number, company, 0,true);
            return true;
        }
        else return  false;
    }
    public boolean SellBond(int amount,String company,int exp)
    {
        refreshUserSecuritiesMap(csvFile2,userBonds,account.getUsername());
        if(sellCheck(csvFile2,amount,company))
        {
            buyOrSell(company,amount,csvFile2,userBonds,1,true,exp);
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

}
