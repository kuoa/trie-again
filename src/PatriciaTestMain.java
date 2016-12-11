import datastructures.HybridTrie;
import datastructures.HybridTrieOld;
import datastructures.PatriciaTrie;

import java.util.List;

/**
 * Created by kuoa on 11/26/16.
 */
public class PatriciaTestMain {
    public static void main(String[] args) {

        final String[] words = new String[]{
                "A", "quel", "genial", "professeur", "de", "dactylographie", "sommes", "nous", "redevables", "de",
                "la", "superbe", "phrase", "ci", "dessus", ",", "un", "modele", "du", "genre", ",", "que", "toute",
                "dactylo", "connait", "par", "coeur", "puisque", "elle", "fait", "appel", "a", "chaqune", "des",
                "touches", "du", "clavier", "de", "la", "machine", "a", "ecrire", "?" };


        PatriciaTrie trie = new PatriciaTrie();
        PatriciaTrie mergeWithTrie = new PatriciaTrie();        

        trie.insert("home");
        trie.insert("hi");
        trie.insert("hit");
        trie.insert("homeless");
        trie.insert("flow");
        trie.insert("foam");
        trie.insert("flower");
               

//        PatriciaTrie cloned = trie.clone();
//
//        System.out.println(trie.draw());
//        System.out.println(cloned.draw());
//
//        trie.insert("BBBBBBBB");
//        trie.remove("foam");
//        cloned.insert("CCCCC");
//
//        System.out.println(trie.draw());
//        System.out.println(cloned.draw());
        
        HybridTrie ht = trie.convert();
//        System.out.println(trie.draw());
        System.out.println(ht.draw());


        /*

        mergeWithTrie.insert("rainbow");
        mergeWithTrie.insert("rain");
        mergeWithTrie.insert("rainy");
        mergeWithTrie.insert("homeless");
        mergeWithTrie.insert("hord");
        mergeWithTrie.insert("homonyme");
        mergeWithTrie.insert("hole");
        mergeWithTrie.insert("fortune");


        System.out.println(trie.draw());
        System.out.println(mergeWithTrie.draw());

        trie.merge(mergeWithTrie);
        System.out.println(trie.draw());
        */



        /*
        int count = 0, oldCount = count;

        for(int i = 0; i < words.length; i++){
            trie.insert(words[i]);

            oldCount = count;
            count = trie.count();

            if(oldCount == count){
                System.out.println(words[i] + " is present");
            }

        }

        System.out.println(trie.count());
        System.out.println(trie.nbNullPointer());

        System.out.println(trie.height());

        List<String> listWords = trie.listWords();
        for(String w : listWords){
            System.out.println(w);
        }

        System.out.println("prefixed by");
        System.out.println(trie.nbPrefixed("de"));
        System.out.println(trie.draw());


       */

        /*
		//insertion
		trie.insert("home");
		trie.insert("homeless");
		trie.insert("bonjour");
		trie.insert("ho");

		System.out.println(trie);
        System.out.println(trie.count());

		trie.insert("bonjour");
		trie.insert("homeless");
        trie.insert("ho");
		System.out.println(trie);
        System.out.println(trie.count());
        System.out.println(trie.height());

        List<String> listWords = trie.listWords();
        for(String w : listWords){
            System.out.println(w);
        }

        System.out.println(trie.nbPrefixed("ho"));
        System.out.println(trie.draw());



*/
        //remove
        /*
        trie.insert("bonjour");
        trie.insert("bon");
        trie.insert("bonjournee");
        System.out.println(trie);
        System.out.println(trie.count());


        trie.remove("bon");
        System.out.println(trie);
        System.out.println(trie.count());
        trie.remove("bonjour");
        trie.remove("bonjournee");
        System.out.println(trie);
        System.out.println(trie.count());
*/

        /* lookup
        trie.insert("home");
        trie.insert("homeless");
        trie.insert("bonjour");
        trie.insert("ho");

        System.out.println(trie.lookup("home"));
        System.out.println(trie.lookup("ho"));
        System.out.println(trie.lookup("homeless"));
        System.out.println(trie.lookup("bonjour"));

        System.out.println(trie.lookup("homelesss"));
        System.out.println(trie.lookup("ahome"));
        System.out.println(trie.lookup("homee"));
        System.out.println(trie.lookup("homelless"));
        System.out.println(trie.lookup("zbonjour"));

        */



    }
}
