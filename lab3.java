import java.io.*;
import java.util.Locale;

public class lab3 {

    
    public static void main(String[] args) throws IOException {

        File inputfile = new File(args[0]);
        
        FileReader a=new FileReader(inputfile);
        for(int i=0;i<inputfile.length();i++){
            System.out.print((char)a.read());
        }

    }
}
    
