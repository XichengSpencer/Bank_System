import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class LoginData {
    HashMap<String,String[]> loginInfo = new HashMap<String,String[]>();

    LoginData(){
        File accountFile = new File(System.getProperty("user.dir")+"/src/login.txt");
        if(accountFile.length()==0) {
            String number = UUID.randomUUID().toString();
            loginInfo.put("Admin", new String[]{"00000",number});
            fileEditor.writeFile("/src/login.txt", new String[]{"Admin", "00000", number});
        }else {
            getFromLocal();
        }
    }

    public HashMap getLoginInfo(){
        return loginInfo;
    }

    public void set(Customer customer){
        //add to hashmap and append to local metadata file
        String number = UUID.randomUUID().toString();
        loginInfo.put(customer.getName(),new String[]{customer.getPassword(),customer.getId()});
        fileEditor.writeFile("/src/login.txt",new String[]{customer.getName(),customer.getPassword(),customer.getId()});
        File folder = new File(System.getProperty("user.dir")+"/src/System Data/"+customer.getId());
        fileEditor.writeFile("/src/metadata.txt",new String[]{new SimpleDateFormat("MM/dd/yyyy").format(new Date()),"newUser", customer.getId()});
        if (!folder.exists()){
            folder.mkdirs();
        }

    }


    //get data from local
    public void getFromLocal(){
        //read from file path
        List<String[]> accountList = fileEditor.fileRead("/src/login.txt");
        for(String[] token :accountList){
            loginInfo.put(token[0],new String[]{token[1],token[2]});
        }
    }


    public String getPassW(String userID){
        return loginInfo.get(userID)[0];
    }

}
