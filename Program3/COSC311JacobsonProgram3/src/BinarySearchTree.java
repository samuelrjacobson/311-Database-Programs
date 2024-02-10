/* This file defines a threaded binary search tree. Three of them will be created,
 * one organized by last name, one by first name, and one by ID#.
 */
public class BinarySearchTree {
	
	private Node root;
	private int size;
	private Node rover;
	
	// searches ID BST for node with specified ID, returns node
	public Node findID(String key) {
		Node current = root;

		while(!current.getData().equals(key)) {
			//going left
			if(key.compareTo(current.getData()) < 0) {
				if(current.leftIsThread()) return null;
				else current = current.getLeftChild();
				if(current == null) return null;
			}
			//going right
			else {
				if(current.rightIsThread()) return null;
				else current = current.getRightChild();
				if(current == null) return null;
			}
		}
		// current now references Node with correct ID
		return current;
	}
	
	// given node, insert into BST
	public void insert(Node newNode) {
		// if tree is empty, insert its first node
		if(root == null) {
			root = newNode;
			size++;
			return;
		}
		Node current = root;
		while (current != null) {
			// if true, insert at/go to left; if false, right
			if (newNode.getData().compareTo(current.getData()) < 0) {
				// if left child is thread, insert to the left
				if(current.leftIsThread()) {
					newNode.setLeftChild(current.getLeftChild());
	                newNode.setLeftIsThread(true);
	                newNode.setRightChild(current);
	                newNode.setRightIsThread(true);
	                current.setLeftChild(newNode);
	                current.setLeftIsThread(false);
	    			size++;
	                return;
	            }
				// if left child is null, insert to the left
	            else if(current.getLeftChild() == null) {
	             	current.setLeftChild(newNode);
	               	current.setLeftIsThread(false);
	               	newNode.setRightChild(current);
	               	newNode.setRightIsThread(true);
	    			size++;
	               	return;
	            }
				// otherwise, go left once
	            else {
	              	current = current.getLeftChild();
	            }
	        }
	        else {
	        	// if right child is thread, insert to the right
	            if (current.rightIsThread()) {
	                newNode.setLeftChild(current);
	                newNode.setLeftIsThread(true);
	                newNode.setRightChild(current.getRightChild());
	                newNode.setRightIsThread(true);
	                current.setRightChild(newNode);
	                current.setRightIsThread(false);
	    			size++;
	                return;
	            }
	            // if right child is null, insert to the right
	            else if(current.getRightChild() == null) {
	             	current.setRightChild(newNode);
	               	current.setRightIsThread(false);
	               	newNode.setLeftChild(current);
	               	newNode.setLeftIsThread(true);
	    			size++;
	               	return;
	            }
	            // otherwise go right once
	            else {
	                current = current.getRightChild();
	            }
	        }
		}	
	}
	// given key and where of a node, find the node and delete from BST
	public boolean delete(String key, int where) {
		Node current = root;
		Node parent = root;
		boolean isLeftChild = true;
		
		// find node to delete
		while(!current.getData().equals(key)) {
			parent = current;
			if(key.compareTo(current.getData()) < 0) {
				isLeftChild = true;
				if(current.leftIsThread()) return false;
				current = current.getLeftChild();
			}
			else {
				isLeftChild = false;
				if(current.rightIsThread()) return false;
				current = current.getRightChild();
			}
			if(current == null) return false;
		}
		// make sure where matches in case there are duplicate names
		while(current.getWhere() != where) {
			current = getSuccessor(current, false);
		}
		// Node to delete has been found.
		
		// if the node has no children
		// if the node is the root
		if(current.getLeftChild() == null && current.getRightChild() == null) root = null;
		// if the node is the leftmost node
		else if(current.getLeftChild() == null && current.rightIsThread() == true) parent.setLeftChild(null);
		// if the node is the rightmost node
		else if(current.leftIsThread() && current.getRightChild() == null) parent.setRightChild(null);
		// if the node has two threads
		else if(current.leftIsThread() && current.rightIsThread()){
			// if node we're deleting is parent's left child
			if(isLeftChild) {
				parent.setLeftChild(current.getLeftChild());
				parent.setLeftIsThread(true);
				if(current.getLeftChild().rightIsThread()) current.getLeftChild().setRightChild(parent);
			}
			// if node we're deleting is parent's right child
			else {
				parent.setRightChild(current.getRightChild());
				parent.setRightIsThread(true);
				if(current.getRightChild().leftIsThread()) current.getRightChild().setLeftChild(parent);
			}
		}
		
		// if the node has a left child but no right child
		else if(current.getRightChild() == null || current.rightIsThread()) {
			if(current == root) {
				root = current.getLeftChild();
				if(root.rightIsThread()) {
					root.setRightChild(null);
					root.setRightIsThread(false);
				}
			}
			// if node we're deleting is parent's left child
			else if(isLeftChild) {
				parent.setLeftChild(current.getLeftChild());
				if(current.getLeftChild().rightIsThread()) {
					current.getLeftChild().setRightChild(parent);
				}
				// if left is thread, it can stay unchanged
			}
			// if node we're deleting is parent's right child
			else {
				parent.setRightChild(current.getLeftChild());
				if(current.getLeftChild().leftIsThread()) {
					current.getLeftChild().setLeftChild(parent);
				}
				if(current.getLeftChild().rightIsThread()) {
					current.getLeftChild().setRightChild(current.getRightChild());
					current.getLeftChild().setRightIsThread(current.rightIsThread());
				}
			}
		}
		// if the node has a right child but no left child
		else if(current.getLeftChild() == null || current.leftIsThread()) {
			if(current == root) {
				root = current.getRightChild();
				if(root.leftIsThread()) {
					root.setLeftChild(null);
					root.setLeftIsThread(false);
				}
			}
			// if node we're deleting is parent's left child
			else if(isLeftChild) {
				parent.setLeftChild(current.getRightChild());
				if(current.getRightChild().leftIsThread()) {
					current.getRightChild().setLeftChild(current.getLeftChild());
					current.getRightChild().setLeftIsThread(current.leftIsThread());
				}
				if(current.getRightChild().rightIsThread()) {
					current.getRightChild().setRightChild(parent);
				}
			}
			// if node we're deleting is parent's right child
			else {
				parent.setRightChild(current.getRightChild());
				if(current.getRightChild().leftIsThread()) {
					current.getRightChild().setLeftChild(parent);
				}
				// if right is thread, it can stay unchanged
			}
		}
		else { // node has two children
			// getSuccessor, when called with true (true that we are deleting), performs most necessary tasks needed in deletion after finding the successor.
			Node successor = getSuccessor(current, true);
		
			// finish deletion tasks
			if(current == root) root = successor;
			else if(isLeftChild) {
				parent.setLeftChild(successor);
				if(successor == current.getRightChild()) {
					successor.setRightChild(parent);
					successor.setRightIsThread(true);
				}
			}
			else parent.setRightChild(successor);
		}
		return true;
	}
	
	// The following methods are used for inorder/reverse-order traversal.
	// used in inorder traversal and in deletion
	private Node getSuccessor(Node origNode, boolean deleting) {
		if(origNode.rightIsThread()) return origNode.getRightChild();
		
		Node parent = origNode;
		Node current = origNode.getRightChild();
		
		if(current == null) return null; // if rightmost node in tree
		
		// find the inorder successor
		while (!current.leftIsThread()) {
			parent = current;
			current = current.getLeftChild();
		}
		// current is now inorder successor.
		
		// deletion when successor is not origNode's right child
		if(deleting && current != origNode.getRightChild()) {
			// Successor cannot have a left child.
			// if the successor has no children:
			if(current.rightIsThread()) {
				parent.setLeftChild(origNode);
				parent.setLeftIsThread(true);
			}
			// if the successor has a right child
			else {
				parent.setLeftChild(current.getRightChild());
				if(current.getRightChild().leftIsThread()) {
					current.getRightChild().setLeftChild(origNode);
				}
			}
			current.setRightChild(origNode.getRightChild());
			if(origNode.getRightChild().leftIsThread()) {
				origNode.getRightChild().setLeftChild(current);
			}
		}
		// deletion (all cases)
		if(deleting) {
			current.setLeftChild(origNode.getLeftChild());
			if(origNode.getLeftChild().rightIsThread()) {
				origNode.getLeftChild().setRightChild(current);
			}
		}
		return current;
	}
	private Node getPredecessor(Node origNode) {
		if(origNode.leftIsThread()) return origNode.leftChild;
		
		Node current = origNode.leftChild;
		
		if(current == null) return null; // if leftmost node in tree
		
		// find inorder predecessor
		while (!current.rightIsThread()) {
			current = current.rightChild;
		}
		return current;
	}
	
	// return where of current record, increment/decrement through array
	public int getNext() {
		int where = rover.getWhere();
		if(rover.rightIsThread()) rover = rover.getRightChild();
		else rover = getSuccessor(rover, false);
		return where;
	}
	public int getPrevious() {
		int where = rover.getWhere();
		if(rover.leftIsThread()) rover = rover.getLeftChild();
		else rover = getPredecessor(rover);
		return where;
	}
	// initialize rover to leftmost node
	public Node initRoverLeftmostNode() {
		Node current = root;
		
		// leftmost node must have null child. In threaded BST, it is only node without a left child/thread. 
		while(current.getLeftChild() != null) {
			current = current.getLeftChild();
		}
		rover = current;
		return current;
	}
	// initialize rover to rightmost node
	public Node initRoverRightmostNode() {
		Node current = root;
		
		// rightmost node must have null child. In threaded BST, it is only node without a right child/thread. 
		while(current.getRightChild() != null) {
			current = current.getRightChild();
		}
		rover = current;
		return current;
	}
	
	// getters
	public int getSize() {
		return size;
	}
	public Node getRover() {
		return rover;
	}
}
