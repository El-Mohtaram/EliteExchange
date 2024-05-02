package ApplicationElite;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Admin {
    Account account = new Account();
    public static ArrayList<String> userslist = new ArrayList<>();

    public static void createuserslist() {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/data/AccountData.csv"))) {
            String line;
            line = br.readLine();
            String[] values = line.split(",");
            while ((line = br.readLine()) != null) {
                values = line.split(",");
                System.out.println(userslist.size());
                if (values[2].equals("user")) {
                    userslist.add(values[0]);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void deleteuser(String user) {
        String  oldContent="";


        int index=0;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/data/AccountData.csv"))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (user.equals(values[0])) {
                    oldContent = line;}
                }


            }

        catch (IOException e) {
            e.printStackTrace();
        }
        try
    {
        List<String> fileContent = Files.readAllLines(Paths.get("src/main/java/data/AccountData.csv"));
        System.out.println(fileContent.get(2));
        for (int i = 0; i < fileContent.size(); i++) {
            if (fileContent.get(i).equals(oldContent)) {
                fileContent.remove(i);
                for (int j = 0; j <userslist.size() ; j++) {
                    if(userslist.get(j).equals(user))
                        userslist.remove(j);
                }
                break; // Assuming you want to replace the first occurrence only
            }
        }

        Files.write(Paths.get("src/main/java/data/AccountData.csv"), fileContent);
    } catch(IOException e)

    {
        e.printStackTrace();
    }
}
}


