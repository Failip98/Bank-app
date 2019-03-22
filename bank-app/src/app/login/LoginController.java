package app.login;


import app.Entities.User;
import app.Main;
import app.db.DB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.awt.*;
import java.io.IOException;

public class LoginController {

    // Use this in other Controllers to get "the currently logged in user".
    private static User user = null;
    public static User getUser() { return user; }

    @FXML private TextField textField_id;
    @FXML private TextField textField_password;
    @FXML private Button btn_loggin;

    public TextField getIdInput(){
        return textField_id;
    }

    public TextField getPasswordInput(){
        return textField_password;
    }

    @FXML
    private void initialize() {
        System.out.println("initialize login");
        btn_loggin.setOnAction( e -> loggInVerifier());
    }

    private void loggInVerifier() {
        String person_id = textField_id.getText();
        String password = textField_password.getText();

        System.out.println(person_id);
        System.out.println(password);
        user = DB.getMatchingUser(person_id, password);
        if(user != null){
            goToHome();
        }else {
            System.out.println("ERROR LOGGIN FAIL");
        }
    }

    public void goToHome() {
        switchScene("/app/home/home.fxml");
    }

    private void switchScene(String pathname) {
        try {
            Parent bla = FXMLLoader.load(getClass().getResource(pathname));
            Scene scene = new Scene(bla, 600, 400);
            Main.stage.setScene(scene);
            Main.stage.show();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
