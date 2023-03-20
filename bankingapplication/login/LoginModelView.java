package mvvmconsole.bankingapplication.login;

import mvvmconsole.bankingapplication.dto.Account;
import mvvmconsole.bankingapplication.repository.Repository;

public class LoginModelView {

    private LoginView loginView;
    private Repository data= Repository.getInstance();

    public LoginModelView(LoginView loginView) {
        this.loginView=loginView;
    }

    public void createAccount(Account account) {
        int accountNo=data.createAccount(account);
        if(accountNo!=0){
            loginView.accountCreationSuccess(accountNo);
        }
    }

    public void checkUser(int accNo, String password) {
        Account account=data.checkUser(accNo,password);
        if(account!=null){
            loginView.loginSuccess(account);
        }else{
            loginView.loginFail();
        }
    }

    public void updateDetails() {
        data.updateDetails();
    }
}
