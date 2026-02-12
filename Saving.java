package Application;

public class Saving extends Account {

    public Saving(String name, String socialSN, double deposit){
        super(name,socialSN,deposit);

        accountNumber = "2" + accountNumber;

    }



}
