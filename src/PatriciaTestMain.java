import datastructures.HybridTrie;
import datastructures.HybridTrieOld;
import datastructures.PatriciaTrie;

/**
 * Created by kuoa on 11/26/16.
 */
public class PatriciaTestMain {
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
        
        
        PatriciaTrie trie = new PatriciaTrie();
        
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
                
        
        System.out.println("Merging two tries");
        
        
        PatriciaTrie mergeTrie = new PatriciaTrie();
                
        mergeTrie.insert("home");
        mergeTrie.insert("hi");
        mergeTrie.insert("hit");
        mergeTrie.insert("homeless");
        mergeTrie.insert("flow");
        mergeTrie.insert("foam");
        mergeTrie.insert("flower");
        
        PatriciaTrie convertTrie = mergeTrie.clone();
        
        System.out.println(trie.draw());
        
        PatriciaTrie mergeWithTrie = new PatriciaTrie();
       
        mergeWithTrie.insert("rainbow");
        mergeWithTrie.insert("rain");
        mergeWithTrie.insert("rainy");
        mergeWithTrie.insert("homeless");
        mergeWithTrie.insert("hord");
        mergeWithTrie.insert("homonyme");
        mergeWithTrie.insert("hole");
        mergeWithTrie.insert("fortune");
        
        System.out.println(mergeWithTrie.draw());
        
        System.out.println("Merging");
        mergeTrie.merge(mergeWithTrie);
        
        System.out.println(mergeTrie.draw());
        
        System.out.println("Converting to hybrid tree");
        
        HybridTrie hybridTrie = convertTrie.convert();
        
        System.out.println(hybridTrie.draw());
        
        System.out.println("Removing <professeur>");
        trie.remove("professeur");
        System.out.println("Removing <ALGAV>");
        trie.remove("ALGAV");
        System.out.println(trie.draw());                                                      

    }
}
