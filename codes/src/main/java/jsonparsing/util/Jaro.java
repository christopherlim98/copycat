package jsonparsing.util;

import java.util.*;
public class Jaro {

    public double compute(String inputName, String sanctionName) {
        int inputNameLength = inputName.length();
        int sanctionNameLength = sanctionName.length();

        if (inputName.equals(sanctionName)) {
            return 1;
        }
        int matchDistance = Integer.max(inputNameLength, sanctionNameLength) / 2 - 1;
        boolean[] inputNameMatches = new boolean[inputNameLength];
        boolean[] sanctionNameMatches = new boolean[sanctionNameLength];
        Map<Integer, Character> inputMap = new TreeMap<>();
        Map<Integer, Character> sanctionMap = new TreeMap<>();

        int matches = 0;
        int transpositions = 0;
        for (int i = 0; i < inputNameLength; i++) {
            int start = Integer.max(0, i - matchDistance);
            int end = Integer.min(i + matchDistance + 1, sanctionNameLength);

            for (int j = start; j < end; j++) {

                if (sanctionNameMatches[j] || (inputName.charAt(i) != sanctionName.charAt(j))) {
                    // if the sanction character is matched, just continue with the rest of the code
                    continue;
                }
                inputNameMatches[i] = true;
                sanctionNameMatches[j] = true;
                inputMap.put(i, inputName.charAt(i));
                sanctionMap.put(j, sanctionName.charAt(j));
                matches++;

            }
        }
        if (matches == 0) return 0;

        ArrayList<Character> inputMatchedChars = new ArrayList<>(inputMap.values());
        ArrayList<Character> sanctionMatchedChars = new ArrayList<>(sanctionMap.values());


        int mapSize = Integer.min(inputMap.size(), sanctionMap.size());
        for (int i = 0; i < mapSize; i++) {
            if (!(inputMatchedChars.get(i).equals(sanctionMatchedChars.get(i)))) {
                transpositions++;
            }
        }


            return (((double) matches / inputNameLength) +
                    ((double) matches / sanctionNameLength) +
                    (((double) matches - transpositions / 2.0) / matches)) / 3.0;
        }

    }



