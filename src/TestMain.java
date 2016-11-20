import datastructures.PatriciaTrie;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import java.util.List;


public class TestMain {

	public static void main(String[] args) {

        final String[] words = new String[]{
                "A", "quel", "genial", "professeur", "de", "dactylographie", "sommes", "nous", "redevables", "de",
                "la", "superbe", "phrase", "ci", "dessus", ",", "un", "modele", "du", "genre", ",", "que", "toute",
                "dactylo", "connait", "par", "coeur", "puisque", "elle", "fait", "appel", "a", "chaqune", "des",
                "touches", "du", "clavier", "de", "la", "machine", "a", "ecrire", "?" };


        PatriciaTrie trie = new PatriciaTrie();




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
        System.out.println("height " + trie.height());
        System.out.println("avg depth " + trie.avgDepth());





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

        List<String> listWords = trie.listWords();
        for(String w : listWords){
            System.out.println(w);
        }

        System.out.println(trie.nbPrefixed("ho"));
        System.out.println(trie.draw());
        System.out.println("height " + trie.height());
        System.out.println("avg depth " + trie.avgDepth());
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
