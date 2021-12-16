import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class StockGenerate implements ActionListener {
    private JFrame frame;
    private JButton generate;
    private JButton back;
    private JTextField nameText;
    private JTextField priceText;
    private JLabel message;
    private JLabel name;
    private JLabel price;

    private String stockPath = "/src/System Data/publicStock.txt";

    public StockGenerate(){
        message = new JLabel();
        name = new JLabel("Name");
        price = new JLabel("Price");

        frame = new JFrame("Stock Add");
        generate = new JButton("Generate");
        nameText = new JTextField();
        priceText = new JTextField();

        name.setBounds(20,10,50,20);
        nameText.setBounds(80,10,70,20);
        price.setBounds(20,40,50,20);
        priceText.setBounds(80,40,70,20);

        generate.setBounds(20,70,100,20);
        message.setBounds(20,100,200,20);

        generate.addActionListener(this);
        frame.add(name);
        frame.add(nameText);
        frame.add(generate);
        frame.add(priceText);
        frame.add(price);
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
            String name = nameText.getText();
            String price = priceText.getText();
            boolean numeric = true;

            try {
                Double num = Double.parseDouble(price);
            } catch (NumberFormatException f) {
                numeric = false;
            }
            if(numeric) {
                if (Double.valueOf(price) <= 0) {
                    message.setText("Incorrect Price");
                } else {
                    HashMap<String, String[]> map = fileEditor.toHash(stockPath);
                    if (map.containsKey(name)) {
                        message.setText("Stock already Exist");
                    } else {
                        fileEditor.writeFile(stockPath, new String[]{name, price, "USD"});
                        message.setText("Stock added");
                    }

                }
            }else {
                message.setText("input is not double");
            }
        }
    }
}
