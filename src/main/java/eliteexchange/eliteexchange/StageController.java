package eliteexchange.eliteexchange;
import ApplicationElite.Account;
import ApplicationElite.Securities;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

public class StageController implements Initializable {

    Account account =new Account();
    Securities stock=new Securities();
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button delete;
    @FXML
    private ComboBox<String> stocklist;

    @FXML
    private Button add;

    @FXML
    private TextField companyName;

    @FXML
    private TextField numberOfStocks;

    @FXML
    private Button BuyStock;

    @FXML
    private Button DeleteStock;

    @FXML
    private Button Sellstock;

    @FXML
    private Button addStock;

    @FXML
    Label messagelabel;

    @FXML
    private Button back;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmpassword;

    @FXML
    private Button login;
    private double x,y;
    @FXML
    private Button signup;
    @FXML
    private Button signupConfirm;
    @FXML
    private Button loginConfirm;
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
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void SignupPressed(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void signupConfirmPressed() {
        account.setUserName(username.getText());
        account.setPassword(password.getText());
        account.setCheckPassword(confirmpassword.getText());
        if(account.CheckMatchPassword()&&password.getText().length()>=3){

            messagelabel.setText("SignUp success");
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
        if(account.CheckLoginData() && account.userOrAdmin().equals("user")){
            stock.RestoreData();
            Parent root = FXMLLoader.load(getClass().getResource("userMenue.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else
        if(account.CheckLoginData() && account.userOrAdmin().equals("admin")&& account.admin_log_in_out().equals("no")){
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
        Parent root = FXMLLoader.load(getClass().getResource("Addscene.fxml"));

        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void confirmAdd(ActionEvent event) {
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                stock.RestoreData2();
                return change;
            }
            stock.RestoreData2();
            return null;


        };
        TextFormatter<String> formatter = new TextFormatter<>(filter);
        numberOfStocks.setTextFormatter(formatter);
        stock.AddStock(companyName.getText(),Integer.parseInt(numberOfStocks.getText()));



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
    void DeleteStock(ActionEvent event) throws IOException {



        Parent root = FXMLLoader.load(getClass().getResource("deleteScene.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();


    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        if (stocklist != null) {
            for(int i=2;i<stock.stockList1.length;i++)
                stocklist.getItems().add(stock.stockList1[i]);
        }
    }
    @FXML
    void deleteButton(ActionEvent event) {
        System.out.println(stocklist.getValue());
        stock.DeleteStock(stocklist.getValue());
        stock.RestoreData2();
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

}








