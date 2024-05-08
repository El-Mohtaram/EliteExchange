package eliteexchange.eliteexchange;
import ApplicationElite.Account;
import ApplicationElite.Admin;
import ApplicationElite.DataShow;
import ApplicationElite.Securities;
import ApplicationElite.Stock;
import com.jfoenix.controls.JFXButton;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.EventObject;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.util.Duration;
import org.controlsfx.control.action.Action;

import javafx.fxml.Initializable;

public class UserController implements Initializable {
    Admin admin = new Admin();
    Account account = new Account();
    Stock stock = new Stock();
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Hyperlink signup;
    @FXML
    private Button delete;
    @FXML
    private Button exit;
    @FXML
    private JFXButton TransHistory;//2
    @FXML
    private TableView<DataShow> Addtable, historyTable;
    @FXML
    private TableView<DataShow> requestsTable;
    @FXML
    private TableView<DataShow> MarketList;

    @FXML
    private TableColumn<DataShow, Float> changePrice;
    @FXML
    private TableColumn<DataShow, String> requestColumn, historyColumn;

    @FXML
    private TableColumn<DataShow, String> company;

    @FXML
    private TableColumn<DataShow, Float> currentPrice;

    @FXML
    private TableColumn<DataShow, Float> startPrice;
    @FXML
    private TableColumn<DataShow, Float> numberofStocks;
    @FXML
    private TextField startprice;
    @FXML
    private TextField amount;

    @FXML
    private ImageView immenu;

    @FXML
    private ImageView immenuclose;
    @FXML
    private Button deleteStock;

    @FXML
    private ComboBox<String> userlist;

    @FXML
    private Button addStock, buy;

    @FXML
    private TextField companyName;

    @FXML
    private TextField numberOfStocks;
    @FXML
    private Label requestMessage, balance,buyMessage;

    @FXML
    private Button BuyStock;

    @FXML
    private Button Sellstock;

    @FXML
    Label messagelabel, datee, signedup1;

    @FXML
    private Button back;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmpassword;

    @FXML
    private Hyperlink login;
    private double x, y;
    @FXML
    private AnchorPane slider;
    @FXML
    private Label Menu;
    @FXML
    private Label MenuClose;
    @FXML
    private JFXButton market;
    @FXML
    private JFXButton wd;
    @FXML
    private ImageView aw;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (balance != null)
            balance.setText("Balance: " + account.getBalance() + "$");
        if (historyTable != null)
            historyTable.setItems(admin.historyList());
        if (historyColumn != null)
            historyColumn.setCellValueFactory(new PropertyValueFactory<>("history"));
        if (MenuClose != null)
            MenuClose.setVisible(false);
        if(immenuclose!=null) {
            immenuclose.setRotate(90);
            immenuclose.setVisible(false);
        }
        if (slider != null)
            slider.setTranslateX(-213);
        if (Menu != null)
            Menu.setOnMouseClicked(event -> {
                menuopen();
            });
        if(immenu != null)
        immenu.setOnMouseClicked(event -> {
            menuopen();
        });
        if (MenuClose != null)
            MenuClose.setOnMouseClicked(event -> {
                menuclose();
            });
        if(immenuclose!= null)
        immenuclose.setOnMouseClicked(event -> {
            menuclose();
        });
    }
    private void menuopen(){
    TranslateTransition slide = new TranslateTransition();
    RotateTransition menutr = new RotateTransition();
    slide.setDuration(Duration.seconds(0.4));
    slide.setNode(slider);
    slide.setToX(0);
    menutr.setDuration(Duration.seconds(0.4));
    menutr.setNode(immenu);
    menutr.setToAngle(90);
    menutr.play();
    slide.play();
    slider.setTranslateX(-213);
    slide.setOnFinished((ActionEvent e) -> {
        Menu.setVisible(false);
        immenu.setVisible(false);
        immenu.setRotate(0);
        MenuClose.setVisible(true);
        immenuclose.setVisible(true);
    });
    }
    private void menuclose(){
        TranslateTransition slide = new TranslateTransition();
        RotateTransition menutr = new RotateTransition();
        slide.setDuration(Duration.seconds(0.4));
        slide.setNode(slider);
        slide.setToX(-213);
        menutr.setDuration(Duration.seconds(0.4));
        menutr.setNode(immenuclose);
        menutr.setToAngle(0);
        menutr.play();
        slide.play();
        slider.setTranslateX(0);
        slide.setOnFinished((ActionEvent e) -> {
            Menu.setVisible(true);
            immenu.setVisible(true);
            MenuClose.setVisible(false);
            immenuclose.setVisible(false);
            immenuclose.setRotate(90);
        });
    }
    private void updateDateTimeLabel() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        datee.setText(formattedDateTime);
    }

    @FXML
    private void mainscene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(Elite.css);
        stage.show();
    }

    @FXML
    void BackbuttonPressed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(Elite.css);
        stage.show();
    }


    @FXML
    private void LoginPressed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainscene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(Elite.css);
        stage.show();
    }

    @FXML
    private void SignupPressed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(Elite.css);
        stage.show();
    }


    @FXML
    private void signupConfirmPressed() {
        account.setUserName(username.getText());
        account.setPassword(password.getText());
        account.setCheckPassword(confirmpassword.getText());
        if (account.CheckMatchPassword() && password.getText().length() >= 3) {
            signedup1.setText("Signed up success");
            account.ImportUserData();
        } else {
            messagelabel.setText(account.SignUpMessages());

        }
    }

    @FXML
    private void LoginConfirmPressed(ActionEvent event) throws IOException {
        account.setUserName(username.getText());
        account.setPassword(password.getText());
        if (account.CheckLoginData() && account.userOrAdmin().equals("user") && Account.BannedOrNot) {
            messagelabel.setText("your account has been banned");
        } else if (account.CheckLoginData() && account.userOrAdmin().equals("user")) {
            stock.RestoreData();
            Parent root = FXMLLoader.load(getClass().getResource("userMenue.fxml"));
            String cssuser = getClass().getResource("usermenu.css").toExternalForm();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            scene.getStylesheets().add(cssuser);
            stage.show();
        } else if (account.CheckLoginData() && account.userOrAdmin().equals("admin") && account.admin_log_in_out().equals("no")) {
            Admin.createuserslist();
            account.adminSwitch();
            stock.RestoreData();
            Parent root = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else if (account.CheckLoginData() && account.userOrAdmin().equals("admin") && account.admin_log_in_out().equals("yes"))
            messagelabel.setText("Admin already logged in!");
        else {
            messagelabel.setText(account.LoginMessages());
        }
    }

    @FXML
    void BackPressed(ActionEvent event) throws IOException {
        if (account.userOrAdmin().equals("user")) {
            Parent root = FXMLLoader.load(getClass().getResource("userMenue.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }


    @FXML
    private void exit(MouseEvent event) throws IOException {
        account.adminSwitch();
        System.exit(0);
    }

    @FXML
    private void min(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    private void dragwindow(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setY(event.getScreenY() - y);
        stage.setX(event.getScreenX() - x);
    }

    @FXML
    private void presswindow(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    @FXML
    private void exit2(ActionEvent event) throws IOException {
        System.exit(0);
    }


    @FXML
    void MarketPressed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Market.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stock.RestoreData();
    }


    @FXML
    private void DandW(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("WDscene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    private void deposite() {
        admin.addRequests(Float.parseFloat(amount.getText()), "deposite");
        requestMessage.setText("Your Request has been sent to the admin successfully");
    }

    @FXML
    private void withdrawal() {
        if (Float.parseFloat(amount.getText()) > account.getBalance()) {
            requestMessage.setText("You have insufficient balance");
        } else {
            admin.addRequests(Float.parseFloat(amount.getText()), "withdrawal");
            requestMessage.setText("Your Request has been sent to the admin successfully");
        }
    }

    @FXML
    private void transactionhistoryscene(ActionEvent event) throws IOException {
        admin.refreshTransactionHistory();
        Parent root = FXMLLoader.load(getClass().getResource("transactionhistory.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}









