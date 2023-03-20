package mvvmconsole.busticketbooking.user;

import mvvmconsole.busticketbooking.dto.Booking;
import mvvmconsole.busticketbooking.dto.Bus;
import mvvmconsole.busticketbooking.dto.Passenger;
import mvvmconsole.busticketbooking.dto.User;
import mvvmconsole.busticketbooking.login.LoginView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UserView  {
    private UserModelView userModelView;
    private User user;
    private Scanner sc=new Scanner(System.in);

    public UserView(){
        this.userModelView=new UserModelView(this);
    }

    public void init(User user){
        this.user=user;
        start();
    }

    private void start(){
        System.out.printf("%50S","<-- User page admin: "+user.getUserName()+" -->\n");
        while (true) {
            System.out.println("\n1. New booking");
            System.out.println("2. Cancel Booking");
            System.out.println("3. Show all Bookings");
            System.out.println("4. Log out");
            System.out.println("Enter your choice");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        newBooking();
                        break;
                    case 2:
                        userModelView.findAllBookings(user);
                        break;
                    case 3:
                        userModelView.getAllBooking(user);
                        break;
                    case 4:
                        LoginView lv=new LoginView();
                        lv.init();
                    default:
                        throw new RuntimeException();
                }
            } catch (Exception e) {
                System.out.println("Invalid Input!");
            }
        }
    }


    private void newBooking() {
        System.out.printf("%50s\n","<-- New Booking --> ");
        System.out.println("Enter the source Location");
        String from=sc.nextLine();
        System.out.println("Enter the destination");
        String to=sc.nextLine();
        System.out.println("Enter the date (in dd-mm-yyyy format)");
        String date=sc.nextLine();
        // try trycatch when you are free
        userModelView.findBus(from,to,date);

    }


    public void noBusFound() {
        System.out.println("No bus found for the given information");
        pressAnyKey();
    }

    public void showFoundBus(List<Bus> foundBus) {
        System.out.println(foundBus.size()+" buses found\n");
        System.out.printf("\n%-10s%-15s%-15s%-15s%-13s%-13s%-8s%-15s%-15s%-12s\n","Travel-Id","Bus No","From","To","Starttime","Reachtime","AC","Sleeper","Ticket-price","Available-Seats");
        for(Bus bus:foundBus)
            System.out.printf("%-10d%-15s%-15s%-15s%-13s%-13s%-8s%-15s%-15d%-12d\n",bus.getTravelId(),bus.getBusNo(),bus.getFrom(),bus.getTo(),bus.getStartTime(),bus.getEndTime(),bus.printAc(),bus.printSLeeper(),bus.getTicketPrice(),bus.getAvailableseats());
        while (true) {
            try {
                System.out.println("\nEnter the travel-id of the bus you want to book or 0 to exit");
                int selectedId=sc.nextInt();
                if(selectedId==0){
                    start();
                }
                userModelView.findSelectedBusSeats(selectedId,foundBus);
            } catch (Exception e) {
                System.out.println("Invalid input!");
            }
        }
    }


    public void selectedWrongBus(List<Bus> foundBus) {
        System.out.println("No bus found for the selected travel id");
        pressAnyKey();
    }


    public void selectedBusFound(Bus bus) {
        System.out.println(bus.getAvailableseats() + " seats found\n");
        bus.printSeats();
        while (true) {
            try {
                System.out.println("Enter the number of seats you want to book");
                int wantedSeats=sc.nextInt();
                sc.nextLine();
                if(wantedSeats>=bus.getAvailableseats() || wantedSeats<1){
                    System.out.println("prompt:: Enter number seats 1 to "+bus.getAvailableseats());
                    continue;
                }
                getPassangerDetails(bus,wantedSeats);
                //continue here //use array smart idea
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("Invalid input");
            }
        }
    }


    public void bookingAddSuccess(int amount) {
        System.out.println("Your booking added successfully and the amount is "+amount);
        pressAnyKey();
    }


    public void bookingAddFailed() {
        System.out.println("Booking failed");
        pressAnyKey();
    }


    public void noBookingsFound() {
        System.out.println("---- No Bookings Found ----");
        pressAnyKey();
    }


    public void showAllBookings(List<Booking> bookings) {
        printBookings(bookings);
        pressAnyKey();
    }

    private void printBookings(List<Booking> bookings) {
        System.out.printf("%50s\n\n","<-- All Bookings --> ");
        for(Booking booking:bookings){
            booking.printSingleDetails();
            System.out.println("Bus details :- ");
            Bus bus=booking.getBus();
            bus.printSingleDetails();
            System.out.println("Passenger details : ");
            System.out.printf("%-20s%-10s%-10s","Name","Age","SeatNo");
            for(Passenger passenger:booking.getPassengers()){
                passenger.printDetails();
            }
            System.out.println("\n");
        }
    }


    public void foundbookingforCancel(List<Booking> bookings) {
        printBookings(bookings);
        while(true) {
            System.out.println("Enter the Booking id to cancel or 0 to exit");
            int cancelId = sc.nextInt();
            sc.nextLine();
            if (cancelId == 0) {
                System.out.println("Exitting cancellation");
                pressAnyKey();
            }
            for (int i = 0; i < bookings.size(); i++) {
                if (bookings.get(i).getBookingId() == cancelId) {
                    userModelView.cancelBooking(cancelId, user,bookings.get(i).getBus(),bookings.get(i).getPassengers());
                }
            }
            System.out.println("No booking found for the give input, enter the correct booking-id.");
        }

    }


    public void seatCancellationSuccess(int amount, int cancelId) {
        System.out.println("The booking id "+cancelId+" is cancelled successfully");
        System.out.println("Your payment of amount "+amount+" will be refunded");
        pressAnyKey();
    }


    public void seatCancellationFailed(int cancelId) {
        System.out.println("Booking cancellation failed for the booking id "+cancelId);
        pressAnyKey();
    }

    private void getPassangerDetails(Bus bus, int wantedSeats) {
        List<Integer> selectedTemp=new ArrayList<>();
        List<Passenger> passengers=new ArrayList<>();
        for(int i=0;i<wantedSeats;i++){
            System.out.println("Enter the passenger "+(i+1)+"'s name");
            String name=sc.nextLine();
            System.out.println("Enter the passenger age");
            int age=sc.nextInt();
            sc.nextLine();
            while(true) {
                System.out.println("Select the seat number you want to book");
                int seatNumber = sc.nextInt();
                sc.nextLine();
                if(selectedTemp.contains(seatNumber)){
                    System.out.println("seat already Booked");
                    continue;
                }
                if(seatNumber<=0 && seatNumber>bus.getTotalseats()){
                    System.out.println("Enter the valid seat number");
                    continue;
                }
                if(checkSeatFound(bus,seatNumber)){
                    selectedTemp.add(seatNumber);
                    passengers.add(new Passenger(name,age,seatNumber));
                    break;
                }else{
                    System.out.println("Seat already booked");
                }
            }
        }
        userModelView.addBooking(passengers,bus,user);
    }

    private boolean checkSeatFound(Bus bus, int seatNumber) {
        for(int j=0;j<bus.getSeats().length;j++){
            for(int k=0;k<bus.getSeats()[j].length;k++){
                if(bus.getSeats()[j][k]==seatNumber){
                    return true;
                }
            }
        }
        return false;
    }

    private void pressAnyKey() {
        System.out.println("\npress any key to go to main menu ");
        sc.nextLine();
        start();
    }

}
