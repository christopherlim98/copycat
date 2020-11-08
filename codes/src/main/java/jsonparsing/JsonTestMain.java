package jsonparsing;

import com.fasterxml.jackson.databind.JsonNode;

import jsonparsing.entity.AbstractSyntaxTree;
import jsonparsing.parser.JsonToTree;
import jsonparsing.util.Algorithm;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static jsonparsing.parser.Json.readFileAsString;
import static jsonparsing.parser.Json.parse;

public class JsonTestMain {
    public static void main(String[] args) throws Exception {
        String fileName = "src/main/resources/json/test.json";
        String json = readFileAsString(fileName);
        try {
            JsonNode node = parse(json);
            AbstractSyntaxTree ast= new AbstractSyntaxTree();
            ast = JsonToTree.parse(node, ast, null);
            ast.printTree();
            System.out.println("=======================");
            ast.printTreePostOrder();

            Algorithm algorithm = new Algorithm();
            LinkedList<String> list = new LinkedList<String>();
            algorithm.traverse(list, ast.getRoot());
            System.out.println(list);


        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
