package mvvmconsole.busticketbooking.dto;

public class Passenger {
    private String name;
    private int age;
    private int seatNo;

    public Passenger() {
    }

    public Passenger(String name, int age, int seatNo) {
        this.name = name;
        this.age = age;
        this.seatNo = seatNo;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getSeatNo() {
        return seatNo;
    }
    public void printDetails(){
        System.out.printf("\n%-20s%-10d%-10d",getName(),getAge(),getSeatNo());
    }
}
