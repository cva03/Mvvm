package mvvmconsole.bankingapplication.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionHistory {
    private String type;
    private long amount;
    private String date;
    private String time;
    private long balance;

    public TransactionHistory(){

    }

    public TransactionHistory(String type, long amount,long balance) {
        this.type = type;
        this.amount = amount;
        DateTimeFormatter dateFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("hh:mm a");
        this.date= LocalDateTime.now().format(dateFormatter);
        this.time= LocalDateTime.now().format(timeFormatter);
        this.balance=balance;
    }

    public String getType() {
        return type;
    }

    public long getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public long getBalance() {
        return balance;
    }
}
