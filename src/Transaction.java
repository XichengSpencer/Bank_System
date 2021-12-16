import java.io.IOException;
import java.util.Date;

public abstract class Transaction implements TransferInterFace {
    private String fromCustomer;
    private String toCustomer;
    private String fromAccount;
    private String toAccount;
    private String fromCurrency;
    private String toCurrency;

    private Account from;
    private Account to;

    private Date transferTime;
    private double amount;

    private double fee = 0;

    public Transaction(String fromCustomer, String toCustomer, String fromAccount, String toAccount, double amount, String fromCurrency, String toCurrency, Date date) {
        this.fromCustomer = fromCustomer;
        this.toCustomer = toCustomer;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.transferTime = date;
    }

    public Transaction(Account from, Account to, double amount, String fromCurrency, String toCurrency, Date date) {
        this.from = from;
        this.to = to;
        this.fromCustomer = from.getCustomer().getName();
        this.toCustomer = to.getCustomer().getName();
        this.fromAccount = from.getAccountType();
        this.toAccount = to.getAccountType();
        this.amount = amount;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.transferTime = date;
    }

    public Transaction(Account from, Account to, double amount, String fromCurrency, String toCurrency) {
        this.from = from;
        this.to = to;
        this.fromCustomer = from.getCustomer().getName();
        this.toCustomer = to.getCustomer().getName();
        this.fromAccount = from.getAccountType();
        this.toAccount = to.getAccountType();
        this.amount = amount;
        this.fromCurrency = fromCurrency;
        this.toCurrency = toCurrency;
        this.transferTime = new Date();
    }

    public Transaction(Account from, Account to, double amount, String fromCurrency) {
        this(from, to, amount, fromCurrency, "USD");
    }

    public String getFromCustomer() {
        return fromCustomer;
    }

    public String getToCustomer() {
        return toCustomer;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public String getToAccount() {
        return toAccount;
    }

    public String getFromCurrency() {
        return fromCurrency;
    }

    public String getToCurrency() {
        return toCurrency;
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }

    public Date getTransferTime() {
        return transferTime;
    }

    public double getAmount() {
        return amount;
    }

    public double getFee() {
        return fee;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    public void setFrom(Account from) {
        this.from = from;
    }

    public void setTo(Account to) {
        this.to = to;
    }

    public abstract String execute() throws IOException;
}
