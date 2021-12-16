import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Login implements ActionListener {
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
    LoginData logininfo;

    public Login(LoginData accounts){

        logininfo = accounts;
        frame = new JFrame("Login Page");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400,300));
        frame.setResizable(false);

        logInButton.addActionListener(this);
        signUpButton.addActionListener(this);
        resetButton.addActionListener(this);
        frame.add(panel1);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //create default metadata file
        File folder = new File(System.getProperty("user.dir")+"/src/System Data/");
        if(!folder.exists()){
            folder.mkdirs();
        }
    }

    public void actionPerformed(ActionEvent e) {

        if(e.getSource()==resetButton) {
            userText.setText("");
            userPassword.setText("");
        }

        if(e.getSource()==logInButton) {

            String userName = userText.getText();
            String password = String.valueOf(userPassword.getPassword());

            if(logininfo.getLoginInfo().containsKey(userName)) {
                if(logininfo.getPassW(userName).equals(password)) {
                    messageLabel.setForeground(Color.green);
                    messageLabel.setText("Login successful");
                    frame.dispose();
                    if(userName.equals("Admin")){

                        AdminPage adminPage= new AdminPage(userName);
                    }
                    else{
                        UserPage userPage = new UserPage(userName);
                    }
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
            String userName = userText.getText();
            String password = String.valueOf(userPassword.getPassword());
            if(logininfo.getLoginInfo().containsKey(userName)) {
                messageLabel.setForeground(Color.black);
                messageLabel.setText("username already exist");
            }else { // Sign up for the customer
                Customer newCus = new Customer(userName, password);
//                logininfo.set(userID,password);
                logininfo.set(newCus);

                messageLabel.setText("Account Created");
            }
        }
    }
}
