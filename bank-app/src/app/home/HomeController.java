package app.home;
import app.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.io.IOException;

public class HomeController {
    @FXML private Button btn_loggout;
    @FXML private Button btn_account;
    @FXML private Button btn_myAccounts;

    @FXML
    void initialize(){
        btn_loggout.setOnAction(e -> goToLoggOut());
        btn_account.setOnAction(e -> goToAccount());
        btn_myAccounts.setOnAction(e -> goToMyAccount());
    }

    private void goToMyAccount() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/myaccount/myAccount.fxml"));
        nextSage(loader, "main");
    }

    private void goToAccount() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/account/account.fxml"));
        nextSage(loader, "main");
    }

    private void goToLoggOut(){
        FXMLLoader loader = new FXMLLoader( getClass().getResource( "/app/login/login.fxml" ) );
        nextSage(loader, "loggin");
    }

    private void nextSage (FXMLLoader loader, String stage){
        try {
            Parent fxmlInstance = loader.load();
            Scene scene;
            if (stage != "loggin"){
                scene = new Scene( fxmlInstance, 1000, 600 );
            }else {
                scene = new Scene( fxmlInstance, 600, 400 );
            }
            Main.stage.setScene(scene);
            Main.stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
