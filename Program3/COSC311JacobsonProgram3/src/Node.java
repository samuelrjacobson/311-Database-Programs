/* This class defines a node of a threaded binary search tree.
 */
public class Node {
	
	int where; // index in the DataBaseArray
	String data; // ID#, last name, or first name
	Node leftChild;
	Node rightChild;
	private boolean leftIsThread;
	private boolean rightIsThread;
	
	// constructors
	public Node() {
		data = "";
		where = -1;
		leftChild = null;
		rightChild = null;
		leftIsThread = false;
		rightIsThread = false;
	}
	public Node(String d, int w) {
		data = d;
		where = w;
		leftChild = null;
		rightChild = null;
		leftIsThread = false;
		rightIsThread = false;
	}
	
	// setters
	public void setLeftChild(Node n) {
		leftChild = n;
	}
	public void setRightChild(Node n) {
		rightChild = n;
	}
	
	// getters
	public Node getLeftChild() {
		return leftChild;
	}
	public Node getRightChild() {
		return rightChild;
	}
	public void setLeftIsThread(boolean isThread) {
		leftIsThread = isThread;
	}
	public void setRightIsThread(boolean isThread) {
		rightIsThread = isThread;
	}
	public boolean leftIsThread() {
		return leftIsThread;
	}
	public boolean rightIsThread() {
		return rightIsThread;
	}
	public String getData() {
		return data;
	}
	public int getWhere() {
		return where;
	}
	
	// compares keys alphabetically for sorting
	public int compareTo(Node otherNode) {
		return getData().compareTo(otherNode.getData());
	}
}
