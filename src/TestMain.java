import datastructures.HybridTrie;
import datastructures.PatriciaTrie;
import interfaces.ITrie;

import java.util.List;


public class TestMain {
	public static void main(String[] args) {
		/**** c'est un peu brouillon, faudra surement modifier un peu des trucs ****/
		Benchmark_old b = new Benchmark_old();
		ITrie pt = null, ht = null;
		
		for(int i=1; i <= 5; i++) {
			b.initBench(new HybridTrie());
			b.insertBench("/users/nfs/Etu3/3300543/workspace/trie-again/data/test"+i+".in");
			System.out.println("Reading a total of "+b.getnChars()+" chars in Hybrid, time : "+b.getElapsed()+" µs");
			ht = b.getTree();
		}
		
		for(int i=1; i <= 5; i++) {
			b.initBench(new PatriciaTrie());
			b.insertBench("/users/nfs/Etu3/3300543/workspace/trie-again/data/test"+i+".in");
			System.out.println("Reading a total of "+b.getnChars()+" chars in PATRICIA, time : "+b.getElapsed()+" µs");
			pt = b.getTree();
		}
		
		b.initBench(null);
		b.lookupBench("/users/nfs/Etu3/3300543/workspace/trie-again/data/test5.in", pt);
		System.out.println("Looking for "+b.getnChars()+" chars in PATRICIA, time : "+b.getElapsed()+" µs");
		
		b.initBench(null);
		b.lookupBench("/users/nfs/Etu3/3300543/workspace/trie-again/data/test5.in", ht);
		System.out.println("Looking for "+b.getnChars()+" chars in Hybrid, time : "+b.getElapsed()+" µs");		
	
		
		
//		final String[] words2 = new String[]{
//                "A", "quel", "genial", "professeur", "de", "dactylographie", "sommes", "nous", "redevables", "de",
//                "la", "superbe", "phrase", "ci", "dessus", ",", "un", "modele", "du", "genre", ",", "que", "toute",
//                "dactylo", "connait", "par", "coeur", "puisque", "elle", "fait", "appel", "a", "chaqune", "des",
//                "touches", "du", "clavier", "de", "la", "machine", "a", "ecrire", "?" };
//		final String[] words = new String[]{
//				"bonjour", "banane", "baleine", "atome", "at", "c", "bateau"
//		};
//		
//		HybridTrie ht = new HybridTrie();
//		for(String w : words) {
//			ht.insert(w);
//		}
		
//		System.out.println(ht);
		
//		System.out.println(ht.lookup("banen"));
//		System.out.println(ht.lookup("banane"));
//		System.out.println(ht.lookup("at"));
//		System.out.println(ht.lookup("ati"));
//		System.out.println(ht.lookup("ato"));
//		System.out.println(ht.lookup("atome"));
//		for(; ht != null; ht = ht.middle) {
//			System.out.print(ht.letter);
//		}
//		System.out.println(ht.middle.middle.right.middle.middle.letter);
		
//		System.out.println(ht.draw());
//		System.out.println("Count = "+ht.count());
//		System.out.println(ht.listWords());
//		
//		System.out.println("Nb null = "+ht.nbNullPointer());
//		System.out.println("Height = "+ht.height());
//		System.out.println("Average depth = "+ht.avgDepth());
//		
//		System.out.println("#words starting by 'ba' = "+ht.nbPrefixed("ba"));
//		System.out.println("#words starting by 'a' = "+ht.nbPrefixed("a"));
//		System.out.println("#words starting by 'ki' = "+ht.nbPrefixed("ki"));
		
//		ht.remove("banane");
//		ht.remove("atome");
//		ht.remove("hello");
//		ht.remove("c");
//		
//		System.out.println(ht.listWords());
	}
}
