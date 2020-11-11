package copycat.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import copycat.util.Algorithm;

public class AbstractSyntaxTree {
    private Node root = null;
    private int size;

    public LinkedList<String> toList (){
        Algorithm algorithm = new Algorithm();
        LinkedList<String> list = new LinkedList<String>();
        algorithm.traverse(list, root);
        return list;
    }

    public HashMap<Integer, String> toHashMap (){
        Algorithm algorithm = new Algorithm();
        return algorithm.traverseWithLevels(new HashMap <Integer, String>(), root, 0);
    }

    public void addRoot(Node root) throws IllegalArgumentException{
        if (root != null){
            this.root = root;
        } else {
            throw new IllegalArgumentException("Tree already has a root");
        }
    }

    public Node getRoot(){
        return this.root;
    }

    public void addChild(Node parent, Node child ){
        parent.addChild(child);
        size++;
    }

    public boolean isEmpty(){
       return size == 0;
    }
    private void preorderSubtree(Node p, List<Node> snapshot) {
        snapshot.add(p);                       // for preorder, we add position p before exploring subtrees
        for (Node c : p.children())
            preorderSubtree(c, snapshot);
    }

    public Iterable<Node> preorder() {
        List<Node> snapshot = new ArrayList<>();
        if (!isEmpty())
            preorderSubtree(root, snapshot);   // fill the snapshot recursively
        return snapshot;
    }

    public Iterable<Node> preorder(Node root) {
        // Find the preorder for a specified root/sub root node.
        List<Node> snapshot = new ArrayList<>();
        if (!root.children().isEmpty()){
            preorderSubtree(root, snapshot);   // fill the snapshot recursively
        }
        return snapshot;
    }

    private void postorderSubtree(Node p, List<Node> snapshot) {
        for (Node c : p.children())
          postorderSubtree(c, snapshot);
        snapshot.add(p);                       // for postorder, we add position p after exploring subtrees
      }

    public Iterable<Node> postorder() {
        List<Node> snapshot = new ArrayList<>();
        if (!isEmpty())
          postorderSubtree(root, snapshot);   // fill the snapshot recursively
        return snapshot;
    }

    public void printTree(){
        for (Node n : preorder()){
            System.out.println("Children number: " + n.getChildCount() + ", content: " + n.getContent());
        }
    }

    public void printTreePostOrder(){
        for (Node n : postorder()){
            System.out.println("Children number: " + n.getChildCount() + ", content: " + n.getContent());
        }
    }

    public int getChildrenCount(){
        int count = 0;
        for (Node n : preorder()){
            count++;
        }
        return count;
    }

    public Node returnRoot(){
        return root;
    }
}
