package mvvmconsole.busticketbooking.dto;

import java.util.List;

public class Bus {
    private static int count=0;
    private int travelId;
    private String busNo;
    private String from;
    private String to;
    private String date;
    private String startTime;
    private String endTime;
    private boolean ac;
    private boolean sleeper;
    private int ticketPrice;
    private int seats[][];
    private int availableseats;
    private int totalseats;

    public Bus() {
        count++;
    }

    public Bus(String busNo, String from, String to, String date, String startTime, String endTime, boolean ac, boolean sleeper, int ticketPrice) {
        this.busNo = busNo;
        this.from = from;
        this.to = to;
        this.date=date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.ac = ac;
        this.sleeper = sleeper;
        this.ticketPrice = ticketPrice;
        this.travelId = ++count;
        if (sleeper) {
            setSleeperSeats(seats);
        } else {
            setSemiSleeperSeats();
        }
        this.availableseats=seats.length*seats[0].length;
        totalseats=availableseats;
    }

    private void setSemiSleeperSeats() {
        this.seats=new int[12][4];
        int num=1;
        for(int i=0;i<this.seats.length;i++){
            for(int j=0;j< this.seats[0].length;j++){
                this.seats[i][j]=num++;
            }
        }
    }

    private void setSleeperSeats(int[][] seats) {
        this.seats = new int[12][3];
        int num=1;
        for(int i=0;i<this.seats.length;i++){
            for(int j=0;j< this.seats[0].length;j++){
                this.seats[i][j]=num++;
            }
        }
    }

    public String printSLeeper(){
        if(sleeper)
            return "Sleeper";
        return "Semi-Sleeper";
    }

    public String printAc(){
        if(ac)
            return "AC";
        return "Non-AC";
    }

    public int[][] getSeats() {
        return seats;
    }


    public void printSeats(){
        System.out.println("Seats which are marked as \"X\" are already booked");
        if(sleeper) {
            System.out.println("\n    Lower                Upper\n");
            for (int i = 0; i < seats.length / 2; i++) {
                for (int lower = 0; lower < seats[i].length; lower++) {
                    if(seats[i][lower]==0)
                        System.out.printf("%-5s", "X");
                    else
                        System.out.printf("%-5d", seats[i][lower]);
                }
                System.out.print("      ");
                for (int upper = 0; upper < seats[i].length; upper++) {
                    if(seats[i + (seats.length / 2)][upper]==0)
                        System.out.printf("%-5s", "X");
                    else
                        System.out.printf("%-5d", seats[i + (seats.length / 2)][upper]);
                }
                System.out.println("\n");
            }
        }else{
            System.out.println("\n         Front    \n");
            for(int i=0;i< seats.length;i++){
                for(int j=0;j<seats[0].length;j++){
                    if(j==2)
                        System.out.print("  ");
                    if(seats[i][j]==0)
                        System.out.printf("%-5s","X");
                    else
                        System.out.printf("%-5d",seats[i][j]);
                }
                System.out.println("\n");
            }
        }
        System.out.println("X - already booked");
    }

    public void printSingleDetails(){
        System.out.println("Travel id : "+getTravelId()+" | Bus-No : "+getBusNo()+" | Source : "+getFrom()+" | Destination : "+getTo()+" | Date : "+getDate()+" | Start-time : "+getStartTime()+" | End-time : "+getEndTime()+" | AC : "+printAc()+" | Sleeper : "+printSLeeper());
    }


    public int getAvailableseats() {
        return availableseats;
    }

    public static int getCount() {
        return count;
    }

    public int getTravelId() {
        return travelId;
    }

    public String getBusNo() {
        return busNo;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public boolean isAc() {
        return ac;
    }

    public boolean isSleeper() {
        return sleeper;
    }

    public String getDate() {
        return date;
    }

    public int getTotalseats() {
        return totalseats;
    }

    public int getTicketPrice() {
        return ticketPrice;
    }
    public void decreaseOneSeat(){
        availableseats--;
    }
    public void increaseOneSeat(){
        availableseats++;
    }


    public void clearSeats(List<Passenger> passengers) {
        int num=0;
        for(int i=0;i<seats.length;i++){
            for(int j=0;j<seats[i].length;j++){
                num++;
                for(int k=0;k<passengers.size();k++){
                    if (seats[i][j] == passengers.get(k).getSeatNo()) {
                        seats[i][j] = num;
                        break;
                    }
                }
            }
        }
    }
}

