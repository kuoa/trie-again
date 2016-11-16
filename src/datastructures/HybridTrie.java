package datastructures;

import interfaces.ITrie;

import java.util.List;

public class HybridTrie implements ITrie {
	
	public HybridTrie left, right, middle;
	public char letter;

	@Override
	public void insert(String word) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String word) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean lookup(String word) {
		// TODO Auto-generated method stub
		return false;
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

}
