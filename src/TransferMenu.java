import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class TransferMenu implements ActionListener {


    private JTextField accountNumberInput;
    private JTextField amountInput;
    private JTextField currencyInput;
    private JComboBox<String> fromAccountInput;
    private JLabel warningLabel = new JLabel();
    private JButton transferButton;
    private JButton logOutButton;
    private JButton returnButton;
    private String username;

    private  JFrame frame;

    public TransferMenu(String userName){
        username = userName;
        JLabel userLabel = new JLabel(userName);
        JLabel message = new JLabel("Input the account number and the amount");
        JLabel accountNumber = new JLabel("Account Number:");
        JLabel amount = new JLabel("Amount:");
        JLabel currency = new JLabel("Currency:");
        JLabel fromAccount = new JLabel("From Account:");
        accountNumberInput = new JTextField();
        amountInput = new JTextField();
        currencyInput = new JTextField();
        fromAccountInput = new JComboBox<>();
        transferButton = new JButton("Transfer");

        returnButton = new JButton("Return");
        logOutButton = new JButton("Log Out");

        userLabel.setBounds(200,10,130,20);
        userLabel.setFont(new Font(null,Font.ITALIC,20));
        message.setBounds(30,30,270,12);
        message.setFont(new Font(null,Font.BOLD,10));
        warningLabel.setBounds(20, 220, 300, 25);
//        warningLabel.setFont(new Font(null, Font.BOLD, 20));
        accountNumber.setBounds(20, 70, 170, 25);
        accountNumberInput.setBounds(20, 110, 170, 25);
        amount.setBounds(210, 70, 70, 25);
        amountInput.setBounds(210, 110, 70, 25);
        currency.setBounds(300,70, 80, 25);
        currencyInput.setBounds(300 ,110, 40, 25);
        fromAccount.setBounds(20, 140, 170, 25);
        fromAccountInput.setBounds(20, 160, 170, 25);
        transferButton.setBounds(280, 160, 100, 25);

        returnButton.setBounds(20,210,80,25);
        logOutButton.setBounds(20,230,80,25);

        frame = new JFrame("Account Menu");
        frame.add(userLabel);
        frame.add(message);
        frame.add(accountNumber);
        frame.add(accountNumberInput);
        frame.add(amount);
        frame.add(amountInput);
        frame.add(currency);
        frame.add(currencyInput);
        frame.add(fromAccount);
//        frame.add(fromAccountInput);
        frame.add(transferButton);

        frame.add(warningLabel);
        frame.add(returnButton);
        frame.add(logOutButton);

        returnButton.addActionListener(this);
        logOutButton.addActionListener(this);
        transferButton.addActionListener(this);
//        fromAccountInput.addActionListener(this);

        /**
         * Add the accounts into the Combobox
         */
        fromAccountInput.addItem("--Please Select--");
        //get the customer
        CustomerData customerData = CustomerData.getInstance();
        Customer customer = customerData.selectCustomer(userName);
        // get the account list of the customer
//        AccountData accountData = AccountData.getInstance();
//        HashMap<String, Account> accountList =  accountData.getAccountList(customer);
        HashMap<String, Account> accountList = customer.getAccountList();
        for (Account account : accountList.values()) {
            fromAccountInput.addItem(account.getId());
        }
        frame.add(fromAccountInput);

        //panel1.add(userLable);


        //panel1.add(userLabel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // transfer
        if (transferButton.equals(e.getSource())) {
            CustomerData customerData = CustomerData.getInstance();
            Customer fromCustomer = customerData.selectCustomer(username);
            String fromAccountNumber = fromAccountInput.getSelectedItem().toString();
            String toAccountNumber = accountNumberInput.getText();

            String fromAccountType = "";
            String toAccountType = "";
            HashMap<String, Account> accountList = fromCustomer.getAccountList();
            for (String type : accountList.keySet()) {
                if (accountList.get(type).getId().equals(fromAccountNumber)) { // find the account
                    fromAccountType = type;
                    break;
                }
            }

            ArrayList<Customer> customers = customerData.getAllCustomers();
            Customer toCustomer = null;
            boolean isFund = false;
            for (Customer customer1 : customers) {
                HashMap<String, Account> accountList1 = customer1.getAccountList();
                for (String type : accountList1.keySet()) {
                    if (accountList1.get(type).getId().equals(toAccountNumber)) { // find the account
                        toCustomer = customerData.selectCustomer(customer1.getName());
                        toAccountType = type;
                        isFund = true;
                        break;
                    }
                }
                if (isFund) {
                    break;
                }
            }

            AccountData accountData = AccountData.getInstance();
            Account from = accountData.getAccount(fromAccountType, fromCustomer, fromAccountNumber);
            Account to = accountData.getAccount(toAccountType, toCustomer, toAccountNumber);
            double amount = Double.valueOf(amountInput.getText());
            String fromCurrency = currencyInput.getText();

            Transfer transfer = new Transfer(from, to, amount, fromCurrency);
            try {
                transfer.execute();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


        // go back to previous page
        if (returnButton.equals(e.getSource())) {
            frame.toBack();
        }
        if (logOutButton.equals(e.getSource())) {
            frame.dispose();
        }
    }
}
