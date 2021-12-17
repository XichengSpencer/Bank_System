import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class WithdrawMenu implements ActionListener {
    private JButton returnButton;
    private JButton logOutButton;

    private JFrame frame;
    private JFrame previous;
    private Customer customer;

    private JTextField withdrawInput;
    private JComboBox<String> currencySelect;
    private JComboBox<String> accountSelect;
    private JLabel warningLabel = new JLabel();
    private JButton withdrawButton;

    public WithdrawMenu(String userName, JFrame previous) {
        CustomerData customerData = CustomerData.getInstance();
        this.customer = customerData.selectCustomer(userName);
        this.previous = previous;

        JLabel userLabel = new JLabel(userName);
        JLabel message = new JLabel("Withdraw money from your account");
        JLabel withdrawLabel = new JLabel("Input your withdraw:");
        JLabel currencyLable = new JLabel("Choose the currency:");
        JLabel accountLable = new JLabel("Choose the account:");
        withdrawInput = new JTextField();
        currencySelect = new JComboBox<>();
        accountSelect = new JComboBox<>();
        withdrawButton = new JButton("Withdraw");
        returnButton = new JButton("Return");
        logOutButton = new JButton("Log Out");

        userLabel.setBounds(200,10,130,20);
        userLabel.setFont(new Font(null,Font.ITALIC,20));
        message.setBounds(150,30,270,12);
        message.setFont(new Font(null,Font.BOLD,10));
        warningLabel.setBounds(20, 210, 300, 25);
//        warningLabel.setFont(new Font(null, Font.BOLD, 20));
        withdrawLabel.setBounds(20, 70, 150, 25);
        currencyLable.setBounds(20, 150, 150, 25);
        withdrawInput.setBounds(20, 110, 150, 25);
        currencySelect.setBounds(20, 190, 150, 25);
        accountLable.setBounds(180, 70, 150, 25);
        accountSelect.setBounds(180, 110, 150, 25);
        withdrawButton.setBounds(180, 190, 150, 25);


        returnButton.setBounds(20,230,80,25);
        logOutButton.setBounds(120,230,80,25);

        frame = new JFrame("Withdraw Menu");
        frame.add(userLabel);
        frame.add(message);
        frame.add(withdrawLabel);
        frame.add(warningLabel);
        frame.add(withdrawInput);
        frame.add(currencyLable);
        frame.add(withdrawButton);
        frame.add(returnButton);
        frame.add(logOutButton);

        withdrawInput.addActionListener(this);
        withdrawButton.addActionListener(this);
        returnButton.addActionListener(this);
        logOutButton.addActionListener(this);

        //panel1.add(userLable);
        currencySelect.addItem("--Select Currency--");
        currencySelect.addItem("USD");
        currencySelect.addItem("RMB");
        currencySelect.addItem("GBP");
        frame.add(currencySelect);

        //panel1.add(userLabel);
        /**
         * Add the accounts into the Combobox
         */
        accountSelect.addItem("--Please Select--");
        //get the customer
        // get the account list of the customer
//        AccountData accountData = AccountData.getInstance();
//        HashMap<String, Account> accountList =  accountData.getAccountList(customer);
        HashMap<String, Account> accountList = customer.getAccountList();
        for (Account account : accountList.values()) {
            accountSelect.addItem(account.getAccountType());
        }
        frame.add(accountSelect);


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
        }
        if (withdrawButton.equals(e.getSource())){
            if (withdrawInput.getText().length() == 0){
                warningLabel.setText("Please input amount!");
                return;
            }
            double amount = Double.valueOf(withdrawInput.getText());
            String toAccountType = accountSelect.getSelectedItem().toString();
            String currency = currencySelect.getSelectedItem().toString();

            HashMap<String, String[]> accountlist = fileEditor.toHash("/src/System Data/" + customer.getId() + "/" + toAccountType + ".txt");
            double accountBalance = Double.valueOf(accountlist.get(toAccountType)[0]);
            if(accountBalance < amount){
                warningLabel.setText("Insufficient balance！");
                return;
            }

            if (amount <= 0){
                warningLabel.setText("Please input amount greater than 0!");
                return;
            }
            if (toAccountType.equals("--Please Select--")){
                warningLabel.setText("Please select account");
                return;
            }
            if (currency.equals("--Select Currency--")){
                warningLabel.setText("Please select currency!");
                return;
            }

            String accountPath = System.getProperty("user.dir") + "/src/System Data/" + customer.getId() + "/" + toAccountType + ".txt";
            File accountFile = new File(accountPath);
            if (!accountFile.exists()) {
                try {
                    accountFile.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

            List<String[]> amountList = fileEditor.fileRead("/src/System Data/"+ customer.getId() + "/" + toAccountType + ".txt");
            // empty file for update
            FileWriter fileWriter = null;
            try {
                fileWriter = new FileWriter(accountFile);
                fileWriter.write("");
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            for(String[] token :amountList){
                if (token[2].equals(currencySelect.getSelectedItem().toString())) { // find the correct currency
                    // choose the same currency
                    double amount1 = Double.valueOf(token[1]);
                    amount1 -= Double.valueOf(withdrawInput.getText());
                    token[1] = ""+amount1;
                }
                fileEditor.writeFile("/src/System Data/"+customer.getId()+"/" + toAccountType + ".txt", token);
            }

            warningLabel.setText("Withdraw complete!");

            TransactionData.getInstance().logTransaction("withdraw", customer.getName(), toAccountType,
                    "","",amount, currency);
        }
    }
}
