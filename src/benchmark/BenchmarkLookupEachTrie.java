package benchmark;

import benchmark.Benchmark;
import datastructures.BalancedHybridTrie;
import datastructures.HybridTrie;
import datastructures.PatriciaTrie;


public class BenchmarkLookupEachTrie {

    public static void main(String[] args){

        Benchmark benchmark = new Benchmark();

        PatriciaTrie patriciaTrie = new PatriciaTrie();
        benchmark.initialize(patriciaTrie);
        benchmark.setOutput("lookupEachPatricia.out");
        benchmark.lookupFromEachTrie(false);

        HybridTrie hybridTrie = new HybridTrie();
        benchmark.initialize(hybridTrie);
        benchmark.setOutput("lookupEachHybrid.out");
        benchmark.lookupFromEachTrie(false);
        
        BalancedHybridTrie bhybridTrie = new BalancedHybridTrie();
        benchmark.initialize(bhybridTrie);
        benchmark.setOutput("lookupEachBalanced.out");
        benchmark.lookupFromEachTrie(false);
    }
}
