package app.myAccount;
import app.Entities.Account;
import app.db.DB;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javax.sound.midi.Soundbank;
import java.util.Random;

public class myAccountController {
    @FXML private Button btn_sallery;
    @FXML private Button btn_payment;
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
        btn_payment.setOnAction(e -> makeapayment());
        btn_deliteAccount.setOnAction( e -> delitMyAccount());
        btn_renameAccount.setOnAction(e -> renameAccount());
        btn_newAccount.setOnAction(e -> createNewAccount());

    }

    private void getSallery(){
        double sallery = 25000;
        String from = "Bank";
        String accounttype = "sallery";
        Account account_nr = DB.getAccountnr(person_id, accounttype);
        DB.givSallery(sallery, account_nr.getAccount_nr());
        newTransaction(account_nr.getAccount_nr(),from,sallery);
    }

    private void makeapayment(){
        double payment = 200;
        String to = "Store";
        String accounttype = "creditcard";
        Account account_nr = DB.getAccountnr(person_id, accounttype);
        DB.storePayment(payment, account_nr.getAccount_nr());
        newTransaction(to,account_nr.getAccount_nr(),payment);
    }

    private void newTransaction(String to, String from, double amount){
        DB.addToTrnsaktion(person_id,to,from,amount);
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
