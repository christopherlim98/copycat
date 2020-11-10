package jsonparsing.copycat;

import jsonparsing.entity.AbstractSyntaxTree;
import jsonparsing.util.*;

import java.util.*;

public class WorkerJob {
    public double compareNaive(AbstractSyntaxTree ast1, AbstractSyntaxTree ast2){
        double score = 0.0;
        Algorithm algo = new Algorithm();
        LinkedList<String> list1 = new LinkedList<>();
        algo.traverse(list1, ast1.getRoot());
        LinkedList<String> list2 = new LinkedList<>();
        algo.traverse(list2, ast2.getRoot());
        String[] parts1 = list1.getFirst().split("");
        String[] parts2 = list1.getFirst().split("");
        Set<String> set1 = new HashSet<>(Arrays.asList(parts1));
        Set<String> set2 = new HashSet<>(Arrays.asList(parts2));

        JaroWinkler2 jaroWinkler2 = new JaroWinkler2();
        System.out.println(jaroWinkler2.similarity(list1.getFirst(), list2.getFirst()));
//        int ast1Root = algo.bitManipulationHash(list1.getFirst());
//        int ast2Root = algo.bitManipulationHash(list2.getFirst());

//        for (String i : set1){
//                System.out.println(i);
//
//        }
//        for (String i : set2){
//            System.out.println(i);
//        }
        //System.out.println(jaroWinklerJob.compute(list1.getFirst(), list2.getFirst()));
//        System.out.println(jaro.compute(list1.getFirst(), list2.getFirst()));
        System.out.println(Levenshtein.distance(list1.getFirst(), list2.getFirst()));
        int lfd = Levenshtein.distance(list1.getFirst(), list2.getFirst());
        double ratio = ((double) lfd) / (Math.max(list1.getFirst().length(), list2.getFirst().length()));
        System.out.println(1-ratio);
//        System.out.println(ast1Root);
//        System.out.println(list1);
//        System.out.println(ast2Root);
//        System.out.println(list2);
//        System.out.println(set1.size());
//        set1.removeAll(set2);
//        System.out.println(set1);
//        System.out.println(set1.size());

        return ratio;

    }

}
