public class QuickSort {
    private long count;
    private long exchange;
    protected  String fileName;

    public void resetCount() {
        count = 0;
        exchange = 0;
    }

    public void sort(int[] input,int beginning,int end) {
        int index;
        increaseCount();
        if (beginning>end){
            return;
        }
        index=partition(input,beginning,end);
        sort(input,beginning,index-1);
        sort(input,index+1,end);

    }

    private int partition(int[] input, int beginning, int end) {
        int pivot = input[beginning];
        int i = beginning;
        int j=end;
        while(i<j){
            while (input[i]<pivot){
                i++;
            }
            while (input[j]>pivot){
                j--;
            }
            if (i < j){
                if(input[i] == input[j]){
                    i++;
                }
                swap(input,i,j);
            }
            increaseCount();
        }
//        swap(input,beginning,j);
        return j;
    }

    private void increaseCount() {
        count++;
    }

    private void swap(int[] list, int i, int j) {
        int temp = list[i];
        list[i] = list[j];
        list[j] = temp;
        increaseCount();
    }

    public long getCount() {
        count = count + exchange;
        exchange = 0;
        return count;
    }
}