import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomerData {
    private static CustomerData customerData = new CustomerData();

    public static CustomerData getInstance(){
        return customerData;
    }

    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        String loginPath = System.getProperty("user.dir")+"/src/login.txt";
        File loginFile = new File(loginPath);
        if (loginFile.length() != 0) {
            List<String[]> loginList = fileEditor.fileRead("/src/login.txt");
            for(String[] token :loginList){
                String userName = token[0];
                String userId = token[2];
                String passWord = token[1];
                Customer customer = new Customer(userName, passWord, userId);
                customer.setAccountList(AccountData.getInstance().getAccountList(customer));
                customers.add(customer);
            }
        }
//        customer.setAccountList(AccountData.getInstance().getAccountList(customer));
        return customers;
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
