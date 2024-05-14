package eliteexchange.eliteexchange;

import ApplicationElite.Account;
import ApplicationElite.Admin;
import ApplicationElite.DataShow;
import ApplicationElite.Securities;
import ApplicationElite.Stock;
import ApplicationElite.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
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

import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.action.Action;
import javafx.scene.media.Media;
import javafx.fxml.Initializable;

public class UserController implements Initializable {
    Admin admin = new Admin();
    Account account = new Account();
    Stock stock = new Stock();
    Bonds bond = new Bonds();
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableColumn<DataShow, String> dateCol;
    @FXML
    private TableView<DataShow> dateTable;
    @FXML
    private LineChart<Number, Number> priceGraph;
    @FXML
    private Hyperlink signup;
    @FXML
    private Button delete;
    @FXML
    private Button exit;
    @FXML
    private JFXButton TransHistory;//2
    @FXML
    private TableView<DataShow> Addtable, historyTable, userStockList, userBondList;
    @FXML
    private TableView<DataShow> requestsTable;
    @FXML
    private TableView<DataShow> MarketList;

    @FXML
    private TableColumn<DataShow, Float> changePrice;
    @FXML
    private TableColumn<DataShow, String> requestColumn, historyColumn;

    @FXML
    private TableColumn<DataShow, String> company, ownedCompanyCol;

    @FXML
    private TableColumn<DataShow, Float> currentPrice;
    @FXML
    private TableColumn<DataShow, Integer> stocksOwned;

    @FXML
    private TableColumn<DataShow, Float> startPrice, totalPrice;
    @FXML
    private TableColumn<DataShow, Float> numberofStocks;
    @FXML
    private TextField startprice;
    @FXML
    private TextField amount;

    @FXML
    private ImageView immenu,star;

    @FXML
    private ImageView immenuclose;
    @FXML
    private Button deleteStock;

    @FXML
    private ComboBox<String> userlist;
    @FXML
    private Region swaptaps;

    @FXML
    private Button addStock, buy, sell;

    @FXML
    private TextField companyName;

    @FXML
    private TextField numberOfStocks;
    @FXML
    private Label requestMessage, balance, buyMessage, SellMessage;

    @FXML
    private Button BuyStock;

    @FXML
    private Button Sellstock;

    @FXML
    Label messagelabel, datee, signedup1,viplevel;

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
    private Label MenuClose, balancetit, Menu;
    @FXML
    private JFXButton market;
    @FXML
    private JFXButton wd;
    @FXML
    private ImageView aw;
    @FXML
    private FontAwesomeIconView showbalance, hidebalance;
    @FXML
    private JFXButton Dashboardb;
    @FXML
    private Label welcomemes;
    @FXML
    private NumberAxis priceAxis;
    @FXML
    private JFXListView<String> hotstocks,hotbonds;

    //private JFXListView<String> hotstocks;

    @FXML
    private TableColumn<DataShow, Float> closePrice;
    @FXML
    private TableColumn<DataShow, Float> lowPrice;

    @FXML
    private TableColumn<DataShow, Float> maxPrice;

    @FXML
    private TableColumn<DataShow, Float> openPrice;

    @FXML
    private TableView<DataShow> pricehistoryTable;

    static boolean sceneloaded=false;
    Image menui = new Image("file:src\\main\\resources\\eliteexchange\\eliteexchange\\menu.png");
    Image menuclosei = new Image("file:src\\main\\resources\\eliteexchange\\eliteexchange\\menuclose.png");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(star != null)
        star.setTranslateX(200);
        if(welcomemes!=null)
        welcomemes.setText("Welcome ");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> updateDateTimeLabel())
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        if (hidebalance != null) {
            hidebalance.setVisible(false);
        }
        if (userBondList != null)
            userBondList.setItems(bond.returnUserList());
        if (userStockList != null)
            userStockList.setItems(stock.returnUserList());
        if (totalPrice != null)
            totalPrice.setCellValueFactory((new PropertyValueFactory<>("price")));
        if (stocksOwned != null)
            stocksOwned.setCellValueFactory((new PropertyValueFactory<>("number")));
        if (ownedCompanyCol != null)
            ownedCompanyCol.setCellValueFactory((new PropertyValueFactory<>("company")));
        if (balance != null) {
            balance.setText("******");
            balance.setTextFill(Color.color(0.9176470588235294, 0.9254901960784314, 0.9372549019607843));
        }
        if (historyTable != null)
            historyTable.setItems(admin.historyList());
        if (pricehistoryTable != null)
            pricehistoryTable.setItems(stock.getStockPriceHistory());
        if (dateTable != null)
            dateTable.setItems(Stock.getDateList());
        if (historyColumn != null)
            historyColumn.setCellValueFactory(new PropertyValueFactory<>("history"));
        if (maxPrice != null)
           maxPrice.setCellValueFactory(new PropertyValueFactory<>("max"));
        if (lowPrice != null)
            lowPrice.setCellValueFactory(new PropertyValueFactory<>("min"));
        if (closePrice != null)
            closePrice.setCellValueFactory(new PropertyValueFactory<>("end"));
        if (openPrice != null)
           openPrice.setCellValueFactory(new PropertyValueFactory<>("start"));
        if (dateCol != null)
            dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        // TransHistory.setText("Transaction\nHistory");
        if (MenuClose != null)
            MenuClose.setVisible(false);
        if (MenuClose != null)
            immenuclose.setRotate(90);
        if (immenuclose != null)
            immenuclose.setVisible(false);
        if (slider != null)
            slider.setTranslateX(-213);
        if (Menu != null)
            Menu.setOnMouseClicked(event -> {
                menuopen();
            });
        if (immenu != null)
            immenu.setOnMouseClicked(event -> {
                menuopen();
            });
        if (MenuClose != null)
            MenuClose.setOnMouseClicked(event -> {
                menuclose();
            });
        if (immenuclose != null)
            immenuclose.setOnMouseClicked(event -> {
                menuclose();
            });
        if (balancetit != null || showbalance != null)
            showbalance.setOnMouseClicked(event -> {
                menuclose();
                showbalance.setVisible(false);
                balance.setText("" + account.getBalance());
                balance.setTextFill(Color.WHITE);
                balancetit.setTextFill(Color.WHITE);
                hidebalance.setVisible(true);
            });
        if (balancetit != null || hidebalance != null)
            hidebalance.setOnMouseClicked(event -> {
                menuclose();
                showbalance.setVisible(true);
                balance.setText("******");
                balancetit.setTextFill(Color.color(0.9176470588235294, 0.9254901960784314, 0.9372549019607843));
                balance.setTextFill(Color.color(0.9176470588235294, 0.9254901960784314, 0.9372549019607843));
                hidebalance.setVisible(false);
            });
        if(hotstocks!=null)
        hotstocks.getItems().addAll("elmohtaram","2342","zdsfsd","fsdaasdf","dsafa","dsafdsaf");
        if(hotstocks!=null)    hotstocks.setCellFactory(param -> new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (item != null && !empty) {
                    setText(item);
                   setFont(Font.font("Yaahowu Bold", 14)); // Set the font to "yy" with size 12pt
                    setStyle("-fx-background-radius: 10;"); // Set the cell background color to red
                } else {
                    setText(null);
                    setGraphic(null);
                    setStyle(""); // Clear styles for empty cells
                }
            }
        });
        if(hotstocks!=null)hotstocks.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> event.consume());
        if(hotstocks!=null)  hotstocks.lookupAll(".scroll-bar").forEach(scrollBar -> scrollBar.setVisible(false));

    }

    private void updateDateTimeLabel() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy\nhh:mm:ss a");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        if (datee != null ){
            datee.setText(formattedDateTime);
        }
        if(account.getUsername()!=null && !sceneloaded){
            welcomemes.setText("Welcome "+account.getUsername());
            sceneloaded=true;
        }
    }

    private void menuopen() {
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

    private void menuclose() {
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
    @FXML
    private void Stockspressed(){
        menuclose();
        TranslateTransition tapsawp = new TranslateTransition();
        tapsawp.setDuration(Duration.seconds(0.4));
        tapsawp.setNode(swaptaps);
        tapsawp.setToX(204);
        tapsawp.play();
        swaptaps.setTranslateX(0);
        tapsawp.setOnFinished((ActionEvent e) -> {
            welcomemes.setVisible(false);
            showbalance.setVisible(false);
            hidebalance.setVisible(false);
            balancetit.setVisible(false);
            balance.setVisible(false);
            hotstocks.setVisible(false);
            hotbonds.setVisible(false);
            viplevel.setVisible(false);
        });
    }
    @FXML
    private void Optionspressed(){
        menuclose();
        TranslateTransition tapsawp = new TranslateTransition();
        tapsawp.setDuration(Duration.seconds(0.4));
        tapsawp.setNode(swaptaps);
        tapsawp.setToX(612);
        tapsawp.play();
        swaptaps.setTranslateX(0);
        tapsawp.setOnFinished((ActionEvent e) -> {

        });
    }
    @FXML
    private void Dashboaedpressed(){
        menuclose();
        TranslateTransition tapsawp = new TranslateTransition();
        tapsawp.setDuration(Duration.seconds(0.4));
        tapsawp.setNode(swaptaps);
        tapsawp.setToX(0);
        tapsawp.play();
        swaptaps.setTranslateX(0);
        tapsawp.setOnFinished((ActionEvent e) -> {
            welcomemes.setVisible(true);
            showbalance.setVisible(true);
            hidebalance.setVisible(true);
            balancetit.setVisible(true);
            balance.setVisible(true);
            hotstocks.setVisible(true);
            hotbonds.setVisible(true);
            viplevel.setVisible(true);
        });
    }
    @FXML
    public void BondsPressed(ActionEvent event) throws IOException {
        menuclose();
        TranslateTransition tapsawp = new TranslateTransition();
        tapsawp.setDuration(Duration.seconds(0.4));
        tapsawp.setNode(swaptaps);
        tapsawp.setToX(408);
        tapsawp.play();
        swaptaps.setTranslateX(0);
        tapsawp.setOnFinished((ActionEvent e) -> {
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("bondsMarket.fxml"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            bond.RefreshBondList();
            stage.show();
        });
    }
    @FXML
    private void setImmenuent(MouseEvent event){
    immenu.setImage(menui);
    }
    @FXML
    private void setImmenuex(MouseEvent event){
   immenu.setImage(menuclosei);
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
//    @FXML
//    void BondsPressed(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("BondsScene.fxml"));
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }

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

    @FXML
    public void sell() {
        DataShow selectedStock = userStockList.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            String selectedName = selectedStock.getCompany();
            if (admin.marketOpenOrClose()) {
                if (stock.SellStock(Integer.parseInt(amount.getText()), selectedName)) {
                    stock.refreshUserStockList();
                    account.updateBalance();
                    stock.RestoreData();
                    SellMessage.setText("Sold Successfully");
                } else SellMessage.setText("Not enough amount");
            } else buyMessage.setText("Sorry, market is closed");
        }
    }



    @FXML
    public void yourBonds(ActionEvent event) throws IOException {
        bond.refreshUserBondList();
        Parent root = FXMLLoader.load(getClass().getResource("userBonds.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void sellBond() {
        DataShow selectedBond = userBondList.getSelectionModel().getSelectedItem();
        if (selectedBond != null) {
            String selectedName = selectedBond.getCompany();
            if (admin.marketOpenOrClose()) {
                if (bond.SellBond(Integer.parseInt(amount.getText()), selectedName)) {
                    bond.refreshUserBondList();
                    account.updateBalance();
                    bond.RefreshBondList();
                    SellMessage.setText("Sold Successfully");
                } else SellMessage.setText("Not enough amount");
            } else buyMessage.setText("Sorry, market is closed");
        }
    }

    @FXML
    public void searchStock() {
        LocalDateTime currentDateTime = LocalDateTime.now(); // Get the current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Define the desired format
        String formattedDateTime = currentDateTime.format(formatter); // Format the date and time
        if (stock.fillDateTable(companyName.getText())) {
            stock.getPriceList(companyName.getText(), formattedDateTime);
            if(priceAxis!=null) {
                if(stock.getPriceList().size()>0) {
                    priceAxis.setAutoRanging(false); // Disable automatic scaling
                    priceAxis.setLowerBound(stock.getPriceList().get(0) - 2); // Set your desired lower bound
                    priceAxis.setUpperBound(stock.getPriceList().get(0) + 2);
                }//
            }
            priceGraph.getData().clear();
            XYChart.Series series = new XYChart.Series();
            for (int i = 0; i < stock.getPriceList().size(); i++) {
                series.getData().add(new XYChart.Data(stock.getTimeList().get(i), stock.getPriceList().get(i)));
            }
            priceGraph.setCreateSymbols(false);
            if (priceGraph != null)
                priceGraph.getData().add(series);
        }
    }

    @FXML
    public void showSelectedDateGraph() {
        DataShow selectedDate = dateTable.getSelectionModel().getSelectedItem();
        if (selectedDate != null) {
            String selectedName = selectedDate.getDate();
            System.out.println(selectedName);
            stock.getPriceList(stock.getCompany(), selectedName);
            priceGraph.getData().clear();
            if(priceAxis!=null) {
                if(stock.getPriceList().size()>0) {
                    priceAxis.setLowerBound(stock.getPriceList().get(0) -2); // Set your desired lower bound
                    priceAxis.setUpperBound(stock.getPriceList().get(0) +2);
                }//
            }
            XYChart.Series series = new XYChart.Series();
            for (int i = 0; i < stock.getPriceList().size(); i++) {
                series.getData().add(new XYChart.Data(stock.getTimeList().get(i), stock.getPriceList().get(i)));
            }
            priceGraph.setCreateSymbols(false);
            if (priceGraph != null)
                priceGraph.getData().add(series);


        }
    }

}










