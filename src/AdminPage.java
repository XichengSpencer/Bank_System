import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AdminPage implements ActionListener {
    private JButton changeInterestButton;
    private JButton changeExchangeButton;
    private JButton dailyReportButton;
    private JButton importButton;
    private JButton logOutButton;
    private JButton stockButton;
    private JLabel infolabel;
    private JFrame frame;
    private String uid;
    private String stockPath = "/src/System Data/publicStock.txt";
    private String interestPath = "/src/System Data/Interest.txt";
    private String ratePath =  "/src/System Data/Exchange.txt";

    public AdminPage(String userID) {
        JLabel adminLabel = new JLabel(userID);
        uid = userID;
        changeInterestButton = new JButton("Change Interest");
        changeExchangeButton = new JButton("Change Exchange Rate");
        importButton = new JButton("Import Data");
        dailyReportButton = new JButton("Daily Report");
        stockButton = new JButton("Stock Simulate");
        logOutButton = new JButton("Log Out");
        infolabel = new JLabel("");

        adminLabel.setBounds(200,10,180,30);
        adminLabel.setFont(new Font(null,Font.ITALIC,35));
        importButton.setBounds(20,70,200,25);
        changeInterestButton.setBounds(20,110,200,25);
        changeExchangeButton.setBounds(20,150,200,25);
        stockButton.setBounds(20,190,200,25);
        dailyReportButton.setBounds(20,230,200,25);
        logOutButton.setBounds(20,270,200,25);
        infolabel.setBounds(20,310,200,25);


        changeInterestButton.addActionListener(this);
        changeExchangeButton.addActionListener(this);
        importButton.addActionListener(this);
        dailyReportButton.addActionListener(this);
        stockButton.addActionListener(this);
        logOutButton.addActionListener(this);

        frame = new JFrame("Admin Page");
        frame.add(adminLabel);
        frame.add(infolabel);
        frame.add(changeInterestButton);
        frame.add(importButton);
        frame.add(dailyReportButton);
        frame.add(stockButton);
        frame.add(logOutButton);
        frame.add(changeExchangeButton);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 420);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==changeInterestButton) {
            changePrice(2);
        }
        if(e.getSource()==importButton) {
           importData();
        }
        if(e.getSource()==dailyReportButton) {
            new Report ();
            //dailyReport();

        }
        if(e.getSource()==stockButton) {
            changePrice(1);
        }
        if(e.getSource()==changeExchangeButton) {
            changePrice(3);
        }

        if(e.getSource()==logOutButton) {
            //TODO
            frame.dispose();
            new Login(new LoginData());
        }

    }



    private void importData() {
        fileEditor.writeFile(stockPath,new String[]{"SONY","1131.96","YEN"});
        fileEditor.writeFile(stockPath,new String[]{"NIO","374.60","RMB"});
        fileEditor.writeFile(stockPath,new String[]{"APPL","190.73","USD"});

        fileEditor.writeFile(ratePath,new String[]{"USD","1"});
        fileEditor.writeFile(ratePath,new String[]{"RMB","0.16"});
        fileEditor.writeFile(ratePath,new String[]{"YEN","0.0088"});

        //saving interest rate
        fileEditor.writeFile(interestPath,new String[]{"0.02"});
        //load interest rate
        fileEditor.writeFile(interestPath,new String[]{"0.10"});
    }

    private void changePrice(int numType) {
        //change stock price
        String filename,type;
        if(numType==1) {
            filename = stockPath;
            type = "Stock";
        }
        else if(numType==2) {
            filename = interestPath;
            type = "Interest";
        }else {
            filename = ratePath;
            type = "Exchange Rate";
        }

        List<String[]> infoList = fileEditor.fileRead(filename);
        fileEditor.clearFile(filename);
        //message display
        for (String[]info : infoList){

            if(numType==1) {
                double price = Double.valueOf(info[1]);
                price *= 0.9 + 0.2 * Math.random();
                info[1] = String.format("%.2f",price);
            }
            else if(numType==2)  {
                double price = Double.valueOf(info[0]);
                price *= 0.99 + 0.02 * Math.random();
                info[0] = String.format("%.5f",price);
            }else {
                double price = Double.valueOf(info[1]);
                price *= 0.99 + 0.02 * Math.random();
                info[1] = String.format("%.5f",price);
            }
            fileEditor.writeFile(filename,info);
        }
        infolabel.setText(infoList.size()+" "+type+" Prices Have Changed");
    }

}
