package Application;

import java.util.ArrayList;
import java.util.List;

public class AccUser {
    protected String user;
    protected String password;

    protected int passwordLength = 32;

    // stores all user's accs
    private List<Account> accounts = new ArrayList<>();

    //returns user's linked accs
    public List<Account> getAcc(){
        return accounts;
    }

    // links an account to user
    void addAccount(Account acc){
        accounts.add(acc);
    }

}
