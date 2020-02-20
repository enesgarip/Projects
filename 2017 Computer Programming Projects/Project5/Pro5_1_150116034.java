//Enes GARÝP-150116034
import java.util.Scanner;
public class Pro5_1_150116034 {

	public static void main(String[] args) {
		 Scanner input=new Scanner(System.in);
		 System.out.println("Hello Teacher!");
		 System.out.println("Please enter the answer key: ");
		 String key=input.nextLine();
		 System.out.println("Please enter the name of student: ");
		 String sName=input.nextLine();
		 System.out.println("Please enter the answers of "+sName+" :");
		 String sKey=input.nextLine();
		 char[] subject1={sKey.charAt(0),sKey.charAt(4),sKey.charAt(8),sKey.charAt(12),sKey.charAt(16),sKey.charAt(20),sKey.charAt(24),sKey.charAt(28),sKey.charAt(32),sKey.charAt(36)};
		 char[] subject2={sKey.charAt(1),sKey.charAt(5),sKey.charAt(9),sKey.charAt(13),sKey.charAt(17),sKey.charAt(21),sKey.charAt(25),sKey.charAt(29),sKey.charAt(33),sKey.charAt(37)};
		 char[] subject3={sKey.charAt(2),sKey.charAt(6),sKey.charAt(10),sKey.charAt(14),sKey.charAt(18),sKey.charAt(22),sKey.charAt(26),sKey.charAt(30),sKey.charAt(34),sKey.charAt(38)};
		 char[] subject4={sKey.charAt(3),sKey.charAt(7),sKey.charAt(11),sKey.charAt(15),sKey.charAt(19),sKey.charAt(23),sKey.charAt(27),sKey.charAt(31),sKey.charAt(35),sKey.charAt(39)};
		 checkAnswer(key,sKey);
		 percentage(subject1,sKey);
		 percentage(subject2,sKey);
		 /*percentage(subject3,sKey);
		 percentage(subject4,sKey);*/
	}
	public static void percentage(char[] ch,String str){
		for(int i =0;i<10;i++){
			int correct1=0;
			int correct2=0;
			int correct3=0;
			int correct4=0;
		if(ch[0]==str.charAt(0)||ch[1]==str.charAt(4)||ch[2]==str.charAt(8)||ch[3]==str.charAt(12)||ch[4]==str.charAt(16)||ch[5]==str.charAt(20)||ch[6]==str.charAt(24)||ch[7]==str.charAt(28)||ch[8]==str.charAt(32)||ch[9]==str.charAt(36)){
			correct1++;
			}
		else if(ch[1]==str.charAt(1)||ch[2]==str.charAt(5)||ch[3]==str.charAt(9)||ch[4]==str.charAt(13)||ch[5]==str.charAt(17)||ch[6]==str.charAt(21)||ch[7]==str.charAt(25)||ch[8]==str.charAt(29)||ch[9]==str.charAt(33)||ch[10]==str.charAt(37)){
			correct2++;
		}
		System.out.print("S1"+"\n---"+"\n"+correct1*10+"%"+"\nS2"+"\n---"+"\n"+correct2*10+"%");
		break;
		}





	}
	public static void checkAnswer(String answerKey,String studentKey){
		int correctNumber=0;
		for(int i=0;i<answerKey.length();i++){
			if(answerKey.charAt(i)==studentKey.charAt(i)){
			correctNumber++;
			}
			else{
				++i;
			}
		}
		System.out.println(correctNumber);
	}
	}


