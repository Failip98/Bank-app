package app.myAccount;
import app.db.DB;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.sound.midi.Soundbank;
import java.util.Random;

public class myAccountController {
    @FXML private Button btn_sallery;
    @FXML private Button btn_deliteAccount;
    @FXML private TextField TextField_delitAccount_nr;
    @FXML private Button btn_renameAccount;
    @FXML private TextField TextField_oldnameAccount_nr;
    @FXML private TextField TextField_newAccountname;
    @FXML private Button btn_newAccount;
    @FXML private TextField TextField_accountName;
    private String person_id = LoginController.getUser().getPerson_id();
    @FXML
    private void initialize(){
        System.out.println("initialize account");
        btn_sallery.setOnAction( e -> getSallery());
        btn_deliteAccount.setOnAction( e -> delitMyAccount());
        btn_renameAccount.setOnAction(e -> renameAccount());
        btn_newAccount.setOnAction(e -> createNewAccount());

    }

    private void getSallery(){
        double d = 25000;
        //Kalla på newTransaction
        DB.givSallery(d);
    }

    private void newTransaction(double amount){
        double d = 10000;
        DB.addToTrnsaktion(person_id,"55","99",d);
    }

    private void delitMyAccount(){
        String account_nr = TextField_delitAccount_nr.getText();
        DB.delitMyAccount(person_id,account_nr);
        TextField_delitAccount_nr.clear();
    }

    private void renameAccount(){
        String account_nr = TextField_oldnameAccount_nr.getText();
        String newAccountName = TextField_newAccountname.getText();
        DB.renameMyAccount(person_id, account_nr, newAccountName );
        TextField_delitAccount_nr.clear();
        TextField_newAccountname.clear();
    }


    private void createNewAccount(){
        Random r = new Random();
        int max = 2147483647;
        int min = 0;
        int account_nr = r.nextInt((max - min));
        String name = TextField_accountName.getText();
        DB.newAccount(person_id,name,Integer.toString(account_nr));
        TextField_accountName.clear();
    }
}
