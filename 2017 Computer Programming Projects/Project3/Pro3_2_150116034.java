/*The purpose of the program is learn the value at position which user enters.
 * Enes GARÝP
 * 150116034
 *
 */
import java.util.Scanner;
public class Pro3_2_150116034 {

	public static void main(String[] args) {
		System.out.println("Enter an integer number: ");
		Scanner input = new Scanner(System.in);
		int position =input.nextInt();
		int value1=0;
		int value2=1;
		int newValue=0,a=3;
		if (position==1){
			System.out.print("In position "+position+","+" the value is "+value1);

		}
		else if (position==2){
			System.out.print("In position "+position+","+" the value is "+value2);

		}
		else if (position<0){
			System.out.println("Illegal input");
			System.exit(1);
		}

		else if(position>2){
			while(position>=a){

			value2=4*value2-value1;
			value1=(value2+value1)/4;	//every time loop turns the value1 updated to it's next value.
			a++;

			}
			newValue=value2;
			}
			System.out.println("In position "+position+","+" the value is "+newValue);


	}

}
