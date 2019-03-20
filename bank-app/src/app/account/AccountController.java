package app.account;


import app.Entities.Transaction;
import app.db.DB;
import app.login.LoginController;
import app.transaction.TransactionController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.List;

public class AccountController {

    @FXML
    VBox transactionBox;

    //@FXML private Button btn_myAccounts;
    @FXML private Button btn_loadTransactions;


    private void initialize(){
        System.out.println("initialize account");
        btn_loadTransactions.setOnAction( e -> clickLoadTransactions());
    }


    void loadMoreTransactions(){

        String s = LoginController.getUser().getPerson_id();
        List<Transaction> transactions = DB.getTransactions(s);
                //DB.getTransactions(accountId);
        System.out.println(transactions.size());
        displayTransaction(transactions);
    }

    void displayTransaction(List<Transaction> transactions){
        // For every transaction, do the following:
        System.out.println(transactions.size());
        int c = 0;
        while (c < transactions.size())
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

    @FXML void clickLoadTransactions() { loadMoreTransactions(); }
}
