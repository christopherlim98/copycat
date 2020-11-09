package jsonparsing.copycat;

import java.util.*;

import jsonparsing.entity.AbstractSyntaxTree;
import jsonparsing.util.Algorithm;
import jsonparsing.util.JaroWinkler;
import jsonparsing.util.JaroWinklerJob;

public class Worker {
    public double compare(AbstractSyntaxTree ast1, AbstractSyntaxTree ast2){
        // Returns a score from 0.0 - 1.0  on how similar the trees are
        double score = 0.0;
        // for each sub tree: Hasher.hash()

        return score;
    }

    /**
     * Compares the two hashmaps level by level, i.e. breadth-wise.
     * As we use string similarity to measure both levels,
     * our comparison is sensitive to order.
     * Note: each level captures the code logic hashed as a single string.
     */

    public double compareKeySets(Set<Integer> ks1, Set<Integer> ks2,
                                 HashMap<Integer, String> hm1, HashMap<Integer, String> hm2  ){
        // Sensitive to order
        double score = 0.0;
        int count = 1;
        for (Integer key : ks1){
            JaroWinklerJob jw = new JaroWinklerJob();
            score += jw.compute(hm1.get(key),hm2.get(key));
            // score += jw.similarity(hm1.get(key),hm2.get(key));
            count++;
        }

        // The depth of the tree correlates to the logic behind the code.
        // As we want the final score to be sensitive to the differences number of logic levels,
        // we take the square value of the fraction when normalising the score.
        return score/count * ((double)Math.pow(ks1.size(),2) /Math.pow(ks2.size(),2));
    }

    public double compareBreadthWise(AbstractSyntaxTree ast1, AbstractSyntaxTree ast2){
        // Returns a score from 0.0 - 1.0  on how similar the trees are
        double score = 0.0;
        Algorithm algo = new Algorithm();
        HashMap<Integer, String> hm1 = algo.traverseWithLevels(new HashMap <Integer, String>(), ast1.getRoot(), 0);
        HashMap<Integer, String> hm2 = algo.traverseWithLevels(new HashMap <Integer, String>(), ast2.getRoot(), 0);

        Set<Integer> keySet1 = hm1.keySet();
        Set<Integer> keySet2 = hm2.keySet();
        if (keySet1.size() > keySet2.size()){
            score = compareKeySets(keySet2, keySet1,
                        hm2, hm1  );
        }else{
            score = compareKeySets(keySet1, keySet2,
            hm1, hm2);
        }
        return score;

    }

}