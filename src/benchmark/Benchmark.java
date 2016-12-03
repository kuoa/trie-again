package benchmark;

import datastructures.HybridTrie;
import datastructures.PatriciaTrie;
import interfaces.ITrie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuoa on 11/26/16.
 */
public class Benchmark {

    private ITrie trie;
    private String input;
    private PrintWriter output;
    private double start;
    private double currentTime;
    private double totalTime;
    private List<String> words;

    private static final double SECOND = 1000000000.0;
    private static final String dataFolder = "./data/";
    private static final String dumpFolder = "./dump/";
    private static final String format = "%-28s";

    private static final String[] wordsToDelete = new String[]{
            "the", "life", "of", "king", "henry", "the", "eighth", "act", "i", "prologue", "i", "come",
            "no", "more", "to", "make", "you", "laugh", "things", "now", "that", "bear", "a", "weighty",
            "and", "a", "serious", "brow", "sad", "high", "and", "working", "full", "of", "state", "and",
            "woe", "such", "noble", "scenes", "as", "draw", "the", "eye", "to", "scene", "i",
            "king", "john", "s", "palace", "enter", "king", "john", "queen", "elinor", "pembroke",
            "essex", "salisbury", "and", "others", "with", "chatillon", "king", "john", "now", "say",
            "chatillon", "what", "would", "france", "with", "us", "chatillon", "thus", "after", "greeting",
            "speaks", "the", "king", "of", "france", "in", "my", "behavior", "to", "the", "majesty",
            "the", "borrow", "d", "majesty", "of", "england", "here", "queen", "elinor", "a", "strange",
            "beginning", "borrow", "d", "majesty", "king", "john", "silence", "good", "mother", "hear",
            "the", "embassy", "chatillon", "philip", "of", "france", "in", "right", "and", "true", "behalf",
            "of", "thy", "deceased", "brother", "geffrey", "s", "son", "arthur", "plantagenet",
            "lays", "most", "lawful", "claim", "to", "this", "street", "enter", "roderigo", "and", "iago",
            "roderigo", "tush", "never", "tell", "me", "i", "take", "it", "much", "unkindly", "that", "thou",
            "iago", "who", "hast", "had", "my", "purse", "as", "if", "the", "strings", "were", "thine",
            "shouldst", "know", "of", "serious", "brow", "sad", "high", "and", "working", "full", "of", "state",
            "and", "woe", "such", "noble", "scenes", "as", "draw", "the", "eye", "to", "flow", "we", "unfold",
            "would", "seem", "in", "me", "to", "affect", "speech", "and", "discourse", "hich", "now", "the",
            "manage", "of", "two", "kingdoms", "must", "with", "fearful", "bloody", "issue", "arbitrate", "king",
            "john", "our", "strong", "possession", "and", "our", "right", "for", "us", "queen", "elinor", "your",
            "strong", "possession", "much", "more", "than", "your", "right", "or", "else", "it", "must", "go", "wrong",
            "with", "you", "and", "me", "so", "much", "my", "conscience", "whispers", "in", "your", "ear", "which",
            "none", "but", "heaven", "and", "you", "and", "i", "shall", "hear", "enter", "a", "sheriff", "essex", "my",
            "liege", "here", "is", "the", "strangest", "controversy", "come", "from", "country", "to", "be", "judged",
            "by", "you", "that", "e", "er", "i", "heard", "shall", "i", "produce", "the", "men", "king", "john", "let",
            "them", "approach", "our", "abbeys", "and", "our", "priories", "shall", "pay", "this", "expedition", "s",
            "charge", "enter", "robert", "and", "the", "bastard", "what", "men", "are", "you", "bastard", "your",
            "faithful", "subject", "i", "a", "gentleman", "born", "in", "northamptonshire", "and", "eldest", "son",
            "as", "i", "suppose", "to", "robert", "faulconbridge", "a", "soldier", "by", "the", "honour", "giving",
            "hand", "of"};

    /**
     * Initialize benchmark parameters
     * @param _trie
     */
    public void initialize(ITrie _trie){
        trie = _trie;
        words = new ArrayList<>();
        start = 0;
        currentTime = 0;
        totalTime = 0;
    }

    /**
     * Set the benchmark input and load it into memory
     * @param _file
     */
    public void setInput(String _file){
        input = dataFolder.concat(_file);
        loadFile();
    }

    /**
     * Set the benchmark output
     * @param _file
     */
    public void setOutput(String _file){
        String outFile = dumpFolder.concat(_file);
        try {
            output = new PrintWriter(outFile, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load the input into memory
     */
    private void loadFile(){

        words.clear();

        System.out.println("Loading " + input + " into memory");

        try {
            BufferedReader br = new BufferedReader(new FileReader(input));
            String line;

            while ((line = br.readLine()) != null){
                words.add(line);
            }

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Build the tree containing the words from input
     * @param verbose displays real time information on console
     */
    public void build(boolean verbose){

        System.out.println("Starting insertion benchmark " + trie.getClass().getSimpleName());

        start = System.nanoTime();

        for(String word : words){
            trie.insert(word);
        }

        currentTime = (System.nanoTime() - start) / SECOND;
        totalTime += currentTime;

        output.printf(format, input);
        output.printf("%-28.5f", currentTime);
        output.printf("%-28.5f", totalTime);
        output.printf(format, words.size());
        output.printf(format, trie.count());
        output.printf(format, trie.height());
        output.printf(format, trie.avgDepth());
        output.print("\n");

        if(verbose) {
            System.out.println("CurrentTime[s] " + currentTime);
            System.out.println("TotalTime[s] " + totalTime);
            System.out.println("Words from input " + words.size());
            System.out.println("Words in trie " + trie.count());
            System.out.println("Height " + trie.height());
            System.out.println("Average height " + trie.avgDepth());
            System.out.println();
        }
    }

    public void remove(boolean verbose){

        System.out.println("Starting deletion benchmark " + trie.getClass().getSimpleName());

        start = System.nanoTime();

        for(String word : wordsToDelete){
            trie.remove(word);
        }

        currentTime = (System.nanoTime() - start) / SECOND;
        totalTime += currentTime;

        output.printf(format, input);
        output.printf("%-28.5f", currentTime);
        output.print("\n");

        if(verbose) {
            System.out.println("CurrentTime[s] " + currentTime);
            System.out.println("TotalTime[s] " + totalTime);
            System.out.println();
        }
    }


    /**
     * Build a trie containing all the words from the data folder
     * @param verbose displays real time information on console
     */
    public void buildFullTrie(boolean verbose){

        System.out.println("Building full trie for " + trie.getClass().getSimpleName());

        printHeader();

        for(String file : getFileNames()){
            /* loading content in memory */
            setInput(file);
            build(verbose);
        }

        output.printf("%s %-28.5f","Total Time", totalTime);
        output.close();
    }

    /**
     * Build a trie for each data input
     * @param verbose displays real time information on console
     */
    public void buildEachTrie(boolean verbose){

        System.out.println("Building each trie for " + trie.getClass().getSimpleName());

        printHeader();

        for(String file : getFileNames()){
            /* loading content in memory */
            if(trie instanceof PatriciaTrie){
                initialize(new PatriciaTrie());
            }
            else if(trie instanceof HybridTrie){
                initialize(new HybridTrie());
            }
            else{
                System.out.print("This should not happen :)");
            }

            setInput(file);
            build(verbose);
        }

        output.close();
    }


    /**
     * Build a trie for each data input
     * remove from each trie the words from "wordsToDelete"
     * @param verbose displays real time information on console
     */
    public void removeFromEachTrie(boolean verbose){

        System.out.println("Removing words from each trie for " + trie.getClass().getSimpleName());

        output.printf(format, "Input");
        output.printf(format, "CurrentTime[s]");
        output.print("\n");

        for(String file : getFileNames()){
            /* loading content in memory */
            if(trie instanceof PatriciaTrie){
                initialize(new PatriciaTrie());
            }
            else if(trie instanceof HybridTrie){
                initialize(new HybridTrie());
            }
            else{
                System.out.print("This should not happen :)");
            }

            setInput(file);

            for(String word : words){
                trie.insert(word);
            }
            remove(verbose);
        }

        output.close();
    }

    /**
     * @return a list of all input names from the data folder
     */
    private List<String> getFileNames(){
        List<String> fileNames = new ArrayList<>();

        File folder = new File(dataFolder);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                fileNames.add(listOfFiles[i].getName());
            }
        }
        return fileNames;

    }

    private void printHeader(){
        output.printf(format, "Input");
        output.printf(format, "CurrentTime[s]");
        output.printf(format, "TotalTime[s]");
        output.printf(format, "WordsInFile");
        output.printf(format, "WordsInTrie");
        output.printf(format, "Height");
        output.printf(format, "Average height");
        output.print("\n");
    }

    public double getTotalTime() { return totalTime; }
}
