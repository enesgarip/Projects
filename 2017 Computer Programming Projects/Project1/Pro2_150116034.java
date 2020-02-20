/* The purpose of that program is to separate the money into units.
 * Enes GARÝP-150116034 */
import java.util.Scanner;

public class Pro2_150116034 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		System.out.print("Enter an amount in double; ");

		double amount = scanner.nextDouble();

		int remainingAmount=(int)(amount*100); //At that time the amount turn into kurus because of *100.

		/*While the money separation continuing:The amount divides by a number and then
		 * an integer number assigns to the variable.The remaining part is reassigned to the remaining and so on.*/

		int numberOf200TLs =(int) remainingAmount/20000;
		remainingAmount = remainingAmount%20000;

		int numberOf100TLs= (int)remainingAmount/10000;
		remainingAmount = remainingAmount%10000;

		int numberOf50TLs=(int)remainingAmount/5000;
		remainingAmount=remainingAmount%5000;

		int numberOf20TLs=(int)remainingAmount/2000;
		remainingAmount=remainingAmount%2000;

		int numberOf10TLs=(int)remainingAmount/1000;
		remainingAmount=remainingAmount%1000;

		int numberOf5TLs=(int)remainingAmount/500;
		remainingAmount=remainingAmount%500;

		int numberOf1TLs=(int)remainingAmount/100;
		remainingAmount=remainingAmount%100;

		int numberOf50Krs=(int)remainingAmount/50;
		remainingAmount=remainingAmount%50;

		int numberOf25Krs=(int)remainingAmount/25;
		remainingAmount=remainingAmount%25;

		int numberOf10Krs=(int)remainingAmount/10;
		remainingAmount=remainingAmount%10;

		int numberOf5Krs=(int)remainingAmount/5;
		remainingAmount=remainingAmount%5;

		int numberOf1Krs=(int)remainingAmount;

		System.out.println("Your amount "+ amount + " consist of");
		System.out.println("     " + numberOf200TLs + " 200Tls");
		System.out.println("     " + numberOf100TLs + " 100Tls");
		System.out.println("     " + numberOf50TLs + " 50TLs");
		System.out.println("     " + numberOf20TLs + " 20TLs");
		System.out.println("     " + numberOf10TLs + " 10TLs");
		System.out.println("     " + numberOf5TLs + " 5TLs");
		System.out.println("     " + numberOf1TLs + " 1TLs");
		System.out.println("     " + numberOf50Krs + " 50Krs");
		System.out.println("     " + numberOf25Krs + " 25Krs");
		System.out.println("     " + numberOf10Krs + " 10Krs");
		System.out.println("     " + numberOf5Krs + " 5Krs");
		System.out.println("     " + numberOf1Krs + " 1Krs");

	}

}
