package Application;

import java.util.List;
import java.util.Scanner;

public class Main {
public static void main(String[] args){

Scanner scanny = new Scanner(System.in);
AccountManagement service = new AccountManagement();
// load csv in here
String file = "";

// note done yet with the login options
System.out.println("Select an option:");
System.out.print("----------------------");
System.out.println("1. Load existing acounts from CSV");
System.out.println("2. Create new account");
System.out.println("3. Login");
String choice = scanny.next();

if(choice.equals("1")) {
    List<String[]> newMembers = csv.read(file);

    for (String[] accountMembers : newMembers) {
        String username = accountMembers[0];
        String name = accountMembers[1];
        String sSN = accountMembers[2];
        String accountType = accountMembers[3];
        double deposit = Double.parseDouble(accountMembers[4]);


        if (accountType.equalsIgnoreCase("Savings")) {
            service.addAccount(new Saving(name, sSN, deposit));
        } else if (accountType.equalsIgnoreCase("Checking")) {
            service.addAccount(new Checking(name, sSN, deposit));
        } else {
            System.out.println("ERROR READING THE ACCOUNT INFORMATION...........");
        }
    }
} else if (choice.equals("2")){

} else if (choice.equals("3")){
    AccUser login = service.Login();
}


service.printAccounts();



}


}
