package mvvmconsole.busticketbooking.user;

import mvvmconsole.busticketbooking.dto.Booking;
import mvvmconsole.busticketbooking.dto.Bus;
import mvvmconsole.busticketbooking.dto.Passenger;
import mvvmconsole.busticketbooking.dto.User;
import mvvmconsole.busticketbooking.repository.Repository;

import java.util.List;

public class UserModelView {
    private UserView userView;
    private Repository data= Repository.getInstance();

    public UserModelView(UserView userController) {
        this.userView=new UserView();
    }

    public void findBus(String from, String to, String date) {
        List<Bus> foundBus=data.findBus(from,to,date);
        if(foundBus.isEmpty()){
            userView.noBusFound();
        }else{
            userView.showFoundBus(foundBus);
        }
    }

    public void findSelectedBusSeats(int selectedId, List<Bus> foundBus) {
        Bus bus=data.findSelectedBusSeats(selectedId,foundBus);
        if(bus==null){
            userView.selectedWrongBus(foundBus);
        }else{
            userView.selectedBusFound(bus);
        }
    }


    public void addBooking(List<Passenger> passengers, Bus bus, User user) {
        int amount=data.addBooking(passengers,bus,user);
        if(amount>=1){
            userView.bookingAddSuccess(amount);
        }else{
            userView.bookingAddFailed();
        }
    }

    public void getAllBooking(User user) {
        List<Booking> bookings=data.getAllBooking(user);
        if(bookings.isEmpty()){
            userView.noBookingsFound();
        }else{
            userView.showAllBookings(bookings);
        }
    }

    public void findAllBookings(User user) {
        List<Booking> bookings=data.getAllBooking(user);
        if(bookings.isEmpty()){
            userView.noBookingsFound();
        }else{
            userView.foundbookingforCancel(bookings);
        }
    }

    public void cancelBooking(int cancelId, User user, Bus bus, List<Passenger> passengers) {
        int amount=data.cancelBooking(cancelId,user,bus,passengers);
        if(amount>0){
            userView.seatCancellationSuccess(amount,cancelId);
        }else{
            userView.seatCancellationFailed(cancelId);
        }
    }
}
