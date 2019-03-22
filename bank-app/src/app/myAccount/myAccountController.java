package app.myAccount;
import app.Entities.Account;
import app.Main;
import app.db.DB;
import app.login.LoginController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.List;
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
    @FXML private Label Label_moveMoney;
    @FXML private Button btn_backhome;
    @FXML private TextField TextField_amount;
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
        btn_backhome.setOnAction(e -> goToHome());
    }

    private void clearLabel(){
        Label_sallery.setText(null);
        Label_moveMoney.setText(null);
        Label_payment.setText(null);
    }

    private void getSallery(){
        clearLabel();
        double sallery = 25000;
        String from = "Bank";
        String accounttype = "sallery";
        Account account_nr = DB.getAccountnr(person_id, accounttype);
        if(account_nr != null){
            DB.addPayment(sallery, account_nr.getAccount_nr());
            newTransaction(account_nr.getAccount_nr(),from,sallery);
            Label_sallery.setText("You got paid");
        }else{
            Label_sallery.setText("Error miss a Sallery Account ");
        }
    }

    private void makeapayment(){
        clearLabel();
        double payment = 200;
        String to = "Store";
        String accounttype = "creditcard";
        Account account_nr = DB.getAccountnr(person_id, accounttype);
        System.out.println(account_nr);
        if(account_nr != null){
            DB.subbtraktPayment(payment, account_nr.getAccount_nr());
            newTransaction(to,account_nr.getAccount_nr(),payment);
            Label_payment.setText("Payment made");
        }else{
            Label_payment.setText("Error miss a Credit Card Account ");
        }
    }

    private void delitMyAccount(){
        clearLabel();
        String account_nr = TextField_delitAccount_nr.getText();
        List<Account> accounts = DB.getOwnedAccounts(person_id);
        if (accounts.stream().anyMatch(a -> a.getAccount_nr().equals(account_nr))){
            DB.delitMyAccount(account_nr);
            TextField_delitAccount_nr.clear();
        }
        else{
            System.out.println("Error");
        }
        TextField_delitAccount_nr.clear();
    }

    private void renameAccount(){
        clearLabel();
        String account_nr = TextField_oldnameAccount_nr.getText();
        String newAccountName = TextField_newAccountname.getText();
        List<Account> accounts = DB.getOwnedAccounts(person_id);
        if (accounts.stream().anyMatch(a -> a.getAccount_nr().equals(account_nr))){
            DB.renameMyAccount(person_id, account_nr, newAccountName );
        }
        else{
            System.out.println("Error");
        }
        TextField_oldnameAccount_nr.clear();
        TextField_newAccountname.clear();
    }

    private void createNewAccount(){
        clearLabel();
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
        clearLabel();
        List<Account> accounts = DB.getOwnedAccounts(person_id);
        if (TextField_moneyFrom.getText() == null || TextField_moneyFrom.getText().trim().isEmpty() || TextField_moneyTo.getText() == null ||
                TextField_moneyTo.getText().trim().isEmpty() || TextField_amount.getText() == null || TextField_amount.getText().trim().isEmpty()){
            Label_moveMoney.setText("Fill in information");
        }else {
            try
            {
                double amount = Double.parseDouble(TextField_amount.getText());
                String from = TextField_moneyFrom.getText();
                String to = TextField_moneyTo.getText();
                if (accounts.stream().anyMatch(a -> a.getAccount_nr().equals(from))){
                    DB.addPayment(amount, to);
                    DB.subbtraktPayment(amount, from);
                    newTransaction(to,from,amount);
                    Label_moveMoney.setText("Done");
                }
                else{
                    System.out.println("Error");
                }
            }
            catch (NullPointerException | NumberFormatException ex)
            {
                System.out.println("Error");
            }
        }
        TextField_moneyFrom.clear();
        TextField_moneyTo.clear();
        TextField_amount.clear();
    }

    private void newTransaction(String to, String from, double amount){
        DB.addToTrnsaktion(person_id,to,from,amount);
    }

    private void goToHome() {
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
