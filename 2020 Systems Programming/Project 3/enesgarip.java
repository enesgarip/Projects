// Enes Garip-150116034
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class enesgarip {
        // Variables for Hit,Miss and Eviction Counts and hit/miss check
        static int l1DhitCount;
        static int l1DmissCount;
        static int l1DevicCount;
        static int l1IhitCount;
        static int l1ImissCount;
        static int l1IevicCount;
        static int l2hitCount;
        static int l2missCount;
        static int l2evicCount;
        static int time;
        static boolean hitCheck;
        static boolean missCheck;

    public static void main(String[] args) {

        String strForTrace="",addressFromTrace="",size ="",data="",operation="";    //Trace file Variables
        if (args.length != 14) {
            System.out.println("Argument Error! Try Again...");
            System.exit(0);
        }                                                                           // Control for Argument Count
        int l1B, l2B;
        String traceFile;
        // Argument Values
        int l1s = Integer.parseInt(args[1]);
        int l1E = Integer.parseInt(args[3]);
        int l1b = Integer.parseInt(args[5]);
        int l2s = Integer.parseInt(args[7]);
        int l2E = Integer.parseInt(args[9]);
        int l2b = Integer.parseInt(args[11]);
        traceFile = args[13];


        StringBuilder strFromRam = new StringBuilder();
        File file = new File("C:\\Users\\Garip\\IdeaProjects\\enesgarip\\src\\ram.txt"); //Reading the Ram File
        try {
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                strFromRam.append(input.next());
            }
        } catch (Exception e) {
            System.out.println("Ram File error!..");
        }

        // Calculating B for L1 and L2 cache
        l1B = (int) Math.pow(2, l1b);
        l2B = (int) Math.pow(2, l2b);
        int x=0,y=0;
        String strFromRamStringVersion=strFromRam.toString();
        String ram[];
        ram = strFromRamStringVersion.trim().split("(?<=\\G.{2})"); // ram array holds 2 characters comes from ram.txt in each index respectively

        File trace = new File("C:\\Users\\Garip\\IdeaProjects\\enesgarip\\src\\"+traceFile);    // Reading Trace File
        try {
            Scanner input = new Scanner(trace);

            while(input.hasNextLine()){

                strForTrace=input.nextLine();
                operation=strForTrace.substring(0,1);               // Split for Operation
                addressFromTrace=strForTrace.substring(2,strForTrace.indexOf(","));         // Split for Address

                if(strForTrace.charAt(0) == 'I'||strForTrace.charAt(0) == 'L'){             // Split for Size
                    size=strForTrace.substring(strForTrace.indexOf(", ")+2);
                }

                if(strForTrace.charAt(0) == 'M'||strForTrace.charAt(0) == 'S'){             // Split for Size and Data for M and S operation
                    size=strForTrace.substring(strForTrace.indexOf(", ")+2,strForTrace.lastIndexOf(","));
                    data=strForTrace.substring(strForTrace.lastIndexOf(",")+2);
                }

                // Calling the functions for each operation
                if(operation.equals("I")){

                    loadInstruction();
                    loadCacheL2();
                }
                if(operation.equals("L")){

                    loadData();
                    loadCacheL2();
                }
                if(operation.equals("M")){

                    loadData();
                    loadCacheL2();
                    storeCache1();
                    storeCache2();
                }
                if(operation.equals("S")){

                    storeCache1();
                    storeCache2();
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Trace File error!..");
        }
        // Hit/Miss and Eviction counts
        System.out.println("L1I-hits:"+l1IhitCount+" L1I-misses:"+l1ImissCount+" L1I-evictions:"+l1IevicCount);
        System.out.println("L1D-hits:"+l1DhitCount+" L1D-misses:"+ l1DmissCount +" L1D-evictions:"+l1DevicCount);
        System.out.println("L2-hits:" +l2hitCount +" L2-misses:" +l2missCount+" L2-evictions:"+l2evicCount);
    }
        //Functions for each operation
        public static void loadData(){

        }
        public static void loadInstruction(){

        }
        public static void loadCacheL2(){

        }
        public static void storeCache1(){

        }
        public static void storeCache2(){

        }

}