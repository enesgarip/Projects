// CSE2138 Systems Programming PROJECT #1
// Enes Garip
// 150116034
// This is a program that converts the input file contains decimal and floating point numbers to hexadecimal notation.

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class enesgarip {
	// Global variables for for files and etc..
    static boolean isLittle=false;
    static int floatingPSize,fractionalPartSize,exponentNum;
    static File file=new File("C:/Users/Garip/Desktop/output.txt"); // Output path
    static FileWriter writer;

    static {
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static BufferedWriter bw=new BufferedWriter(writer);

    public static void main(String[] args) {

        File file = new File("C:\\Win7 backup\\CSE PROJECTS\\2020 Systems Programming\\Proeject 1\\input.txt"); //C:\Users\Garip\Desktop	// Input Path
      //  C:\Win7backup\Windows7 backup\CSE PROJECTS\2020 Systems Programming\Project 1
        System.out.print("Enter the byte ordering type(default is big endian): ");
        Scanner endian=new Scanner(System.in);
        String order=endian.nextLine();
        if(order.toLowerCase().contains("little")){
            isLittle=true;
        }
        System.out.print("Enter the size of the floating point data type (1,2,3 or 4): ");
        Scanner floatSize=new Scanner(System.in);
        floatingPSize= floatSize.nextInt();
        System.out.println("...");
        try (Scanner sc = new Scanner(file, StandardCharsets.UTF_8.name())) {
            while (sc.hasNextLine()){
                String s= sc.nextLine();
                whichNum(s);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        try {
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File is ready.");
    }
    				// The numbers categorized in this section.
    public static void whichNum(String str){
        if(str.contains(".")){
          //  System.out.println("float");
            forFraction(str);
        }
        else if(str.contains("u")){
           // System.out.println("unsigned int");
            unsignedConv(str);
        }
        else {
           // System.out.println("signed int");
            forSigned(str);
        }

    }
    		// For signed integers that function determines that the number is positive or negative. If the number positive the boolean isNegative value is
    // equals to the false and if the number is positive vice versa.
    public static void forSigned(String str){
        int x,y;
        boolean isNegative=false;
        int[] arr= new int[16];
        x=Integer.parseInt(str);
        if(x>0){
            signedConv(x,isNegative);
        }
        else if(x<0){
            isNegative=true;
            signedConv(x,isNegative);
        }
        else{
            signedConv(x,isNegative);
        }
    }
    			//Signed Conversion Part
    public static void signedConv(int k, boolean value){
        int i=0, x,remainder,iter=0;
        int[] arr=new int[16];
        System.out.print("\n");
        x=k;
        	//If the number is positive...
        if(value==false) {
            do{
                remainder=x%2;
                arr[i++]=remainder;
                x=x/2;
            }while(x > 0);

            hexConv(arr);
        }
        //if the number is negative.
        else{

            x=x*-1;

           /* realx=x;
            base=log2(realx);
            System.out.println(x+" base: "+base);*/

               do{
                    remainder=x%2;
                    arr[i++]=remainder;
                    x=x/2;
                }while(x > 0);
               for (int l=0;l<16;l++){
                    if(arr[l]==0){
                        arr[l]=1;
                    }
                    else{
                        arr[l]=0;
                    }
               }
               if(arr[0]==0){
                    arr[0]=1;
               }
               else{
                    for (int l = 0; l <16 ; l++) {
                        if (arr[l]==0){
                            iter=l;
                            break;
                        }
                    }

                    for(int c=0;c<iter;c++){
                        arr[c]=0;
                    }
                    arr[iter]=1;
               }


            hexConv(arr);


        }
    }
    	// Unsigned conversion part
    public static void unsignedConv(String str){
        int i=0,remainder,x;
        String str2;
        int[] arr=new int[16];
        str2=str.substring(0,str.length()-1);
        x=Integer.parseInt(str2);
        do{
            remainder=x%2;
            arr[i++]=remainder;
            x=x/2;
        }while(x > 0);

        hexConv(arr);

    }
    /*public static int log2(int x)
    {

        return (int) (Math.log(x) / Math.log(2)+1e-10);
    }*/
    		//Fraction part
    public static void forFraction(String str){
        int dotIndex,beforeD,afterD,dotIndexBinary,signBit;
        String beforeDot,afterDot;
       // Determining the floating point values..
        if(floatingPSize==1){
            fractionalPartSize=3;
            exponentNum=4;

        }
        else if(floatingPSize==2){
            fractionalPartSize=9;
            exponentNum=6;
        }
        else if(floatingPSize==3){
            fractionalPartSize=15;
            exponentNum=8;
        }
        else if(floatingPSize==4){
            fractionalPartSize=21;
            exponentNum=10;

        }
        else{
            System.out.println("Incorrect floating point size!!");
            }

        dotIndex=str.indexOf(".");
        	//if floating point number is negative..
        if(str.contains("-")){
            signBit=1;

            beforeDot=str.substring(1,dotIndex);
            afterDot=str.substring(dotIndex+1);
            String beforeDotBinary = "";
            beforeD=Integer.parseInt(beforeDot);
            double strToNum=Double.parseDouble(str);
            int number=(int) strToNum;
            double fractional=strToNum-number;

            while ( beforeD> 0)
            {
                beforeDotBinary =  ( (beforeD % 2 ) == 0 ? "0" : "1") + beforeDotBinary;
                beforeD = beforeD / 2;
            }
            afterD=Integer.parseInt(afterDot);

            int k_prec=32;

            String fractionalBinary="";
            while (k_prec-- > 0)
            {
                // Find next bit in fraction
                fractional *= 2;
                int fract_bit = (int) fractional;

                if (fract_bit == 1)
                {
                    fractional -= fract_bit;
                    fractionalBinary += (char)(1 + '0');
                }
                else
                {
                    fractionalBinary += (char)(0 + '0');
                }
            }

            String midBinary=beforeDotBinary+"."+fractionalBinary;
            dotIndexBinary=midBinary.indexOf(".");
            String mantissa=beforeDotBinary.charAt(0)+"."+beforeDotBinary.substring(1)+fractionalBinary;

            int exponent=dotIndexBinary-1;


            String temp=mantissa.substring(2);

            while(temp.length() != fractionalPartSize) {
                int x = temp.length() - 1;
                String value = "1";
                temp = sum(temp,value);
                temp = temp.substring(0,x);

            }

            int bias=(int)Math.pow(2,exponentNum-1)-1;

            int exp=exponent+bias;
            String expBinary = "";
            while ( exp> 0)
            {
                expBinary =  ( (exp % 2 ) == 0 ? "0" : "1") + expBinary;
                exp = exp / 2;
            }


            if(floatingPSize==3){
                while(temp.length() != 13) {
                    int x = temp.length() - 1;
                    String value = "1";
                    temp = sum(temp,value);
                    temp = temp.substring(0,x);

                }
                while(temp.length()!=15){
                    temp=temp+"0";
                }
            }
            if(floatingPSize==4){
                while(temp.length() != 13) {
                    int x = temp.length() - 1;
                    String value = "1";
                    temp = sum(temp,value);
                    temp = temp.substring(0,x);

                }
                while(temp.length()!=21){
                    temp=temp+"0";
                }
            }
            String finalBinaryTheLegend=signBit+expBinary+temp+"";

            String hexaOfFloat=hexadecimalStringFloat(finalBinaryTheLegend,floatingPSize);

            try {
                bw.write(hexaOfFloat+ "\n");
                bw.newLine();
                bw.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        //if floating point number is positive..
        else{
            signBit=0;


            beforeDot=str.substring(0,dotIndex);
            afterDot=str.substring(dotIndex+1,str.length());
            String beforeDotBinary = "";
            beforeD=Integer.parseInt(beforeDot);
            double strToNum=Double.parseDouble(str);
            int number=(int) strToNum;
            double fractional=strToNum-number;

            while ( beforeD> 0)
            {
                beforeDotBinary =  ( (beforeD % 2 ) == 0 ? "0" : "1") + beforeDotBinary;
                beforeD = beforeD / 2;
            }


            afterD=Integer.parseInt(afterDot);

            int k_prec=32;
            String fractionalBinary="";
            while (k_prec-- > 0)
            {
                // Find next bit in fraction
                fractional *= 2;
                int fract_bit = (int) fractional;

                if (fract_bit == 1)
                {
                    fractional -= fract_bit;
                    fractionalBinary += (char)(1 + '0');
                }
                else
                {
                    fractionalBinary += (char)(0 + '0');
                }
            }

            String midBinary=beforeDotBinary+"."+fractionalBinary;

            dotIndexBinary=midBinary.indexOf(".");

            String mantissa=beforeDotBinary.charAt(0)+"."+beforeDotBinary.substring(1)+fractionalBinary;

            int exponent=dotIndexBinary-1;


            String temp=mantissa.substring(2);

            while(temp.length() != fractionalPartSize) {
                int x = temp.length() - 1;
                String value = "1";
                temp = sum(temp,value);
                temp = temp.substring(0,x);

            }

            int bias=(int)Math.pow(2,(exponentNum-1))-1;

            int exp=exponent+bias;
            String expBinary = "";
            while ( exp> 0)
            {
                expBinary =  ( (exp % 2 ) == 0 ? "0" : "1") + expBinary;
                exp = exp / 2;
            }


            if(floatingPSize==3){
                while(temp.length() != 13) {
                    int x = temp.length() - 1;
                    String value = "1";
                    temp = sum(temp,value);
                    temp = temp.substring(0,x);

                }
                while(temp.length()!=15){
                    temp=temp+"0";
                }
            }
            if(floatingPSize==4){
                while(temp.length() != 13) {
                    int x = temp.length() - 1;
                    String value = "1";
                    temp = sum(temp,value);
                    temp = temp.substring(0,x);

                }
                while(temp.length()!=21){
                    temp=temp+"0";
                }
            }

            String finalBinaryTheLegend=signBit+expBinary+temp+"";

            String hexaOfFloat=hexadecimalStringFloat(finalBinaryTheLegend,floatingPSize);

            try {
                bw.write(hexaOfFloat+ "\n");
                bw.newLine();
                bw.flush();
                } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    // For add +1 to the binary number.
    public static String sum(String b1, String b2) {
        int len1 = b1.length();
        int len2 = b2.length();
        int carry = 0;
        String res = "";

        int maxLen = Math.max(len1, len2);
        for (int i = 0; i < maxLen; i++) {

            int p = i < len1 ? b1.charAt(len1 - 1 - i) - '0' : 0;
            int q = i < len2 ? b2.charAt(len2 - 1 - i) - '0' : 0;
            int tmp = p + q + carry;
            carry = tmp / 2;
            res = tmp % 2 + res;
        }
        return (carry == 0) ? res : "1" + res;
    }
    //for determine the precision.
    public static int prec (int floSize){
        int x=0;
        if(floSize==1){
            x=3;
        }
        if(floSize==2){
            x=9;
        }
        if(floSize==3){
            x=15;
        }
        if(floSize==4){
            x=21;
        }
        return x;
    }
    	//Conversion to hex for unsigned and signed numbers.
    public static void hexConv(int arr[]) {

        String str1,str2,str3,str4;
        str1=""+arr[15]+arr[14]+arr[13]+arr[12];
        str2=""+arr[11]+arr[10]+arr[9]+arr[8];
        str3=""+arr[7]+arr[6]+arr[5]+arr[4];
        str4=""+arr[3]+arr[2]+arr[1]+arr[0];


        try {
            if(!isLittle) {
                bw.write(forHex(str1) + forHex(str2) + " " + forHex(str3) + forHex(str4) + "\n");
                bw.newLine();
                bw.flush();
            }
            else{
                bw.write(forHex(str3) + forHex(str4) + " " + forHex(str1) + forHex(str2) + "\n");
                bw.newLine();
                bw.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    	// Switch for hex
    public static String forHex(String str){
        String hex;
        switch(str){
            case "0000": hex="0";break;
            case "0001": hex="1";break;
            case "0010": hex="2";break;
            case "0011": hex="3";break;
            case "0100": hex="4";break;
            case "0101": hex="5";break;
            case "0110": hex="6";break;
            case "0111": hex="7";break;
            case "1000": hex="8";break;
            case "1001": hex="9";break;
            case "1010": hex="A";break;
            case "1011": hex="B";break;
            case "1100": hex="C";break;
            case "1101": hex="D";break;
            case "1110": hex="E";break;
            case "1111": hex="F";break;
            default:
                throw new IllegalStateException("Unexpected value");
        }
        return hex;
    }
    		// Conversion to hex for floating point numbers
    public static String hexadecimalStringFloat (String input,int k) {
        k=k*8;
        StringBuilder num = new StringBuilder();
        String str="";
        for(int i = 0; i < k; i +=4 ) {
            str = input.substring(i, i + 4);
            if(i%8==0){
                num.append(" ");
            }
            switch(str)
            {
                case "0000" : num.append("0"); break;
                case "0001" : num.append("1"); break;
                case "0010" : num.append("2"); break;
                case "0011" : num.append("3"); break;
                case "0100" : num.append("4"); break;
                case "0101" : num.append("5"); break;
                case "0110" : num.append("6"); break;
                case "0111" : num.append("7"); break;
                case "1000" : num.append("8"); break;
                case "1001" : num.append("9"); break;
                case "1010" : num.append("A"); break;
                case "1011" : num.append("B"); break;
                case "1100" : num.append("C"); break;
                case "1101" : num.append("D"); break;
                case "1110" : num.append("E"); break;
                case "1111" : num.append("F"); break;

            }

        }
        String[] arr=num.toString().split(" ");
        String str2="";
        if (!isLittle){

            return num.toString();
        }else{

            if(floatingPSize==1){
                str2=arr[1];
            }
            if(floatingPSize==2){
                str2=arr[2]+" "+arr[1];
            }
            if(floatingPSize==3){
                str2=arr[3]+" "+arr[2]+" "+arr[1];
            }
            if(floatingPSize==4){
                str2=arr[4]+" "+arr[3]+" "+arr[2]+" "+arr[1];
            }
        }
        return str2;
    }

}