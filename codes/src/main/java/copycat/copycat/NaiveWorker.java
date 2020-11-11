package copycat.copycat;

import java.util.LinkedList;

import copycat.entity.AbstractSyntaxTree;
import copycat.util.Algorithm;
import copycat.util.Levenshtein;

public class NaiveWorker implements  WorkerFactory{
    @Override
    public double compare(AbstractSyntaxTree ast1, AbstractSyntaxTree ast2){
        Algorithm algo = new Algorithm();

        // We use a linkedlist as we can add elements fast
        // Also linkedlist is good as we only need to access the first node
        // as the first node contains the most information.
        LinkedList<String> list1 = new LinkedList<>();
        algo.traverse(list1, ast1.getRoot());
        LinkedList<String> list2 = new LinkedList<>();
        algo.traverse(list2, ast2.getRoot());


        // Uses levenshtein distance to measure the similarity of the root node
        int lfd = Levenshtein.distance(list1.getFirst(), list2.getFirst());
        double ratio = ((double) lfd) / (Math.max(list1.getFirst().length(), list2.getFirst().length()));

        return (1-ratio)*100;

    }
}
