import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main (String[] args) {
        //a hard-coded account lib
        LoginData accountData= new LoginData();
        new Login(accountData);

        //test for read
        //List<String[]> list = fileEditor.fileRead("/src/abc.txt");
        //test for write
         //fileEditor.writeFile("/src/a.txt", new String[]{"iPhone", "13pro"});
        //test for list write
        //fileEditor.listWrite("/src/System Data/file/","file.txt", Arrays.asList(new String[]{"a"},new String[]{"b"})) ;
        //test for meta
        //fileEditor.writeMeta("/src/System Data/metadata.txt",new String[]{"0"});
        /*try {
            Date date= new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse("Wed Dec 16 22:32:14 EST 2021");
            String s = new SimpleDateFormat("MM/dd/yyyy").format(date);
            date = new SimpleDateFormat("MM/dd/yyyy").parse(s);
            Date date2 = new Date("12/16/2021");
            System.out.println(date.equals(date2));
        }catch (ParseException exp) {
            exp.printStackTrace();
        }*/
        //new ReportPresent();
        //test exist function
        //System.out.println(fileEditor.exist("/src/ab.txt"));
    }
}
