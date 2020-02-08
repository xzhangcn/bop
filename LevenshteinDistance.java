import edu.princeton.cs.algs4.StdOut;

/******************************************************************************
 *  author:     Xiaoyu Zhang
 *  created:    2020/02/06
 *  Compilation:  javac-algs4 LevenshteinDistance.java
 *  Execution:    java-algs4 LevenshteinDistance
 *  Dependencies: StdOut.java
 *
 *  The Levenshtein distance is a measure of dissimilarity between two Strings.
 *  Mathematically, given two Strings x and y, the distance measures
 *  the minimum number of character edits required to transform x into y.
 *
 *  Typically three type of edits are allowed:
 *      Insertion of a character c
 *      Deletion of a character c
 *      Substitution of a character c
 *
 *  This can further be optimized by observing that we only need the value of
 *  three adjacent cells in the table to find the value of the current cell.
 ******************************************************************************/

public class LevenshteinDistance {
    private String s1;
    private String s2;
    private int m;
    private int n;

    Node[][] dp;

    public LevenshteinDistance(String s1, String s2) {
        this.s1 = s1;
        this.s2 = s2;

        this.m = s1.length();
        this.n = s2.length();

        dp = new Node[m + 1][n + 1];
        for (int i = 0; i <= this.m; i++)
            for (int j = 0; j <= this.n; j++)
                dp[i][j] = new Node();
    }

    /*
    public int calculate(String x, String y) {
        if (x.isEmpty())
            return y.length();

        if (y.isEmpty())
            return x.length();

        int substitution = calculate(x.substring(1), y.substring(1))
                + costOfSubstitution(x.charAt(0), y.charAt(0));
        int insertion = calculate(x, y.substring(1)) + 1;
        int deletion = calculate(x.substring(1), y) + 1;

        return min(substitution, insertion, deletion);
    }

    private int costOfSubstitution(char a, char b) {
        return a == b ? 0 : 1;
    }

    private int min(int... numbers) {
        return Arrays.stream(numbers).min().orElse(Integer.MAX_VALUE);
    }

    // calcuate the distance with dynamic programming approach
    public int calculateDP(String x, String y) {
        int[][] dp = new int[x.length() + 1][y.length() + 1];

        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                }
                else if (j == 0) {
                    dp[i][j] = i;
                }
                else {
                    dp[i][j] = min(
                            dp[i - 1][j - 1] + costOfSubstitution(x.charAt(i - 1), y.charAt(j - 1)),
                            dp[i - 1][j] + 1, dp[i][j - 1] + 1);
                }
            }
        }

        return dp[x.length()][y.length()];
    }

    // dp approach 2
    public int calculateDP2(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();

        int[][] dp = new int[m + 1][n + 1];

        // base case
        for (int i = 1; i <= m; i++)
            dp[i][0] = i;
        for (int j = 1; j <= n; j++)
            dp[0][j] = j;

        // bottom up
        for (int i = 1; i <= m; i++)
            for (int j = 1; j <= n; j++)
                if (s1.charAt(i - 1) == s2.charAt(j - 1))
                    dp[i][j] = dp[i - 1][j - 1];
                else
                    dp[i][j] = min(dp[i - 1][j] + 1, dp[i][j - 1] + 1, dp[i - 1][j - 1] + 1);

        return dp[m][n];    // Levenshtein distiance
    }
    */

    private class Node {
        public int val;
        public int choice;  // 0 - skip; 1 - substition; 2 - deletion; 3 - insertion

        public Node() {
            this.val = 0;
            this.choice = 0;
        }

        public Node(int val, int choice) {
            this.val = val;
            this.choice = choice;
        }
    }

    // dp approach 3
    // record the operations while building up the dp table
    public int calculateDP3() {

        // base case
        for (int i = 1; i <= this.m; i++)
            dp[i][0].val = i;
        for (int j = 1; j <= this.n; j++)
            dp[0][j].val = j;

        // bottom up
        for (int i = 1; i <= this.m; i++)
            for (int j = 1; j <= this.n; j++)
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j].val = dp[i - 1][j - 1].val;
                    dp[i][j].choice = 0;    // skip
                }
                else {
                    int choiceTemp, valueTemp, a, b, c;
                    a = dp[i - 1][j - 1].val;
                    b = dp[i - 1][j].val;
                    c = dp[i][j - 1].val;

                    if (a <= b) {
                        valueTemp = a;
                        choiceTemp = 1;     // substitution
                    }
                    else {
                        valueTemp = b;
                        choiceTemp = 2;     // deletion
                    }

                    if (valueTemp > c) {
                        valueTemp = c;
                        choiceTemp = 3;     // insertion
                    }

                    dp[i][j].val = valueTemp + 1;
                    dp[i][j].choice = choiceTemp;
                }

        return dp[this.m][this.n].val;    // Levenshtein distiance
    }

    /**
     * Unit tests the {@code FileManager} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        String str1 = args[0];
        String str2 = args[1];

        LevenshteinDistance ld = new LevenshteinDistance(str1, str2);

        /*
        StdOut.printf("The Levenshtein Distance between %s and %s: %d \n", str1, str2,
                      ld.calculate(str1, str2));

        StdOut.printf("The Levenshtein Distance by DP between %s and %s: %d \n", str1, str2,
                      ld.calculateDP2(str1, str2));
         */

        StdOut.printf("The Levenshtein Distaince between %s and %s: %d \n", str1, str2,
                      ld.calculateDP3());
        StdOut.println();
    }
}
