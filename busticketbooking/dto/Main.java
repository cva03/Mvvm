package mvvmconsole.busticketbooking.dto;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Bus bus1=new Bus("12","madurai","chennai","29-08-2000","10:00","11:00",true,true,1000);
        List<Passenger> pass=new ArrayList<>();
        pass.add(new Passenger("siva",12,1));
        pass.add(new Passenger("pk",12,2));
        pass.add(new Passenger("siva subramaniyan",12,2));
        List<Booking> booking=new ArrayList<>();
        booking.add(new Booking(pass,2000,bus1));
        booking.add(new Booking(pass,1500,bus1));
        System.out.println();
        for(Booking booking1:booking){
            booking1.printSingleDetails();
            System.out.println("Bus details :- ");
            Bus bus=booking1.getBus();
            bus.printSingleDetails();
            System.out.println("Passenger details : ");
            System.out.printf("%-20s%-10s%-10s","Name","Age","SeatNo");
            for(Passenger passenger:booking1.getPassengers()){
                passenger.printDetails();
            }
            System.out.println("\n");
        }

    }

}
