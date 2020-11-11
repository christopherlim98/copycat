package copycat.constants;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    // Contains the logic types that we identified as important
    public static String[] TYPES = {"FunctionDefinition", "IterationStatement", "Expression",
    "ArithmeticExpression","PostfixExpression","ParameterList", "ParameterDeclaration",
"DeclarationSpecifiers","TypeSpecifier","DeclarationSpecifier","TypeSpecifier"
        ,"Identifier","DirectDeclarator","Declarator",
        "Initializer","IntegerConstant","InitDeclarator","InitDeclaratorList","Declaration"};

    // Contains the map for hashing our types to a single character
    public static Map<String,String> HASHDICT =new HashMap<String, String>() {{
        put("TranslationUnit", "a");
        put("FunctionDefinition", "b");
        put("IterationStatement", "c");
        put("Expression", "d");
        put("ArithmeticExpression", "e");
        put("PostfixExpression", "f");
        put("ParameterList", "g");
        put("ParameterDeclaration", "h");
        put("DeclarationSpecifiers", "i");
        put("TypeSpecifier", "j");
        put("Identifier", "k");
        put("DirectDeclarator", "l");
        put("Declarator", "m");
        put("Initializer", "n");
        put("IntegerConstant", "o");
        put("InitDeclarator", "p");
        put("InitDeclaratorList", "q");
        put("Declaration", "r");
    }};

    /**
     * Contains the weights that we associate with the types
     * We used frequency analysis to determine the weights
     * More frequent types had lower weights
     * Less frequent types had higher weights to accentuate uniqueness.
     * Used fibonacci for the difference in weights, as fibonacci sequence highlights a 60% difference with each increment
     */
    public static Map<Character, Integer> HASHWEIGHTS =new HashMap<Character, Integer>() {{
        put('a', 1);
        put('b', 34);
        put('c', 21);
        put('d', 2);
        put('e', 8);
        put('f', 3);
        put('g', 34);
        put('h', 34);
        put('i', 21);
        put('j', 21);
        put('k', 1);
        put('l', 13);
        put('m', 13);
        put('n', 8);
        put('o', 5);
        put('p', 21);
        put('q', 34);
        put('r', 34);
    }};

    /**
     * varying sensitivity level / define similarity threshold
     * in order of importance:
     * FunctionDefinition (Self-defined Method)
     * IterationStatement (Logic calls like do/while/for)
     * Expression/ArithmeticExpression/PostfixExpression (Method calls like printf)/ ArgumentExpressionList
     * ParameterList/ParameterDeclaration/DeclarationSpecifier/TypeSpecifier (Argument)
     * DeclarationSpecifier/TypeSpecifier
     * ----------
     * Declarator/DirectDeclarator
     * TranslationUnit
     * Identifier
     */


    public static final Map<Integer, String> USEROPTIONS =new HashMap<Integer, String>() {{
        put(1, "1. Generate ASTs");
        put(2, "2. Naive Comparison");
        put(3, "3. Progressive Comparison");
        put(4, "4. Snapshot Comparison");
        put(5, "5. Compare All");
        put(6, "6. Exit");
    }};

    public static final String INSTRUCTIONS = "Hello! Welcome to copycat. We scan your source code for plagiarism.\n";
}
