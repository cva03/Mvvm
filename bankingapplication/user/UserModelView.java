package mvvmconsole.bankingapplication.user;

import mvvmconsole.bankingapplication.dto.Account;
import mvvmconsole.bankingapplication.dto.MoneyRequest;
import mvvmconsole.bankingapplication.dto.TransactionHistory;
import mvvmconsole.bankingapplication.repository.Repository;

import java.util.List;

public class UserModelView {

    private UserView userView;
    private Repository data= Repository.getInstance();

    public UserModelView(UserView userView) {
        this.userView=userView;
    }


    public void changePassword(String password, Account account) {
        data.changePassword(password,account);
        userView.passwordChangeSuccessfull(password,account);
    }

    public void showTransactions(Account account) {
        List<TransactionHistory> trans=data.getTransactions(account);
        if(trans.size()<1){
            userView.noTransactionsFound();
        }else{
            userView.showTransactions(trans);
        }

    }

    public void getBalance(Account account) {
       long balance= data.getBalance(account);
       userView.showBalance(balance);
    }

    public void checkAcc(int accNo) {
       String accName= data.checkTransferAcc(accNo);
        if(!accName.equals(" ")){
            userView.requestAccFound(accName,accNo);
        }else {
            userView.requestAccNotFound();
        }
    }

    public void requestMoney(long amount, int accNo, String description, Account account) {
       data.requestMoney(amount,accNo,description,account);
       userView.requestSuccess(amount,accNo);
    }

    public void findRequest(Account account) {
        List<MoneyRequest> requests=data.findMoneyRequest(account);
        if(requests.size()>0){
            userView.showRequest(requests);
        }else{
            userView.noRequestFound();
        }
    }


    public void payRequest(long amount, int fromAccNo, Account account, int selectedId) {
        if(data.payRequest(amount,fromAccNo,account,selectedId)){
            userView.requestPaymentSuccess(amount,fromAccNo);
        }else{
            userView.requestPaymentFailed(amount,fromAccNo);
        }
    }
}
