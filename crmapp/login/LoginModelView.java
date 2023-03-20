package mvvmconsole.crmapp.login;

import mvvmconsole.crmapp.dto.Admin;
import mvvmconsole.crmapp.repository.Repository;

public class LoginModelView  {
    private LoginView loginView;
    private Repository data= Repository.getInstance();

    public LoginModelView(LoginView loginView) {
        this.loginView=loginView;
    }



    public void addAdmin(Admin admin) {
        if(data.addAdmin(admin)){
            loginView.adminCreationSuccessfull();
        }else{
            loginView.adminCreationFailed();
        }
    }


    public void verifyAdmin(String userName, String password) {
        Admin admin=data.verifyAdmin(userName,password);
        if(admin!=null){
            loginView.adminVerificationSuccess(admin);
        }else {
            loginView.adminVerificationFailed();
        }
    }


    public void close() {
        data.close();
    }
}
