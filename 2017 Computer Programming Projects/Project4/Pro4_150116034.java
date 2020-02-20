/*The purpose of that program is changes the case of letters, calculates the number of vowels and consonants
 * capitalizes the first letter of each word or encrypt/ decrypt the input based on user's selection.
 *
 *
 *
 * Enes GARÝP 150116034
 *
 *
 */
import java.util.Scanner;
public class Pro4_150116034 {

	public static void main(String[] args) {
		int totalWords=0;
		int totalLetters=0;
		while(true){									//It is an infinite loop for taking input while user writes "exit".
		Scanner input =new Scanner(System.in);
		System.out.print("Please enter an input string: ");
		String string=input.nextLine();
		updateLetterCount(totalLetters,string);
		if(string.equals("stat")){
			printStat(totalWords,totalLetters);

		}
		if(string.equals("exit")||string.equals("quit")){		//It is the logic of exit or quit.
			System.out.println("Program ends. Bye");
			System.exit(1);
		}
		else{
		System.out.println("1.Change Case");
		System.out.println("2.Count vowels and consonants");
		System.out.println("3.Capitalize the first letter");
		System.out.println("4.Encrypt or Decrypt");
		System.out.print("\nPlease select an option: ");
		int optionNum=input.nextInt();				//That optionNum variable holds the number of the selection that user has selected.
		if(optionNum==1){
			changeCase(string);						//Calling the changeCase method.
		}
		else if (optionNum==2){
			countVC(string);								//Calling the countVC method.
		}
		else if (optionNum==3){
			capitalize(string);							//Calling the capitalize method.
		}
		else if (optionNum==4){
			System.out.print("Enter an offset value: ");		//It encodes or decodes input string based on offset.
			int offset=input.nextInt();
			encryptOrDecrypt(string,offset);
		}
		}

		}
	}
	public static String changeCase(String str){ 				//It turns the case of letters upper to lower or vice versa.
		for(int i=0;i<str.length();i++){						//It scan all the letters.
			if(Character.isUpperCase(str.charAt(i))){

				System.out.print(Character.toLowerCase(str.charAt(i)));			//Turn to lower case the letter at i.
			}
			else{

				System.out.print(Character.toUpperCase(str.charAt(i)));			//Turn to upper case the letter at i.
			}

		}
		System.out.println("");
	return str;
	}
	public static void countVC(String str){				//It counts the vowels and consonants.
		int countConsonants=0;
		int countVowels=0;

		for(int i=0;i<str.length();i++){				//It scans the input.
			char x=Character.toLowerCase(str.charAt(i));		//Turns all letters to lower case.
			if(x=='a'||x=='e'||x=='i'||x=='o'||x=='u'){			//Test the input.
				countVowels++;
			}
			else if(x> 'a'&& x<='z'){
				countConsonants++;
			}



		}
		System.out.println("The number of vowels is "+countVowels);
		System.out.println("The number of consonants is "+countConsonants);

}
	public static String capitalize(String str){					//It capitalize the first letter of words.
		str=" "+str;												//It provides the first word's first letter to upper case.
		String newLetter="";
		for(int i=0;i<str.length();i++){
			char space=str.charAt(i);
			if(space==' '){
				newLetter=newLetter+" ";
				char firstLetter=str.charAt(i+1);
				newLetter=newLetter + Character.toUpperCase(firstLetter);
				i++;
			}
			else{
				newLetter=newLetter+space;
			}
		}
		System.out.println(newLetter.trim());
		return str;
}
	public static String encryptOrDecrypt(String str,int offset){				//It replace all the letters related to the offset value.
		String source =str.toUpperCase();
		int testNum=offset;
		if ((testNum<=-25 || testNum>-1)&&(testNum>=25 || testNum<1)){
			System.out.println("Invalid offset. ");
		}
		else{
			System.out.println("Source: "+source);
			System.out.print("Processed: ");
			for (int i=0;i<source.length();i++){
			int x=source.charAt(i);
			int y=x+offset;
			if(str.charAt(i)!=' '&& Character.isLetter(source.charAt(i))){
				System.out.print((char)y);

			}
			else{
				System.out.print(source.charAt(i));

			}
		}
			System.out.println("");
		}
		return source;

	}
	public static int updateLetterCount(int countL,String str){
		for(int i=0;i<str.length();i++){
			if(Character.isLetter(str.charAt(i))){
				countL++;

			}


		}
		return countL;

	}
	public static void printStat(int wordCount,int letterCount){
		System.out.println("The number of words: "+wordCount);
		System.out.println("The number of alphabetic letters: "+letterCount);
	}

}
