import java.io.IOException;
import java.util.HashMap;

public class Transfer extends Transaction{
    public Transfer(Account from, Account to, double amount, String fromCurrency) {
        super(from, to, amount, fromCurrency);
    }

    @Override
    public String execute() throws IOException {
        String msg = "";
        // too much
        if (getAmount() > getFrom().getTotalAmount().get(getFromCurrency())){
            double amount = getFrom().getTotalAmount().get(getFrom().getAccountType());
            msg =  "Sorry you only have" + amount;
        }
        // deposit
        else if (getTo() == null){
            msg = "You can't transfer to a empty account.";
        }else{
            // pay from checking
            if (getFrom().getTotalAmount().get(getFromCurrency()) < getAmount() * 1.05) {
                msg = "Not enough money to pay for the transaction fee.";
            } else {
                AccountData accountData = AccountData.getInstance();
                HashMap<String, Account> accounts = accountData.getAccountList(getFrom().getCustomer());
                // TODO transaction Data:
                TransactionData transactionData = TransactionData.getInstance();
                transactionData.doTransaction(this);
            }
        }
        return msg;
    }
}
