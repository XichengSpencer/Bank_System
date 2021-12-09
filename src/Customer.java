import java.util.HashMap;

public class Customer extends User {
    private HashMap<String, Account> accountList = new HashMap<>();

    public Customer(String name, String password) {
        super(name, password);
    }

    public void addAccount(String type, Account account) {
        accountList.put(type, account);
    }

    public HashMap<String, Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(HashMap<String, Account> accountList) {
        this.accountList = accountList;
    }
}
