package benchmark;

import datastructures.BalancedHybridTrie;
import datastructures.HybridTrie;
import datastructures.PatriciaTrie;
import interfaces.ITrie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

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
    private static final String formatString = "%-28s";
    private static final String formatDouble = "%-28.5f";


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
    
   private static final String[] wordsToLookup = new String[]{"a", "ability", "able", "about", "above", "accept", "according", "account", "across", "act", "action", "activity", "actually", "add", "address", "administration", "admit", "adult", "affect", "after", "again", "against", "age", "agency", "agent", "ago", "agree", "agreement", "ahead", "air", "all", "allow", "almost", "alone", "along", "already", "also", "although", "always", "American", "among", "amount", "analysis", "and", "animal", "another", "answer", "any", "anyone", "anything", "appear", "apply", "approach", "area", "argue", "arm", "around", "arrive", "art", "article", "artist", "as", "ask", "assume", "at", "attack", "attention", "attorney", "audience", "author", "authority", "available", "avoid", "away", "baby", "back", "bad", "bag", "ball", "bank", "bar", "base", "be", "beat", "beautiful", "because", "become", "bed", "before", "begin", "behavior", "behind", "believe", "benefit", "best", "better", "between", "beyond", "big", "bill", "billion", "bit", "black", "blood", "blue", "board", "body", "book", "born", "both", "box", "boy", "break", "bring", "brother", "budget", "build", "building", "business", "but", "buy", "by", "call", "camera", "campaign", "can", "cancer", "candidate", "capital", "car", "card", "care", "career", "carry", "case", "catch", "cause", "cell", "center", "central", "century", "certain", "certainly", "chair", "challenge", "chance", "change", "character", "charge", "check", "child", "choice", "choose", "church", "citizen", "city", "civil", "claim", "class", "clear", "clearly", "close", "coach", "cold", "collection", "college", "color", "come", "commercial", "common", "community", "company", "compare", "computer", "concern", "condition", "conference", "Congress", "consider", "consumer", "contain", "continue", "control", "cost", "could", "country", "couple", "course", "court", "cover", "create", "crime", "cultural", "culture", "cup", "current", "customer", "cut", "dark", "data", "daughter", "day", "dead", "deal", "death", "debate", "decade", "decide", "decision", "deep", "defense", "degree", "Democrat", "democratic", "describe", "design", "despite", "detail", "determine", "develop", "development", "die", "difference", "different", "difficult", "dinner", "direction", "director", "discover", "discuss", "discussion", "disease", "do", "doctor", "dog", "door", "down", "draw", "dream", "drive", "drop", "drug", "during", "each", "early", "east", "easy", "eat", "economic", "economy", "edge", "education", "effect", "effort", "eight", "either", "election", "else", "employee", "end", "energy", "enjoy", "enough", "enter", "entire", "environment", "environmental", "especially", "establish", "even", "evening", "event", "ever", "every", "everybody", "everyone", "everything", "evidence", "exactly", "example", "executive", "exist", "expect", "experience", "expert", "explain", "eye", "face", "fact", "factor", "fail", "fall", "family", "far", "fast", "father", "fear", "federal", "feel", "feeling", "few", "field", "fight", "figure", "fill", "film", "final", "finally", "financial", "find", "fine", "finger", "finish", "fire", "firm", "first", "fish", "five", "floor", "fly", "focus", "follow", "food", "foot", "for", "force", "foreign", "forget", "form", "former", "forward", "four", "free", "friend", "from", "front", "full", "fund", "future", "game", "garden", "gas", "general", "generation", "get", "girl", "give", "glass", "go", "goal", "good", "government", "great", "green", "ground", "group", "grow", "growth", "guess", "gun", "guy", "hair", "half", "hand", "hang", "happen", "happy", "hard", "have", "he", "head", "health", "hear", "heart", "heat", "heavy", "help", "her", "here", "herself", "high", "him", "himself", "his", "history", "hit", "hold", "home", "hope", "hospital", "hot", "hotel", "hour", "house", "how", "however", "huge", "human", "hundred", "husband", "I", "idea", "identify", "if", "image", "imagine", "impact", "important", "improve", "in", "include", "including", "increase", "indeed", "indicate", "individual", "industry", "information", "inside", "instead", "institution", "interest", "interesting", "international", "interview", "into", "investment", "involve", "issue", "it", "item", "its", "itself", "job", "join", "just", "keep", "key", "kid", "kill", "kind", "kitchen", "know", "knowledge", "land", "language", "large", "last", "late", "later", "laugh", "law", "lawyer", "lay", "lead", "leader", "learn", "least", "leave", "left", "leg", "legal", "less", "let", "letter", "level", "lie", "life", "light", "like", "likely", "line", "list", "listen", "little", "live", "local", "long", "look", "lose", "loss", "lot", "love", "low", "machine", "magazine", "main", "maintain", "major", "majority", "make", "man", "manage", "management", "manager", "many", "market", "marriage", "material", "matter", "may", "maybe", "me", "mean", "measure", "media", "medical", "meet", "meeting", "member", "memory", "mention", "message", "method", "middle", "might", "military", "million", "mind", "minute", "miss", "mission", "model", "modern", "moment", "money", "month", "more", "morning", "most", "mother", "mouth", "move", "movement", "movie", "Mr", "Mrs", "much", "music", "must", "my", "myself", "name", "nation", "national", "natural", "nature", "near", "nearly", "necessary", "need", "network", "never", "new", "news", "newspaper", "next", "nice", "night", "no", "none", "nor", "north", "not", "note", "nothing", "notice", "now", "n't", "number", "occur", "of", "off", "offer", "office", "officer", "official", "often", "oh", "oil", "ok", "old", "on", "once", "one", "only", "onto", "open", "operation", "opportunity", "option", "or", "order", "organization", "other", "others", "our", "out", "outside", "over", "own", "owner", "page", "pain", "painting", "paper", "parent", "part", "participant", "particular", "particularly", "partner", "party", "pass", "past", "patient", "pattern", "pay", "peace", "people", "per", "perform", "performance", "perhaps", "period", "person", "personal", "phone", "physical", "pick", "picture", "piece", "place", "plan", "plant", "play", "player", "PM", "point", "police", "policy", "political", "politics", "poor", "popular", "population", "position", "positive", "possible", "power", "practice", "prepare", "present", "president", "pressure", "pretty", "prevent", "price", "private", "probably", "problem", "process", "produce", "product", "production", "professional", "professor", "program", "project", "property", "protect", "prove", "provide", "public", "pull", "purpose", "push", "put", "quality", "question", "quickly", "quite", "race", "radio", "raise", "range", "rate", "rather", "reach", "read", "ready", "real", "reality", "realize", "really", "reason", "receive", "recent", "recently", "recognize", "record", "red", "reduce", "reflect", "region", "relate", "relationship", "religious", "remain", "remember", "remove", "report", "represent", "Republican", "require", "research", "resource", "respond", "response", "responsibility", "rest", "result", "return", "reveal", "rich", "right", "rise", "risk", "road", "rock", "role", "room", "rule", "run", "safe", "same", "save", "say", "scene", "school", "science", "scientist", "score", "sea", "season", "seat", "second", "section", "security", "see", "seek", "seem", "sell", "send", "senior", "sense", "series", "serious", "serve", "service", "set", "seven", "several", "sex", "sexual", "shake", "share", "she", "shoot", "short", "shot", "should", "shoulder", "show", "side", "sign", "significant", "similar", "simple", "simply", "since", "sing", "single", "sister", "sit", "site", "situation", "six", "size", "skill", "skin", "small", "smile", "so", "social", "society", "soldier", "some", "somebody", "someone", "something", "sometimes", "son", "song", "soon", "sort", "sound", "source", "south", "southern", "space", "speak", "special", "specific", "speech", "spend", "sport", "spring", "staff", "stage", "stand", "standard", "star", "start", "state", "statement", "station", "stay", "step", "still", "stock", "stop", "store", "story", "strategy", "street", "strong", "structure", "student", "study", "stuff", "style", "subject", "success", "successful", "such", "suddenly", "suffer", "suggest", "summer", "support", "sure", "surface", "system", "table", "take", "talk", "task", "tax", "teach", "teacher", "team", "technology", "television", "tell", "ten", "tend", "term", "test", "than", "thank", "that", "the", "their", "them", "themselves", "then", "theory", "there", "these", "they", "thing", "think", "third", "this", "those", "though", "thought", "thousand", "threat", "three", "through", "throughout", "throw", "thus", "time", "to", "today", "together", "tonight", "too", "top", "total", "tough", "toward", "town", "trade", "traditional", "training", "travel", "treat", "treatment", "tree", "trial", "trip", "trouble", "true", "truth", "try", "turn", "TV", "two", "type", "under", "understand", "unit", "until", "up", "upon", "us", "use", "usually", "value", "various", "very", "victim", "view", "violence", "visit", "voice", "vote", "wait", "walk", "wall", "want", "war", "watch", "water", "way", "we", "weapon", "wear", "week", "weight", "well", "west", "western", "what", "whatever", "when", "where", "whether", "which", "while", "white", "who", "whole", "whom", "whose", "why", "wide", "wife", "will", "win", "wind", "window", "wish", "with", "within", "without", "woman", "wonder", "word", "work", "worker", "world", "worry", "would", "write", "writer", "wrong", "yard", "yeah", "year", "yes", "yet", "you", "young", "your", "yourself"};

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

        output.printf(formatString, input);
        output.printf(formatDouble, currentTime);
        output.printf(formatDouble, totalTime);
        output.printf(formatString, words.size());
        output.printf(formatString, trie.count());
        output.printf(formatString, trie.height());
        output.printf(formatString, trie.avgDepth());
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

        output.printf(formatString, input);
        output.printf(formatDouble, currentTime);
        output.print("\n");

        if(verbose) {
            System.out.println("CurrentTime[s] " + currentTime);
            System.out.println("TotalTime[s] " + totalTime);
            System.out.println();
        }
    }
    
    public void lookup(boolean verbose){

        System.out.println("Starting lookup benchmark " + trie.getClass().getSimpleName());

        start = System.nanoTime();

        for(String word : wordsToLookup){
            trie.lookup(word);
        }

        currentTime = (System.nanoTime() - start) / SECOND;
        totalTime += currentTime;

        output.printf(formatString, input);
        output.printf(formatDouble, currentTime);
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

        output.printf(formatDouble, totalTime);
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
            } else if(trie instanceof BalancedHybridTrie){
                initialize(new BalancedHybridTrie());
            } else if(trie instanceof HybridTrie){
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

        output.printf(formatString, "Input");
        output.printf(formatString, "CurrentTime[s]");
        output.print("\n");

        for(String file : getFileNames()){
            /* loading content in memory */
            if(trie instanceof PatriciaTrie){
                initialize(new PatriciaTrie());
            } else if(trie instanceof BalancedHybridTrie){
                initialize(new BalancedHybridTrie());
            } else if(trie instanceof HybridTrie){
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
     * Build a trie for each data input
     * lookup from each trie the words from "wordsToLookup"
     * @param verbose displays real time information on console
     */
    public void lookupFromEachTrie(boolean verbose){

        System.out.println("Looking up words from each trie for " + trie.getClass().getSimpleName());

        output.printf(formatString, "Input");
        output.printf(formatString, "CurrentTime[s]");
        output.print("\n");

        for(String file : getFileNames()){
            /* loading content in memory */
            if(trie instanceof PatriciaTrie){
                initialize(new PatriciaTrie());
            } else if(trie instanceof BalancedHybridTrie){
                initialize(new BalancedHybridTrie());
            } else if(trie instanceof HybridTrie){
                initialize(new HybridTrie());
            }
            else{
                System.out.print("This should not happen :)");
            }

            setInput(file);

            for(String word : words){
                trie.insert(word);
            }
            lookup(verbose);
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

    /**
     * Compares the merge time and succesive add time for all input data
     * @param verbose
     */

    public void mergeVsAdd(boolean verbose){
        System.out.println("Starting merge vs add");

        List<String> files = getFileNames();

        output.printf(formatString, "Input First");
        output.printf(formatString, "Input Second");
        output.printf(formatString, "PATMergeTime[s]");
        output.printf(formatString, "PATSuccesiveAddTime[s]");
        output.printf(formatString, "HYBSuccesiveAddTime[s]");
        output.print("\n");

        for(int i = 0; i < files.size(); i++){

            PatriciaTrie firstPat = new PatriciaTrie();
            HybridTrie firstHyb = new HybridTrie();
            /* load file */
            setInput(files.get(i));

            for(String word : words){
                firstPat.insert(word);
                firstHyb.insert(word);
            }

            for(int j = 0; j < files.size(); j++){
                if(i == j) continue;

                PatriciaTrie secondPat = new PatriciaTrie();
                HybridTrie secondHyb = new HybridTrie();
                /*load file */
                setInput(files.get(j));

                for(String word : words){
                    secondPat.insert(word);
                    secondHyb.insert(word);
                }

                output.printf(formatString, files.get(i));
                output.printf(formatString, files.get(j));

                mergePatricia(firstPat, secondPat, verbose);
                succesiveAddPatricia(firstPat, secondPat, verbose);
                succesiveAddHybrid(firstHyb, secondHyb, verbose);

                output.print("\n");
            }
        }

        output.close();
    }

    private void succesiveAddPatricia(PatriciaTrie first, PatriciaTrie second, boolean verbose){
        PatriciaTrie firstCloned = first.clone();

        System.out.println("Starting succesive Add Patricia");

        start = System.nanoTime();

        for(String word : second.getWordsUnsorted()){
           firstCloned.insert(word);
        }

        currentTime = (System.nanoTime() - start) / SECOND;
        output.printf(formatDouble, currentTime);

        if(verbose){
            System.out.println("Words in first " + first.count());
            System.out.println("Words in second " + second.count());
            System.out.println("Words new count " + firstCloned.count());
            System.out.println("Rezult " + currentTime + "\n");

        }
    }

    private void succesiveAddHybrid(HybridTrie first, HybridTrie second, boolean verbose){
        HybridTrie firstCloned = (HybridTrie) first.clone();

        System.out.println("Starting succesive Add Hybrid");

        start = System.nanoTime();

        for(String word : second.listWords()){
            firstCloned.insert(word);
        }

        currentTime = (System.nanoTime() - start) / SECOND;
        output.printf(formatDouble, currentTime);

        if(verbose){
            System.out.println("Words in first " + first.count());
            System.out.println("Words in second " + second.count());
            System.out.println("Words new count " + firstCloned.count());
            System.out.println("Rezult " + currentTime + "\n");

        }
    }

    private void mergePatricia(PatriciaTrie first, PatriciaTrie second, boolean verbose){
        PatriciaTrie firstCloned = first.clone();
        PatriciaTrie secondCloned = second.clone();

        System.out.println("Starting merge");

        start = System.nanoTime();

        firstCloned.merge(secondCloned);

        currentTime = (System.nanoTime() - start) / SECOND;
        output.printf(formatDouble, currentTime);

        if(verbose){
            System.out.println("Starting merge");
            System.out.println("Words in first " + first.count());
            System.out.println("Words in second " + second.count());
            System.out.println("Words new count " + firstCloned.count());
            System.out.println("Rezult " + currentTime + "\n");
        }
    }

    private void printHeader(){
        output.printf(formatString, "Input");
        output.printf(formatString, "CurrentTime[s]");
        output.printf(formatString, "TotalTime[s]");
        output.printf(formatString, "WordsInFile");
        output.printf(formatString, "WordsInTrie");
        output.printf(formatString, "Height");
        output.printf(formatString, "Average height");
        output.print("\n");
    }

    public double getTotalTime() { return totalTime; }
}
