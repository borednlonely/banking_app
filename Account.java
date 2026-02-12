package Application;
import java.util.Random;
import java.util.Scanner;
public abstract class Account {


    protected String name;

    private String socialSN;
    private double balance;

    static int index = 1000;

    public String accountNumber;



    double rate;

    private Scanner scan;

    // constructor
    public Account(String name, String socialSN, double deposit) {

        this.name = name;
        this.socialSN = socialSN;
        balance = deposit;
        this.accountNumber = setAccountNumber();


    }


    // creates account num for user
    private String setAccountNumber() {
        index++;
        String lastTwoSSN = socialSN.substring(socialSN.length()-2);
        int setID = index;
        int randomAccNum = (int)(Math.random() * Math.pow(10,3));


        return lastTwoSSN + setID + randomAccNum;
    }










    // deposit, withdraw, transfer
    public void deposit(double input){
        balance = balance + input;
        System.out.println("DEPOSITING $" + input);
        System.out.printf("CURRENT BALANCE IS: $%.2f\n",balance);
    }
    public void withdraw(double input){
        balance = balance - input;
        System.out.println("WITHDRAWING $" + input);
        System.out.printf("CURRENT BALANCE IS: $%.2f\n",balance);
    }

    public void transfer(String location, double amount){
        balance = balance - amount;
        System.out.println("TRANSFERRING $" + amount + " TO "+location);
        System.out.printf("CURRENT BALANCE IS: $%.2f\n",balance);
    }

    public void info(){
        System.out.printf("\nNAME: %s \nACCOUNT NUMBER: %s \nBALANCE: $%.2f",name,accountNumber,balance);



    }


}
