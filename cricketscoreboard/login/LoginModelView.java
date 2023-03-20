package mvvmconsole.cricketscoreboard.login;

import mvvmconsole.cricketscoreboard.dto.Staff;
import mvvmconsole.cricketscoreboard.repository.Repository;

public class LoginModelView {
    private LoginView loginView;
    private Repository data= Repository.getInstance();

    public LoginModelView(LoginView loginView) {
        this.loginView=loginView;
    }


    public void verifyAdmin(String userName, String password) {
        Staff staff=data.checkAdmin(userName,password);
        if(staff==null){
            loginView.staffVerificationFailed();
        }else{
            loginView.staffVerificationSucess(staff);
        }
    }

    public void addStaff(Staff staff) {
        if(data.addStaff(staff)){
            loginView.staffCreationSuccess();
        }else{
            loginView.staffCreationFailed();
        }
    }

    public void upload() {
        data.upload();
    }
}
