import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class LoanData {
    public static void updateLoan(Customer customer, String account, double loan, String currency){
        String accountPath = System.getProperty("user.dir") + "/src/System Data/" + customer.getId() + "/loan.txt";
        File accountFile = new File(accountPath);
        if (!accountFile.exists()) {
            try {
                accountFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        List<String[]> amountList = fileEditor.fileRead("/src/System Data/"+ customer.getId() + "/loan.txt");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(accountFile);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        boolean flag = false;
        for(String[] token :amountList){
            if (token[2].equals(currency)) { // find the correct currency
                // choose the same currency
                double amount1 = Double.valueOf(token[1]);
                amount1 += loan;
                token[1] = ""+amount1;
                flag = true;
                if(amount1 <= 0){
                    continue;
                }
            }
            fileEditor.writeFile("/src/System Data/"+customer.getId()+"/loan.txt", token);
        }
        if (!flag){
            fileEditor.writeFile("/src/System Data/"+customer.getId()+"/loan.txt", new String[]{account, ""+(int)loan, currency});
        }
    }

    public static void updateAccount(Customer customer, String account, String currency, double amount){
        String accountPath = System.getProperty("user.dir") + "/src/System Data/" + customer.getId() + "/" + account + ".txt";
        File accountFile = new File(accountPath);
        if (!accountFile.exists()) {
            try {
                accountFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        List<String[]> amountList = fileEditor.fileRead("/src/System Data/"+ customer.getId() + "/" + account + ".txt");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(accountFile);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        for(String[] token :amountList){
            if (token[2].equals(currency)) { // find the correct currency
                // choose the same currency
                double amount1 = Double.valueOf(token[1]);
                amount1 += amount;
                token[1] = ""+amount1;
            }
            fileEditor.writeFile("/src/System Data/"+customer.getId()+"/" + account + ".txt", token);
        }
    }

    public static HashMap<String, Double> getAllLoan(Customer customer){
        HashMap<String, Double> list = new HashMap<>();
        list.put("RMB", 0.0);
        list.put("USD", 0.0);
        list.put("GBP", 0.0);
        String accountPath = System.getProperty("user.dir")+"/src/System Data/"+customer.getId();
        File accountFolder = new File(accountPath);
        if (!accountFolder.exists()) {
            accountFolder.mkdirs();
            return null;
        } else {
            File loanFile = new File(accountPath+"/loan.txt");
            if (loanFile.length() != 0){
                List<String[]> loanList = fileEditor.fileRead("/src/System Data/"+customer.getId()+"/loan.txt");
                for (String[] token: loanList){
                    list.replace(token[2],list.get(token[2])+Double.valueOf(token[1]));
                }
            } else {
                return null;
            }

            return list;
        }
    }
}
