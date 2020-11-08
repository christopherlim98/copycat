package jsonparsing.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

public class Node {
    private Node parent;
    private String content;
    private String type;
    private Integer childrenNumber;
    private List <Node> children = new ArrayList<>();


    public Node (Node parent, String content, String type, Integer childrenNumber, List<Node> children){
        this.parent = parent; // if parent node is null, the node is the root node.
        this.content = content;
        this.type = type;
        this.childrenNumber = childrenNumber;
        this.children = children;
    }

    public Node (Node parent, String content, String type, Integer childrenNumber){
        this.parent = parent; // if parent node is null, the node is the root node.
        this.content = content;
        this.type = type;
        this.childrenNumber = childrenNumber;
    }

    public static Node fromJsonNode (Node parent, JsonNode jsonNode){
        int jsonChildrenNumber = jsonNode.get("children").size();
        String jsonType = jsonNode.get("type").toString().replace("\"", "");
        String jsonContent = jsonNode.get("content").toString();
        return new Node(parent, jsonContent, jsonType, jsonChildrenNumber);
    }

    public String getType(){
        return type;
    }

    public String getContent(){
        return content;
    }

    public int getChildCount(){
        return childrenNumber;
    }


    public List<Node> children(){
        return children;
    }

    public void addChild(Node child){
        children.add(child);
    }

    public Node getChildAt(int childIndex){
        return children.get(childIndex);
    }

    public int getIndex(Node nodeToFind){
        // O(n) operation
        for (int i = 0; i < childrenNumber; i ++){
            Node child = children.get(i);
            if (child.equals(nodeToFind)){
                return i;
            }
        }
        // if node does not exist, return -1
        return -1;
    }

    public Node getParent(){
        return parent;
    }

    public boolean isLeaf(){
        if (childrenNumber == 0){
            return true;
        }else{
            return false;
        }
    }
}
