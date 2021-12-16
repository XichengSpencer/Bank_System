import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
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
    }
}
