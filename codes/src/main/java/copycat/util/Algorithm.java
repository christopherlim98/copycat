package copycat.util;

import java.util.*;

import copycat.constants.Constants;
import copycat.entity.*;

public class Algorithm{
    public static Set<String> set = new HashSet<>(Arrays.asList(Constants.TYPES));

    public String traverse(LinkedList<String> list,Node root ){
        // Post order traversal
        
        String type = root.getType();
        if (root.children().isEmpty()){
            // Base case: no children
            list.addFirst(hash(type));
            return hash(type);

        } else {
            String temp = "";
            for (Node child : root.children()){
                temp += traverse(list, child);
            }
            String result =  hash(type) + temp;
            list.addFirst(result);
            return result;
        }
    }

    public HashMap<Integer, String> traverseWithLevels(HashMap <Integer, String> map, Node root, Integer level){
        // Transforms AST to a hashmap
        // Each key corresponds to level
        // Value is a string concatenation that corresponds to all nodes at that level.
        String type = root.getType();
        if (root.children().isEmpty()){
            // Base case: no children
            if (map.containsKey(level)){
                String v = map.get(level);
                map.put(level,  hash(type) + v );
            } else {
                map.put(level,  hash(type));
            }
            return map;
        } else {
            for (Node child : root.children()){
                traverseWithLevels(map, child, level + 1);
            }
            if (map.containsKey(level)){
                String v = map.get(level);
                map.put(level,  hash(type) + v );
            } else {
                map.put(level,  hash(type) );
            }
            return map;
        }
    }



    public static String hash(String type) {
        return Constants.HASHDICT.get(type);
    }

}