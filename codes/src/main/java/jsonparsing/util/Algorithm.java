package jsonparsing.util;

import jsonparsing.entity.*;

import java.util.*;

import jsonparsing.constants.Constants;

public class Algorithm{
    public static Set<String> set = new HashSet<>(Arrays.asList(Constants.TYPES));

    public String traverse(LinkedList<String> list,Node root ){
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

    public static int polynomialHash(String input) {
        int h = 0;
        for (int i=0;i<input.length();i++){
            h^=((int) input.charAt(i));
        }
        return h;
    }

    public static String hash(String type) {
        return Constants.HASHDICT.get(type);
    }

}
