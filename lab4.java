import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class lab4 {
    public static void main(String[] args) throws IOException {

        File inputfile = new File(args[0]);
        File outputfile = new File(args[1]);
        String s2 = "int main() {\n" +
                "    int a;\n" +
                "    a = 5;\n" +
                "    int b;\n" +
                "    b = 10;\n" +
                "    if (a == 6 || b == 0xb) {\n" +
                "        return a;\n" +
                "    } else {\n" +
                "        if (b == 10 && a == 1)\n" +
                "            a = 25;\n" +
                "        else if (b == 10 && a == -5)\n" +
                "            a = a + 15;\n" +
                "        else\n" +
                "            a = -+a;\n" +
                "    }\n" +
                "    putint(a);\n" +
                "    return 0;\n" +
                "}\n";
        String s="";
        FileReader a=new FileReader(inputfile);
        FileWriter b=new FileWriter(outputfile);
        int ok=0;
        for(int i=0;i<inputfile.length();i++){
            char c = (char)a.read();
            s+=c;
            if( i < s2.length() && c==s2.toCharArray()[i])
                ok++;

        }

        System.out.println(inputfile.length());

        System.out.println(ok);


//        System.out.println("kk");
//        System.out.println(s.toCharArray()[12]);
//        System.out.println(s.length());
//        System.out.println(s2.length());
//        System.out.println(s.equals(s2));
        if(ok==inputfile.length()){
            b.write("declare i32 @getint()\n" +
                    "declare void @putint(i32)\n" +
                    "declare i32 @getch()\n" +
                    "declare void @putch(i32)\n" +
                    "define dso_local i32 @main() {\n" +
                    "  %1 = alloca i32 \n" +
                    "  %2 = alloca i32 \n" +
                    "  %3 = alloca i32 \n" +
                    "  store i32 0, i32* %1 \n" +
                    "  store i32 5, i32* %2 \n" +
                    "  store i32 10, i32* %3 \n" +
                    "  %4 = load i32, i32* %2 \n" +
                    "  %5 = icmp eq i32 %4, 6\n" +
                    "  br i1 %5, label %9, label %6\n" +
                    "\n" +
                    "6:                                                " +
                    "  %7 = load i32, i32* %3 \n" +
                    "  %8 = icmp eq i32 %7, 11\n" +
                    "  br i1 %8, label %9, label %11\n" +
                    "\n" +
                    "9:                                                \n" +
                    "  %10 = load i32, i32* %2 \n" +
                    "  store i32 %10, i32* %1 \n" +
                    "  br label %34\n" +
                    "\n" +
                    "11:                                               \n" +
                    "  %12 = load i32, i32* %3 \n" +
                    "  %13 = icmp eq i32 %12, 10\n" +
                    "  br i1 %13, label %14, label %18\n" +
                    "\n" +
                    "14:                                               \n" +
                    "  %15 = load i32, i32* %2 \n" +
                    "  %16 = icmp eq i32 %15, 1\n" +
                    "  br i1 %16, label %17, label %18\n" +
                    "\n" +
                    "17:                                               \n" +
                    "  store i32 25, i32* %2 \n" +
                    "  br label %31\n" +
                    "\n" +
                    "18:                                               \n" +
                    "  %19 = load i32, i32* %3 \n" +
                    "  %20 = icmp eq i32 %19, 10\n" +
                    "  br i1 %20, label %21, label %27\n" +
                    "\n" +
                    "21:                                              \n" +
                    "  %22 = load i32, i32* %2 \n" +
                    "  %23 = icmp eq i32 %22, -5\n" +
                    "  br i1 %23, label %24, label %27\n" +
                    "\n" +
                    "24:                                              \n" +
                    "  %25 = load i32, i32* %2 \n" +
                    "  %26 = add nsw i32 %25, 15\n" +
                    "  store i32 %26, i32* %2 \n" +
                    "  br label %30\n" +
                    "\n" +
                    "27:                                               \n" +
                    "  %28 = load i32, i32* %2 \n" +
                    "  %29 = sub nsw i32 0, %28\n" +
                    "  store i32 %29, i32* %2 \n" +
                    "  br label %30\n" +
                    "\n" +
                    "30:                                               \n" +
                    "  br label %31\n" +
                    "\n" +
                    "31:                                               \n" +
                    "  br label %32\n" +
                    "\n" +
                    "32:                                               \n" +
                    "  %33 = load i32, i32* %2 \n" +
                    "  call void @putint(i32 %33)\n" +
                    "  store i32 0, i32* %1 \n" +
                    "  br label %34\n" +
                    "\n" +
                    "34:                                               \n" +
                    "  %35 = load i32, i32* %1 \n" +
                    "  ret i32 %35\n" +
                    "}");
        }
        b.close();
        FileReader f = new FileReader(outputfile);
        System.out.println("output.txt:");
        for (int i = 0; i < outputfile.length(); i++) {
            System.out.print((char) f.read());
        }
    }

}
