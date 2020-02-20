/******************************************************************************
 *  author:     Xiaoyu Zhang
 *  created:    2020/02/20
 *  Compilation:  javac-algs4 AlgoString.java
 *  Execution:    java-algs4 AlgoString
 *  Dependencies: StdOut.java
 *
 *  This class is the definition for some classic String algorithm implementation.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlgoString {

    public AlgoString() {

    }

    // Challenge 1
    //      Given a string S and a set of words D, find the longest word in D
    //      that is a subsequence of S.
    //
    // Word W is a subsequence of S if some number of characters,
    // possibly zero, can be deleted from S to form W, without reordering
    // the remaining characters.
    //
    // Note: D can appear in any format (list, hash table, prefix tree, etc.
    //
    // For example, given the input of S = "abppplee"
    // and D = {"able", "ale", "apple", "bale", "kangaroo"}
    // the correct output would be "apple"
    //
    // The words "able" and "ale" are both subsequences of S,
    // but they are shorter than "apple".
    // The word "bale" is not a subsequence of S because even
    // though S has all the right letters, they are not in the right order.
    // The word "kangaroo" is the longest word in D, but it isn't a
    // subsequence of S.

    /**
     * Returns longest word in a dictionary that is a sequence of some string
     *
     * @param word    the given String from which the sequence to be formed
     * @param letters the dictionary
     * @return longest word in dict {@code letters} if present; {@code null} otherwise
     */
    public String findLongestWordInString(String word, String[] letters) {
        // preprocess the String word by build a map of letter to indices;
        Map<Character, ArrayList<Integer>> map = buildMap(word);

        // then for the character in the letter, we need to know
        // "Given my last matched character was at index X
        // and my next character to match is Y, where does this match occur?",
        // you would look up Y in the map, and then binary search the occurrence
        // list to find the smallest number > X in that list,
        // or report that none exists (the word is not a subsequence then).
        int maxLen = -1;
        String result = null;

        for (int i = 0; i < letters.length; i++) {
            int prevIndex = -1;

            for (int j = 0; j < letters[i].length(); j++) {
                char c = letters[i].charAt(j);
                ArrayList<Integer> list = map.get(c);
                Integer[] arr = list.toArray(new Integer[list.size()]);
                // binary search to find the smallest key > the current index
            }
        }

        return null;
    }

    // Build a map of letter -> sorted list of indices where letter occurs.
    public Map<Character, ArrayList<Integer>> buildMap(String word) {
        Map<Character, ArrayList<Integer>> charMapIndex = new HashMap<>();

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (charMapIndex.containsKey(c)) {
                ArrayList<Integer> list = charMapIndex.get(c);
                list.add(i);
            }
            else {
                ArrayList<Integer> list = new ArrayList<>();
                list.add(i);
                charMapIndex.put(c, list);
            }
        }

        return charMapIndex;
    }

    /**
     * Returns the smallest key which is greater than specified key.
     *
     * @param a   the array of integers, must be sorted in ascending order
     * @param key the search key
     * @return key in array {@code a} if present; {@code -1} otherwise
     */
    public int keyOf(int[] a, int key) {
        if (key < a[0])
            return a[0];
        if (key > a[a.length - 1])
            return -1;

        int lo = 0;
        int hi = a.length - 1;
        int mid = -1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            mid = lo + (hi - lo) / 2;
            if (a[mid] > key) hi = mid - 1;
            else if (a[mid] <= key) lo = mid + 1;
            // else return a[mid + 1];
        }

        StdOut.printf("lo = %d, mid = %d, hi = %d \n", lo, mid, hi);
        return a[lo];
    }

    /**
     * Unit tests the {@code FileManager} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        AlgoString algoStr = new AlgoString();

        String word = "abppplee";
        Map<Character, ArrayList<Integer>> map = algoStr.buildMap(word);
        for (Map.Entry<Character, ArrayList<Integer>> entry : map.entrySet()) {
            Character key = entry.getKey();
            ArrayList<Integer> list = entry.getValue();
            StdOut.printf("%c -> [ ", key);

            // two ways to convert an ArrayList to an array
            /* appraoch 1
            int[] arr = new int[list.size()];
            for (int i = 0; i < list.size(); i++)
                arr[i] = list.get(i);

             */
            // approach 2
            Integer[] arr = list.toArray(new Integer[list.size()]);

            for (int index : arr)
                StdOut.printf("%d ", index);
            StdOut.println("]");
        }

        int a[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        // int a2[] = { 3, 5, 6, 9, 12, 15, 16, 17, 26, 50, 100 };
        StdOut.printf("%d \n", algoStr.keyOf(a, 3));
    }
}
