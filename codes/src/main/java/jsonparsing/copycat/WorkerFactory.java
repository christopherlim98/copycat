package jsonparsing.copycat;
import jsonparsing.entity.AbstractSyntaxTree;

public interface WorkerFactory{
    public double compare(AbstractSyntaxTree ast1, AbstractSyntaxTree ast2);
    
}