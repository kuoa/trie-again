package datastructures;

import interfaces.ITrie;

public class BalancedHybridTrie extends HybridTrie {
	@Override
	public void insert(String word) {
		root = _insert(root, word);
	}
	
	private Node _balance(Node A) {
		if(A == null) return A;
		
		int hl,hr;
		hl = ((A.left != null) ? A.left.height : 0);
		hr = ((A.right != null) ? A.right.height : 0);
		
		if(hl - hr == 2) { /* need right rotation */
//			if(A.left.left == null || A.left.left.height < A.left.right.height) {
//				A.left = rotateLeft(A.left);
//			}
			A = rotateRight(A);
		} else if(hr - hl == 2) { /* need left rotation */
//			if( A.right.right.height < A.right.left.height) {
//				A.right = rotateRight(A.right);
//			}
			A = rotateLeft(A);
		}
		
		return A;
	}
	
	private Node rotateLeft(Node a) {
		Node nv = a.right;
		a.right = nv.left;
		nv.left = a;
		
		return nv;
	}

	private static Node rotateRight(Node a) {
		Node nv = a.left;
		a.left = nv.right;
		nv.right = a;
		
		return nv;
	}
	
	private Node _insert(Node root, String word) {
		if(word.isEmpty()) return root;
		
		char l = word.charAt(0);
		if(root == null) {
			Node n = new Node(l);
			if(word.length() == 1) 
				n.isEnd = true;
			n.middle = _insert(n.middle, word.substring(1));
			return n;
		}
		
		char letter = root.letter;
		if(l < letter) {
			root.left = _insert(root.left, word);
		}
		else if(l > letter) {
			root.right = _insert(root.right, word);
		} else {
			if(word.length() == 1) 
				root.isEnd = true;
			root.middle = _insert(root.middle, word.substring(1));
		}
		
		root.height = 1+Math.max(
				((root.left != null) ? root.left.height : 0),
				((root.right != null) ? root.right.height : 0)
			);
		root = _balance(root);
		
		return root;
	}
	
	@Override
	public ITrie clone() {
		BalancedHybridTrie bht = new BalancedHybridTrie();
		bht.root = _clone(root);
		return bht;
	}
}
