import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
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
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

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
            if(date.length()==0){
                date = sdf.format(new Date());
                dailyReport(date);
                message.setText("report generated");
            }else if(fileEditor.isValid(date)) {
                dailyReport(date);
                message.setText("report generated");
            }else{
                message.setText("false format");
            }

        }if(e.getSource() == back){
            frame.dispose();
        }if(e.getSource()==resetButton) {
            text.setText("");
        }

    }
    private void dailyReport(String date) {
        String temp = date.replace("/","_");
        if(new File("/src/System Data/Daily report/"+temp+"/report.txt").exists()){
            message.setText("report already exist");
        }else {
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
            list.add(new String[]{"\n"});

            list.addAll(readMeta(date));
            list.addAll(readTransaction(date));


            fileEditor.listWrite("/src/System Data/Daily report/" + temp + "/", "report.txt", list);
        }
    }

    private List<? extends String[]> readTransaction(String Date) {
        List<String[]> list =  fileEditor.fileRead("/src/transaction.txt");
        List<String[]> result = new ArrayList<>();
        int withdraw_amount = 0;
        double withdraw_rmb = 0;
        double withdraw_usd = 0;
        double withdraw_gbp = 0;

        int stock_amount = 0;
        double stock_value = 0;
        int deposit_num = 0;
        double deposit_rmb = 0;
        double deposit_usd = 0;
        double deposit_gnp = 0;
        int transfer_num = 0;
        double transfer_rmb = 0;
        double transfer_usd = 0;
        double transfer_gnp = 0;
        int loan_pay = 0;
        int loan_receive = 0;
        double loan_rmb = 0;
        double loan_usd = 0;
        double loan_gnp = 0;

        for (String [] s : list){
            try {
                Date recordDate= new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(s[0]);
                String temp =sdf.format(recordDate);
                recordDate = sdf.parse(temp);
                if(recordDate.equals(sdf.parse(Date))){
                    String action = s[1];
                    switch (action){
                        case "deposit":
                            deposit_num++;
                            if(s[7].equals("RMB")) {

                                deposit_rmb += Double.valueOf(s[6]);
                            }else if(s[7].equals("USD")){

                                deposit_usd += Double.valueOf(s[6]);
                            }else{

                                deposit_gnp += Double.valueOf(s[6]);
                            }
                            break;
                        case "transfer":
                            transfer_num++;
                            if(s[7].equals("RMB")) {

                                transfer_rmb += Double.valueOf(s[6]);
                            }else if(s[7].equals("USD")){

                                transfer_usd += Double.valueOf(s[6]);
                            }else{

                                transfer_gnp += Double.valueOf(s[6]);
                            }
                            break;
                        case "loan":

                            double value = Double.valueOf(s[6]);

                            if (s[4].length()==0){
                                loan_pay++;
                            }else {
                                loan_receive++;
                                value=-value;
                            }
                            if(s[7].equals("RMB")) {

                                loan_rmb += value;
                            }else if(s[7].equals("USD")){

                                loan_usd += value;
                            }else{

                                loan_gnp += value;
                            }
                            break;
                        case "stock":
                            stock_amount++;
                            if(s[7].equals("USD")) {
                                stock_value += Double.valueOf(s[6]);
                            }
                            break;
                        case "withdraw":
                            withdraw_amount++;
                            if(s[7].equals("RMB")) {

                                withdraw_rmb += Double.valueOf(s[6]);
                            }else if(s[7].equals("USD")){

                                withdraw_usd += Double.valueOf(s[6]);
                            }else{

                                withdraw_gbp += Double.valueOf(s[6]);
                            }
                            break;

                    }


                }
            }catch (ParseException exp) {
                exp.printStackTrace();
            }
        }
        result.add(new String[]{"Number of deposit: ", String.valueOf(deposit_num)});
        result.add(new String[]{"Total of RMB deposit: ", String.valueOf(deposit_rmb)});
        result.add(new String[]{"Total of USD deposit: ", String.valueOf(deposit_usd)});
        result.add(new String[]{"Total of GBP deposit: ", String.valueOf(deposit_gnp)});
        result.add(new String[]{"\n"});


        result.add(new String[]{"Number of transfer: ", String.valueOf(transfer_num)});
        result.add(new String[]{"Total of RMB transfer: ", String.valueOf(transfer_rmb)});
        result.add(new String[]{"Total of USD transfer: ", String.valueOf(transfer_usd)});
        result.add(new String[]{"Total of GBP transfer: ", String.valueOf(transfer_gnp)});
        result.add(new String[]{"\n"});

        result.add(new String[]{"Number of loan received: ", String.valueOf(loan_receive)});
        result.add(new String[]{"Number of loan payed: ", String.valueOf(loan_pay)});
        result.add(new String[]{"Total of RMB loan balance: ", String.valueOf(loan_rmb)});
        result.add(new String[]{"Total of USD loan balance: ", String.valueOf(loan_usd)});
        result.add(new String[]{"Total of GBP loan balance: ", String.valueOf(loan_gnp)});
        result.add(new String[]{"\n"});

        result.add(new String[]{"Number of withdraw: ", String.valueOf(withdraw_amount)});
        result.add(new String[]{"Total of RMB withdraw: ", String.valueOf(withdraw_rmb)});
        result.add(new String[]{"Total of USD withdraw: ", String.valueOf(withdraw_usd)});
        result.add(new String[]{"Total of GBP withdraw: ", String.valueOf(withdraw_gbp)});
        result.add(new String[]{"\n"});

        result.add(new String[]{"Stock Transaction Performed: ", String.valueOf(stock_amount)});
        result.add(new String[]{"Stock Value in Transactions: ", String.valueOf(stock_value)});
        result.add(new String[]{"\n"});

        return result;
    }

    private List<String []> readMeta(String Date) {

        int saving_count = 0;
        int checking = 0;
        int loan_account = 0;
        int stock_account = 0;
        int new_user = 0;


        List<String[]> list = fileEditor.fileRead("/src/metadata.txt");
        List<String[]> result = new ArrayList<>();
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
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
                            case "createSec":
                                stock_account++;
                                break;

                        }
                    }
                } catch (ParseException exp) {
                    exp.printStackTrace();
                }
            }
            result.add(new String[]{"Number of New Account Created: ", String.valueOf(new_user)});
            result.add(new String[]{"\n"});
            result.add(new String[]{"Saving Account Created: ", String.valueOf(saving_count)});
            result.add(new String[]{"\n"});
            result.add(new String[]{"Checking Account Created: ", String.valueOf(checking)});
            result.add(new String[]{"\n"});
            result.add(new String[]{"Security Account Created: ", String.valueOf(stock_account)});
            result.add(new String[]{"\n"});


        }
        return result;

    }
}
