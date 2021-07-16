
public class MedianOfThreeSort {
	private long count;
    private long exchange;
    protected  String fileName;
	
    public int sort(int[] input) {
        quickSelect(input, 0, input.length-1, input.length/2);

        return input[input.length/2];
    }

    public int medianOfThree(int[] input, int left, int right) {
        int mid = (left + right) / 2;
        if(input[right] < input[left])
            Inputs.swap(input, left, right);
        if(input[mid] < input[left])
            Inputs.swap(input, left, mid);
        if(input[right] < input[mid])
            Inputs.swap(input, right, mid);
        return mid;
    }

    public int partition(int[] input, int left, int right, int pivotIndex) {
        int pivot = input[pivotIndex], pIndex = left, i;

        Inputs.swap(input, pivotIndex, right);

        for (i = left; i < right; i++)
        {
            increaseCount();
            if (input[i] <= pivot)
            {
                Inputs.swap(input, i, pIndex);
                pIndex++;
            }
        }

        Inputs.swap(input, pIndex, right);

        return pIndex;
    }

    public int quickSelect(int[] A, int left, int right, int k)
    {
        increaseCount();
        if (left == right) {
            return A[left];
        }

        // select a pivotIndex as median of three pivot selection
        int pivotIndex = medianOfThree(A, left, right);

        pivotIndex = partition(A, left, right, pivotIndex);

        increaseCount();
        if (k == pivotIndex) {
            return A[k];
        }

        else if (k < pivotIndex) {
            return quickSelect(A, left, pivotIndex - 1, k);
        }

        else {
            return quickSelect(A, pivotIndex + 1, right, k);
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
