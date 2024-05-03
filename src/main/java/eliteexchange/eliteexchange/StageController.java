package eliteexchange.eliteexchange;
import ApplicationElite.Account;
import ApplicationElite.Admin;
import ApplicationElite.Securities;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.EventObject;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

import javafx.util.Duration;
import org.controlsfx.control.action.Action;

import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;

public class StageController implements Initializable {

    Account account =new Account();
    Securities stock=new Securities();
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label datee;
    @FXML
    private Button delete;
    @FXML
    private Button deleteuserbutton;
    @FXML
    private Button exit;
    @FXML
    private ComboBox<String> stocklist,userlist;

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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> updateDateTimeLabel())
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
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
    @FXML
    void usermanagementscene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Usermanagement.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void deleteButton(ActionEvent event) {
        System.out.println(stocklist.getValue());
       stock.DeleteStock(stocklist.getValue());
        stock.RestoreData2();
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
    private void deleteusers(){
        System.out.println(userlist.getValue());
        Admin.deleteuser(userlist.getValue());
    }
    @FXML
    private void printdate(){
        while(true){
            String cd= new SimpleDateFormat("yyyy.MM.dd HH.mm.ss").format(new Date());
            datee.setText(cd);
        }
    }

}








