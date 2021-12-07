import java.util.HashMap;

public class Customer extends User {
    private HashMap<String, Account> accounts = new HashMap<>();

    public Customer(String name, String password) {
        super(name, password);
    }
}
