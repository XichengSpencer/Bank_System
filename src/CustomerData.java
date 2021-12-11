import java.io.File;
import java.util.List;

public class CustomerData {
    private static CustomerData customerData = new CustomerData();

    public static CustomerData getInstance(){
        return customerData;
    }

    public Customer selectCustomer(String userName) {

        Customer customer = null;
        String loginPath = System.getProperty("user.dir")+"/src/login.txt";
        File loginFile = new File(loginPath);
        if (loginFile.length() != 0) {
            List<String[]> loginList = fileEditor.fileRead("/src/login.txt");
            for(String[] token :loginList){
                if (token[0].equals(userName)) {
                    String userId = token[2];
                    String passWord = token[1];
                    customer = new Customer(userName, passWord, userId);
                    customer.setAccountList(AccountData.getInstance().getAccountList(customer));
                }
            }
        }
//        customer.setAccountList(AccountData.getInstance().getAccountList(customer));
        return customer;
    }

}
