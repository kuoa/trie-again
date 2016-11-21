package datastructures;

import interfaces.ITrie;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HybridTrie implements ITrie {
	
	public HybridTrie left, right, middle;
	public char letter;
	
	private boolean isNull;
	
	private static int _did = 0; /* used for draw() */
		
	public HybridTrie() {
		isNull = true;
	}
	
	public static HybridTrie nullHT() {
		return new HybridTrie();
	}
	
	public void set(char l) {
		isNull = false;
		letter = l;
		left = nullHT();
		middle = nullHT();
		right = nullHT();
	}

	public void insert(String word) {
		_insert(word+"#");
	}
	
	private void _insert(String word) {
		if(word.isEmpty()) return;
		
		char l = word.charAt(0);
		if(isNull) {
			set(l);
			middle._insert(word.substring(1));
			return;
		}
		
		if(l < letter) {
			left._insert(word);
		}
		else if(l > letter) {
			right._insert(word);
		} else {
			middle._insert(word.substring(1));
		}
				
		
	}

	@Override
	public void remove(String word) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean lookup(String word) {
		return _lookup(word+"#");
	}
	
	public boolean _lookup(String word) {
		if(isNull) return false;
		
		if(word.isEmpty())
			return true;
		
		char l = word.charAt(0);
		
		if(letter == l) {
			if(l == '#') return true;
			return middle._lookup(word.substring(1));
		} else if(l < letter) {
			return left._lookup(word);
		} else {
			return right._lookup(word);
		}
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> listWords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int nbNullPointer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int avgDepth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int nbPrefixed(String pref) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ITrie merge(ITrie trie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITrie convert() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String draw() {
		StringBuilder sb = new StringBuilder("digraph T {\n");
		_draw(sb, 0);
		sb.append("}");
		
		return sb.toString();
	}
	
	private void _draw(StringBuilder sb, int father) {
		String fath = Integer.toString(father);
		Integer me = _did++;
		
		if(isNull) {
			sb.append(me + "[shape=point]\n");
			sb.append(fath +" -> "+ me +"\n");
			return;
		}
		
		sb.append(me + " [label=\""+letter+"\"]\n");
		sb.append(father + " -> " + me + "\n");
		
		left._draw(sb, me);
		middle._draw(sb, me);
		right._draw(sb, me);
		
	}
	
	public String toString() {
		return _toString("| ");
	}
	
	public String _toString(String blk) {
		if(isNull) {
			return "<>";
		} else {
			return "["+letter+"]\n"+blk+left._toString(blk+"| ")+"\n"+blk+middle._toString(blk+"| ")+"\n"+blk+right._toString(blk+"| ")+")";
		}
	}

}
