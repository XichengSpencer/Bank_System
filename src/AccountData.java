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
            Account sAccount = new Account(customer);
            if (savingAccount.length() != 0) {
                List<String[]> accountList = fileEditor.fileRead("/src/System Data/"+customer.getId()+"/saving.txt");
                for(String[] token :accountList){
                    String accountNumber = token[0];
                    sAccount.setId(accountNumber);
                    sAccount.addAmount(token[2], Double.valueOf(token[1]));
                }
                list.put("saving", sAccount);
            }
            File checkingAccount = new File(accountPath + "/checking.txt");
            Account cAccount = new Account("checking", customer);
            if (checkingAccount.length() != 0) {
                List<String[]> accountList = fileEditor.fileRead("/src/System Data/"+customer.getId()+"/checking.txt");
                for(String[] token :accountList){
                    String accountNumber = token[0];
                    cAccount.setId(accountNumber);
                    cAccount.addAmount(token[2], Double.valueOf(token[1]));
                }
                list.put("checking", cAccount);
            }
            File stockAccount = new File(accountPath + "/stock.txt");
            Account stAccount = new Account("stock", customer);
            if (stockAccount.length() != 0) {
                List<String[]> accountList = fileEditor.fileRead("/src/System Data/"+customer.getId()+"/stock.txt");
                for(String[] token :accountList){
                    String accountNumber = token[0];
                    stAccount.setId(accountNumber);
                    stAccount.addAmount(token[2], Double.valueOf(token[1]));
                }
                list.put("Stock", stAccount);
            }
        }
        return list;
    }

    public Account getAccount(String  accountType, Customer customer, String accountNumber) {
        Account account = new Account(accountType, customer, accountNumber);

        String accountPath = System.getProperty("user.dir")+"/src/System Data/"+customer.getId();
        File accountFolder = new File(accountPath);
        if (!accountFolder.exists()) {
            accountFolder.mkdirs();
            return null;
        }

        if (accountType.equals("saving")) {
            File savingAccount = new File(accountPath + "/saving.txt");
            if (savingAccount.length() != 0) {
                List<String[]> accountList = fileEditor.fileRead("/src/System Data/"+customer.getId()+"/saving.txt");
                for(String[] token :accountList){
                    account.addAmount(token[2], Double.valueOf(token[1]));
                }
            }
        } else if (accountType.equals("checking")) {
            File checkingAccount = new File(accountPath + "/checking.txt");
            if (checkingAccount.length() != 0) {
                List<String[]> accountList = fileEditor.fileRead("/src/System Data/" + customer.getId() + "/checking.txt");
                for (String[] token : accountList) {
                    account.addAmount(token[2], Double.valueOf(token[1]));
                }
            }
        } else {
            File stockAccount = new File(accountPath + "/stock.txt");
            if (stockAccount.length() != 0) {
                List<String[]> accountList = fileEditor.fileRead("/src/System Data/"+customer.getId()+"/stock.txt");
                for(String[] token :accountList){
                    account.addAmount(token[2], Double.valueOf(token[1]));
                }
            }
        }
        return account;
    }
}