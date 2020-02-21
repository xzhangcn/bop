/******************************************************************************
 *
 * created:    2020/02/20
 * Compilation:  javac-algs4 AlgoString.java
 * Execution:    java-algs4 AlgoString
 * Dependencies: StdOut.java
 *
 * This class is the definition for some classic String algorithm implementation.
 *
 * @author Xiaoyu Zhang
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlgoString {

    public AlgoString() {

    }

    /**
     * Challenge 1
     *      Given a string S and a set of words D, find the longest word in D
     *      that is a subsequence of S.
     *
     * Word W is a subsequence of S if some number of characters,
     * possibly zero, can be deleted from S to form W, without reordering
     * the remaining characters.
     *
     * Note: D can appear in any format (list, hash table, prefix tree, etc.
     *
     * For example, given the input of S = "abppplee"
     * and D = {"able", "ale", "apple", "bale", "kangaroo"}
     * the correct output would be "apple"
     *
     * The words "able" and "ale" are both subsequences of S,
     * but they are shorter than "apple".
     * The word "bale" is not a subsequence of S because even
     * though S has all the right letters, they are not in the right order.
     * The word "kangaroo" is the longest word in D, but it isn't a
     * subsequence of S.
     */

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
        boolean found = true;

        for (int i = 0; i < letters.length; i++) {
            int prevIndex = -1;

            // StdOut.printf("letter: %s \n", letters[i]);

            for (int j = 0; j < letters[i].length(); j++) {
                char c = letters[i].charAt(j);
                ArrayList<Integer> list = map.get(c);

                // StdOut.printf("character <<< %c \n", c);

                if (list == null) {
                    found = false;
                    break;
                }

                // StdOut.printf("character %c \n", c);

                Integer[] arr = list.toArray(new Integer[list.size()]);
                // binary search to find the smallest key > the current index
                int currIndex = smallestKey(arr, prevIndex);
                if (currIndex == -1) {
                    found = false;
                    break;
                }
                prevIndex = currIndex;
            }

            if (found) {
                // StdOut.printf("interim found >>>>>>>>>>>>>>>: %s \n\n", letters[i]);

                if (letters[i].length() > maxLen) {
                    maxLen = letters[i].length();
                    result = letters[i];
                }
            }
        }

        // StdOut.printf("result >>>>>>>>>>>>>>>: %s \n\n", result);
        return result;
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
     * Returns the smallest key which is greater than specified key. For the problem of finding
     * longest word in String, since all keys are indices into a letter, so all keys are equal or
     * greater than 0.
     *
     * @param a   the array of integers, must be sorted in ascending order
     * @param key the specified key to be compared with
     * @return key in array {@code a} if present; {@code -1} otherwise
     */
    public int smallestKey(Integer[] a, int key) {

        /*
        StdOut.printf("array a >>>>>> key %d \n", key);
        for (int num : a)
            StdOut.printf("%d \t", num);
        StdOut.println();
         */

        if (key < a[0])
            return a[0];
        if (key >= a[a.length - 1])
            return -1;

        int lo = 0;
        int hi = a.length - 1;
        int mid = -1;
        while (lo <= hi) {
            mid = lo + (hi - lo) / 2;

            // StdOut.printf("lo = %d, mid = %d, hi = %d \n", lo, mid, hi);

            if (a[mid] > key) hi = mid - 1;
            else if (a[mid] <= key) lo = mid + 1;

            // StdOut.printf("lo = %d, mid = %d, hi = %d \n\n", lo, mid, hi);
        }

        return a[lo];
    }

    /**
     * Returns the index of the specified key in the specified array. The below is the classic
     * binary search implementation for finding the index of the search key in a sorted array.
     *
     * @param a   the array of integers, must be sorted in ascending order
     * @param key the search key
     * @return index of key in array {@code a} if present; {@code -1} otherwise
     */
    public static int indexOf(int[] a, int key) {
        int lo = 0;
        int hi = a.length - 1;
        while (lo <= hi) {
            // Key is in a[lo..hi] or not present.
            int mid = lo + (hi - lo) / 2;
            if (key < a[mid]) hi = mid - 1;
            else if (key > a[mid]) lo = mid + 1;
            else return mid;
        }
        return -1;
    }

    /**
     * Given a non-empty string like "Code" return a string like "CCoCodCode". Examples:
     * stringSplosion("Code") → "CCoCodCode" stringSplosion("abc") → "aababc" stringSplosion("ab") →
     * "aab"
     *
     * @param str given non-empty string
     * @return string being exploded
     */
    public String stringSplosion(String str) {

        // StdOut.printf("str length %d \n", str.length());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            String subStr = str.substring(0, i + 1);
            sb.append(subStr);

            // StdOut.printf("i = %d, subStr %s \n", i, subStr);
        }

        return sb.toString();
    }

    /**
     * Consider the leftmost and righmost appearances of some value in an array. We'll say that the
     * "span" is the number of elements between the two inclusive. A single value has a span of 1.
     * Returns the largest span found in the given array. (Efficiency is not a priority.)
     * <p>
     * maxSpan([1, 2, 1, 1, 3]) → 4;
     * <p>
     * maxSpan([1, 4, 2, 1, 4, 1, 4]) → 6.
     * <p>
     * maxSpan([1, 4, 2, 1, 4, 4, 4]) → 6.
     *
     * @param nums the array of integers
     * @return the max span of the array {@code nums}
     */
    public int maxSpan(int[] nums) {
        if (nums.length == 0)
            return 0;

        // build the mapping table [ values -> (beginning index) + "#" + (end index) ]
        Map<Integer, String> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int val = nums[i];
            String strIndex = map.get(val);

            if (strIndex == null) {
                // encounter the value first time, use # as delimiter
                // and put -1 as the end index to be a sentinal
                map.put(val, String.valueOf(i) + "#" + String.valueOf(-1));
            }
            else {
                // StdOut.printf("i: %d; val: %d; strIndex: %s \n", i, val, strIndex);

                int delimitIndex = strIndex.indexOf("#");
                String newStrIndex = strIndex.substring(0, delimitIndex) + "#" + String.valueOf(i);
                map.put(val, newStrIndex);
            }
        }

        // once we have the mapping table built, we can go through it to find the max span.
        // Although we can find the max span while traversing the array in the first time,
        // this way of putting code in separate section is cleaner and easy to understand.
        int maxDistance = 1;
        int maxKey = Integer.MIN_VALUE;
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            Integer key = entry.getKey();
            String[] strIndices = entry.getValue().split("#");

            int indexStart = Integer.parseInt(strIndices[0]);
            int indexEnd = Integer.parseInt(strIndices[1]);
            if (indexEnd != -1) {
                int distance = indexEnd - indexStart + 1;
                if (distance > maxDistance) {
                    maxDistance = distance;
                    maxKey = key;
                }
            }
        }

        StdOut.printf("the value for the max span in the array is %d \n", maxKey);
        return maxDistance;
    }

    /**
     * Given two strings, base and remove, return a version of the base string where all instances
     * of the remove string have been removed (not case sensitive). You may assume that the remove
     * string is length 1 or more. Remove only non-overlapping instances, so with "xxx" removing
     * "xx" leaves "x".
     * <p>
     * withoutString("Hello there", "llo") → "He there"
     * <p>
     * withoutString("Hello there", "e") → "Hllo thr"
     * <p>
     * withoutString("Hello there", "x") → "Hello there"
     *
     * @param base   the base string from which certain string {@code remove} to be removed
     * @param remove the remove string where all instances of it in {@code base} will be removed
     * @return
     */
    public String withoutString(String base, String remove) {
        return null;
    }

    /**
     * Unit tests the {@code FileManager} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {

        AlgoString algoStr = new AlgoString();

        String word = "abppplee";
        String[] letters = { "able", "ale", "apple", "bale", "kangaroo" };

        // String word = "maobpcpnklea";
        // String[] letters = { "malea", "apple", "monkey", "plea" };


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


        //int a[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        // Integer a2[] = { 3, 5, 6, 9, 12, 15, 16, 17, 26, 50, 100 };
        // StdOut.printf("%d \n", algoStr.smallestKey(a2, 28));

        StdOut.printf("word: %s \n", word);
        StdOut.println("letters: ");
        for (String str : letters)
            StdOut.printf("%s \t", str);
        StdOut.println();
        String result = algoStr.findLongestWordInString(word, letters);
        StdOut.printf("The longest word in dict which is the sequence of string %s is %s \n", word,
                      result);

        StdOut.println("\n>>> Testing: stringSplosion");
        String str = "Code";
        StdOut.printf("%s after splosion is %s \n", str, algoStr.stringSplosion(str));

        StdOut.println("\n>>> Testing: maxSpan");
        int[] nums = { 1, 4, 2, 1, 4, 1, 4 };
        for (int num : nums)
            StdOut.printf("%d \t", num);
        StdOut.println();
        StdOut.printf("maxSpan is %d \n", algoStr.maxSpan(nums));

        StdOut.println("\n>>> Testing: replaceAll");
        String base = "Hello there";
        StdOut.printf("%s \n", base.replaceAll("llo", ""));
    }
}
