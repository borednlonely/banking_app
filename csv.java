package Application;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class csv {

    // reads each row, splits by comma, stores each row as string array + returns list of all rows
    public static List<String[]> read(String files){
        List<String[]> data = new LinkedList<String[]>();
        String row;
        try{
            // reads file line by line
            BufferedReader b = new BufferedReader(new FileReader(files));
            while((row = b.readLine()) !=null){
                // splits by comma
                String[] records = row.split(",");
                data.add(records);
            }
        } catch(FileNotFoundException e){
            System.out.println("COULDN'T READ THE FILE");
            e.printStackTrace();
        } catch(IOException e){
            System.out.println("COULDN'T READ THE FILE");
            throw new RuntimeException(e);
        }
        return data;
    }
}
