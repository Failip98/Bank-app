package app.Entities;

import app.annotations.Column;

public class Account {

    @Column
    private String account_nr;
    @Column
    private String name;
    @Column
    private String owner_id;
    @Column
    private Float amount;
    @Column
    private String accounttype;

    @Override
    public String toString(){
        return String.format("Account_nr: %s, Name: %s, Amount: %.2f, Accounttype: %s", account_nr, name, amount, accounttype);
        //return String.format("Accounts: { account_nr: %s, name: %s, owner_id: %s, amount: %f, accounttype: %s }", account_nr, name, owner_id, amount, accounttype);
    }

    public String getAccount_nr() {
        return account_nr;
    }

    public String getAccounttype() {
        return accounttype;
    }
}
