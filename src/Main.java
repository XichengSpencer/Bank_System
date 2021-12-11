public class Main {
    public static void main (String[] args){
        //a hard-coded account lib
        LoginData accountData= new LoginData();
        new Login(accountData);

        //test for read
        //List<String[]> list = fileEditor.fileRead("/src/abc.txt");
        //test for write
        // fileEditor.writeFile("/src/abc.txt", new String[]{"iPhone", "13pro"});
    }
}
