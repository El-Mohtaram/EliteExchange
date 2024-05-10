package ApplicationElite;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PriceUpdater {
    public static void main(String[] args) {
        Stock stocks = new Stock();
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Schedule the task to run every 2 minutes
        scheduler.scheduleAtFixedRate(() -> {
            stocks.RestoreData();
            stocks.UpdatePrices();


        }, 0, 10, TimeUnit.SECONDS);
    }


}


