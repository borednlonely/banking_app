package Application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;



public class AccountManagement {

    // Scanny the Scanner :P
    private Scanner scanny = new Scanner(System.in);
    private String passwordCharacterList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*";


    // uses username as foreign key for db
    // db rejects accounts whose owner doesnt exist
    public void addAccount(Account account, String username) {
        String sql = "INSERT INTO accounts (account_number, username, account_type, name, ssn, balance, debit_card_num, debit_pin) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = db.connect();

             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.accountNumber);
            ps.setString(2, username);
            // sql doesnt have subclasses so checking/savings accs become type string
            ps.setString(3, (account instanceof Checking) ? "Checking" : "Savings");
            ps.setString(4, account.name);
            ps.setString(5, account.getSocialSN());
            ps.setDouble(6, account.getBalance());
            if (account instanceof Checking) {
                Checking c = (Checking) account;
                ps.setString(7, String.valueOf(c.getDebitCardNum()));
                ps.setString(8, String.valueOf(c.getDebitPin()));
            } else {
                ps.setString(7, null);   // savings has no deb card so = NULL columns
                ps.setString(8, null);
            }
            ps.executeUpdate();  //inserts account row and sets info to db
        } catch (SQLException e) {
            System.out.println("COULDN'T SAVE ACCOUNT");
            e.printStackTrace();
        }
    }
    // inserts a user row with primary key being the username
    //rejects duplicates additions :D
    void addUser(AccUser account) {
        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, account.user);       // fills the first ?
            ps.setString(2, account.password);   // fills the second ?
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("COULDN'T SAVE USER");
            e.printStackTrace();
        }
    }

    // checks if username is already in use
    // select 1 for every matching row that exists w that username. 0 = means none
    private boolean usernameTaken(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username); // fills with the desired username for our db to search
            ResultSet rs = ps.executeQuery(); //this is where postgres actually searches
            return rs.next(); //checks if anything came back or not
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }


    //we got full signup here :D, make a free username, password, then save it!
    //keep in mind that user row needs to exist bevore the account row references it
    public void registerAccount(Account account, AccUser use) {
        use.user = username(account.name);
        while (usernameTaken(use.user)) {
            System.out.println("USERNAME TAKEN, TRY AGAIN");
            use.user = username(account.name);
        }
        use.password = password(use.passwordLength);
        addUser(use);
        addAccount(account, use.user);
        use.addAccount(account);
    }

    // create your own username or have one generated
    protected String username(String username) {
        System.out.println("CREATE A NEW USERNAME: Enter Yes to create one, or No to have one generated for you");
        String answer = scanny.next();
        if (answer.equalsIgnoreCase("Yes")) {
            System.out.println("ENTER YOUR USERNAME: ");
            return username = scanny.next();
        } else {
            System.out.println("generating your username.......");
            return new String(randomUserNameGen(username));
        }

    }
    // create your own password or have one generated
    protected String password(int password) {
        System.out.println("WOULD YOU LIKE TO CREATE YOUR OWN PASSWORD? Yes or No?");
        String answer = scanny.next();
        if (answer.equalsIgnoreCase("Yes")) {
            System.out.println("Enter a password: ");
            return scanny.next();

        } else {
            System.out.println("Invalid input, generating random password......");
            return randPassGenerator(password);
        }

    }
    //generates username with random numbers + initials from name
    private String randomUserNameGen(String username) {
        String[] user = username.split(" ");

        String characters = "";

        for (String letters : user) {
            if (!letters.isEmpty()) {
                characters += letters.charAt(0);
            }
        }
        Random randy = new Random();
        int rand = randy.nextInt(100);
        username = characters + rand;
        return username;
    }
    // generates random password using passwordCharacterList
    protected String randPassGenerator(int password) {
        char[] pass = new char[password];
        for (int i = 0; i < password; i++) {
            int randPassCreate = (int) (Math.random() * passwordCharacterList.length());
            pass[i] = passwordCharacterList.charAt(randPassCreate);
        }
        return new String(pass);
    }

    // password reset not working yet.
    void passwordReset(AccUser acc, String newPassword) {
        acc.password = newPassword;
    }

 // SELECTs the stored password for that username and compares it. No rows back = no user. returns AccUser if found, null if failed
 AccUser Login() {
     System.out.println("WELCOME TO GENERIC BANK APP (trademark pending)");
     System.out.println("ENTER USERNAME:");
     String username = scanny.next();
     System.out.println("ENTER PASSWORD:");
     String passer = scanny.next();

     String sql = "SELECT password FROM users WHERE username = ?";
     try (Connection conn = db.connect();
          PreparedStatement ps = conn.prepareStatement(sql)) {
         ps.setString(1, username);
         ResultSet rs = ps.executeQuery();
         if (rs.next() && rs.getString("password").equals(passer)) {
             System.out.println("LOGIN WORKED");
             AccUser found = new AccUser();
             found.user = username;
             found.password = passer;
             loadAccounts(found);
             return found;
         }
     } catch (SQLException e) {
         e.printStackTrace();
     }
     System.out.println("FAILED LOGIN");
     return null;
 }
    // rebuilds this user's account as real objects from their rows
    // uses the loading constructors so numbers/cards are copied not regen'd
    //type column decides which subclass to build :P
    private void loadAccounts(AccUser user) {
        String sql = "SELECT * FROM accounts WHERE username = ? ORDER BY account_number";
        try (Connection conn = db.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.user);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String accountNumber = rs.getString("account_number");
                String name = rs.getString("name");
                String ssn = rs.getString("ssn");
                double balance = rs.getDouble("balance");
                String type = rs.getString("account_type");

                if (type.equals("Checking")) {
                    long card = Long.parseLong(rs.getString("debit_card_num"));
                    int pin = Integer.parseInt(rs.getString("debit_pin"));
                    user.addAccount(new Checking(accountNumber, name, ssn, balance, card, pin));
                } else {
                    user.addAccount(new Saving(accountNumber, name, ssn, balance));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }






        // prints all accounts stored from the DB
        public void printAccounts() {
            String sql = "SELECT account_number, name, account_type, balance FROM accounts";
            try (Connection conn = db.connect();
                 PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println("\n----------------------------------------");
                    System.out.printf("NAME: %s \nACCOUNT NUMBER: %s (%s) \nBALANCE: $%.2f\n",
                            rs.getString("name"),
                            rs.getString("account_number"),
                            rs.getString("account_type"),
                            rs.getDouble("balance"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


}

