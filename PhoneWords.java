import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;
import java.util.List;

/******************************************************************************
 *  author:     Xiaoyu Zhang
 *  created:    2020/02/05
 *  Compilation:  javac-algs4 PhoneWords.java
 *  Execution:    java-algs4 PhoneWords
 *  Dependencies: StdOut.java
 *
 *  Given a string containing digits from 0-9 inclusive,
 *  return all possible English words that the number could represent.
 *
 ******************************************************************************/

public class PhoneWords {
    private final String letterMap[] = {
            "",     // 0
            "",     // 1
            "ABC",  // 2
            "DEF",  // 3
            "GHI",  // 4
            "JKL",  // 5
            "MNO",  // 6
            "PQRS", // 7
            "TUV",  // 8
            "WXYZ"  // 9
    };

    private Queue<String> res;

    private final TrieST26<Integer> st;      // dictionary represented in 26-way trie
    private static final int R = 26;        // 26 capital letter from A to Z

    public PhoneWords(String[] dictionary) {
        this.res = new Queue<String>();

        st = new TrieST26<>();
        for (int i = 0; i < dictionary.length; i++)
            st.put(dictionary[i], i);
    }

    public Queue<String> letterCombinations(String phone) {
        if (phone == null || phone.equals(""))
            return res;

        findCombination(phone, 0, "");
        return res;
    }

    private void findCombination(String digits, int index, String s) {
        if (index == digits.length()) {
            res.enqueue(s);
            return;
        }

        char c = digits.charAt(index);
        String letters = letterMap[c - '0'];
        for (int i = 0; i < letters.length(); i++) {
            findCombination(digits, index + 1, s + letters.charAt(i));
        }
    }

    public Queue<String> validLetterCombinations(String phone) {
        if (phone == null || phone.equals(""))
            return res;

        findValidCombination(phone, 0, "");
        return res;
    }

    private void findValidCombination(String digits, int index, String s) {

        if (st.hasKeysWithPrefix(s) == null)
            return;

        if (index == digits.length()) {
            if (st.contains(s)) res.enqueue(s);


            return;
        }

        char c = digits.charAt(index);
        String letters = letterMap[c - '0'];
        for (int i = 0; i < letters.length(); i++) {
            findValidCombination(digits, index + 1, s + letters.charAt(i));
        }
    }

    public List<String> letterCombinations2(String phone) {
        LinkedList<String> ans = new LinkedList<String>();
        if (phone.isEmpty()) return ans;

        String[] mapping = new String[] {
                "0", "1", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"
        };

        ans.add("");

        for (int i = 0; i < phone.length(); i++) {
            int x = Character.getNumericValue(phone.charAt(i));
            while (ans.peek().length() == i) {
                String t = ans.remove();
                for (char s : mapping[x].toCharArray())
                    ans.add(t + s);
            }
        }
        return ans;
    }

    // R-way trie node
    private static class Node {
        private Object val;
        private Node[] next = new Node[R];
    }

    private class TrieST26<Value> {
        private Node root;      // root of trie
        private int n;          // number of keys in trie

        /**
         * Initializes an empty string symbol table.
         */
        public TrieST26() {
        }

        /**
         * Returns the value associated with the given key.
         *
         * @param key the key
         * @return the value associated with the given key if the key is in the symbol table and
         * {@code null} if the key is not in the symbol table
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public Value get(String key) {
            if (key == null) throw new IllegalArgumentException("argument to get() is null");
            Node x = get(root, key, 0);
            if (x == null) return null;
            return (Value) x.val;
        }

        /**
         * Does this symbol table contain the given key?
         *
         * @param key the key
         * @return {@code true} if this symbol table contains {@code key} and {@code false}
         * otherwise
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public boolean contains(String key) {
            if (key == null) throw new IllegalArgumentException("argument to contains() is null");
            return get(key) != null;
        }

        private Node get(Node x, String key, int d) {
            if (x == null) return null;
            if (d == key.length()) return x;
            char c = key.charAt(d);
            return get(x.next[c - 'A'], key, d + 1);
        }

        /**
         * Inserts the key-value pair into the symbol table, overwriting the old value with the new
         * value if the key is already in the symbol table. If the value is {@code null}, this
         * effectively deletes the key from the symbol table.
         *
         * @param key the key
         * @param val the value
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public void put(String key, Value val) {
            if (key == null) throw new IllegalArgumentException("first argument to put() is null");
            if (val == null) delete(key);
            else root = put(root, key, val, 0);
        }

        private Node put(Node x, String key, Value val, int d) {
            if (x == null) x = new Node();
            if (d == key.length()) {
                if (x.val == null) n++;
                x.val = val;
                return x;
            }
            char c = key.charAt(d);
            x.next[c - 'A'] = put(x.next[c - 'A'], key, val, d + 1);
            return x;
        }

        /**
         * Returns the number of key-value pairs in this symbol table.
         *
         * @return the number of key-value pairs in this symbol table
         */
        public int size() {
            return n;
        }

        /**
         * Is this symbol table empty?
         *
         * @return {@code true} if this symbol table is empty and {@code false} otherwise
         */
        public boolean isEmpty() {
            return size() == 0;
        }

        public Node hasKeysWithPrefix(String prefix) {
            // StdOut.printf("ST26: %s \n", prefix);

            return get(root, prefix, 0);
        }

        /**
         * Gets a prefix node given a prefix and another prefix node to start the search from.
         *
         * @param prefix The prefix to search for.
         * @param start  The node to start the prefix search from. If {@code null} is provided the
         *               search will start from the root node
         * @returns The prefix node corresponding to {@code prefix} or {@code null} if the prefix
         * node does not exists.
         */
        public Node getPrefixNodeFrom(final String prefix, final Node start) {
            if (start == null)
                return get(root, prefix, 0);
            else
                return get(start, prefix, prefix.length() - 1);
        }

        /**
         * Removes the key from the set if the key is present.
         *
         * @param key the key
         * @throws IllegalArgumentException if {@code key} is {@code null}
         */
        public void delete(String key) {
            if (key == null) throw new IllegalArgumentException("argument to delete() is null");
            root = delete(root, key, 0);
        }

        private Node delete(Node x, String key, int d) {
            if (x == null) return null;
            if (d == key.length()) {
                if (x.val != null) n--;
                x.val = null;
            }
            else {
                char c = key.charAt(d);
                x.next[c - 'A'] = delete(x.next[c - 'A'], key, d + 1);
            }

            // remove subtrie rooted at x if it is completely empty
            if (x.val != null) return x;
            for (int c = 0; c < R; c++)
                if (x.next[c] != null)
                    return x;
            return null;
        }
    }

    /**
     * Unit tests the {@code FileManager} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String phone = args[0];

        In in = new In(args[1]);
        String[] dictionary = in.readAllStrings();

        PhoneWords pw = new PhoneWords(dictionary);
        StdOut.printf("Valid English words that can be formed from phone number of %s: \n", phone);
        for (String word : pw.validLetterCombinations(phone))
            StdOut.printf("%s \t", word);
        StdOut.println();
    }
}
