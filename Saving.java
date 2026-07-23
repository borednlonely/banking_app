package Application;

public class Saving extends Account {

    // CREATE - brand new account (your original, unchanged)
    public Saving(String name, String socialSN, double deposit){
        super(name,socialSN,deposit);

        accountNumber = "2" + accountNumber;

    }

    // load existing accounts
    public Saving(String accountNumber, String name, String socialSN, double balance){
        super(accountNumber, name, socialSN, balance);
    }

}
