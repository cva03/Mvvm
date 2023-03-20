package mvvmconsole.busticketbooking.dto;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String userName;
    private String password;
    private List<Booking> bookings;

    public User() {
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.bookings=new ArrayList<>();
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addOneBookings(Booking bookings) {
        this.bookings.add(bookings);
    }

}
