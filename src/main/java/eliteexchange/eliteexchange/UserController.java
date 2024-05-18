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
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    ObservableList<String>changemList= stock.getPercentageList();


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
    private TableColumn<String,String>changem;

    @FXML
    private TableColumn<DataShow, String> company, ownedCompanyCol, companyB, companym, companyBm,ownedCompanybCol;

    @FXML
    private TableColumn<DataShow, Float> currentPrice;
    @FXML
    private TableColumn<DataShow, Integer> stocksOwned,bondsOwned;

    @FXML
    private TableColumn<DataShow, Float> startPrice, totalPrice, startPricem,totalvaluebonds;
    @FXML
    private TableColumn<DataShow, Float> numberofStocks;

    @FXML
    private TextField amount;

    @FXML
    private ImageView immenu, star;

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
    private Label requestMessage, balance, buyMessage, SellMessage,testcompany;

    @FXML
    private Button BuyStock;

    @FXML
    private Button Sellstock;

    @FXML
    Label messagelabel, datee, signedup1, viplevel;
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
    @FXML
    private TableColumn<DataShow, Float> exportcompany, yieldB, yieldBm;
    @FXML
    private TableColumn<DataShow, Integer> numberB, numberBm, numberofStocksm;
    @FXML
    private TableView<DataShow> companylists;
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
    private Label MenuClose, balancetit, Menu, hotbondstit;
    @FXML
    private JFXButton market,withdraw,depositb,sellb;
    @FXML
    private JFXButton wd;
    @FXML
    private ImageView aw;
    @FXML
    private FontAwesomeIconView showbalance, hidebalance;
    @FXML
    private JFXButton Dashboardb;
    @FXML
    private Label welcomemes, hotstockstit, titleslabel;
    @FXML
    private NumberAxis priceAxis;
    @FXML
    private TableView<DataShow> hotstocks, hotbonds, StocksMarket;
    @FXML
    private TableView<DataShow> NotifTable;
    @FXML
    private TableColumn<DataShow, String> NotifCol=new TableColumn<>("Notification");;
    @FXML
    private Label messages,yourbonds;
    Image menui = new Image("file:src\\main\\resources\\eliteexchange\\eliteexchange\\menu.png");
    Image menuclosei = new Image("file:src\\main\\resources\\eliteexchange\\eliteexchange\\menuclose.png");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(userBondList != null)
        userBondList.setVisible(false);
        if(sellb != null)
        sellb.setVisible(false);
        if(yourbonds != null)
        yourbonds.setVisible(false);
        if(userStockList != null)
        userStockList.setVisible(false);
        if (StocksMarket != null) {
            StocksMarket.setVisible(false);
            StocksMarket.setTranslateX(150);
        }
        if (NotifTable != null)  {
            try {
                NotifTable.setItems(stock.fillnotfilist());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        if (NotifCol != null) {
            NotifCol.setCellValueFactory(new PropertyValueFactory<>("notif"));
        }
        if (titleslabel != null) titleslabel.setVisible(false);
        if (historyTable != null) historyTable.setVisible(false);
        if (amount != null) amount.setVisible(false);
        if (depositb != null) depositb.setVisible(false);
        if (withdraw != null) withdraw.setVisible(false);
        if (historyTable != null) historyTable.setVisible(false);
        if(userBondList!= null)
        userBondList.setTranslateX(200);
        if (viplevel != null) {
            viplevel.setTranslateX(170);
        }
        if (star != null)
            star.setTranslateX(200);
        if (welcomemes != null)
            welcomemes.setText("Welcome ");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    try {
                        updateDateTimeLabel();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        if (startPrice != null) {
            startPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
            startPrice.setResizable(false);
        }
        if (historyColumn != null) historyColumn.setResizable(false);
        if (company != null) {
            company.setCellValueFactory(new PropertyValueFactory<>("company"));
            company.setResizable(false);
        }
        if (yieldB != null) {
            yieldB.setCellValueFactory(new PropertyValueFactory<>("yield"));
            yieldB.setResizable(false);
        }
        if (numberB != null) {
            numberB.setCellValueFactory(new PropertyValueFactory<>("number"));
            numberB.setResizable(false);
        }
        if (companyB != null) {
            companyB.setCellValueFactory(new PropertyValueFactory<>("company"));
            companyB.setResizable(false);
        }
        if (startPricem != null) {
            startPricem.setCellValueFactory(new PropertyValueFactory<>("price"));
            startPricem.setResizable(false);
        }
        if (companym != null) {
            companym.setCellValueFactory(new PropertyValueFactory<>("company"));
            companym.setResizable(false);
        }
//        if (yieldBm != null)
//            yieldBm.setCellValueFactory(new PropertyValueFactory<>("yield"));
//        yieldBm.setResizable(false);
//        if (numberBm != null)
//            numberBm.setCellValueFactory(new PropertyValueFactory<>("number"));
//        numberBm.setResizable(false);
//        if (companyBm != null)
//            companyBm.setCellValueFactory(new PropertyValueFactory<>("company"));
//        companyBm.setResizable(false);
        if (numberofStocksm != null) {
            numberofStocksm.setCellValueFactory(new PropertyValueFactory<>("number"));
            numberofStocksm.setResizable(false);
        }
        if (hidebalance != null) {
            hidebalance.setVisible(false);
        }
        if (userBondList != null)
            userBondList.setItems(bond.returnUserList());
        if (userStockList != null)
            userStockList.setItems(stock.returnUserList());
        if (totalPrice != null)
            totalPrice.setCellValueFactory((new PropertyValueFactory<>("price")));
        if(totalvaluebonds!= null)
        totalvaluebonds.setCellValueFactory((new PropertyValueFactory<>("price")));

        if (stocksOwned != null)
            stocksOwned.setCellValueFactory((new PropertyValueFactory<>("number")));
        if(bondsOwned != null)
        bondsOwned.setCellValueFactory((new PropertyValueFactory<>("number")));
        if (ownedCompanyCol != null){
            ownedCompanyCol.setCellValueFactory((new PropertyValueFactory<>("company")));
        ownedCompanybCol.setCellValueFactory((new PropertyValueFactory<>("company")));}
        if (balance != null) {
            balance.setText("******");
            balance.setTextFill(Color.color(0.9176470588235294, 0.9254901960784314, 0.9372549019607843));
        }
        if (historyTable != null)
            historyTable.setItems(admin.historyList());
        if (pricehistoryTable != null)
            pricehistoryTable.setItems(stock.getStockPriceHistory());
        if (companylists != null)
            companylists.setItems(stock.fillcompanytaple());
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
        if (exportcompany != null)
            exportcompany.setCellValueFactory(new PropertyValueFactory<>("company2"));
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
                balance.setText("" + account.getBalance() + " $");
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

        if (hotstocks != null) {
            hotstocks.setItems(stock.returnList());
            stock.RestoreData();
        }
        if (StocksMarket != null) {
            StocksMarket.setItems(stock.returnList());
            stock.RestoreData();
        }
        if (hotstocks != null) hotstocks.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> event.consume());
        if (hotstocks != null) hotstocks.lookupAll(".scroll-bar").forEach(scrollBar -> scrollBar.setVisible(false));

        if (hotbonds != null) {
            hotbonds.setItems(bond.getBondData());
            bond.RefreshBondList();
        }
        if (hotbonds != null) hotbonds.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> event.consume());
        if (hotbonds != null) hotbonds.lookupAll(".scroll-bar").forEach(scrollBar -> scrollBar.setVisible(false));
        if (changem != null) {

            changem.setCellValueFactory(cellData -> {
                int index = cellData.getTableView().getItems().indexOf(cellData.getValue());
               return new SimpleStringProperty(changemList.get(index));
            });
            for (int i = 0; i < changemList.size(); i++)
                changem.getTableView().getItems().add(changemList.get(i));
        }

    }

    private void updateDateTimeLabel() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMM d, yyyy\nhh:mm:ss a");
        String formattedDateTime = LocalDateTime.now().format(formatter);
        if (datee != null) {
            datee.setText(formattedDateTime);
        }
        if (account.getUsername() != null) {
            if(welcomemes!=null)welcomemes.setText("Welcome " + account.getUsername());
        }
       /* if (hotbonds != null) {
            hotbonds.setItems(bond.getBondData());
           // bond.RefreshBondList();
        }*/
        if(StocksMarket != null){
        DataShow selectedStock = StocksMarket.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            String selectedName = selectedStock.getCompany();
            testcompany.setText(selectedName);
        }}
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
    private void Stockspressed() {
        menuclose();
        yourbonds.setVisible(false);
        welcomemes.setVisible(false);
        showbalance.setVisible(false);
        hidebalance.setVisible(false);
        titleslabel.setVisible(false);
        balancetit.setVisible(false);
        balance.setVisible(false);
        hotbonds.setVisible(false);
        viplevel.setVisible(false);
        hotstocks.setVisible(false);
        hotstockstit.setVisible(false);
        sellb.setVisible(false);
        hotbondstit.setVisible(false);
        historyTable.setVisible(false);
        amount.setVisible(false);
        depositb.setVisible(false);
        withdraw.setVisible(false);
        userStockList.setVisible(false);
        userBondList.setVisible(false);
        TranslateTransition tapsawp = new TranslateTransition();
        tapsawp.setDuration(Duration.seconds(0.4));
        tapsawp.setNode(swaptaps);
        tapsawp.setToX(204);
        tapsawp.play();
        swaptaps.setTranslateX(0);
        titleslabel.setTranslateX(0);
        tapsawp.setOnFinished((ActionEvent e) -> {
            StocksMarket.setVisible(true);
            titleslabel.setText("Stocks Market");
            titleslabel.setVisible(true);
        });
    }

    @FXML
    private void Optionspressed() {
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
    private void Dashboaedpressed() {
        menuclose();
        yourbonds.setVisible(false);
        userStockList.setVisible(false);
        messages.setVisible(false);
        StocksMarket.setVisible(false);
        titleslabel.setVisible(false);
        amount.setVisible(false);
        depositb.setVisible(false);
        withdraw.setVisible(false);
        userBondList.setVisible(false);
        sellb.setVisible(false);
        TranslateTransition tapsawp = new TranslateTransition();
        tapsawp.setDuration(Duration.seconds(0.4));
        tapsawp.setNode(swaptaps);
        tapsawp.setToX(0);
        tapsawp.play();
        swaptaps.setTranslateX(0);
        tapsawp.setOnFinished((ActionEvent e) -> {
            viplevel.setTranslateX(170);
            viplevel.setTranslateY(0);
            viplevel.setText("VIP Level");
            viplevel.setVisible(true);
            welcomemes.setVisible(true);
            showbalance.setVisible(true);
            hotbondstit.setVisible(true);
            hidebalance.setVisible(true);
            balancetit.setVisible(true);
            balance.setVisible(true);
            hotstocks.setVisible(true);
            hotstockstit.setVisible(true);
            hotbonds.setVisible(true);
            viplevel.setVisible(true);
            historyTable.setVisible(false);
        });
    }

    @FXML
    public void BondsPressed(ActionEvent event) throws IOException {
        menuclose();
        userStockList.setVisible(false);
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
    private void setImmenuent(MouseEvent event) {
        immenu.setImage(menui);
        Menu.setTextFill(Color.color(0.09411764705882353,0.10196078431372549,0.12549019607843137));
    }
    @FXML
    private void setImmenuex(MouseEvent event) {
        immenu.setImage(menuclosei);
        Menu.setTextFill(Color.color(0.6392156862745098,0.6392156862745098,0.6392156862745098));
    }
    @FXML
    public void yourStocks(ActionEvent event) throws IOException {
        stock.refreshUserStockList();
        bond.refreshUserBondList();
        menuclose();
        amount.setText("");
        messages.setVisible(false);
        amount.setVisible(false);
        depositb.setVisible(false);
        withdraw.setVisible(false);
        StocksMarket.setVisible(false);
        titleslabel.setVisible(false);
        welcomemes.setVisible(false);
        showbalance.setVisible(false);
        hotbondstit.setVisible(false);
        hidebalance.setVisible(false);
        balancetit.setVisible(false);
        balance.setVisible(false);
        hotstocks.setVisible(false);
        hotstockstit.setVisible(false);
        hotbonds.setVisible(false);
        viplevel.setVisible(false);
        historyTable.setVisible(false);
        TranslateTransition tapsawp = new TranslateTransition();
        tapsawp.setDuration(Duration.seconds(0.4));
        tapsawp.setNode(swaptaps);
        tapsawp.setToX(0);
        tapsawp.play();
        swaptaps.setTranslateX(0);
        tapsawp.setOnFinished((ActionEvent e) -> {

            userBondList.setVisible(true);
            messages.setTranslateY(220);
            yourbonds.setTranslateY(20);
            yourbonds.setTranslateX(170);
            messages.setVisible(true);
            sellb.setVisible(true);
            yourbonds.setVisible(true);
            viplevel.setTranslateX(-495);
            viplevel.setTranslateY(20);
            viplevel.setText("Your Stocks");
            viplevel.setVisible(true);
            titleslabel.setText("Your Securities");
            titleslabel.setTranslateX(20);
            titleslabel.setVisible(true);
            userStockList.setVisible(true);
            amount.setTranslateY(230);
            amount.setPromptText("Amount to sell");
            amount.setVisible(true);
        });
    }
    @FXML
    private void mainscene(ActionEvent event) throws IOException {
        Dashboaedpressed();
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
        menuclose();
        amount.setText("");
        sellb.setVisible(false);
        userStockList.setVisible(false);
        messages.setVisible(false);
        StocksMarket.setVisible(false);
        titleslabel.setVisible(false);
        welcomemes.setVisible(false);
        hotbondstit.setVisible(false);
        hotstocks.setVisible(false);
        userBondList.setVisible(false);
        hotstockstit.setVisible(false);
        hotbonds.setVisible(false);
        yourbonds.setVisible(false);
        viplevel.setVisible(false);
        historyTable.setVisible(false);
        TranslateTransition tapsawp = new TranslateTransition();
        tapsawp.setDuration(Duration.seconds(0.4));
        tapsawp.setNode(swaptaps);
        tapsawp.setToX(0);
        tapsawp.play();
        swaptaps.setTranslateX(0);
        tapsawp.setOnFinished((ActionEvent e) -> {
            messages.setText("");
            messages.setTranslateY(0);
            messages.setVisible(true);
            amount.setTranslateY(0);
            amount.setPromptText("Amount $");
            amount.setVisible(true);
            depositb.setVisible(true);
            withdraw.setVisible(true);
            balancetit.setVisible(true);
            balance.setVisible(true);
            hidebalance.setVisible(true);
            showbalance.setVisible(true);
            titleslabel.setTranslateX(-20);
            titleslabel.setText("Deposite and Withdraw");
            titleslabel.setVisible(true);
        });
    }

    @FXML
    private void deposite() {
        admin.addRequests(Float.parseFloat(amount.getText()), "deposite");
        messages.setText("Your Request has been sent to the admin successfully");
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished((ActionEvent e) -> {
            messages.setText("");
            messages.setVisible(true);
        });
        delay.play();
    }

    @FXML
    private void withdrawal() {
        if (Float.parseFloat(amount.getText()) > account.getBalance()) {
            messages.setText("You have insufficient balance");
        } else {
            admin.addRequests(Float.parseFloat(amount.getText()), "withdrawal");
            messages.setText("Your Request has been sent to the admin successfully");
        }
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished((ActionEvent e) -> {
            messages.setText("");
            messages.setVisible(true);
        });
        delay.play();
    }

    @FXML
    private void transactionhistoryscene(ActionEvent event) throws IOException {
        admin.refreshTransactionHistory();
        menuclose();
        userStockList.setVisible(false);
        yourbonds.setVisible(false);
        sellb.setVisible(false);
        messages.setVisible(false);
        amount.setVisible(false);
        depositb.setVisible(false);
        withdraw.setVisible(false);
        StocksMarket.setVisible(false);
        titleslabel.setVisible(false);
        userBondList.setVisible(false);
        welcomemes.setVisible(false);
        showbalance.setVisible(false);
        hotbondstit.setVisible(false);
        hidebalance.setVisible(false);
        balancetit.setVisible(false);
        balance.setVisible(false);
        hotstocks.setVisible(false);
        hotstockstit.setVisible(false);
        hotbonds.setVisible(false);
        viplevel.setVisible(false);
        historyTable.setTranslateX(-80);
        TranslateTransition tapsawp = new TranslateTransition();
        tapsawp.setDuration(Duration.seconds(0.4));
        tapsawp.setNode(swaptaps);
        tapsawp.setToX(0);
        tapsawp.play();
        swaptaps.setTranslateX(0);
        tapsawp.setOnFinished((ActionEvent e) -> {
            titleslabel.setText("Your Transactions History");
            titleslabel.setTranslateX(-28);
            titleslabel.setVisible(true);
            historyTable.setVisible(true);

        });
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
                    messages.setText("Sold Successfully");
                } else messages.setText("Not Enough Amount");
            } else messages.setText("Sorry, The Market is Closed");
        }
        PauseTransition delay = new PauseTransition(Duration.seconds(5));
        delay.setOnFinished((ActionEvent e) -> {
            messages.setText("");
            messages.setVisible(true);
        });
        delay.play();
    }



    @FXML
    public void sellBond() {
      /*   DataShow selectedBond = userBondList.getSelectionModel().getSelectedItem();
         if (selectedBond != null) {
             String selectedName = selectedBond.getCompany();
             if (admin.marketOpenOrClose()) {
                 if (bond.SellBond(Integer.parseInt(amount.getText()), selectedName,0)) {
                     bond.refreshUserBondList();
                     account.updateBalance();
                     bond.RefreshBondList();
                     SellMessage.setText("Sold Successfully");
                 } else SellMessage.setText("Not enough amount");
             } else buyMessage.setText("Sorry, market is closed");
         }
         */

    }

    @FXML
    public void searchStock() {
        LocalDateTime currentDateTime = LocalDateTime.now(); // Get the current date and time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Define the desired format
        String formattedDateTime = currentDateTime.format(formatter); // Format the date and time
        if (stock.fillDateTable(testcompany.getText())) {
            stock.getPriceList(testcompany.getText(), formattedDateTime);
            priceGraph.getData().clear();
            float max = 0;
            float min = stock.getPriceList().get(0);
            XYChart.Series series = new XYChart.Series();
            for (int i = 0; i < stock.getPriceList().size(); i++) {
                series.getData().add(new XYChart.Data(stock.getTimeList().get(i), stock.getPriceList().get(i)));
                if (max < stock.getPriceList().get(i)) max = stock.getPriceList().get(i);
                if (min > stock.getPriceList().get(i)) min = stock.getPriceList().get(i);
            }
            if (priceAxis != null) {
                if (stock.getPriceList().size() > 0) {
                    priceAxis.setAutoRanging(false); // Disable automatic scaling
                    priceAxis.setLowerBound((int) (min - 2)); // Set your desired lower bound
                    priceAxis.setUpperBound((int) (max + 2));
                    priceAxis.setTickUnit(1);
                }//
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
            XYChart.Series series = new XYChart.Series();
            float max = 0;
            float min = stock.getPriceList().get(0);
            for (int i = 0; i < stock.getPriceList().size(); i++) {
                series.getData().add(new XYChart.Data(stock.getTimeList().get(i), stock.getPriceList().get(i)));
                if (max < stock.getPriceList().get(i)) max = stock.getPriceList().get(i);
                if (min > stock.getPriceList().get(i)) min = stock.getPriceList().get(i);
            }
            if (priceAxis != null) {
                if (stock.getPriceList().size() > 0) {
                    priceAxis.setLowerBound((int) (min - 2)); // Set your desired lower bound
                    priceAxis.setUpperBound((int) (max + 2));
                    priceAxis.setTickUnit(1);
                }//
            }
            priceGraph.setCreateSymbols(false);
            if (priceGraph != null)
                priceGraph.getData().add(series);


        }
    }

    @FXML
    public void Exportcsvscene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Exportcsv.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void export() {
        DataShow selectedcompany = companylists.getSelectionModel().getSelectedItem();
        String company2 = selectedcompany.getCompany2();
        FileChooser fileChooser = new FileChooser();
        Stage primaryStage = new Stage();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showSaveDialog(primaryStage);
        if (selectedFile != null) {
            exportCSV(selectedFile, company2);
        }
    }

    public void exportCSV(File file, String company) {
        stock.fillStockHistoryData(company, true, file);

    }
    @FXML
    private void ahmeds(ActionEvent event) throws IOException {
        stock.fillnotfilist();
        Parent root = FXMLLoader.load(getClass().getResource("notificationscene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        System.out.println("--------------------------> "+stock.getNumberofnotifications());
        stock.SeenNotifications();
        System.out.println("--------------------------> "+stock.getNumberofnotifications());
    }
    public void zopr(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("userMenue.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}










