/**
 * created:    2019/10/26
 * <p>
 * {@code MaximumSquareSubmatrix} is to solve Maximum square submatrix problem.
 *
 * <p>
 * Given an n-by-n matrix of 0s and 1s, find a contiguous square submatrix of maximum size that contains only 1s.
 *
 * <p>
 * Execution:   java MaximumSquareSubmatrix < square7.txt
 *
 * <p>
 * The maximum square submatrix problem is related to problems that arise in databases, image processing,
 * and maximum likelihood estimation.
 *
 * @author Xiaoyu Zhang
 */

public class MaximumSquareSubmatrix {

    // Returns the size of the largest contiguous square submatrix of a[][] containing only 1s.
    public static int size(int[][] a) {

        int n = a.length;
        int size = 0;

        int[][] dp = new int[n][n];     // dp array

        // Dynamic programming
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {

                dp[i][j] = a[i][j];                 // initialize dp array

                size = Math.max(size, dp[i][j]);     // initialize size

                // StdOut.printf("TRACE: 1st:   [ %d, %d ] = { %d }; \t size = { %d } \n", i, j, a[i][j], size);

                if (i >= 1 && j >= 1) {

                    if (a[i-1][j-1] >= 1 && a[i-1][j] >= 1 && a[i][j-1] >= 1) {

                        if (a[i][j] == 1) {

                            // update a[i][j] dynamically
                            // reuse the array of a[i], without extra space
                            dp[i][j] = Math.min(Math.min(dp[i-1][j-1], dp[i-1][j]), dp[i][j-1]) + 1;

                            size = Math.max(size, dp[i][j]);

                            // StdOut.printf("TRACE: 2nd:   [ %d, %d ] = { %d }; \t size = { %d } \n", i, j, a[i][j], size);
                        }
                    }
                }
            }
        }

        return size;
    }

    // Reads an n-by-n matrix of 0s and 1s from standard input and prints the size of
    // the largest contiguous square submatrix containing only 1s.
    public static void main(String[] args) {
        int n = StdIn.readInt();
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                a[i][j] = StdIn.readInt();

                // StdOut.print(a[i][j] + " ");
            }
        }

        StdOut.println(size(a));

    }
}