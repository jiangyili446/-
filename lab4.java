import java.io.*;
import java.util.Locale;

public class lab4 {

    
    public static void main(String[] args) throws IOException {

        File inputfile = new File(args[0]);
        
        FileReader a=new FileReader(inputfile);
        for(int i=0;i<inputfile.length();i++){
            System.out.println(a.read());
        }

    }
}
    
