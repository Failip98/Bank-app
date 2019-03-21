package app.myAccount;
import app.Entities.Account;
import app.db.DB;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    @FXML private Button btn_switchSalleryAccount;
    @FXML private Button btn_switchCreditCardAccount;
    @FXML private TextField TextField_switchAccountType;
    @FXML private Label Label_sallery;
    @FXML private Label Label_payment;
    @FXML private Button btn_moveMoney;
    @FXML private TextField TextField_moneyFrom;
    @FXML private TextField TextField_moneyTo;
    private String person_id = LoginController.getUser().getPerson_id();
    @FXML
    private void initialize(){
        System.out.println("initialize account");
        btn_sallery.setOnAction( e -> getSallery());
        btn_payment.setOnAction(e -> makeapayment());
        btn_deliteAccount.setOnAction( e -> delitMyAccount());
        btn_renameAccount.setOnAction(e -> renameAccount());
        btn_newAccount.setOnAction(e -> createNewAccount());
        btn_switchSalleryAccount.setOnAction(e -> switchAccountType("sallery"));
        btn_switchCreditCardAccount.setOnAction(e -> switchAccountType("creditcard"));
        btn_moveMoney.setOnAction(e -> moveMoney());
    }

    private void getSallery(){
        double sallery = 25000;
        String from = "Bank";
        String accounttype = "sallery";
        Account account_nr = DB.getAccountnr(person_id, accounttype);
        if(account_nr != null){
            DB.givSallery(sallery, account_nr.getAccount_nr());
            newTransaction(account_nr.getAccount_nr(),from,sallery);
            Label_sallery.setText("You got paid");
        }else{
            Label_sallery.setText("Error miss a Sallery Account ");
        }
    }

    private void makeapayment(){
        double payment = 200;
        String to = "Store";
        String accounttype = "creditcard";
        Account account_nr = DB.getAccountnr(person_id, accounttype);
        System.out.println(account_nr);
        if(account_nr != null){
            DB.storePayment(payment, account_nr.getAccount_nr());
            newTransaction(to,account_nr.getAccount_nr(),payment);
            Label_payment.setText("Payment made");
        }else{
            Label_payment.setText("Error miss a Credit Card Account ");
        }

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

    private void switchAccountType(String accounttype){
        Account account_nr = DB.getAccountnr(person_id, accounttype);
        if(account_nr != null){
            DB.switchAccounttype(person_id,account_nr.getAccount_nr(), "save");
        }
        String toSwichAccount_nr = TextField_switchAccountType.getText();
        DB.switchAccounttype(person_id,toSwichAccount_nr, accounttype);
        TextField_switchAccountType.clear();

    }

    private void moveMoney(){
        String from = TextField_moneyFrom.getText();
        String to = TextField_moneyFrom.getText();


    }
}
