
public class Node {
	
	int where; // index in the DataBaseArray
	String data; // ID#, last name, or first name
	Node leftChild;
	Node rightChild;
	
	//constructors
	public Node() {
		data = "";
		where = -1;
		leftChild = null;
		rightChild = null;
	}
	public Node(String d, int w) {
		data = d;
		where = w;
		leftChild = null;
		rightChild = null;
	}
	
	//setters
	public void setLeftChild(Node n) {
		leftChild = n;
	}
	public void setRightChild(Node n) {
		rightChild = n;
	}
	
	//getters
	public Node getLeftChild() {
		return leftChild;
	}
	public Node getRightChild() {
		return rightChild;
	}
	public String getData() {
		return data;
	}
	public int getWhere() {
		return where;
	}
	
	//compares keys alphabetically for sorting
	public int compareTo(Node otherNode) {
		return getData().compareTo(otherNode.getData());
	}
}
