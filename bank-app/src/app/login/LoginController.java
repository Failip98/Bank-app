package app.login;
import app.Entities.User;
import app.Main;
import app.db.DB;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.io.IOException;

public class LoginController {

    private static User user = null;
    public static User getUser() { return user; }

    @FXML private TextField textField_id;
    @FXML private TextField textField_password;
    @FXML private PasswordField PasswordField_password;
    @FXML private Button btn_loggin;
    @FXML private Label Label_error_id;
    @FXML private Label Label_error_password;

    public TextField getIdInput(){
        return textField_id;
    }

    public PasswordField getPasswordInput(){
        return PasswordField_password;
    }

    @FXML
    private void initialize() {
        btn_loggin.setOnAction( e -> loggInVerifier());
    }

    private void loggInVerifier() {
        Label_error_id.setText(null);
        Label_error_password.setText(null);
        if (textField_id.getText() == null || textField_id.getText().trim().isEmpty() ||
                PasswordField_password.getText() == null || PasswordField_password.getText().trim().isEmpty()){
            Label_error_id.setText("Fill in information");
            Label_error_password.setText("Fill in information");
            cleartextinput();
        }else {
            String person_id = textField_id.getText();
            String password = PasswordField_password.getText();
            user = DB.getMatchingUser(person_id, password);
            if(user != null){
                goToHome();
            }else {
                Label_error_id.setText("Fill in proper Id");
                Label_error_password.setText("Fill in proper Password");
                cleartextinput();
            }
        }
    }

    public void cleartextinput(){
        textField_id.clear();
        PasswordField_password.clear();
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
