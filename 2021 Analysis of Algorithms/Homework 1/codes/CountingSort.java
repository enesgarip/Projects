public class CountingSort {
    private long count;
    private long exchange;
    protected String fileName;

    public void sort(int[] input) {
        int arrayLength = input.length;
        if (arrayLength == 0)
            return;
        /** find maximum and minimum values **/
        int max = input[0], min = input[0];

        for (int i = 1; i < arrayLength; i++) {
            increaseCount();
            if (input[i] > max)
                max = input[i];

            if (input[i] < min)
                min = input[i];
        }
        int range = max - min + 1;
        int[] countedArray = new int[range];
        /** initialize the occurrence of each element in the count array **/
        for (int i = 0; i < arrayLength; i++) {
            countedArray[input[i] - min]++;
        }
        /** calculate sum of indexes **/
        for (int i = 1; i < range; i++) {
            countedArray[i] += countedArray[i - 1];
        }
            /** modify original array according to the sum count **/
        int j = 0;
        for (int i = 0; i < range; i++) {
            while (j < countedArray[i]) {
                input[j++] = i + min;
                increaseCount();
            }
        }
    }

    private void increaseCount() {
        count++;
    }


    public void resetCount() {
        count = 0;
        exchange = 0;
    }

    public long getCount() {
        count = count + exchange;
        exchange = 0;
        return count;
    }
}