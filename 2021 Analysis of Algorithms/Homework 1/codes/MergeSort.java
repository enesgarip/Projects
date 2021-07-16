import java.util.Arrays;

public class MergeSort {
	private long count;
    private long exchange;
    protected  String fileName;

    public int sort(int[] input) {
        int size = input.length, median = 0;
        int[] left, right;
        
        if (size > 1) {

            left = Arrays.copyOf(input, size / 2 );
            right = Arrays.copyOfRange(input, size / 2 , size);
            sort(left);
            increaseCount();
            sort(right);
            increaseCount();
            merge(left, right, input);
        }
        
        if(size != 0) {
            median = input[size/2];
        }
        
        return median;
    }

    private void merge(int[] left, int[] right, int[] input) {
        int i = 0;
        int j = 0;
        int k = 0;

        int leftSize = left.length;
        int rightSize = right.length;

        while (i < leftSize && j < rightSize) {
            if (left[i] <= right[j]){
                input[k] = left[i];
                i++;
            } else {
                input[k] = right[j];
                j++;
            }
            increaseCount();
            k++;
        }

        if(i == leftSize) {
            for(int x = j; x < rightSize ; x++) {
                input[k] = right[x];
                k++;
                increaseCount();
            }
        } else {
            for(int x = i; x < leftSize; x++) {
                input[k] = left[x];
                k++;
                increaseCount();
            }
        }
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
