package benchmark;

import datastructures.PatriciaTrie;

/**
 * Created by kuoa on 11/26/16.
 */
public class BenchmarkMergeVsAdd {

    public static void main(String[] args){

        Benchmark benchmark = new Benchmark();

        PatriciaTrie patriciaTrie = new PatriciaTrie();
        benchmark.initialize(null);
        benchmark.setOutput("mergeVsAdd.out");
        benchmark.mergeVsAdd(false);
    }
}
