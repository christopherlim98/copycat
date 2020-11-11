package jsonparsing;

import jsonparsing.copycat.Worker;
import jsonparsing.entity.AbstractSyntaxTree;
import jsonparsing.exception.JsonToTreeTimeoutException;
import jsonparsing.parser.AstFactory;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class MicroTest {

    public static void main(String[] args){
        // Initialise Ast Tree Builder and Comparison Worker.
        AstFactory astFactory = new AstFactory();
        Worker worker = new Worker();

        // Specify file name to be compared
        String fileName = "src/main/resources/json/student9823.json";
        String fileName2 = "src/main/resources/json/student2553.json";

        // Make Ast using astFactory
        // try{
            AbstractSyntaxTree ast1= astFactory.makeAstFromJsonFile(fileName);
            AbstractSyntaxTree ast2= astFactory.makeAstFromJsonFile(fileName2);
            double score = worker.compareSnapshots(ast1, ast2);

            // Get an array of all the file names in resources
            System.out.println(ast1.toHashMap());

            System.out.println("==============================");
            System.out.println(ast2.toHashMap());
            System.out.println("Similarity score: " + score);
        // } catch (JsonToTreeTimeoutException e){
        //     System.out.println(e.getMessage());
        // }

    }
}