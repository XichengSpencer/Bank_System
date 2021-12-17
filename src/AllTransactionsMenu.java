import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AllTransactionsMenu implements ActionListener {


    private JLabel warningLabel = new JLabel();
    private JTextArea outputTextArea;

    private JButton logOutButton;
    private JButton returnButton;
    private JFrame previous;

    private  JFrame frame;

    public AllTransactionsMenu(JFrame previous){
        this.previous = previous;
        JLabel msgLabel = new JLabel("All Transactions:");

        returnButton = new JButton("Return");
        logOutButton = new JButton("Log Out");

        warningLabel.setBounds(20, 220, 300, 25);

        msgLabel.setBounds(50, 30, 150, 25);

        returnButton.setBounds(20,500,80,25);
        logOutButton.setBounds(120,500,80,25);

        frame = new JFrame("Transactions log");
        frame.add(msgLabel);
        frame.add(warningLabel);

        frame.add(returnButton);
        frame.add(logOutButton);

        returnButton.addActionListener(this);
        logOutButton.addActionListener(this);

        // build the scrollPane
        String[] columnNames = { "Time", "OP", "From", "Acct", "To", "Acct", "Amt", "Cy" };
        Object[][] data = { { 1, 2, 3, 4, 5, 6, 7, 8 } };
        JTable jTable = new JTable(data, columnNames);
        jTable.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(jTable);
        jTable.setModel(new DefaultTableModel());
        // set the table
        ((DefaultTableModel) jTable.getModel()).setColumnIdentifiers(new String[]{ "Time", "OP", "From", "Acct", "To", "Acct", "Amt", "Cy"});
        jTable.getColumnModel().getColumn(0).setPreferredWidth(250);
        List<String[]> allTransaction = new TransactionData().getAllTransactions();
        for (String[] transaction : allTransaction) {
            ((DefaultTableModel) jTable.getModel()).addRow(transaction);
        }
        scrollPane.setBounds(50, 60, 700, 400);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(scrollPane);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
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
