import java.util.Arrays;

public class Main {
    @SuppressWarnings("unused")
	public static void main(String[] args) {
    	
        int[] input, tempInput;
        int size, median;
        String str, fileName;
        InsertionSort insertionSort = new InsertionSort();
        MergeSort mergeSort = new MergeSort();
        HeapSort heapSort = new HeapSort();
        MedianOfThreeSort medianOfThreeSort = new MedianOfThreeSort();
        QuickSort quickSort = new QuickSort();
        CountingSort countingSort=new CountingSort();
        BinaryInsertionSort binaryInsertionSort=new BinaryInsertionSort();
        for (int j = 0; j < 5; j++) {
        	size = 2;
        	
            if(j == 0)
                fileName = "_RandomOrdered.txt";
            else if(j == 1)
                fileName = "_ReverseOrdered.txt";
            else if( j == 2)
                fileName = "_Ordered.txt";
            else if(j == 3)
                fileName = "_50PercentOrdered.txt";
            else
                fileName = "_75PercentOrdered.txt";
            
            for(int i = 0; i < 14; i++) {
                size = size << 1;
                
                if(j == 0) 
                    input = Inputs.generateRandomInput(size);
                else if(j == 1)
                    input = Inputs.generateReverseOrderedInput(size);
                else if( j == 2)
                    input = Inputs.generateOrderedInput(size);
                else if(j == 3)
                    input = Inputs.generatePercentageOrderedInput(size, 50);
                else
                    input = Inputs.generatePercentageOrderedInput(size, 75);

                for (int k = 0; k < 7; k++) {
                    String prefix;
                    long timeComplexity;
                    tempInput = Arrays.copyOf(input, size);
                    // Insertion Sort
                    if(k == 0) {
                        insertionSort.resetCount();
                        median = insertionSort.sort(tempInput);
                        timeComplexity = insertionSort.getCount();
                        str = String.format("%d\t%d\n",size, timeComplexity);
                        prefix = "InsertionSort";
                    }
                    // Merge Sort
                    else if(k == 1) {
                        mergeSort.resetCount();
                        median = mergeSort.sort(tempInput);
                        timeComplexity = mergeSort.getCount();
                        str = String.format("%d\t%d\n",size, timeComplexity);
                        prefix = "MergeSort";
                    }
                    // Heap Sort
                    else if(k == 2){
                        heapSort.resetCount();
                        median = heapSort.sort(tempInput);
                        timeComplexity = heapSort.getCount();
                        str = String.format("%d\t%d\n",size, timeComplexity);
                        prefix = "HeapSort";
                    }
                    // Median of Three Sort
                    else if (k == 3){
                        medianOfThreeSort.resetCount();
                        median = medianOfThreeSort.sort(tempInput);
                        timeComplexity = medianOfThreeSort.getCount();
                        str = String.format("%d\t%d\n",size, timeComplexity);
                        prefix = "MedianOfThreeSort";
                    }
                    //  QuickSort
                    // https://stackoverflow.com/questions/44285883/implementing-quicksort-partition-algorithm-with-first-element-as-pivot
                    else if (k==4){
                        quickSort.resetCount();
                        quickSort.sort(tempInput,0,size-1);
                        timeComplexity = quickSort.getCount();
                        str = String.format("%d\t%d\n",size, timeComplexity);
                        prefix = "QuickSort";
                    }

                    // CountingSort
                    else if(k==5){
                        countingSort.resetCount();
                        countingSort.sort(tempInput);
                        timeComplexity = countingSort.getCount();
                        str = String.format("%d\t%d\n",size, timeComplexity);
                        prefix = "CountingSort";
                    }
                    // BinaryInsertionSort
                    else {
                        binaryInsertionSort.resetCount();
                        binaryInsertionSort.sort(tempInput);
                        timeComplexity = binaryInsertionSort.getCount();
                        str = String.format("%d\t%d\n",size, timeComplexity);
                        prefix = "BinaryInsertionSort";
                    }
                    Inputs.writeFile(prefix.concat(fileName), str);
                }
            }
        }
    }
}