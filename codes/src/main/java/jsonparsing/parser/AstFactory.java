package jsonparsing.parser;

import com.fasterxml.jackson.databind.JsonNode;

import jsonparsing.entity.AbstractSyntaxTree;

public class AstFactory {
    public static AbstractSyntaxTree makeAst(JsonNode node){
        AbstractSyntaxTree ast= new AbstractSyntaxTree();
        JsonToTree.parse(node, ast, null);
        return ast;
    }
}
