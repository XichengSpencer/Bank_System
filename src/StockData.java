import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockData {
    public static HashMap<String, Double> getStockInformation(){
        HashMap<String, Double> list = new HashMap<>();
        HashMap<String, String[]> raw = fileEditor.toHash("/src/publicStock.txt");
        for (String key: raw.keySet()){
            list.put(key, Double.valueOf(raw.get(key)[0]));
        }
        return list;
    }

    public static HashMap<String, String[]> getHoldingShareInformation(Customer customer){
        HashMap<String, String[]> list = new HashMap<>();
        String accountPath = System.getProperty("user.dir") + "/src/System Data/" + customer.getId() + "/stock.txt";
        File accountFile = new File(accountPath);

        if (!accountFile.exists()) {
            return null;
        }

        list = fileEditor.toHash("/src/System Data/" + customer.getId() + "/stock.txt");
        if (list.size() == 0){
            return null;
        } else {
            return list;
        }
    }

    public static double getSecurityBalance(Customer customer){
        List<String[]> accounts = fileEditor.fileRead("/src/System Data/"+customer.getId()+"/security.txt");
        if (accounts.size()==0){
            return -1;
        } else {
            return Double.valueOf(accounts.get(0)[1]);
        }
    }

    public static double getSavingBalace(Customer customer){
        String accountPath = System.getProperty("user.dir") + "/src/System Data/" + customer.getId() + "/saving.txt";
        File accountFile = new File(accountPath);

        if (!accountFile.exists()) {
            return -1;
        }

        List<String[]> accounts = fileEditor.fileRead("/src/System Data/"+customer.getId()+"/saving.txt");
        if (accounts.size()==0){
            return -1;
        }
        for (String[] a : accounts){
            if (a[2] == "USD"){
                return Double.valueOf(a[1]);
            }
        }
        return -1;
    }

    public static void updateStock(Customer customer, String stockName, int share, double sharePrice){
        String accountPath = System.getProperty("user.dir") + "/src/System Data/" + customer.getId() + "/stock.txt";
        File accountFile = new File(accountPath);

        if (!accountFile.exists()) {
            try {
                accountFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        List<String[]> amountList = fileEditor.fileRead("/src/System Data/"+ customer.getId() + "/stock.txt");
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
            if (token[0].equals(stockName)){
                flag = true;
                int oldShare = Integer.parseInt(token[1]);
                int newShare = oldShare + share;
                if (newShare==0){
                    continue;
                }
                if (sharePrice>0){
                    double newAverage = Double.valueOf(token[2]);
                    newAverage = (newAverage*oldShare + share*sharePrice)/(newShare);
                    token[2] = ""+newAverage;
                }
                token[1] = ""+newShare;
            }

            fileEditor.writeFile("/src/System Data/"+customer.getId()+"/stock.txt", token);
        }
        if (!flag){
            fileEditor.writeFile("/src/System Data/"+customer.getId()+"/stock.txt", new String[]{stockName,
                    ""+share, ""+sharePrice});
        }
    }

    public static void updateAccount(Customer customer, double amount){
        String accountPath = System.getProperty("user.dir") + "/src/System Data/" + customer.getId() + "/security.txt";
        File accountFile = new File(accountPath);
        if (!accountFile.exists()) {
            try {
                accountFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        List<String[]> amountList = fileEditor.fileRead("/src/System Data/"+ customer.getId() + "/security.txt");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(accountFile);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        for(String[] token :amountList) {
            token[1] = "" + amount;
            fileEditor.writeFile("/src/System Data/" + customer.getId() + "/security.txt", token);
        }
    }

    public static double getTotalAmountOfStocks(Customer customer){
        HashMap<String, String[]> userlist = getHoldingShareInformation(customer);
        HashMap<String, Double> stocklist = getStockInformation();

        if (userlist == null){
            return 0;
        }

        double totalAmount = 0;
        for (Map.Entry<String, String[]> entry : userlist.entrySet()){
            totalAmount += Double.valueOf(entry.getValue()[0])*stocklist.get(entry.getKey());
        }
        return totalAmount;
    }
}
