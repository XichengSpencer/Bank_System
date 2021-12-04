import java.util.List;

public class Main {
    public static void main (String[] args){
        AccountData accountData= new AccountData();
        new Login(accountData.getLoginInfo());
        //test for read
        //List<String[]> list = fileEditor.fileRead("/src/abc.txt");
        //test for write
        // fileEditor.writeFile("/src/abc.txt", new String[]{"iPhone", "13pro"});
    }
}
