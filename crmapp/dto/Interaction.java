package mvvmconsole.crmapp.dto;

public class Interaction {
    private int id;
    private int customerId;
    private String type;
    private String notes;


    public void setId(int id) {
        this.id = id;
    }

    public Interaction(int customerId, String type, String notes) {
        this.customerId = customerId;
        this.type = type;
        this.notes = notes;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Interaction(){

    }

    public Interaction(int id, int customerId, String type, String notes) {
        this.id = id;
        this.customerId = customerId;
        this.type = type;
        this.notes = notes;
    }

    public void update(String type, String notes) {
        this.type = type;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public String getType() {
        return type;
    }

    public String getNotes() {
        return notes;
    }

    public void print() {
        System.out.printf("\n%-15d%-15d%-30s%-50s",id,customerId,type,notes);
    }
}
