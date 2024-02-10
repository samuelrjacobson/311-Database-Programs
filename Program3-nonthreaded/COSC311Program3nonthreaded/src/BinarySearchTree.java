
public class BinarySearchTree {
	
	private Node root;
	private int size;
	private Node rover;
	//cannot search BST by where attribute
	//Find: probably not: Search ID BST for Node with given ID#, get where
	//		1. Search DataBaseArray for student with given ID#, get where, lastname, and firstname
	//		2. Traverse BSTs, if lastname/firstname match, check where
	//		3. If where doesn't match, find inorder successor
	//OR--Traverse entire BST inorder, stop when where matches
	//WAITT---ID IS UNIQUE THOUGH
	public Node findID(String key) {
		//System.out.println("finding");
		Node current = root;

		while(!current.getData().equals(key)) {
			//System.out.println("inside findwhile");
			//going left
			if(key.compareTo(current.getData()) < 0) {
				current = current.getLeftChild();
			}
			//going right
			else {
				current = current.getRightChild();
			}
			if(current == null) return null;
		}
		// current now references Node with correct ID
		return current;
	}
	//inorder traversal of BST--recursive
	public void inOrder(Node localRoot, DataBaseArray myDB) {
		if(localRoot != null) {
			inOrder(localRoot.getLeftChild(), myDB);
			System.out.println(myDB.getDataBaseRecordToString(localRoot.getWhere()));
			inOrder(localRoot.getRightChild(), myDB);
		}
	}
	public void reverseOrder(Node localRoot, DataBaseArray myDB) {
		if(localRoot != null) {
			reverseOrder(localRoot.getRightChild(), myDB);
			System.out.println(myDB.getDataBaseRecordToString(localRoot.getWhere()));
			reverseOrder(localRoot.getLeftChild(), myDB);
		}
	}
	
	public Node initRoverLeftmostNode() {
		Node current = root;
		
		while(current.leftChild != null) {
			current = current.leftChild;
		}
		rover = current;
		System.out.println(rover.getData());
		return current;
	}
	public Node initRoverRightmostNode() {
		Node current = root;
		
		while(current.rightChild != null) {
			current = current.rightChild;
		}
		rover = current;
		return current;
	}
	public void insert(Node newNode) {
		System.out.println("inserting");
		// if tree is empty
		if(root == null) {
			root = newNode;
			size++;
		}
		else {
			Node current = root;
			Node parent;
			while(true) {
				parent = current;
				// go left
				if(current == null) System.out.println("CURRENT IS NULL");
				else System.out.println("NOT NULL");
				if(newNode.getData().compareTo(current.getData()) < 0) {
					System.out.println("left");
					current = current.getLeftChild();
					//if(current == null) {
					// if we want to insert as parent's left child
					if(current == null) {
						parent.setLeftChild(newNode);
						size++;

						return;
					}
				}
				// or go right
				else {
					System.out.println("right");
					current = current.getRightChild();
					//if(current == null) {
					if(current == null) {
						parent.setRightChild(newNode);
						size++;

						return;
					}
				} //end go right
			} //end while
		} //end tree isn't empty
	}
	public boolean delete(String key, int where) {
		Node current = root;
		Node parent = root;
		boolean isLeftChild = true;
		
		// find node to delete
		while(!current.getData().equals(key)) {
			parent = current;
			if(key.compareTo(current.getData()) < 0) {
				isLeftChild = true;
				current = current.leftChild;
			}
			else {
				isLeftChild = false;
				current = current.rightChild;
			}
			if(current == null) return false;
		}
		// make sure where matches in case there are duplicate names
		while(current.getWhere() != where) {
			current = getSuccessor(current, false);
		}
		
		// if the node has no children
		if(current.leftChild == null && current.rightChild == null) {
			if(current == root) root = null;
			else if (isLeftChild) parent.leftChild = null;
			else parent.rightChild = null;
		} // has a left child but no right child
		else if(current.rightChild == null) {
			if(current == root) root = current.leftChild;
			else if(isLeftChild) parent.leftChild = current.leftChild;
			else parent.rightChild = current.leftChild;
		} // has a right child but no left child
		else if(current.leftChild == null) {
			if(current == root) root = current.rightChild;
			else if(isLeftChild) parent.leftChild = current.rightChild;
			else parent.rightChild = current.rightChild;
		}
		else { //two children
			Node successor = getSuccessor(current, true);
		
			if(current == root) root = successor;
			else if(isLeftChild) parent.leftChild = successor;
			else parent.rightChild = successor;
			successor.leftChild = current.leftChild;
		}
		return true;
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

	private Node getSuccessor(Node origNode, boolean deleting) {
		
		Node successorParent = origNode;
		Node successor = origNode;
		Node current = origNode.rightChild;
		
		while(current != null) {
			successorParent = successor;
			successor = current;
			current = current.leftChild;
		}
		if(deleting && successor != origNode.rightChild) {
			successorParent.leftChild = successor.rightChild;
			successor.rightChild = origNode.rightChild;
		}
		return successor;
	}
	//return where of current record, increment/decrement through array
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
	public int getSize() {
		return size;
	}
	public Node getRover() {
		return rover;
	}
	public void traverseInOrder(DataBaseArray myDB) {
		inOrder(root, myDB);
	}
	public void traverseReverseOrder(DataBaseArray myDB) {
		reverseOrder(root, myDB);
	}
}
