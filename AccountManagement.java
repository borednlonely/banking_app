package Application;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;



public class AccountManagement {


    private Map<String, Account> accs = new HashMap<>();
    //hashmap stores users by username
    private Map<String, AccUser> users = new HashMap<>();
    // Scanny the Scanner :P
    private Scanner scanny = new Scanner(System.in);
    private String passwordCharacterList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%^&*";
    // stores account in hashmap with account number
    public void addAccount(Account account) {
        accs.put(account.accountNumber, account);

    }
    // stores users in hashmap, val = username, password, linked accs
    void addUser(AccUser account) {
        users.put(account.user, account);
    }

    public Account findAccountNumber(String accountNumber) {
        return accs.get(accountNumber);
    }

    // register account. user creation, pass creation, duplicate username check using hashmap
    public void registerAccount(Account account, AccUser use) {
        use.user = username(account.name);
        use.password = password(use.passwordLength);
        addAccount(account);
        // uses hasmap for username check/duplicate
        while (users.containsKey(use.user)) {
            System.out.println("USERNAME TAKEN, TRY AGAIN");
            use.user = username(account.name);
        }
        addUser(use);
        // adds the account to the user List
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

    // password reset
    void passwordReset(AccUser acc, String newPassword) {
        acc.password = newPassword;
    }

 // main login: asks for creds, uses hashmap for lookup instead of looping through all users
    // returns AccUser if found, null if failed
    AccUser Login() {
        System.out.println("WELCOME TO GENERIC BANK APP (trademark pending)");
        System.out.println("DO YOU HAVE A LOGIN, YES or NO?");
        String loginQuestion = scanny.next();
        if (loginQuestion.equalsIgnoreCase("YES")) {
            System.out.println("ENTER USERNAME:");
            String username = scanny.next();
            System.out.println("ENTER PASSWORD:");
            String passer = scanny.next();
            // lookup by user key
            AccUser search = users.get(username);
            if (search != null && search.password.equals(passer)) {
                System.out.println("LOGIN WORKED");
                return search;
            } else {
                System.out.println("FAILED LOGIN");
                return null;
            }


        } else {
            return null;
        }
    }

        // prints all accounts stored in the hashmap
        public void printAccounts () {
            for (Account i : accs.values()) {
                System.out.println("\n----------------------------------------");
                i.info();
            }
        }



}

