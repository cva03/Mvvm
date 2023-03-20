package mvvmconsole.crmapp.login;


import mvvmconsole.crmapp.admin.AdminView;
import mvvmconsole.crmapp.dto.Admin;

import java.util.Scanner;

public class LoginView  {
    private LoginModelView loginModelView;
    private Scanner sc=new Scanner(System.in);
    public LoginView(){
        this.loginModelView=new LoginModelView(this);
    }



    public void init(){
        start();
    }

    private void start() {
        System.out.printf("\n%70s", "<------ Welcome to CRM Appliation ------>");
        while (true) {
            System.out.println("\n1. Admin login");
            System.out.println("2. Admin Signup");
            System.out.println("0. Exit");
            System.out.println("Enter your choice");
            try {
                int choice = sc.nextInt();
                sc.nextLine();
                switch (choice) {
                    case 1:
                        adminLogin();
                        break;
                    case 2:
                        createAdmin();
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 0:
                        loginModelView.close();
                        System.exit(0);
                    default:
                        throw new RuntimeException();
                }
            }catch (Exception e){
                System.out.println("Invalid Input!");
            }
        }
    }

    private void adminLogin() {
        System.out.printf("%50S\n","<-- Admin login page -->");
        System.out.println("Enter the Admin name :");
        String userName=sc.nextLine();
        System.out.println("Enter the password :");
        String password=sc.nextLine();
        loginModelView.verifyAdmin(userName,password);
    }

    private void createAdmin() {
        System.out.printf("%50S\n","<-- Sign up -->");
        System.out.println("Enter the admin name ");
        String adminName=sc.nextLine();
        while(true) {
            System.out.println("Enter the password ");
            String password = sc.nextLine();
            System.out.println("Re-enter the password");
            if (password.equals(sc.nextLine())) {
                Admin admin= new Admin(adminName,password);
                loginModelView.addAdmin(admin);
                break;
            }else{
                System.out.println("Password doesn't match");
            }
        }
    }

    public void adminCreationSuccessfull() {
        System.out.println("Admin creation successfull");
        start();
    }

    public void adminCreationFailed() {
        System.out.println("Admin name already exist!");
        start();
    }

    public void adminVerificationSuccess(Admin admin) {
        System.out.println("Admin verification successfull");
        AdminView av=new AdminView();
        av.init(admin);
    }

    public void adminVerificationFailed() {
        System.out.println("Invalid Credentials!");
        start();
    }
}
