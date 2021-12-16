import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class TransactionData {
    private static TransactionData transactionData = new TransactionData();

    public static TransactionData getInstance(){
        return transactionData;
    }

    public void doTransaction(Transaction transaction) throws IOException {
        // change amount from customers
        String fromCustomerId = transaction.getFrom().getCustomer().getId();
        String toCustomerId = transaction.getTo().getCustomer().getId();
        String fromPath = System.getProperty("user.dir") + "/src/System Data/" + fromCustomerId;
        String toPath = System.getProperty("user.dir") + "/src/System Data/" + toCustomerId;
        File fromFolder = new File(fromPath);
        File toFolder = new File(toPath);
        if (!fromFolder.exists()) {
            fromFolder.mkdirs();
            return;
        } else {

            if (transaction.getFrom().getAccountType().equals("saving")) {
                File savingAccount = new File(fromPath + "/saving.txt");
                if (savingAccount.length() != 0) {
                    List<String[]> accountList = fileEditor.fileRead("/src/System Data/"+fromCustomerId+"/saving.txt");
                    // empty file for update
                    FileWriter fileWriter = new FileWriter(savingAccount);
                    fileWriter.write("");
                    fileWriter.flush();
                    fileWriter.close();

                    for(String[] token :accountList){
                        if (token[2].equals(transaction.getFromCurrency())) {
                            // choose the same currency
                            double amount = Double.valueOf(token[1]);
                            amount -= transaction.getAmount();
                            token[1] = ""+amount;
                        }
                        fileEditor.writeFile("/src/System Data/"+fromCustomerId+"/saving.txt", token);
                    }
                }
            } else if (transaction.getFrom().getAccountType().equals("checking")) {
            } else {

            }

        }

        // log in file
        String transactionPath = System.getProperty("user.dir") + "/src/transaction.txt";
        File transactionFile = new File(transactionPath);
        if (!transactionFile.exists()) {
            try {
                transactionFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        fileEditor.writeFile("/src/transaction.txt", new String[]{transaction.getTransferTime().toString(), transaction.getFrom().getCustomer().getName(), transaction.getFrom().getAccountType(), transaction.getTo().getCustomer().getName(), transaction.getTo().getAccountType(), ""+transaction.getAmount(), transaction.getFromCurrency()});
    }
}
