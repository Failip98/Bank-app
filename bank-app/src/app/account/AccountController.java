package app.account;
import app.Entities.Account;
import app.Entities.Transaction;
import app.Main;
import app.db.DB;
import app.login.LoginController;
import app.transaction.TransactionController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class AccountController {

    @FXML ScrollPane scrollPane;
    @FXML VBox transactionBox;
    @FXML private Label label_error;
    @FXML private ComboBox<Account> ComboBox_account;
    @FXML private Button btn_loadAllTransactions;
    @FXML private Button btn_loadTenTransactions;
    @FXML private Button btn_backhome;

    private String person_id = LoginController.getUser().getPerson_id();

    @FXML
    private void initialize(){
        System.out.println("initialize account");
        btn_loadAllTransactions.setOnAction( e -> loadTransactions(0));
        btn_loadTenTransactions.setOnAction(e -> loadTransactions(10));
        btn_backhome.setOnAction(e -> goToHome());
        uppdateAccountTocombobox();
    }

    private void loadTransactions(int i){
        label_error.setText(null);
        transactionBox.getChildren().clear();
        String account_id = null;
        if (ComboBox_account.getValue() != null){
            account_id = ComboBox_account.getSelectionModel().getSelectedItem().getAccount_nr();
            List<Transaction> transactions = DB.getTransactions(account_id);
            displayTransaction(transactions,i);
        }
        else {
            label_error.setText("select a account");
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

    private void uppdateAccountTocombobox(){
        removefromComboBox();
        List<Account> accounts = DB.getOwnedAccounts(person_id);
        ComboBox_account.getItems().addAll(accounts);
    }

    private void removefromComboBox(){
        ComboBox_account.getItems().clear();
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
