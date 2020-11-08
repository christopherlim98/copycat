package jsonparsing;

import com.fasterxml.jackson.databind.JsonNode;

import jsonparsing.entity.AbstractSyntaxTree;
import jsonparsing.parser.AstFactory;
import jsonparsing.parser.JsonToTree;
import jsonparsing.util.Algorithm;

import java.io.IOException;

import java.util.*;

import static jsonparsing.parser.Json.readFileAsString;
import static jsonparsing.parser.Json.parse;

public class JsonTestMain {
    public static void main(String[] args) throws Exception {
        String fileName = "src/main/resources/json/student5378.json";
        String json = readFileAsString(fileName);
        String fileName2 = "src/main/resources/json/student7386.json";
        String json2 = readFileAsString(fileName2);
        String fileName3 = "src/main/resources/json/student6018.json";
        String json3 = readFileAsString(fileName3);
        try {
            JsonNode node = parse(json);
            AbstractSyntaxTree ast= AstFactory.makeAst(node);
            LinkedList<String> list = ast.toList();
            System.out.println(list);

            System.out.println("=============================================");

            JsonNode node2 = parse(json2);
            AbstractSyntaxTree ast2= AstFactory.makeAst(node2);
            LinkedList<String> list2 = ast2.toList();
            System.out.println(list2);

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
