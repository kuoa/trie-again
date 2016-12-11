import datastructures.BalancedHybridTrie;
import datastructures.HybridTrie;
import datastructures.PatriciaTrie;


public class TestMain {
	public static void main(String[] args) {

		
		
//		final String[] words = new String[]{
//                "A", "quel", "genial", "professeur", "de", "dactylographie", "sommes", "nous", "redevables", "de",
//                "la", "superbe", "phrase", "ci", "dessus", ",", "un", "modele", "du", "genre", ",", "que", "toute",
//                "dactylo", "connait", "par", "coeur", "puisque", "elle", "fait", "appel", "a", "chaqune", "des",
//                "touches", "du", "clavier", "de", "la", "machine", "a", "ecrire", "?" };
		
		String str = "bonjour je suis un texte exemple et je contiens un paquet "
				+ "de mots juste pour remplir ce tableau avec nimporte quoi meme"
				+ " si cela na aucun sens et en plus cette phrase ne se termine"
				+ " jamais oh mon dieu cest vraiment incroyable a quel point"
				+ "ca ne fini pas a ce quil parait cette phrase peut atteindre"
				+ " les mille caracteres";
		String[] words = str.split(" ");
		
//		
		HybridTrie ht = new BalancedHybridTrie();
		for(String w : words) {
			ht.insert(w);
		}		
		
//		PatriciaTrie pt = (PatriciaTrie)ht.convert();
//		PatriciaTrie pt2 = ht.convert_S();
//		System.out.println("pat " + pt2.count());
//		System.out.println("Pt = "+pt.listWords().size()+"\nHt = "+ht.listWords().size());
		
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
