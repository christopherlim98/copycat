package jsonparsing.copycat;

import java.util.*;

import jsonparsing.entity.AbstractSyntaxTree;
import jsonparsing.util.Algorithm;
import jsonparsing.util.JaroWinkler;
import jsonparsing.util.Levenshtein;
import jsonparsing.constants.Constants;

public class Worker {
    public double compareBreadthWise(AbstractSyntaxTree ast1, AbstractSyntaxTree ast2){
        // Returns a score from 0.0 - 1.0  on how similar the trees are

        // PUT JOHN TEST HERE!!!!!!
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
        //int totalChars = 0;
        for (int i = 0; i < charTotal.length ; i ++){
            char ch = (char)('a' + i);
            differences += Math.abs(charDiff[i]) * Constants.HASHWEIGHTS.get(ch);
            totalWeight += charTotal[i] * Constants.HASHWEIGHTS.get(ch);
            //totalChars += charTotal[i];
        }
        int smaller = s1.length();
        int longer = s2.length();
        if (s1.length() > s2.length()){
            smaller = s2.length();
            longer = s1.length();
        }
        int similarity = totalWeight - differences ;
        score = (double)(similarity) /totalWeight;
        return score;
    }

    public double compareKeySetsSensitiveToOrder(Set<Integer> ks1, Set<Integer> ks2,
                                 HashMap<Integer, String> hm1, HashMap<Integer, String> hm2  ){
        // Sensitive to order
        double score = 0.0;
        int count = 0;
        for (Integer key : ks1){
            JaroWinkler jw = new JaroWinkler();
            score += jw.similarity(hm1.get(key), hm2.get(key));
            count++;
        }
        // The depth of the tree correlates to the logic behind the code.
        // As we want the final score to be sensitive to the differences number of logic levels,
        // we take the square value of the fraction when normalising the score.
        return score/count * ((double)Math.pow(ks1.size(),2) /Math.pow(ks2.size(),2));
    }

    public double compareSnapshots(AbstractSyntaxTree ast1, AbstractSyntaxTree ast2){
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
    public double compareNaive(AbstractSyntaxTree ast1, AbstractSyntaxTree ast2){
        double score = 0.0;
        Algorithm algo = new Algorithm();
        LinkedList<String> list1 = new LinkedList<>();
        algo.traverse(list1, ast1.getRoot());
        LinkedList<String> list2 = new LinkedList<>();
        algo.traverse(list2, ast2.getRoot());

        // JaroWinkler jaroWinkler2 = new JaroWinkler();

        int lfd = Levenshtein.distance(list1.getFirst(), list2.getFirst());
        double ratio = ((double) lfd) / (Math.max(list1.getFirst().length(), list2.getFirst().length()));
        // System.out.println("Levenstein:"+(1-ratio));

        return (1-ratio)*100;

    }

}