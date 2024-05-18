package ApplicationElite;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PriceUpdater {
    public static void main(String[] args) {

        LocalDateTime currentDateTime = LocalDateTime.now(); // Get the current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Define the desired format
        String formattedDateTime = currentDateTime.format(formatter); // Format the date and time
        Stock stocks = new Stock();
        Bonds bond = new Bonds();
        Account acc=new Account();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule the task to run every 2 minutes
        scheduler.scheduleAtFixedRate(() -> {
            bond.RefreshBondList();
            bond.returnYield();
            stocks.RestoreData();
            stocks.UpdatePrices();

            System.out.println("hello");
            if (stocks.admin.marketOpenOrClose()) {
                for (String key : Stock.stocks.keySet()) {
                    String csvname = key;
                    File file = new File("src/main/java/PriceHistory/" + csvname + ".csv");
                    if (file.exists()) {
                        try (FileWriter fileWriter = new FileWriter("src/main/java/PriceHistory/" + csvname + ".csv", true)) {
                            PrintWriter printWriter = new PrintWriter(fileWriter);
                            List<String> fileContent = Files.readAllLines(Paths.get("src/main/java/PriceHistory/" + csvname + ".csv"));
                            String lastline = fileContent.get(fileContent.size() - 1);
                            String[] line = lastline.split(",");
                            if (line[1].equals(formattedDateTime))
                                printWriter.print("," + Stock.stocks.get(csvname));
                            else {
                                printWriter.println(" ");
                                printWriter.print(csvname + "," + formattedDateTime + "," + Stock.stocks.get(csvname));
                            }
                        } catch (IOException e) {
                        }
                    } else {
                        try (FileWriter fileWriter = new FileWriter("src/main/java/PriceHistory/" + csvname + ".csv", true)) {
                            PrintWriter printWriter = new PrintWriter(fileWriter);
                            printWriter.print(csvname + "," + formattedDateTime);
                        } catch (IOException e) {
                        }
                    }
                }
             }
            try {
                stocks.refreshPercentageList();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            }, 0, 3, TimeUnit.SECONDS);
        }
}


