package datastructures;

import interfaces.ITrie;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class HybridTrie implements ITrie {
	
	public HybridTrie left, right, middle;
	public char letter;
	
	private boolean isNull;
		
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
		return "digraph T {\n" + _draw(new AtomicInteger(0), new StringBuilder(""), 0).toString() + "}";
	}
	
	private StringBuilder _draw(AtomicInteger nc, StringBuilder sb, int father) {
		String me = Integer.toString(father);
		
		if(isNull) {
			int id = nc.incrementAndGet();
			sb.append("null"+ id + "[shape=point]\n");
			sb.append(me +" -> null"+ id +"\n");
			return sb;
		}
		
		sb.append(me + "[label = \""+letter+"\"]\n");
		
		int ncl, ncm, ncr;
		
		ncl = nc.incrementAndGet();
		sb.append(me + " -> " + ncl + "\n");//[label=\""+String.valueOf(left.letter)+"\"]\n");
		
		ncm = nc.incrementAndGet();
		sb.append(me + " -> " + ncm + "\n");//[label=\""+String.valueOf(left.letter)+"\"]\n");
		
		ncr = nc.incrementAndGet();
		sb.append(me + " -> " + ncr + "\n");
		
		left._draw(nc, sb, ncl);
		middle._draw(nc, sb, ncm);
		right._draw(nc, sb, ncr);
		
		return sb;
	}
	
	public String toString() {
		if(isNull) {
			return "<>";
		} else {
			return "["+letter+"]("+left.toString()+", "+middle.toString()+", "+right.toString()+")";
		}
	}

}
