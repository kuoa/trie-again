package datastructures;

import java.util.ArrayList;
import java.util.List;

import interfaces.ITrie;

class Node {
	Node left, middle, right;
	char letter;
	int height;
	boolean isEnd;
	
	public Node() {
		
	}
	
	public Node(char l) {
		letter = l;
		isEnd = false;
		height = 1;
	}	
}

public class HybridTrie implements ITrie {
	
	protected Node root;
	
	private static int _did = 0;
	private static int _nbLeaves = 0, _depthesSum = 0;
	
	public PatriciaTrie convert_S(){
		PatriciaTrie t = new PatriciaTrie();
		
		for (String word : listWords()){
			t.insert(word);
		}
		
		return t;
			
	}
	
	@Override
	public void insert(String word) {
		root = _insert(root, word);
	}

	@Override
	public void remove(String word) {
		root = _remove(root, word);

	}

	@Override
	public boolean lookup(String word) {
		return _lookup(root, word);
	}

	@Override
	public int count() {
		return _count(root);
	}

	@Override
	public List<String> listWords() {
		List<String> ret = new ArrayList<>();
		_listWords(root, ret, "");
		return ret;
	}

	@Override
	public int nbNullPointer() {
		return _nbNullPointer(root);
	}

	@Override
	public int height() {
		return _height(root);
	}

	@Override
	public int avgDepth() {
		if(root == null) return 0;
		
		_nbLeaves = 0;
		_depthesSum = 0;
		
		_avgDepth(root,0);
		
		return _depthesSum / _nbLeaves;
	}

	@Override
	public int nbPrefixed(String pref) {
		return _nbPrefixed(root, pref);
	}

	@Override
	public ITrie convert() {
		PatriciaTrie pt = new PatriciaTrie();
		
		_convert("", pt, root);
		
		return pt;
	}

	@Override
	public ITrie clone() {
		HybridTrie ht = new HybridTrie();
		ht.root = _clone(root);
		return ht;
	}

	@Override
	public String draw() {
		StringBuilder sb = new StringBuilder("digraph T {\n");
		_did = 0;
		_draw(root, sb, 0);
		
		return sb.append("\n}").toString();
	}
	
	private static void _draw(Node root, StringBuilder sb, Integer father) {
		String fath = Integer.toString(father);
		Integer me = _did++;
		
		if(root == null) {
			sb.append(me + "[shape=point]\n");
			sb.append(fath +" -> "+ me +"\n");
			return;
		}
		
		sb.append(me + " [label=\""+root.letter+" ["+root.height+"]\""+
				((root.isEnd) ? ",shape=square,style=filled,color=lightgreen" : ",shape=circle")
				+"]\n");
		sb.append(father + " -> " + me + "\n");
		
		_draw(root.left, sb, me);
		_draw(root.middle, sb, me);
		_draw(root.right, sb, me);
	}
	
	private static Node _insert(Node root, String word) {
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
		
		return root;
	}

	private static Node _remove(Node root, String word) {
		if(root == null || word.isEmpty()) return null;
		
		char l = word.charAt(0), letter = root.letter;
		
		if(letter == l) {
			if(root.isEnd && word.length() == 1) {
				root.isEnd = false;
				return rot(root);
			}
			root.middle = _remove(root.middle, word.substring(1));
		} else if(l < letter) {
			root.left = _remove(root.left, word);
		} else {
			root.right = _remove(root.right, word);
		}
		return root;
	}
	
	private static Node rot(Node root) {
		if(root == null) return null;
		
		if(root.left == null && root.right == null && root.middle == null && !root.isEnd) return null;
		return root;
	}

	private static boolean _lookup(Node root, String word) {
		if(root == null || word.isEmpty()) return false;
		
		char l = word.charAt(0), letter = root.letter;
		
		if(letter == l) {
			if(root.isEnd && word.length() == 1) return true;
			return _lookup(root.middle, word.substring(1));
		} else if(l < letter) {
			return _lookup(root.left, word);
		} else {
			return _lookup(root.right, word);
		}
	}

	private static int _count(Node root) {
		if(root == null) return 0;
		
		return ((root.isEnd) ? 1 : 0) + _count(root.left) + _count(root.middle) + _count(root.right);
	}

	private static void _listWords(Node root, List<String> ret, String pref) {
		if(root == null) return;
		
		if(root.isEnd) {
			ret.add(pref+root.letter);
		}
		
		_listWords(root.left, ret, pref);
		_listWords(root.middle, ret, pref+root.letter);
		_listWords(root.right, ret, pref);
	}

	private static int _nbNullPointer(Node root) {
		if(root == null) return 1;
		return _nbNullPointer(root.left)+_nbNullPointer(root.middle)+_nbNullPointer(root.right);
	}

	private static int _height(Node root) {
		if(root == null) return 0;
		return 1+Math.max(_height(root.left),Math.max(_height(root.middle),_height(root.right)));
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

	private static int _nbPrefixed(Node root, String pref) {
		if(root == null) return 0;
		
		if(pref.isEmpty()) return _count(root);
		
		char l = pref.charAt(0), letter = root.letter;
		
		if(letter == l) {
			return _nbPrefixed(root.middle, pref.substring(1));
		} else if(l < letter) {
			return _nbPrefixed(root.left, pref);
		} else {
			return _nbPrefixed(root.right, pref);
		}
	}

	protected static Node _clone(Node root) {
		if(root == null) return null;
		Node n = new Node(root.letter);
		n.isEnd = root.isEnd;
		n.height = root.height;
		
		n.left = _clone(root.left);
		n.middle = _clone(root.middle);
		n.right = _clone(root.right);
		
		return n;
	}
	
	
	
	private static void _convert(String word, PatriciaTrie pat, Node node){
		
		if(node == null){
			return;
		}
		
		String newWord = word + node.letter;
		
		/* word found */
		if(node.isEnd){

			/* no child so append epsilon */
			if(node.middle == null){				
				pat.insert(newWord + PatriciaTrie.epsilon);				
			}
			else{
				/* insert word */
				pat.insert(newWord);
												
				Tuple<String, PatriciaTrie> currentTuple = 
						pat.data.get(newWord.charAt(0));						
				
				/* son */
				if(currentTuple.child == null){
					currentTuple.child = new PatriciaTrie();
				}
												
				/* epsilon */
				currentTuple.child.data.put(PatriciaTrie.epsilon,
						new Tuple<String, PatriciaTrie>(PatriciaTrie.epsilon.toString(), null));

				_convert("", currentTuple.child, node.middle);				
			}											
		}
		else{
			_convert(newWord, pat, node.middle);
		}
		
		_convert(word, pat, node.left);
		_convert(word,pat, node.right);		
				
	}
}

/*
public static void convert(Node root, PatriciaTrie pt) {
	if(root == null) return;
	
	String pref = "";
	Node ptr = root;
	Hashtable<Character, Tuple<String, PatriciaTrie>> hash = pt.data;
	
	do {
		pref += ptr.letter;
		ptr = ptr.middle;
	} while(ptr != null && ptr.left == null && ptr.right == null && !ptr.isEnd);
	
//	System.out.println(pref);
	
	if(ptr != null && ptr.isEnd) {
		pref += PatriciaTrie.epsilon;
//		ptr = ptr.middle;
		

//		if(ptr.left == null && ptr.middle == null && ptr.right == null) {
//			
//		} else {
//	
//		}
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
*/
