package app.db;

import app.Entities.Transaction;
import app.Entities.User;
import app.login.LoginController;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/** A Helper class for interacting with the Database using short-commands */
public abstract class DB {

    public static PreparedStatement prep(String SQLQuery){
        return Database.getInstance().prepareStatement(SQLQuery);
    }

    public static User getMatchingUser(String person_id, String password){
        User result = null;
        PreparedStatement ps = prep("SELECT * FROM users WHERE person_id = ? AND password = ?");
        try {
            ps.setString(1, person_id);
            ps.setString(2, password);
            result = (User)new ObjectMapper<>(User.class).mapOne(ps.executeQuery());
        } catch (Exception e) { e.printStackTrace(); }
        return result; // return User;
    }

    public static List<Transaction> getTransactions(String person_id){
        List<Transaction> result = null;
        PreparedStatement ps = prep("SELECT * FROM transactions WHERE owner_id = ?");
        try {
            ps.setString(1, person_id);
            result = (List<Transaction>)(List<?>) new ObjectMapper<>(Transaction.class).map(ps.executeQuery());
        } catch (Exception e) { e.printStackTrace(); }
        return result; // transactionslista
    }

    public static void givSallery(Double amount) {
        PreparedStatement ps = prep("UPDATE accounts SET accounts.amount = ? WHERE accounts.account_nr = '55'");
        try {
            ps.setDouble(1, amount);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addToTrnsaktion(String person_id, String to, String from, Double amount){
        try {
            PreparedStatement ps = prep("INSERT INTO transactions SET transactions.owner_id = ? , transactions.`to` = ?, transactions.`from` =?, transactions.amount = ?;");
            ps.setString(1, person_id);
            ps.setString(2, to);
            ps.setString(3, from);
            ps.setDouble(4, amount);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void delitMyAccount(String person_id, String account_nr){
        try {
            PreparedStatement ps = prep("DELETE FROM accounts WHERE accounts.owner_id = ? AND accounts.account_nr = ?");
            ps.setString(1, person_id);
            ps.setString(2, account_nr);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void renameMyAccount(String person_id, String account_nr, String accountName){
        try {
            PreparedStatement ps = prep("UPDATE accounts SET accounts.NAME = ? WHERE accounts.owner_id = ? AND accounts.account_nr = ?");
            ps.setString(1, accountName);
            ps.setString(2, person_id);
            ps.setString(3, account_nr);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void newAccount(String person_id, String accountName, String account_nr){
        try {
            PreparedStatement ps = prep("INSERT INTO accounts SET accounts.account_nr = ?, accounts.NAME = ?, accounts.owner_id = ?, accounts.amount = '0'");
            ps.setString(1, account_nr);
            ps.setString(2, accountName);
            ps.setString(3, person_id);
            ps.executeUpdate();
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    /*public Transaction getTransaction(String person_id) {
        Transaction result = null;
        PreparedStatement ps = prep("SELECT * FROM transactions WHERE person_id = ?");
        try {
            ps.setString(1, person_id);
            result = (Transaction)new ObjectMapper<>(Transaction.class).mapOne(ps.executeQuery());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
*/



    /*
        Example method with default parameters
    public static List<Transaction> getTransactions(int accountId){ return getTransactions(accountId, 0, 10); }
    public static List<Transaction> getTransactions(int accountId, int offset){ return getTransactions(accountId, offset, offset + 10); }
    public static List<Transaction> getTransactions(int accountId, int offset, int limit){
        List<Transaction> result = null;
        PreparedStatement ps = prep("bla bla from transactions WHERE account-id = "+accountId+" OFFSET "+offset+" LIMIT "+limit);
        try {
            result = (List<Transaction>)new ObjectMapper<>(Transaction.class).map(ps.executeQuery());
        } catch (Exception e) { e.printStackTrace(); }
        return result; // return User;
    }
    */


}