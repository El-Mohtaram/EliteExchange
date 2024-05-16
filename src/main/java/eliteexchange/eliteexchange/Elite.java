package eliteexchange.eliteexchange;

import ApplicationElite.Account;
import ApplicationElite.Bonds;
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
    public static String css;
    public static FXMLLoader usermainmenufxml;
    public static Scene usermainmenu;
    @Override
    public void start(Stage stage) throws IOException {
        //stage.initStyle(StageStyle.TRANSPARENT); //i love Elmalak
        FXMLLoader mainload = new FXMLLoader(Elite.class.getResource("mainscene.fxml"));
        usermainmenufxml = new FXMLLoader(Elite.class.getResource("userMenue.fxml"));
        usermainmenu= new Scene(usermainmenufxml.load());
        Group group = new Group();
        Scene scene = new Scene(mainload.load());
        scene.setFill(Color.TRANSPARENT);
        Image icon = new Image(getClass().getResourceAsStream("icon.png"));
        stage.getIcons().add(icon);
        css = getClass().getResource("menu.css").toExternalForm();
        stage.setTitle("EliteExchange 0.9.2b");
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        // Enable hardware acceleration for Windows
        Account account = new Account();
        account.RestoreData();
        System.setProperty("prism.order", "d3d");
        System.setProperty("prism.forceGPU", "true");
        launch();

    }
}