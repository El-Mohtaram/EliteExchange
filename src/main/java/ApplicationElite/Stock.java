package ApplicationElite;

import java.io.*;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Stock extends Securities {

    private static String company;
    private String csvFile = "src/main/java/data/Market.csv";
    private String csvFile2 = "src/main/java/data/stock.csv";
    public static HashMap<String, Float> stocks = new LinkedHashMap<>();
    private static HashMap<String, Integer> userStocks = new LinkedHashMap<>();
    static private ObservableList<DataShow> stockData = FXCollections.observableArrayList();
    static private ObservableList<DataShow> dateList = FXCollections.observableArrayList();
    static private ObservableList<DataShow> userStockList = FXCollections.observableArrayList();
    static private ObservableList<DataShow> stockPriceHistory = FXCollections.observableArrayList();
    static private ObservableList<DataShow> companylist = FXCollections.observableArrayList();
    static private ArrayList<Float> priceList = new ArrayList<>();
    static private ArrayList<Integer> timeList = new ArrayList<>();

    public void addStock(String name, int number, float f) {
        Add(name, number, f, csvFile, stocks, 0, 0);
    }

    public void RestoreData() {
        RestoreData(csvFile, stocks, stockData, 0);
    }

    public void DeleteStock(String name) {
        Delete(name, csvFile, stocks, 0);
    }

    public void refreshUserStockList() {
        refreshUserSecurities(userStockList, csvFile2, false);
    }

    public ObservableList<DataShow> returnList() {
        return stockData;
    }

    public ObservableList<DataShow> returnUserList() {
        return userStockList;
    }

    public void UpdatePrices() {
        for (String key : stocks.keySet()) {
            float minPrice = stocks.get(key) - 0.1f;
            float maxPrice = stocks.get(key) + 0.1f;

            // Generate a random price within the specified range
            Random random = new Random();
            stocks.replace(key, (minPrice + (maxPrice - minPrice) * random.nextFloat()));

        }
        try {
            String filePath = csvFile; // Replace with your actual file path
            int targetColumn = 2; // Example column number
            ArrayList<String> newValues = new ArrayList<>();

            for (String key : stocks.keySet()) {

                newValues.add("" + stocks.get(key));

            }

            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            List<String> lines = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);

            }
            reader.close();

            // Update the specified column with different values for each row
            for (int i = 1; i < lines.size(); i++) {
                String[] columns = lines.get(i).split(","); // Assuming CSV columns are comma-separated
                columns[targetColumn] = newValues.get(i - 1);
                lines.set(i, String.join(",", columns));

            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (String updatedLine : lines) {
                writer.write(updatedLine);
                writer.newLine();

            }
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public boolean BuyStock(int amount, String company) {

            if (buyCheck(csvFile, amount, company,2)) {
                buyOrSell(company, amount, csvFile2, userStocks, 0,false,0,0);
                updateAmountInMarket(csvFile, amount, company, 0, false);
                return true;
            } else return false;

    }

    public boolean SellStock(int amount, String company) {
        refreshUserSecuritiesMap(csvFile2, userStocks, account.getUsername());
        if (sellCheck(csvFile2, amount, company)) {
            buyOrSell(company, amount, csvFile2, userStocks, 1, false, 0, 0);
            updateAmountInMarket(csvFile, amount, company, 1, false);
            return true;
        } else return false;

    }

    static public float getPrice(String company) {
        return stocks.get(company);
    }

    public boolean fillDateTable(String company) {
        if (!stocks.containsKey(company)) return false;
        else {
            dateList.clear();
            try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/PriceHistory/" + company + ".csv"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    System.out.println(values[1]);
                    dateList.add(new DataShow(values[1], 0));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(dateList.size());
            return true;
        }
    }

    public static ObservableList<DataShow> getDateList() {
        return dateList;
    }

    public void getPriceList(String company, String date) {
        this.company = company;
        priceList.clear();
        timeList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/PriceHistory/" + company + ".csv"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (date.equals(values[1])) {
                    int x = 0;
                    for (int i = 2; i < values.length; i++) {
                        priceList.add(Float.parseFloat(values[i]));
                        timeList.add(x);
                        x += 10;

                    }
                    System.out.println(priceList.size());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Float> getPriceList() {
        return priceList;
    }

    public ArrayList<Integer> getTimeList() {
        return timeList;
    }

    public String getCompany() {
        return company;
    }

    public void fillStockHistoryData(String company, boolean state, File file) {
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/PriceHistory/" + company + ".csv"))) {
            String line;
            int index = 0;
            while ((line = br.readLine()) != null) {
                index++;
                String[] values = line.split(",");
                float max = Float.parseFloat(values[2]);
                float min = Float.parseFloat(values[2]);
                float start = Float.parseFloat(values[2]);
                float end = Float.parseFloat(values[values.length - 1]);
                for (int i = 3; i < values.length; i++) {
                    if (min > Float.parseFloat(values[i])) min = Float.parseFloat(values[i]);
                    if (max < Float.parseFloat(values[i])) max = Float.parseFloat(values[i]);
                }
                if (!state)
                    stockPriceHistory.add(new DataShow(values[1], start, min, max, end));
                else {
                    try (FileWriter writer = new FileWriter(file, true)) {
                        PrintWriter printWriter = new PrintWriter(writer);
                        if (index == 1) {
                            printWriter.println(" date , opening price , lowest price , max price , closing price");
                        }
                        printWriter.println(values[1] + "," + start + "," + min + "," + max + "," + end);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public ObservableList<DataShow> fillcompanytaple() {
        for (String key : stocks.keySet())
            companylist.add(new DataShow(key, true));
        return companylist;
    }

    public static ObservableList<DataShow> getStockPriceHistory() {
        return stockPriceHistory;
    }
}





