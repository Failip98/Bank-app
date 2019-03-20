package app.Entities;


import app.annotations.Column;
import java.sql.Time;
import java.time.LocalDate;

public class Transaction {
    @Column
    private String owner_id;
    @Column
    private String to;
    @Column
    private String from;
    @Column
    private Float amount;
    @Column
    private LocalDate date;

    //public String getMessage() { return message; }
    public String getOwner_id(){ return owner_id; }
    public String getTo(){ return to; }
    public String getFrom(){ return from; }
    public String getAmount() { return amount.toString(); }
    public LocalDate getDate() { return date; }
}
