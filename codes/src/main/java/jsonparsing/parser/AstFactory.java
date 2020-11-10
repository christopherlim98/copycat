package jsonparsing.parser;

import com.fasterxml.jackson.databind.JsonNode;

import jsonparsing.entity.AbstractSyntaxTree;
import static jsonparsing.parser.Json.readFileAsString;

import java.io.IOException;

import static jsonparsing.parser.Json.parse;

public class AstFactory {
    public AbstractSyntaxTree makeAstFromJsonFile(String fileName){
        try{
            String json = readFileAsString(fileName);
         
            JsonNode node = parse(json);
            return makeAst(node);
        }catch (IOException e){
            System.out.println("IO Error happened with file: " + fileName);
            e.printStackTrace();
            return null;
        }
    }

    public AbstractSyntaxTree makeAst(JsonNode node){
        AbstractSyntaxTree ast= new AbstractSyntaxTree();
 
        JsonToTree.parse(node, ast, null);
        return ast;
    }
}
