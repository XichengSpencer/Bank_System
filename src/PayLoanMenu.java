import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PayLoanMenu implements ActionListener {

    private String userName;
    private JFrame frame;

    private JFrame previous;

    public PayLoanMenu(String userName, JFrame previous) {
        this.userName = userName;
        this.previous = previous;

        JLabel userLabel = new JLabel(userName);

        userLabel.setBounds(200,10,130,20);
        userLabel.setFont(new Font(null,Font.ITALIC,20));

        frame = new JFrame("Account Menu");
        frame.add(userLabel);

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
