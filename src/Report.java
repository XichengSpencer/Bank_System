import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        list.add(new String[]{"Today's saving interest followed by loan interest rate:"});
        list.addAll(fileEditor.fileRead(interestPath));
        list.add(new String[]{"\n"});

        list.add(new String[]{"Today's exchange rate:"});
        list.addAll(fileEditor.fileRead(ratePath));
        list.add(new String[]{"\n"});

        list.add(new String[]{"Today's stock price:"});
        list.addAll(fileEditor.fileRead(stockPath));

        //TODO
        date = date.replace("/","_");
        fileEditor.listWrite("/src/System Data/Daily report/"+date+"/","report.txt",list);
    }
}
