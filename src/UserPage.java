import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPage extends JFrame implements ActionListener {


    private JPanel panel1;
    private JButton accountSummaryButton;
    private JButton transferButton;
    private JButton depositButton;
    private JButton loanButton;
    private JButton openAccountButton;
    private JButton logOutButton;
    private JButton logButton;
    private JButton stockButton;
    private String username;
    private JButton withDrawButton;

    private  JFrame frame;

    UserPage(String userName){
        username = userName;
        JLabel userLabel = new JLabel(userName);

        accountSummaryButton = new JButton("Account Summary");
        openAccountButton = new JButton("Open Account");
        transferButton = new JButton("Transfer");
        depositButton = new JButton("Deposit");
        loanButton = new JButton("Loan");
        stockButton = new JButton("Stock");
        logButton = new JButton("View Transactions");
        logOutButton = new JButton("Log Out");
        withDrawButton = new JButton("Withdraw");

        userLabel.setBounds(200,10,180,45);
        userLabel.setFont(new Font(null,Font.ITALIC,35));
        accountSummaryButton.setBounds(20,70,150,25);
        openAccountButton.setBounds(20,110,150,25);
        transferButton.setBounds(20, 150, 150, 25);
        depositButton.setBounds(20, 190, 150, 25);
        loanButton.setBounds(20,230,150,25);
        stockButton.setBounds(20,270,150,25);
        logButton.setBounds(20,310,150,25);
        logOutButton.setBounds(20,350,150,25);
        withDrawButton.setBounds(200, 70, 150, 25);

        frame = new JFrame("UserPage");
        frame.add(userLabel);
        frame.add(accountSummaryButton);
        frame.add(openAccountButton);
        frame.add(transferButton);
        frame.add(depositButton);
        frame.add(loanButton);
        frame.add(stockButton);
        frame.add(logButton);
        frame.add(logOutButton);
        frame.add(withDrawButton);

        accountSummaryButton.addActionListener(this);
        openAccountButton.addActionListener(this);
        transferButton.addActionListener(this);
        depositButton.addActionListener(this);
        loanButton.addActionListener(this);
        stockButton.addActionListener(this);
        logButton.addActionListener(this);
        logOutButton.addActionListener(this);
        withDrawButton.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==accountSummaryButton) {
//            frame.dispose();
            frame.setVisible(false);
            AccountSummaryMenu accountSummaryMenu = new AccountSummaryMenu(username, frame);
        }
        if(e.getSource()==openAccountButton) {
            frame.setVisible(false);
            AccountMenu accountMenu = new AccountMenu(username, frame);
        }
        if (e.getSource() == transferButton) {
            frame.dispose();
            TransferMenu transferMenu = new TransferMenu(username, frame);
        }
        if (e.getSource() == depositButton) {
            frame.dispose();
            DepositMenu depositMenu = new DepositMenu(username, frame);
        }
        if(e.getSource()==loanButton) {
            //TODO  loan
            frame.setVisible(false);
            LoanMenu loanMenu = new LoanMenu(username,frame);
        }
        if(e.getSource()==stockButton) {
            //TODO stock

            frame.dispose();
        }
        if (e.getSource() == logButton) {
            frame.dispose();
            TransactionsMenu transactionsMenu = new TransactionsMenu(username, frame);
        }
        if (withDrawButton.equals(e.getSource())){
            frame.setVisible(false);
            WithdrawMenu withdrawMenu = new WithdrawMenu(username, frame);
        }
        if(e.getSource()==logOutButton) {
            //TODO
            frame.dispose();
            new Login(new LoginData());
        }
    }
}
