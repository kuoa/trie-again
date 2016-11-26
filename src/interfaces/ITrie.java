package interfaces;

import java.util.List;

public interface ITrie {
	public void insert(String word);
	public void remove(String word);
	
	public boolean lookup(String word);
	public int count();
	public List<String> listWords();
	public int nbNullPointer();
	public int height();
	public int avgDepth(); /* double? */
	public int nbPrefixed(String pref);	

	public ITrie convert();

    /* DOT language */
	public String draw();
}
