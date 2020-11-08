package jsonparsing;

import com.fasterxml.jackson.databind.JsonNode;

import jsonparsing.entity.AbstractSyntaxTree;
import jsonparsing.parser.AstFactory;
import jsonparsing.parser.JsonToTree;
import jsonparsing.util.Algorithm;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.util.*;

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
        File[] files = new File("src/main/resources/json").listFiles();
        
        for (File file : files) {
            if (!file.isDirectory()) {
                String json = readFileAsString("src/main/resources/json/"+ file.getName());
                JsonNode node = parse(json);
                AbstractSyntaxTree ast= AstFactory.makeAst(node);
                LinkedList<String> list = ast.toList();
                List<Integer> result = new ArrayList<>();
                for (String string :list){
                    result.add(Algorithm.hash(string));
                }
                System.out.println(result.subList(0, 10));
                System.out.println("=============== New AST ================");
            }

    

        }

   



    }
}
