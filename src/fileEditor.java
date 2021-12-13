import java.io.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;


public class fileEditor {

    public static List<String[]> fileRead(String fileName) {
        BufferedReader fileReader = null;
        List<String[]> list = new ArrayList<String[]>();
        try {
            // Create an object of file reader
            // class with CSV file as a parameter.

            String line = "";
            fileReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + fileName));

            while ((line = fileReader.readLine()) != null) {
                //Get all tokens available in line
                String[] tokens = line.split(",");
                if (tokens.length > 0) {
                    //Create a new student object and fill his  data
                    list.add(tokens);
                }
            }
            fileReader.close();

        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return list;
    }

    //Before use writeFile
    //read and find if file exist
    public static void writeFile(String filename, String[] tokens) {
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File(System.getProperty("user.dir") + filename),true))) {

            String steam = String.join(",", tokens);
            System.out.println(steam);
            writer.write(steam);
            writer.write("\n");

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }

    }

    public static boolean listWrite(String folder,String filename, List<String[]> list) {
        String path = System.getProperty("user.dir") + folder;
        File f= new File(path);
        if(!f.exists()){f.mkdirs();}
        //open file
        f = new File(path+filename);

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(f,true))) {

            for (String [] item : list){
                writer.write(String.join(" ", item));
                writer.write("\n");           }
            return true;


        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void clearFile(String filename) {
        Boolean removed;
        try {
            File f = new File(System.getProperty("user.dir") + filename);
            if (f.exists() && f.isFile()) {
                f.delete();
            }
            f.createNewFile();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static boolean isValid(String dateStr) {
        DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException  e) {
            return false;
        }
        return true;
    }
}

