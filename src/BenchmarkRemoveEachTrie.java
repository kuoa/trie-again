import benchmark.Benchmark;
import datastructures.HybridTrie;
import datastructures.PatriciaTrie;

/**
 * Created by kuoa on 11/26/16.
 */
public class BenchmarkRemoveEachTrie {

    public static void main(String[] args){

        Benchmark benchmark = new Benchmark();

        PatriciaTrie patriciaTrie = new PatriciaTrie();
        benchmark.initialize(patriciaTrie);
        benchmark.setOutput("removeEachPatricia.out");
        benchmark.removeFromEachTrie(false);

        HybridTrie hybridTrie = new HybridTrie();
        benchmark.initialize(hybridTrie);
        benchmark.setOutput("removeEachHybrid.out");
        benchmark.removeFromEachTrie(false);
    }
}
