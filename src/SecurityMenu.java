import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class SecurityMenu implements ActionListener {
    private JTextField amountInput;
    private JComboBox<String> currencySelect;
    private JLabel warningLabel = new JLabel();
    private JButton OpenButton;
    private JButton logOutButton;
    private JButton returnButton;
    private String username;
    private JFrame previous;

    private  JFrame frame;

    public SecurityMenu(String userName, JFrame previous){
        username = userName;
        this.previous = previous;
        JLabel userLabel = new JLabel(userName);
        JLabel message = new JLabel("Input the amount you want to transfer from saving to security account");
        JLabel amountLabel = new JLabel("Input the amount:");
        JLabel currencyLable = new JLabel("Choose the currency:");
        amountInput = new JTextField();
        currencySelect = new JComboBox<>();
        OpenButton = new JButton("Open Account");
        returnButton = new JButton("Return");
        logOutButton = new JButton("Log Out");

        userLabel.setBounds(200,10,130,20);
        userLabel.setFont(new Font(null,Font.ITALIC,20));
        message.setBounds(150,30,270,12);
        message.setFont(new Font(null,Font.BOLD,10));
        warningLabel.setBounds(20, 210, 300, 25);
//        warningLabel.setFont(new Font(null, Font.BOLD, 20));
        amountLabel.setBounds(20, 70, 150, 25);
        currencyLable.setBounds(20, 150, 150, 25);
        amountInput.setBounds(20, 110, 150, 25);
        currencySelect.setBounds(20, 190, 150, 25);
        OpenButton.setBounds(180, 190, 150, 25);


        returnButton.setBounds(20,230,80,25);
        logOutButton.setBounds(120,230,80,25);

        frame = new JFrame("Security Account Menu");
        frame.add(userLabel);
        frame.add(message);
        frame.add(amountLabel);
        frame.add(warningLabel);
        frame.add(amountInput);
        frame.add(currencyLable);
        frame.add(OpenButton);
        frame.add(returnButton);
        frame.add(logOutButton);

        amountInput.addActionListener(this);
        OpenButton.addActionListener(this);
        returnButton.addActionListener(this);
        logOutButton.addActionListener(this);

        currencySelect.addItem("--Select Currency--");
        currencySelect.addItem("USD");
        currencySelect.addItem("RMB");
        currencySelect.addItem("GBP");
        frame.add(currencySelect);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (amountInput.equals(e.getSource())) {
            int deposit =Integer.parseInt(amountInput.getText());
            if (deposit < 100) {
                warningLabel.setText("Deposit should be greater than 1000");
            } else {
                warningLabel.setText("");
            }
        }
        // open account button
        if (OpenButton.equals(e.getSource())) {
            // check greater than 1000
            int deposit =Integer.parseInt(amountInput.getText());
            if (deposit < 1000) {
                warningLabel.setText("Deposit should be greater than 1000");
                return;
            } else {
                warningLabel.setText("");
            }
            CustomerData customerData = CustomerData.getInstance();
            Customer customer = customerData.selectCustomer(username);
            double amount = Double.valueOf(amountInput.getText());

            // check if security exist,if not create one
            String securityPath = System.getProperty("user.dir") + "/src/System Data/" + customer.getId() + "/security.txt";
            File securityFile = new File(securityPath);
            SecurityAccount securityAccount = new SecurityAccount(customer, 0.0);
            if (!securityFile.exists()) {
                try {
                    securityFile.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } else {
                warningLabel.setText("Already have one");
                return;
            }
            fileEditor.writeFile("/src/System Data/" + customer.getId() + "/security.txt", new String[]{securityAccount.getId(), ""+securityAccount.getTotalAmount().get("USD"), currencySelect.getSelectedItem().toString()});

            // Do transfer
            String fromAccountType = "saving";
            // update the file or add the file under the folder
//            String accountPath = System.getProperty("user.dir") + "/src/System Data/" + customer.getId() + "/saving.txt";
//            File accountFile = new File(accountPath);
//            if (!accountFile.exists()) {
//                try {
//                    accountFile.createNewFile();
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
            Account savingAccount = customer.getAccountList().get(fromAccountType);

            Transfer transfer = new Transfer(savingAccount, securityAccount, amount, currencySelect.getSelectedItem().toString());
            try {
                transfer.execute();
                TransactionData.getInstance().logTransaction("Transfer", transfer);
            } catch (IOException ex) {
                ex.printStackTrace();
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
