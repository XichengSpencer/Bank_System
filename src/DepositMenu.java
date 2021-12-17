import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DepositMenu implements ActionListener {
    private JTextField depositInput;
    private JComboBox<String> currencySelect;
    private JComboBox<String> accountSelect;
    private JLabel warningLabel = new JLabel();
    private JButton depositButton;
    private JButton logOutButton;
    private JButton returnButton;
    private String username;
    private JFrame previous;

    private  JFrame frame;

    public DepositMenu(String userName, JFrame previous){
        username = userName;
        this.previous = previous;
        JLabel userLabel = new JLabel(userName);
        JLabel message = new JLabel("Deposit money to you account");
        JLabel depositLabel = new JLabel("Input your deposit:");
        JLabel currencyLable = new JLabel("Choose the currency:");
        JLabel accountLable = new JLabel("Choose the account:");
        depositInput = new JTextField();
        currencySelect = new JComboBox<>();
        accountSelect = new JComboBox<>();
        depositButton = new JButton("Deposit");
        returnButton = new JButton("Return");
        logOutButton = new JButton("Log Out");

        userLabel.setBounds(200,10,130,20);
        userLabel.setFont(new Font(null,Font.ITALIC,20));
        message.setBounds(150,30,270,12);
        message.setFont(new Font(null,Font.BOLD,10));
        warningLabel.setBounds(20, 210, 300, 25);
//        warningLabel.setFont(new Font(null, Font.BOLD, 20));
        depositLabel.setBounds(20, 70, 150, 25);
        currencyLable.setBounds(20, 150, 150, 25);
        depositInput.setBounds(20, 110, 150, 25);
        currencySelect.setBounds(20, 190, 150, 25);
        accountLable.setBounds(180, 70, 150, 25);
        accountSelect.setBounds(180, 110, 150, 25);
        depositButton.setBounds(180, 190, 150, 25);


        returnButton.setBounds(20,230,80,25);
        logOutButton.setBounds(120,230,80,25);

        frame = new JFrame("Deposit Menu");
        frame.add(userLabel);
        frame.add(message);
        frame.add(depositLabel);
        frame.add(warningLabel);
        frame.add(depositInput);
        frame.add(currencyLable);
        frame.add(depositButton);
        frame.add(returnButton);
        frame.add(logOutButton);

        depositInput.addActionListener(this);
        depositButton.addActionListener(this);
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
        CustomerData customerData = CustomerData.getInstance();
        Customer customer = customerData.selectCustomer(userName);
        // get the account list of the customer
//        AccountData accountData = AccountData.getInstance();
//        HashMap<String, Account> accountList =  accountData.getAccountList(customer);
        HashMap<String, Account> accountList = customer.getAccountList();
        for (Account account : accountList.values()) {
            accountSelect.addItem(account.getId());
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

        if (depositInput.equals(e.getSource())) {
            int deposit =Integer.parseInt(depositInput.getText());
            if (deposit < 100) {
                warningLabel.setText("Deposit should be greater than 100");
            } else {
                warningLabel.setText("");
            }
        }
        // deposit button
        if (depositButton.equals(e.getSource())) {
            CustomerData customerData = CustomerData.getInstance();
            Customer customer = customerData.selectCustomer(username);
            double amount = Double.valueOf(depositInput.getText());
            String toAccountType = "";
            HashMap<String, Account> accountList = customer.getAccountList();
            for (String type : accountList.keySet()) {
                if (accountList.get(type).getId().equals(accountSelect.getSelectedItem().toString())) { // find the account
                    toAccountType = type;
                    break;
                }
            }

            Account account = new Account(toAccountType, customer, currencySelect.getSelectedItem().toString(), amount);
            customer.addAccount(toAccountType, account);
            // update the file or add the file under the folder
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
                    //fileEditor.writeFile("/src/metadata.txt",new String[]{new SimpleDateFormat("MM/dd/yyyy").format(new Date()),"deposit"+currencySelect.getSelectedItem().toString(),amount1+""});
                    amount1 += Double.valueOf(depositInput.getText());
                    token[1] = ""+amount1;
                }
                fileEditor.writeFile("/src/System Data/"+customer.getId()+"/" + toAccountType + ".txt", token);

            }
            // log the transaction
            TransactionData.getInstance().logTransaction("Deposit", customer.getName(), "cash", customer.getName(), toAccountType, Double.valueOf(depositInput.getText()), currencySelect.getSelectedItem().toString());
        }

        // go back to previous page
        if (returnButton.equals(e.getSource())) {
            frame.dispose();
            previous.setVisible(true);
        }
        if (logOutButton.equals(e.getSource())) {
            frame.dispose();
        }
    }
}
