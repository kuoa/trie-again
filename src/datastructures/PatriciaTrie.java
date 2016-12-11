package datastructures;

import interfaces.ITrie;

import java.util.*;
import java.util.Map.Entry;

public class PatriciaTrie implements ITrie {
	Hashtable<Character, Tuple<String, PatriciaTrie>> data;

	public final static Character epsilon = '#';
	private static Integer id = 0; /* used for drawing */
	private static final int DEPTH_SUM = 0; /* used for average depth */
	private static final int NULL_POINTERS_SUM = 1; /* used for average depth */

	public PatriciaTrie() {
		data = new Hashtable<>();
	}

	@Override
	public void insert(String _word) {

		if (_word.isEmpty()) {
			return;
		}

		String word = _word.endsWith(epsilon.toString()) ? _word : _word + epsilon;
		Character key = word.charAt(0);

		if (data.containsKey(key)) {
			/* key is present */
			Tuple<String, PatriciaTrie> value = data.get(key);
			PatriciaTrie child = value.child;
			String prefix = value.prefix;

			/* word is present */
			if (word.equals(prefix)) {
				return;
			}

			int commonPrefixIndex = getCommonPrefixIndex(word, prefix);
			String commonPrefix = prefix.substring(0, commonPrefixIndex);

			/* prefix of same length, insert in children */
			if (commonPrefix.length() == prefix.length()) {
				String suffix = word.substring(commonPrefixIndex);
				child.insert(suffix);

			} else {
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
		} else {
			/* key is not present */
			Tuple<String, PatriciaTrie> value = new Tuple<>(word, null);
			data.put(key, value);
		}
	}

	@Override
	public void remove(String _word) {

		if (_word.isEmpty()) {
			return;
		}

		String word = _word.endsWith(epsilon.toString()) ? _word : _word + epsilon;
		Character key = word.charAt(0);

		if (data.containsKey(key)) {
			/* key is present */
			Tuple<String, PatriciaTrie> value = data.get(key);
			PatriciaTrie child = value.child;
			String prefix = value.prefix;

			/* word found */
			if (word.equals(prefix)) {
				data.remove(key);
				return;
			}

			int commonPrefixIndex = getCommonPrefixIndex(word, prefix);
			String commonPrefix = prefix.substring(0, commonPrefixIndex);

			/* prefix of same length, delete in children */
			if (commonPrefix.length() == prefix.length()) {
				String suffix = word.substring(commonPrefixIndex);
				child.remove(suffix);

				/* only one son left, rearrange */
				if (child.data.size() == 1) {

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

		if (_word.isEmpty()) {
			return false;
		}

		String word = _word.endsWith(epsilon.toString()) ? _word : _word + epsilon;
		Character key = word.charAt(0);

		if (data.containsKey(key)) {
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
			if (commonPrefix.length() == prefix.length()) {
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

		while (!stack.empty()) {
			PatriciaTrie trie = stack.pop();

			for (Entry<Character, Tuple<String, PatriciaTrie>> e : trie.data.entrySet()) {
				Tuple<String, PatriciaTrie> tuple = e.getValue();

				/* word found */ /* same as tuple.child == null */
				if (tuple.prefix.endsWith(epsilon.toString())) {
					numberWords++;
				} else {
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

		/*
		 * since we are using a hashmap, there is no way to traverse the trie in
		 * alphabetical order
		 */
		/* we have no option but to sort */
		Collections.sort(listWords);

		return listWords;
	}

	private void listWordsRecursive(PatriciaTrie rootTrie, String rootWord, List<String> listWords) {

		for (Entry<Character, Tuple<String, PatriciaTrie>> e : rootTrie.data.entrySet()) {
			Tuple<String, PatriciaTrie> tuple = e.getValue();
			String prefix = tuple.prefix;

			if (prefix.endsWith(epsilon.toString())) {
				/* word found | remove epsilon */
				String wordFound = rootWord + (prefix.substring(0, prefix.length() - 1));
				listWords.add(wordFound);
			} else {
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

		while (!stack.empty()) {
			PatriciaTrie trie = stack.pop();

			for (Entry<Character, Tuple<String, PatriciaTrie>> e : trie.data.entrySet()) {
				Tuple<String, PatriciaTrie> tuple = e.getValue();

				/* word found */ /* same as tuple.child == null */
				if (tuple.child == null) {
					nullPointers++;
				} else {
					stack.push(tuple.child);
				}
			}
		}
		return nullPointers;
	}

	@Override
	public int height() {

		int height = 0;

		for (Entry<Character, Tuple<String, PatriciaTrie>> e : data.entrySet()) {
			Tuple<String, PatriciaTrie> tuple = e.getValue();

			if (tuple.child == null)
				continue;

			int newHeight = tuple.child.height();

			if (newHeight > height) {
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

	/*
	 * rez[DEPTH_SUM] = sum of depths | rez[NULL_POINTERS_SUM] = nb of null
	 * nodes
	 */
	private void avgDepthRecur(PatriciaTrie rootTrie, int fatherDepth, int[] result) {

		for (Entry<Character, Tuple<String, PatriciaTrie>> e : rootTrie.data.entrySet()) {
			Tuple<String, PatriciaTrie> tuple = e.getValue();

			if (tuple.child == null) {
				result[DEPTH_SUM] += fatherDepth + 1;
				result[NULL_POINTERS_SUM]++;
				continue;
			}

			avgDepthRecur(tuple.child, fatherDepth + 1, result);
		}

	}

	@Override
	public int nbPrefixed(String _word) {

		if (_word.isEmpty()) {
			return 0;
		}

		String word = _word.endsWith(epsilon.toString()) ? _word : _word + epsilon;
		Character key = word.charAt(0);

		if (data.containsKey(key)) {
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
			if (commonPrefixIndex == word.length() - 1) {
				return child.count();
			}

			String commonPrefix = prefix.substring(0, commonPrefixIndex);
			/* prefix of same length, search in children */
			if (commonPrefix.length() == prefix.length()) {
				String suffix = word.substring(commonPrefixIndex);
				return child.nbPrefixed(suffix);
			}
		}

		/* word not found */
		return 0;
	}

	public void merge(PatriciaTrie mergeTrie) {

		/* long live the users */
		if (mergeTrie == null) {
			return;
		}

		PatriciaTrie primaryTrie = this;

		for (Entry<Character, Tuple<String, PatriciaTrie>> e : mergeTrie.data.entrySet()) {
			Tuple<String, PatriciaTrie> mergeTuple = e.getValue();
			Character mergeKey = e.getKey();

			if (primaryTrie.data.containsKey(mergeKey)) {
				Tuple<String, PatriciaTrie> primaryTuple = primaryTrie.data.get(mergeKey);

				/* same prefix */
				/* AL == AL */
				if (primaryTuple.prefix.equals(mergeTuple.prefix)) {
					if (mergeTuple.child != null) {
						primaryTuple.child.merge(mergeTuple.child);
					}
					continue;
				}

				/* get common prefix */
				int commonPrefixIndex = getCommonPrefixIndex(primaryTuple.prefix, mergeTuple.prefix);
				String commonPrefix = primaryTuple.prefix.substring(0, commonPrefixIndex);

				/* common prefix same size as primaryPrefix */
				/* AL <-> ALASKA */
				if (commonPrefix.length() == primaryTuple.prefix.length()) {
					String mergeSuffix = mergeTuple.prefix.substring(commonPrefixIndex);

					Tuple<String, PatriciaTrie> newMergeTuple = new Tuple<>(mergeSuffix, mergeTuple.child);
					PatriciaTrie newMergePat = new PatriciaTrie();
					newMergePat.data.put(mergeSuffix.charAt(0), newMergeTuple);

					primaryTuple.child.merge(newMergePat);
					continue;
				}

				/* common prefix same size as mergePrefix */
				/* ALASKA <-> AL */
				if (commonPrefix.length() == mergeTuple.prefix.length()) {

					String primarySuffix = primaryTuple.prefix.substring(commonPrefixIndex);

					Tuple<String, PatriciaTrie> newPrimaryTuple = new Tuple<>(primarySuffix, primaryTuple.child);
					PatriciaTrie newPrimaryPat = new PatriciaTrie();
					newPrimaryPat.data.put(primarySuffix.charAt(0), newPrimaryTuple);

					/* replace node */
					primaryTrie.data.put(commonPrefix.charAt(0), mergeTuple);
					mergeTuple.child.merge(newPrimaryPat);
					continue;
				}

				/*
				 * common prefix size different than primaryPrefix and
				 * mergePrefix
				 */
				/* ABC <-> ACD */

				PatriciaTrie commonTrie = new PatriciaTrie();

				/* BC */
				String primarySuffix = primaryTuple.prefix.substring(commonPrefixIndex);
				Tuple<String, PatriciaTrie> newPrimaryTuple = new Tuple<>(primarySuffix, primaryTuple.child);
				commonTrie.data.put(primarySuffix.charAt(0), newPrimaryTuple);

				/* CD */
				String mergeSuffix = mergeTuple.prefix.substring(commonPrefixIndex);
				Tuple<String, PatriciaTrie> newMergeTuple = new Tuple<>(mergeSuffix, mergeTuple.child);
				commonTrie.data.put(mergeSuffix.charAt(0), newMergeTuple);

				/* replace old node */
				Tuple<String, PatriciaTrie> commonTuple = new Tuple<>(commonPrefix, commonTrie);
				primaryTrie.data.put(commonPrefix.charAt(0), commonTuple);
			} else {
				/* key not present | insert whole branch */
				primaryTrie.data.put(mergeKey, mergeTuple);
			}
		}
	}

	@Override
	public HybridTrie convert() {

		HybridTrie hybridTrie = new HybridTrie();
		
		Node n = null;
				
		for (Entry<Character, Tuple<String, PatriciaTrie>> e : data.entrySet()) {
			n = convertRecur(e.getValue(), n, null);
		}	
		
		hybridTrie.root = n;
		
		return hybridTrie;
	}
	
	/**
	 * Converts recursively a tuple to the equivalent Hybrid Tree
	 * @param tuple : tuple to be converted
	 * @param cNode	: current Node of the Hybrid Tree where insertion will continue
	 * @param pNode : previous Node of the Hybrid Tree used in case of a end word
	 */

	private Node convertRecur(Tuple<String, PatriciaTrie> tuple, Node cNode, Node pNode) {

		Node current = cNode;		/* current Node being tracked */
		Node prev = pNode;		/* previous Node beeing tracked */
		String prefix = tuple.prefix;
						
		if (current == null) {
			/* empty node -> add to middle */		
			
			for (int i = 0; i < prefix.length(); i++) {
				Character c = prefix.charAt(i);

				if (c.equals(epsilon)) {					
					prev.isEnd = true;					
				} else {
					current = new Node(c);
					cNode = current;
					prev = current;					
					current = current.middle;
				}				
			}
			
			PatriciaTrie child = tuple.child;
			
			if (child != null) {
				current = prev;
				for (Entry<Character, Tuple<String, PatriciaTrie>> e : child.data.entrySet()) {
					current = convertRecur(e.getValue(), cNode.middle, cNode);
				}
			}
			return current;
		}		
		else if(prefix.charAt(0) < cNode.letter){
			/* left insertion */
			cNode.left = convertRecur(tuple, cNode.left, prev);
			return cNode;
		}		
		else{
			/* right insertion */
			cNode.right = convertRecur(tuple, cNode.right, prev);
			return cNode;
		}
	}

	
	@Override
	public String toString() {
		String s = "(";

		for (Entry<Character, Tuple<String, PatriciaTrie>> e : data.entrySet()) {
			Tuple<String, PatriciaTrie> v = e.getValue();

			s += v.prefix + "[" + ((v.child == null) ? "<null>" : v.child.toString()) + "]";
		}

		return s + ")";
	}

	@Override
	public String draw() {
		StringBuilder dotEntry = new StringBuilder();
		dotEntry.append("digraph PatriciaTrie {\n");

		drawRecursive(this, "<-RootNode->", dotEntry);

		dotEntry.append("}\n");

		return dotEntry.toString();
	}

	private void drawRecursive(PatriciaTrie rootTrie, String father, StringBuilder dotEntry) {

		for (Entry<Character, Tuple<String, PatriciaTrie>> e : rootTrie.data.entrySet()) {
			Tuple<String, PatriciaTrie> tuple = e.getValue();

			dotEntry.append("\t" + (++id) + " [label=\"" + tuple.prefix + "\"]\n");
			dotEntry.append("\t" + father + " -> " + id + "\n");

			if (tuple.child != null) {
				drawRecursive(tuple.child, id.toString(), dotEntry);
			}
		}
	}

	private int getCommonPrefixIndex(String a, String b) {

		int minLength = Math.min(a.length(), b.length());
		int i;

		for (i = 0; i < minLength; i++) {
			if (a.charAt(i) != b.charAt(i)) {
				break;
			}
		}

		return i;
	}

	public PatriciaTrie clone() {
		PatriciaTrie cPatriciaTrie = new PatriciaTrie();

		for (Entry<Character, Tuple<String, PatriciaTrie>> e : data.entrySet()) {
			Tuple<String, PatriciaTrie> tuple = e.getValue();

			Tuple<String, PatriciaTrie> cTuple = new Tuple<>(tuple.prefix,
					tuple.child == null ? tuple.child : tuple.child.clone());
			cPatriciaTrie.data.put(e.getKey(), cTuple);
		}

		return cPatriciaTrie;
	}

	public List<String> getWordsUnsorted() {
		List<String> listWords = new ArrayList<>();

		listWordsRecursive(this, "", listWords);

		return listWords;
	}

}