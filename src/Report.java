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
    private JTextField text;
    private String stockPath = "/src/System Data/publicStock.txt";
    private String interestPath = "/src/System Data/Interest.txt";
    private String ratePath =  "/src/System Data/Exchange.txt";

    public Report(){
        frame = new JFrame("Report Generator");
        generate = new JButton("generate");
        back = new JButton("back");
        text = new JTextField("Enter mm-dd-yyyy");

        text.setBounds(50,10,200,20);
        generate.setBounds(20,40,100,20);
        back.setBounds(180,40,100,20);

        frame.add(generate);
        frame.add(back);
        frame.add(text);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 200);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==generate){
            //dailyReport();
        }if(e.getSource() == back){
            frame.dispose();
        }
    }
    private void dailyReport() {
        String path = "src/System Data/";

        List<String[]> list = new ArrayList<String[]>();
        list.add(new String[]{"Today's saving interest followed by loan interest rate:"});
        list.addAll(fileEditor.fileRead(interestPath));
        list.add(new String[]{"\n\n"});

        list.add(new String[]{"Today's exchange rate:"});
        list.addAll(fileEditor.fileRead(ratePath));
        list.add(new String[]{"\n\n"});

        list.add(new String[]{"Today's stock price:"});
        list.addAll(fileEditor.fileRead(stockPath));

        //TODO
        fileEditor.listWrite("",list);
    }
}
