import java.io.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
    //first token in line as key, rest as value
    public static HashMap<String,String[]> toHash(String filename){
        BufferedReader fileReader = null;
        HashMap<String,String[]> list = new HashMap<String,String[]>();
        try {
            // Create an object of file reader
            // class with CSV file as a parameter.

            String line = "";
            fileReader = new BufferedReader(new FileReader(System.getProperty("user.dir") + filename));

            while ((line = fileReader.readLine()) != null) {
                //Get all tokens available in line
                String[] tokens = line.split(",");
                String[] value = Arrays.copyOfRange(tokens,1,tokens.length);
                list.put(tokens[0],value);
            }
            fileReader.close();

        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return list;
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
    public static void listWrite(String fullpath,List<String[]>list){
        int last = fullpath.lastIndexOf("/");
        if(last<fullpath.length()) {
            String filename = fullpath.substring(last + 1);
            String folder = fullpath.substring(0,last+1);
            listWrite(folder,filename,list);
        }
    }
    public static void writeMeta(String filename,String[] metadata ){
        if (metadata[0].equals("clear")){
            clearFile(filename);
            writeFile(filename,new String[]{"0"});
        }else {
            List<String[]> metaList = fileRead(filename);
            clearFile(filename);
            metaList.add(metadata);
            int lineCount = Integer.parseInt(metaList.get(0)[0]);
            metaList.get(0)[0] = ++lineCount + "";

            listWrite(filename, metaList);
        }

    }
    public static boolean exist(String path){
        return new File(System.getProperty("user.dir")+path).exists();
    }
}

