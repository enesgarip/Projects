
public class HeapSort {
	private long count;
    private long exchange;
    protected  String fileName;
	
    public int sort(int[] input)
    {
        int size = input.length;
        int median = input[size/2];

        buildHeap(input);

        for (int i=size-1; i>0; i--)
        {
            Inputs.swap(input, 0, i);
            heapify(input, i, 0);
            if(i == size/2) {
                median = input[i];
                return median;
            }
        }
        return median;
    }

    private void buildHeap(int[] input) {
        int size = input.length;
        for (int i = input.length / 2 - 1; i >= 0; i--){
            heapify(input, size, i);
            increaseCount();
        }

    }

    void heapify(int[] input, int size, int i)
    {
        int largest = i;
        int l = 2*i + 1;
        int r = 2*i + 2;

        if (l < size && input[l] > input[largest]) {
            largest = l;
            increaseCount();
        }

        if (r < size && input[r] > input[largest]){
            largest = r;
            increaseCount();
        }


        if (largest != i) {
            Inputs.swap(input, i, largest);

            heapify(input, size, largest);
            increaseCount();
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
