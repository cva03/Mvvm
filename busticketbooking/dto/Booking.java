package mvvmconsole.busticketbooking.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Booking {
    private static int count=0;
    private int bookingId;
    private String date;
    private String time;
    private List<Passenger> passengers;
    private int totalPrice;
    private Bus bus;

    public Booking() {
        count++;
    }

    public Booking(List<Passenger> passengers, int totalPrice, Bus bus) {
        DateTimeFormatter date=DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter time=DateTimeFormatter.ofPattern("hh:mm a");
        this.date = LocalDateTime.now().format(date);
        this.time = LocalDateTime.now().format(time);
        this.bookingId=++count;
        this.passengers = passengers;
        this.totalPrice = totalPrice;
        this.bus=bus;
    }

    public static int getCount() {
        return count;
    }

    public int getBookingId() {
        return bookingId;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public Bus getBus() {
        return bus;
    }

    public void printSingleDetails(){
        System.out.println("Booking Id : "+getBookingId()+" | Date : "+getDate()+" | Time : "+getTime()+" | Passengers : "+getPassengers().size()+" | Total-price : "+getTotalPrice());
    }

}

