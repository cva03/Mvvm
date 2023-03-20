package mvvmconsole.bankingapplication.user;

import mvvmconsole.bankingapplication.dto.Account;
import mvvmconsole.bankingapplication.dto.MoneyRequest;
import mvvmconsole.bankingapplication.dto.TransactionHistory;
import mvvmconsole.bankingapplication.login.LoginView;
import mvvmconsole.bankingapplication.user.transferfund.TransferView;

import java.util.List;
import java.util.Scanner;

public class UserView  {

    private UserModelView userModelView;
    private Scanner sc=new Scanner(System.in);
    private Account account;

    public UserView(){
        this.userModelView=new UserModelView(this);
    }

    public void init(Account account){
        this.account=account;
        start();
    }
    private void start(){
        System.out.printf("\n%50s%s","<---- Banking Page User: ",(account.getFirstName()+" "+account.getLastName())+" ---->\n");
        while (true) {
            System.out.println("1. Check account balance");
            System.out.println("2. Transfer Funds");
            System.out.println("3. View Transaction History");
            System.out.println("4. Change Password");
            System.out.println("5. Request Money");
            System.out.println("6. Pay for Request");
            System.out.println("7. Logout");
            int choice = sc.nextInt();
            sc.nextLine();
            try {
                switch (choice) {
                    case 1:
                        userModelView.getBalance(account);
                        break;
                    case 2:
                        TransferView tv=new TransferView();
                        tv.init(account);
                        break;
                    case 3:
                        userModelView.showTransactions(account);
                        break;
                    case 4:
                        changePassword();
                        break;
                    case 5:
                        requestMoney();
                        break;
                    case 6:
                        userModelView.findRequest(account);
                        break;
                    case 7:
                        LoginView lv=new LoginView();
                        lv.init();

                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Invalid input!");
            }
        }
    }



    private void requestMoney() {
        try {
            System.out.println("Enter the account number you want to request");
            int accNo = sc.nextInt();
            sc.nextLine();
            if(accNo==account.getAccountNumber()){
                System.out.println("You cant Request money from your own account number!");
                requestMoney();
            }
            userModelView.checkAcc(accNo);
        }catch (Exception e){
            System.out.println("In valid input, Re-enter");
            requestMoney();
        }
    }

    private void changePassword() {
        System.out.printf("\n%50s\n","<----- Change password ----->");
        String password;
        while (true) {
            System.out.println("Enter the password");
            password = sc.nextLine();
            System.out.println("Re-Enter the password");
            if (password.equals(sc.nextLine())) {
                break;
            }
            System.out.println("Password mismatch ");
        }
        userModelView.changePassword(password,account);
    }




    public void passwordChangeSuccessfull(String password, Account account) {
        System.out.println("Password Changed Successfully");
        pressAnyKey();
    }

    public void showTransactions(List<TransactionHistory> transactionHistory) {
        System.out.printf("\n%-15s%-15s%-50s%-15s%-15s\n", "Date", "Time", "Description", "amount", "balance");
        for (TransactionHistory transactions : transactionHistory) {
            System.out.printf("%-15s%-15s%-50s%-15d%-15d\n", transactions.getDate(), transactions.getTime(), transactions.getType(), transactions.getAmount(), transactions.getBalance());
        }
        System.out.println("\npress any key to go to main menu ");
        sc.nextLine();
        start();
    }


    public void noTransactionsFound() {
        System.out.println("No Transactions found");
        System.out.println("\npress any key to go to main menu ");
        sc.nextLine();
        start();
    }

    public void showBalance(long balance) {
        System.out.println("Balance : "+balance+" INR");
        pressAnyKey();
    }

    public void requestAccFound(String accName, int accNo) {
        System.out.println("Transfer accound holder name: "+accName);
        System.out.println("\nEnter the amount you want to request");
        long amount=sc.nextLong();
        sc.nextLine();
        System.out.println("Enter the description");
        String description=sc.nextLine();
        System.out.println("Enter CONFIRM (in full caps) to send ");
        if(sc.nextLine().equals("CONFIRM")) {
            userModelView.requestMoney(amount,accNo,description,account);
        }else{
            System.out.println("Request Cancelled , Redirecting to main menu");
            start();
        }
    }


    public void requestAccNotFound() {
        System.out.println("No account found for the given Acc number\n");
        pressAnyKey();
    }


    public void requestSuccess(long amount, int accNo) {
        System.out.printf("\nMoney request of %d sent successfully to the account number : %d\n",amount,accNo);
        pressAnyKey();
    }


    public void showRequest(List<MoneyRequest> requests) {
        System.out.printf("\n%-12s%-15s%-15s%-15s%-15s%-50s","Request-Id","Amount","Request from","Date","Time","Description");
        boolean isPresent=true;
        for(MoneyRequest request:requests){
            if(!request.isPaid()) {
                System.out.printf("\n%-12d%-15d%-15d%-15s%-15s%-50s", request.getRequestId(), request.getAmount(), request.getFromAccNo(), request.getDate(), request.getTime(), request.getDescription());
                isPresent=false;
            }
        }
        if(isPresent){
            noRequestFound();
        }
        while (true) {
            System.out.println("Select the Request-Id you want pay");
            int selectedId = sc.nextInt();
            sc.nextLine();
            for (MoneyRequest request : requests) {
                if (request.getRequestId() == selectedId) {
                    System.out.println("The amount is : " + request.getAmount() + " INR");
                    System.out.println("Enter CONFIRM (in full caps) to pay");
                    if (sc.nextLine().equals("CONFIRM")) {
                        userModelView.payRequest(request.getAmount(), request.getFromAccNo(), account, selectedId);
                    }
                }
            }
            System.out.println("Wrong Re-quest Id");
        }
    }

    public void noRequestFound() {
        System.out.printf("%50s","---- No request found -----");
        pressAnyKey();
    }


    public void requestPaymentSuccess(long amount, int fromAccNo) {
        System.out.println("Money request payment for account number "+fromAccNo+" fo amount "+amount+" is paid successfully");
        pressAnyKey();
    }


    public void requestPaymentFailed(long amount, int fromAccNo) {
        System.out.println("Money request payment for account number "+fromAccNo+" fo amount "+amount+" is failed due to insufficient funds");
    }

    private void pressAnyKey(){
        System.out.println("\npress any key to go to main menu ");
        sc.nextLine();
        start();
    }

}
