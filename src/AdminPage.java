import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPage implements ActionListener {
    private JButton changeInterestButton;
    private JButton dailyReportButton;
//    private JButton openAccountButton;
    private JButton logOutButton;
    private JButton stockButton;
    private JFrame frame;

    public AdminPage(String userID) {
        JLabel adminLabel = new JLabel(userID);

        changeInterestButton = new JButton("Change Interest");
//        openAccountButton = new JButton("Open Account");
        dailyReportButton = new JButton("Daily Report");
        stockButton = new JButton("Stock");
        logOutButton = new JButton("Log Out");

        adminLabel.setBounds(200,10,180,30);
        adminLabel.setFont(new Font(null,Font.ITALIC,35));
        changeInterestButton.setBounds(20,110,150,25);
//        openAccountButton.setBounds(20,110,150,25);
        dailyReportButton.setBounds(20,150,150,25);
        stockButton.setBounds(20,190,150,25);
        logOutButton.setBounds(20,230,150,25);

        changeInterestButton.addActionListener(this);
//        openAccountButton.addActionListener(this);
        dailyReportButton.addActionListener(this);
        stockButton.addActionListener(this);
        logOutButton.addActionListener(this);

        frame = new JFrame("Admin Page");
        frame.add(adminLabel);
        frame.add(changeInterestButton);
//        frame.add(openAccountButton);
        frame.add(dailyReportButton);
        frame.add(stockButton);
        frame.add(logOutButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 420);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==changeInterestButton) {
            //TODO change Interest
            frame.dispose();
        }
//        if(e.getSource()==openAccountButton) {
//            //TODO open Account
//            frame.dispose();
//
//        }
        if(e.getSource()==dailyReportButton) {
            frame.dispose();
        }
        if(e.getSource()==stockButton) {
            frame.dispose();
        }
        if(e.getSource()==logOutButton) {
            //TODO
            frame.dispose();
            new Login(new LoginData());
        }

    }
}
