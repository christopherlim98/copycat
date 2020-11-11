package copycat;

import java.util.*;

import copycat.entity.AbstractSyntaxTree;
import copycat.entity.Node;
import copycat.parser.AstFactory;


public class JohnTest {

    public static void main(String[] args) {
         // Initialise Ast Tree Builder and Comparison Worker.
        String fileName = "src/main/resources/json/student1317.json";
        String fileName2 = "src/main/resources/json/student1317.json";
        try {
            AstFactory astFactory = new AstFactory();
            AbstractSyntaxTree ast1= astFactory.makeAstFromJsonFile(fileName);
            AbstractSyntaxTree ast2= astFactory.makeAstFromJsonFile(fileName2);
            JohnTest johnTest = new JohnTest();
            System.out.println(johnTest.compareSetWise(ast1,ast2));

        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("Error");
        }

    }
    public double compareSetWise(AbstractSyntaxTree ast1, AbstractSyntaxTree ast2) {
        // Retrieving root nodes for both ast trees
        Node root = ast1.returnRoot();
        Node root2 = ast2.returnRoot();

        // Constructing 2 deques to store the nodes in each level
        Deque<Node> listOfNodes = new ArrayDeque<>();
        Deque<Node> listOfNodes2 = new ArrayDeque<>();

        // Constructing a hashMap to calculate the difference between the nodes in each level
        HashMap<String, Integer> hashmap = new HashMap<>();

        // Start off by adding the root node of both trees into the 2 deques
        listOfNodes.add(root);
        listOfNodes2.add(root2);

        // Computing the size of both trees through preorder traversal
        int children1 = ast1.getChildrenCount();
        int children2 = ast2.getChildrenCount();
        int size = children1 + children2;

        // Recursive method to get a similarity index of either 1 / 0
        // 1 indicating plaglarism
        // 2 indicating non-plaglarism
        int test = traverse(listOfNodes,listOfNodes2, hashmap, size, 0);
        return test;
    }


    public int traverse(Deque<Node> listOfNodes, Deque<Node> listOfNodes2, HashMap<String, Integer> hashmap,int size, int hashSetSize) {
        // Storing the next level of children nodes of both tree into separate deques
        Deque<Node> storage = new ArrayDeque<>();
        Deque<Node> storage2 = new ArrayDeque<>();

        // Store the number of total children in each level only
        int totalChildren = 0;

            // To check whether or not all the nodes in the deque has been popped out and processed
            while(listOfNodes.size() != 0 && listOfNodes2.size() != 0){

                // Check if the children of each node is fully processed
                int count = 0;
                int count2 = 0;
                List<Node> children = null;
                List<Node> children2 = null;
                if(listOfNodes.size() != 0){
                    children = listOfNodes.pop().children();
                    while(children.size() != count ){
                        // For each node, the children is retrieved and then the content is
                        // stored in the HashMap to calculate the difference in nodes for each level
                        Node content = children.get(count);
                        storage.add(content);
                        count++;
                        totalChildren++;
                        if(hashmap.get(content.getType()) != null){
                            // if the key is found in the first tree, the value will rewarded and +1
                            hashmap.put(content.getType(), hashmap.get(content.getType()) + 1);
                        }
                        else{
                            hashmap.put(content.getType(), 1);
                        }
                    }
                }
                if(listOfNodes2.size() != 0){
                    children2 = listOfNodes2.pop().children();
                    while(children2.size() != count2 ){
                        // For each node, the children is retrieved and then the content is
                        // stored in the HashMap to calculate the difference in nodes for each level
                        Node content2 = children2.get(count2);
                        storage2.add(content2);
                        count2++;
                        totalChildren++;
                        if(hashmap.get(content2.getType()) != null){
                            // if the key is found in the second tree, the value will penalised and -1
                            hashmap.put(content2.getType(), hashmap.get(content2.getType()) - 1);
                        }
                        else{
                            hashmap.put(content2.getType(), -1);
                        }
                    }
                }
            }

            // Once hashmap is generated, the similarity index is computed by dividing the
            // similar children node against the total number of nodes in the tree
            Set <String> setTest = hashmap.keySet();
            Iterator<String> iter = setTest.iterator();
            int currentSize = 0;

            // this is to iterate through the hashmap to get all the nodes that are similar
            while(iter.hasNext()){
                // the value of the hashmap is always abs (|-1| = 1)
                currentSize += Math.abs(hashmap.get((String)iter.next()));
            }
            // this is to keep the total count of children nodes that are similar as it goes down the tree
            hashSetSize += totalChildren - currentSize;

            // Constantly check as it go through each levels if the similarity index is above 0.8
            // if it is, the function will return 1 to indicate plaglarism right away.
            if(((double)hashSetSize)/ size  > 0.8){
                return 1;
            }
            if(storage.size() == 0 || storage2.size()== 0){
            // else, the function will return 0 to indicate non-plaglarism
                return 0;
            }
            // function is recursively called at every level
            return traverse(storage, storage2, new HashMap<String, Integer>(), size, hashSetSize);
    }



}
