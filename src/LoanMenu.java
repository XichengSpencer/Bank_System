import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoanMenu implements ActionListener {

    private String userName;
    private JFrame previous;

    private JFrame frame;
    private JButton requestButton;
    private JButton payButton;
    private JButton returnButton;
    private JButton logOutButton;

    public LoanMenu(String userName, JFrame previous) {
        this.previous = previous;
        this.userName = userName;

        JLabel userLabel = new JLabel(userName);
        requestButton = new JButton("Request Loan");
        payButton = new JButton("Pay Loan");

        logOutButton = new JButton("Log Out");
        returnButton = new JButton("Return");

        userLabel.setBounds(200,10,130,20);
        userLabel.setFont(new Font(null,Font.ITALIC,20));
        requestButton.setBounds(30, 80, 120, 25);
        payButton.setBounds(30, 120, 120, 25);


        returnButton.setBounds(20,230,80,25);
        logOutButton.setBounds(120,230,80,25);

        frame = new JFrame("Account Menu");
        frame.add(userLabel);
        frame.add(requestButton);
        frame.add(payButton);
        frame.add(returnButton);
        frame.add(logOutButton);

        returnButton.addActionListener(this);
        logOutButton.addActionListener(this);
        requestButton.addActionListener(this);
        payButton.addActionListener(this);

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
        if (requestButton.equals(e.getSource())){
            frame.setVisible(false);
            new RequestLoanMenu(userName, frame);
        }
        if (payButton.equals(e.getSource())){
            frame.setVisible(false);
            new PayLoanMenu(userName, frame);
        }
    }
}
