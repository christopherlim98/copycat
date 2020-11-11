package copycat.copycat;

import java.util.LinkedList;

import copycat.entity.AbstractSyntaxTree;
import copycat.util.Algorithm;
import copycat.util.Levenshtein;

public class Worker2 implements  WorkerFactory{
    @Override
    public double compare(AbstractSyntaxTree ast1, AbstractSyntaxTree ast2){
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
