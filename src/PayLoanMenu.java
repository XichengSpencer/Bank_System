import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class PayLoanMenu implements ActionListener {

    private String userName;
    private JFrame frame;
    private JComboBox<String> currencySelect;
    private JTextField amountInput;
    private JButton payButton;

    private JButton logOutButton;
    private JButton returnButton;
    private JLabel warningLabel = new JLabel();

    private JFrame previous;
    HashMap<String, Double> loanStatus;
    Customer customer;

    public PayLoanMenu(String userName, JFrame previous) {
        CustomerData customerData = CustomerData.getInstance();
        this.customer = customerData.selectCustomer(userName);

        this.userName = userName;
        this.previous = previous;

        JLabel userLabel = new JLabel(userName);
        JLabel loanLabel = new JLabel("Current loan status: ");
        JLabel RMBLabel = new JLabel("RMB: ");
        JLabel USDLabel = new JLabel("USD: ");
        JLabel GBPLabel = new JLabel("GBP: ");

        loanStatus = LoanData.getAllLoan(customer);
        JLabel RMBLoanLabel = new JLabel(""+loanStatus.get("RMB"));
        JLabel USDLoanLabel = new JLabel(""+loanStatus.get("USD"));
        JLabel GBPLoanLabel = new JLabel(""+loanStatus.get("GBP"));
        if (LoanData.getAllLoan(customer) == null){
            RMBLoanLabel = new JLabel("0");
            USDLoanLabel = new JLabel("0");
            GBPLoanLabel = new JLabel("0");
        }


        JLabel currencyLabel = new JLabel("Currency: ");
        currencySelect = new JComboBox<>();
        JLabel amountInputLabel = new JLabel("Pay Amount: ");
        amountInput = new JTextField();
        logOutButton = new JButton("Log Out");
        returnButton = new JButton("Return");
        payButton = new JButton("Pay");

        userLabel.setBounds(200,10,130,20);
        userLabel.setFont(new Font(null,Font.ITALIC,20));
        loanLabel.setBounds(30, 50, 130, 12);
        RMBLabel.setBounds(30,70, 50, 12);
        RMBLoanLabel.setBounds(80,70, 70, 12);
        USDLabel.setBounds(150,70, 50, 12);
        USDLoanLabel.setBounds(200,70, 70, 12);
        GBPLabel.setBounds(270,70, 50, 12);
        GBPLoanLabel.setBounds(320,70, 70, 12);

        amountInputLabel.setBounds(100, 100, 100, 25);
        amountInput.setBounds(100, 130, 100, 25);
        currencyLabel.setBounds(210, 100, 100, 25);
        currencySelect.setBounds(210, 130, 100, 25);
        payButton.setBounds(270, 170, 100, 25);

        returnButton.setBounds(20,230,80,25);
        logOutButton.setBounds(120,230,80,25);

        warningLabel.setBounds(20, 170, 300, 25);

        currencySelect.addItem("--Currency--");
        currencySelect.addItem("USD");
        currencySelect.addItem("RMB");
        currencySelect.addItem("GBP");

        frame = new JFrame("Pay Loan Menu");
        frame.add(userLabel);
        frame.add(loanLabel);
        frame.add(RMBLabel);
        frame.add(USDLabel);
        frame.add(GBPLabel);
        frame.add(RMBLoanLabel);
        frame.add(USDLoanLabel);
        frame.add(GBPLoanLabel);
        frame.add(currencyLabel);
        frame.add(currencySelect);
        frame.add(amountInputLabel);
        frame.add(amountInput);
        frame.add(logOutButton);
        frame.add(returnButton);
        frame.add(payButton);
        frame.add(warningLabel);

        returnButton.addActionListener(this);
        logOutButton.addActionListener(this);
        payButton.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (returnButton.equals(e.getSource())) {
            frame.dispose();
            previous.setVisible(true);
        }
        if (logOutButton.equals(e.getSource())) {
            frame.dispose();
            new Login(new LoginData());
        }

        if (payButton.equals((e.getSource()))){
            if (LoanData.getAllLoan(customer) == null){
                warningLabel.setText("You don't have any loan to pay");
                return;
            }
            double amount = Integer.parseInt(amountInput.getText());
            String currency = (String) currencySelect.getSelectedItem();
            double accountBalance = 0;

            if (currency.equals("--Currency--")){
                warningLabel.setText("Please select a currency!");
                return;
            }
            AccountData accountData = AccountData.getInstance();
            HashMap<String, Account> accountList =  accountData.getAccountList(customer);
            for (Account account : accountList.values()) {
                if (account.getAccountType()=="checking"){
                    accountBalance = account.getTotalAmount().get(currency);
                }
            }

            if (accountBalance < amount){
                warningLabel.setText("Insufficient Balance!");
            }

            LoanData.updateAccount(customer, "checking", currency, 0-amount);

            LoanData.updateLoan(customer, "checking", 0-amount, currency);

            warningLabel.setText("Paid!");
        }
    }
}
