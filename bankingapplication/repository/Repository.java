package mvvmconsole.bankingapplication.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mvvmconsole.bankingapplication.dto.Account;
import mvvmconsole.bankingapplication.dto.MoneyRequest;
import mvvmconsole.bankingapplication.dto.TransactionHistory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Repository {

    private static Repository data;
    private HashMap<Integer, Account> accountDetails=new HashMap<>();

    private Repository(){
       // createAccount(new Account("Siva", "subramaniyan", "siva@zoho", 23, "30,vaigai colony, Annanagar", "Indian", "29-08-2000", 12345678987L));
       // createAccount(new Account("Praveen", "kumar", "pk@zoho", 23, "31,vaigai colony, Annanagar", "Indian", "06-10-2000", 12345678988L));
        readFile();
    }

    private void readFile() {
        ObjectMapper obj=new ObjectMapper();

        try {
            List<Account> accounts = obj.readValue(new File("/Users/cva/IdeaProjects/Practice/src/com.console/bankingapplication/repository/accountdetails.json"), new TypeReference<List<Account>>() {});;
            for(Account account:accounts){
                accountDetails.put(account.getAccountNumber(),account);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeFile(){
        System.out.println("writing");
        ObjectMapper obj=new ObjectMapper();
        List<Account> accounts=new ArrayList<>();
        for(Map.Entry<Integer, Account> map: accountDetails.entrySet()){
            accounts.add(map.getValue());
        }
        try {
            obj.writeValue(new File("/Users/cva/IdeaProjects/Practice/src/com.console/bankingapplication/repository/accountdetails.json"), accounts);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Repository getInstance(){
        if(data==null){
            data=new Repository();
        }
        return data;
    }


    public int createAccount(Account account) {
        for(int i=100000001;i<1000000000;i++){
            if(!accountDetails.containsKey(i)){
                account.setAccountNumber(i);
                accountDetails.put(i,account);
                return i;
            }
        }
        return 0;
    }

    public Account checkUser(int accNo, String password) {
        if(accountDetails.get(accNo).getPassword().equals(password)) {
            return accountDetails.get(accNo);
        }
        return null;
    }


    public String checkTransferAcc(int accNo) {
        if(accountDetails.containsKey(accNo)){
           return accountDetails.get(accNo).getFirstName()+" "+accountDetails.get(accNo).getLastName();
        }
        return " ";
    }

    public boolean sendAmount(long amount, int accNo, Account account) {
        if(accountDetails.get(account.getAccountNumber()).getBalance()<amount){
            return false;
        }
        System.out.println();
        account.removeMoney(amount);
        accountDetails.get(accNo).addMoney(amount);
        TransactionHistory tran1=new TransactionHistory(("Money transferr to account no :"+accNo),amount, account.getBalance());
        accountDetails.get(account.getAccountNumber()).addTransactionHistory(tran1);
        TransactionHistory tran2=new TransactionHistory(("Money received from account no :"+account.getAccountNumber()),amount,accountDetails.get(accNo).getBalance());
        accountDetails.get(accNo).addTransactionHistory(tran2);
        return true;
    }

    public void changePassword(String password, Account account) {
        account.setPassword(password);
        accountDetails.put(account.getAccountNumber(),account);
    }

    public List<TransactionHistory> getTransactions(Account account) {
        return accountDetails.get(account.getAccountNumber()).getTransactionHistory();
    }

    public long getBalance(Account account) {
        return accountDetails.get(account.getAccountNumber()).getBalance();
    }

    public void requestMoney(long amount, int accNo, String description, Account account) {
        MoneyRequest request=new MoneyRequest(amount,account.getAccountNumber(),description);
        MoneyRequest sent=new MoneyRequest(amount,accNo,description);
        sent.setRequestId(request.getRequestId());
        accountDetails.get(account.getAccountNumber()).addMoneyRequestsent(sent);
        accountDetails.get(accNo).addMoneyRequest(request);
    }

    public List<MoneyRequest> findMoneyRequest(Account account) {
        return accountDetails.get(account.getAccountNumber()).getMoneyRequest();
    }

    public boolean payRequest(long amount, int accNo, Account account, int selectedId) {
        if(accountDetails.get(account.getAccountNumber()).getBalance()<amount){
            return false;
        }
        account.removeMoney(amount);
        accountDetails.get(accNo).addMoney(amount);
        TransactionHistory tran1=new TransactionHistory(("Money request paid to :"+accNo),amount, account.getBalance());
        accountDetails.get(account.getAccountNumber()).addTransactionHistory(tran1);
        TransactionHistory tran2=new TransactionHistory(("Money request paid from :"+account.getAccountNumber()),amount,accountDetails.get(accNo).getBalance());
        accountDetails.get(accNo).addTransactionHistory(tran2);
        for(int i=0;i< accountDetails.get(accNo).getMoneyRequest().size();i++){
            if(accountDetails.get(accNo).getMoneyRequest().get(i).getRequestId()==selectedId){
                accountDetails.get(accNo).getMoneyRequest().get(i).moneyPaid();
            }
        }
        for(int i=0;i< accountDetails.get(account.getAccountNumber()).getMoneyRequest().size();i++){
            if(accountDetails.get(account.getAccountNumber()).getMoneyRequest().get(i).getRequestId()==selectedId){
                accountDetails.get(account.getAccountNumber()).getMoneyRequest().get(i).moneyPaid();
            }
        }
        return true;
    }

    public void updateDetails() {
        writeFile();
    }
}
