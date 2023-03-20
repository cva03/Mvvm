package mvvmconsole.busticketbooking.admin;

import mvvmconsole.busticketbooking.dto.Admin;
import mvvmconsole.busticketbooking.dto.Bus;
import mvvmconsole.busticketbooking.login.LoginView;

import java.util.List;
import java.util.Scanner;

public class AdminView  {
    private AdminViewModel adminViewModel;
    private Admin admin;
    private Scanner sc=new Scanner(System.in);

    public AdminView(){
        this.adminViewModel=new AdminViewModel(this);
    }

    public void init(Admin admin){
        this.admin=admin;
        start();
    }

    private void start() {
        System.out.printf("%50S","<-- Admin page admin: "+admin.getAdminName()+" -->\n");
        while (true) {
            System.out.println("\n1. Add Bus");
            System.out.println("2. Cancel Bus");
            System.out.println("3. Show all Bus");
            System.out.println("4. Log out");
            System.out.println("Enter your choice");
            try {
                //admin revenue null pointer
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        addBus();
                        break;
                    case 2:
                        cancelBus();
                        break;
                    case 3:
                        adminViewModel.getAllBus();
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


    private void cancelBus() {
        System.out.printf("%50s\n","<-- Cancel bus --> ");
        while (true){
            try{
                System.out.println("Enter the travel id");
                int travelId=sc.nextInt();
                sc.nextLine();
               adminViewModel.cancelBus(travelId);
            }catch (Exception e){
                System.out.println("Invalid input!");
            }
        }

    }

    private void addBus() {
        System.out.printf("%50s\n","<-- Add bus --> ");
        while (true){
            try{
                System.out.println("Enter the Bus No");
                String busNo=sc.nextLine();
                System.out.println("Enter the Source Location");
                String sourceLoc=sc.nextLine();
                System.out.println("Enter the Destination");
                String destination=sc.nextLine();
                System.out.println("Enter the date");
                String date=sc.nextLine();
                System.out.println("Enter the Start time (in hh:mm format)");
                String startTime=sc.nextLine();
                System.out.println("Enter the Reach time (in hh:mm format)");
                String endTime=sc.nextLine();
                System.out.println("Enter \"AC\" for AC and any other key for non-AC");
                boolean ac= sc.nextLine().equalsIgnoreCase("ac");
                System.out.println("Enter \"Sleeper\" for Sleeper and any other key for Semi-Sleeper");
                boolean sleeper=sc.nextLine().equalsIgnoreCase("sleeper");
                System.out.println("Enter the ticket-price");
                int ticketprice=sc.nextInt();
                sc.nextLine();
                Bus bus=new Bus(busNo,sourceLoc,destination,date,startTime,endTime,ac,sleeper,ticketprice);
                adminViewModel.addBus(bus);
            }catch (Exception e){
                System.out.println("Invalid input, Re-enter");
            }
        }
    }

    public void emptyBusList() {
        System.out.println("No buses added yet");
        pressAnyKey();
    }

    public void showAllBus(List<Bus> busList) {
        System.out.printf("\n%-10s%-15s%-15s%-15s%-13s%-13s%-8s%-15s%-15s%-12s\n","Bus-Id","Bus No","From","To","Starttime","Reachtime","AC","Sleeper","Ticket-price","Available-Seats");
        for(Bus bus:busList)
            System.out.printf("%-10d%-15s%-15s%-15s%-13s%-13s%-8s%-15s%-15d%-12d\n",bus.getTravelId(),bus.getBusNo(),bus.getFrom(),bus.getTo(),bus.getStartTime(),bus.getEndTime(),bus.printAc(),bus.printSLeeper(),bus.getTicketPrice(),bus.getAvailableseats());
        while(true) {
            try {
                System.out.println("Enter the travel id to show the seats or enter 0 to go to main menu");
                int travelId=sc.nextInt();
                sc.nextLine();
                if(travelId==0){
                    System.out.println("returning to main menu");
                    start();
                }
                adminViewModel.printSeats(travelId);
            }catch (Exception e){
                System.out.println("Invalid input!");
            }
        }
    }


    public void busAddedSuccess(Bus bus) {
        System.out.println("Bus added successfully and the travel id is : "+bus.getTravelId());
        pressAnyKey();
    }


    public void busCancelSuccess(int travelId) {
        System.out.println("The bus with travel-id "+travelId+" is cancelled successfully.");
        pressAnyKey();
    }


    public void wrongTravelId(int travelId) {
        System.out.println("No bus found for the given travel id "+travelId);
        pressAnyKey();
    }


    public void printAllSeats(Bus bus) {
        bus.printSeats();
        pressAnyKey();
    }

    private void pressAnyKey() {
        System.out.println("\npress any key to go to main menu ");
        sc.nextLine();
        start();
    }
}
