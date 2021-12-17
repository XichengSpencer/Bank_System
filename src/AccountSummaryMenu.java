import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class AccountSummaryMenu implements ActionListener {


    private JLabel warningLabel = new JLabel();
    private JLabel cUSD = new JLabel();
    private JLabel cRMB = new JLabel();
    private JLabel cGBP = new JLabel();
    private JLabel sUSD = new JLabel();
    private JLabel sRMB = new JLabel();
    private JLabel sGBP = new JLabel();
    private JLabel securityUSD = new JLabel();
    private JLabel securityRMB = new JLabel();
    private JLabel securityGBP = new JLabel();

    private JButton logOutButton;
    private JButton returnButton;
    private String username;

    private JFrame frame;
    private JFrame previous;

    public AccountSummaryMenu(String userName, JFrame previous){
        username = userName;
        this.previous = previous;
        JLabel userLabel = new JLabel(userName);
        JLabel message = new JLabel("The summary of you accounts");
        JLabel savingLabel = new JLabel("Saving:");
        JLabel usdLable = new JLabel("USD:");
        JLabel rmbLable = new JLabel("RMB:");
        JLabel gbpLable = new JLabel("GNP:");
        JLabel checkingLable = new JLabel("Checking:");
        JLabel securityLable = new JLabel("Security:");

        returnButton = new JButton("Return");
        logOutButton = new JButton("Log Out");

        userLabel.setBounds(200,10,130,20);
        userLabel.setFont(new Font(null,Font.ITALIC,20));
        message.setBounds(100,30,270,12);
        message.setFont(new Font(null,Font.BOLD,10));
        warningLabel.setBounds(20, 220, 300, 25);

        savingLabel.setBounds(70, 60, 80, 25);
        checkingLable.setBounds(160, 60, 80, 25);
        securityLable.setBounds(250, 60, 80, 25);
        usdLable.setBounds(20, 90, 40, 20);
        rmbLable.setBounds(20, 130, 40, 20);
        gbpLable.setBounds(20, 170, 40, 20);

        sUSD.setBounds(70, 90, 80, 20);
        sRMB.setBounds(70, 130, 80, 20);
        sGBP.setBounds(70, 170, 80, 20);

        cUSD.setBounds(160, 90, 80, 20);
        cRMB.setBounds(160, 130, 80, 20);
        cGBP.setBounds(160, 170, 80, 20);

        securityUSD.setBounds(250, 90, 80, 20);
        securityRMB.setBounds(250, 130, 80, 20);
        securityGBP.setBounds(250, 170, 80, 20);

        returnButton.setBounds(20,230,80,25);
        logOutButton.setBounds(120,230,80,25);

        frame = new JFrame("Account Summary");
        frame.add(userLabel);
        frame.add(message);
        frame.add(checkingLable);
        frame.add(warningLabel);
        frame.add(savingLabel);
        frame.add(securityLable);
        frame.add(securityLable);
        frame.add(usdLable);
        frame.add(rmbLable);
        frame.add(gbpLable);

        frame.add(returnButton);
        frame.add(logOutButton);

        returnButton.addActionListener(this);
        logOutButton.addActionListener(this);

        //prepare data for amount
        //get the customer
        CustomerData customerData = CustomerData.getInstance();
        Customer customer = customerData.selectCustomer(userName);
        // get the account list of the customer
        sUSD.setText("0.00");
        sRMB.setText("0.00");
        sGBP.setText("0.00");
        cUSD.setText("0.00");
        cRMB.setText("0.00");
        cGBP.setText("0.00");
        securityUSD.setText("0.00");
        securityRMB.setText("0.00");
        securityGBP.setText("0.00");
        HashMap<String, Account> accountList = customer.getAccountList();
        for (String type : accountList.keySet()) {
            HashMap<String, Double> amountList = accountList.get(type).getTotalAmount();
            if (type.equals("saving")) {
                for (String currency : amountList.keySet()) {
                    if (currency.equals("USD")) {
                        sUSD.setText(""+amountList.get(currency));
                    } else if (currency.equals("RMB")) {
                        sRMB.setText("" + amountList.get(currency));
                    } else {
                        sGBP.setText(""+amountList.get(currency));
                    }
                }
            } else if (type.equals("checking")) {
                for (String currency : amountList.keySet()) {
                    if (currency.equals("USD")) {
                        cUSD.setText(""+amountList.get(currency));
                    } else if (currency.equals("RMB")) {
                        cRMB.setText("" + amountList.get(currency));
                    } else {
                        cGBP.setText(""+amountList.get(currency));
                    }
                }
            } else if (type.equals("security")) {
                double amountInAccount = accountList.get(type).getTotalAmount().get("USD");
                double stockValue = StockData.getTotalAmountOfStocks(customer);

                securityUSD.setText("" + (amountInAccount+stockValue));
            }
        }
        frame.add(sUSD);
        frame.add(sRMB);
        frame.add(sGBP);
        frame.add(cUSD);
        frame.add(cRMB);
        frame.add(cGBP);
        frame.add(securityUSD);
        frame.add(securityRMB);
        frame.add(securityGBP);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // go back to previous page
        if (returnButton.equals(e.getSource())) {
            frame.dispose();
            previous.setVisible(true);
        }
        if (logOutButton.equals(e.getSource())) {
            frame.dispose();
            new Login(new LoginData());
        }
    }
}
