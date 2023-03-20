package mvvmconsole.busticketbooking.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mvvmconsole.busticketbooking.dto.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Repository {
    private static Repository data;
    private HashMap<String, User> userDetails =new HashMap<>();
    private HashMap<String, Admin> adminDetails =new HashMap<>();
    private List<Bus> busdetails=new ArrayList<>();
    private Repository(){
        readFile();
    }

    private void readFile() {
        ObjectMapper obj=new ObjectMapper();
        try {
            userDetails=obj.readValue(new File("/Users/cva/IdeaProjects/Practice/src/com.console/busticketbooking/repository/userdetails.json"), new TypeReference<HashMap<String, User>>() {});
            adminDetails=obj.readValue(new File("/Users/cva/IdeaProjects/Practice/src/com.console/busticketbooking/repository/admindetails.json"),new TypeReference<HashMap<String, Admin>>(){});
            busdetails=obj.readValue(new File("/Users/cva/IdeaProjects/Practice/src/com.console/busticketbooking/repository/busdetails.json"), new TypeReference<List<Bus>>() {});
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeFile() {
        ObjectMapper obj=new ObjectMapper();
        try{
            obj.writeValue(new File("/Users/cva/IdeaProjects/Practice/src/com.console/busticketbooking/repository/userdetails.json"), userDetails);
            obj.writeValue(new File("/Users/cva/IdeaProjects/Practice/src/com.console/busticketbooking/repository/admindetails.json"),adminDetails);
            obj.writeValue(new File("/Users/cva/IdeaProjects/Practice/src/com.console/busticketbooking/repository/busdetails.json"),busdetails);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static Repository getInstance(){
        if(data==null){
            data= new Repository();
        }
        return data;
    }


    public boolean addUser(User user) {
        if(userDetails.containsKey(user.getUserName())){
            return false;
        }
        userDetails.put(user.getUserName(),user);
        return true;
    }

    public boolean addAdmin(Admin admin) {
        if(adminDetails.containsKey(admin.getAdminName())){
            return false;
        }
        adminDetails.put(admin.getAdminName(),admin);
        return true;
    }

    public Admin verifyAdmin(String userName, String password) {
        if(adminDetails.containsKey(userName)){
            if(adminDetails.get(userName).getPassword().equals(password)){

                return adminDetails.get(userName);
            }
        }
        return null;
    }

    public User verifyUser(String userName, String password) {
        if(userDetails.containsKey(userName)){
            if(userDetails.get(userName).getPassword().equals(password)){
                return userDetails.get(userName);
            }
        }
        return null;
    }

    public List<Bus> getAllBus() {
        return busdetails;
    }

    public void addBus(Bus bus) {
        busdetails.add(bus);
    }

    public boolean cancelBus(int travelId) {
        for(Bus bus:busdetails){
            if(bus.getTravelId()==travelId){
               return busdetails.remove(bus);
            }
        }
        return false;
    }

    public Bus printSeats(int travelId) {
        for(Bus bus:busdetails){
            if(bus.getTravelId()==travelId){
                return bus;
            }
        }
        return null;
    }

    public List<Bus> findBus(String from, String to, String date) {
        List<Bus> foundBus=new ArrayList<>();
        for(Bus bus: busdetails){
            if(bus.getFrom().equals(from) && bus.getTo().equals(to) && bus.getDate().equals(date)){
                foundBus.add(bus);
            }
        }
        return foundBus;
    }

    public Bus findSelectedBusSeats(int selectedId, List<Bus> foundBus) {
        for(Bus bus:busdetails){
            if(bus.getTravelId()==selectedId && foundBus.contains(bus)){
                return bus;
            }
        }
        return null;
    }

    public int addBooking(List<Passenger> passengers, Bus bus, User user) {
        Booking booking=new Booking(passengers,passengers.size()*bus.getTicketPrice(),bus);
        int count=0;
        for(int i=0;i<bus.getSeats().length;i++){
            for(int j=0;j<bus.getSeats()[i].length;j++){
                int seatNo=bus.getSeats()[i][j];
                for(Passenger passenger:passengers){
                    if(passenger.getSeatNo()==seatNo){
                        bus.getSeats()[i][j]=0;
                        bus.decreaseOneSeat();
                        count++;
                    }
                }
            }
        }
        user.addOneBookings(booking);
        return passengers.size()*bus.getTicketPrice();
    }

    public List<Booking> getAllBooking(User user) {
        return userDetails.get(user.getUserName()).getBookings();
    }

    public int cancelBooking(int cancelId, User user, Bus bus, List<Passenger> passengers) {
        int amount=-1;
        for(Booking booking:userDetails.get(user.getUserName()).getBookings()){
            if(booking.getBookingId()==cancelId){
                amount=booking.getTotalPrice();
                booking.getBus().clearSeats(passengers);
                bus.increaseOneSeat();
                userDetails.get(user.getUserName()).getBookings().remove(booking);
                break;
            }
        }
        return amount;
    }

    public void upload() {
        writeFile();
    }

}
