package benchmark;

import benchmark.Benchmark;
import datastructures.BalancedHybridTrie;
import datastructures.HybridTrie;
import datastructures.PatriciaTrie;

/**
 * Created by kuoa on 11/26/16.
 */
public class BenchmarkEachTrie {

    public static void main(String[] args){

        Benchmark benchmark = new Benchmark();

        PatriciaTrie patriciaTrie = new PatriciaTrie();
        benchmark.initialize(patriciaTrie);
        benchmark.setOutput("eachPatricia.out");
        benchmark.buildEachTrie(false);

        HybridTrie hybridTrie = new HybridTrie();
        benchmark.initialize(hybridTrie);
        benchmark.setOutput("eachHybrid.out");
        benchmark.buildEachTrie(false);
        
        BalancedHybridTrie bhybridTrie = new BalancedHybridTrie();
        benchmark.initialize(bhybridTrie);
        benchmark.setOutput("eachBalancedHybrid.out");
        benchmark.buildEachTrie(false);
    }
}
