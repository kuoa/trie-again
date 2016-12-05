import datastructures.HybridTrie;
import datastructures.PHybridTrie;
import datastructures.PatriciaTrie;
import interfaces.ITrie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class TestMain {
	public static void main(String[] args) {

		
		
		final String[] words2 = new String[]{
                "A", "quel", "genial", "professeur", "de", "dactylographie", "sommes", "nous", "redevables", "de",
                "la", "superbe", "phrase", "ci", "dessus", ",", "un", "modele", "du", "genre", ",", "que", "toute",
                "dactylo", "connait", "par", "coeur", "puisque", "elle", "fait", "appel", "a", "chaqune", "des",
                "touches", "du", "clavier", "de", "la", "machine", "a", "ecrire", "?" };
		final String[] words = new String[]{
				"caca", "boudin", "sucette", "bukake", "procrastination", "sexe", "boulot", "prog",
				"manette", "hésitation", "zombie", "cacahuète"
		};
		final String[] words3 = new String[]{
				"aaa", "bbb", "ccc", "ddd", "eee", "fff", "ggg", "hhh", "iii"
		};
		
//		
		PHybridTrie ht = new PHybridTrie();
		for(String w : words) {
			ht.insert(w);
		}
		
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
		
		System.out.println(ht.draw());
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
