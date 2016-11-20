package datastructures;

import interfaces.ITrie;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.*;
import java.util.Map.Entry;

public class PatriciaTrie implements ITrie {
    Hashtable<Character, Tuple<String, PatriciaTrie>> data;

    public final static Character epsilon = '#';
    private static Integer id = 0; /* used for drawing */
    private final int DEPTH_SUM = 0; /* used for average depth */
    private final int NULL_POINTERS_SUM = 1; /* used for average depth */

    public PatriciaTrie() {
        data = new Hashtable<>();
    }

    @Override
    public void insert(String _word) {

        if(_word.isEmpty()){
            return;
        }

        String word = _word.endsWith(epsilon.toString()) ? _word : _word + epsilon;
        Character key = word.charAt(0);

        if(data.containsKey(key)){
            /* key is present */
            Tuple<String, PatriciaTrie> value = data.get(key);
            PatriciaTrie child = value.child;
            String prefix = value.prefix;

            /* word is present */
            if(word.equals(prefix)){
                return;
            }

            int commonPrefixIndex = getCommonPrefixIndex(word, prefix);
            String commonPrefix = prefix.substring(0, commonPrefixIndex);

            /* prefix of same length, insert in children */
            if(commonPrefix.length() == prefix.length()){
                String suffix = word.substring(commonPrefixIndex);
                child.insert(suffix);

            }else{
                PatriciaTrie newSon = new PatriciaTrie();

                /* uncommon part */
                String oldRest = prefix.substring(commonPrefixIndex);

                /* insert uncommon part and his child */
                Tuple<String, PatriciaTrie> oldChildTuple = new Tuple<>(oldRest, child);
                newSon.data.put(oldRest.charAt(0), oldChildTuple);

                /* insert new word */
                String newRest = word.substring(commonPrefixIndex);
                Tuple<String, PatriciaTrie> newChildTuple = new Tuple<>(newRest, null);
                newSon.data.put(newRest.charAt(0), newChildTuple);

                /* new tree */
                Tuple<String, PatriciaTrie> newNode = new Tuple<>(commonPrefix, newSon);
                data.put(commonPrefix.charAt(0), newNode);
            }
        }
        else{
			/* key is not present */
            Tuple<String, PatriciaTrie> value = new Tuple<>(word, null);
            data.put(key, value);
        }
    }

    @Override
    public void remove(String _word) {

        if(_word.isEmpty()){
            return;
        }

        String word = _word.endsWith(epsilon.toString()) ? _word : _word + epsilon;
        Character key = word.charAt(0);

        if(data.containsKey(key)){
            /* key is present */
            Tuple<String, PatriciaTrie> value = data.get(key);
            PatriciaTrie child = value.child;
            String prefix = value.prefix;

            /* word found */
            if(word.equals(prefix)){
                data.remove(key);
                return;
            }

            int commonPrefixIndex = getCommonPrefixIndex(word, prefix);
            String commonPrefix = prefix.substring(0, commonPrefixIndex);

             /* prefix of same length, delete in children */
            if(commonPrefix.length() == prefix.length()){
                String suffix = word.substring(commonPrefixIndex);
                child.remove(suffix);

                /* only one son left, rearrange */
                if(child.data.size() == 1){

                    /* get the son */
                    Tuple<String, PatriciaTrie> sonValue = child.data.entrySet().iterator().next().getValue();
                    PatriciaTrie sonTrie = sonValue.child;
                    String sonPrefix = sonValue.prefix;

                    /* rearrange current node */
                    String newPrefix = prefix + sonPrefix;
                    Tuple<String, PatriciaTrie> newValue = new Tuple<>(newPrefix, sonTrie);
                    data.put(key, newValue);
                }
            }

            /* word not found */
        }

    }

    @Override
    public boolean lookup(String _word) {

        if(_word.isEmpty()){
            return false;
        }

        String word = _word.endsWith(epsilon.toString()) ? _word : _word + epsilon;
        Character key = word.charAt(0);

        if(data.containsKey(key)) {
            /* key is present */
            Tuple<String, PatriciaTrie> value = data.get(key);
            PatriciaTrie child = value.child;
            String prefix = value.prefix;

            /* word found */
            if (word.equals(prefix)) {
                return true;
            }

            int commonPrefixIndex = getCommonPrefixIndex(word, prefix);
            String commonPrefix = prefix.substring(0, commonPrefixIndex);

            /* prefix of same length, search in children */
            if(commonPrefix.length() == prefix.length()) {
                String suffix = word.substring(commonPrefixIndex);
                return child.lookup(suffix);
            }
        }

        /* word not found */
        return false;
    }

    @Override
    public int count() {

        int numberWords = 0;

        Stack<PatriciaTrie> stack = new Stack<>();
        stack.push(this);

        while(!stack.empty()){
            PatriciaTrie trie = stack.pop();

            for(Entry<Character, Tuple<String, PatriciaTrie>> e : trie.data.entrySet()){
                Tuple<String, PatriciaTrie> tuple = e.getValue();

                /* word found */ /* same as tuple.child == null */
                if(tuple.prefix.endsWith(epsilon.toString())){
                    numberWords++;
                }
                else{
                    stack.push(tuple.child);
                }
            }
        }
        return numberWords;
    }

    @Override
    public List<String> listWords() {
        List<String> listWords = new ArrayList<>();

        listWordsRecursive(this, "", listWords);

        /* since we are using a hashmap, there is no way to traverse the trie in alphabetical order */
        /* we have no option but to sort */
        Collections.sort(listWords);

        return listWords;
    }

    private void listWordsRecursive(PatriciaTrie rootTrie, String rootWord, List<String> listWords){

        for(Entry<Character, Tuple<String, PatriciaTrie>> e : rootTrie.data.entrySet()) {
            Tuple<String, PatriciaTrie> tuple = e.getValue();
            String prefix = tuple.prefix;

            if(prefix.endsWith(epsilon.toString())){
                /* word found | remove epsilon */
                String wordFound = rootWord + (prefix.substring(0, prefix.length() - 1));
                listWords.add(wordFound);
            }else{
                String newWord = rootWord + prefix;
                listWordsRecursive(tuple.child, newWord, listWords);
            }
        }
    }

    @Override
    public int nbNullPointer() {
        int nullPointers = 0;

        Stack<PatriciaTrie> stack = new Stack<>();
        stack.push(this);

        while(!stack.empty()){
            PatriciaTrie trie = stack.pop();

            for(Entry<Character, Tuple<String, PatriciaTrie>> e : trie.data.entrySet()){
                Tuple<String, PatriciaTrie> tuple = e.getValue();

                /* word found */ /* same as tuple.child == null */
                if(tuple.child == null){
                    nullPointers++;
                }
                else{
                    stack.push(tuple.child);
                }
            }
        }
        return nullPointers;
    }

    @Override
    public int height() {

        int height = 0;

        for(Entry<Character, Tuple<String, PatriciaTrie>> e : data.entrySet()) {
            Tuple<String, PatriciaTrie> tuple = e.getValue();

            if(tuple.child == null) continue;

            int newHeight = tuple.child.height();

            if(newHeight > height){
                height = newHeight;
            }
        }

        return 1 + height;
    }

    @Override
    public int avgDepth() {
        int[] result = new int[2];

        avgDepthRecur(this, 0, result);

        return result[DEPTH_SUM] / result[NULL_POINTERS_SUM];
    }

    /* rez[DEPTH_SUM] = sum of depths | rez[NULL_POINTERS_SUM] = nb of null nodes */
    private void avgDepthRecur(PatriciaTrie rootTrie, int fatherDepth, int[] result){

        for(Entry<Character, Tuple<String, PatriciaTrie>> e : rootTrie.data.entrySet()) {
            Tuple<String, PatriciaTrie> tuple = e.getValue();

            if(tuple.child == null){
                result[DEPTH_SUM] += fatherDepth + 1;
                result[NULL_POINTERS_SUM]++;
                continue;
            }

            avgDepthRecur(tuple.child, fatherDepth + 1, result);
        }

    }

    @Override
    public int nbPrefixed(String _word) {

        if(_word.isEmpty()){
            return 0;
        }

        String word = _word.endsWith(epsilon.toString()) ? _word : _word + epsilon;
        Character key = word.charAt(0);

        if(data.containsKey(key)) {
            /* key is present */
            Tuple<String, PatriciaTrie> value = data.get(key);
            PatriciaTrie child = value.child;
            String prefix = value.prefix;

            /* word found */
            if (word.equals(prefix)) {
                return 1;
            }

            int commonPrefixIndex = getCommonPrefixIndex(word, prefix);

            /* node found | count his children */
            if(commonPrefixIndex == word.length() - 1){
                return child.count();
            }

            String commonPrefix = prefix.substring(0, commonPrefixIndex);
            /* prefix of same length, search in children */
            if(commonPrefix.length() == prefix.length()) {
                String suffix = word.substring(commonPrefixIndex);
                return child.nbPrefixed(suffix);
            }
        }

        /* word not found */
        return 0;
    }

    @Override
    public ITrie merge(ITrie trie) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ITrie convert() {

        HybridTrie hybridTrie = new HybridTrie();

        for(String word : listWords()){
            hybridTrie.insert(word);
        }

        return hybridTrie;
    }

    @Override
    public String toString(){
        String s = "(";

        for(Entry<Character, Tuple<String, PatriciaTrie>> e : data.entrySet()){
            Tuple<String, PatriciaTrie> v = e.getValue();

            s += v.prefix + "[" + ((v.child == null) ? "<null>" : v.child.toString()) + "]";
        }

        return s + ")";
    }

    @Override
    public String draw(){
        StringBuilder dotEntry = new StringBuilder();
        dotEntry.append("digraph PatriciaTrie {\n");

        drawRecursive(this, "<-RootNode->", dotEntry);

        dotEntry.append("}\n");

        return dotEntry.toString();
    }

    private void drawRecursive(PatriciaTrie rootTrie, String father, StringBuilder dotEntry){

        for(Entry<Character, Tuple<String, PatriciaTrie>> e : rootTrie.data.entrySet()) {
            Tuple<String, PatriciaTrie> tuple = e.getValue();

            dotEntry.append("\t" + (++id) + " [label=\"" + tuple.prefix + "\"]\n");
            dotEntry.append("\t" + father + " -> " + id + "\n");

            if(tuple.child != null) {
                drawRecursive(tuple.child, id.toString(), dotEntry);
            }
        }
    }

    private int getCommonPrefixIndex(String a, String b){

        int minLength = Math.min(a.length(), b.length());
        int i;

        for(i = 0; i < minLength; i++){
            if(a.charAt(i) != b.charAt(i)){
                break;
            }
        }

        return i;
    }
}
