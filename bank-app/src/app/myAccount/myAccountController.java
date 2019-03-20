package app.myAccount;
import app.db.DB;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class myAccountController {
    @FXML
    private Button btn_sallery;

    @FXML
    private void initialize(){
        System.out.println("initialize account");
        btn_sallery.setOnAction( e -> newTransaction());
    }

    private void getSallery(){
        double d = 25000;
        //Kalla på newTransaction
        DB.givSallery(d);
    }

    private void newTransaction(){
        double d = 10000;
        DB.addToTrnsaktion(LoginController.getUser().getPerson_id(),"55","99",d);
    }

    private void delitMyConto(){
        String name = "55";
        String account_nr = "99";
        DB.delitMyAccount(LoginController.getUser().getPerson_id(),name,account_nr);
    }



}
