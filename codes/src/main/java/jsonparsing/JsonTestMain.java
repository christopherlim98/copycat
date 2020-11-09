package jsonparsing;

import jsonparsing.copycat.Worker;
import jsonparsing.entity.AbstractSyntaxTree;
import jsonparsing.parser.AstFactory;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.nio.charset.*;

import java.net.URL;
import java.util.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;



public class JsonTestMain {
    public static void showFiles(File[] files) {
        System.out.println(files);
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.println("Directory: " + file.getName());
                showFiles(file.listFiles()); // Calls same method again.
            } else {
                System.out.println( file.getName());
            }
        }
    }
    public static void main(String[] args) throws Exception {
        // Initialise Ast Tree Builder and Comparison Worker.
        AstFactory astFactory = new AstFactory();
        Worker worker = new Worker();

        // Build AST from json files
        String fileName = "src/main/resources/json/student8599.json";
        String fileName2 = "src/main/resources/json/student4473.json";
        AbstractSyntaxTree ast1= astFactory.makeAstFromJsonFile(fileName);
        AbstractSyntaxTree ast2= astFactory.makeAstFromJsonFile(fileName2);

        // Print similarity score
        System.out.println( "Similarity score: " + worker.compareBreadthWise(ast1, ast2));

        // File[] files = new File("src/main/resources/json").listFiles();

        // for (File file : files) {
        //     if (!file.isDirectory()) {
        //         String json = readFileAsString("src/main/resources/json/"+ file.getName());
        //         JsonNode node = parse(json);
        //         AbstractSyntaxTree ast= AstFactory.makeAst(node);
        //         LinkedList<String> list = ast.toList();
        //         List<Integer> result = new ArrayList<>();
        //         for (String string :list){
        //             result.add(Algorithm.hash(string));
        //         }
        //         System.out.println(result.subList(0, 10));
        //         System.out.println("=============== New AST ================");
        //     }



        // }



    }
}
