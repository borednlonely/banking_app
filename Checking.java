package Application;

public class Checking extends Account {
private long debitCardNum;
private int debitPin;
    public Checking(String name, String socialSN, double deposit){
        super(name,socialSN,deposit);

        accountNumber = "1" + accountNumber;
        setDebitCard();


    }
    // load existing accounts
    public Checking(String accountNumber, String name, String socialSN, double balance, long debitCardNum, int debitPin) {
        super(accountNumber, name, socialSN, balance);
        this.debitCardNum = debitCardNum;
        this.debitPin = debitPin;
    }

    // generates debit card # and pin
    private void setDebitCard(){
        debitCardNum = (long)(Math.random() * Math.pow(10,15));
        debitPin = (int)(Math.random() * Math.pow(10,4));

    }

    public long getDebitCardNum() {return debitCardNum; }
    public int getDebitPin() {return debitPin; }

    public void info(){
        super.info();
        System.out.println("\nYour Checking Account Info:  "+ "\n DEBIT CARD: " + debitCardNum +
                "\n DEBIT CARD PIN: "+ debitPin);
    }


}
