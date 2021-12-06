import java.io.File;
import java.util.HashMap;
import java.util.List;

public class AccountData {
    HashMap<String,String[]> accountinfo = new HashMap<String,String[]>();
    int number = 0;
    AccountData(){

        File accountFile = new File(System.getProperty("user.dir")+"/src/abc.txt");
        if(accountFile.length()==0) {
            accountinfo.put("Admin", new String[]{"00000","1"});
            fileEditor.writeFile("/src/abc.txt", new String[]{"Admin", "00000", "1"});
            number += 1;
        }else {
            getFromLocal();
        }
    }

    public HashMap getLoginInfo(){
        return accountinfo;
    }

    public void set(String id,String password){
        //add to hashmap and append to local file
        String account_num = String.valueOf(number);
        accountinfo.put(id,new String[]{password,account_num});
        fileEditor.writeFile("/src/abc.txt",new String[]{id,password,account_num});

    }


    //get data from local
    public void getFromLocal(){
        //read from file path
        List<String[]> accountList = fileEditor.fileRead("/src/abc.txt");
        for(String[] token :accountList){
            accountinfo.put(token[0],new String[]{token[1],token[2]});
            number++;
        }
    }


    public String getPassW(String userID){
        return accountinfo.get(userID)[0];
    }

}
