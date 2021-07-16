
public class InsertionSort {
	private long count;
    private long exchange;
    protected  String fileName;

    public int sort(int[] input) {
        for (int i = 1; i < input.length; i++) {
            int key = input[i];
            int j = i - 1;

            while (j >= 0 && input[j] > key) {
                increaseCount();
                input[j+1] = input[j];
                j--;
            }
            increaseCount();
            input[j+1] = key;
        }
        int medianPosition = input.length / 2;

        return input[medianPosition];
    }

    public void increaseCount() {
        count++;
    }

    public void increaseCount(long c) {
        count = count + c;
    }

    public long getCount() {
        count = count + exchange;
        exchange = 0;
        return count;
    }

    public void resetCount() {
        count = 0;
        exchange = 0;
    }

    public void increaseExchange() {
        exchange++;
    }
}
