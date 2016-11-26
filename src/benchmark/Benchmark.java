package benchmark;

import interfaces.ITrie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kuoa on 11/26/16.
 */
public class Benchmark {

    private ITrie trie;
    private int wordsRead;
    private String file;
    private double start;
    private double time;
    private List<String> words;


    public void initialize(ITrie _trie, String _file){
        trie = _trie;
        wordsRead = 0;
        file = new File("").getAbsolutePath().concat(_file);

        words = new ArrayList<>();
        start = 0;
        time = 0;

        loadFile();
    }

    private void loadFile(){


        System.out.println("Loading " + file + " into memory");

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
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

        System.out.println("Finished loading " + file + " into memory");
    }

    public void start(){

        System.out.println("Starting insertion benchmark");

        start = System.nanoTime();

        for(String word : words){
            trie.insert(word);
        }

        time = (System.nanoTime() - start)/ 1000000000;

        System.out.println("Ending insertion benchmark");
        System.out.println("Rezult " + time);
        System.out.println("Words " + trie.count());
        System.out.println("Height " + trie.height());
        System.out.println("Average height " + trie.avgDepth());
        System.out.println();
    }

    public void lookup(){

        System.out.println("Starting lookup benchmark");

        start = System.nanoTime();

        for(String word : words){
            trie.lookup(word);
        }

        time = (System.nanoTime() - start)/ 1000000000;

        System.out.println("Ending lookup benchmark " + time + "\n");

    }

}
