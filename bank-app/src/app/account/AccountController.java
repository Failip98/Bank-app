package app.account;


import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;

import javax.sound.midi.Soundbank;
import java.awt.*;
import java.io.IOException;

public class AccountController {

    @FXML
    VBox transactionBox;

    @FXML private Button btn_myAccounts;
    @FXML private Button btn_loadTransactions;


    private void initialize(){
        System.out.println("initialize account");
        //loadMoreTransactions();
        btn_loadTransactions.setOnAction( e -> clickLoadTransactions());
    }


    void loadMoreTransactions(){
//        List<Transaction> transactions = DB.getTransactions(accountId);
        displayTransaction(/*transactions*/);
    }

    void displayTransaction(/*List<Transaction> transactions*/){
        // For every transaction, do the following:
        try {
            FXMLLoader loader = new FXMLLoader( getClass().getResource( "/app/transaction/transaction.fxml" ) );
            Parent fxmlInstance = loader.load();
            Scene scene = new Scene( fxmlInstance );

//            TransactionController controller = loader.getController();
//            controller.setTransaction(transaction);

            transactionBox.getChildren().add(scene.getRoot());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML void clickLoadTransactions() { loadMoreTransactions(); }
}
