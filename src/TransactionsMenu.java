import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class TransactionsMenu implements ActionListener {


    private JLabel warningLabel = new JLabel();
    private JTextArea outputTextArea;

    private JButton logOutButton;
    private JButton returnButton;
    private String username;
    private JFrame previous;

    private  JFrame frame;

    public TransactionsMenu(String userName, JFrame previous){
        username = userName;
        this.previous = previous;
        JLabel userLabel = new JLabel(userName);
        JLabel savingLabel = new JLabel("Saving Account:");
        JLabel checkingLable = new JLabel("Checking Account:");

        returnButton = new JButton("Return");
        logOutButton = new JButton("Log Out");

        userLabel.setBounds(200,10,130,20);
        userLabel.setFont(new Font(null,Font.ITALIC,20));
        warningLabel.setBounds(20, 220, 300, 25);

        savingLabel.setBounds(70, 30, 150, 25);
        checkingLable.setBounds(240, 30, 150, 25);

        returnButton.setBounds(20,230,80,25);
        logOutButton.setBounds(120,230,80,25);

        frame = new JFrame("Transactions log");
        frame.add(userLabel);
        frame.add(checkingLable);
        frame.add(warningLabel);
        frame.add(savingLabel);
        frame.add(checkingLable);

        frame.add(returnButton);
        frame.add(logOutButton);

        returnButton.addActionListener(this);
        logOutButton.addActionListener(this);

        //prepare data for amount
        //get the customer
        CustomerData customerData = CustomerData.getInstance();
        Customer customer = customerData.selectCustomer(userName);
        HashMap<String, Account> accountList = customer.getAccountList();


//        outputTextArea = new JTextArea("",5,20);
//        JScrollPane scrollPane = new JScrollPane(outputTextArea);
//        scrollPane.setBounds(50, 60, 300, 160);
//        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        frame.add(scrollPane);
        String[] columnNames = { "Time", "Operation", "From User", "Account", "To User", "Account", "Amount", "Currency" };
        Object[][] data = { { "1", "2", 3, 4, 5, 6, 7, 8 } };
        JTable jTable = new JTable(data, columnNames);
        jTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(jTable);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        jTable.getColumnModel().getColumn(2).setPreferredWidth(120);
        jTable.setModel(new DefaultTableModel());
        ((DefaultTableModel) jTable.getModel()).setColumnIdentifiers(new String[]{ "Time", "Operation", "From User", "Account", "To User", "Account", "Amount", "Currency"});
        ((DefaultTableModel) jTable.getModel()).addRow(new String[]{"1", "2", "3", "4", "5", "6", "7", "8" });
        scrollPane.setBounds(50, 60, 300, 160);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // open a saving account

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
