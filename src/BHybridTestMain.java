import datastructures.BalancedHybridTrie;
import datastructures.PatriciaTrie;

public class BHybridTestMain {
    public static void main(String[] args) {

        final String[] words = new String[]{
                "A", "quel", "genial", "professeur", "de", "dactylographie", 
                "sommes", "nous", "redevables", "de", "la", "superbe", 
                "phrase", "ci", "dessus", ",", "un", "modele", "du", 
                "genre", ",", "que", "toute", "dactylo", "connait", "par", 
                "coeur", "puisque", "elle", "fait", "appel", "a", "chaqune", 
                "des", "touches", "du", "clavier", "de", "la", "machine", "a", 
                "ecrire", "?" 
                };
        
        
        BalancedHybridTrie trie = new BalancedHybridTrie();
        
        System.out.println("Inserting words");
        for(String word : words){
        	trie.insert(word);
        }
        
        System.out.println("Number of words : " + trie.count());
        System.out.println("Height : " + trie.height());
        System.out.println("Average depth : " + trie.avgDepth());
        System.out.println("Listing words");
        
        for(String word : trie.listWords()){
        	System.out.println(word);
        }
        
        System.out.println("Visual representation");
        System.out.println(trie.draw());
        
        System.out.println("The word <genial> present ? " + trie.lookup("genial"));
        System.out.println("The word <ALGAV> present ? " + trie.lookup("ALGAV"));
        
        System.out.println("Words prefixed by <dac> " + trie.nbPrefixed("dac"));
        
        System.out.println("Converting to patricia trie");
        
        BalancedHybridTrie newTrie = new BalancedHybridTrie();
        
        newTrie.insert("home");
        newTrie.insert("my");
        newTrie.insert("mansion");
        newTrie.insert("mein");
        newTrie.insert("hi");
        newTrie.insert("hit");
        newTrie.insert("homeless");
        newTrie.insert("flow");
        newTrie.insert("mine");
        newTrie.insert("foam");
        newTrie.insert("flower");
        
        BalancedHybridTrie convertTrie = (BalancedHybridTrie) newTrie.clone();
        
        System.out.println(convertTrie.draw());
        
        PatriciaTrie pTrie = (PatriciaTrie) convertTrie.convert();
        
//        System.out.println(pTrie.draw());
        
        System.out.println("Removing <professeur>");
        trie.remove("professeur");
        System.out.println("Removing <ALGAV>");
        trie.remove("ALGAV");
//        System.out.println(trie.draw());                                                      

    }
}
