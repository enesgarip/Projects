

public class BinaryInsertionSort {
    private long count;
    private long exchange;
    protected  String fileName;

    public void resetCount() {
        count = 0;
        exchange = 0;
    }

    public void sort(int[] input) {
        int length = input.length;
        for (int i = 1; i < length; ++i) {
            int key = input[i];
            int insertedPosition = findPosition(input, 0, i - 1, key);

            for (int j = i - 1; j >= insertedPosition; --j) {
                input[j + 1] = input[j];
                increaseCount();
            }
            increaseCount();
            input[insertedPosition] = key;
        }

    }
    public int findPosition(int[] nums, int start, int end, int key) {
        while (start <= end) {
            int mid = start + (end - start) / 2;
            increaseCount();
            if (key < nums[mid]) {
                end = mid - 1;
            } else {
                start = mid + 1;
            }
        }

        return start;
    }

    private void increaseCount() {
        count++;
    }

    public long getCount() {
        count = count + exchange;
        exchange = 0;
        return count;
    }
}