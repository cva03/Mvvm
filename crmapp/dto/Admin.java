package mvvmconsole.crmapp.dto;


public class Admin {
    private String adminName;
    private String password;

    public Admin() {
    }

    public Admin(String adminName, String password) {
        this.adminName = adminName;
        this.password = password;
    }

    public String getAdminName() {
        return adminName;
    }

    public String getPassword() {
        return password;
    }


}
