import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReportPresent implements ActionListener {
    private JFrame frame;
    private JButton open;
    private JLabel message;
    private JTextArea textArea;
    private JButton back;

    public ReportPresent(){
        message = new JLabel("Text Reader");
        frame = new JFrame("Report Generator");
        open = new JButton("Open");
        back = new JButton("back");
        textArea = new JTextArea();



        open.setBounds(450,40,100,20);

        message.setBounds(20,10,100,20);
        textArea.setBounds(10,70,550,500);
        JScrollPane scrollPane = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        open.addActionListener(this);
        frame.add(open);
        frame.add(message);
        frame.add(scrollPane);

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(700, 700);
        //frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==open) {
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir")+"/src/System Data/Daily Report/");
            chooser.showOpenDialog(null);
            File f = chooser.getSelectedFile();
            if(f==null){
                return;
            }
            String filepath = f.getAbsolutePath();

            try{
                FileReader reader = new FileReader(filepath);
                BufferedReader bf = new BufferedReader(reader);
                textArea.read(bf,null);
                bf.close();
                textArea.requestFocus();

            }catch (Exception x){
                JOptionPane.showMessageDialog(null,x);
            }
            if(e.getSource()==back){
                frame.dispose();
            }
        }



    }
}
