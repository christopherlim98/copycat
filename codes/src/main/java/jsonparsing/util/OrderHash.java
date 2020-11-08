package jsonparsing.util;

public class OrderHash {
    // We care about order (Polynomial)
    /* Experiments have shown that 33, 37, 39, and 41 are particularly good choices for a 
    when working with character strings that are English words. 
    In fact, in a list of over 50,000 English words, taking a to be 33, 37, 39, or 41 produced less than 7 collisions 
    in each case.*/
    public static Long polynomialHash(String s) {
        Long h = 0L;
        int a = 33;
        for (int i = 0 ; i < s.length() ; i++) {
            h *= a;
            h += ((int)s.charAt(i));
        }
        return h;
    }
}
