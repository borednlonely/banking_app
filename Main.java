package Application;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanny = new Scanner(System.in);
        AccountManagement service = new AccountManagement();


// load csv in here
        String file = "YOUR CSV FILE HERE";

        //  try (Connection conn = db.connect()) {
        //      System.out.println("CONNECTED TO POSTGRES");
        //   } catch (SQLException e) {
        //       e.printStackTrace();
        //   }

//  LOGIN OPTIONS
        System.out.println("Select an option:");
        System.out.print("----------------------\n");
        System.out.println("1. Load existing acounts from CSV");
        System.out.println("2. Create new account");
        System.out.println("3. Login");
        String choice = scanny.nextLine().trim();

        if (choice.equals("1")) {
            List<String[]> newMembers = csv.read(file);

            for (String[] accountMembers : newMembers) {
                String username = accountMembers[0];
                String name = accountMembers[1];
                String sSN = accountMembers[2];
                String accountType = accountMembers[3];
                double deposit = Double.parseDouble(accountMembers[4]);

                AccUser imported = new AccUser();
                imported.user = username;
                imported.password = service.randPassGenerator(imported.passwordLength);
                service.addUser(imported);
                System.out.println("IMPORTED " + username + " PASSWORD: " + imported.password);


                if (accountType.equalsIgnoreCase("Savings")) {
                    service.addAccount(new Saving(name, sSN, deposit), username);
                } else if (accountType.equalsIgnoreCase("Checking")) {
                    service.addAccount(new Checking(name, sSN, deposit), username);
                } else {
                    System.out.println("ERROR READING THE ACCOUNT INFORMATION...........");
                }
            }

        } else if (choice.equals("2")) {
            System.out.println("ENTER YOUR FULL NAME:");
            scanny.nextLine();
            String name = scanny.nextLine();
            System.out.println("ENTER YOUR SSN (9 digits):");
            String ssn = scanny.next();
            System.out.println("CHECKING OR SAVINGS?");
            String type = scanny.next();
            System.out.println("INITIAL DEPOSIT AMOUNT:");
            double deposit = scanny.nextDouble();

            Account newAccount;
            if (type.equalsIgnoreCase("Checking")) {
                newAccount = new Checking(name, ssn, deposit);
            } else {
                newAccount = new Saving(name, ssn, deposit);
            }

            AccUser newUser = new AccUser();
            service.registerAccount(newAccount, newUser);
            System.out.println("ACCOUNT CREATED! YOUR USERNAME IS: " + newUser.user);
            System.out.println("YOUR PASSWORD IS: " + newUser.password);

        } else if (choice.equals("3")) {
            AccUser login = service.Login();
            if (login != null) {
                while (true) {
                    System.out.println("\nWECLOME " + login.user + " :D ");
                    System.out.println("Choose an option below: ");
                    System.out.println("1. View accounts");
                    System.out.println("2. Deposit");
                    System.out.println("3. Withdraw");
                    System.out.println("4. Logout");
                    String pick = scanny.nextLine();

                    if (pick.equals("1")) {
                        for (Account a : login.getAcc()) {
                            a.info();
                        }
                    } else if (pick.equals("2") || pick.equals("3")) {
                        System.out.println("HERE ARE YOUR ACCOUNTS: ");
                        for (int i = 0; i < login.getAcc().size(); i++) {
                            System.out.print((i + 1) + ".");
                            login.getAcc().get(i).info();
                        }
                        System.out.println("\nPLEASE CHOOSE ACCOUNT (1-" + login.getAcc().size() + "):");
                        int ider = Integer.parseInt(scanny.nextLine()) - 1;

                        if (ider < 0 || ider > login.getAcc().size()) {

                            System.out.println("SORRY, NO SUCH ACCOUNT EXISTS :[");
                            continue;
                        }

                        System.out.println("Enter Amount please: ");
                        double amount = Double.parseDouble(scanny.nextLine());

                        if (pick.equals("2")) {
                            login.getAcc().get(ider).deposit(amount);

                        } else {
                            login.getAcc().get(ider).withdraw(amount);
                        }

                    } else if (pick.equals("4")) {
                        System.out.println("THANK YOU FOR BANKING WITH US. GOODBYE...." + login.user);
                        break;
                    } else {
                        System.out.println("INVALID OPTION!!!!");
                    }

                }
            }



        }


    }
}
