package eliteexchange.eliteexchange;

import ApplicationElite.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
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
        if (marketStatues != null) {
            marketStatues.setToggleGroup(aaa);
            marketStatues.setOnAction(this::changeMarketStatues);
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
    void BackPressed(MouseEvent event) throws IOException {
            Parent root = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            String Admincss = getClass().getResource("AdminMenu.css").toExternalForm();
            scene.getStylesheets().add(Admincss);
            stage.setScene(scene);
            stage.show();
    }

    @FXML
    void RefreshRequestsScreen(MouseEvent event) throws IOException {
        RotateTransition RefreshA = new RotateTransition();
        RefreshA.setDuration(Duration.seconds(0.7));
        RefreshA.setNode(RefreshIcon);
        RefreshA.setToAngle(angle += 360);
        RefreshA.play();
        System.out.println(event);
        Admin.createuserslist();
        requestsTable.setItems(admin.returnList());
        admin.RestoreData();
    }

    @FXML
    void RefreshUserMangementScreen(MouseEvent event) throws IOException {
        RotateTransition RefreshA = new RotateTransition();
        RefreshA.setDuration(Duration.seconds(0.7));
        RefreshA.setNode(RefreshIcon);
        RefreshA.setToAngle(angle += 360);
        RefreshA.play();
        Admin.createuserslist();
        System.out.println(event);
        for(int i =0 ; i < Admin.userslist.size() ; i++)
        {
            userlist.getItems().add(Admin.userslist.get(i));
        }
        admin.RestoreData();
    }


    @FXML
    void AddstockScene(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("StockManagementScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        String StockManagementcss = getClass().getResource("StockManagementScene.css").toExternalForm();
        stage.getScene().getStylesheets().add(StockManagementcss);
        stock.RestoreData();
        stage.show();

    }
    @FXML
    void addStock(ActionEvent event) {
        stock.addStock(companyName.getText(), Integer.parseInt(numberOfStocks.getText()), Float.parseFloat(startprice.getText()));
        stock.RestoreData();
        Addtable.setItems(stock.returnList());
    }

    @FXML
    void deleteStock(ActionEvent event) {
        // int selectedID = Addtable.getSelectionModel().getSelectedIndex();
        // Addtable.getItems().remove(selectedID);
        DataShow selectedStock = Addtable.getSelectionModel().getSelectedItem();
        if (selectedStock != null) {
            String selectedName = selectedStock.getCompany();
            System.out.println("Selected stock name: " + selectedName);
            stock.DeleteStock(selectedName);
            int selectedID = Addtable.getSelectionModel().getSelectedIndex();
            Addtable.getItems().remove(selectedID);
        }
    }

    @FXML
    void usermanagementscene(ActionEvent event) throws IOException {
        System.out.println(event);
        Admin.createuserslist();
        Parent root = FXMLLoader.load(getClass().getResource("Usermanagement.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String UserManagementcss = getClass().getResource("UserManagement.css").toExternalForm();
        scene.getStylesheets().add(UserManagementcss);
        stage.setScene(scene);
        stage.show();
        stock.RestoreData();
    }

    @FXML
    private void blockuser() throws IOException {
        Admin.createuserslist();
        Admin.blockuser(userlist.getValue());
        for (int i = 0; i < Admin.userslist.size(); i++) {
            userlist.getItems().set(i, Admin.userslist.get(i));
        }
        System.out.println(userlist.getValue());
    }

    @FXML
    private void unblockuser() throws IOException {
        Admin.createuserslist();
        Admin.unblockuser(userlist.getValue());
        for (int i = 0; i < Admin.userslist.size(); i++) {
            userlist.getItems().set(i, Admin.userslist.get(i));
        }
        System.out.println(userlist.getValue());
    }

    @FXML
    private void deleteusers() throws IOException {
        Admin.createuserslist();
        System.out.println(userlist.getValue());
        Admin.deleteuser(userlist.getValue());
        for (int i = 0; i < Admin.userslist.size(); i++) {
            userlist.getItems().set(i, Admin.userslist.get(i));
        }
    }

    @FXML
    private void adminRequests(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("RequestScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String Requestscss = getClass().getResource("Requests.css").toExternalForm();
        scene.getStylesheets().add(Requestscss);
        stage.setScene(scene);
        stage.show();
        admin.RestoreData();
    }

    @FXML
    public void accept() {
        DataShow selectedRequest = requestsTable.getSelectionModel().getSelectedItem();
        if (selectedRequest != null) {
            String selectedName = selectedRequest.getRequests();
            System.out.println(selectedName);
            admin.acceptRequest(selectedName);
            admin.deleteRequest(selectedName);
            int selectedID = requestsTable.getSelectionModel().getSelectedIndex();
            requestsTable.getItems().remove(selectedID);
            admin.RestoreData();
        }
    }

    @FXML
    public void refuse() {

        DataShow selectedRequest = requestsTable.getSelectionModel().getSelectedItem();
        if (selectedRequest != null) {
            String selectedName = selectedRequest.getRequests();
            System.out.println(selectedName);
            admin.deleteRequest(selectedName);
            int selectedID = requestsTable.getSelectionModel().getSelectedIndex();
            requestsTable.getItems().remove(selectedID);
            admin.transaction(selectedName, 1);
            admin.RestoreData();
        }
    }

    @FXML
    private void changeMarketStatues(ActionEvent event) {
        if (marketStatues.isSelected()) {
            marketStatues.setText("Close Market");
            admin.openMarket();
        } else {
            marketStatues.setText("Open Market");
            admin.closeMarket();
        }
    }

    @FXML
    public void BondsManagement(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("BondsManagementScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String BondsManagementcss = getClass().getResource("BondsManagementScene.css").toExternalForm();
        scene.getStylesheets().add(BondsManagementcss);
        stage.setScene(scene);
        bond.RefreshBondList();
        stage.show();
    }

    @FXML
    void addBond(ActionEvent event) {
        bond.addBonds(companyName.getText(), Float.parseFloat(value.getText()), Integer.parseInt(numberOfStocks.getText()), Float.parseFloat(yield.getText()), Integer.parseInt(ValidUntil.getText()));
        bond.RefreshBondList();
        bondsTable.setItems(Bonds.getBondData());
        bond.RefreshBondList();
    }

    @FXML
    public void deleteBond() {
        DataShow selectedBond = bondsTable.getSelectionModel().getSelectedItem();
        if (selectedBond != null) {
            String selectedName = selectedBond.getCompany();
            System.out.println("Selected stock name: " + selectedName);
            bond.deleteBonds(selectedName);
            int selectedID = bondsTable.getSelectionModel().getSelectedIndex();
            bondsTable.getItems().remove(selectedID);

        }
    }

    @FXML
    void LogoutButtonPressed(ActionEvent event) throws IOException {
        account.adminSwitch();
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String MainScenecss = getClass().getResource("menu.css").toExternalForm();
        scene.getStylesheets().add(MainScenecss);
        stage.setScene(scene);
        stage.show();
    }

}
