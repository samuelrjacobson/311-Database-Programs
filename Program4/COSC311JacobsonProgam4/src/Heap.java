//This file defines a heap to be used in HeapSort.
import java.io.*;

public class Heap {
	private Node[] heapArray;
	private int maxSize; // length of array
	private int currentSize; // number of elements filled
	
	// constructor
	public Heap(int max) {
		maxSize = max;
		currentSize = 0;
		heapArray = new Node[maxSize];
	}
	
	// removing a node 
	public Node remove() {
		Node root = heapArray[0];
		heapArray[0] = heapArray[--currentSize];
		trickleDown(0);
		return root;
	}
	
	// restore heap condition
	public void trickleDown(int index) {
		int largerChild;
		Node top = heapArray[index];
		while(index < currentSize/2) {
			int leftChild = 2 * index + 1;
			int rightChild = leftChild + 1;
			if(rightChild < currentSize && heapArray[leftChild].getData() < heapArray[rightChild].getData())
				largerChild = rightChild;
			else largerChild = leftChild;
		
			if(top.getData() >= heapArray[largerChild].getData()) break;
		
			heapArray[index] = heapArray[largerChild];
			index = largerChild;
		}
		heapArray[index] = top;
	}
	
	// write data to file
	public void displayArray(String outFile) {
		try {
			FileWriter heapFileWriter = new FileWriter(outFile);
		    PrintWriter heapPrintWriter = new PrintWriter(heapFileWriter);
		
			for(int i = 0; i < maxSize; i++) {
				heapPrintWriter.write(heapArray[i].getData() + "\n");
			}
			heapPrintWriter.close();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//insert nodes
	public void insertAt(int index, Node newNode) {
		heapArray[index] = newNode;
	}
	public void incrementSize() {
		currentSize++;
	}
}
