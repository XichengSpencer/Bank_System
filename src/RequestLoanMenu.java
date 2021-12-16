import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class RequestLoanMenu implements ActionListener {

    private String userName;
    private JTextField amountInput;
    private JButton loanButton;
    private JComboBox<String> allAccount;
    private JComboBox<String> currencySelect;

    private JLabel warningLabel = new JLabel();

    private JButton logOutButton;
    private JButton returnButton;

    private JFrame frame;
    private JFrame previous;
    private Customer customer;

    public RequestLoanMenu(String userName, JFrame previous) {

        CustomerData customerData = CustomerData.getInstance();
        this.customer = customerData.selectCustomer(userName);

        this.previous = previous;
        this.userName = userName;
        JLabel userLabel = new JLabel(userName);
        JLabel message1 = new JLabel("Please input the amount of Loan you want to request.");
        JLabel message2 = new JLabel ("All Loan will be charged with 20% interest rate");
        JLabel amount = new JLabel("Loan Amount:");
        JLabel toAccount = new JLabel("Loan to Account:");
        JLabel currencyLabel = new JLabel("Currency: ");
        amountInput = new JTextField();
        loanButton = new JButton("Confirm");
        logOutButton = new JButton("Log Out");
        returnButton = new JButton("Return");
        allAccount = new JComboBox<>();
        currencySelect = new JComboBox<>();

        userLabel.setBounds(200,10,130,20);
        userLabel.setFont(new Font(null,Font.ITALIC,20));
        message1.setBounds(30,30,330,12);
        message1.setFont(new Font(null,Font.BOLD,12));
        message2.setBounds(30,42,330,12);
        message2.setFont(new Font(null,Font.BOLD,8));
        amount.setBounds(160, 70, 100, 25);
        amountInput.setBounds(160, 100, 100, 25);
        loanButton.setBounds(220, 140, 100, 25);
        toAccount.setBounds(30, 70, 120, 25);
        allAccount.setBounds(30, 100, 120, 25);
        currencyLabel.setBounds(270, 70, 100, 25);
        currencySelect.setBounds(270, 100, 100, 25);

        warningLabel.setBounds(170, 170, 300, 25);

        returnButton.setBounds(20,230,80,25);
        logOutButton.setBounds(120,230,80,25);

        frame = new JFrame("Account Menu");
        frame.add(userLabel);
        frame.add(message1);
        frame.add(message2);
        frame.add(amount);
        frame.add(toAccount);
        frame.add(allAccount);
        frame.add(amountInput);
        frame.add(loanButton);
        frame.add(currencyLabel);
        currencySelect.addItem("--Currency--");
        currencySelect.addItem("USD");
        currencySelect.addItem("RMB");
        currencySelect.addItem("GBP");

        frame.add(currencySelect);

        frame.add(warningLabel);

        frame.add(returnButton);
        frame.add(logOutButton);

        allAccount.addItem("--Please Select--");
        // get the account list of the customer
        AccountData accountData = AccountData.getInstance();
        HashMap<String, Account> accountList =  accountData.getAccountList(customer);
        for (Account account : accountList.values()) {
            allAccount.addItem(account.getAccountType());
        }
        frame.add(allAccount);

        returnButton.addActionListener(this);
        logOutButton.addActionListener(this);
        loanButton.addActionListener(this);

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
        if (loanButton.equals(e.getSource())){
            double amount = Integer.parseInt(amountInput.getText());
            double loan = amount * 1.2;
            String account = (String) allAccount.getSelectedItem();

            if (account.equals("--Please Select--")){
                warningLabel.setText("Please select an account!");
                return;
            }

            String currency = (String) currencySelect.getSelectedItem();

            if (currency.equals("--Currency--")){
                warningLabel.setText("Please select a currency!");
                return;
            }

            LoanData.updateAccount(customer, account, currency, amount);

            LoanData.updateLoan(customer, account, loan, currency);

            warningLabel.setText("Confirmed");
        }
    }
}
