/* This file defines a Double-Ended Doubly-Linked List. There will be three
 * LinkedLists--one to list by ID# in order, one for last names, and one for first names. */

public class LinkedList {

	private Node front;
	private Node back;
	private int size;
    private Node rover;  //used to traverse the array

	//constructor
	public LinkedList() {
		front = null;
		back = null;
	}
	
	//insert new elements
	public void insertFront(Node newNode) {
		newNode.setNext(front);
		if(front != null) front.setPrev(newNode); //if list was not empty
		front = newNode;
		if(back == null) back = newNode;
	}	
	public void insertBack(Node newNode) {
		newNode.setPrev(back);
		back.setNext(newNode);
		back = newNode;
	}
	public void insert(Node newNode) {
		rover = back;
		while(rover != null) {
			if(rover.getData().compareTo(newNode.getData()) < 0) break;
			rover = rover.getPrev();
		}
		if(rover == null) insertFront(newNode); //newNode is 1st in order
		else if(rover.getNext() == null) insertBack(newNode); //newNode is last
		else { //newNode is neither 1st nor last
			rover.getNext().setPrev(newNode);
			newNode.setNext(rover.getNext());
			rover.setNext(newNode);
			newNode.setPrev(rover);
		}
		size++;
	}
	
	//find--linear search
	//Given ID, search for ID in LinkedList, return where
	public int findID(String key) {
		rover = front;
		while (rover != null) {
			if (rover.getData().equals(key)) break;
			rover = rover.getNext();
		}
		return rover != null ? rover.getWhere() : -1;
	}
	//Given where, search for where in LinkedList, return Node
	public Node findWhere(int where) {
		rover = front;
		while (rover != null) {
			if (rover.getWhere() == where) break;
			rover = rover.getNext();
		}
		return rover;
	}
	
	//delete
	public void deleteFront() {
		front.getNext().setPrev(null);
		front = front.getNext();
	}
	public void deleteBack() {
		back.getPrev().setNext(null);
		back = back.getPrev();
	}
	public Node delete(int where) {
		Node nodeToDelete = findWhere(where);

		if(size == 1) {
			front = null;	back = null;
		}
		//LinkedList has 2 or more elements
		else if(nodeToDelete.getPrev() == null) deleteFront();
		else if(nodeToDelete.getNext() == null) deleteBack();
		else {
			nodeToDelete.getPrev().setNext(nodeToDelete.getNext());
			nodeToDelete.getNext().setPrev(nodeToDelete.getPrev());
		}
		size--;
		return nodeToDelete;
	}
	
	//The following methods are used when displaying records in order.
	
	//setting iterator in preparation for listing
	public void roverInitFront() {
		rover = front;
	}
	public void roverInitBack() {
		rover = back;
	}
		
	//check whether there is another IndexRecord in IndexArray
	public boolean hasNext() {
		return (rover.getNext() != null);
	}
		
	//check whether IndexArray has more values of lesser index than current
	public boolean hasPrevious() {
		return (rover.getPrev() != null);
	}
		
	//return where of current record, increment/decrement through array
	public int getNext() {
		int where = rover.getWhere();
		rover = rover.getNext();
		return where;
	}
	public int getPrevious() {
		int where = rover.getWhere();
		rover = rover.getPrev();
		return where;
	}
		
	//getters
	public Node getRover() {
		return rover;
	}
	public int getSize() {
		return size;
	}
}
