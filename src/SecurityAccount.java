public class SecurityAccount extends Account {
    public SecurityAccount(Customer customer) {
        super("security", customer);
    }

    public SecurityAccount(Customer customer, String currency, double amount) {
        super("security", customer, currency, amount);
    }

    public SecurityAccount(Customer customer, double amount) {
        super("security", customer, amount);
    }
}
