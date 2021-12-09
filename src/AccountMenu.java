import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountMenu implements ActionListener {

    private JButton checkingButton;
    private JButton savingButton;
    private JButton logOutButton;
    private JButton returnButton;
    private String uid;

    private  JFrame frame;

    public AccountMenu(String userID){
        uid = userID;
        JLabel userLabel = new JLabel(userID);
        JLabel message = new JLabel("Choose the account Type you want to open");
        savingButton = new JButton("Open A Saving Account");
        checkingButton = new JButton("Open A Checking Account");
        returnButton = new JButton("Return");
        logOutButton = new JButton("Log Out");

        userLabel.setBounds(200,10,130,20);
        userLabel.setFont(new Font(null,Font.ITALIC,20));
        message.setBounds(150,30,270,12);
        message.setFont(new Font(null,Font.BOLD,10));
        savingButton.setBounds(20,70,150,25);
        checkingButton.setBounds(20,110,150,25);
        returnButton.setBounds(20,150,150,25);
        logOutButton.setBounds(20,190,150,25);

        frame = new JFrame("Account Menu");
        frame.add(userLabel);
        frame.add(message);
        frame.add(savingButton);
        frame.add(checkingButton);
        frame.add(returnButton);
        frame.add(logOutButton);

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

    }
}
