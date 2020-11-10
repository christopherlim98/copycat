package jsonparsing.util;

import java.util.Arrays;

public class JaroWinkler2 {
    private static final double DEFAULT_THRESHOLD = 0.7;
    private static final double p = 0.1;    // Constant scaling factor in Jaro-Winkler Similarity formula

    /**
     * Calculate Jaro-Winkler similarity.
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return The Jaro-Winkler similarity in the range [0, 1]
     * @throws NullPointerException if s1 or s2 is null.
     */
    public static double similarity(final String s1, final String s2) {
        if (s1 == null || s2 == null) {
            throw new NullPointerException("Strings must not be null");
        } else if (s1.equals(s2)) {
        // Similarity score = 1 for same strings
            return 1;
        }

        int[] mtl = match(s1, s2);
        // Number of matching characters
        int m = mtl[0];
        // Number of transpositions
        int t = mtl[1];
        // Length of common prefix at the start of the string
        int l = mtl[2];
        if (m == 0) {
            return 0.0;
        }
        // Calculates the Jaro similarity using formula
        double jaro = ((m / s1.length() + m / s2.length() + (m - t) / m)) / 3;
        // Calculates the Jaro-Winkler similarity using formula if Jaro similarity > 0.7
        double jaroWinkler = jaro;
        if (jaro > DEFAULT_THRESHOLD) {
            jaroWinkler = jaro + p * l * (1 - jaro);
        }
        return jaroWinkler;
    }

    /**
     * Return Jaro Winkler distance
     * @param s1 The first string to compare.
     * @param s2 The second string to compare.
     * @return (1 - similarity)
     * @throws NullPointerException if s1 or s2 is null.
     */
    public static double distance(String s1, String s2) {
        return 1.0 - similarity(s1, s2);
    }

    // Gets the number of matching characters, transpositions and longest prefix matches between two strings
    private static int[] match(String s1, String s2) {
        // Decide which string is longer and shorter
        String longer, shorter;
        if (s1.length() > s2.length()) {
            longer = s1;
            shorter = s2;
        } else {
            longer = s2;
            shorter = s1;
        }
        // Calculate the matching range (How many indexes ahead or behind)
        int range = Math.max(longer.length() / 2 - 1, 0);

        // Creates an array to store indexes that match characters in shorter
        int[] match_indexes = new int[shorter.length()];
        Arrays.fill(match_indexes, -1);

        // Creates a boolean array to make sure we do not double count characters
        boolean[] match_flags = new boolean[longer.length()];   // Default value for boolean is false

        int matches = 0;

        // Traverse through each character in shorter
        for (int shorterIndex = 0 ; shorterIndex < shorter.length() ; shorterIndex++) {
            char c1 = shorter.charAt(shorterIndex);
            // Traverse through each character in longer, bounded by range
            for (int start = Math.max(shorterIndex - range, 0), end = Math.min(shorterIndex + range + 1, longer.length()) ; start < end ; start++) {
                if (!match_flags[start] && c1 == longer.charAt(start)) {
                    match_indexes[shorterIndex] = start;
                    match_flags[start] = true;
                    matches++;
                    break;
                }
            }
        }
        // Transfer all matching characters to char arrays, in order
        char[] ms1 = new char[matches];
        char[] ms2 = new char[matches];
        // Stores the indexes of chars belonging to shorter string that matched with chars in longer string
        for (int i = 0, k = 0; i < shorter.length(); i++) {
            if (match_indexes[i] != -1) {
                ms1[k] = shorter.charAt(i);
                k++;
            }
        }
        for (int i = 0, k = 0; i < longer.length(); i++) {
            if (match_flags[i]) {
                ms2[k] = longer.charAt(i);
                k++;
            }
        }
        // Count of non-matching character at same index
        int countNonMatching = 0;
        for (int i = 0; i < ms1.length; i++) {
            if (ms1[i] != ms2[i]) {
                countNonMatching++;
            }
        }
        // Number of transposition = Count of non-matching character at same index / 2
        int transpositions = countNonMatching / 2;

        // Count largest number of exact character matches at the beginning of each string
        int prefixMatches = 0;
        for (int i = 0; i < shorter.length(); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                prefixMatches++;
            } else {
                break;
            }
        }
        return new int[]{matches, transpositions, prefixMatches};
    }
}