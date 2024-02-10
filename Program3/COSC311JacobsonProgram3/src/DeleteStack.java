/* When a DataBaseRecord is deleted, its index is pushed onto the DeleteStack
 * so the index can be reused when a new record is added. */

public class DeleteStack {

	private int [] data;
	private int next; //refers to the next index to be reused

	// constructors
	public DeleteStack ( ){     
		data = new int[100];       
		next = 0;                     
	}
	public DeleteStack (int sz){
		data = new int[sz];
	    next = 0;
	}

	// We will check whether DeleteStack is empty before popping.
	public boolean isEmpty( ){
		return (next == 0);
	}

	// We will check that DeleteStack isn't full before pushing.
	public boolean isFull(){
		return (next == data.length);     
	}

	// add a value to stack
	public void push(int pushVal){
		data[next++] = pushVal;
	}

	// remove top value from stack
	public int pop(){
		return(data[--next]);
	}
}