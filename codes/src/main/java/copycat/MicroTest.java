package copycat;

import copycat.copycat.NaiveWorker;
import copycat.copycat.ProgressiveWorker;
import copycat.copycat.SnapshotWorker;
import copycat.copycat.WorkerFactory;
import copycat.entity.AbstractSyntaxTree;
import copycat.exception.JsonToTreeTimeoutException;
import copycat.parser.AstFactory;
import java.io.IOException;


public class MicroTest {

    public static void main(String[] args){
        // Initialise Ast Tree Builder and Comparison Worker.
        AstFactory astFactory = new AstFactory();
        // WorkerFactory worker = new SnapshotWorker();
        // WorkerFactory worker = new ProgressiveWorker();
        WorkerFactory worker = new NaiveWorker();

        // Specify file name to be compared
        String fileName = "src/main/resources/json/student1317.json";
        String fileName2 = "src/main/resources/json/student1364.json";

        // Make Ast using astFactory
        try{
            AbstractSyntaxTree ast1= astFactory.makeAstFromJsonFile(fileName);
            AbstractSyntaxTree ast2= astFactory.makeAstFromJsonFile(fileName2);
            double score = worker.compare(ast1, ast2);


            System.out.println("==============================");
            System.out.println("Similarity score: " + score);
        } catch (JsonToTreeTimeoutException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}