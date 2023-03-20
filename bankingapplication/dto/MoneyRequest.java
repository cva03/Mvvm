package mvvmconsole.bankingapplication.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MoneyRequest {
    private static int lastid=0;
    private int requestId;
    private long amount;
    private int fromAccNo;
    private String date;
    private String time;
    private String description;
    private boolean paid;

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public MoneyRequest(){
        lastid++;
    }

    public MoneyRequest(long amount, int fromAccNo, String description) {
        this.amount = amount;
        this.fromAccNo = fromAccNo;
        this.description = description;
        DateTimeFormatter dateformatter=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter=DateTimeFormatter.ofPattern("hh:mm a");
        this.date= LocalDateTime.now().format(dateformatter);
        this.time= LocalDateTime.now().format(timeFormatter);
        this.requestId=++lastid;
        this.paid=false;
    }

    public static int getLastid() {
        return lastid;
    }

    public boolean isPaid() {
        return paid;
    }

    public void moneyPaid() {
        this.paid = true;
    }

    public long getAmount() {
        return amount;
    }

    public int getFromAccNo() {
        return fromAccNo;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getDescription() {
        return description;
    }

    public int getRequestId() {
        return requestId;
    }

}
