package app.account;
import app.myAccount.*;
import app.Entities.Account;
import app.Entities.Transaction;
import app.Main;
import app.db.DB;
import app.home.HomeController;
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
    @FXML private Button btn_backhome;

    private String person_id = LoginController.getUser().getPerson_id();
    @FXML
    private void initialize(){
        System.out.println("initialize account");
        btn_loadAllTransactions.setOnAction( e -> loadTransactions(0));
        btn_loadTenTransactions.setOnAction(e -> loadTransactions(10));
        btn_backhome.setOnAction(e -> goToHome());
    }


    private void loadTransactions(int i){
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

    private void displayTransaction(List<Transaction> transactions, int show){
        if (show != 0) {
            transactions = transactions.subList(0, Math.min(show, transactions.size()));
        }

        for (Transaction t : transactions) {
            try {
                FXMLLoader loader = new FXMLLoader( getClass().getResource( "/app/transaction/transaction.fxml" ) );
                Parent fxmlInstance = loader.load();
                Scene scene = new Scene( fxmlInstance );
                TransactionController controller = loader.getController();
                controller.setTransaction(t);
                transactionBox.getChildren().add(scene.getRoot());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
