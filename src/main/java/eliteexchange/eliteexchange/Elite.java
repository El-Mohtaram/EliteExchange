package eliteexchange.eliteexchange;
import ApplicationElite.Account;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Elite extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //stage.initStyle(StageStyle.TRANSPARENT); //i love attalah
        FXMLLoader fxmlLoader = new FXMLLoader(Elite.class.getResource("mainscene.fxml"));
        Group group = new Group();
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        stage.getIcons().add(icon);
        String css = getClass().getResource("menu.css").toExternalForm();
        stage.setTitle("EliteExchange 0.3.2b");
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        Account account=new Account();
        account.RestoreData();
        launch();

    }
}