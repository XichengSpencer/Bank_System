import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TransactionData {
    private static TransactionData transactionData = new TransactionData();

    public static TransactionData getInstance(){
        return transactionData;
    }

    public String doTransaction(Transaction transaction, boolean hasFee) throws IOException {
        String msg = "";
        // change amount from customers
        String fromCustomerId = transaction.getFrom().getCustomer().getId();
        String toCustomerId = transaction.getTo().getCustomer().getId();
        String fromPath = System.getProperty("user.dir") + "/src/System Data/" + fromCustomerId;
        String toPath = System.getProperty("user.dir") + "/src/System Data/" + toCustomerId;
        File fromFolder = new File(fromPath);
        File toFolder = new File(toPath);
        if (!fromFolder.exists()) {
            fromFolder.mkdirs();
            msg = "No account";
            return msg;
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
                            if (hasFee) {
                                amount -= transaction.getAmount() * 1.05;
                            } else {
                                amount -= transaction.getAmount();
                            }

                            token[1] = ""+amount;
                        }
                        fileEditor.writeFile("/src/System Data/"+fromCustomerId+"/saving.txt", token);
                    }
                }
            } else if (transaction.getFrom().getAccountType().equals("checking")) {
                File checkingAccount = new File(fromPath + "/checking.txt");
                if (checkingAccount.length() != 0) {
                    List<String[]> accountList = fileEditor.fileRead("/src/System Data/"+fromCustomerId+"/checking.txt");
                    // empty file for update
                    FileWriter fileWriter = new FileWriter(checkingAccount);
                    fileWriter.write("");
                    fileWriter.flush();
                    fileWriter.close();

                    for(String[] token :accountList){
                        if (token[2].equals(transaction.getFromCurrency())) { // the same currency with fromAccount
                            // choose the same currency
                            double amount = Double.valueOf(token[1]);
                            if (hasFee) {
                                amount -= transaction.getAmount() * 1.05;
                            } else {
                                amount -= transaction.getAmount();
                            }
                            token[1] = ""+amount;
                        }
                        fileEditor.writeFile("/src/System Data/"+fromCustomerId+"/checking.txt", token);
                    }
                }
            }
        }

        if (!toFolder.exists()) {
            toFolder.mkdirs();
            msg = "No account";
            return msg;
        } else {
            if (transaction.getTo().getAccountType().equals("saving")) {
                File savingAccount = new File(toPath + "/saving.txt");
                if (savingAccount.length() != 0) {
                    List<String[]> accountList = fileEditor.fileRead("/src/System Data/"+toCustomerId+"/saving.txt");
                    // empty file for update
                    FileWriter fileWriter = new FileWriter(savingAccount);
                    fileWriter.write("");
                    fileWriter.flush();
                    fileWriter.close();

                    for(String[] token :accountList){
                        if (token[2].equals(transaction.getFromCurrency())) {
                            // choose the same currency
                            double amount = Double.valueOf(token[1]);
                            amount += transaction.getAmount();
                            token[1] = ""+amount;
                        }
                        fileEditor.writeFile("/src/System Data/"+toCustomerId+"/saving.txt", token);
                    }
                }
            } else if (transaction.getTo().getAccountType().equals("checking")) {
                File checkingAccount = new File(toPath + "/checking.txt");
                if (checkingAccount.length() != 0) {
                    List<String[]> accountList = fileEditor.fileRead("/src/System Data/"+toCustomerId+"/checking.txt");
                    // empty file for update
                    FileWriter fileWriter = new FileWriter(checkingAccount);
                    fileWriter.write("");
                    fileWriter.flush();
                    fileWriter.close();

                    for(String[] token :accountList){
                        if (token[2].equals(transaction.getFromCurrency())) { // the same currency with fromAccount
                            // choose the same currency
                            double amount = Double.valueOf(token[1]);
                            amount += transaction.getAmount();
                            token[1] = ""+amount;
                        }
                        fileEditor.writeFile("/src/System Data/"+toCustomerId+"/checking.txt", token);
                    }
                }
            } else if (transaction.getTo().getAccountType().equals("security")) {
                File checkingAccount = new File(toPath + "/security.txt");
                if (checkingAccount.length() != 0) {
                    List<String[]> accountList = fileEditor.fileRead("/src/System Data/"+toCustomerId+"/security.txt");
                    // empty file for update
                    FileWriter fileWriter = new FileWriter(checkingAccount);
                    fileWriter.write("");
                    fileWriter.flush();
                    fileWriter.close();

                    for(String[] token :accountList){
                        if (token[2].equals(transaction.getFromCurrency())) { // the same currency with fromAccount
                            // choose the same currency
                            double amount = Double.valueOf(token[1]);
                            amount += transaction.getAmount();
                            token[1] = ""+amount;
                        }
                        fileEditor.writeFile("/src/System Data/"+toCustomerId+"/security.txt", token);
                    }
                }
            }
        }

        // log in file
//        String transactionPath = System.getProperty("user.dir") + "/src/transaction.txt";
//        File transactionFile = new File(transactionPath);
//        if (!transactionFile.exists()) {
//            try {
//                transactionFile.createNewFile();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//        }
//        fileEditor.writeFile("/src/transaction.txt", new String[]{transaction.getTransferTime().toString(), "Transfer", transaction.getFrom().getCustomer().getName(), transaction.getFrom().getAccountType(), transaction.getTo().getCustomer().getName(), transaction.getTo().getAccountType(), ""+transaction.getAmount(), transaction.getFromCurrency()});
//        return msg;
        logTransaction("transfer", transaction);
        return msg;
    }

    public List<String[]> getAllTransactions() {
        List<String[]> allTransactions = new ArrayList<>();
        String transactionPath = System.getProperty("user.dir") + "/src/transaction.txt";
        File transactionFile = new File(transactionPath);
        if (transactionFile.length() != 0) {
            allTransactions = fileEditor.fileRead("/src/transaction.txt");
        }
        return allTransactions;
    }

    public List<String[]> getTransactions(String userName) {
        List<String[]> allTransactions = getAllTransactions();
        List<String[]> customerTransaction = new ArrayList<>();
        for (String[] token : allTransactions) {
            if (token[2].equals(userName)||token[4].equals(userName)) {
                customerTransaction.add(token);
            }
        }
        return customerTransaction;
    }

    public void logTransaction(String operation, Transaction transaction) {
        String transactionPath = System.getProperty("user.dir") + "/src/transaction.txt";
        File transactionFile = new File(transactionPath);
        if (!transactionFile.exists()) {
            try {
                transactionFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        fileEditor.writeFile("/src/transaction.txt", new String[]{transaction.getTransferTime().toString(), operation, transaction.getFrom().getCustomer().getName(), transaction.getFrom().getAccountType(), transaction.getTo().getCustomer().getName(), transaction.getTo().getAccountType(), ""+transaction.getAmount(), transaction.getFromCurrency()});
    }

    public void logTransaction(String operation, String fromCustomer, String fromAccount, String toCustomer, String toAccount, double amount, String currency) {
        String transactionPath = System.getProperty("user.dir") + "/src/transaction.txt";
        File transactionFile = new File(transactionPath);
        if (!transactionFile.exists()) {
            try {
                transactionFile.createNewFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        fileEditor.writeFile("/src/transaction.txt", new String[]{new Date().toString(), operation, fromCustomer, fromAccount, toCustomer, toAccount, "" + amount, currency});
    }
}
