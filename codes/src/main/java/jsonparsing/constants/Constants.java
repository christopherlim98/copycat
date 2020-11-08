package jsonparsing.constants;

import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static String[] TYPES = {"FunctionDefinition", "IterationStatement", "Expression",
    "ArithmeticExpression","PostfixExpression","ParameterList", "ParameterDeclaration",
"DeclarationSpecifiers","TypeSpecifier","DeclarationSpecifier","TypeSpecifier"
        ,"Identifier","DirectDeclarator","Declarator",
        "Initializer","IntegerConstant","InitDeclarator","InitDeclaratorList","Declaration"};

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
}
