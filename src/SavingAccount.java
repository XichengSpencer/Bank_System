public class SavingAccount extends Account {
    public SavingAccount(String accountType, Customer customer) {
        super(accountType, customer);
    }

    public SavingAccount(String accountType, Customer customer, String currency, double amount) {
        super(accountType, customer, currency, amount);
    }

    public SavingAccount(Customer customer, double amount) {
        super(customer, amount);
    }

    public SavingAccount(Customer customer) {
        super(customer);
    }
}
