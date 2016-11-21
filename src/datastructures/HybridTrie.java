package datastructures;

import interfaces.ITrie;

import java.util.ArrayList;
import java.util.List;

public class HybridTrie implements ITrie {
	
	public HybridTrie left, right, middle;
	public char letter;
	
	private boolean isNull, isEnd;
	
	private static int _did = 0; /* used for draw() */
	private static int _depthsSum, _nbLeaves;
		
	public HybridTrie() {
		isNull = true;
		isEnd = false;
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
	
	public void nullify() {
		isNull = true;
		left = null;
		right = null;
		middle = null;
	}

	@Override	
	public void insert(String word) {
		if(word.isEmpty()) return;
		
		char l = word.charAt(0);
		if(isNull) {
			set(l);
			if(word.length() == 1) isEnd = true;
			middle.insert(word.substring(1));
			return;
		}
		
		if(l < letter) {
			left.insert(word);
		}
		else if(l > letter) {
			right.insert(word);
		} else {
			if(word.length() == 1) isEnd = true;
			middle.insert(word.substring(1));
		}
				
		
	}

	@Override
	public void remove(String word) {
		_remove(word);
	}
	
	private boolean _remove(String word) { /* returns true if children was nullified */
		if(isNull || word.isEmpty()) return false;
		
		char l = word.charAt(0);
		
		if(letter == l) {
			if(isEnd && word.length() == 1) {
				isEnd = false;
				if(left.isNull && middle.isNull && right.isNull && !isEnd) {
					nullify();
					return true;
				} else {
					return false;
				}
			}
			if(middle._remove(word.substring(1))) {
				if(left.isNull && middle.isNull && right.isNull && !isEnd) {
					nullify();
					return true;
				} else {
					return false;
				}
			} return false;
		} else if(l < letter) {
			if(left._remove(word)) {
				if(left.isNull && middle.isNull && right.isNull && !isEnd) {
					nullify();
					return true;
				} else {
					return false;
				}
			} return false;
		} else {
			if(right._remove(word)) {
				if(left.isNull && middle.isNull && right.isNull && !isEnd) {
					nullify();
					return true;
				} else {
					return false;
				}
			} return false;
		}
	}
//	return false;

	@Override
	public boolean lookup(String word) {
		if(isNull || word.isEmpty()) return false;
		
		char l = word.charAt(0);
		
		if(letter == l) {
			if(isEnd && word.length() == 1) return true;
			return middle.lookup(word.substring(1));
		} else if(l < letter) {
			return left.lookup(word);
		} else {
			return right.lookup(word);
		}
	}

	@Override
	public int count() {
		if(isNull) return 0;
		
		return ((isEnd) ? 1 : 0) + left.count() + middle.count() + right.count();
	}

	@Override
	public List<String> listWords() {
		List<String> lw = new ArrayList<>();
		
		_listWords(lw, "");
		
		return lw;
	}
	
	private void _listWords(List<String> lw, String cur) {
		if(isNull) return;
		
		if(isEnd) {
			lw.add(cur+letter);
		}
		
		left._listWords(lw, cur);
		middle._listWords(lw, cur+letter);
		right._listWords(lw, cur);
	}

	@Override
	public int nbNullPointer() {
		return (isNull ? 1 : (left.nbNullPointer()+middle.nbNullPointer()+right.nbNullPointer()));
	}

	@Override
	public int height() {
		if(isNull) return 0;
		
		return 1+Math.max(Math.max(left.height(), right.height()), middle.height());
	}

	@Override
	public int avgDepth() {
		_depthsSum = 0;
		_nbLeaves  = 0;
		_avgDepth(0);
		
		return _depthsSum / _nbLeaves;
	}
	
	private void _avgDepth(int dep) {
		if(isNull) {
			_depthsSum += dep;
			_nbLeaves++;
			return;
		} 
		
		left._avgDepth(dep+1);
		middle._avgDepth(dep+1);
		right._avgDepth(dep+1);	
	}

	@Override
	public int nbPrefixed(String pref) {
		if(isNull) return 0;
		
		if(pref.isEmpty()) return count();
		
		char l = pref.charAt(0);
		
		if(letter == l) {
			return middle.nbPrefixed(pref.substring(1));
		} else if(l < letter) {
			return left.nbPrefixed(pref);
		} else {
			return right.nbPrefixed(pref);
		}
	}

	@Override
	public ITrie merge(ITrie trie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITrie convert() {
		PatriciaTrie pt = new PatriciaTrie();
		for(String w : listWords()) {
			pt.insert(w);
		}
		return pt;
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
		
		sb.append(me + " [label=\""+letter+"\""+
				((isEnd) ? ",shape=square" : ",shape=circle")
				+"]\n");
		sb.append(father + " -> " + me + "\n");
		
		left._draw(sb, me);
		middle._draw(sb, me);
		right._draw(sb, me);
		
	}
	
	public String toString() {
		return _toString("| ");
	}
	
	private String _toString(String blk) {
		if(isNull) {
			return "<>";
		} else {
			return "["+letter+"]\n"+blk+left._toString(blk+"| ")+"\n"+blk+middle._toString(blk+"| ")+"\n"+blk+right._toString(blk+"| ")+")";
		}
	}

}
