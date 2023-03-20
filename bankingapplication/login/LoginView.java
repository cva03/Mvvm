package mvvmconsole.bankingapplication.login;

import mvvmconsole.bankingapplication.dto.Account;
import mvvmconsole.bankingapplication.user.UserView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class LoginView {

    private LoginModelView loginModelView;
    private Scanner sc=new Scanner(System.in);

    public LoginView(){
        this.loginModelView=new LoginModelView(this);
    }

    public void init(){
        start();
    }

    private void start(){
        System.out.printf("%50s","<------ Welcome to Your Bank ------>\n");
        while(true){
            System.out.println("1. Login");
            System.out.println("2. New User");
            System.out.println("3. Exit");
            System.out.println("Enter your choice");
            int choice=sc.nextInt();
            sc.nextLine();
            try{
                switch (choice){
                    case 1:
                        accountLogin();
                        break;
                    case 2:
                        accountCreation();
                        break;
                    case 3:
                        loginModelView.updateDetails();
                        System.exit(0);
                    default:
                        throw new RuntimeException();
                }
            }catch (Exception e){
                System.out.println("Invalid input!");
            }
        }
    }

    private void accountCreation() {
        System.out.printf("\n%50s","<----- Account creation tab ------>\n");
        String fname,lname,address,nationality,dob;
        int age;
        long adhaarNo;
        while (true) {
            try {
                System.out.println("Enter the first name");
                fname = sc.nextLine();
                System.out.println("Enter the last name");
                lname = sc.nextLine();
                System.out.println("Enter the age");
                age = sc.nextInt();
                sc.nextLine();
                System.out.println("Enter your Address");
                address = sc.nextLine();
                System.out.println("Enter your Nationality");
                nationality = sc.nextLine();
                while (true) {
                    try {
                        System.out.println("Enter your Date of Birth (in DD-MM-YYYY format)");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                        LocalDate date1 = LocalDate.parse(sc.nextLine(), formatter);
                        dob = String.valueOf(date1);
                        break;
                    } catch (Exception e) {
                        System.out.println("invalid date");
                        continue;
                    }
                }
                System.out.println("Enter your Aadhar number");
                adhaarNo = sc.nextLong();
                sc.nextLine();
                break;
            } catch (Exception e) {
                System.out.println("Invalid Inputs");
                continue;
            }
        }
        String password;
        while (true) {
            System.out.println("Enter the password");
            password = sc.nextLine();
            System.out.println("Re-Enter the password");
            if (password.equals(sc.nextLine())) {
                break;
            }
            System.out.println("Password mismatch.");
        }
        Account account = new Account(fname, lname, password, age, address, nationality, dob, adhaarNo);
        loginModelView.createAccount(account);


    }

    private void accountLogin() {
        System.out.printf("\n%50s","<----- Login Page ------>\n");
        try {
            System.out.println("Enter the account number");
            int accNo = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter the password");
            String password=sc.nextLine();
            loginModelView.checkUser(accNo,password);
        }catch (Exception e){
            System.out.println("Invalid input, Re- enter the username and password");
            accountLogin();
        }
    }

    public void accountCreationSuccess(int accountNo) {
        System.out.println("Your account is created successfully");
        System.out.println("Your account number is "+accountNo);
        System.out.println("\n press any key to go to login page");
        sc.nextLine();
        start();
    }

    public void loginSuccess(Account account) {
        System.out.println("Login Successfull");
        System.out.println("Welcome "+account.getFirstName()+" "+account.getLastName());
        UserView uv=new UserView();
        uv.init(account);
    }

    public void loginFail() {
        System.out.println("Invalid credentials \nRe-directing..");
        start();
    }
}
