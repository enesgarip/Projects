/*The purpose of the program is to find number of bits of an integer number.
 * Enes GARÝP
 * 150116034
 *
 */
import java.util.Scanner;
public class Pro3_1_150116034 {

	public static void main(String[] args) {
		boolean i=true;
		while(i){
			int k=0;	// a variable to know how many times the loop turns.
			int numberOfBits;
		Scanner input=new Scanner(System.in);
		System.out.print("Enter an integer number: ");
		int N=input.nextInt();
		if(N>0){
			while(N>0){
				N/=2;
				k++;
				if (N<1){
					System.out.println("The number of digits: "+k);
				}


				}
			}
		else if (N<0){
			System.out.println("The number of digits: Illegal input");
			i=true;
		}
		else if (N==0){
			System.out.print("Program ends. Bye");
			System.exit(1);
		}
		 }

	}

}
