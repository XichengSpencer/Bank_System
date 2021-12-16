public class CheckingAccount extends Account {

    public CheckingAccount(String accountType, Customer customer) {
        super(accountType, customer);
    }

    public CheckingAccount(String accountType, Customer customer, String currency, double amount) {
        super(accountType, customer, currency, amount);
    }

    public CheckingAccount(Customer customer, double amount) {
        super(customer, amount);
    }

    public CheckingAccount(Customer customer) {
        super(customer);
    }
}
