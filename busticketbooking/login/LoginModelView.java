package mvvmconsole.busticketbooking.login;


import mvvmconsole.busticketbooking.dto.Admin;
import mvvmconsole.busticketbooking.dto.User;
import mvvmconsole.busticketbooking.repository.Repository;

public class LoginModelView  {
    private LoginView loginView;
    private Repository data= Repository.getInstance();

    public LoginModelView(LoginView loginView) {
        this.loginView=loginView;
    }


    public void addUser(User user) {
        if(data.addUser(user)){
            loginView.userCreationSuccess(false);
        }else{
            loginView.userCreationFailed(false);
        }
    }


    public void addAdmin(Admin admin) {
        if(data.addAdmin(admin)){
            loginView.userCreationSuccess(true);
        }else{
            loginView.userCreationFailed(true);
        }
    }


    public void verifyAdmin(String userName, String password) {
        Admin admin=data.verifyAdmin(userName,password);
        if(admin!=null){
            loginView.adminVerificationSuccess(admin);
        }else{
            loginView.adminVerificationFailed();
        }
    }


    public void verifyUser(String userName, String password) {
        User user=data.verifyUser(userName,password);
        if(user!=null){
            loginView.userVerificationSuccess(user);
        }else{
            loginView.adminVerificationFailed();
        }
    }


    public void upload() {
        data.upload();
    }
}
