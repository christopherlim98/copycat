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

        JaroWinkler jaroWinkler2 = new JaroWinkler();
        System.out.println("Jaro-Winkler:"+jaroWinkler2.similarity(list1.getFirst(), list2.getFirst()));
//        Hasher hasher = new Hasher();
//        int ast1Root = hasher.polynomialHash(list1.getFirst());
////        int ast2Root = hasher.polynomialHash(list2.getFirst());
////        System.out.println(ast1Root);
////        System.out.println(ast2Root);

        System.out.println(Levenshtein.distance(list1.getFirst(), list2.getFirst()));
        int lfd = Levenshtein.distance(list1.getFirst(), list2.getFirst());
        double ratio = ((double) lfd) / (Math.max(list1.getFirst().length(), list2.getFirst().length()));
        System.out.println("Levenstein:" + (1-ratio));


        return 1-ratio;

    }

}
