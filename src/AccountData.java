import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AccountData {
    private static AccountData accountData = new AccountData();

    public static AccountData getInstance(){
        return accountData;
    }

//    public Account getAccount(String accountNumber) {
//        String accountPath = System.getProperty("user.dir")+"/src/System Data/"+customer.getId();
//    }

    public HashMap<String, Account> getAccountList(Customer customer) {
        HashMap<String,Account> list = new HashMap<>();
        String userName = customer.getName();
        String passWord = customer.getPassword();

        String accountPath = System.getProperty("user.dir")+"/src/System Data/"+customer.getId();
        File accountFolder = new File(accountPath);
        if (!accountFolder.exists()) {
            accountFolder.mkdirs();
            return null;
        } else {
            File savingAccount = new File(accountPath + "/saving.txt");
            if (savingAccount.length() != 0) {
                List<String[]> accountList = fileEditor.fileRead("/src/System Data/"+customer.getId()+"/saving.txt");
                for(String[] token :accountList){
                    String accountNumber = token[0];
                    Account account = new Account(customer);
                    list.put("saving", account);
                }
            }
            File checkingAccount = new File(accountPath + "/checking.txt");
            if (checkingAccount.length() != 0) {
                List<String[]> accountList = fileEditor.fileRead("/src/System Data/"+customer.getId()+"/checking.txt");
                for(String[] token :accountList){
                    String accountNumber = token[0];
                    Account account = new Account(customer);
                    list.put("checking", account);
                }
            }
            File stockAccount = new File(accountPath + "/stock.txt");
            if (savingAccount.length() != 0) {
                List<String[]> accountList = fileEditor.fileRead("/src/System Data/"+customer.getId()+"/stock.txt");
                for(String[] token :accountList){
                    String accountNumber = token[0];
                    Account account = new Account(customer);
                    list.put("Stock", account);
                }
            }

        }
        return list;
    }

}