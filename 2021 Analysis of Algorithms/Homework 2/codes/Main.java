import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/* Abbas Göktuğ Yılmaz - 150115061
 * Enes Garip - 150116034
 * Veysi Öz - 150116005
 */

public class Main {
	public static void main(String[] args) {
		try {
			findBestKnapsack("test1.txt", "output1.txt");
			findBestKnapsack("test2.txt", "output2.txt");
			findBestKnapsack("test3.txt", "output3.txt");
			findBestKnapsack("test4.txt", "output4.txt");
		} catch (IOException e) {
			System.err.println("File Error!");
		}
	}

	static void findBestKnapsack(String inputName, String outputName) throws IOException {
		Scanner scanner = new Scanner(new File(inputName));
		FileWriter writer = new FileWriter(outputName);
		String firstLine = scanner.nextLine();
		String[] splitted = firstLine.split("\s");
		int m = Integer.parseInt(splitted[0]);
		int n = Integer.parseInt(splitted[1]);

		ArrayList<Integer> v = vValues(n, scanner);
		ArrayList<Integer> v2 = knapsackCapacities(m, scanner);
		ArrayList<Integer> v3 = weights(scanner);
		ArrayList<Integer[]> whichMax = new ArrayList<Integer[]>();

		Integer[] values = v.toArray(new Integer[0]);

		for (int i = 0; i < v2.size(); i++) {
			Integer[] weights = v3.subList(i * v.size(), (i + 1) * v.size()).toArray(new Integer[0]);
			whichMax.add(KnapSack(v2.get(i), weights, values, n));
		}

		Integer[] max = new Integer[n + 1];
		max[0] = 0;
		for (int i = 0; i < whichMax.size(); i++) {
			if (whichMax.get(i)[0] > max[0]) {
				max = whichMax.get(i);
			}
		}

		writer.write(max[0].toString() + "\n");
		for (int i = 1; i < n + 1; i++) {
			writer.write(max[i].toString() + "\n");
		}
		writer.close();
	}

	static ArrayList<Integer> vValues(int n, Scanner scanner) {
		ArrayList<Integer> v = new ArrayList<>();
		int iter = n / 10;

		if (n % 10 > 0) {
			iter++;
		}
		for (int i = 0; i < iter; i++) {
			String line = scanner.nextLine();
			String[] str = line.split("\s");
			for (String s : str) {
				if (!s.equals("")) {
					v.add(Integer.parseInt(s));
				}
			}
		}
		return v;
	}

	static ArrayList<Integer> knapsackCapacities(int m, Scanner scanner) {
		ArrayList<Integer> v = new ArrayList<>();
		int iter = m / 10;
		if (m % 10 > 0) {
			iter++;
		}
		for (int i = 0; i < iter; i++) {
			String line = scanner.nextLine();
			String[] str = line.split("\s");
			for (String s : str) {
				if (!s.equals("")) {
					v.add(Integer.parseInt(s));
				}
			}
		}
		return v;
	}

	static ArrayList<Integer> weights(Scanner scanner) {
		ArrayList<Integer> v = new ArrayList<>();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] str = line.split("\s");
			for (String s : str) {
				if (!s.equals("")) {
					v.add(Integer.parseInt(s));
				}
			}
		}
		return v;
	}

	static Integer[] KnapSack(int capacity, Integer[] wt, Integer[] val, int n) {
		double[][] arr = new double[n][5];

		for (int i = 0; i < n; i++) {
			arr[i][0] = val[i];
			arr[i][1] = wt[i];
			arr[i][2] = 0;
			arr[i][3] = i;
			arr[i][4] = 0;
		}

		for (int i = 0; i < arr.length; i++) {
			double value = 0, weigth = 0;
			value = arr[i][0];
			weigth = arr[i][1];
			if (weigth == 0)
				arr[i][2] = 0;
			else
				arr[i][2] = value / (weigth * 1.0);
		}

		double sum = 0, valueSum = 0;
		while (true) {
			double[] temp = new double[4];
			temp = Max(arr);

			if (sum + temp[1] > capacity)
				break;
			else
				sum += temp[1];

			for (int i = 0; i < arr.length; i++) {
				if (arr[i][3] == temp[3])
					arr[i][4] = 1;
			}

			valueSum += temp[0];
		}

		Integer[] result = new Integer[n + 1];
		result[0] = (int) valueSum;

		for (int i = 1; i < n + 1; i++) {
			result[i] = (int) arr[i - 1][4];
		}

		return result;
	}

	public static double[] Max(double[][] arr) {
		double[] temp = new double[5];
		double[] max = { 0, 0, 0, 0, 0 };

		for (int i = 0; i < arr.length; i++) {
			temp[0] = arr[i][0];
			temp[1] = arr[i][1];
			temp[2] = arr[i][2];
			temp[3] = arr[i][3];
			temp[4] = arr[i][4];

			if (temp[2] >= max[2]) {
				max[0] = temp[0];
				max[1] = temp[1];
				max[2] = temp[2];
				max[3] = temp[3];
				max[4] = temp[4];
			}
		}

		for (int i = 0; i < arr.length; i++) {
			if (arr[i][3] == max[3]) {
				arr[i][2] = 0;
			}
		}
		return max;
	}
}
