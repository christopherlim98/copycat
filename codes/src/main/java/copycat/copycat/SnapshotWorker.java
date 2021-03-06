package copycat.copycat;

import java.util.*;

import copycat.entity.AbstractSyntaxTree;
import copycat.util.Algorithm;
import copycat.util.Levenshtein;
import copycat.constants.Constants;

public class SnapshotWorker implements WorkerFactory{

    // Compare using snapshot
    // Transforms the AST into hashmaps (snapshots) that allow us
    // to compare level by level for differences
    @Override
    public double compare(AbstractSyntaxTree ast1, AbstractSyntaxTree ast2){
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
        return 100 * score;
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
        int count = 0;
        for (Integer key : ks1){
            score += compareInsensitiveToOrder(key, hm1.get(key), hm2.get(key));
            count++;
        }

        // The depth of the tree correlates to the logic behind the code.
        // As we want the final score to be sensitive to the differences number of logic levels,
        // we take the square value of the fraction when normalising the score.
        return score/count * ((double)Math.pow(ks1.size(),2) /Math.pow(ks2.size(),2));
    }

    public double compareInsensitiveToOrder(Integer level, String s1, String s2){
        // Compares two strings for differences in O(n) time by using a character counter array.
        // s1 adds to the array, s2 deducts from the array
        // We take the absolute value in each array cell to count the difference.
        double score = 0.0;
        int [] charDiff = new int[Constants.HASHDICT.size()];
        int [] charTotal = new int[Constants.HASHDICT.size()];
        int differences = 0;
        for (int i = 0; i < s1.length(); i++){
            int index = s1.charAt(i) - 'a';
            charDiff[index]++;
            charTotal[index]++;
        }

        for (int i = 0; i < s2.length(); i++){
            int index = s2.charAt(i) - 'a';
            charDiff[index]--;
            charTotal[index]++;
        }

        int totalWeight = 0;

        // Applies weights to each node
        for (int i = 0; i < charTotal.length ; i ++){
            char ch = (char)('a' + i);
            differences += Math.abs(charDiff[i]) * Constants.HASHWEIGHTS.get(ch);
            totalWeight += charTotal[i] * Constants.HASHWEIGHTS.get(ch);
        }

        // Determine difference in number of nodes at the same level
        int smaller = s1.length();
        int longer = s2.length();
        if (s1.length() > s2.length()){
            smaller = s2.length();
            longer = s1.length();
        }
        int similarity = totalWeight - differences ;

        // At the same level: sensitive to differences in number of nodes
        // E.g. level 0: 5 nodes vs 20 nodes.
        // The fraction serves to dampen the score if there is a huge difference in number of nodes
        score = (double)(similarity * ((double)smaller/longer))/totalWeight;
        return score;
    }

}