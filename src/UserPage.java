import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserPage extends JFrame implements ActionListener {


    private JPanel panel1;
    private JButton accountSummaryButton;
    private JButton loanButton;
    private JButton openAccountButton;
    private JButton logOutButton;
    private JButton stockButton;
    private String username;

    private  JFrame frame;

    UserPage(String userName){
        username = userName;
        JLabel userLabel = new JLabel(userName);

        accountSummaryButton = new JButton("Account Summary");
        openAccountButton = new JButton("Open Account");
        loanButton = new JButton("Loan");
        stockButton = new JButton("Stock");
        logOutButton = new JButton("Log Out");

        userLabel.setBounds(200,10,180,30);
        userLabel.setFont(new Font(null,Font.ITALIC,35));
        accountSummaryButton.setBounds(20,70,150,25);
        openAccountButton.setBounds(20,110,150,25);
        loanButton.setBounds(20,150,150,25);
        stockButton.setBounds(20,190,150,25);
        logOutButton.setBounds(20,230,150,25);

        frame = new JFrame("UserPage");
        frame.add(userLabel);
        frame.add(accountSummaryButton);
        frame.add(openAccountButton);
        frame.add(loanButton);
        frame.add(stockButton);
        frame.add(logOutButton);

        accountSummaryButton.addActionListener(this);
        openAccountButton.addActionListener(this);
        loanButton.addActionListener(this);
        stockButton.addActionListener(this);
        logOutButton.addActionListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(420, 420);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==accountSummaryButton) {
            //TODO change Interest

            frame.dispose();
        }
        if(e.getSource()==openAccountButton) {
            //TODO open Account

            frame.dispose();
            AccountMenu menu = new AccountMenu(username);


        }
        if(e.getSource()==loanButton) {
            //TODO  loan
            frame.dispose();
        }
        if(e.getSource()==stockButton) {
            //TODO stock

            frame.dispose();
        }
        if(e.getSource()==logOutButton) {
            //TODO
            frame.dispose();
            new Login(new LoginData());
        }
    }
}
