package datastructures;

public class Tuple<A, B> {
	public A prefix;
	public B child;
	
	public Tuple(A _prefix, B _child) {
		prefix = _prefix;
		child = _child;
	}
}
