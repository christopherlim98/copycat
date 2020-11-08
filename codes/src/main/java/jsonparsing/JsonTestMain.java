package jsonparsing;

import com.fasterxml.jackson.databind.JsonNode;

import jsonparsing.copycat.Worker;
import jsonparsing.entity.AbstractSyntaxTree;
import jsonparsing.parser.AstFactory;
import jsonparsing.parser.JsonToTree;
import jsonparsing.util.Algorithm;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.util.*;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;

import static jsonparsing.parser.Json.readFileAsString;
import static jsonparsing.parser.Json.parse;

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

        String fileName = "src/main/resources/json/student8599.json";
        String fileName2 = "src/main/resources/json/student2965.json";
        String json = readFileAsString(fileName);
        String json2 = readFileAsString(fileName2);
        try {
            Algorithm algo = new Algorithm();
            AstFactory astFactory = new AstFactory();

            // System.out.println("=============================================");
            // HashMap<Integer, String> hm = algo.traverseWithLevels(new HashMap <Integer, String>(), ast.getRoot(), 0);
            // System.out.println(hm);

            JsonNode node = parse(json);
            AbstractSyntaxTree ast1= astFactory.makeAst(node);
            LinkedList<String> list = ast1.toList();
            HashMap<Integer, String> hm = algo.traverseWithLevels(new HashMap <Integer, String>(), ast1.getRoot(), 0);

            JsonNode node2 = parse(json2);
            AbstractSyntaxTree ast2= astFactory.makeAst(node2);
            LinkedList<String> list2 = ast2.toList();
            HashMap<Integer, String> hm2 = algo.traverseWithLevels(new HashMap <Integer, String>(), ast2.getRoot(), 0);
            Worker worker = new Worker();
            System.out.println( "Similarity score: " + worker.compare(ast1, ast2));
        } catch (IOException e){}
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
