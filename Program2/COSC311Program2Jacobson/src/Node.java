/* This file defines a Node, an element of a LinkedList. Node has two data pieces,
 * for the ID/last name/first name String and the where int, as well as references
 * to the previous and next Node in the list. */

public class Node {

	private String data;
	private int where;
	private Node prev;
	private Node next;
	
	//constructors
	public Node() {
		data = "";
		where = -1;
		prev = null;
		next = null;
	}
	public Node(String d, int w) {
		data = d;
		where = w;
		prev = null;
		next = null;
	}
	
	//setters
	public void setPrev(Node n) {
		prev = n;
	}
	public void setNext(Node n) {
		next = n;
	}
	
	//getters
	public Node getPrev() {
		return prev;
	}
	public Node getNext() {
		return next;
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
