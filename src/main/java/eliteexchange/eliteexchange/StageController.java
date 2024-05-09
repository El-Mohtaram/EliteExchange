package eliteexchange.eliteexchange;

import ApplicationElite.Account;
import ApplicationElite.Admin;
import ApplicationElite.DataShow;
import ApplicationElite.Stock;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;

public class StageController implements Initializable {
    Admin admin = new Admin();
    Account account = new Account();
    Stock stock = new Stock();
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
    private TableColumn<DataShow, Float> startPrice;
    @FXML
    private TableColumn<DataShow, Float> numberofStocks;
    @FXML
    private TextField startprice;
    @FXML
    private TextField amount;

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
    private Label requestMessage, balance, buyMessage;

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
    private Button signupConfirm;
    @FXML
    private Button loginConfirm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (marketStatues != null) {
            marketStatues.setToggleGroup(aaa);
            marketStatues.setOnAction(this::changeMarketStatues);
        }
        if (company != null)
            company.setCellValueFactory(new PropertyValueFactory<>("company"));
        if (startPrice != null)
            startPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
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
//        Timeline timeline = new Timeline(
//                new KeyFrame(Duration.seconds(1), event -> updateDateTimeLabel())
//        );
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();
        // Add shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            account.adminSwitch();
        }));
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
        stage.show();
    }

    @FXML
    void BackbuttonPressed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
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
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            String usercss = getClass().getResource("usermenu.css").toExternalForm();
            stage.getScene().getStylesheets().add(usercss);
            stage.show();
        } else if (account.CheckLoginData() && account.userOrAdmin().equals("admin") && account.admin_log_in_out().equals("no")) {
            Admin.createuserslist();
            account.adminSwitch();
            stock.RestoreData();
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
    void AddstockScene(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("AddScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
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
    private void exit(MouseEvent event) throws IOException {
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
        account.adminSwitch();
        System.exit(0);
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
    void MarketPressed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Market.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stock.RestoreData();
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
    void RefreshScreen(MouseEvent event) throws IOException {
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
        System.out.println(userlist.getValue());
        Admin.blockuser(userlist.getValue());
        for (int i = 0; i < Admin.userslist.size(); i++) {
            userlist.getItems().set(i, Admin.userslist.get(i));
        }
    }

    @FXML
    private void unblockuser() throws IOException {
        Admin.createuserslist();
        System.out.println(userlist.getValue());
        Admin.unblockuser(userlist.getValue());
        for (int i = 0; i < Admin.userslist.size(); i++) {
            userlist.getItems().set(i, Admin.userslist.get(i));
        }
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
    private void DandW(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("WDscene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    @FXML
    private void adminRequests(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("RequestScene.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
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
    public void yourStocks(ActionEvent event) throws IOException {
        stock.refreshUserStockList();
        Parent root = FXMLLoader.load(getClass().getResource("UserStocks.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}










