package stu.hellen.algorithm.treesolutions;

import java.util.HashMap;
import java.util.Scanner;

public class FrequencyCounter {
    // Do not instantiate.
    private FrequencyCounter() { }

    /**
     * Reads in a command-line integer and sequence of words from
     * standard input and prints out a word (whose length exceeds
     * the threshold) that occurs most frequently to standard output.
     * It also prints out the number of words whose length exceeds
     * the threshold and the number of distinct such words.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        int distinct = 0, words = 0;
        int minlen = Integer.parseInt(args[0]);
        HashMap<String, Integer> st = new HashMap<>();

        Scanner scanner = new Scanner(System.in);
        // compute frequency counts
        while (scanner.hasNext()) {
            String key = scanner.next();
            if (key.length() < minlen) continue;
            words++;
            if (st.containsKey(key)) {
                st.put(key, st.get(key) + 1);
            }
            else {
                st.put(key, 1);
                distinct++;
            }
        }

        // find a key with the highest frequency count
        String max = "";
        st.put(max, 0);
        for (String word : st.keySet()) {
            if (st.get(word) > st.get(max))
                max = word;
        }

        System.out.println(max + " " + st.get(max));
        System.out.println("distinct = " + distinct);
        System.out.println("words    = " + words);
    }
}
