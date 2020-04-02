/**
 * created:    2019/09/22
 * <p>
 * {@code TrinomialDP} takes two integer command-line arguments n and k and computes the trinomial coefficient T(n, k) using dynamic programming.
 *
 * @author Xiaoyu Zhang
 */

public class TrinomialDP {

    // Retures the trinomial coefficient T(n, k)
    public static long trinomial(int n, int k)
    {
        if (n == 0 && k == 0)   return 1;
        if (Math.abs(k) > n)    return 0;

        // Trinomial coefficient array， and the array size needs to be 'n+2'
        long[][] coeff = new long[n+2][n+2];

        int i, j = 0;
        for (i = 0; i <= n; i++) {
            for (j = 0; j <= n; j++) {
                if (i == 0 && j == 0)   coeff[i][j] = 1;
                else if (j > i)         coeff[i][j] = 0;
                else {
                    // StdOut.printf("i = (%d), j = (%d) \n", i, j);

                    // at this point, i already > 0,
                    // so there is no need to worry about the array index out of bounds exception for (i-1).
                    // also, as the array size is (n+2), there is no need to worry about the index bound for (j+1)
                    coeff[i][j] = coeff[i-1][j] + coeff[i-1][j+1];

                    if (j - 1 < 0)      coeff[i][j] += coeff[i-1][1-j];     // because trinomial(n, k) = trinomial(n, -k)
                    else                coeff[i][j] += coeff[i-1][j-1];
                }

                if (i == n && j == Math.abs(k))   break;
            }
        }

        // StdOut.printf("i = (%d), j = (%d) \n", i, j);
        // StdOut.printf("coefficient = (%d) \n", coeff[i-1][j]);

        return coeff[i-1][j];
    }

    // Takes two integer command-line arguments n and k and prints T(n, k)
    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);

        StdOut.println(trinomial(n, k));
    }
}
