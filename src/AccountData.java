import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class AccountData {
    HashMap<String,String[]> accountinfo = new HashMap<String,String[]>();

    AccountData(){

        File accountFile = new File(System.getProperty("user.dir")+"/src/abc.txt");
        if(accountFile.length()==0) {
            String number = UUID.randomUUID().toString();
            accountinfo.put("Admin", new String[]{"00000",number});
            fileEditor.writeFile("/src/abc.txt", new String[]{"Admin", "00000", number});
        }else {
            getFromLocal();
        }
    }

    public HashMap getLoginInfo(){
        return accountinfo;
    }

    public void set(String id,String password){
        //add to hashmap and append to local metadata file
        String number = UUID.randomUUID().toString();
        accountinfo.put(id,new String[]{password,number});
        fileEditor.writeFile("/src/abc.txt",new String[]{id,password,number});
        File folder = new File(System.getProperty("user.dir")+"/src/Data"+number);
        if (!folder.exists()){
            folder.mkdirs();
        }

    }


    //get data from local
    public void getFromLocal(){
        //read from file path
        List<String[]> accountList = fileEditor.fileRead("/src/abc.txt");
        for(String[] token :accountList){
            accountinfo.put(token[0],new String[]{token[1],token[2]});
        }
    }


    public String getPassW(String userID){
        return accountinfo.get(userID)[0];
    }

}
