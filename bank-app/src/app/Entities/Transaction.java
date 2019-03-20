package app.Entities;


import app.annotations.Column;
import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.sql.Timestamp;

public class Transaction {
    @Column
    private String owner_id;
    @Column
    private String to;
    @Column
    private String from;
    @Column
    private double amount;
    @Column
    private java.sql.Timestamp date;
    private List<Transaction> transactionList;
    //public String getMessage() { return message; }
    public String getOwner_id(){ return owner_id; }
    public String getTo(){ return to; }
    public String getFrom(){ return from; }
    public String getAmount() {
        return Double.toString(amount);
    }
    public ZonedDateTime getDate() {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.of("Europe/Berlin"));
    }
    public String getDateAsString(){
        return getDate().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME).replace('T', ' ');
    }
    public List<Transaction> getList(){
        return transactionList;
    }
}
