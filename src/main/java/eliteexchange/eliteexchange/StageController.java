package eliteexchange.eliteexchange;

import ApplicationElite.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.*;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
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

public class StageController implements Initializable {
    float angle = 0;
    Admin admin = new Admin();
    Account account = new Account();
    Stock stock = new Stock();
    Bonds bond = new Bonds();
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private ToggleButton marketStatues;

    @FXML
    private ToggleGroup aaa;
    @FXML
    private Hyperlink signup;
    @FXML
    private Button delete;
    @FXML
    private Button exit;
    private static ActionEvent refreshmanagement;
    @FXML
    private TableView<DataShow> Addtable;
    @FXML
    private TableView<DataShow> requestsTable;
    @FXML
    private Button market;
    @FXML
    private TableView<DataShow> MarketList;

    @FXML
    private TableColumn<DataShow, Float> changePrice;
    @FXML
    private TableColumn<DataShow, String> requestColumn;

    @FXML
    private TableColumn<DataShow, String> company;

    @FXML
    private TableColumn<DataShow, Float> currentPrice;

    @FXML
    private TableColumn<DataShow, Float> faceValue;
    @FXML
    private TableColumn<DataShow, Float> numberofStocks;
    @FXML
    private TextField startprice, value;
    @FXML
    private TextField amount;

    @FXML
    private Button deleteStock;

    @FXML
    private ComboBox<String> userlist;

    @FXML
    private Button addStock, buy;

    @FXML
    private TextField companyName, ValidUntil;

    @FXML
    private TextField numberOfStocks;
    @FXML
    private Label requestMessage, balance, buyMessage;

    @FXML
    private Button BuyStock;

    @FXML
    private Button Sellstock;

    @FXML
    Label messagelabel, datee, signedup1;

    @FXML
    private TableView<DataShow> bondsTable;

    @FXML
    private TableColumn<DataShow, String> companyB;


    @FXML
    private TableColumn<DataShow, Integer> numberB, expCol;


    @FXML
    private TableColumn<DataShow, Float> priceBCol;


    @FXML
    private TextField yield;

    @FXML
    private TableColumn<DataShow, Float> yieldB, startPrice;

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
    private Button signupConfirm;
    @FXML
    private Button loginConfirm;

    @FXML
    public FontAwesomeIconView RefreshIcon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (datee != null) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), event -> updateDateTimeLabel())
            );
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();
        }
        if (bondsTable != null)
            bondsTable.setItems(Bonds.getBondData());
        if (companyB != null)
            companyB.setCellValueFactory(new PropertyValueFactory<>("company"));
        if (expCol != null)
            expCol.setCellValueFactory(new PropertyValueFactory<>("exp"));
        if (faceValue != null)
            faceValue.setCellValueFactory(new PropertyValueFactory<>("price"));
        if (yieldB != null)
            yieldB.setCellValueFactory(new PropertyValueFactory<>("yield"));
        if (numberB != null)
            numberB.setCellValueFactory(new PropertyValueFactory<>("number"));
        if (startPrice != null)
            startPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        if (company != null)
            company.setCellValueFactory(new PropertyValueFactory<>("company"));
        if (numberofStocks != null)
            numberofStocks.setCellValueFactory(new PropertyValueFactory<>("number"));
        if (Addtable != null)
            Addtable.setItems(stock.returnList());
        if (requestColumn != null)
            requestColumn.setCellValueFactory(new PropertyValueFactory<>("requests"));
        if (requestsTable != null)
            requestsTable.setItems(admin.returnList());

        if (userlist != null) {
            for (int i = 0; i < Admin.userslist.size(); i++) {
                userlist.getItems().add(Admin.userslist.get(i));
            }
        }

        // Add shutdown hook
        if (account != null)
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                account.adminSwitch();
            }));

        TableColumn<DataShow, String> column1 = new TableColumn<>("Data");
        column1.setCellValueFactory(new PropertyValueFactory<>("data"));
    }


    private void updateDateTimeLabel() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        if (datee != null)
            datee.setText(formattedDateTime);
        if (numberofStocks != null)
            numberofStocks.setCellValueFactory(new PropertyValueFactory<>("number"));
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
    private void exit(MouseEvent event) throws IOException {
        System.exit(0);
    }

    @FXML
    private void min(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
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
            signedup1.setVisible(true);
            messagelabel.setVisible(false);
            signedup1.setText("Signed up success");
            account.ImportUserData();
        } else {
            signedup1.setVisible(false);
            messagelabel.setVisible(true);
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
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(Elite.usermainmenu);
            String usercss = getClass().getResource("usermenu.css").toExternalForm();
            stage.getScene().getStylesheets().add(usercss);
            stage.show();
        } else if (account.CheckLoginData() && account.userOrAdmin().equals("admin") && account.admin_log_in_out().equals("no")) {
            Admin.createuserslist();
            account.adminSwitch();
            stock.RestoreData();
            bond.RefreshBondList();
            Parent root = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            String Admincss = getClass().getResource("AdminMenu.css").toExternalForm();
            scene.getStylesheets().add(Admincss);
            stage.setScene(scene);
            stage.show();
        } else if (account.CheckLoginData() && account.userOrAdmin().equals("admin") && account.admin_log_in_out().equals("yes"))
            messagelabel.setText("Admin already logged in!");
        else {
            messagelabel.setText(account.LoginMessages());
        }
    }

    @FXML
    void BackPressed(MouseEvent event) throws IOException {
        if (account.userOrAdmin().equals("user")) {
            Parent root = FXMLLoader.load(getClass().getResource("userMenue.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            String usercss = getClass().getResource("usermenu.css").toExternalForm();
            stage.getScene().getStylesheets().add(usercss);
            stage.show();
        } else {
            Parent root = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            String Admincss = getClass().getResource("AdminMenu.css").toExternalForm();
            scene.getStylesheets().add(Admincss);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    private void buy() {

        DataShow selectedStock = Addtable.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            String selectedName = selectedStock.getCompany();
            if (admin.marketOpenOrClose()) {
                if (stock.BuyStock(Integer.parseInt(amount.getText()), selectedName)) {
                    account.updateBalance();
                    stock.RestoreData();
                    buyMessage.setText("Bought Successfully");
                } else buyMessage.setText("Not enough amount");
            } else buyMessage.setText("Sorry, market is closed");
        }
    }


    @FXML
    public void buyBond() {
        DataShow selectedBond = bondsTable.getSelectionModel().getSelectedItem();
        if (selectedBond != null) {
            String selectedName = selectedBond.getCompany();
            int selectedExp = selectedBond.getExp();
            float selectedYield = selectedBond.getYield();
            if (admin.marketOpenOrClose()) {
                if (bond.buyBond(selectedName, Integer.parseInt(amount.getText()), selectedExp, selectedYield)) {
                    account.updateBalance();
                    bond.RefreshBondList();
                    buyMessage.setText("Bought Successfully");
                } else buyMessage.setText("Not enough amount");
            } else buyMessage.setText("Sorry, market is closed");
        }
    }

    @FXML
    public void goToGraphs(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("graphs.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        bond.RefreshBondList();
        stage.show();

    }

    @FXML
    public void priceHistory(ActionEvent event) throws IOException {
        File file = null;
        stock.fillStockHistoryData("amazon", false, file);
        Parent root = FXMLLoader.load(getClass().getResource("priceHistory.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}