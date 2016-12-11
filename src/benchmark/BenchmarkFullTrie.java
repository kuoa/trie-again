package benchmark;

import benchmark.Benchmark;
import datastructures.BalancedHybridTrie;
import datastructures.HybridTrie;
import datastructures.PatriciaTrie;

/**
 * Created by kuoa on 11/26/16.
 */
public class BenchmarkFullTrie {

    public static void main(String[] args){

        Benchmark benchmark = new Benchmark();

        PatriciaTrie patriciaTrie = new PatriciaTrie();
        benchmark.initialize(patriciaTrie);
        benchmark.setOutput("fullPatricia.out");
        benchmark.buildFullTrie(false);

        HybridTrie hybridTrie = new HybridTrie();
        benchmark.initialize(hybridTrie);
        benchmark.setOutput("fullHybrid.out");
        benchmark.buildFullTrie(false);
        
        BalancedHybridTrie bhybridTrie = new BalancedHybridTrie();
        benchmark.initialize(bhybridTrie);
        benchmark.setOutput("fullBalancedHybrid.out");
        benchmark.buildFullTrie(false);
    }
}
