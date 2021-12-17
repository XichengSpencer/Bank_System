import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class StockMenu implements ActionListener {
    private String userName;

    private JButton returnButton;
    private JButton logOutButton;

    private JFrame frame;
    private JFrame previous;
    private Customer customer;

    private JComboBox<String> sellectStockBuy;
    private JTextField shareBuy;
    private JButton buyButton;
    private JLabel buyWarningLabel = new JLabel();

    private JComboBox<String> sellectStockSell;
    private JTextField shareSell;
    private JButton sellButton;
    private JLabel sellWarningLabel = new JLabel();
    JScrollPane sellScroll;

    private HashMap<String, Double> stockData;
    private HashMap<String, String[]> holdingShareData;
    private double securityBalance;
    private JLabel securityBalanceLabel;


    public StockMenu(String userName, JFrame previous) {
        CustomerData customerData = CustomerData.getInstance();
        this.customer = customerData.selectCustomer(userName);

        stockData = StockData.getStockInformation();
        holdingShareData = StockData.getHoldingShareInformation(customer);

        JLabel securityLabel = new JLabel("Security Account Balance: ");
        String accountPath = System.getProperty("user.dir") + "/src/System Data/" + customer.getId() + "/security.txt";
        File accountFile = new File(accountPath);
        if (!accountFile.exists()){
            securityBalanceLabel = new JLabel("You don't have a security account yet!");
        } else {
            securityBalance = StockData.getSecurityBalance(customer);
            if (securityBalance == -1){
                securityBalanceLabel = new JLabel("You don't have a security account yet!");
            } else {
                securityBalanceLabel = new JLabel(""+securityBalance);
            }
        }


        this.userName = userName;
        this.previous = previous;

        JLabel userLabel = new JLabel(userName);
        JLabel stockInformationLabel = new JLabel("Stock Information:");
        JLabel selectStockLabel = new JLabel("Stock to buy: ");
        sellectStockBuy = new JComboBox<>();
        JLabel shareLabel = new JLabel("Shares to buy: ");
        shareBuy = new JTextField();
        buyButton = new JButton("Buy");

        JLabel holdStockInformationLabel = new JLabel("Your Stock Information:");
        JLabel selectSellStockLabel = new JLabel("Stock to sell: ");
        sellectStockSell = new JComboBox<>();
        JLabel sellShareLabel = new JLabel("Shares to sell: ");
        shareSell = new JTextField();
        sellButton = new JButton("Sell");

        logOutButton = new JButton("Log Out");
        returnButton = new JButton("Return");

        userLabel.setBounds(380,10,130,20);
        userLabel.setFont(new Font(null,Font.ITALIC,20));
        securityLabel.setBounds(60, 50, 200, 25);
        securityBalanceLabel.setBounds(260, 50, 500, 25);
        stockInformationLabel.setBounds(450, 100, 150, 25);
        selectStockLabel.setBounds(450, 500, 150, 25);
        sellectStockBuy.setBounds(570, 500, 130, 25);
        shareLabel.setBounds(450, 540, 150, 25);
        shareBuy.setBounds(570, 540, 130, 25);
        buyButton.setBounds(570, 580, 130, 25);
        buyWarningLabel.setBounds(450, 620, 500, 25);

        holdStockInformationLabel.setBounds(60, 100, 150, 25);
        selectSellStockLabel.setBounds(60, 500, 150, 25);
        sellectStockSell.setBounds(180, 500, 130, 25);
        sellShareLabel.setBounds(60, 540, 150, 25);
        shareSell.setBounds(180, 540, 130, 25);
        sellButton.setBounds(180, 580, 130, 25);
        sellWarningLabel.setBounds(60, 620, 500, 25);


        returnButton.setBounds(20,730,80,25);
        logOutButton.setBounds(120,730,80,25);

        frame = new JFrame("Stock Menu");
        frame.add(userLabel);
        frame.add(stockInformationLabel);
        frame.add(selectStockLabel);
        sellectStockBuy.addItem("--Stock--");
        for (String s : stockData.keySet()){
            sellectStockBuy.addItem(s);
        }
        frame.add(sellectStockBuy);
        frame.add(shareLabel);
        frame.add(shareBuy);
        frame.add(securityBalanceLabel);
        frame.add(securityLabel);
        frame.add(buyWarningLabel);

        frame.add(holdStockInformationLabel);
        frame.add(selectSellStockLabel);
        sellectStockSell.addItem("--Stock--");
        if (holdingShareData != null) {
            for (String s : holdingShareData.keySet()) {
                sellectStockSell.addItem(s);
            }
        }
        frame.add(sellectStockSell);
        frame.add(sellShareLabel);
        frame.add(shareSell);
        frame.add(sellWarningLabel);

        if (StockData.getSavingBalace(customer) < 2500.0){
            buyWarningLabel.setText("You can't buy since you don't have 2500 in saving account");
            buyWarningLabel.setText(""+StockData.getSavingBalace(customer));
        } else {
            frame.add(buyButton);
        }

        frame.add(sellButton);
        frame.add(returnButton);
        frame.add(logOutButton);

        returnButton.addActionListener(this);
        logOutButton.addActionListener(this);
        buyButton.addActionListener(this);
        sellButton.addActionListener(this);

        String[] buyColumn = { "Stock", "Price" };
        String[][] buyData = new String[stockData.size()][2];
        int buyIndex = 0;
        for (Map.Entry<String, Double> entry: stockData.entrySet()){
            buyData[buyIndex][0] = entry.getKey();
            buyData[buyIndex][1] = ""+entry.getValue();
            buyIndex++;
        }
        JTable buyTable = new JTable(buyData, buyColumn);
        buyTable.setFillsViewportHeight(true);
        JScrollPane buyScroll = new JScrollPane(buyTable);
        buyTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        buyTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        buyScroll.setBounds(450, 130, 250, 350);
        buyScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(buyScroll);

        String[] sellColumn = { "Stock", "Share", "Ave price", "Current Total" };
        String[][] sellData;
        if (holdingShareData != null) {
            sellData = new String[holdingShareData.size()][4];
            int sellIndex = 0;
            for (Map.Entry<String, String[]> entry : holdingShareData.entrySet()) {
                sellData[sellIndex][0] = entry.getKey();
                sellData[sellIndex][1] = entry.getValue()[0];
                sellData[sellIndex][2] = entry.getValue()[1];
                sellData[sellIndex][3] = "" + (Integer.parseInt(entry.getValue()[0]) * stockData.get(entry.getKey()));
                sellIndex++;
            }
        } else {
            sellData = new String[0][4];
        }
        JTable sellTable = new JTable(sellData, sellColumn);
        sellTable.setFillsViewportHeight(true);
        sellScroll = new JScrollPane(sellTable);
        sellTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        sellTable.getColumnModel().getColumn(1).setPreferredWidth(90);
        sellTable.getColumnModel().getColumn(2).setPreferredWidth(90);
        sellTable.getColumnModel().getColumn(3).setPreferredWidth(90);
        sellScroll.setBounds(60, 130, 350, 350);
        sellScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(sellScroll);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 800);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void updateSellTable(){
        frame.remove(sellScroll);
        holdingShareData = StockData.getHoldingShareInformation(customer);
        String[] sellColumn = { "Stock", "Share", "Ave price", "Current Total" };
        String[][] sellData;
        if (holdingShareData != null) {
            sellData = new String[holdingShareData.size()][4];
            int sellIndex = 0;
            for (Map.Entry<String, String[]> entry : holdingShareData.entrySet()) {
                sellData[sellIndex][0] = entry.getKey();
                sellData[sellIndex][1] = entry.getValue()[0];
                sellData[sellIndex][2] = entry.getValue()[1];
                sellData[sellIndex][3] = String.format("%.2f",(Integer.parseInt(entry.getValue()[0]) *
                        stockData.get(entry.getKey())));
                sellIndex++;
            }
        } else {
            sellData = new String[0][4];
        }
        JTable sellTable = new JTable(sellData, sellColumn);
        sellTable.setFillsViewportHeight(true);
        sellScroll = new JScrollPane(sellTable);
        sellTable.getColumnModel().getColumn(0).setPreferredWidth(50);
        sellTable.getColumnModel().getColumn(1).setPreferredWidth(90);
        sellTable.getColumnModel().getColumn(2).setPreferredWidth(90);
        sellTable.getColumnModel().getColumn(3).setPreferredWidth(90);
        sellScroll.setBounds(60, 130, 350, 350);
        sellScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.add(sellScroll);
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
        if (buyButton.equals(e.getSource())){
            if (shareBuy.getText().length() == 0){
                buyWarningLabel.setText("Please input the amount of share you want to buy!");
                return;
            }

            int shareAmount = Integer.parseInt(shareBuy.getText());
            String stockTobuy = (String) sellectStockBuy.getSelectedItem();

            if (stockTobuy.equals("--Stock--")){
                buyWarningLabel.setText("Please select a stock to buy!");
                return;
            }

            double amountToPay = shareAmount*stockData.get(stockTobuy);
            if (amountToPay > securityBalance){
                buyWarningLabel.setText("Insufficient balance!");
                return;
            }

            securityBalance -= amountToPay;
            String temp = String.format("%.2f", securityBalance);
            securityBalance = Double.valueOf(temp);

            StockData.updateStock(customer, stockTobuy, shareAmount, stockData.get(stockTobuy));
            StockData.updateAccount(customer, securityBalance);

            buyWarningLabel.setText("Purchase Complete!");
            securityBalanceLabel.setText(""+securityBalance);
            updateSellTable();
            TransactionData.getInstance().logTransaction("stock", customer.getName(), "security",
                    "bank", "", amountToPay, "USD");
        }
        if (sellButton.equals(e.getSource())){
            if (shareSell.getText().length() == 0){
                sellWarningLabel.setText("Please input the amount of share you want to buy!");
                return;
            }
            int shareAmount = Integer.parseInt(shareSell.getText());
            String stockToSell = (String) sellectStockSell.getSelectedItem();

            if (stockToSell.equals("--Stock--")){
                sellWarningLabel.setText("Please select a stock to sell!");
                return;
            }

            if (shareAmount > Double.valueOf(holdingShareData.get(stockToSell)[0])){
                sellWarningLabel.setText("You don't have enough share to sell!");
                return;
            }

            double amountToAdd = shareAmount*stockData.get(stockToSell);
            securityBalance += amountToAdd;
            String temp = String.format("%.2f", securityBalance);
            securityBalance = Double.valueOf(temp);

            StockData.updateStock(customer, stockToSell, 0-shareAmount, -1);
            StockData.updateAccount(customer, securityBalance);

            sellWarningLabel.setText("Sell Complete!");
            securityBalanceLabel.setText(""+securityBalance);
            updateSellTable();
            TransactionData.getInstance().logTransaction("stock", "bank", "",
                    customer.getId(), "security", amountToAdd, "USD");
        }
    }
}
