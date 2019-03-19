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
    private Float amaunt;
    @Column
    private String lock;

    @Override
    public String toString(){
        return String.format("Accounts: { account_nr: %s, name: %s, owner_id: %s, amaunt: %d }", account_nr, name, owner_id, amaunt, lock);
    }
}
