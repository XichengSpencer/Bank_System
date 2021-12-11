import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class AccountMenu implements ActionListener {


    private JTextField depositInput;
    private JLabel warningLabel = new JLabel();
    private JButton checkingButton;
    private JButton savingButton;
    private JButton logOutButton;
    private JButton returnButton;
    private String username;

    private  JFrame frame;

    public AccountMenu(String userName){
        username = userName;
        JLabel userLabel = new JLabel(userName);
        JLabel message = new JLabel("Choose the account Type you want to open");
        JLabel depositLabel = new JLabel("Input your deposit:");
        depositInput = new JTextField();
        savingButton = new JButton("Open A Saving Account");
        checkingButton = new JButton("Open A Checking Account");
        returnButton = new JButton("Return");
        logOutButton = new JButton("Log Out");

        userLabel.setBounds(200,10,130,20);
        userLabel.setFont(new Font(null,Font.ITALIC,20));
        message.setBounds(150,30,270,12);
        message.setFont(new Font(null,Font.BOLD,10));
        warningLabel.setBounds(20, 220, 300, 25);
//        warningLabel.setFont(new Font(null, Font.BOLD, 20));
        depositLabel.setBounds(20, 70, 150, 25);
        depositInput.setBounds(20, 110, 150, 25);
        savingButton.setBounds(220,70,150,25);
        checkingButton.setBounds(220,110,150,25);
        returnButton.setBounds(220,150,150,25);
        logOutButton.setBounds(220,190,150,25);

        frame = new JFrame("Account Menu");
        frame.add(userLabel);
        frame.add(message);
        frame.add(depositLabel);
        frame.add(warningLabel);
        frame.add(depositInput);
        frame.add(savingButton);
        frame.add(checkingButton);
        frame.add(returnButton);
        frame.add(logOutButton);

        depositInput.addActionListener(this);
        savingButton.addActionListener(this);
        checkingButton.addActionListener(this);
        returnButton.addActionListener(this);
        logOutButton.addActionListener(this);

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

        if (savingButton.equals(e.getSource())) {
            CustomerData customerData = CustomerData.getInstance();
            Customer customer = customerData.selectCustomer(username);
            int amount = Integer.parseInt(depositInput.getText()) - 5;
            Account newSavAct = new Account(customer, amount);
            customer.addAccount("saving", newSavAct);
            // update the file or add the file under the folder
            String accountPath = System.getProperty("user.dir") + "/src/System Data/" + customer.getId() + "/saving.txt";
            File accountFile = new File(accountPath);
            if (!accountFile.exists()){
                try {
                    accountFile.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            fileEditor.writeFile("/src/System Data/" + customer.getId() + "/saving.txt", new String[]{newSavAct.getId(), ""+amount});
        }
        if (depositInput.equals(e.getSource())) {
            int deposit =Integer.parseInt(depositInput.getText());
            if (deposit < 100) {
                warningLabel.setText("Deposit should be greater than 100");
            } else {
                warningLabel.setText("");
            }
        }
    }
}
