import benchmark.Benchmark;
import datastructures.HybridTrie;
import datastructures.PatriciaTrie;

/**
 * Created by kuoa on 11/26/16.
 */
public class BechmarkTestMain {

    public static void main(String[] args){

        Benchmark benchmark = new Benchmark();

        PatriciaTrie patriciaTrie = new PatriciaTrie();
        benchmark.initialize(patriciaTrie, "/data/hamlet.txt");
        benchmark.start();
        benchmark.lookup();

        HybridTrie hybridTrie = new HybridTrie();
        benchmark.initialize(hybridTrie, "/data/hamlet.txt");
        benchmark.start();
        benchmark.lookup();

    }
}
