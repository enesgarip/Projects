import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;

public class Verifier {

  static int nKnapsacks;
  static int nItems;
  static ArrayList<Integer> values = new ArrayList<Integer>();
  static ArrayList<Integer> capacities = new ArrayList<Integer>();
  static Map<Integer[], Integer> weights = new HashMap<Integer[], Integer>();
  static ArrayList<Integer[]> keySet = new ArrayList<Integer[]>();
  static int totalValue;

  public static void main(String[] args) {
      readSample(args[0]);
      int inc[] = readSolution(args[1]);
      checkSolution(inc);
  }

  public static void readSample(String fileName) {
    try {
      File sample = new File(fileName);
      int sum = 0;
      Scanner scanner = new Scanner(sample);
      
      //read # of knapsacks and items
      String line = scanner.nextLine();
      String nums[] = line.split(" ");
      nKnapsacks = Integer.parseInt(nums[0]);
      nItems = Integer.parseInt(nums[1]);

      //read item values
      while (sum < nItems) {
        line = scanner.nextLine();
        nums = line.split(" ");
        for (int i = 0; i < nums.length; i++) 
          values.add(Integer.parseInt(nums[i]));
        sum += nums.length;
      }
    
      //read knapsack capacities 
      sum = 0;
      while (sum < nKnapsacks) {
        line = scanner.nextLine();
        nums = line.split(" ");
        for(int i = 0; i < nums.length; i++) {
          capacities.add(Integer.parseInt(nums[i]));
          sum++;
        }
      }


      //read item weights
      for (int i = 0; i < nKnapsacks; i++) {
        sum = 0;
        while (sum < nItems) {
          line = scanner.nextLine();
          nums = line.split(" ");
          for (int j = 0; j < nums.length; j++) {
            Integer key[] = {i,(sum+j)};
            keySet.add(key);
            weights.put(key,Integer.parseInt(nums[j]));
          }
          sum += nums.length;
        }
      }

      scanner.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occured.");
      e.printStackTrace();
    }
  }

  public static int[] readSolution(String fileName) {
    int inc[] = new int[nItems];
    try {
      File solution = new File(fileName);
      Scanner scanner = new Scanner(solution);
      
      //read total value
      String line = scanner.nextLine();
      totalValue = Integer.parseInt(line);

      //read which values are included
      for (int i = 0; i < nItems; i++) {
        line = scanner.nextLine();
        inc[i] = Integer.parseInt(line);
      }
      
      scanner.close();
    } catch (FileNotFoundException e) {
      System.out.println("An error occured.");
      e.printStackTrace();
    }
    return inc;
  }

  public static void checkSolution (int[] solution) {
    int value = 0, weight;

    for (int i = 0; i < nKnapsacks; i++) {
      weight = 0;
      for (int j = 0; j < nItems; j++) {
        if (solution [j] == 1) {
          weight += weights.get(keySet.get(i*nItems + j));
          if(weight > capacities.get(i)) {
            System.out.println("The " + (i+1) + ". knapsack\'s capacity " + capacities.get(i) + " is exceeded!");
            System.exit(1);
          }
        }
      }
    }

    for (int i = 0; i < nItems; i++) {
      if(solution[i] == 1) value += values.get(i);
    }

    if (value != totalValue) System.out.println("The actual value is " + value + " but given as " + totalValue + ".");

  }
}