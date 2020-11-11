package jsonparsing.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import jsonparsing.entity.AbstractSyntaxTree;
import jsonparsing.entity.Node;
import jsonparsing.constants.Constants;

import java.util.*;
import java.util.concurrent.Callable;


public class JsonToTree implements Callable <AbstractSyntaxTree> {
    private static Set<String> set = new HashSet<>(Arrays.asList(Constants.TYPES));
    private JsonNode root;
    private AbstractSyntaxTree ast;
    private Node parent;

    public JsonToTree(JsonNode root, AbstractSyntaxTree ast, Node parent){
        this.root = root;
        this.ast = ast;
        this.parent = parent;
    }

    @Override
    public AbstractSyntaxTree call() throws Exception {
        return parse(root, ast, parent);
    }

    public AbstractSyntaxTree parse(JsonNode root, AbstractSyntaxTree ast, Node parent){
        if(root.isObject()){
            // base case :
            processNode(root, ast, parent);
        } else if (root.isArray()){
            // Process children
            processChildren(root, ast, parent);
        }
        return ast;
    }

    public AbstractSyntaxTree processChildren(JsonNode root, AbstractSyntaxTree ast, Node parent){
        // Traverse children of JSON
        ArrayNode arrayNode = (ArrayNode) root;
        for(int i = 0; i < arrayNode.size(); i++) {
            JsonNode arrayElement = arrayNode.get(i);
            parse(arrayElement, ast, parent);
        }
        return ast;
    }

    public  AbstractSyntaxTree processNode(JsonNode root, AbstractSyntaxTree ast, Node parent){
        JsonNode children= root.get("children");
        // Insert into tree
        Node node = Node.fromJsonNode(parent, root);
        String type = node.getType();
        if (parent == null){
            ast.addRoot(node);
        } else {
            if (set.contains(type)){
                ast.addChild(parent, node);
            }else{
                parse(children, ast, parent);
            }

        }

        // Continue traversing Json
        if (children != null){
            parent = node;
            parse(children, ast, parent);
        }
        return ast;
    }
}
