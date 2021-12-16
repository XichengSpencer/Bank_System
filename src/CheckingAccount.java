public class CheckingAccount extends Account {

    public CheckingAccount(Customer customer) {
        super("checking", customer);
    }

    public CheckingAccount(Customer customer, String currency, double amount) {
        super("checking", customer, currency, amount);
    }

    public CheckingAccount(Customer customer, double amount) {
        super(customer, amount);
    }

}
