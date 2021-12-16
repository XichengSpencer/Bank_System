import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Report implements ActionListener {
    private JFrame frame;
    private JButton generate;
    private JButton back;
    private JButton resetButton;
    private JTextField text;
    private JLabel message;
    private String stockPath = "/src/System Data/publicStock.txt";
    private String interestPath = "/src/System Data/Interest.txt";
    private String ratePath =  "/src/System Data/Exchange.txt";
    private String metaPath = "/src/System Data/metadata.txt";
    private String transactionPath = "/src/transaction.txt";
    private SimpleDateFormat sdf =new SimpleDateFormat("MM/dd/yyyy");

    public Report(){
        message = new JLabel();
        frame = new JFrame("Report Generator");
        generate = new JButton("Generate");
        back = new JButton("Back");
        resetButton = new JButton("Reset");
        text = new JTextField("Enter mm/dd/yyyy");

        text.setBounds(50,10,200,20);
        generate.setBounds(20,40,100,20);
        resetButton.setBounds(125,40,100,20);
        back.setBounds(230,40,100,20);
        message.setBounds(20,70,250,20);

        resetButton.addActionListener(this);
        generate.addActionListener(this);
        back.addActionListener(this);

        frame.add(resetButton);
        frame.add(generate);
        frame.add(back);
        frame.add(text);
        frame.add(message);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(350, 200);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==generate){
            String date = text.getText();
            dailyReport(date);
            message.setText("report generated");
        }if(e.getSource() == back){
            frame.dispose();
        }if(e.getSource()==resetButton) {
            text.setText("");
        }

    }
    private void dailyReport(String date) {

        List<String[]> list = new ArrayList<String[]>();
        list.add(new String[]{"Current saving interest followed by loan interest rate:"});
        list.addAll(fileEditor.fileRead(interestPath));
        list.add(new String[]{"\n"});

        list.add(new String[]{"Current exchange rate:"});
        List<String[]> exchangelist = fileEditor.fileRead(ratePath);
        list.addAll(exchangelist);
        list.add(new String[]{"\n"});


        list.add(new String[]{"Current stock price:"});
        List<String[]> stocklist =fileEditor.fileRead(stockPath);
        list.addAll(stocklist);

        list.addAll(readMeta(date));

        date = date.replace("/","_");
        fileEditor.listWrite("/src/System Data/Daily report/"+date+"/","report.txt",list);
    }

    private List<String []> readMeta(String Date) {

        int saving_count = 0;
        int checking = 0;
        int loan_account = 0;
        int stock_account = 0;
        double stock_value = 0;
        int new_user = 0;
        int deposit_num = 0;
        double deposit_rmb = 0;
        double deposit_usd = 0;
        double deposit_gnp = 0;
        int transaction_num = 0;
        double transaction_rmb = 0;
        double transaction_usd = 0;
        double transaction_gnp = 0;
        int loan_num = 0;
        double loan_rmb = 0;
        double loan_usd = 0;
        double loan_gnp = 0;

        List<String[]> list = fileEditor.fileRead("/src/System Data/metadata.txt");
        List<String[]> result = new ArrayList<>();
        if (list.size() > 0) {
            for (int i = 1; i < list.size(); i++) {
                try {
                    Date record = sdf.parse(list.get(i)[0]);
                    Date current = sdf.parse(Date);
                    if (record.equals(current)) {
                        String action = list.get(i)[1];
                        switch (action) {
                            case "newUser":
                                new_user++;
                            case "createSav":
                                saving_count++;
                                break;
                            case "createChk":
                                checking++;
                                break;
                            case "depositRMB":
                                deposit_num++;
                                deposit_rmb += Double.valueOf(list.get(i)[2]);
                                break;
                            case "depositUSD":
                                deposit_num++;
                                deposit_usd += Double.valueOf(list.get(i)[2]);
                                break;
                            case "depositGNP":
                                deposit_num++;
                                deposit_gnp += Double.valueOf(list.get(i)[2]);
                                break;
                            case "transactionUSD":
                                transaction_num++;
                                transaction_usd += Double.valueOf(list.get(i)[2]);
                                break;
                            case "transactionRMB":
                                transaction_num++;
                                transaction_rmb += Double.valueOf(list.get(i)[2]);
                                break;
                            case "transactionGNP":
                                transaction_num++;
                                transaction_gnp += Double.valueOf(list.get(i)[2]);
                                break;
                            case "loanRMB":
                                loan_num++;
                                loan_rmb += Double.valueOf(list.get(i)[2]);
                                break;
                            case "loanUSD":
                                loan_num++;
                                loan_rmb += Double.valueOf(list.get(i)[2]);
                                break;
                            case "loanGNP":
                                loan_num++;
                                loan_rmb += Double.valueOf(list.get(i)[2]);
                                break;
                            case"Stock":
                                stock_value += Double.valueOf(list.get(i)[2]);
                        }
                    }
                } catch (ParseException exp) {
                    exp.printStackTrace();
                }
            }
            result.add(new String[]{"Number of New Account Created: ", String.valueOf(new_user)});
            result.add(new String[]{"Saving Account Created: ", String.valueOf(saving_count)});
            result.add(new String[]{"Checking Account Created: ", String.valueOf(checking)});
            result.add(new String[]{"Loan Account Created: ", String.valueOf(loan_account)});
            result.add(new String[]{"Stock Account Created: ", String.valueOf(stock_account)});
            result.add(new String[]{"Stock transaction amount: ", String.valueOf(stock_account)});
            result.add(new String[]{"Total number of deposit: ", String.valueOf(deposit_num)});
            result.add(new String[]{"Total of RMB deposit: ", String.valueOf(deposit_rmb)});
            result.add(new String[]{"Total of USD deposit: ", String.valueOf(deposit_usd)});
            result.add(new String[]{"Total of GNP deposit: ", String.valueOf(deposit_gnp)});
            result.add(new String[]{"Total number of transaction: ", String.valueOf(transaction_num)});
            result.add(new String[]{"Total of RMB transaction: ", String.valueOf(transaction_rmb)});
            result.add(new String[]{"Total of USD transaction: ", String.valueOf(transaction_usd)});
            result.add(new String[]{"Total of GNP transaction: ", String.valueOf(transaction_gnp)});
            result.add(new String[]{"Total number of loan: ", String.valueOf(loan_num)});
            result.add(new String[]{"Total of RMB loan: ", String.valueOf(loan_rmb)});
            result.add(new String[]{"Total of USD loan: ", String.valueOf(loan_usd)});
            result.add(new String[]{"Total of GNP loan: ", String.valueOf(loan_gnp)});

        }
        return result;

    }
}
