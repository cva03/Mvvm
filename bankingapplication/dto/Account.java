package mvvmconsole.bankingapplication.dto;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private int accountNumber;
    private String firstName;
    private String lastName;
    private String password;
    private int age;
    private String address;
    private String nationality;
    private String dob;
    private long aadharNumber;
    private long balance;
    private List<TransactionHistory> transactionHistory;
    private List<MoneyRequest> moneyRequest;
    private List<MoneyRequest> moneyRequestsent;

    public void setPassword(String password) {
        this.password = password;
    }

    public Account(){

    }

    public Account(String firstName, String lastName, String password, int age, String address, String nationality, String dob, long aadharNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.age = age;
        this.address = address;
        this.nationality = nationality;
        this.dob = dob;
        this.aadharNumber = aadharNumber;
        this.balance=10000;
        this.transactionHistory =new ArrayList<>();
        this.moneyRequest=new ArrayList<>();
        this.moneyRequestsent=new ArrayList<>();
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public String getNationality() {
        return nationality;
    }

    public String getDob() {
        return dob;
    }

    public long getAadharNumber() {
        return aadharNumber;
    }

    public long getBalance() {
        return balance;
    }

    public List<TransactionHistory> getTransactionHistory() {
        return transactionHistory;
    }

    public List<MoneyRequest> getMoneyRequest() {
        return moneyRequest;
    }

    public void addMoney(long amount){
        this.balance+=amount;
    }

    public void removeMoney(long amount){
        this.balance-=amount;
    }

    public void addTransactionHistory(TransactionHistory tran){
        this.transactionHistory.add(tran);
    }

    public void addMoneyRequest(MoneyRequest request){
        this.moneyRequest.add(request);
    }

    public void addMoneyRequestsent(MoneyRequest request){
        this.moneyRequestsent.add(request);
    }

    public List<MoneyRequest> getMoneyRequestsent() {
        return moneyRequestsent;
    }

}
