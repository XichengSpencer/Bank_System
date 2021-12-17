import java.io.IOException;
import java.util.HashMap;

public class Transfer extends Transaction {
    public Transfer(Account from, Account to, double amount, String fromCurrency) {
        super(from, to, amount, fromCurrency);
    }

    @Override
    public String execute() throws IOException {
        String msg = "";
        double amount = getFrom().getTotalAmount().get(getFromCurrency());
        // too much
        if (getAmount() > amount) {
            msg = "Sorry you only have" + amount;
        }
        // deposit
        else if (getTo() == null) {
            msg = "You can't transfer to a empty account.";
        } else {
            if (!getTo().getCustomer().equals(getFrom().getCustomer())) {
                // transfer between different user, will charge fee
                if (amount < getAmount() * 1.05) {
                    msg = "Not enough money to pay for the transaction fee.";
                } else {
                    AccountData accountData = AccountData.getInstance();
                    HashMap<String, Account> accounts = accountData.getAccountList(getFrom().getCustomer());
                    // TODO transaction Data:
                    TransactionData transactionData = TransactionData.getInstance();
                    transactionData.doTransaction(this, true);
                    msg = "Transfer Complete!";
                }
            } else {
                // transfer between same user, won't charge fee
                AccountData accountData = AccountData.getInstance();
                HashMap<String, Account> accounts = accountData.getAccountList(getFrom().getCustomer());
                // TODO transaction Data:
                TransactionData transactionData = TransactionData.getInstance();
                transactionData.doTransaction(this, false);
                msg = "Transfer Complete!";
            }
        }
        return msg;
    }
}
