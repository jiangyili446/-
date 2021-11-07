import java.io.*;
import java.util.Locale;

public class lab2 {

    public static FileReader yufafp = null;
    public static BufferedReader f1 = null;

    public static String buffer = null;
    public static int readNext = 1;

    public static String[] lexerToken = new String[100];
    public static int tokenIndex = 0;
    public static int tokenSize = 0;

    public static String finalExp ="";
    public static int nameIndex = 1;

    public static int suanfuMatrix[][] ={
            {1,-1,-1,1,-1,1},
            {1,1,-1,1,-1,1},
            {-1,-1,-1,0,-1,1},
            {1,1,9,1,9,1},
            {1,1,9,1,9,1}
    };

    public static void main(String[] args) throws IOException {

        File inputfile = new File(args[0]);
        File no_notes = new File("no_notes.txt");
        File lexer = new File("lexer.txt");
        File outputfile = new File(args[1]);


        clean_notes(inputfile, no_notes);


        if (lexer(no_notes, lexer) == 0) {

        } else {

            System.exit(9);
        }


        yufafp = new FileReader(lexer);
        f1 = new BufferedReader(yufafp);
        String tokens;
        while ((tokens = f1.readLine()) != null) {
            lexerToken[tokenSize] = tokens;
            tokenSize++;
        }
        if (comunit() != 0) {
            System.exit(9);
        }
        yufafp.close();
        f1.close();

        FileReader fp3 = new FileReader(lexer);

        FileWriter fp4 = new FileWriter(outputfile);


        translate(fp3, fp4);

        fp3.close();
        fp4.close();

        FileReader fp5 = new FileReader(outputfile);
        FileReader fp6 = new FileReader(lexer);
        FileReader fp7 = new FileReader(no_notes);


        char k;
        System.out.println("no_notes.txt:");
        for (int i = 0; i < no_notes.length(); i++) {
            System.out.print((char) fp7.read());
            //int l = fp7.read();
            //System.out.print(i+"  "+l+"  "+(char)l+"\n");
        }
        //System.out.println(no_notes.length());
        System.out.println();
        System.out.println("lexer.txt:");
        for (int i = 0; i < lexer.length(); i++) {
            System.out.print((char) fp6.read());
        }
        System.out.println();
        System.out.println("output.txt:");
        for (int i = 0; i < outputfile.length(); i++) {
            System.out.print((char) fp5.read());
        }

    }

    private static void translate(FileReader fp1, FileWriter fp2) throws IOException {
        String buf;
        BufferedReader ff = new BufferedReader(fp1);
        fp2.write("define dso_local ");

        while ((buf = ff.readLine()) != null) {

            if (buf.equals("Ident(int)")) {
                fp2.write("i32");
            } else if (buf.equals("Ident(main)")) {
                fp2.write("@main");
            } else if (buf.equals("LPar")) {
                fp2.write("(");
            } else if (buf.equals("RPar")) {
                fp2.write(")");
            } else if (buf.equals("LBrace")) {
                fp2.write("{\n");
            } else if (buf.equals("RBrace")) {
                fp2.write("}\n");
            } else if (buf.equals("Return")) {

                String[] exp = new String[100];
                int expSize = 0;
                String s;
                while (true) {
                    s = ff.readLine();
                    if (s.equals("Semicolon")) {
                        break;
                    }
                    exp[expSize] = s;
                    expSize++;
                }
                translateExp(fp2, exp, expSize);
                fp2.write(finalExp);
                int nameindex_ = nameIndex-1;
                fp2.write("ret i32 %x"+nameindex_+"\n");
            } else if (buf.toCharArray()[0] == 'N') {
                int num = 0;
                for (int i = 7; buf.toCharArray()[i] != ')'; i++) {
                    num = num * 10 + buf.toCharArray()[i] - '0';
                }
                fp2.write("i32 " + num + "\n");
            } else {

            }
        }
    }

    private static void translateExp(FileWriter fp2, String[] aa, int expSize) {

        int aaindex;
        String[] a2 = new String[100];
        int a2index;

        for (a2index = 0, aaindex = 0; aaindex < expSize; aaindex++) {
            if (aa[aaindex].toCharArray()[0] == 'P' && a2index > 0) {
                if (a2[a2index - 1].equals("Plus")) {

                } else if (a2[a2index - 1].equals("Sub")) {
                    a2[a2index - 1] = "Sub";
                } else {
                    a2[a2index] = "Plus";
                    a2index++;
                }
            } else if (aa[aaindex].toCharArray()[0] == 'S' && a2index > 0) {
                if (a2[a2index - 1].equals("Plus")) {
                    a2[a2index - 1] = "Sub";
                } else if (a2[a2index - 1].equals("Sub")) {
                    a2[a2index - 1] = "Plus";
                } else {
                    a2[a2index] = "Sub";
                    a2index++;
                }
            } else {
                a2[a2index] = aa[aaindex];
                a2index++;
            }
        }

        String[] a3 = new String[100];
        int a3index = 0;
        for (int i = 0; i < a2index; i++) {
            if (i == 0) {
                if (a2[i].equals("Plus") || a2[i].equals("Sub")) {
                    a3[a3index] = "Number(0)";
                    a3index++;
                    a3[a3index] = a2[i];
                    a3index++;
                } else {
                    a3[a3index] = a2[i];
                    a3index++;
                }
            } else {
                if (a2[i].equals("LPar") && (a2[i + 1].equals("Plus") || a2[i + 1].equals("Sub"))) {
                    a3[a3index] = "LPar";
                    a3index++;
                    a3[a3index] = "Number(0)";
                    a3index++;
                } else {
                    a3[a3index] = a2[i];
                    a3index++;
                }
            }
        }
        System.out.println("aa:");
        for (int i = 0; i < expSize; i++) {
            System.out.println(aa[i]);
        }
        System.out.println("a2:");
        for (int i = 0; i < a2index; i++) {
            System.out.println(a2[i]);
        }
        System.out.println("a3:");
        for (int i = 0; i < a3index; i++) {
            System.out.println(a3[i]);
        }


        String[] list = new String[100];
        int end = 0;
        int a3begin = 0;
        list[end] = a3[a3begin];
        end++;
        a3begin++;
        while (end > 0) {
            int k;
            int f;
            if(list[end-1]==null){
                break;
            }
            if (list[end - 1].toCharArray()[0] == '%') {
                if(end==1){
                    k=-1;
                    f=0;
                }
                else{
                    k = cmp(list[end - 2], a3[a3begin]);
                    f = 0;
                }

            } else {
                k = cmp(list[end - 1], a3[a3begin]);
                f = 1;
            }

            if (k == 1) {
                if (f == 0) {
                    if (list[end - 1].toCharArray()[0] == '%' && list[end - 2].equals("Plus") && list[end - 3].toCharArray()[0] == '%') {
                        finalExp += "%x" + nameIndex + " = add i32 " + list[end - 3] + ", " + list[end - 1] + "\n";

                        end = end - 2;
                        list[end - 1] = "%x" + nameIndex;
                        nameIndex++;
                        System.out.println("finalExp: \n" + finalExp);
                    } else if (list[end - 1].toCharArray()[0] == '%' && list[end - 2].equals("Sub") && list[end - 3].toCharArray()[0] == '%') {
                        finalExp += "%x" + nameIndex + " = sub i32 " + list[end - 3] + ", " + list[end - 1] + "\n";

                        end = end - 2;
                        list[end - 1] = "%x" + nameIndex;
                        nameIndex++;
                        System.out.println("finalExp: \n" + finalExp);
                    } else if (list[end - 1].toCharArray()[0] == '%' && list[end - 2].equals("Mult") && list[end - 3].toCharArray()[0] == '%') {
                        finalExp += "%x" + nameIndex + " = mul i32 " + list[end - 3] + ", " + list[end - 1] + "\n";

                        end = end - 2;
                        list[end - 1] = "%x" + nameIndex;
                        nameIndex++;
                        System.out.println("finalExp: \n" + finalExp);
                    } else if (list[end - 1].toCharArray()[0] == '%' && list[end - 2].equals("Div") && list[end - 3].toCharArray()[0] == '%') {
                        finalExp += "%x" + nameIndex + " = sdiv i32 " + list[end - 3] + ", " + list[end - 1] + "\n";

                        end = end - 2;
                        list[end - 1] = "%x" + nameIndex;
                        nameIndex++;
                        System.out.println("finalExp: \n" + finalExp);
                    } else if (list[end - 1].toCharArray()[0] == '%' && list[end - 2].equals("Mol") && list[end - 3].toCharArray()[0] == '%') {
                        finalExp += "%x" + nameIndex + " = srem i32 " + list[end - 3] + ", " + list[end - 1] + "\n";

                        end = end - 2;
                        list[end - 1] = "%x" + nameIndex;
                        nameIndex++;
                        System.out.println("finalExp: \n" + finalExp);
                    } else {
                        System.out.println("sufu error");
                        System.exit(9);
                    }
                } else if (f == 1) {
                    if (list[end - 1].toCharArray()[0] == 'N') {
                        String num = list[end - 1].substring(7, list[end - 1].length() - 1);
                        finalExp += "%x" + nameIndex + " = sub i32 " + num + ", 0\n";
                        list[end - 1] = "%x" + nameIndex;
                        nameIndex++;
                        System.out.println("finalExp: \n" + finalExp);
                    } else if (list[end - 3].equals("LPar") && list[end - 2].toCharArray()[0] == '%' && list[end - 1].equals("RPar")) {
                        list[end - 3] = list[end - 2];
                        end = end - 2;
                    } else {
                        System.out.println("asfsfgdfvdf");
                        System.out.println(9);
                    }
                }
            } else if (k == -1 || k == 0) {
                list[end] = a3[a3begin];
                end++;
                a3begin++;
            } else if (k == 6) {
                return;
            } else {
                System.out.println(9);
            }
        }

        return;
    }

    private static int cmp(String s1, String s2) {
        System.out.println("s1:" + s1 + "\n" + "s2:" + s2 + "\n");
        if (s1==null) {
            if (s2==null) {
                return 6;
            } else {
                return -1;
            }
        } else {
            if(s2==null){
                return 1;
            }
            return suanfuMatrix[getNum(s1)][getNum(s2)];
        }
    }

    private static int getNum(String s) {
        if (s.equals("Plus") || s.equals("Sub"))
            return 0;
        else if (s.equals("Mult") || s.equals("Div") || s.equals("Mol"))
            return 1;
        else if (s.equals("LPar"))
            return 2;
        else if (s.equals("RPar"))
            return 3;
        else if (s.toCharArray()[0] == 'N')
            return 4;
        else
            return 5;
    }

    private static int comunit() throws IOException {
        if (funcdef() == 0) {
            return 0;
        } else {
            System.out.println("comunit error\n");
            return 9;
        }
    }

    private static int funcdef() throws IOException {

        if (functype() == 0) {
            if (ident() == 0) {
                buffer = lexerToken[tokenIndex];
                if (buffer != null && buffer.equals("LPar")) {
                    tokenIndex++;
                    buffer = lexerToken[tokenIndex];
                    if (buffer != null && buffer.equals("RPar")) {
                        tokenIndex++;
                        if (block() == 0) {
                            return 0;
                        } else {
                            System.out.println("funcdef error1");
                            return 9;
                        }
                    } else {
                        System.out.println("funcdef error2");
                        return 9;
                    }
                } else {
                    System.out.print("funcdef error3\n");
                    return 9;
                }
            } else {
                System.out.print("funcdef error\n");
                return 9;
            }
        }
        return 0;
    }

    private static int block() throws IOException {

        buffer = lexerToken[tokenIndex];
        if (buffer != null && buffer.equals("LBrace")) {
            tokenIndex++;
            if (stmt() == 0) {
                buffer = lexerToken[tokenIndex];
                if (buffer != null && buffer.equals("RBrace")) {
                    tokenIndex++;
                    return 0;
                } else {
                    System.out.print("block error\n");
                    return 9;
                }
            } else {
                System.out.print("block error\n");
                return 9;
            }
        } else {
            System.out.print("block error\n");
            return 9;
        }
    }

    private static int stmt() throws IOException {

        buffer = lexerToken[tokenIndex];
        if (buffer != null && buffer.equals("Return")) {
            tokenIndex++;
            if (exp() == 0) {
                buffer = lexerToken[tokenIndex];
                if (buffer.equals("Semicolon")) {
                    tokenIndex++;
                    return 0;
                } else {
                    System.out.print(buffer + "stmt(semicolon) error\n");
                    return 9;
                }
            } else {
                System.out.print("stmt error\n");
                return 9;
            }
        } else {
            System.out.print("stmt error\n");
            return 9;
        }
    }

    private static int exp() throws IOException {
        if (addexp() == 0) {
            return 0;
        } else {
            System.out.print("exp error\n");
            return 9;
        }
    }

    private static int addexp() throws IOException {
        if (mulexp() == 0) {
            buffer = lexerToken[tokenIndex];
            if (buffer != null && (buffer.equals("Plus") || buffer.equals("Sub"))) {
                while (true) {
                    buffer = lexerToken[tokenIndex];
                    if (buffer != null && (buffer.equals("Plus") || buffer.equals("Sub"))) {
                        tokenIndex++;
                        if (mulexp() == 0) {
                            buffer = lexerToken[tokenIndex];
                            if ((buffer.equals("Plus") || buffer.equals("Sub"))) {

                            } else {
                                break;
                            }
                        } else {
                            System.out.print("addexp error\n");
                            return 9;
                        }
                    } else {
                        System.out.print("addexp error\n");
                        return 9;
                    }

                }
                return 0;
            }
            return 0;
        } else {
            System.out.print("addexp error\n");
            return 9;
        }
    }

    private static int mulexp() throws IOException {
        if (unaryExp() == 0) {
            buffer = lexerToken[tokenIndex];
            if (buffer != null && (buffer.equals("Mult") || buffer.equals("Div") || buffer.equals("Mol"))) {

                while (true) {
                    buffer = lexerToken[tokenIndex];
                    if (buffer != null && (buffer.equals("Mult") || buffer.equals("Div") || buffer.equals("Mol"))) {
                        tokenIndex++;
                        if (unaryExp() == 0) {
                            buffer = lexerToken[tokenIndex];
                            if (buffer != null && (buffer.equals("Mult") || buffer.equals("Div") || buffer.equals("Mol"))) {

                            } else {
                                break;
                            }
                        } else {
                            System.out.print("mulexp error\n");
                            return 9;
                        }
                    } else {
                        System.out.print("mulexp error\n");
                        return 9;
                    }

                }
                return 0;
            }
            return 0;
        } else {
            System.out.print("mulexp error\n");
            return 9;
        }
    }

    private static int unaryExp() throws IOException {
        buffer = lexerToken[tokenIndex];
        if (buffer != null && buffer.equals("LPar")) {
            tokenIndex++;
            if (exp() == 0) {
                buffer = lexerToken[tokenIndex];
                if (buffer != null && buffer.equals("RPar")) {
                    tokenIndex++;
                    return 0;
                } else {
                    System.out.print("primaryExp error\n");
                    return 9;
                }
            } else {
                System.out.print("primaryExp error\n");
                return 9;
            }
        } else if (buffer != null && buffer.toCharArray()[0] == 'N') {
            tokenIndex++;
            return 0;
        } else if (buffer != null && (buffer.equals("Plus") || buffer.equals("Sub"))) {
            tokenIndex++;
            if (unaryExp() == 0) {
                return 0;
            } else {
                System.out.print("unaryExp error\n");
                return 9;
            }
        } else {
            System.out.print("unaryExp error\n");
            return 9;
        }
    }

    private static int ident() throws IOException {

        buffer = lexerToken[tokenIndex];
        if (buffer != null && buffer.equals("Ident(main)")) {
            tokenIndex++;
            return 0;
        } else {
            System.out.print("ident error\n");
            return 9;
        }
    }

    private static int functype() throws IOException {

        buffer = lexerToken[tokenIndex];
        if (buffer != null && buffer.equals("Ident(int)")) {
            tokenIndex++;
            return 0;
        } else {
            System.out.print(" error\n");
            return 9;
        }
    }

    private static int lexer(File no_notes, File lexer) throws IOException {
        FileReader fp1 = new FileReader(no_notes);
        FileWriter fp2 = new FileWriter(lexer);
        char readChar = 0;
        char[] token = new char[20];


        int tokenIndex = 0;
        token[tokenIndex] = '\0';
        System.out.println("token" + String.valueOf(token));
        int i = -1;
        int ifRead = 1;
        while (true) {
            if (ifRead == 1) {
                readChar = (char) fp1.read();
                i++;
            }
            ifRead = 1;

            if (classification(readChar) == 1 || classification(readChar) == 3) {

                token[tokenIndex] = readChar;
                tokenIndex++;
                token[tokenIndex] = '\0';

                for (; i < no_notes.length(); ) {
                    readChar = (char) fp1.read();
                    i++;
                    if (classification(readChar) == 1 || classification(readChar) == 2 || classification(readChar) == 3) {
                        token[tokenIndex] = readChar;
                        tokenIndex++;
                        token[tokenIndex] = '\0';
                    } else if (readChar == ' ' || readChar == 10) {
                        keys(token, fp2);
                        tokenIndex = 0;
                        for (int t = 0; t < 20; t++) {
                            token[t] = '\0';
                        }
                        break;
                    } else if (classification(readChar) == 4) {
                        keys(token, fp2);
                        tokenIndex = 0;
                        for (int t = 0; t < 20; t++) {
                            token[t] = '\0';
                        }
                        ifRead = 0;
                        break;
                    } else {
                        keys(token, fp2);
                        tokenIndex = 0;
                        for (int t = 0; t < 20; t++) {
                            token[t] = '\0';
                        }
                        ifRead = 0;
                        break;
                    }
                }
                if (i == no_notes.length()) {
                    keys(token, fp2);
                    fp1.close();
                    fp2.close();
                    return 0;
                }


            } else if (readChar == '0') {
                token[tokenIndex] = readChar;
                tokenIndex++;
                token[tokenIndex] = '\0';

                readChar = (char) fp1.read();
                i++;
                if (readChar == 'x' || readChar == 'X') {

                    tokenIndex = 0;
                    for (int t = 0; t < 20; t++) {
                        token[t] = '\0';
                    }
                    int hex = 0;
                    int times = 0;
                    for (; i < no_notes.length(); ) {
                        readChar = (char) fp1.read();
                        i++;
                        if ((readChar >= '0' && readChar <= '9')) {
                            hex = hex * 16 + readChar - '0';
                        } else if ((readChar >= 'a' && readChar <= 'f')) {
                            hex = hex * 16 + 10 + readChar - 'a';
                        } else if ((readChar >= 'A' && readChar <= 'F')) {
                            hex = hex * 16 + 10 + readChar - 'A';
                        } else if (readChar == ' ' || readChar == 10) {
                            if (times == 0) {
                                fp1.close();
                                fp2.close();
                                return 9;
                            } else {
                                String s = "Number(" + hex + ")\n";
                                fp2.write(s);
                                tokenIndex = 0;
                                for (int t = 0; t < 20; t++) {
                                    token[t] = '\0';
                                }
                                break;
                            }
                        } else {
                            if (times == 0) {
                                fp1.close();
                                fp2.close();
                                return 9;
                            } else {
                                fp2.write("Number(" + hex + ")\n");
                                tokenIndex = 0;
                                for (int t = 0; t < 20; t++) {
                                    token[t] = '\0';
                                }
                                ifRead = 0;
                                break;
                            }

                        }
                        times++;
                    }
                    if (i == no_notes.length()) {
                        if (times == 0) {
                            fp1.close();
                            fp2.close();
                            return 9;
                        } else {
                            fp2.write("Number(" + hex + ")\n");
                            fp1.close();
                            fp2.close();
                            return 0;
                        }

                    }

                } else if (readChar >= '0' && readChar <= '7') {

                    tokenIndex = 0;
                    for (int t = 0; t < 20; t++) {
                        token[t] = '\0';
                    }
                    int octal = 0;
                    octal = octal * 8 + readChar - '0';
                    int times = 0;
                    for (; i < no_notes.length(); ) {
                        readChar = (char) fp1.read();
                        i++;
                        if ((readChar >= '0' && readChar <= '7')) {
                            octal = octal * 8 + readChar - '0';
                        } else if (readChar == ' ' || readChar == 10) {
                            if (times == 0) {
                                fp1.close();
                                fp2.close();
                                return 9;
                            } else {
                                fp2.write("Number(" + octal + ")\n");
                                tokenIndex = 0;
                                for (int t = 0; t < 20; t++) {
                                    token[t] = '\0';
                                }
                                break;
                            }
                        } else {

                            fp2.write("Number(" + octal + ")\n");
                            tokenIndex = 0;
                            for (int t = 0; t < 20; t++) {
                                token[t] = '\0';
                            }
                            ifRead = 0;
                            break;


                        }
                        times++;
                    }
                    if (i == no_notes.length()) {
                        if (times == 0) {
                            fp1.close();
                            fp2.close();
                            return 9;
                        } else {
                            fp2.write("Number(" + octal + ")\n");
                            fp1.close();
                            fp2.close();
                            return 0;
                        }

                    }

                } else if (readChar == ' ' || readChar == 10) {
                    String k = "";
                    for (int z = 0; z < token.length; z++) {
                        if (token[z] != '\0') {
                            k += token[z];
                        }
                    }
                    String s = "Number(" + k + ")\n";
                    fp2.write(s);
                    tokenIndex = 0;
                    for (int t = 0; t < 20; t++) {
                        token[t] = '\0';
                    }
                } else {
                    String k = "";
                    for (int z = 0; z < token.length; z++) {
                        if (token[z] != '\0') {
                            k += token[z];
                        }
                    }
                    String s = "Number(" + k + ")\n";
                    fp2.write(s);
                    tokenIndex = 0;
                    for (int t = 0; t < 20; t++) {
                        token[t] = '\0';
                    }
                    ifRead = 0;
                }

            } else if (classification(readChar) == 2) {

                token[tokenIndex] = readChar;
                tokenIndex++;
                token[tokenIndex] = '\0';
                for (; i < no_notes.length(); ) {
                    readChar = (char) fp1.read();
                    i++;
                    if (classification(readChar) == 2) {
                        token[tokenIndex] = readChar;
                        tokenIndex++;
                        token[tokenIndex] = '\0';
                    } else if (readChar == ' ' || readChar == 10) {
                        String k = "";
                        for (int z = 0; z < token.length; z++) {
                            if (token[z] != '\0') {
                                k += token[z];
                            }
                        }
                        String s = "Number(" + k + ")\n";
                        fp2.write(s);
                        tokenIndex = 0;
                        for (int t = 0; t < 20; t++) {
                            token[t] = '\0';
                        }
                        break;
                    } else {
                        String k = "";
                        for (int z = 0; z < token.length; z++) {
                            if (token[z] != '\0') {
                                k += token[z];
                            }
                        }
                        String s = "Number(" + k + ")\n";
                        fp2.write(s);
                        tokenIndex = 0;
                        for (int t = 0; t < 20; t++) {
                            token[t] = '\0';
                        }
                        ifRead = 0;
                        break;
                    }
                }
                if (i == no_notes.length()) {
                    String k = "";
                    for (int z = 0; z < token.length; z++) {
                        if (token[z] != '\0') {
                            k += token[z];
                        }
                    }
                    String s = "Number(" + k + ")\n";
                    fp2.write(s);
                    fp1.close();
                    fp2.close();
                    return 0;
                }

            } else if (readChar == ' ') {

            } else if (readChar == '\n') {

            } else if (readChar == 9 || readChar == 13) {

            } else if (i == no_notes.length()) {
                fp1.close();
                fp2.close();
                return 0;
            } else {

                char c = readChar;
                readChar = (char) fp1.read();
                i++;
                //System.out.println(c+"  "+readChar+"  "+i+" hhhhhhhhhhhhhhhhhhhh");
                if (i == no_notes.length()) {
                    a(c, fp2);
                    System.out.println(c + "  " + i + " hhhhhhhhhhhhhhhhhhhh");
                    fp1.close();
                    fp2.close();
                    return 0;
                } else if (c == '=' && readChar == '=') {
                    fp2.write("Eq\n");
                } else {
                    if (a(c, fp2) == -1) {
                        fp1.close();
                        fp2.close();
                        return 0;
                    }
                    ifRead = 0;

                }

            }

        }

    }

    private static int a(char c, FileWriter fp2) throws IOException {
        switch (c) {
            case '=': {
                fp2.write("Assign\n");
                return 0;
            }
            case ';': {
                fp2.write("Semicolon\n");
                return 0;
            }
            case '(': {
                fp2.write("LPar\n");
                return 0;
            }
            case ')': {
                fp2.write("RPar\n");
                return 0;
            }
            case '{': {
                fp2.write("LBrace\n");
                return 0;
            }
            case '}': {
                fp2.write("RBrace\n");
                return 0;
            }
            case '+': {
                fp2.write("Plus\n");
                return 0;
            }
            case '-': {
                fp2.write("Sub\n");
                return 0;
            }
            case '*': {
                fp2.write("Mult\n");
                return 0;
            }
            case '/': {
                fp2.write("Div\n");
                return 0;
            }
            case '%': {
                fp2.write("Mol\n");
                return 0;
            }
            case '<': {
                fp2.write("Lt\n");
                return 0;
            }
            case '>': {
                fp2.write("Gt\n");
                return 0;
            }
            default: {
                fp2.write("Err\n");
                return -1;
            }
        }
    }

    private static void keys(char[] s, FileWriter fp2) throws IOException {
        String s1 = "";
        for (int z = 0; z < s.length; z++) {
            if (s[z] != '\0') {
                s1 += s[z];
            }
        }
        if (s1.toString().equals("if")) fp2.write("If\n");
        else if (s1.toString().equals("else")) fp2.write("Else\n");
        else if (s1.toString().equals("while")) fp2.write("While\n");
        else if (s1.toString().equals("break")) fp2.write("Break\n");
        else if (s1.toString().equals("continue")) fp2.write("Continue\n");
        else if (s1.toString().equals("return")) fp2.write("Return\n");
        else {
            String k = "";
            for (int z = 0; z < s.length; z++) {
                if (s[z] != '\0') {
                    k += s[z];
                }
            }
            fp2.write("Ident(" + k + ")\n");
        }
    }

    private static int classification(char c) {
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
            return 1;
        else if (c >= '0' && c <= '9')
            return 2;
        else if (c == '_')
            return 3;
        else if (c == '=' ||
                c == ';' ||
                c == '(' ||
                c == ')' ||
                c == '{' ||
                c == '}' ||
                c == '+' ||
                c == '*' ||
                c == '/' ||
                c == '<' ||
                c == '>')
            return 4;
        else
            return 5;
    }

    private static void clean_notes(File inputfile, File no_notes) throws IOException {
        FileReader fp = new FileReader(inputfile);

        FileWriter fp2 = new FileWriter(no_notes);
        char c;
        int a = 0;
        int b = 0;
        for (int i = 0; i < inputfile.length(); ) {
            c = (char) fp.read();
            i++;
            if (c == '/') {
                char c2 = (char) fp.read();
                i++;
                if (c2 == '/') {
                    while ((c = (char) fp.read()) != '\n') {
                        i++;
                        if (i >= inputfile.length())
                            break;
                    }
                    i++;
                    fp2.write("\n");
                } else if (c2 == '*') {
                    while (i < inputfile.length()) {
                        c = (char) fp.read();
                        i++;
                        if (c == '*') {
                            c = (char) fp.read();
                            i++;
                            if (c == '/') {
                                break;
                            }
                        }
                    }

                } else {
                    fp2.write("/");
                }
            } else {
                fp2.write(c);
            }
        }

        fp.close();
        fp2.close();
    }
}
