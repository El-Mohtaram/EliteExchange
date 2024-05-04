package ApplicationElite;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Account {
    private static ArrayList<String>username=new ArrayList<>();
    private final String csvFile = "src/main/java/data/AccountData.csv";
    private final String csvFile2 = "src/main/java/data/stock.csv";
    private String password;
    public static String username1;
    private String ConfirmPassword;
    static private String userOrAdmin;
    static private String adminInOrOut;
    static public boolean BannedOrNot;
    static private  float balance=0.0f;
 
   
   

    public void setUserName(String username)
    {
       this.username1=username;
    }
    public void setPassword(String password)
    {
        this.password=password;
        
       
    }
    public void setCheckPassword(String ConfirmPassword)
    {
        this.ConfirmPassword=ConfirmPassword;
       
    }
    public boolean CheckMatchPassword()
    {
        if(username1.contains(" ")||password.contains(" ")||ConfirmPassword.contains(" "))
            return false;
        if(password.length()==0||username1.length()==0) return false;
        if(this.password.equals(this.ConfirmPassword))
        {

            if(!username.contains(username1)  && password.length()>=3)
            {
            username.add(username1);
          
            try {
                FileWriter fileWriter = new FileWriter(csvFile,true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.println(username1 + "," + password+",user"+","+balance);
                printWriter.close();
                
            }
            catch(IOException e){
                e.printStackTrace();
            }
    
            return true;
            }
            else return false;
        } 
        
        else return false;
    }
    public boolean CheckLoginData()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
          line= br.readLine();
          String[] values = line.split(",");
          adminInOrOut=values[3];
            while ((line = br.readLine()) != null) {
                 values = line.split(",");

                if((values.length>3)&&values[3].equals("banned")){
                    BannedOrNot=true;
                }
                else BannedOrNot = false;

                if (username1.equals(values[0]) && password.equals(values[1])) {
                    userOrAdmin=values[2];
                    balance=Float.parseFloat(values[3]);
                    return true;
                }
                
                
            }

            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public void ImportUserData()
{ 
    try {
        FileWriter fileWriter = new FileWriter(csvFile2,true);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.println(username1 + "," );
        printWriter.close();
    }
    catch(IOException e){
        e.printStackTrace();
    }
}
    public void RestoreData()
    {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                username.add(values[0]);    
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String SignUpMessages()
    {
        if(username1.length()==0||password.length()==0||ConfirmPassword.length()==0)
        return "Please fill all data";
        else if(username1.contains(" ")) return "invalid username";
        else if(password.contains(" ")) return "invalid Password";
        else if(password.length()<3)
        return "Password should be at least 3 characters";
        else if(!password.equals(ConfirmPassword))
        return "Password mismatch please try again!";
        else if(username.contains(username1)) return "This username is already taken";
        else
        return "";

    }
    public String LoginMessages()
    {
        if(username1.length()==0||password.length()==0)
        return "Please fill all data";
      else return "Username or password is incorrect, please try again";

    }
    public String userOrAdmin()
    {
        return userOrAdmin;
    }
public String admin_log_in_out()
{
return adminInOrOut;
}

public void adminSwitch()
{
    String dataOverwrite="";
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
    String line= br.readLine();
   
        String[] values = line.split(",");
        dataOverwrite=values[0];
        if(values[3].equals("yes"))
        values[3]="no";
        else values[3]="yes";
        for(int i=1;i<4;i++)
dataOverwrite=dataOverwrite+","+values[i];
       
    }
        
 catch (IOException e) {
    e.printStackTrace();
}
        
           try {
            List<String> fileContent = Files.readAllLines(Paths.get("src/main/java/data/AccountData.csv"));
                    fileContent.set(0, dataOverwrite);
                    Files.write(Paths.get("src/main/java/data/AccountData.csv"), fileContent);
                }   
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public float getBalance()
    {
        return balance;
    }
    public void setBalance(float balance)
    {
        Account.balance=balance;
    }
    public void refreshBalance() throws FileNotFoundException, IOException
    {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (username1.equals(values[0])) 
                {
                    balance=Float.parseFloat(values[3]);
                    break;
                }
    }
}

   

}
}






  

