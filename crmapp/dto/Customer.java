package mvvmconsole.crmapp.dto;

public class Customer {
    private int id;
    private String name;
    private String email;
    private String phone;
    private int interactions;

    public Customer(int id, String name, String email, String phone) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public void setInteractions(int interactions) {
        this.interactions = interactions;
    }

    public Customer(){
    }

    public Customer(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Customer(int id, String name, String email, String phone,int interactions) {
        this.id=id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.interactions=interactions;
    }

    public void update(String name,String email,String phone){
        this.name = name;
        this.email = email;
        this.phone = phone;
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }


    public void print() {
        System.out.printf("\n%-15d%-25s%-45s%-20s%-15d",id,name,email,phone,interactions);
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
