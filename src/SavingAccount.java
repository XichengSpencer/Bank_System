public class SavingAccount extends Account {
    public SavingAccount(Customer customer) {
        super("saving", customer);
    }

    public SavingAccount(Customer customer, String currency, double amount) {
        super("saving", customer, currency, amount);
    }

    public SavingAccount(Customer customer, double amount) {
        super(customer, amount);
    }
}
