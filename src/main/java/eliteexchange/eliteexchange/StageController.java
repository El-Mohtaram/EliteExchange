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
   Admin admin=new Admin();
    Account account =new Account();
    Stock stock=new Stock();
    private Stage stage;
    private Scene scene;
    @FXML
    private TableView<DataShow> Addtable;
    @FXML
    private TableView<DataShow> requestsTable;

    @FXML
    private TableColumn<DataShow, String> requestColumn;

    @FXML
    private TableColumn<DataShow, String> company;

    @FXML
    private TableColumn<DataShow, Float> startPrice;
    @FXML
    private TableColumn<DataShow, Float> numberofStocks;
    @FXML
    private TextField startprice;
    @FXML
    private TextField amount;

    @FXML
    private ComboBox<String> userlist;

    @FXML
    private TextField companyName;

    @FXML
    private TextField numberOfStocks;
    @FXML
    private Label requestMessage,balance;

    @FXML
    Label messagelabel,datee,signedup1;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmpassword;


    private double x,y;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(balance!=null)
        balance.setText("Balance: "+account.getBalance()+"$");
        if(company!=null)
        company.setCellValueFactory(new PropertyValueFactory<>("company"));
        if(startPrice!=null)
        startPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        if(numberofStocks!=null)
        numberofStocks.setCellValueFactory(new PropertyValueFactory<>("number"));
        if(Addtable!=null)
        Addtable.setItems(stock.returnList());
        if(requestColumn!=null)
        requestColumn.setCellValueFactory(new PropertyValueFactory<>("requests"));
        if(requestsTable!=null)
        requestsTable.setItems(admin.returnList());
    
        if(userlist != null){
            for (int i = 0; i <Admin.userslist.size() ; i++) {
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
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void BackbuttonPressed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainScene.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void LoginPressed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("mainscene.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add(Elite.css);
        stage.show();
    }

    @FXML
    private void SignupPressed(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
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
        if(account.CheckMatchPassword()&&password.getText().length()>=3){
            signedup1.setText("Signed up success");
            account.ImportUserData();
        }
        else {
            messagelabel.setText(account.SignUpMessages());

        }
    }
    @FXML
    private void LoginConfirmPressed(ActionEvent event) throws IOException {
        account.setUserName(username.getText());
        account.setPassword(password.getText());
        if(account.CheckLoginData() && account.userOrAdmin().equals("user")&&Account.BannedOrNot){
            messagelabel.setText("your account has been banned");
        }
       else if(account.CheckLoginData() && account.userOrAdmin().equals("user")){
            stock.RestoreData();
            Parent root = FXMLLoader.load(getClass().getResource("userMenue.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else
        if(account.CheckLoginData() && account.userOrAdmin().equals("admin")&& account.admin_log_in_out().equals("no")){
            Admin.createuserslist();
            account.adminSwitch();
            stock.RestoreData();
            Parent root = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));

            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else if(account.CheckLoginData() && account.userOrAdmin().equals("admin")&& account.admin_log_in_out().equals("yes"))
            messagelabel.setText("Admin already logged in!");
        else
        {
            messagelabel.setText(account.LoginMessages());
        }
    }
    @FXML
    void AddstockScene(ActionEvent event) throws IOException {
  
        Parent root = FXMLLoader.load(getClass().getResource("AddScene.fxml")); 
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stock.RestoreData();
        stage.show();
       
    }
    @FXML
    void addStock(ActionEvent event) {
        stock.addStock(companyName.getText(),Integer.parseInt(numberOfStocks.getText()),Float.parseFloat(startprice.getText()));
        stock.RestoreData();
        Addtable.setItems(stock.returnList());
    }

    @FXML
    void BackPressed(ActionEvent event) throws IOException {
        if(account.userOrAdmin().equals("user"))
        {
            Parent root = FXMLLoader.load(getClass().getResource("userMenue.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else
        {
            Parent root = FXMLLoader.load(getClass().getResource("AdminMenu.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
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
    private void dragwindow (MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setY(event.getScreenY() - y);
        stage.setX(event.getScreenX() - x);
    }
    @FXML
    private void presswindow (MouseEvent event) {
        x = event.getSceneX();
        y =event.getSceneY();
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
    void MarketPressed(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("Market.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
stock.RestoreData();
    }
    @FXML
    void usermanagementscene(ActionEvent event) throws IOException{
        Admin.createuserslist();
        Parent root = FXMLLoader.load(getClass().getResource("Usermanagement.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            stock.RestoreData();
    }
    @FXML
    private void blockuser(){
        Admin.createuserslist();
        System.out.println(userlist.getValue());
        Admin.blockuser(userlist.getValue());
        for (int i = 0; i <Admin.userslist.size() ; i++) {
            userlist.getItems().set(i,Admin.userslist.get(i));
        }
    }
    @FXML
    private void unblockuser(){
        Admin.createuserslist();
        System.out.println(userlist.getValue());
        Admin.unblockuser(userlist.getValue());
        for (int i = 0; i <Admin.userslist.size() ; i++) {
            userlist.getItems().set(i,Admin.userslist.get(i));
        }
    }
    @FXML
    private void deleteusers(){
        Admin.createuserslist();
        System.out.println(userlist.getValue());
        Admin.deleteuser(userlist.getValue());
        for (int i = 0; i <Admin.userslist.size() ; i++) {
            userlist.getItems().set(i,Admin.userslist.get(i));
        }
    }
    @FXML
    private void DandW(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("WDscene.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
    }
    @FXML
    private void deposite()
    {
admin.addRequests(Float.parseFloat(amount.getText()), "deposite");
requestMessage.setText("Your Request has been sent to the admin successfully");
    }
    @FXML
    private void withdrawal()
    {
admin.addRequests(Float.parseFloat(amount.getText()), "withdrawal");
requestMessage.setText("Your Request has been sent to the admin successfully");
    }
    @FXML
    private void adminRequests(ActionEvent event) throws IOException
    {
        Parent root = FXMLLoader.load(getClass().getResource("RequestScene.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        admin.RestoreData();
    }
    @FXML
    public void accept()
    {
        
        DataShow selectedRequest = requestsTable.getSelectionModel().getSelectedItem();
        if (selectedRequest!= null) {
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
    public void refuse()
    {
        
        DataShow selectedRequest = requestsTable.getSelectionModel().getSelectedItem();
        if (selectedRequest!= null) {
            String selectedName = selectedRequest.getRequests();
            System.out.println(selectedName);
        admin.deleteRequest(selectedName);
        admin.transaction(selectedName,1);
        int selectedID = requestsTable.getSelectionModel().getSelectedIndex();
        requestsTable.getItems().remove(selectedID);
        admin.RestoreData();
    }
}
@FXML
    private void transactionhistoryscene(ActionEvent event) throws IOException {
    Parent root = FXMLLoader.load(getClass().getResource("transactionhistory.fxml"));
    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
    scene = new Scene(root);
    stage.setScene(scene);
    stage.show();

}
}










