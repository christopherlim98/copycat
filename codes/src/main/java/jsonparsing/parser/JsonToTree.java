package jsonparsing.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import jsonparsing.entity.AbstractSyntaxTree;
import jsonparsing.entity.Node;

import java.util.List;
import java.util.Stack;


public class JsonToTree {
    private static AbstractSyntaxTree ast = new AbstractSyntaxTree();

    public static List<Object> traverse(JsonNode root, List<Object> result, AbstractSyntaxTree ast, Node parent){
        if(root.isObject()){
            // base case :
            processNode(root, result, ast, parent);
        } else if (root.isArray()){
            // Process children
            processChildren(root, result, ast, parent);
        }
        return result;
    }

    public static List<Object> processChildren(JsonNode root, List<Object> result, AbstractSyntaxTree ast, Node parent){
        // Traverse children of JSON
        ArrayNode arrayNode = (ArrayNode) root;
        for(int i = 0; i < arrayNode.size(); i++) {
            JsonNode arrayElement = arrayNode.get(i);
            traverse(arrayElement, result, ast, parent);
        }
        return result;
    }

    public static List<Object> processNode(JsonNode root, List<Object> result, AbstractSyntaxTree ast, Node parent){
        int childrenNumber = root.get("children").size();
        String type = root.get("type").toString();
        String content = root.get("content").toString();
        JsonNode children= root.get("children");

        // Insert into tree
        Node node = Node.fromJsonNode(parent, root);
        System.out.println(" Children number: " + childrenNumber + " , type: " + type
                            + "content:" + content);
        result.add(content);


        // Continue traversing Json
        if (children != null){
            parent = node;
            traverse(children, result, ast, parent);
        }
        return result;
    }
}
