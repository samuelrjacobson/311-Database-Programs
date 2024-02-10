/* This file reads int data from files, sorts the data, and outputs the sorted data to files.
 * It sorts using quickSort, mergeSort, and heapSort for each file, and outputs the sorting time for each.
 * For the purposes of this assignment, we're sorting identical data from three input files to test different cases:
 * dataAscend.txt	<- Data is already sorted in ascending order
 * dataDescend.txt	<- Data is in descending order
 * dataRandom.txt	<- Data is in random order */
import java.io.*;
import java.util.*;

public class COSC311Program4 {

	// declare arrays for data
	private static int[] array = new int[10000];
	private static int[] quickArray = new int[10000];
	private static int[] mergeArray = new int[10000];
	
	public static void main(String[] args) {
		
		// filenames to use in program
		String[] inputFiles = {"dataAscend.txt", "dataDescend.txt", "dataRandom.txt"};
		String[] outputFiles = {"ascending.txt", "descending.txt", "random.txt"};
		
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Enter an output filename: ");
		String outName = new String(keyboard.next()); // user chooses word to start each output file name with
		
		// read data from file and add to array
        try {
        	for(int j = 2; j >= 0 ; j--) {
        		
        		FileInputStream fiStream = new FileInputStream(inputFiles[j]);
        		Scanner fileReader = new Scanner(fiStream);
		
        		// read ints into array
        		for(int i = 0; i < array.length; i++) {
        			array[i] = fileReader.nextInt();
        		}
        	
        		// QuickSort
        		// copy values to new array so original won't be altered
        		for(int i = 0; i < array.length; i++) {
        			quickArray[i] = array[i];
        		}
        		
        		// perform quickSort and record time taken
        		long startTime = System.currentTimeMillis();
        		quickSort();
                long endTime = System.currentTimeMillis();
                long timeElapsed = (endTime - startTime);
                
                // display time of sort
                String outputString = outName + "_quick_" + outputFiles[j];
                System.out.print(outputString);
                if(outputString.length() % 8 != 0) System.out.print("\t");
                System.out.println("\tQuickSort took " + timeElapsed + " milliseconds.");
                
                // open file for writing
                FileWriter quickFileWriter = new FileWriter(outName + "_quick_" + outputFiles[j]);
    		    PrintWriter quickPrintWriter = new PrintWriter(quickFileWriter);
        	
    		    // write sorted data to file
        		for(int i = 0; i < quickArray.length; i++) {
        			quickFileWriter.write(quickArray[i] + "\n");
        		}
        		quickPrintWriter.close();
        		
        		
        		// MergeSort
        		// copy values to new array so original won't be altered
        		for(int i = 0; i < array.length; i++) {
        			mergeArray[i] = array[i];
        		}
        		
        		// perform mergeSort and record time taken
        		startTime = System.currentTimeMillis();
        		mergeSort();
        		
        		// open file for writing
                FileWriter mergeFileWriter = new FileWriter(outName + "_merge_" + outputFiles[j]);
    		    PrintWriter mergePrintWriter = new PrintWriter(mergeFileWriter);
    		    
        		// write sorted data to file
        		for(int i = 0; i < mergeArray.length; i++) {
        			mergePrintWriter.write(mergeArray[i] + "\n");
        		}
        		mergePrintWriter.close();
        		
        		// stop timer
                endTime = System.currentTimeMillis();
                timeElapsed = (endTime - startTime);
                
                // display time of sort and file-writing
                outputString = outName + "_merge_" + outputFiles[j];
                System.out.print(outputString);
                if(outputString.length() % 8 != 0) System.out.print("\t");
                System.out.println("\tMergeSort took " + timeElapsed + " milliseconds.");
 
        		
        		// HeapSort        		
        		// start timer and create a Heap
        		startTime = System.currentTimeMillis();
        		Heap heap = new Heap(array.length);
        		
        		// add values to heap
        		for(int i = 0; i < array.length; i++) {
        			Node newNode = new Node(array[i]);
        			heap.insertAt(i, newNode);
        			heap.incrementSize();
        		}
        		// heapify
        		for(int i = array.length/2 -1; i>= 0; i--) {
        			heap.trickleDown(i);
        		}
        		
        		// remove all nodes while writing data to file
        		for(int i = array.length - 1; i >= 0; i--) {
        			Node biggestNode = heap.remove();
        			heap.insertAt(i, biggestNode);
        		}
        		// stop timer
        		endTime = System.currentTimeMillis();
                timeElapsed = (endTime - startTime);
                
                // display time of sort
                outputString = outName + "_heap_" + outputFiles[j];
                System.out.print(outputString);
                if(outputString.length() % 8 != 0) System.out.print("\t");
                System.out.println("\tHeapSort took " + timeElapsed + " milliseconds.");
        		heap.displayArray(outName + "_heap_" + outputFiles[j]);
        	}
    	}
    	catch (FileNotFoundException e) {
    		e.printStackTrace();
    	}
        catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//QuickSort
	public static void quickSort() {
		recQuickSort(0, quickArray.length - 1);
	}
	public static void recQuickSort(int left, int right) {
		if(right - left <= 0) return;
		
		int pivot = quickArray[right];
		int partition = partitionIt(left, right, pivot);
		recQuickSort(left, partition - 1);
		recQuickSort(partition + 1, right);
	}
	public static int partitionIt(int left, int right, int pivot) {
		int leftPtr = left - 1;
		int rightPtr = right;
		while(true) {
			while(quickArray[++leftPtr] < pivot) {
				;
			}
			while(rightPtr > 0 && quickArray[--rightPtr] > pivot) {
				;
			}
			if(leftPtr >= rightPtr) break;
			else swap(leftPtr, rightPtr);
		}
		swap(leftPtr, right);
		return leftPtr;
	}
	public static void swap(int dex1, int dex2) {
		int temp = quickArray[dex1];
		quickArray[dex1] = quickArray[dex2];
		quickArray[dex2] = temp;
	}
	
	
	//MergeSort
	public static void mergeSort() {
		int[] workSpace = new int[mergeArray.length];
		recMergeSort(workSpace, 0, mergeArray.length - 1);
	}
	private static void recMergeSort(int[] workSpace, int lowerBound, int upperBound) {
		if(lowerBound == upperBound) return;
		else {
			int mid = (lowerBound + upperBound) / 2;
			recMergeSort(workSpace, lowerBound, mid);
			recMergeSort(workSpace, mid + 1, upperBound);
			merge(workSpace, lowerBound, mid + 1, upperBound);
		}
	}
	private static void merge(int[] workSpace, int lowPtr, int highPtr, int upperBound) {
		int i= 0;
		int lowerBound = lowPtr;
		int mid = highPtr - 1;
		int n = upperBound - lowerBound + 1; // number of elements
		
		while(lowPtr <= mid && highPtr <= upperBound) {
			if(mergeArray[lowPtr] < mergeArray[highPtr]) workSpace[i++] = mergeArray[lowPtr++];
			else workSpace[i++] = mergeArray[highPtr++];
		}
		while(lowPtr <= mid) workSpace[i++] = mergeArray[lowPtr++];
		
		while(highPtr <= upperBound) workSpace[i++] = mergeArray[highPtr++];
		
		for(i = 0; i < n; i++) {
			mergeArray[lowerBound + i] = workSpace[i];
		}
	}
}
