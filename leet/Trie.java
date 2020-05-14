/**
 * Leetcode Problem on the day of 05/14/2020: Implement Trie (Prefix Tree).
 */

class Trie {
    private static final int R = 26;        // 26 capital letter from a to z
    private Node root;                      // root of trie
    private int n;                          // number of keys in trie

    // R-way trie node
    private static class Node {
        private int val;                    // default 0 for non-key, 1 for key
        private Node[] next = new Node[R];
    }

    /** Initialize your data structure here. */
    public Trie() {
        root = null;
        n = 0;
    }

    /** Inserts a word into the trie. */
    public void insert(String word) {
        if (word == null) throw new IllegalArgumentException("argument to insert() is null");
        root = put(root, word, 0);
    }

    private Node put(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (x.val == 0)
                n++;

            x.val = 1;
            return x;
        }

        char c = key.charAt(d);
        x.next[c - 'a'] = put(x.next[c - 'a'], key, d + 1);

        return x;
    }

    /** Returns if the word is in the trie. */
    public boolean search(String word) {
        if (word == null) throw new IllegalArgumentException("argument to search() is null");
        Node x = get(root, word, 0);

        return x != null && x.val == 1;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        
        return get(x.next[c - 'a'], key, d + 1);
    }

    /** Returns if there is any word in the trie that starts with the given prefix. */
    public boolean startsWith(String prefix) {
        return get(root, prefix, 0) != null;
    }

    /**
     * Unit tests
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Trie trie = new Trie();
        String s1 = "apple", s2 = "app";

        trie.insert(s1);
        System.out.printf("search '%s': %b\n", s1, trie.search(s1));            // returns true
        System.out.printf("search '%s': %b\n", s2, trie.search(s2));            // returns false
        System.out.printf("start with '%s': %b\n", s2, trie.startsWith(s2));    // returns true

        trie.insert(s2);
        System.out.printf("search '%s': %b\n", s2, trie.search(s2));            // returns true
    }
}
