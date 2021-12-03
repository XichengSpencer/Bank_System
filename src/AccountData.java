import java.util.HashMap;

public class AccountData {
    HashMap<String,String> accountinfo = new HashMap<String,String>();

    AccountData(){

        accountinfo.put("Admin","00000");
        accountinfo.put("Brometheus","PASSWORD");
        accountinfo.put("BroCode","abc123");
    }

    public HashMap getLoginInfo(){
        return accountinfo;
    }
}
