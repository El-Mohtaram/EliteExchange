package ApplicationElite;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Admin {
    public static int lastday=1;
    public static int Currentday=1;
    Account account = new Account();
    public static ArrayList<String> userslist = new ArrayList<>();
    private String csvfile = "src/main/java/data/financialRequests.csv";
    private static ArrayList<String> requests = new ArrayList<>();
    private static ObservableList<DataShow> transaction = FXCollections.observableArrayList();
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
                if (values.length > 4) {
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
        String[] values = {};
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
                    fileContent.add(i, values[0] + "," + values[1] + "," + values[2] + "," + values[3] + "," + "banned");
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
                    fileContent.add(i, values[0] + "," + values[1] + "," + values[2] + "," + values[3]);
                    for (int j = 0; j < userslist.size(); j++) {
                        if (userslist.get(j).equals(user)) {
                            nameuser = userstate[0];
                            userslist.remove(j);
                            userslist.add(j, nameuser);
                        }
                    }
                    break;
                }
            }

            Files.write(Paths.get("src/main/java/data/AccountData.csv"), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addRequests(float amount, String process) {
        try {
            FileWriter fileWriter = new FileWriter(csvfile, true);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.println(account.username1 + "," + amount + "," + process);
            requests.add(account.username1 + " wants to " + process + " " + amount + " $");
            printWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void RestoreData() {
        requests.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(csvfile))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                requests.add(values[0] + " wants to " + values[2] + " " + values[1] + " $");
            }

            requestsTable.clear();
            for (int i = 0; i < requests.size(); i++) {
                requestsTable.add(new DataShow(requests.get(i)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<DataShow> returnList() {

        return requestsTable;
    }

    /////////////////////////////////////
    public void acceptRequest(String request) {
        String[] values2 = request.split(" ");
        transaction(request, 0);
        //String newcontent="";
        String oldContent = "";
        String[] values = {};
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/data/AccountData.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                values = line.split(",");
                if (values2[0].equals(values[0])) {
                    oldContent = line;
                    System.out.println(oldContent);
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            List<String> fileContent = Files.readAllLines(Paths.get("src/main/java/data/AccountData.csv"));
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(oldContent)) {
                    System.out.println(oldContent);
                    fileContent.remove(i);
                    if (values2[3].equals("deposite")) {
                        float newBalance = Float.parseFloat(values2[4]) + Float.parseFloat(values[3]);
                        account.setBalance(newBalance);
                        fileContent.add(i, values[0] + "," + values[1] + "," + values[2] + "," + newBalance);
                    } else {
                        float newBalance = Float.parseFloat(values[3]) - Float.parseFloat(values2[4]);
                        account.setBalance(newBalance);
                        fileContent.add(i, values[0] + "," + values[1] + "," + values[2] + "," + newBalance);
                    }

                }

            }

            Files.write(Paths.get("src/main/java/data/AccountData.csv"), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////
    public void deleteRequest(String request) {
        String[] values2 = request.split(" ");
        String oldContent = "";
        String[] values = {};
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/data/financialRequests.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                values = line.split(",");
                if (values2[0].equals(values[0]) && values2[3].equals(values[2]) && values2[4].equals(values[1])) {
                    oldContent = line;
                    System.out.println(oldContent);
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            List<String> fileContent = Files.readAllLines(Paths.get("src/main/java/data/financialRequests.csv"));
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(oldContent)) {
                    System.out.println(oldContent);
                    fileContent.remove(i);
                    break;
                }

            }

            Files.write(Paths.get("src/main/java/data/financialRequests.csv"), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void transaction(String request, int state) {
        LocalDateTime currentDateTime = LocalDateTime.now(); // Get the current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); // Define the desired format
        String formattedDateTime = currentDateTime.format(formatter); // Format the date and time
        String[] values2 = request.split(" ");
        String oldContent = "";
        String[] values = {};
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/data/TransactionHistory.csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                values = line.split(",");
                if (values2[0].equals(values[0])) {
                    oldContent = line;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            List<String> fileContent = Files.readAllLines(Paths.get("src/main/java/data/TransactionHistory.csv"));
            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(oldContent)) {
                    fileContent.remove(i);
                    if (state == 1)
                        fileContent.add(i, oldContent + ", your request to " + values2[3] + " " + values2[4] + "$" + " had been refused  " + formattedDateTime);
                    else if (state == 2) {
                        fileContent.add(i, oldContent + ",You bought " + values2[1] + " stocks of " + values2[2] + " for " + values2[3] + "$" + "   " + formattedDateTime);
                    }
                    else if (state == 4) {
                        fileContent.add(i, oldContent + ",You bought " + values2[1] + " bonds of " + values2[2] + " for " + values2[3] + "$" + "   " + formattedDateTime);}
                    else if (state == 3) {
                        fileContent.add(i, oldContent + ",You sold " + values2[1] + " stocks of " + values2[2] + " for " + values2[3] + "$" + "   " + formattedDateTime);}
                    else if (values2[3].equals("deposite"))
                        fileContent.add(i, oldContent + ", you  " + values2[3] + "d " + values2[4] + "$  " + formattedDateTime);
                    else if (values2[3].equals("withdrawal"))
                        fileContent.add(i, oldContent + ", you  " + values2[3] + "ed " + values2[4] + "$  " + formattedDateTime);


                }

            }
            Files.write(Paths.get("src/main/java/data/TransactionHistory.csv"), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshTransactionHistory() {
        transaction.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/data/TransactionHistory.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (account.username1.equals(values[0])) {
                    System.out.println("found");
                    for (int i = 1; i < values.length; i++) {
                        transaction.add(new DataShow(values[i], 0.0f));
                        System.out.println(values[i]);
                        System.out.println(transaction.get(i - 1));
                    }
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<DataShow> historyList() {
        return transaction;
    }

    public void openMarket() {
        Currentday++;
        String oldContent = "";
        String dataOverwrite = "";
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/data/Market.csv"))) {
            oldContent = br.readLine();
            String[] values = oldContent.split(",");
            dataOverwrite = values[0]+"," + values[1]+","+ values[2] + "," + "open";
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            List<String> fileContent = Files.readAllLines(Paths.get("src/main/java/data/Market.csv"));

            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(oldContent)) {
                    fileContent.set(i, dataOverwrite);
                    break;
                }
            }

            Files.write(Paths.get("src/main/java/data/Market.csv"), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeMarket() {
        String oldContent = "";
        String dataOverwrite = "";
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/data/Market.csv"))) {
            oldContent = br.readLine();
            String[] values = oldContent.split(",");
            dataOverwrite = values[0]+"," + values[1]+","+ values[2] + "," + "close";
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            List<String> fileContent = Files.readAllLines(Paths.get("src/main/java/data/Market.csv"));

            for (int i = 0; i < fileContent.size(); i++) {
                if (fileContent.get(i).equals(oldContent)) {
                    fileContent.set(i, dataOverwrite);
                    break;
                }
            }

            Files.write(Paths.get("src/main/java/data/Market.csv"), fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean marketOpenOrClose() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/data/Market.csv"))) {
            String line = br.readLine();
            String[] values = line.split(",");
            if (values[3].equals("open")) return true;
            else return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}




