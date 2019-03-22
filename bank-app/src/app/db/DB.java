package app.db;

import app.Entities.Account;
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

    public static List<Transaction> getTransactions( String account_nr){
        List<Transaction> result = null;
        PreparedStatement ps = prep("SELECT * FROM transactions WHERE transactions.`to` = ? OR transactions.`from` = ? ORDER BY transactions.date DESC;");
        try {
            ps.setString(1, account_nr);
            ps.setString(2, account_nr);
            result = (List<Transaction>)(List<?>) new ObjectMapper<>(Transaction.class).map(ps.executeQuery());
        } catch (Exception e) { e.printStackTrace(); }
        return result; // transactionslista
    }

    public static void delitMyAccount( String account_nr){
        PreparedStatement ps = prep("DELETE FROM accounts WHERE accounts.account_nr = ?");
        try {
            ps.setString(1, account_nr);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static Account getAccountnr(String person_id, String accounttype){
        PreparedStatement ps = prep("SELECT accounts.account_nr FROM accounts WHERE accounts.owner_id = ? AND accounts.accounttype = ? ");
        Account result = null;
        try {
            ps.setString(1,person_id);
            ps.setString(2,accounttype);
            result = (Account)new ObjectMapper<>(Account.class).mapOne(ps.executeQuery());

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }



    public static void addPayment(Double amount, String account_nr) {
        PreparedStatement ps = prep("UPDATE accounts SET accounts.amount = accounts.amount+? WHERE accounts.account_nr = ?");
        try {
            ps.setDouble(1, amount);
            ps.setString(2, account_nr);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void subbtraktPayment(Double amount, String account_nr){
        PreparedStatement ps = prep("UPDATE accounts SET accounts.amount = accounts.amount-? WHERE accounts.account_nr = ?");
        try {
            ps.setDouble(1, amount);
            ps.setString(2, account_nr);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void addToTrnsaktion(String person_id, String to, String from, Double amount){
        PreparedStatement ps = prep("INSERT INTO transactions SET transactions.owner_id = ? , transactions.`to` = ?, transactions.`from` =?, transactions.amount = ?;");
        try {
            ps.setString(1, person_id);
            ps.setString(2, to);
            ps.setString(3, from);
            ps.setDouble(4, amount);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    public static void renameMyAccount(String person_id, String account_nr, String accountName){
        PreparedStatement ps = prep("UPDATE accounts SET accounts.NAME = ? WHERE accounts.owner_id = ? AND accounts.account_nr = ?");
        try {
            ps.setString(1, accountName);
            ps.setString(2, person_id);
            ps.setString(3, account_nr);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void switchAccounttype(String person_id, String account_nr, String accountType){
        PreparedStatement ps = prep("UPDATE accounts SET accounts.accounttype = ? WHERE accounts.owner_id = ? AND accounts.account_nr = ?");
        try {
            ps.setString(1, accountType);
            ps.setString(2, person_id);
            ps.setString(3, account_nr);
            ps.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void newAccount(String person_id, String accountName, String account_nr){
        PreparedStatement ps = prep("INSERT INTO accounts SET accounts.account_nr = ?, accounts.NAME = ?, accounts.owner_id = ?, accounts.amount = '0', accounts.accounttype= 'save'");
        try {
            ps.setString(1, account_nr);
            ps.setString(2, accountName);
            ps.setString(3, person_id);
            ps.executeUpdate();
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    public static List<Account> getOwnedAccounts(String owner_id){
        List<Account> result = null;
        PreparedStatement ps = prep ("SELECT * FROM accounts WHERE accounts.owner_id = ? ");
        try {
            ps.setString(1, owner_id);
            result = (List<Account>)(List<?>)new ObjectMapper<>(Account.class).map(ps.executeQuery());
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
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