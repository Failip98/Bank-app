package app.myAccount;
import app.db.DB;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.sound.midi.Soundbank;

public class myAccountController {
    @FXML private Button btn_sallery;
    @FXML private Button btn_deliteAccount;
    @FXML private TextField TextField_delitAccount_nr;

    @FXML
    private void initialize(){
        System.out.println("initialize account");
        btn_sallery.setOnAction( e -> getSallery());
        btn_deliteAccount.setOnAction( e -> delitMyConto());
    }

    private void getSallery(){
        double d = 25000;
        //Kalla på newTransaction
        DB.givSallery(d);
    }

    private void newTransaction(double amount){
        double d = 10000;
        DB.addToTrnsaktion(LoginController.getUser().getPerson_id(),"55","99",d);
    }

    private void delitMyConto(){
        String account_nr = TextField_delitAccount_nr.getText();
        DB.delitMyAccount(LoginController.getUser().getPerson_id(),account_nr);
    }



}
