package datastructures;

public class Tuple<A, B> {
	public A pref;
	public B children;
	
	public Tuple(A _pref, B _children) {
		pref = _pref;
		children = _children;
	}
}
