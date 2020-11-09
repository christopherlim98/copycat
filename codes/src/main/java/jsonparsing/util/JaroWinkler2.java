// package jsonparsing.util;

// import java.util.Arrays;

// public class JaroWinkler2 {
//     private static final double DEFAULT_THRESHOLD = 0.7;
//     private static final int THREE = 3;
//     private static final double JW_COEF = 0.1;
//     // private static final double threshold;

//     /**
//      * Returns the current value of the threshold used for adding the Winkler
//      * bonus. The default value is 0.7.
//      *
//      * @return the current value of the threshold
//      */
//     // public static double getThreshold() {
//     //     return threshold;
//     // }

//     /**
//      * Ca lculate Jaro-Winkler similarity.
//      * @param s1 The first string to compare.
//      * @param s2 The second string to compare.
//      * @return The Jaro-Winkler similarity in the range [0, 1]
//      * @throws NullPointerException if s1 or s2 is null.
//      */
//     public static double similarity(final String s1, final String s2) {
//         if (s1 == null) {
//             throw new NullPointerException("s1 must not be null");
//         }

//         if (s2 == null) {
//             throw new NullPointerException("s2 must not be null");
//         }
//         // Similarity score = 1 for same strings
//         if (s1.equals(s2)) {
//             return 1;
//         }

//         int[] mtp = matches(s1, s2);
//         float m = mtp[0];
//         if (m == 0) {
//             return 0.0;
//         }
//         // Calculates the Jaro similarity
//         double jaro = ((m / s1.length() + m / s2.length() + (m - mtp[1]) / m))
//                 / THREE;
//         // Calculates the Jaro-Winkler similarity
//         double jaroWinkler = jaro;
//         if (jaro > getThreshold()) {
//             jaroWinkler = jaro + Math.min(JW_COEF, 1.0 / mtp[3]) * mtp[2] * (1 - jaro);
//         }
//         return jaroWinkler;
//     }

//     /**
//      * Return (1 - similarity)
//      * @param s1 The first string to compare.
//      * @param s2 The second string to compare.
//      * @return (1 - similarity)
//      * @throws NullPointerException if s1 or s2 is null.
//      */
//     public static double distance(String s1, String s2) {
//         return 1.0 - similarity(s1, s2);
//     }

//     private static int[] matches(String s1, String s2) {
//         // Decide which string is longer and shorter
//         String longer, shorter;
//         if (s1.length() > s2.length()) {
//             longer = s1;
//             shorter = s2;
//         } else {
//             longer = s2;
//             shorter = s1;
//         }
//         // Calculate the matching range (How many indexes ahead or behind)
//         int range = Math.max(longer.length() / 2 - 1, 0);

//         // Creates an array to store indexes that match characters in shorter
//         int[] match_indexes = new int[shorter.length()];
//         Arrays.fill(match_indexes, -1);

//         // Creates a boolean array to make sure we do not double count characters
//         boolean[] match_flags = new boolean[longer.length()];   // Default value for boolean is false

//         int matches = 0;

//         // Traverse through each character in shorter
//         for (int shorterIndex = 0 ; shorterIndex < shorter.length() ; shorterIndex++) {
//             char c1 = shorter.charAt(shorterIndex);
//             // Traverse through each character in longer, bounded by range
//             for (int start = Math.max(shorterIndex - range, 0), end = Math.min(shorterIndex + range + 1, longer.length()) ; start < end ; start++) {
//                 if (!match_flags[start] && c1 == longer.charAt(start)) {
//                     match_indexes[shorterIndex] = start;
//                     match_flags[start] = true;
//                     matches++;
//                     break;
//                 }
//             }
//         }
//         // Transfer all matching characters to char arrays, in order
//         char[] ms1 = new char[matches];
//         char[] ms2 = new char[matches];
//         for (int i = 0, k = 0; i < shorter.length(); i++) {
//             if (match_indexes[i] != -1) {
//                 ms1[k] = shorter.charAt(i);
//                 k++;
//             }
//         }
//         for (int i = 0, k = 0; i < longer.length(); i++) {
//             if (match_flags[i]) {
//                 ms2[k] = longer.charAt(i);
//                 k++;
//             }
//         }
//         // Count number transpositions
//         int transpositions = 0;
//         for (int i = 0; i < ms1.length; i++) {
//             if (ms1[i] != ms2[i]) {
//                 transpositions++;
//             }
//         }
//         // Count number of matching characters
//         int prefix = 0;
//         for (int i = 0; i < shorter.length(); i++) {
//             if (s1.charAt(i) == s2.charAt(i)) {
//                 prefix++;
//             } else {
//                 break;
//             }
//         }
//         return new int[]{matches, transpositions / 2, prefix, longer.length()};
//     }
// }