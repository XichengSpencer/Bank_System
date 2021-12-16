import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Account {
    private String accountType;
    private String id;
    private Customer customer;
    private HashMap<String, Double> totalAmount =new HashMap<>();
    private static volatile int Guid = 100;

    public Account(String accountType, Customer customer) {
        this.accountType = accountType;
        this.id = getGuid();
        this.customer = customer;
        initializeAmount();
    }

    public Account(String accountType, Customer customer, String id) {
        this.accountType = accountType;
        this.id = id;
        this.customer = customer;
        initializeAmount();
    }

    public static String getGuid()
    {
        Account.Guid += 1;

        long now = System.currentTimeMillis();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
        String time = dateFormat.format(now);
        String info = now + "";
        int ran = 0;
        if (Account.Guid > 999)
        {
            Account.Guid = 100;
        }
        ran = Account.Guid;

        return time + info.substring(2, info.length()) + ran;
    }

    public Account(String accountType, Customer customer, String currency, double amount) {
        this(accountType, customer);
        addAmount(currency, amount);
    }

    public Account(String accountType, Customer customer, String currency, double amount, String id) {
        this(accountType, customer);
        addAmount(currency, amount);
        this.id = id;
    }

    public Account(Customer customer, double amount) {
        this("saving", customer);
        addAmount("USD", amount);
    }

    public Account(Customer customer, double amount, String id) {
        this("saving", customer, id);
        addAmount("USD", amount);
    }

    public Account(Customer customer) {
        this("saving", customer);
    }
    public Account(Customer customer, String id) {
        this("Saving", customer, id);
    }

    protected void initializeAmount() {
        totalAmount.put("USD", 0.0);
        totalAmount.put("RMB", 0.0);
        totalAmount.put("GBP", 0.0);
    }

    protected void addAmount(String currency, double amount) {
        if (totalAmount.containsKey(currency)) {
            double currentAmount = totalAmount.get(currency);
            totalAmount.replace(currency, currentAmount, currentAmount + amount);
        } else {
            totalAmount.put(currency, amount);
        }
    }

    protected void reduceAmount(String currency, double amount) {
        assert totalAmount.containsKey(currency) : "This account doesn't have this type of currency!";
        assert totalAmount.get(currency) - amount < 0 : "Insufficient balance";

        double currentAmount = totalAmount.get(currency);
        totalAmount.replace(currency, currentAmount, currentAmount - amount);
    }

    public String getAccountType() {
        return accountType;
    }

    public String getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public HashMap<String, Double> getTotalAmount() {
        return totalAmount;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setTotalAmount(HashMap<String, Double> totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountType='" + accountType + '\'' +
                ", id='" + id + '\'' +
                ", customer=" + customer +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
