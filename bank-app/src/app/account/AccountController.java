package app.account;


import app.Entities.Account;
import app.Entities.Transaction;
import app.db.DB;
import app.login.LoginController;
import app.transaction.TransactionController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class AccountController {

    @FXML
    VBox transactionBox;


    @FXML private Button btn_loadAllTransactions;
    @FXML private Button btn_loadTenTransactions;
    @FXML private TextField TextField_account_id;

    private String person_id = LoginController.getUser().getPerson_id();
    @FXML
    private void initialize(){
        System.out.println("initialize account");
        btn_loadAllTransactions.setOnAction( e -> loadTransactions(0));
        btn_loadTenTransactions.setOnAction(e -> loadTransactions(10));
    }


    void loadTransactions(int i){
        String account_id = TextField_account_id.getText();
        List<Account> accounts = DB.getOwnedAccounts(person_id);

        if (accounts.stream().anyMatch(a -> a.getAccount_nr().equals(account_id))){
            List<Transaction> transactions = DB.getTransactions(account_id);
            displayTransaction(transactions,i);
        }
        else{
            System.out.println("Error");
        }
    }



    void displayTransaction(List<Transaction> transactions, int show){
        // For every transaction, do the following:
        int c = 0;
        if(show != 0){
        }
        else{
            show = transactions.size();
        }
        while (c < show)
        {
            try {
                FXMLLoader loader = new FXMLLoader( getClass().getResource( "/app/transaction/transaction.fxml" ) );
                Parent fxmlInstance = loader.load();
                Scene scene = new Scene( fxmlInstance );
                TransactionController controller = loader.getController();
                controller.setTransaction(transactions.get(c));
                transactionBox.getChildren().add(scene.getRoot());
            } catch (IOException e) {
                e.printStackTrace();
            }
            c ++;
        }

    }

    //@FXML void clickLoadTransactions() { loadMoreTransactions(); }
}
