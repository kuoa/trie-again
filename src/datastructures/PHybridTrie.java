package datastructures;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import interfaces.ITrie;

class Node {
	Node left, middle, right;
	char letter;
	int height;
	boolean isEnd;
	
	static int _did = 0;
	static int _nbLeaves = 0, _depthesSum = 0;
	
	public Node() {
		
	}
	
	public Node(char l) {
		letter = l;
		isEnd = false;
		height = 1;
	}

	public static void draw(Node root, StringBuilder sb, Integer father) {
		String fath = Integer.toString(father);
		Integer me = _did++;
		
		if(root == null) {
			sb.append(me + "[shape=point]\n");
			sb.append(fath +" -> "+ me +"\n");
			return;
		}
		
		sb.append(me + " [label=\""+root.letter+" ["+root.height+"]\""+
				((root.isEnd) ? ",shape=square" : ",shape=circle")
				+"]\n");
		sb.append(father + " -> " + me + "\n");
		
		draw(root.left, sb, me);
		draw(root.middle, sb, me);
		draw(root.right, sb, me);
	}

	public static Node insert(Node root, String word) {
		if(word.isEmpty()) return null;
		
		char l = word.charAt(0);
		if(root == null) {
			Node n = new Node(l);
			if(word.length() == 1) 
				n.isEnd = true;
			n.middle = insert(n.middle, word.substring(1));
			return n;
		}
		
		char letter = root.letter;
		if(l < letter) {
			root.left = insert(root.left, word);
		}
		else if(l > letter) {
			root.right = insert(root.right, word);
		} else {
			if(word.length() == 1) 
				root.isEnd = true;
			root.middle = insert(root.middle, word.substring(1));
		}
		
		root.height = 1+Math.max(
				((root.left != null) ? root.left.height : 0),
				((root.right != null) ? root.right.height : 0)
			);
		root = balance(root);
		
		return root;
	}
	
	public static Node insertNormal(Node root, String word) {
		if(word.isEmpty()) return null;
		
		char l = word.charAt(0);
		if(root == null) {
			Node n = new Node(l);
			if(word.length() == 1) 
				n.isEnd = true;
			n.middle = insert(n.middle, word.substring(1));
			return n;
		}
		
		char letter = root.letter;
		if(l < letter) {
			root.left = insert(root.left, word);
		}
		else if(l > letter) {
			root.right = insert(root.right, word);
		} else {
			if(word.length() == 1) 
				root.isEnd = true;
			root.middle = insert(root.middle, word.substring(1));
		}
		
		return root;
	}
	
	private static Node balance(Node A) {
		if(A == null) return A;
		
		int hl,hr;
		hl = ((A.left != null) ? A.left.height : 0);
		hr = ((A.right != null) ? A.right.height : 0);
		
		if(hl - hr == 2) { /* need right rotation */
//			if(A.left.height < A.right.height) {
//				A.left = rotateLeft(A.left);
//			}
			A = rotateRight(A);
		} else if(hl - hr == -2) { /* need left rotation */
//			if(A.right.height < A.left.height) {
//				A.right = rotateRight(A.right);
//			}
			A = rotateLeft(A);
		}
		
		return A;
	}
	
	private static Node rotateLeft(Node a) {
		Node nv = a.right;
		a.right = nv.left;
		nv.left = a;
		
		/* update heights */
//		nv.height++;
//		a.height--;		
		
		return nv;
	}

	private static Node rotateRight(Node a) {
		Node nv = a.left;
		a.left = nv.right;
		nv.right = a;
		
//		nv.height--;
//		a.height++;
		
		return nv;
	}

	public static Node remove(Node root, String word) {
		if(root == null || word.isEmpty()) return null;
		
		char l = word.charAt(0), letter = root.letter;
		
		if(letter == l) {
			if(root.isEnd && word.length() == 1) {
				root.isEnd = false;
				return rot(root);
			}
			root.middle = remove(root.middle, word.substring(1));
		} else if(l < letter) {
			root.left = remove(root.left, word);
		} else {
			root.right = remove(root.right, word);
		}
		return root;
	}
	
	private static Node rot(Node root) {
		if(root == null) return null;
		
		if(root.left == null && root.right == null && root.middle == null && !root.isEnd) return null;
		return root;
	}

	public static boolean lookup(Node root, String word) {
		if(root == null || word.isEmpty()) return false;
		
		char l = word.charAt(0), letter = root.letter;
		
		if(letter == l) {
			if(root.isEnd && word.length() == 1) return true;
			return lookup(root.middle, word.substring(1));
		} else if(l < letter) {
			return lookup(root.left, word);
		} else {
			return lookup(root.right, word);
		}
	}

	public static int count(Node root) {
		if(root == null) return 0;
		
		return ((root.isEnd) ? 1 : 0) + count(root.left) + count(root.middle) + count(root.right);
	}

	public static void listWords(Node root, List<String> ret, String pref) {
		if(root == null) return;
		
		if(root.isEnd) {
			ret.add(pref+root.letter);
		}
		
		listWords(root.left, ret, pref);
		listWords(root.middle, ret, pref+root.letter);
		listWords(root.right, ret, pref);
	}

	public static int nbNullPointer(Node root) {
		if(root == null) return 1;
		return nbNullPointer(root.left)+nbNullPointer(root.middle)+nbNullPointer(root.right);
	}

	public static int height(Node root) {
		if(root == null) return 0;
		return 1+height(root.left)+height(root.middle)+height(root.right);
	}

	public static int avgDepth(Node root) {
		if(root == null) return 0;
		
		_nbLeaves = 0;
		_depthesSum = 0;
		
		_avgDepth(root,0);
		
		return _depthesSum / _nbLeaves;
	}
	
	private static void _avgDepth(Node root, int prof) {
		if(root == null) return;
		if(root.left == null && root.middle == null && root.right == null) {
			_nbLeaves++;
			_depthesSum += prof;
			return;
		}
		
		_avgDepth(root.left, prof+1);
		_avgDepth(root.middle, prof+1);
		_avgDepth(root.right, prof+1);
	}

	public static int nbPrefixed(Node root, String pref) {
		if(root == null) return 0;
		
		if(pref.isEmpty()) return count(root);
		
		char l = pref.charAt(0), letter = root.letter;
		
		if(letter == l) {
			return nbPrefixed(root.middle, pref.substring(1));
		} else if(l < letter) {
			return nbPrefixed(root.left, pref);
		} else {
			return nbPrefixed(root.right, pref);
		}
	}

	public static Node clone(Node root) {
		if(root == null) return null;
		Node n = new Node(root.letter);
		n.isEnd = root.isEnd;
		n.height = root.height;
		
		n.left = clone(root.left);
		n.middle = clone(root.middle);
		n.right = clone(root.right);
		
		return n;
	}

	public static void convert(Node root, PatriciaTrie pt) {
		if(root == null) return;
		
		String pref = "";
		Node ptr = root;
		Hashtable<Character, Tuple<String, PatriciaTrie>> hash = pt.data;
		
		do {
			pref += ptr.letter;
			ptr = ptr.middle;
		} while(ptr != null && ptr.left == null && ptr.right == null && !ptr.isEnd);
		
//		System.out.println(pref);
		
		if(ptr != null && ptr.isEnd) {
			pref += PatriciaTrie.epsilon;
//			ptr = ptr.middle;
			
			/* reached a leaf */
//			if(ptr.left == null && ptr.middle == null && ptr.right == null) {
//				
//			} else {
//				/** what do we have to do? **/
//			}
		}
		
		PatriciaTrie np = new PatriciaTrie();
		
		convert(ptr, np);
		hash.put(pref.charAt(0), new Tuple<>(pref, np));
		
		
		if(root.left != null) {
			convert(root.left, pt);
		}
		if(root.right != null) {
			convert(root.right, pt);
		}
	}
	
}

public class PHybridTrie implements ITrie {
	
	Node root;
	
	@Override
	public void insert(String word) {
		root = Node.insert(root, word);

	}

	@Override
	public void remove(String word) {
		root = Node.remove(root, word);

	}

	@Override
	public boolean lookup(String word) {
		return Node.lookup(root, word);
	}

	@Override
	public int count() {
		return Node.count(root);
	}

	@Override
	public List<String> listWords() {
		List<String> ret = new ArrayList<>();
		Node.listWords(root, ret, "");
		return ret;
	}

	@Override
	public int nbNullPointer() {
		return Node.nbNullPointer(root);
	}

	@Override
	public int height() {
		return Node.height(root);
	}

	@Override
	public int avgDepth() {
		return Node.avgDepth(root);
	}

	@Override
	public int nbPrefixed(String pref) {
		return Node.nbPrefixed(root, pref);
	}

	@Override
	public ITrie convert() {
		PatriciaTrie pt = new PatriciaTrie();
		
		Node.convert(root, pt);
		
		return pt;
	}
	
	public void insertNormal(String word) {
		root = Node.insertNormal(root, word);
	}

	@Override
	public ITrie clone() {
		PHybridTrie ht = new PHybridTrie();
		ht.root = Node.clone(root);
		return ht;
	}

	@Override
	public String draw() {
		StringBuilder sb = new StringBuilder("digraph T {\n");
		
		Node.draw(root, sb, 0);
		
		return sb.append("\n}").toString();
	}

}
