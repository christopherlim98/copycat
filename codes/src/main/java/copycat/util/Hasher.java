package copycat.util;

public class Hasher {

            // recursively traverse the tree


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

        /*
         * at each node, simple/cheap hash:
         *  - type
         *  - content
         *  - children number
         *
         * at each node, complex/expensive hash:
         *  - level
         *  - children
         *  - hash of all the children
         *  -
         */
        //



    // We care about order (Polynomial)
    /* Experiments have shown that 33, 37, 39, and 41 are particularly good choices for a
    when working with character strings that are English words.
    In fact, in a list of over 50,000 English words, taking a to be 33, 37, 39, or 41 produced less than 7 collisions
    in each case.*/
    public static int polynomialHash(String s) {
        int h = 0;
        int a = 27;
        for (int i = 0 ; i < s.length() ; i++) {
            h *= 27;
            h += ((int)s.charAt(i));
        }
        return h;
    }
    public static int bitManipulationHash(String input) {
        int h = 0;
        for (int i=0;i<input.length();i++){
            h^=((int) input.charAt(i));
        }
        return h;
    }
}
