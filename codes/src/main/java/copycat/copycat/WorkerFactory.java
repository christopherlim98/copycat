package copycat.copycat;
import copycat.entity.AbstractSyntaxTree;

public interface WorkerFactory{
    public double compare(AbstractSyntaxTree ast1, AbstractSyntaxTree ast2);

}