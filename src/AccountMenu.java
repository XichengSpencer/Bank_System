import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AccountMenu implements ActionListener {


    private JTextField depositInput;
    private JComboBox<String> currencySelect;
    private JLabel warningLabel = new JLabel();
    private JButton securityButton;
    private JButton checkingButton;
    private JButton savingButton;
    private JButton logOutButton;
    private JButton returnButton;
    private String username;

    private JFrame frame;
    private JFrame previous;

    public AccountMenu(String userName, JFrame previous){
        username = userName;
        this.previous = previous;
        JLabel userLabel = new JLabel(userName);
        JLabel message = new JLabel("Choose the account Type you want to open");
        JLabel depositLabel = new JLabel("Input your deposit:");
        JLabel currencyLable = new JLabel("Choose the currency:");
        depositInput = new JTextField();
        currencySelect = new JComboBox<>();
        savingButton = new JButton("Open A Saving Account");
        checkingButton = new JButton("Open A Checking Account");
        securityButton = new JButton("Open A Security Account");
        returnButton = new JButton("Return");
        logOutButton = new JButton("Log Out");

        userLabel.setBounds(200,10,130,20);
        userLabel.setFont(new Font(null,Font.ITALIC,20));
        message.setBounds(100,30,270,12);
        message.setFont(new Font(null,Font.BOLD,10));
        warningLabel.setBounds(20, 210, 300, 25);
//        warningLabel.setFont(new Font(null, Font.BOLD, 20));
        depositLabel.setBounds(20, 70, 150, 25);
        currencyLable.setBounds(20, 150, 150, 25);
        depositInput.setBounds(20, 110, 150, 25);
        currencySelect.setBounds(20, 190, 150, 25);
        savingButton.setBounds(180,70,200,25);
        checkingButton.setBounds(180,110,200,25);
        securityButton.setBounds(180,150,200,25);
        returnButton.setBounds(20,230,80,25);
        logOutButton.setBounds(120,230,80,25);

        frame = new JFrame("Account Menu");
        frame.add(userLabel);
        frame.add(message);
        frame.add(depositLabel);
        frame.add(warningLabel);
        frame.add(depositInput);
        frame.add(currencyLable);
//        frame.add(currencySelect);
        frame.add(savingButton);
        frame.add(checkingButton);
        frame.add(securityButton);
        frame.add(returnButton);
        frame.add(logOutButton);

        depositInput.addActionListener(this);
        savingButton.addActionListener(this);
        checkingButton.addActionListener(this);
        securityButton.addActionListener(this);
        returnButton.addActionListener(this);
        logOutButton.addActionListener(this);

        //panel1.add(userLable);
        currencySelect.addItem("--Select Currency--");
        currencySelect.addItem("USD");
        currencySelect.addItem("RMB");
        currencySelect.addItem("GBP");
        frame.add(currencySelect);

        //panel1.add(userLabel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // open a saving account
        if (savingButton.equals(e.getSource())) {
            if(depositInput.getText().length() == 0){
                warningLabel.setText("Please input deposit!");
                return;
            }
            if (currencySelect.getSelectedItem().toString().equals("--Select Currency--")){
                warningLabel.setText("Please select currency!");
                return;
            }
            CustomerData customerData = CustomerData.getInstance();
            Customer customer = customerData.selectCustomer(username);
            double amount = Double.valueOf(depositInput.getText()) - 5;
            Account newSavAct = new Account(customer, amount);
            customer.addAccount("saving", newSavAct);
            // update the file or add the file under the folder
            String accountPath = System.getProperty("user.dir") + "/src/System Data/" + customer.getId() + "/saving.txt";
            File accountFile = new File(accountPath);
            if (!accountFile.exists()) {
                try {
                    accountFile.createNewFile();
                    //add action to meta

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                warningLabel.setText("Already have one");
                return;
            }
            //write metadata
            fileEditor.writeFile("/src/metadata.txt",new String[]{new SimpleDateFormat("MM/dd/yyyy").format(new Date()),"createSav"});

            fileEditor.writeFile("/src/System Data/" + customer.getId() + "/saving.txt", new String[]{newSavAct.getId(), ""+amount, currencySelect.getSelectedItem().toString()});
            warningLabel.setText("A saving account has been opened");
        }
        // open a checking account
        if (checkingButton.equals(e.getSource())) {
            if(depositInput.getText().length() == 0){
                warningLabel.setText("Please input deposit!");
                return;
            }
            if (currencySelect.getSelectedItem().toString().equals("--Select Currency--")){
                warningLabel.setText("Please select currency!");
                return;
            }
            CustomerData customerData = CustomerData.getInstance();
            Customer customer = customerData.selectCustomer(username);
            double amount = Double.valueOf(depositInput.getText()) - 5;
            Account newSavAct = new Account(customer, amount);
            customer.addAccount("checking", newSavAct);
            // update the file or add the file under the folder
            String accountPath = System.getProperty("user.dir") + "/src/System Data/" + customer.getId() + "/checking.txt";
            File accountFile = new File(accountPath);
            if (!accountFile.exists()){
                try {
                    accountFile.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                warningLabel.setText("Already have one");
                return;
            }
            fileEditor.writeFile("/src/metadata.txt",new String[]{new SimpleDateFormat("MM/dd/yyyy").format(new Date()),"createChk"});
            fileEditor.writeFile("/src/System Data/" + customer.getId() + "/checking.txt", new String[]{newSavAct.getId(), ""+amount, currencySelect.getSelectedItem().toString()});
            warningLabel.setText("A checking account has been opened");
        }
        // create a security account
        if (securityButton.equals(e.getSource())) {
            if(depositInput.getText().length() == 0){
                warningLabel.setText("Please input deposit!");
                return;
            }
            if (currencySelect.getSelectedItem().toString().equals("--Select Currency--")){
                warningLabel.setText("Please select currency!");
                return;
            }
            // make sure the amount in saving account is larger than 5000
            CustomerData customerData = CustomerData.getInstance();
            Customer customer = customerData.selectCustomer(username);
            double amountInSaving = customer.getAccountList().get("saving").getTotalAmount().get("USD");
            if (amountInSaving < 5000.0) {
                warningLabel.setText("You don't have enough money to open a security account");
                return;
            } else {
                warningLabel.setText("");
            }
            SecurityMenu securityMenu = new SecurityMenu(username, previous);
            warningLabel.setText("A security account has been opened");
        }
        if (depositInput.equals(e.getSource())) {
            int deposit =Integer.parseInt(depositInput.getText());
            if (deposit < 100) {
                warningLabel.setText("Deposit should be greater than 100");
            } else {
                warningLabel.setText("");
            }
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
