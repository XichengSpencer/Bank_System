import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class Login extends  JFrame implements ActionListener {
    private JTextField userText;
    private JPanel panel1;
    private JPasswordField userPassword;
    private JButton logInButton;
    private JButton signUpButton;
    private JButton resetButton;
    private JLabel messageLabel;
    private JLabel userName;
    private JLabel password;
    private JFrame frame;
    private JFrame Login;
    HashMap<String,String> logininfo = new HashMap<String,String>();

    public Login(HashMap<String,String> loginInfoOriginal){

        logininfo = loginInfoOriginal;
        frame = new JFrame("Login Page");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400,300));
        frame.setResizable(false);

        logInButton.setFocusable(false);
        signUpButton.setFocusable(false);
        logInButton.addActionListener(this);
        signUpButton.addActionListener(this);
        resetButton.addActionListener(this);
        frame.add(panel1);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==resetButton) {
            userText.setText("");
            userPassword.setText("");
        }

        if(e.getSource()==logInButton) {

            String userID = userText.getText();
            String password = String.valueOf(userPassword.getPassword());

            if(logininfo.containsKey(userID)) {
                if(logininfo.get(userID).equals(password)) {
                    messageLabel.setForeground(Color.green);
                    messageLabel.setText("Login successful");
                    frame.dispose();
                    UserPage userPage = new UserPage(userID);
                }
                else {
                    messageLabel.setForeground(Color.red);
                    messageLabel.setText("Wrong password");
                }

            }
            else {
                messageLabel.setForeground(Color.red);
                messageLabel.setText("username not found");
            }
        }
        if(e.getSource() == signUpButton){
            String userID = userText.getText();
            String password = String.valueOf(userPassword.getPassword());
            if(logininfo.containsKey(userID)) {
                messageLabel.setForeground(Color.black);
                messageLabel.setText("username already exist");
            }else {
                logininfo.put(userID,password);
            }
        }
    }
}
