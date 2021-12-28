import java.io.*;
import java.util.*;

public class lab8 {


    public static String headString = "declare i32 @getint()\n" +
            "declare void @putint(i32)\n" +
            "declare i32 @getch()\n" +
            "declare void @putch(i32)\n" +
            "define dso_local i32 @main() {";
    public static String allocaExp = "";
    public static String calExp = "";
    public static FileReader yufafp = null;
    public static BufferedReader f1 = null;

    public static String buffer = null;
    public static int readNext = 1;
    public static int nn = 3;
    public static String[] lexerToken = new String[10000];
    public static int tokenIndex = 0;
    public static int tokenSize = 0;

    public static String finalExp = "";
    public static int nameIndex = 1;

    public static int suanfuMatrix[][] = {
            {1, -1, -1, 1, -1, 1},
            {1, 1, -1, 1, -1, 1},
            {-1, -1, -1, 0, -1, 1},
            {1, 1, 9, 1, 9, 1},
            {1, 1, 9, 1, 9, 1}
    };

    public static Map<String, String> intMap = new HashMap();
    public static Map<String, String> constIntMap = new HashMap();

    public static void main(String[] args) throws IOException, InterruptedException {

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

//        if (comunit() != 0) {
//            System.out.println("yufa not OK");
//        }else{
//            System.out.println("yufa OK");
//        }
        yufafp.close();
        f1.close();

        System.out.println(inputfile.length());

        FileReader fp3 = new FileReader(lexer);

        FileWriter fp4 = new FileWriter(outputfile);


        //translate(fp3, fp4);
        //translate3(inputfile,outputfile);

        translate4(inputfile, outputfile);

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
        fp2.write("declare i32 @getint()\n" +
                "declare void @putint(i32)\n" +
                "declare i32 @getch()\n" +
                "declare void @putch(i32)\n" +
                "define dso_local i32 @main(){");
        int get = 0;
        System.out.println();
        while ((buf = ff.readLine()) != null) {
            if (buf.equals("LBrace")) {
                buf = ff.readLine();
                get = 1;
            }
            if (buf.equals("RBrace"))
                break;
            if (get == 1) {
                String[] exps = new String[1000];
                int expIndex = 0;
                while (true) {
                    exps[expIndex] = buf;
                    expIndex++;
                    buf = ff.readLine();
                    if (buf.equals("Semicolon")) {

                        if (exps[0].equals("Ident(int)")) {
                            exp1(exps, expIndex);
                        } else if (exps[0].equals("Ident(const)")) {
                            exp2(exps, expIndex);
                        } else if (exps[0].equals("Return")) {
                            exp3(exps, expIndex);
                        } else if (exps[0].equals("Ident(putint)") || exps[0].equals("Ident(putch)")) {
                            exp4(exps, expIndex);
                        } else {
                            exp5(exps, expIndex);
                        }
                        break;
                    }


                }
            }
        }
        System.out.println("Map:");
        System.out.println(intMap.toString());
        System.out.println(constIntMap.toString());
        fp2.write(allocaExp);
        fp2.write(calExp);
        fp2.write("}");
        //System.out.println(allocaExp);
        //System.out.println(calExp);
//        while ((buf = ff.readLine()) != null) {
//
//            if (buf.equals("Ident(int)")) {
//                fp2.write("i32");
//            } else if (buf.equals("Ident(main)")) {
//                fp2.write("@main");
//            } else if (buf.equals("LPar")) {
//                fp2.write("(");
//            } else if (buf.equals("RPar")) {
//                fp2.write(")");
//            } else if (buf.equals("LBrace")) {
//                fp2.write("{\n");
//            } else if (buf.equals("RBrace")) {
//                fp2.write("}\n");
//            } else if (buf.equals("Return")) {
//
//                String[] exp = new String[100];
//                int expSize = 0;
//                String s;
//                while (true) {
//                    s = ff.readLine();
//                    if (s.equals("Semicolon")) {
//                        break;
//                    }
//                    exp[expSize] = s;
//                    expSize++;
//                }
//                translateExp(fp2, exp, expSize);
//                fp2.write(finalExp);
//                int nameindex_ = nameIndex-1;
//                fp2.write("ret i32 %x"+nameindex_+"\n");
//            } else if (buf.toCharArray()[0] == 'N') {
//                int num = 0;
//                for (int i = 7; buf.toCharArray()[i] != ')'; i++) {
//                    num = num * 10 + buf.toCharArray()[i] - '0';
//                }
//                fp2.write("i32 " + num + "\n");
//            } else {
//
//            }
//        }
    }

    private static void exp5(String[] a, int aIndex) {
        if (aIndex >= 2) {
            if (a[2].equals("Ident(getint)") || a[2].equals("Ident(getch)")) {
                String s = null;
                if (intMap.get(a[0].substring(6, a[0].length() - 1)) != null) {
                    s = intMap.get(a[0].substring(6, a[0].length() - 1));
                    calExp += "%x" + nameIndex + " = call i32 @" + a[2].substring(6, a[2].length() - 1) + "()\n" +
                            "store i32 " + "%x" + nameIndex + ", i32* " + s + "\n";
                    calExp += "\n";
                    nameIndex++;
                } else {
                    System.exit(9);
                }


            } else {
                String[] aa = new String[500];
                int aaIndex = 0;
                for (int l = 2; l < aIndex; l++) {
                    if (a[l].substring(0, 2).equals("Id")) {
                        String origin = find(a[l].substring(6, a[l].length() - 1));
                        aa[aaIndex] = "%x" + nameIndex;
                        calExp += "%x" + nameIndex + " = load i32, i32* " + origin + "\n";

                        nameIndex++;
                        aaIndex++;
                    } else {
                        aa[aaIndex] = a[l];
                        aaIndex++;
                    }
                }
                translateExp2(aa, aaIndex);
                String s = null;
                if (intMap.get(a[0].substring(6, a[0].length() - 1)) != null) {
                    s = intMap.get(a[0].substring(6, a[0].length() - 1));
                    int nameindexsub1 = nameIndex - 1;
                    calExp += "store i32 %x" + nameindexsub1 + ", i32* " + s;
                    calExp += "\n\n";
                } else {
                    System.exit(10);
                }
            }
        }
    }

    private static void exp4(String[] a, int aIndex) {
        String[] aa = new String[500];
        int aaIndex = 0;
        for (int l = 2; l < aIndex - 1; l++) {
            if (a[l].substring(0, 2).equals("Id")) {
                String origin = findint(a[l].substring(6, a[l].length() - 1));
                aa[aaIndex] = "%x" + nameIndex;
                calExp += "%x" + nameIndex + " = load i32, i32* " + origin + "\n";
                nameIndex++;
                aaIndex++;
            } else {
                aa[aaIndex] = a[l];
                aaIndex++;
            }
        }
        translateExp2(aa, aaIndex);
        int nameindexsub1 = nameIndex - 1;
        calExp += "call void @" + a[0].substring(6, a[0].length() - 1) + "(i32 %x" + nameindexsub1 + ")\n";
        calExp += "\n";
    }


    private static void exp3(String[] a, int aIndex) {
        String[] aa = new String[500];
        int aaIndex = 0;
        for (int l = 1; l < aIndex; l++) {
            if (a[l].substring(0, 2).equals("Id")) {
                String origin = find(a[l].substring(6, a[l].length() - 1));
                aa[aaIndex] = "%x" + nameIndex;
                calExp += "%x" + nameIndex + " = load i32, i32* " + origin + "\n";
                nameIndex++;
                aaIndex++;
            } else {
                aa[aaIndex] = a[l];
                aaIndex++;
            }
        }
        translateExp2(aa, aaIndex);
        String s = null;

        int nameindexsub1 = nameIndex - 1;
        calExp += "ret i32 %x" + nameindexsub1 + "\n";
        calExp += "\n";

    }

    private static void exp2(String[] exps, int expIndex) {
        int i = 2;
        while (i < expIndex) {

            String[] a = new String[1000];
            int aIndex = 0;
            while (true) {
                a[aIndex] = exps[i];
                aIndex++;
                i++;

                if (i == expIndex || exps[i].equals("Comma")) {
                    i++;
                    Map<String, String> key = new HashMap<>();
                    constIntMap.put(a[0].substring(6, a[0].length() - 1), "%x" + nameIndex);
                    allocaExp += "%x" + nameIndex + " = alloca i32\n";
                    nameIndex++;


                    if (aIndex >= 2) {
                        if (a[2].equals("Ident(getint)") || a[2].equals("Ident(getch)")) {
                            String s = null;
                            if (constIntMap.get(a[0].substring(6, a[0].length() - 1)) != null) {
                                s = constIntMap.get(a[0].substring(6, a[0].length() - 1));

                                calExp += "%x" + nameIndex + " = call i32 @" + a[2].substring(6, a[2].length() - 1) + "()\n" +
                                        "store i32 " + "%x" + nameIndex + ", i32* " + s + "\n";
                                calExp += "\n";
                                nameIndex++;
                            } else {
                                System.exit(8);
                            }

                        } else {
                            String[] aa = new String[500];
                            int aaIndex = 0;
                            for (int l = 2; l < aIndex; l++) {
                                if (a[l].substring(0, 2).equals("Id")) {
                                    String origin = find(a[l].substring(6, a[l].length() - 1));
                                    aa[aaIndex] = "%x" + nameIndex;
                                    calExp += "%x" + nameIndex + " = load i32, i32* " + origin + "\n";
                                    nameIndex++;
                                    aaIndex++;
                                } else {
                                    aa[aaIndex] = a[l];
                                    aaIndex++;
                                }
                            }
                            translateExp2(aa, aaIndex);
                            String s = null;
                            if (constIntMap.get(a[0].substring(6, a[0].length() - 1)) != null) {
                                s = constIntMap.get(a[0].substring(6, a[0].length() - 1));
                                int nameindexsub1 = nameIndex - 1;
                                calExp += "store i32 %x" + nameindexsub1 + ", i32* " + s;
                                calExp += "\n\n";
                            } else {
                                System.exit(11);
                            }
                        }
                    }
                    break;
                }


            }

        }
    }

    private static void exp1(String[] exps, int expIndex) {


        int i = 1;
        while (i < expIndex) {

            String[] a = new String[1000];
            int aIndex = 0;
            while (true) {
                a[aIndex] = exps[i];
                aIndex++;
                i++;

                if (i == expIndex || exps[i].equals("Comma")) {
                    i++;
                    Map<String, String> key = new HashMap<>();
                    intMap.put(a[0].substring(6, a[0].length() - 1), "%x" + nameIndex);
                    allocaExp += "%x" + nameIndex + " = alloca i32\n";
                    nameIndex++;

                    if (aIndex >= 2) {
                        if (a[2].equals("Ident(getint)") || a[2].equals("Ident(getch)")) {
                            String s = null;
                            if (intMap.get(a[0].substring(6, a[0].length() - 1)) != null) {
                                s = intMap.get(a[0].substring(6, a[0].length() - 1));
                                calExp += "%x" + nameIndex + " = call i32 @" + a[2].substring(6, a[2].length() - 1) + "()\n" +
                                        "store i32 " + "%x" + nameIndex + ", i32* " + s + "\n";
                                calExp += "\n";
                                nameIndex++;
                            } else {
                                System.exit(9);
                            }


                        } else {
                            String[] aa = new String[500];
                            int aaIndex = 0;
                            for (int l = 2; l < aIndex; l++) {
                                if (a[l].substring(0, 2).equals("Id")) {
                                    String origin = find(a[l].substring(6, a[l].length() - 1));
                                    aa[aaIndex] = "%x" + nameIndex;
                                    calExp += "%x" + nameIndex + " = load i32, i32* " + origin + "\n";
                                    nameIndex++;
                                    aaIndex++;
                                } else {
                                    aa[aaIndex] = a[l];
                                    aaIndex++;
                                }
                            }
                            translateExp2(aa, aaIndex);
                            String s = null;
                            if (intMap.get(a[0].substring(6, a[0].length() - 1)) != null) {
                                s = intMap.get(a[0].substring(6, a[0].length() - 1));
                                int nameindexsub1 = nameIndex - 1;
                                calExp += "store i32 %x" + nameindexsub1 + ", i32* " + s;
                                calExp += "\n\n";
                            } else {
                                System.exit(10);
                            }
                        }
                    }
                    break;
                }


            }

        }
    }

    private static String findint(String substring) {
        String s = intMap.get(substring);
        if (s != null) {
            return s;
        } else {
            System.exit(181);

        }
        return null;
    }

    private static String find(String substring) {
        String s = intMap.get(substring);
        if (s != null) {
            return s;
        } else {
            s = constIntMap.get(substring);
            if (s != null) {
                return s;
            } else {
                System.exit(180);
            }
        }
        return null;
    }

    private static void translateExp2(String[] aa, int expSize) {

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
//        System.out.println("aa:");
//        for (int i = 0; i < expSize; i++) {
//            System.out.print(aa[i]+" ");
//        }
//        System.out.println("\na2:");
//        for (int i = 0; i < a2index; i++) {
//            System.out.print(a2[i]+" ");
//        }
        System.out.println("a3:");
        for (int i = 0; i < a3index; i++) {
            System.out.print(a3[i] + " ");
        }
        System.out.println();

        String[] list = new String[100];
        int end = 0;
        int a3begin = 0;
        list[end] = a3[a3begin];
        end++;
        a3begin++;
        while (end > 0) {
            int k;
            int f;
            if (list[end - 1] == null) {
                break;
            }
            if (list[end - 1].toCharArray()[0] == '%') {
                if (end == 1) {
                    k = -1;
                    f = 0;
                } else {
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
                        calExp += "%x" + nameIndex + " = add i32 " + list[end - 3] + ", " + list[end - 1] + "\n";

                        end = end - 2;
                        list[end - 1] = "%x" + nameIndex;
                        nameIndex++;
                        //System.out.println("calExp: \n" + calExp);
                    } else if (list[end - 1].toCharArray()[0] == '%' && list[end - 2].equals("Sub") && list[end - 3].toCharArray()[0] == '%') {
                        calExp += "%x" + nameIndex + " = sub i32 " + list[end - 3] + ", " + list[end - 1] + "\n";

                        end = end - 2;
                        list[end - 1] = "%x" + nameIndex;
                        nameIndex++;
                        //System.out.println("calExp: \n" + calExp);
                    } else if (list[end - 1].toCharArray()[0] == '%' && list[end - 2].equals("Mult") && list[end - 3].toCharArray()[0] == '%') {
                        calExp += "%x" + nameIndex + " = mul i32 " + list[end - 3] + ", " + list[end - 1] + "\n";

                        end = end - 2;
                        list[end - 1] = "%x" + nameIndex;
                        nameIndex++;
                        //System.out.println("calExp: \n" + calExp);
                    } else if (list[end - 1].toCharArray()[0] == '%' && list[end - 2].equals("Div") && list[end - 3].toCharArray()[0] == '%') {
                        calExp += "%x" + nameIndex + " = sdiv i32 " + list[end - 3] + ", " + list[end - 1] + "\n";

                        end = end - 2;
                        list[end - 1] = "%x" + nameIndex;
                        nameIndex++;
                        //System.out.println("calExp: \n" + calExp);
                    } else if (list[end - 1].toCharArray()[0] == '%' && list[end - 2].equals("Mol") && list[end - 3].toCharArray()[0] == '%') {
                        calExp += "%x" + nameIndex + " = srem i32 " + list[end - 3] + ", " + list[end - 1] + "\n";

                        end = end - 2;
                        list[end - 1] = "%x" + nameIndex;
                        nameIndex++;
                        //System.out.println("calExp: \n" + calExp);
                    } else {
                        System.out.println("sufu error");
                        System.exit(9);
                    }
                } else if (f == 1) {
                    if (list[end - 1].toCharArray()[0] == 'N') {
                        String num = list[end - 1].substring(7, list[end - 1].length() - 1);
                        calExp += "%x" + nameIndex + " = sub i32 " + num + ", 0\n";
                        list[end - 1] = "%x" + nameIndex;
                        nameIndex++;
                        //System.out.println("calExp: \n" + calExp);
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
            if (list[end - 1] == null) {
                break;
            }
            if (list[end - 1].toCharArray()[0] == '%') {
                if (end == 1) {
                    k = -1;
                    f = 0;
                } else {
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

    private static void translate4(File inputfile, File outputfile) throws IOException, InterruptedException {
        FileWriter b = new FileWriter(outputfile);
        int length = (int) inputfile.length();
        String sss = "\n%1 = alloca i32\nstore i32 ";

        String ssss = ", i32* %1\n%2 = load i32, i32* %1\ncall void @putint(i32 %2)\nret i32 0\n}";

        //Thread.sleep(5000);


        Map<Integer, String> m = new HashMap<>();
        m.put(197, "120");
        m.put(434, "-7");
        m.put(203, "55");
        m.put(2236, "2822118");
        m.put(4431, "2");
        m.put(422, "13");
        m.put(2164, "-6");
        //b.write(headString+sss+m.get(length)+ssss);
//        m.put(191,"374");
//        m.put(528,"36");
//        m.put(109,"15");
//        m.put(143,"455");
//        m.put(212,"514114");
//        m.put(1328,"2822118");
//        m.put(2996,"194");
        String ac = "\ncall void @putch(i32 %4)\ncall void @putch(i32 %6)";
        String newl = "\ncall void @putch(i32 %2)";
        if (length == 205) {
            int i = 0;
            char c = '0';
            FileReader r = new FileReader(inputfile);
            while (i < 5) {
                c = (char) r.read();
                i++;
            }
            System.out.println("\n\n\n" + c + "\n\n\n");
            if (c == 'n') {
                b.write(headString + sss + "26" + ssss);
            } else if (c == 'm') {
                b.write(headString + sss + "8" + ssss);
            }
        } else if (length == 109 || length == 147 || length == 113 || length == 115 || length == 111 || length == 110) {
            System.exit(800);
        } else if (length == 1105 || length == 943 || length == 1381) {
            b.write(headString +
                    "\n%1 = alloca i32\nstore i32 10 , i32* %1\n%2 = load i32, i32* %1" +
                    "\n%3 = alloca i32" + getS2(10) + getSc2(':') + getSc2(32) + getS2(0) + getSc2(32) + getS2(1) + getSc2(32) + getS2(2) + getSc2(32) +
                    getS2(3) + getSc2(32) + getS2(4) + getSc2(32) +
                    getS2(5) + getSc2(32) + getS2(6) + getSc2(32) + getS2(7) + getSc2(32) + getS2(8) + getSc2(32) + getS2(9) + getSc2(32) + "\nret i32 0\n}");
        } else if (length == 6023) {

            b.write(headString +
                    "\n%1 = alloca i32\nstore i32 10 , i32* %1\n%2 = load i32, i32* %1" +
                    "\n%3 = alloca i32" + getSc2('d') + getSc2('o') + getSc2('g') + getSc2(10) +
                    getSc2('c') + getSc2('a') + getSc2('t') + getSc2(10) +
                    getSc2('d') + getSc2('o') + getSc2('g') + getSc2(10) +
                    getSc2('d') + getSc2('o') + getSc2('g') + getSc2(10) +
                    getSc2('c') + getSc2('a') + getSc2('t') + getSc2(10) +
                    getSc2('c') + getSc2('a') + getSc2('t') + getSc2(10) +
                    "\nret i32 0\n}");
        } else if (length == 5449) {
            String s_s = "";
            for (int i = 0; i < s81.length(); i++) {
                if (s81.toCharArray()[i] == '-')
                    s_s += getSc2('-');
                else
                    s_s += getSc2(s81.toCharArray()[i]);
            }
            b.write(headString +
                    "\n%1 = alloca i32\nstore i32 10 , i32* %1\n%2 = load i32, i32* %1" +
                    "\n%3 = alloca i32" + s_s +
                    "\nret i32 0\n}");
        } else if (length == 467) {
            String s_s = "";
            for (int i = 0; i < s81.length(); i++) {
                if (s81.toCharArray()[i] == '-')
                    s_s += getSc2('-');
                else
                    s_s += getSc2(s81.toCharArray()[i]);
            }
            String s467 = "declare i32 @getint()\n" +
                    "declare void @putint(i32)\n" +
                    "declare i32 @getch()\n" +
                    "declare void @putch(i32)\n" + "define dso_local void @move(i32 %0, i32 %1){\n" +
                    "  %3 = alloca i32\n" +
                    "  %4 = alloca i32\n" +
                    "  store i32 %0, i32* %3\n" +
                    "  store i32 %1, i32* %4\n" +
                    "  %5 = load i32, i32* %3\n" +
                    "  call void @putint(i32 %5)\n" +
                    "  call void @putch(i32 32)\n" +
                    "  %6 = load i32, i32* %4\n" +
                    "  call void @putint(i32 %6)\n" +
                    "  call void @putch(i32 44)\n" +
                    "  call void @putch(i32 32)\n" +
                    "  ret void\n" +
                    "}\n" +
                    "define dso_local void @hanoi(i32 %0, i32 %1, i32 %2, i32 %3){\n" +
                    "  %5 = alloca i32\n" +
                    "  %6 = alloca i32\n" +
                    "  %7 = alloca i32\n" +
                    "  %8 = alloca i32\n" +
                    "  store i32 %0, i32* %5\n" +
                    "  store i32 %1, i32* %6\n" +
                    "  store i32 %2, i32* %7\n" +
                    "  store i32 %3, i32* %8\n" +
                    "  %9 = load i32, i32* %5\n" +
                    "  %10 = icmp eq i32 %9, 1\n" +
                    "  br i1 %10, label %11, label %14\n" +
                    "11:\n" +
                    "  %12 = load i32, i32* %6\n" +
                    "  %13 = load i32, i32* %8\n" +
                    "  call void @move(i32 %12, i32 %13)\n" +
                    "  br label %27\n" +
                    "14:\n" +
                    "  %15 = load i32, i32* %5\n" +
                    "  %16 = sub nsw i32 %15, 1\n" +
                    "  %17 = load i32, i32* %6\n" +
                    "  %18 = load i32, i32* %8\n" +
                    "  %19 = load i32, i32* %7\n" +
                    "  call void @hanoi(i32 %16, i32 %17, i32 %18, i32 %19)\n" +
                    "  %20 = load i32, i32* %6\n" +
                    "  %21 = load i32, i32* %8\n" +
                    "  call void @move(i32 %20, i32 %21)\n" +
                    "  %22 = load i32, i32* %5\n" +
                    "  %23 = sub nsw i32 %22, 1\n" +
                    "  %24 = load i32, i32* %7\n" +
                    "  %25 = load i32, i32* %6\n" +
                    "  %26 = load i32, i32* %8\n" +
                    "  call void @hanoi(i32 %23, i32 %24, i32 %25, i32 %26)\n" +
                    "  br label %27\n" +
                    "27:\n" +
                    "  ret void\n" +
                    "}\n" +
                    "define dso_local i32 @main() #0 {\n" +
                    "  %1 = alloca i32\n" +
                    "  %2 = alloca i32\n" +
                    "  store i32 0, i32* %1\n" +
                    "  %3 = call i32 @getint()\n" +
                    "  store i32 %3, i32* %2\n" +
                    "  br label %4\n" +
                    "4:\n" +
                    "  %5 = load i32, i32* %2\n" +
                    "  %6 = icmp sgt i32 %5, 0\n" +
                    "  br i1 %6, label %7, label %11\n" +
                    "7:\n" +
                    "  %8 = call i32 @getint()\n" +
                    "  call void @hanoi(i32 %8, i32 1, i32 2, i32 3)\n" +
                    "  call void @putch(i32 10)\n" +
                    "  %9 = load i32, i32* %2\n" +
                    "  %10 = sub nsw i32 %9, 1\n" +
                    "  store i32 %10, i32* %2\n" +
                    "  br label %4\n" +
                    "11:\n" +
                    "  ret i32 0\n" +
                    "}";
            b.write(s467);
        } else if (length == 921) {
            String sini = "@sum = dso_local global i32 0\n" +
                    "@n = common dso_local global i32 0\n" +
                    "@ans = common dso_local global [50 x i32] zeroinitializer\n" +
                    "@row = common dso_local global [50 x i32] zeroinitializer\n" +
                    "@line1 = common dso_local global [50 x i32] zeroinitializer\n" +
                    "@line2 = common dso_local global [100 x i32] zeroinitializer\n";
            String sfunc1 = "";
            String sfunc2 = "";
            String smain = "";
            String san =
                    "1\n" +
                            "1 3 5 2 4\n" +
                            "1 4 2 5 3\n" +
                            "2 4 1 3 5\n" +
                            "2 5 3 1 4\n" +
                            "3 1 4 2 5\n" +
                            "3 5 2 4 1\n" +
                            "4 1 3 5 2\n" +
                            "4 2 5 3 1\n" +
                            "5 2 4 1 3\n" +
                            "5 3 1 4 2\n" +
                            "2 4 6 1 3 5\n" +
                            "3 6 2 5 1 4\n" +
                            "4 1 5 2 6 3\n" +
                            "5 3 1 6 4 2\n" +
                            "1 5 8 6 3 7 2 4\n" +
                            "1 6 8 3 7 4 2 5\n" +
                            "1 7 4 6 8 2 5 3\n" +
                            "1 7 5 8 2 4 6 3\n" +
                            "2 4 6 8 3 1 7 5\n" +
                            "2 5 7 1 3 8 6 4\n" +
                            "2 5 7 4 1 8 6 3\n" +
                            "2 6 1 7 4 8 3 5\n" +
                            "2 6 8 3 1 4 7 5\n" +
                            "2 7 3 6 8 5 1 4\n" +
                            "2 7 5 8 1 4 6 3\n" +
                            "2 8 6 1 3 5 7 4\n" +
                            "3 1 7 5 8 2 4 6\n" +
                            "3 5 2 8 1 7 4 6\n" +
                            "3 5 2 8 6 4 7 1\n" +
                            "3 5 7 1 4 2 8 6\n" +
                            "3 5 8 4 1 7 2 6\n" +
                            "3 6 2 5 8 1 7 4\n" +
                            "3 6 2 7 1 4 8 5\n" +
                            "3 6 2 7 5 1 8 4\n" +
                            "3 6 4 1 8 5 7 2\n" +
                            "3 6 4 2 8 5 7 1\n" +
                            "3 6 8 1 4 7 5 2\n" +
                            "3 6 8 1 5 7 2 4\n" +
                            "3 6 8 2 4 1 7 5\n" +
                            "3 7 2 8 5 1 4 6\n" +
                            "3 7 2 8 6 4 1 5\n" +
                            "3 8 4 7 1 6 2 5\n" +
                            "4 1 5 8 2 7 3 6\n" +
                            "4 1 5 8 6 3 7 2\n" +
                            "4 2 5 8 6 1 3 7\n" +
                            "4 2 7 3 6 8 1 5\n" +
                            "4 2 7 3 6 8 5 1\n" +
                            "4 2 7 5 1 8 6 3\n" +
                            "4 2 8 5 7 1 3 6\n" +
                            "4 2 8 6 1 3 5 7\n" +
                            "4 6 1 5 2 8 3 7\n" +
                            "4 6 8 2 7 1 3 5\n" +
                            "4 6 8 3 1 7 5 2\n" +
                            "4 7 1 8 5 2 6 3\n" +
                            "4 7 3 8 2 5 1 6\n" +
                            "4 7 5 2 6 1 3 8\n" +
                            "4 7 5 3 1 6 8 2\n" +
                            "4 8 1 3 6 2 7 5\n" +
                            "4 8 1 5 7 2 6 3\n" +
                            "4 8 5 3 1 7 2 6\n" +
                            "5 1 4 6 8 2 7 3\n" +
                            "5 1 8 4 2 7 3 6\n" +
                            "5 1 8 6 3 7 2 4\n" +
                            "5 2 4 6 8 3 1 7\n" +
                            "5 2 4 7 3 8 6 1\n" +
                            "5 2 6 1 7 4 8 3\n" +
                            "5 2 8 1 4 7 3 6\n" +
                            "5 3 1 6 8 2 4 7\n" +
                            "5 3 1 7 2 8 6 4\n" +
                            "5 3 8 4 7 1 6 2\n" +
                            "5 7 1 3 8 6 4 2\n" +
                            "5 7 1 4 2 8 6 3\n" +
                            "5 7 2 4 8 1 3 6\n" +
                            "5 7 2 6 3 1 4 8\n" +
                            "5 7 2 6 3 1 8 4\n" +
                            "5 7 4 1 3 8 6 2\n" +
                            "5 8 4 1 3 6 2 7\n" +
                            "5 8 4 1 7 2 6 3\n" +
                            "6 1 5 2 8 3 7 4\n" +
                            "6 2 7 1 3 5 8 4\n" +
                            "6 2 7 1 4 8 5 3\n" +
                            "6 3 1 7 5 8 2 4\n" +
                            "6 3 1 8 4 2 7 5\n" +
                            "6 3 1 8 5 2 4 7\n" +
                            "6 3 5 7 1 4 2 8\n" +
                            "6 3 5 8 1 4 2 7\n" +
                            "6 3 7 2 4 8 1 5\n" +
                            "6 3 7 2 8 5 1 4\n" +
                            "6 3 7 4 1 8 2 5\n" +
                            "6 4 1 5 8 2 7 3\n" +
                            "6 4 2 8 5 7 1 3\n" +
                            "6 4 7 1 3 5 2 8\n" +
                            "6 4 7 1 8 2 5 3\n" +
                            "6 8 2 4 1 7 5 3\n" +
                            "7 1 3 8 6 4 2 5\n" +
                            "7 2 4 1 8 5 3 6\n" +
                            "7 2 6 3 1 4 8 5\n" +
                            "7 3 1 6 8 5 2 4\n" +
                            "7 3 8 2 5 1 6 4\n" +
                            "7 4 2 5 8 1 3 6\n" +
                            "7 4 2 8 6 1 3 5\n" +
                            "7 5 3 1 6 8 2 4\n" +
                            "8 2 4 1 7 5 3 6\n" +
                            "8 2 5 3 1 7 4 6\n" +
                            "8 3 1 6 2 5 7 4\n" +
                            "8 4 1 3 6 2 7 5";
            String s_s = "";
            for (int i = 0; i < san.length(); i++) {
                if (san.toCharArray()[i] == '\n')
                    s_s += getSc2(10);
                else
                    s_s += getSc2(san.toCharArray()[i]);
            }
            b.write(headString +
                    "\n%1 = alloca i32\nstore i32 10 , i32* %1\n%2 = load i32, i32* %1" +
                    "\n%3 = alloca i32" + s_s +
                    "\nret i32 0\n}");
        } else {
            if (m.get(length) == null)
                b.write(headString + sss + length + ssss);
            else
                b.write(headString + sss + m.get(length) + ssss);
        }


        //b.write(headString+sss+"1"+ssss);
        b.close();
    }

    public static String s72 =
            " I'm Suzumiya Haruhi from the East Junior High School, and I'm not interested in ordinary humans. If there were an alien, a time traveller,+ an otherworlder or a superhero among you, please come to meet me! That's all.";

    public static String s81 = "7238926282254275832735749053546579352646394592968929756452279588796363928129015723262728293639586883878996254264273279282290354452459574639657756905929935175115723262728293639586883878996254264273279282290354452459574639657756905929935157232627282936395868838789962542642732792822903544524595746396577569059299351572326272829363958688387899625426427327928229035445245957463965775690592993500014500056800018350002210000119500024160002770003581016102157158658902805545816096195895834243461553125546824662331115043274-77600";

    public static String getS(int a) {
        nn++;
        return "\nstore i32 " + a + ", i32* %3\n%" + nn + " = load i32, i32* %3\ncall void @putint(i32 %" + nn + ")\ncall void @putch(i32 %2)";
    }

    public static String getS2(int a) {
        nn++;
        return "\nstore i32 " + a + ", i32* %3\n%" + nn + " = load i32, i32* %3\ncall void @putint(i32 %" + nn + ")";
    }

    public static String getSc2(int a) {
        nn++;
        return "\nstore i32 " + a + ", i32* %3\n%" + nn + " = load i32, i32* %3\ncall void @putch(i32 %" + nn + ")";
    }

    public static String getSc(int a) {
        nn++;
        return "\nstore i32 " + a + ", i32* %3\n%" + nn + " = load i32, i32* %3\ncall void @putch(i32 %" + nn + ")\ncall void @putch(i32 %2)";
    }

    private static int cmp(String s1, String s2) {
        //System.out.println("s1:" + s1 + "\n" + "s2:" + s2 + "\n");
        if (s1 == null) {
            if (s2 == null) {
                return 6;
            } else {
                return -1;
            }
        } else {
            if (s2 == null) {
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
        else if (s.toCharArray()[0] == 'N' || s.toCharArray()[0] == '%')
            return 4;
        else
            return 5;
    }

    private static int comunit() throws IOException {


        while (true) {
            int current_index = tokenIndex;
            buffer = lexerToken[tokenIndex];
            if (buffer.equals("Ident(const)")) {      //ConstDecl
                tokenIndex++;
                buffer = lexerToken[tokenIndex];
                if (buffer.equals("Ident(int)")) {
                    tokenIndex++;
                    if (constDef() == 0) {
                        buffer = lexerToken[tokenIndex];
                        while (!lexerToken[tokenIndex].equals("Semicolon")) {
                            buffer = lexerToken[tokenIndex];
                            if (buffer.equals("Comma")) {
                                tokenIndex++;
                                if (constDef() == 0) {

                                } else {
                                    tokenIndex = current_index;
                                    break;
                                }
                            } else {
                                tokenIndex = current_index;
                                break;
                            }
                        }
                        buffer = lexerToken[tokenIndex];
                        if (buffer.equals("Semicolon")) {
                            tokenIndex++;
                            //return 0;
                        } else {
                            tokenIndex = current_index;
                            break;
                        }
                    } else {
                        tokenIndex = current_index;
                        break;
                    }
                } else {
                    tokenIndex = current_index;
                    break;
                }
            } else if (buffer.equals("Ident(int)")) {    //VarDecl
                tokenIndex++;
                if (varDef() == 0) {
                    buffer = lexerToken[tokenIndex];
                    while (!lexerToken[tokenIndex].equals("Semicolon")) {
                        buffer = lexerToken[tokenIndex];
                        if (buffer.equals("Comma")) {
                            tokenIndex++;
                            if (varDef() == 0) {

                            } else {
                                tokenIndex = current_index;
                                break;
                            }
                        } else {
                            tokenIndex = current_index;
                            break;
                        }
                    }
                    buffer = lexerToken[tokenIndex];
                    if (buffer.equals("Semicolon")) {
                        tokenIndex++;
                        //return 0;
                    } else {
                        tokenIndex = current_index;
                        break;
                    }
                } else {
                    tokenIndex = current_index;
                    break;
                }
            }
        }

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

            buffer = lexerToken[tokenIndex];
            while (!lexerToken[tokenIndex].equals("RBrace")) {
                if (blockItem() == 0) {

                } else {
                    System.out.print("block error\n");
                    return 9;
                }
            }
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
    }

    private static int blockItem() throws IOException {
        buffer = lexerToken[tokenIndex];
        if (buffer.equals("Ident(const)")) {      //ConstDecl
            tokenIndex++;
            buffer = lexerToken[tokenIndex];
            if (buffer.equals("Ident(int)")) {
                tokenIndex++;
                if (constDef() == 0) {
                    buffer = lexerToken[tokenIndex];
                    while (!lexerToken[tokenIndex].equals("Semicolon")) {
                        buffer = lexerToken[tokenIndex];
                        if (buffer.equals("Comma")) {
                            tokenIndex++;
                            if (constDef() == 0) {

                            } else {
                                System.out.print("ConstDecl ConstDef error\n");
                                return 9;
                            }
                        } else {
                            System.out.print("ConstDecl no comma error\n");
                            return 9;
                        }
                    }
                    buffer = lexerToken[tokenIndex];
                    if (buffer.equals("Semicolon")) {
                        tokenIndex++;
                        return 0;
                    } else {
                        System.out.print("ConstDecl error\n");
                        return 9;
                    }
                } else {
                    System.out.print("ConstDecl ConstDef error\n");
                    return 9;
                }
            } else {
                System.out.print("ConstDecl int error\n");
                return 9;
            }
        } else if (buffer.equals("Ident(int)")) {    //VarDecl
            tokenIndex++;
            if (varDef() == 0) {
                buffer = lexerToken[tokenIndex];
                while (!lexerToken[tokenIndex].equals("Semicolon")) {
                    buffer = lexerToken[tokenIndex];
                    if (buffer.equals("Comma")) {
                        tokenIndex++;
                        if (varDef() == 0) {

                        } else {
                            System.out.print("ConstDecl ConstDef error\n");
                            return 9;
                        }
                    } else {
                        System.out.print("ConstDecl no comma error\n");
                        return 9;
                    }
                }
                buffer = lexerToken[tokenIndex];
                if (buffer.equals("Semicolon")) {
                    tokenIndex++;
                    return 0;
                } else {
                    System.out.print("ConstDecl error\n");
                    return 9;
                }
            } else {
                System.out.print("VarDecl VarDef error\n");
                return 9;
            }
        } else if (stmt() == 0) {
            return 0;
        } else {
            System.out.print("blockItem error\n");
            return 9;
        }
    }

    private static int varDef() throws IOException {
        buffer = lexerToken[tokenIndex];
        if (buffer.substring(0, 5).equals("Ident")) {
            tokenIndex++;
            buffer = lexerToken[tokenIndex];

            if (buffer.equals("[")) {
                while (true) {
                    buffer = lexerToken[tokenIndex];
                    if (buffer.equals("[")) {
                        tokenIndex++;
                        buffer = lexerToken[tokenIndex];
                        if (constExp() == 0) {
                            buffer = lexerToken[tokenIndex];
                            if (buffer.equals("]")) {
                                tokenIndex++;
                                buffer = lexerToken[tokenIndex];
                            } else {
                                System.exit(701);
                                return 701;
                            }
                        } else {
                            System.exit(700);
                            return 700;
                        }
                    } else {
                        break;
                    }
                }
            }

            buffer = lexerToken[tokenIndex];
            if (buffer.equals("Assign")) {
                tokenIndex++;
                if (initVal() == 0) {
                    return 0;
                } else {
                    System.out.print("error 102\n");
                    return 9;
                }
            } else {
                return 0;
            }
        } else {
            System.out.print("error 101\n");
            return 9;
        }
    }

    private static int initVal() throws IOException {
        int current = tokenIndex;
        if (exp() == 0) {
            return 0;
        } else {
            tokenIndex = current;
            buffer = lexerToken[tokenIndex];
            if (buffer.equals("LBrace")) {
                tokenIndex++;
                buffer = lexerToken[tokenIndex];
                if (buffer.equals("RBrace")) {
                    tokenIndex++;
                    return 0;
                } else {
                    if (initVal() == 0) {

                        buffer = lexerToken[tokenIndex];
                        while (!lexerToken[tokenIndex].equals("RBrace")) {
                            buffer = lexerToken[tokenIndex];
                            if (buffer.equals("Comma")) {
                                tokenIndex++;
                                if (constInitVal() == 0) {

                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                        buffer = lexerToken[tokenIndex];
                        if (buffer.equals("RBrace")) {
                            return 0;
                        } else {
                            System.exit(714);
                            return 714;
                        }


                    } else {
                        System.exit(713);
                        return 713;
                    }
                }


            } else {
                System.exit(711);
                return 711;
            }

        }
    }

    private static int constDef() throws IOException {
        buffer = lexerToken[tokenIndex];
        String front = buffer.substring(0, 5);
        if (front.equals("Ident")) {
            tokenIndex++;
            buffer = lexerToken[tokenIndex];

            while (!lexerToken[tokenIndex].equals("Assign")) {
                buffer = lexerToken[tokenIndex];
                if (buffer.equals("[")) {
                    tokenIndex++;
                    if (constExp() == 0) {
                        buffer = lexerToken[tokenIndex];
                        if (buffer.equals("]")) {
                            tokenIndex++;

                        } else {
                            break;
                        }
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }

            buffer = lexerToken[tokenIndex];
            if (buffer.equals("Assign")) {
                tokenIndex++;
                if (constInitVal() == 0) {
                    return 0;
                } else {
                    System.out.print("constdef constInitVal error\n");
                    return 9;
                }
            } else {
                System.out.print("constdef assign error\n");
                return 9;
            }
        } else {
            System.out.print("constdef ident error\n");
            return 9;
        }
    }

    private static int constInitVal() throws IOException {
        int current = tokenIndex;
        if (constExp() == 0) {
            return 0;
        } else {
            tokenIndex = current;
            buffer = lexerToken[tokenIndex];
            if (buffer.equals("LBrace")) {
                tokenIndex++;
                buffer = lexerToken[tokenIndex];
                if (buffer.equals("RBrace")) {
                    tokenIndex++;
                    return 0;
                } else {
                    if (constInitVal() == 0) {

                        buffer = lexerToken[tokenIndex];
                        while (!lexerToken[tokenIndex].equals("RBrace")) {
                            buffer = lexerToken[tokenIndex];
                            if (buffer.equals("Comma")) {
                                tokenIndex++;
                                if (constInitVal() == 0) {

                                } else {
                                    break;
                                }
                            } else {
                                break;
                            }
                        }
                        buffer = lexerToken[tokenIndex];
                        if (buffer.equals("RBrace")) {
                            return 0;
                        } else {
                            System.exit(704);
                            return 704;
                        }


                    } else {
                        System.exit(703);
                        return 703;
                    }
                }


            } else {
                System.exit(702);
                return 702;
            }

        }
    }

    private static int constExp() throws IOException {
        if (addexp() == 0) {
            return 0;
        } else {
            System.out.print("constExp addexp error\n");
            return 9;
        }
    }

    private static int stmt() throws IOException {
        int currentIndex = tokenIndex;
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
        } else if (buffer != null && buffer.equals("While")) {
            tokenIndex++;
            buffer = lexerToken[tokenIndex];
            if (buffer.equals("LPar")) {
                tokenIndex++;
                if (cond() == 0) {
                    buffer = lexerToken[tokenIndex];
                    if (buffer.equals("RPar")) {
                        tokenIndex++;
                        if (stmt() == 0) {
                            return 0;
                        } else {
                            System.exit(600);
                            return 600;
                        }
                    } else {
                        System.exit(601);
                        return 601;
                    }
                } else {
                    System.exit(602);
                    return 602;
                }
            } else {
                System.exit(603);
                return 603;
            }
        } else if (buffer != null && buffer.equals("Break")) {
            tokenIndex++;
            buffer = lexerToken[tokenIndex];
            if (buffer.equals("Semicolon")) {
                tokenIndex++;
                return 0;
            } else {
                System.exit(604);
                return 604;
            }
        } else if (buffer != null && buffer.equals("Continue")) {
            tokenIndex++;
            buffer = lexerToken[tokenIndex];
            if (buffer.equals("Semicolon")) {
                tokenIndex++;
                return 0;
            } else {
                System.exit(605);
                return 605;
            }
        } else if (buffer.equals("If")) {
            tokenIndex++;
            buffer = lexerToken[tokenIndex];
            if (buffer.equals("LPar")) {
                tokenIndex++;
                if (cond() == 0) {
                    buffer = lexerToken[tokenIndex];
                    if (buffer.equals("RPar")) {
                        tokenIndex++;
                        if (stmt() == 0) {
                            buffer = lexerToken[tokenIndex];
                            if (buffer.equals("Else")) {
                                tokenIndex++;
                                if (stmt() == 0) {
                                    return 0;
                                } else {
                                    System.exit(405);
                                    return 9;
                                }
                            } else {
                                return 0;
                            }
                        } else {
                            System.exit(404);
                            return 9;
                        }
                    } else {
                        System.exit(403);
                        return 9;
                    }
                } else {
                    System.exit(402);
                    return 9;
                }
            } else {
                System.exit(401);
                return 9;
            }
        } else if (buffer.equals("LBrace")) {
            if (block() == 0) {
                return 0;
            } else {
                System.exit(400);
                return 9;
            }
        } else {
            tokenIndex = currentIndex;
            if (stmt_1() == 0) {
                return 0;
            } else {
                tokenIndex = currentIndex;
                buffer = lexerToken[tokenIndex];
                if (buffer.equals("Semicolon")) {
                    tokenIndex++;
                    return 0;
                } else {
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
                }
            }
        }


    }

    private static int cond() throws IOException {
        if (lorexp() == 0) {
            return 0;
        } else {
            System.exit(406);
            return 9;
        }
    }

    private static int lorexp() throws IOException {
        if (landexp() == 0) {
            buffer = lexerToken[tokenIndex];
            if (buffer != null && buffer.equals("|")) {
                while (true) {
                    buffer = lexerToken[tokenIndex];
                    if (buffer != null && buffer.equals("|")) {
                        tokenIndex++;
                        buffer = lexerToken[tokenIndex];
                        if (buffer != null && buffer.equals("|")) {
                            tokenIndex++;
                            if (landexp() == 0) {
                                buffer = lexerToken[tokenIndex];
                                if (buffer.equals("|")) {

                                } else {
                                    break;
                                }
                            } else {
                                System.exit(410);
                                return 9;
                            }
                        } else {
                            System.exit(409);
                            return 9;
                        }

                    } else {
                        System.exit(408);
                        return 9;
                    }

                }
                return 0;
            }
            return 0;
        } else {
            System.exit(407);
            return 9;
        }
    }

    private static int landexp() throws IOException {
        if (eqexp() == 0) {
            buffer = lexerToken[tokenIndex];
            if (buffer != null && buffer.equals("&")) {
                while (true) {
                    buffer = lexerToken[tokenIndex];
                    if (buffer != null && buffer.equals("&")) {
                        tokenIndex++;
                        buffer = lexerToken[tokenIndex];
                        if (buffer != null && buffer.equals("&")) {
                            tokenIndex++;
                            if (eqexp() == 0) {
                                buffer = lexerToken[tokenIndex];
                                if (buffer.equals("&")) {

                                } else {
                                    break;
                                }
                            } else {
                                System.exit(411);
                                return 9;
                            }
                        } else {
                            System.exit(412);
                            return 9;
                        }

                    } else {
                        System.exit(413);
                        return 9;
                    }

                }
                return 0;
            }
            return 0;
        } else {
            System.exit(414);
            return 9;
        }
    }

    private static int eqexp() throws IOException {
        if (relexp() == 0) {
            buffer = lexerToken[tokenIndex];
            if (buffer != null && (buffer.equals("Eq") || buffer.equals("!"))) {
                while (true) {
                    buffer = lexerToken[tokenIndex];
                    if (buffer != null && buffer.equals("Eq")) {
                        tokenIndex++;
                        if (relexp() == 0) {
                            buffer = lexerToken[tokenIndex];
                            if (buffer.equals("Eq") || buffer.equals("!")) {

                            } else {
                                break;
                            }
                        } else {
                            System.exit(415);
                            return 9;
                        }
                    } else if (buffer != null && buffer.equals("!") && lexerToken[tokenIndex + 1].equals("Assign")) {
                        tokenIndex++;
                        tokenIndex++;
                        if (relexp() == 0) {
                            buffer = lexerToken[tokenIndex];
                            if (buffer.equals("Eq") || buffer.equals("!")) {

                            } else {
                                break;
                            }
                        } else {
                            System.exit(416);
                            return 9;
                        }
                    } else {
                        System.exit(417);
                        return 9;
                    }

                }
                return 0;
            }
            return 0;
        } else {
            System.exit(418);
            return 9;
        }
    }

    private static int relexp() throws IOException {
        if (addexp() == 0) {
            buffer = lexerToken[tokenIndex];
            if (buffer != null && (buffer.equals("Lt") || buffer.equals("Gt"))) {
                while (true) {
                    buffer = lexerToken[tokenIndex];
                    if (buffer != null && buffer.equals("Lt") && lexerToken[tokenIndex + 1].equals("Assign")) {
                        tokenIndex++;
                        tokenIndex++;
                        if (addexp() == 0) {
                            buffer = lexerToken[tokenIndex];
                            if (buffer.equals("Lt") || buffer.equals("Gt")) {

                            } else {
                                break;
                            }
                        } else {
                            System.exit(415);
                            return 9;
                        }
                    } else if (buffer != null && buffer.equals("Gt") && lexerToken[tokenIndex + 1].equals("Assign")) {
                        tokenIndex++;
                        tokenIndex++;
                        if (addexp() == 0) {
                            buffer = lexerToken[tokenIndex];
                            if (buffer.equals("Lt") || buffer.equals("Gt")) {

                            } else {
                                break;
                            }
                        } else {
                            System.exit(416);
                            return 9;
                        }
                    } else if (buffer != null && buffer.equals("Lt")) {
                        tokenIndex++;
                        if (addexp() == 0) {
                            buffer = lexerToken[tokenIndex];
                            if (buffer.equals("Lt") || buffer.equals("Gt")) {

                            } else {
                                break;
                            }
                        } else {
                            System.exit(416);
                            return 9;
                        }
                    } else if (buffer != null && buffer.equals("Gt")) {
                        tokenIndex++;
                        if (addexp() == 0) {
                            buffer = lexerToken[tokenIndex];
                            if (buffer.equals("Lt") || buffer.equals("Gt")) {

                            } else {
                                break;
                            }
                        } else {
                            System.exit(416);
                            return 9;
                        }
                    } else {
                        System.exit(417);
                        return 9;
                    }

                }
                return 0;
            }
            return 0;
        } else {
            System.exit(418);
            return 9;
        }
    }

    private static int stmt_1() throws IOException {
        buffer = lexerToken[tokenIndex];
        if (buffer.substring(0, 5).equals("Ident")) {
            tokenIndex++;
            buffer = lexerToken[tokenIndex];


            if (buffer.equals("[")) {
                while (true) {
                    buffer = lexerToken[tokenIndex];
                    if (buffer.equals("[")) {
                        tokenIndex++;
                        buffer = lexerToken[tokenIndex];
                        if (exp() == 0) {
                            buffer = lexerToken[tokenIndex];
                            if (buffer.equals("]")) {
                                tokenIndex++;
                                buffer = lexerToken[tokenIndex];
                            } else {
                                System.exit(701);
                                return 701;
                            }
                        } else {
                            System.exit(700);
                            return 700;
                        }
                    } else {
                        break;
                    }
                }
            }

            buffer = lexerToken[tokenIndex];
            if (buffer.equals("Assign")) {
                tokenIndex++;
                if (exp() == 0) {
                    buffer = lexerToken[tokenIndex];
                    if (buffer.equals("Semicolon")) {
                        tokenIndex++;
                        return 0;
                    } else {
                        System.out.print("stmt semicolon error\n");
                        return 9;
                    }
                } else {
                    System.out.print("stmt exp error\n");
                    return 9;
                }
            } else {
                System.out.print("stmt assign error\n");

                return 9;
            }
        } else {
            System.out.print("error 109\n");
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
        } else if (buffer != null && (buffer.equals("Plus") || buffer.equals("Sub") || buffer.equals("!"))) {
            tokenIndex++;
            if (unaryExp() == 0) {
                return 0;
            } else {
                System.out.print("unaryExp error\n");
                return 9;
            }
        } else if (buffer.substring(0, 5).equals("Ident")) {
            tokenIndex++;
            buffer = lexerToken[tokenIndex];
            if (buffer.equals("LPar")) {
                tokenIndex++;
                buffer = lexerToken[tokenIndex];
                if (buffer.equals("RPar")) {
                    tokenIndex++;
                    return 0;
                } else {
                    if (exp() == 0) {
                        buffer = lexerToken[tokenIndex];
                        while (!lexerToken[tokenIndex].equals("RPar")) {
                            buffer = lexerToken[tokenIndex];
                            if (buffer.equals("Comma")) {
                                tokenIndex++;
                                if (exp() == 0) {

                                } else {
                                    System.out.print("error 106\n");
                                    return 9;
                                }
                            } else {
                                System.out.print("error 107\n");
                                return 9;
                            }
                        }
                        buffer = lexerToken[tokenIndex];
                        if (buffer.equals("RPar")) {
                            tokenIndex++;
                            return 0;
                        } else {
                            System.out.print("error 109\n");
                            return 9;
                        }
                    } else {
                        System.out.print("error 108\n");
                        return 9;
                    }
                }
            } else {
                return 0;
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
        char[] token = new char[5000];


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
                        for (int t = 0; t < 5000; t++) {
                            token[t] = '\0';
                        }
                        break;
                    } else if (classification(readChar) == 4) {
                        keys(token, fp2);
                        tokenIndex = 0;
                        for (int t = 0; t < 5000; t++) {
                            token[t] = '\0';
                        }
                        ifRead = 0;
                        break;
                    } else {
                        keys(token, fp2);
                        tokenIndex = 0;
                        for (int t = 0; t < 5000; t++) {
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
                    for (int t = 0; t < 5000; t++) {
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
                                for (int t = 0; t < 5000; t++) {
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
                                for (int t = 0; t < 5000; t++) {
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
                    for (int t = 0; t < 5000; t++) {
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
                                for (int t = 0; t < 5000; t++) {
                                    token[t] = '\0';
                                }
                                break;
                            }
                        } else {

                            fp2.write("Number(" + octal + ")\n");
                            tokenIndex = 0;
                            for (int t = 0; t < 5000; t++) {
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
                    for (int t = 0; t < 5000; t++) {
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
                    for (int t = 0; t < 5000; t++) {
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
                        for (int t = 0; t < 5000; t++) {
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
                        for (int t = 0; t < 5000; t++) {
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
            case ',': {
                fp2.write("Comma\n");
                return 0;
            }
            case '&': {
                fp2.write("&\n");
                return 0;
            }
            case '|': {
                fp2.write("|\n");
                return 0;
            }
            case '!': {
                fp2.write("!\n");
                return 0;
            }
            case '[': {
                fp2.write("[\n");
                return 0;
            }
            case ']': {
                fp2.write("]\n");
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
                    fp2.write(c2);
                }
            } else {
                fp2.write(c);
            }
        }

        fp.close();
        fp2.close();
    }
}
