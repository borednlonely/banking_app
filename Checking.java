package Application;

public class Checking extends Account {
private int debitCardNum;
private int debitPin;
    public Checking(String name, String socialSN, double deposit){
        super(name,socialSN,deposit);

        accountNumber = "1" + accountNumber;
        setDebitCard();


    }
    // generates debit card # and pin
    private void setDebitCard(){
        debitCardNum = (int)(Math.random() * Math.pow(10,15));
        debitPin = (int)(Math.random() * Math.pow(10,4));

    }

    public void info(){
        super.info();
        System.out.println("\nYour Checking Account Info:  "+ "\n DEBIT CARD: " + debitCardNum +
                "\n DEBIT CARD PIN: "+ debitPin);
    }


}
