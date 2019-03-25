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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class myAccountController {
    @FXML private Button btn_sallery;
    @FXML private Button btn_payment;
    @FXML private Button btn_deliteAccount;
    @FXML private ComboBox<Account> comboBox_deliteaccount;
    @FXML private Label Label_deliteAccount;
    @FXML private Button btn_renameAccount;
    @FXML private ComboBox<Account> comboBox_renameAccount;
    @FXML private TextField TextField_newAccountname;
    @FXML private Label Label_rename;
    @FXML private Button btn_newAccount;
    @FXML private Label Label_newAccount;
    @FXML private TextField TextField_accountName;
    @FXML private Button btn_switchSalleryAccount;
    @FXML private Button btn_switchCreditCardAccount;
    @FXML private ComboBox<Account> comboBox_switchAccount;
    @FXML private Label Label_switchAccountType;
    @FXML private Label Label_sallery;
    @FXML private Label Label_payment;
    @FXML private Button btn_moveMoney;
    @FXML private ComboBox<Account> comboBox_movefromAccount;
    @FXML private TextField TextField_moneyTo;
    @FXML private Label Label_moveMoney;
    @FXML private Button btn_backhome;
    @FXML private TextField TextField_amount;
    private String person_id = LoginController.getUser().getPerson_id();
    @FXML

    private void initialize(){
        btn_sallery.setOnAction( e -> getSallery());
        btn_payment.setOnAction(e -> makeapayment());
        btn_deliteAccount.setOnAction( e -> delitMyAccount());
        btn_renameAccount.setOnAction(e -> renameAccount());
        btn_newAccount.setOnAction(e -> createNewAccount());
        btn_switchSalleryAccount.setOnAction(e -> switchAccountType("sallery"));
        btn_switchCreditCardAccount.setOnAction(e -> switchAccountType("creditcard"));
        btn_moveMoney.setOnAction(e -> moveMoney());
        btn_backhome.setOnAction(e -> goToHome());
        uppdateAccountTocombobox();
    }

    private void clearLabel(){
        Label_sallery.setText(null);
        Label_moveMoney.setText(null);
        Label_payment.setText(null);
        Label_deliteAccount.setText(null);
        Label_rename.setText(null);
        Label_newAccount.setText(null);
        Label_switchAccountType.setText(null);
    }

    private void getSallery(){
        clearLabel();
        double sallery = 25000;
        String from = "Bank";
        String accounttype = "sallery";
        Account account_nr = DB.getAccountnr(person_id, accounttype);
        if(account_nr != null){
            DB.addPayment(sallery, account_nr.getAccount_nr());
            DB.subbtraktPayment(sallery, from);
            newTransaction(account_nr.getAccount_nr(),from,sallery);
            Label_sallery.setText("You got paid");
        }else{
            Label_sallery.setText("Error miss a Sallery Account ");
        }
        uppdateAccountTocombobox();
    }

    private void makeapayment(){
        clearLabel();
        double payment = 200;
        String to = "Store";
        String accounttype = "creditcard";
        Account account_nr = DB.getAccountnr(person_id, accounttype);
        if(account_nr != null){
            DB.subbtraktPayment(payment, account_nr.getAccount_nr());
            DB.addPayment(payment, to);
            newTransaction(to,account_nr.getAccount_nr(),payment);
            Label_payment.setText("Payment made");
        }else{
            Label_payment.setText("Error miss a Credit Card Account ");
        }
        uppdateAccountTocombobox();
    }

    private void delitMyAccount(){
        clearLabel();
        String deliteaccount_nr = checkIfEmpty(comboBox_deliteaccount);
        if (deliteaccount_nr != null){
            if (comboBox_deliteaccount.getSelectionModel().getSelectedItem().getAmount() == 0){
                DB.delitMyAccount(deliteaccount_nr);
                Label_deliteAccount.setText("Done");
            }else {
                Label_deliteAccount.setText("Can only remove empty Accounts");
            }
        }
        else{
            Label_deliteAccount.setText("Error try again");
        }
        uppdateAccountTocombobox();
    }

    private void renameAccount(){
        clearLabel();
        String account_nr = checkIfEmpty(comboBox_renameAccount);
        System.out.println(account_nr);
        if (account_nr != null){
            if(TextField_newAccountname.getText() == null || TextField_newAccountname.getText().trim().isEmpty()){
                Label_rename.setText("Fill in new name");
            }else {
                String newAccountName = TextField_newAccountname.getText();
                DB.renameMyAccount(person_id, account_nr, newAccountName );
                Label_rename.setText("Done");
            }
        }else {
            Label_rename.setText("Fill in Account nr");
        }
        TextField_newAccountname.clear();
        uppdateAccountTocombobox();
    }

    private void createNewAccount(){

        if(TextField_accountName.getText() == null || TextField_accountName.getText().trim().isEmpty()){
            Label_newAccount.setText("Fill in new Account name");
        }else {
            clearLabel();
            Random r = new Random();
            int max = 2147483647;
            int min = 0;
            int account_nr = r.nextInt((max - min));
            String name = TextField_accountName.getText();
            DB.newAccount(person_id,name,Integer.toString(account_nr));
            TextField_accountName.clear();
            Label_newAccount.setText("Done");
        }
        uppdateAccountTocombobox();
    }

    private void switchAccountType(String accounttype){
        clearLabel();
        String switchAccount_nr = checkIfEmpty(comboBox_switchAccount);
        Account oldaccount_nr = DB.getAccountnr(person_id, accounttype);
        if(switchAccount_nr != null){
            if(oldaccount_nr != null){
                DB.switchAccounttype(person_id,oldaccount_nr.getAccount_nr(), "save");
            }
            DB.switchAccounttype(person_id, switchAccount_nr ,accounttype);//nya
            Label_switchAccountType.setText("Done");
        }
        else {
            Label_switchAccountType.setText("Fill in Account nr");
        }
        uppdateAccountTocombobox();

    }

    private void moveMoney(){
        clearLabel();
        String from = checkIfEmpty(comboBox_movefromAccount);
        if (from != null){
            if (TextField_moneyTo.getText() == null || TextField_moneyTo.getText().trim().isEmpty()
                    || TextField_amount.getText() == null || TextField_amount.getText().trim().isEmpty()) {
                Label_moveMoney.setText("Fill in information");
            }else{
                try {
                    double amount = Double.parseDouble(TextField_amount.getText());
                    String to = TextField_moneyTo.getText();
                    DB.addPayment(amount, to);
                    DB.subbtraktPayment(amount, from);
                    newTransaction(to,from,amount);
                    Label_moveMoney.setText("Done");
                }catch (NullPointerException | NumberFormatException ex) {
                    Label_moveMoney.setText("Fill in only numbers");
                }
            }
        }else {
            Label_moveMoney.setText("Fill in Account nr");
        }
        TextField_moneyTo.clear();
        TextField_amount.clear();
        uppdateAccountTocombobox();

    }

    private void newTransaction(String to, String from, double amount){
        DB.addToTrnsaktion(to,from,amount);
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

    private void uppdateAccountTocombobox(){
        removefromComboBox();
        List<Account> accounts = DB.getOwnedAccounts(person_id);
        comboBox_deliteaccount.getItems().addAll(accounts);
        comboBox_renameAccount.getItems().addAll(accounts);
        comboBox_switchAccount.getItems().addAll(accounts);
        comboBox_movefromAccount.getItems().addAll(accounts);
    }

    private void removefromComboBox(){
        comboBox_deliteaccount.getItems().clear();
        comboBox_renameAccount.getItems().clear();
        comboBox_switchAccount.getItems().clear();
        comboBox_movefromAccount.getItems().clear();
    }

    private String checkIfEmpty(ComboBox<Account> box){
        if (box.getValue() != null){
            return box.getSelectionModel().getSelectedItem().getAccount_nr();
        }
        else {
            return null;
        }
    }
}
