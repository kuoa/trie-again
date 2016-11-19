package datastructures;

import interfaces.ITrie;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

public class PatriciaTrie implements ITrie {
	Hashtable<Character, Tuple<String, PatriciaTrie>> data;	
	
	public final static Character epsilon = '#';
	
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
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<String> listWords() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int nbNullPointer() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int height() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int avgDepth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int nbPrefixed(String pref) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ITrie merge(ITrie trie) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITrie convert() {
		// TODO Auto-generated method stub
		return null;
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
	
	public static int getCommonPrefixIndex(String a, String b){
		
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
