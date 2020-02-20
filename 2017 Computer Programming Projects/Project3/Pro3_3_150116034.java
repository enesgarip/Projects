/*The purpose of that program is showing the user input as a pattern.
 * Enes GARÝP
 * 150116034
 */
import java.util.Scanner;
public class Pro3_3_150116034 {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter an input string");
		int a;
		int b;
		int x;
		String string=input.next(); //input command
		char ch=string.charAt(0);	//to use for reverse the input
		for (a=0; a<=string.length()-1;a++){
			for(b=0;b<=string.length()-1;b++){
				System.out.print(b<string.length()-a ? string.charAt(b) : " ");
			}
			for (x=string.length()-2;x>-1;x--)
			{
				System.out.print(x<=(string.length()-1)-a ? string.charAt(x): " " );
				}
			for(b=1;b<=string.length()-1;b++)
			{
				System.out.print(b<string.length()-a ? string.charAt(b) : " ");
			}
			for (x=string.length()-2;x>-1;x--)
			{
				System.out.print(x<=(string.length()-1)-a ? string.charAt(x): " " );
		}
		System.out.println();
		}


			}



	}




