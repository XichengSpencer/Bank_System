import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


public class fileEditor {

    public static List<String[]> fileRead(String fileName){
        BufferedReader fileReader = null;
        List<String[]> list = new ArrayList<String[]>();
        try {
            // Create an object of file reader
            // class with CSV file as a parameter.

            String line = "";
            fileReader = new BufferedReader(new FileReader(System.getProperty("user.dir")+fileName));

            //Read the CSV file header to skip it

            while ((line = fileReader.readLine()) != null) {
                //Get all tokens available in line
                String[] tokens = line.split(",");
                if (tokens.length > 0) {
                    //Create a new student object and fill his  data
                    list.add(tokens);
                }
            }

            //Print the new student list
        }
        catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return list;
    }

    public static void writeFile(){

    }
}
