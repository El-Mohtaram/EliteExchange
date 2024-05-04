package ApplicationElite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Admin {
    Account account=new Account();
    public static ArrayList<String> userslist = new ArrayList<>();
    private String csvfile="src/main/java/data/financialRequests.csv";
    private static ArrayList<String>requests=new ArrayList<>();
     static private ObservableList<DataShow> requestsTable = FXCollections.observableArrayList();

    public static void createuserslist() {
        userslist.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/data/AccountData.csv"))) {
            String line;
            line = br.readLine();
            String[] values = line.split(",");
            while ((line = br.readLine()) != null) {
                values = line.split(",");
                System.out.println(userslist.size());
                if (values.length >4) {
                    if (values[2].equals("user")) {
                        userslist.add(values[0] + "," + values[4]);
                    }
                } else if (values[2].equals("user")) {
                    userslist.add(values[0]);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void deleteuser(String user) {
        String oldContent = "";

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/data/AccountData.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (user.equals(values[0])) {
                    oldContent = line;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            List<String> fileContent = Files.readAllLines(Paths.get("src/main/java/data/AccountData.csv"));
            System.out.println(fileContent.get(2));
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(oldContent)) {
                    fileContent.remove(i);
                    for (int j = 0; j < userslist.size(); j++) {
                        if (userslist.get(j).equals(user))
                            userslist.remove(j);
                    }
                    break; // Assuming you want to replace the first occurrence only
                }
            }

            Files.write(Paths.get("src/main/java/data/AccountData.csv"), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void blockuser(String user) {
        String oldContent = "";
        String[] values={};
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/data/AccountData.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                 values = line.split(",");

                if (user.equals(values[0])) {
                    oldContent = line;
                    break;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String nameuser;
            List<String> fileContent = Files.readAllLines(Paths.get("src/main/java/data/AccountData.csv"));
            System.out.println(fileContent.get(2));
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(oldContent)) {
                    fileContent.remove(i);
                    fileContent.add(i, values[0] + "," +values[1] + "," +values[2] + "," +values[3]+","+ "banned");
                    for (int j = 0; j < userslist.size(); j++) {
                        if (userslist.get(j).equals(user)) {
                            nameuser = userslist.get(j);
                            userslist.remove(j);
                            userslist.add(j, nameuser + "," + "banned");
                        }
                    }
                    break; // Assuming you want to replace the first occurrence only
                }
            }

            Files.write(Paths.get("src/main/java/data/AccountData.csv"), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void unblockuser(String user) {
        String oldContent = "";
        String[] userstate = user.split(",");
        String line = "";
        String[] values = {};
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/data/AccountData.csv"))) {
            br.readLine();
            while ((line = br.readLine()) != null) {
                values = line.split(",");
                if (userstate[0].equals(values[0])) {
                    oldContent = line;
                    break;
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            String nameuser;
            List<String> fileContent = Files.readAllLines(Paths.get("src/main/java/data/AccountData.csv"));
            System.out.println(fileContent.get(2));
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(oldContent)) {
                    fileContent.remove(i);
                    fileContent.add(i, values[0] + "," + values[1] + "," + values[2]+","+values[3]);
                    for (int j = 0; j < userslist.size(); j++) {
                        if (userslist.get(j).equals(user)) {
                            nameuser = userstate[0];
                            userslist.remove(j);
                            userslist.add(j, nameuser);
                        }
                    }
                    break; // Assuming you want to replace the first occurrence only
                }
            }

            Files.write(Paths.get("src/main/java/data/AccountData.csv"), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void addRequests(float amount,String process)
    {
  try {
                FileWriter fileWriter = new FileWriter(csvfile,true);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.println(account.username1 + "," +account.balance+","+amount+","+process);
                requests.add(account.username1+" wants to "+process+" "+amount+"$"+" ,User balance:"+account.balance+"$");
                printWriter.close();
                
            }
            catch(IOException e){
                e.printStackTrace();
            }
    }
    public void RestoreData()
    {
        requests.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(csvfile))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values=line.split(",");
              requests.add(values[0]+" wants to "+values[3]+" "+values[2]+"$"+" User balance: "+values[1]+"$");
                              }

                requestsTable.clear();
                for (int i = 0; i < requests.size(); i++) {
                    requestsTable.add(new DataShow(requests.get(i)));
                }
            
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public ObservableList<DataShow> returnList()
{
  
    return requestsTable;
}

}


