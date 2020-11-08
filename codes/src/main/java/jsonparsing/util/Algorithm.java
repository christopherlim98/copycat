package jsonparsing.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import jsonparsing.parser.Json;
import jsonparsing.entity.AbstractSyntaxTree;
import jsonparsing.entity.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

import jsonparsing.constants.Constants;

import static jsonparsing.parser.Json.readFileAsString;

public class Algorithm{


    public static Set<String> set = new HashSet<>(Arrays.asList(Constants.TYPES));

    public static int hash(String input) {
        int h = 0;
        for (int i=0;i<input.length();i++){
            h^=((int) input.charAt(i));
        }
        return h;

    }

    public static String traverse(LinkedList<String> list,Node root ){
        if (root.children().isEmpty()){
            // Base case: no children
            String type = root.getType();
            list.addFirst(type);
            return type;

        } else {
            String temp = "";
            for (Node child : root.children()){
                temp += traverse(list, child);
            }
            String result = root.getType() + temp;
            list.addFirst(result);
            return result;
        }
    }


}
