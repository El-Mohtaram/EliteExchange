package ApplicationElite;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Account {
    private static ArrayList<String> username = new ArrayList<>();
    private final String csvFile = "src/main/java/data/AccountData.csv";
    private final String csvFile2 = "src/main/java/data/stock.csv";
    private String password;
    public static String username1;
    private String ConfirmPassword;
    static private String userOrAdmin;
    static private String adminInOrOut;
    static public boolean BannedOrNot;
    static private float balance = 0.0f;


    public void setUserName(String username) {
        this.username1 = username;
    }

    public void setPassword(String password) {
        this.password = password;


    }

    public void setCheckPassword(String ConfirmPassword) {
        this.ConfirmPassword = ConfirmPassword;

    }

    public boolean CheckMatchPassword() {
        if (username1.contains(" ") || password.contains(" ") || ConfirmPassword.contains(" "))
            return false;
        if (password.length() == 0 || username1.length() == 0) return false;
        if (this.password.equals(this.ConfirmPassword)) {

            if (!username.contains(username1) && password.length() >= 3) {
                username.add(username1);

                try {
                    FileWriter fileWriter = new FileWriter(csvFile, true);
                    PrintWriter printWriter = new PrintWriter(fileWriter);
                    printWriter.println(username1 + "," + password + ",user" + "," + balance);
                    FileWriter fileWrite = new FileWriter("src/main/java/data/TransactionHistory.csv", true);
                    PrintWriter printWrite = new PrintWriter(fileWrite);
                    printWrite.println(username1);
                    printWriter.close();
                    printWrite.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }


                return true;
            } else return false;
        } else return false;
    }

    public boolean CheckLoginData() {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            line = br.readLine();
            String[] values = line.split(",");
            adminInOrOut = values[3];
            while ((line = br.readLine()) != null) {
                values = line.split(",");

                if ((values.length > 3) && values[3].equals("banned")) {
                    BannedOrNot = true;
                } else BannedOrNot = false;

                if (username1.equals(values[0]) && password.equals(values[1])) {
                    userOrAdmin = values[2];
                    balance = Float.parseFloat(values[3]);
                    return true;
                }

            }

            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void ImportUserData() {
        try {
            FileWriter fileWriter = new FileWriter(csvFile2, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(username1 + ",");
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileWriter fileWriter = new FileWriter("src/main/java/data/UserBonds.csv", true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(username1);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void RestoreData() {
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

    public String SignUpMessages() {
        if (username1.length() == 0 || password.length() == 0 || ConfirmPassword.length() == 0)
            return "Please fill all data";
        else if (username1.contains(" ")) return "invalid username";
        else if (password.contains(" ")) return "invalid Password";
        else if (password.length() < 3)
            return "Password should be at least 3 characters";
        else if (!password.equals(ConfirmPassword))
            return "Password mismatch please try again!";
        else if (username.contains(username1)) return "This username is already taken";
        else
            return "";

    }

    public String LoginMessages() {
        if (username1.length() == 0 || password.length() == 0)
            return "Please fill all data";
        else return "Username or password is incorrect, please try again";

    }

    public String userOrAdmin() {
        return userOrAdmin;
    }

    public String admin_log_in_out() {
        return adminInOrOut;
    }

    public void adminSwitch() {
        String dataOverwrite = "";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = br.readLine();

            String[] values = line.split(",");
            dataOverwrite = values[0];
            if (values[3].equals("yes"))
                values[3] = "no";
            else values[3] = "yes";
            for (int i = 1; i < 4; i++)
                dataOverwrite = dataOverwrite + "," + values[i];

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            List<String> fileContent = Files.readAllLines(Paths.get("src/main/java/data/AccountData.csv"));
            fileContent.set(0, dataOverwrite);
            Files.write(Paths.get("src/main/java/data/AccountData.csv"), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        Account.balance = balance;
    }

    public void refreshBalance() throws FileNotFoundException, IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (username1.equals(values[0])) {
                    balance = Float.parseFloat(values[3]);
                    break;
                }
            }
        }


    }

    public String getUsername() {
        return username1;
    }

    public void updateBalance() {
        String oldContent = "";
        String dataOverwrite = "";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (username1.equals(values[0])) {
                    oldContent = line;
                    dataOverwrite = values[0] + "," + values[1] + "," + "user" + "," + getBalance();

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            List<String> fileContent = Files.readAllLines(Paths.get(csvFile));

            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(oldContent)) {
                    fileContent.set(i, dataOverwrite);
                    break;
                }
            }

            Files.write(Paths.get(csvFile), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateBalance2(String username, float amount) {
        String oldContent = "";
        String dataOverwrite = "";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (username.equals(values[0])) {
                    oldContent = line;
                    dataOverwrite = values[0] + "," + values[1] + "," + "user" + "," + (Float.parseFloat(values[3]) + amount);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            List<String> fileContent = Files.readAllLines(Paths.get(csvFile));

            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(oldContent)) {
                    fileContent.set(i, dataOverwrite);
                    break;
                }
            }

            Files.write(Paths.get(csvFile), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void ChangeUsernameOrPassWord(String NewData,String currentPassword,int state) throws IOException {
        String oldContent = "";
        String dataOverwrite = "";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (username1.equals(values[0])&&values[1].equals(currentPassword)) {
                    oldContent = line;
                    if(state==0)
                    dataOverwrite = NewData+ "," + values[1] + "," + "user" + "," + getBalance();
                    else if (state ==1)
                        dataOverwrite = values[0] + "," +  NewData+ "," + "user" + "," + getBalance();

                    break;
                }
            }
            try {
                List<String> fileContent = Files.readAllLines(Paths.get(csvFile));

                for (int i = 0; i < fileContent.size(); i++) {
                    if (fileContent.get(i).equals(oldContent)) {
                        fileContent.set(i, dataOverwrite);
                        break;
                    }
                }

                Files.write(Paths.get(csvFile), fileContent);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
public void setPremiumUser(){
    String oldContent = "";
    String dataOverwrite = "";
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        String line;
        br.readLine();
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            if (username1.equals(values[0])) {
                oldContent = line;

                    dataOverwrite = values[0]+ "," + values[1] + "," + "user" + "," + (getBalance()-69.99f)+",Premium-900";

                break;
            }
        }
        try {
            List<String> fileContent = Files.readAllLines(Paths.get(csvFile));

            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(oldContent)) {
                    fileContent.set(i, dataOverwrite);
                    break;
                }
            }

            Files.write(Paths.get(csvFile), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
} catch (IOException e) {
        throw new RuntimeException(e);
    }
}
    public void RefreshPremium()
    {
        ArrayList<String>lines=new ArrayList<>();
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(csvFile2))) {
                String line = br.readLine();
                lines.add(line);
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    line = values[0]+","+values[1]+","+values[2]+","+values[3];
                        String[] values2 = values[4].split("-");
                        if (Integer.parseInt(values2[1]) > 0) {
                            line = line + ","+values2[0]+"-"+(Integer.parseInt(values2[1]) - 1) ;
                        }
                    lines.add(line);
                    }

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
        catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }}
    public boolean IsUserPremium(){
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (username1.equals(values[0])) {
                    for (int i = 4; i < values.length ; i++) {
                        String []line2 =values[i].split("-");
                        if(values[0].equals("Premium"))
                            return true;
                    }

                    break;
                }
            }
            return false;

    }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}







  

