package jsonparsing.util;

import jsonparsing.entity.*;

import java.util.*;

import jsonparsing.constants.Constants;

public class Algorithm{
    public static Set<String> set = new HashSet<>(Arrays.asList(Constants.TYPES));

    public static int hash(String input) {
        int h = 0;
        for (int i=0;i<input.length();i++){
            h^=((int) input.charAt(i));
        }
        return h;

    }

    public String traverse(LinkedList<String> list,Node root ){
        if (root.children().isEmpty()){
            // Base case: no children
            String type = root.getType();

            list.addFirst(Constants.HASHDICT.get(type));
            return Constants.HASHDICT.get(type);

        } else {
            String temp = "";
            for (Node child : root.children()){
                temp += traverse(list, child);
            }
            String type = root.getType();

            String result =  Constants.HASHDICT.get(type) + temp;
            list.addFirst(result);
            return result;
        }
    }

    public HashMap<Integer, String> traverseWithLevels(HashMap <Integer, String> map, Node root, Integer level){
        if (root.children().isEmpty()){
            // Base case: no children
            String type = root.getType();
            if (map.containsKey(level)){
                String v = map.get(level);
                map.put(level,  Constants.HASHDICT.get(type) + v );
            } else {
                map.put(level,  Constants.HASHDICT.get(type));
            }
            return map;
        } else {
            for (Node child : root.children()){
                traverseWithLevels(map, child, level + 1);
            }
            String type = root.getType();
            if (map.containsKey(level)){
                String v = map.get(level);
                map.put(level,  Constants.HASHDICT.get(type) + v );
            } else {
                map.put(level,  Constants.HASHDICT.get(type) );
            }
            return map;
        }
    }


}
