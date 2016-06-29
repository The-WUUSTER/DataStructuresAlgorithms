package structures;

import node.TreeNode;

public class Tree {
	private TreeNode root;
	
	/**
	 * Constructor for a tree
	 * Uses integers only since there's no set way to compare generic types
	 */
	public Tree () {
		root = null;
	}
	
	/**
	 * Returns the root of the tree
	 * @return
	 */
	public TreeNode getRoot() {
		return root;
	}
	
	/**
	 * Adds a node to the tree
	 * @param rt root of the tree
	 * @param data value to be inserted
	 */
	public void addNode(int data) {
		TreeNode node = new TreeNode(data);
		
		if (root == null) {
			root = node;
			return;
		}
		
		TreeNode current = root;
		TreeNode parent = null;
		
		while (true) {
			parent = current;
			if (data < current.getData()) {
				current = current.left;
				if (current == null) {
					parent.left = node;
					return;
				}
			}
			else {
				current = current.right;
				if (current == null) {
					parent.right = node;
					return;
				}
			}
		}
	}
	
	/**
	 * Deletes a node from the tree
	 * @param data value to be deleted
	 * @return true if deleted, false if value does not exist in tree
	 */
	public boolean deleteNode(int data) {
		TreeNode parent = root;
		TreeNode current = root;
		boolean isLeftChild = false;
		
		while(current.getData() != data){
			parent = current;
			if (current.getData() > data){
				isLeftChild = true;
				current = current.left;
			}
			else {
				isLeftChild = false;
				current = current.right;
			}
			//node is not found
			if (current == null){
				return false;
			}
		}
		
		//node is found
		//Case 1: if node to be deleted has no children
		if (current.left == null && current.right == null) {
			if (current == root){
				root = null;
			}
			if (isLeftChild == true) {
				parent.left = null;
			}
			else {
				parent.right = null;
			}
		}
		//Case 2 : if node to be deleted has only one child
		else if (current.right == null) {
			if (current == root) {
				root = current.left;
			}
			else if (isLeftChild) {
				parent.left = current.left;
			}
			else {
				parent.right = current.left;
			}
		}
		else if (current.left == null) {
			if (current == root) {
				root = current.right;
			}
			else if (isLeftChild) {
				parent.left = current.right;
			}
			else {
				parent.right = current.right;
			}
		}
		else {
			//now we have found the minimum element in the right sub tree
			TreeNode successor = getSuccessor(current);
			if (current == root) {
				root = successor;
			}
			else if (isLeftChild) {
				parent.left = successor;
			}
			else {
				parent.right = successor;
			}			
			successor.left = current.left;
		}		
		return true;	
	}
	
	/**
	 * Helper method for deleting nodes
	 * @param tbd node to be deleted
	 * @return successor node
	 */
	public TreeNode getSuccessor(TreeNode tbd) {
		TreeNode successor = null;
		TreeNode successor_parent = null;
		TreeNode current = tbd.right;
		
		while (current != null) {
			successor_parent = successor;
			successor = current;
			current = current.right;
		}
		
		//check if the successor has a right child (cannot have left)
		//add it to the left of successor_parent if it has a right child
		if (successor != tbd.right) {
			successor_parent.left = successor.right;
			successor.right = tbd.right;
		}
		return successor;
	}
	
	/**
	 * Searches the tree for the target number
	 * @param rt root node
	 * @param data number to be searched
	 * @return true if found, false otherwise
	 */
	public boolean findTarget(TreeNode rt, int data) {
		if (rt == null) {
			return false;
		}
		if (rt.getData() == data) {
			return true;
		}
		else {
			if (data < rt.getData()) {
				return findTarget(rt.left, data);
			}
			else {
				return findTarget(rt.right, data);
			}
		}
	}
	
	/**
	 * Gets the height/depth of the tree
	 * @param r root of the tree
	 * @return
	 */
	public int getDepth(TreeNode rt) {
		if (rt == null) {
			return 0;
		}
		return Math.max(1 + getDepth(rt.left), 1 + getDepth(rt.right));
	}
	
	/**
	 * Performs an in-order traversal of the tree
	 * @param rt
	 */
	public void inOrderTraversal(TreeNode rt) {
		if (rt != null) {
			inOrderTraversal(rt.left);
			System.out.print(rt.getData() + " ");
			inOrderTraversal(rt.right);
		}
	}
}