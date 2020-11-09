package jsonparsing.copycat;

import java.util.*;

import info.debatty.java.stringsimilarity.JaroWinkler;
import jsonparsing.entity.AbstractSyntaxTree;
import jsonparsing.util.Algorithm;
// import jsonparsing.util.JaroWinkler;

public class Worker {
    public double compare(AbstractSyntaxTree ast1, AbstractSyntaxTree ast2){
        double score = 0.0;
        // returns a score from 0.0 - 1.0  on how similar the trees are
        // for each sub tree: Hasher.hash()

        Algorithm algo = new Algorithm();
        LinkedList <String> list = ast1.toList();
        HashMap<Integer, String> hm1 = algo.traverseWithLevels(new HashMap <Integer, String>(), ast1.getRoot(), 0);

        LinkedList <String> list2 = ast2.toList();
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

    public double compareKeySets(Set<Integer> ks1, Set<Integer> ks2,
                                 HashMap<Integer, String> hm1, HashMap<Integer, String> hm2  ){
        double score = 0.0;
        int count = 1;
        for (Integer key : ks1){
            // JaroWinkler jw = new JaroWinkler();
            JaroWinkler jw = new JaroWinkler();
            // score += jw.compute(hm1.get(key),hm2.get(key));
            score += jw.similarity(hm1.get(key),hm2.get(key));
            count++;
        }
        return score/count * ((double)Math.pow(ks1.size(),2) /Math.pow(ks2.size(),2));
    }

    public double compareBreadthWise(AbstractSyntaxTree tree1, AbstractSyntaxTree tree2){
        // insensitive to order

        // sensitive to order

        // returns a score from 0.0 - 1.0  on how similar the trees are
        double score = 0.0;

        return score;

    }

}
