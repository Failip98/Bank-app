package app.home;

import app.Main;
import app.account.AccountController;
import app.transaction.TransactionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.IOException;

public class HomeController {


    @FXML private Button btn_loggin;
    @FXML private Button btn_account;
    @FXML private Button btn_myAccounts;
    @FXML
    void initialize(){
        // load accounts from db using LoginController.user.getId() and display them
        btn_loggin.setOnAction(e -> goToLoggin());
        btn_account.setOnAction(e -> goToAccount());
        btn_myAccounts.setOnAction(e -> goToMyAccount());
    }
    @FXML
    void goToMyAccount() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/myaccount/myAccount.fxml"));
            nextSage(loader, "main");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    void goToAccount() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/account/account.fxml"));
            nextSage(loader, "main");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    @FXML
    void goToLoggin(){
        try {
            FXMLLoader loader = new FXMLLoader( getClass().getResource( "/app/login/login.fxml" ) );
            nextSage(loader, "loggin");
        }catch (IOException e){
            e.printStackTrace();
        }

    }


    private void nextSage (FXMLLoader loader, String stage) throws IOException{
        try {
            Parent fxmlInstance = loader.load();
            Scene scene;
            if (stage != "loggin"){
                scene = new Scene( fxmlInstance, 800, 600 );
            }else {
                scene = new Scene( fxmlInstance, 600, 400 );
            }

            // Make sure that you display "the correct account" based on which one you clicked on
//            AccountController controller = loader.getController();
//            controller.setAccount(accountFromDB);

            // If you don't want to have/use the static variable Main.stage
//        Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
            Main.stage.setScene(scene);
            Main.stage.show();

        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
