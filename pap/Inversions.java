/**
 * created:    2019/10/26
 * <p>
 * {@code Inversions} is to solve the problem of counting the number of inversions in an array.
 *
 * <p>
 * Suppose that a music site wants to compare your song preferences to those of a friend.
 * One approach is to have you and your friend each rank a set of n songs and count the
 * number of pairs of songs (i, j) for which you prefer i to j but your friend prefers j to i.
 * When the count is low, the preferences are similar.
 *
 * <p>
 * More generally, given an array of integers, a pair of elements a[i] and a[j] are inverted
 * if i < j and a[i] > a[j].
 *
 * <p>
 * Remarks:
 * Counting inversions arise in a number of applications, including sorting, voting theory,
 * collaborative filtering, rank aggregation, and non-parametric statistics.
 *
 * @author Xiaoyu Zhang
 */


public class Inversions {

    // Return the number of inversions in the permutation a[].
    public static long count(int[] a) {
        long count = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j])
                    count++;
            }
        }

        return count;
    }

    // Return a permutation of length n with exactly k inversions.
    public static int[] generate(int n, long k) {
        if (n == 0 && k == 0) return new int[0];  // take care of corner case

        int[] arr = new int[n];

        // two pointers, one starting from the front (moving forward) and one starting from the end (moving backward)
        int i = 0, j = n - 1;

        while (n > 0) {
            if (k >= (n - 1)) {
                // put element (n-1) first in the permutation, so that it is inverted with (n-1) elements
                arr[i] = n - 1;

                // number of permutation decrease by (n-1)
                k -= (n - 1);

                // move the front pointer one step forward
                i++;

            } else {
                // put element (n-1) last in the permutation
                arr[j] = n - 1;

                // move the end pointer backward
                j--;
            }

            // one element already in place, and taking care of the rest
            n--;
        }

        return arr;
    }

    // Takes an integer n and a long k as command-line arguments,
    // and prints a permutation of length n with exactly k inversions.
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int k = Integer.parseInt(args[1]);

        for (int val : generate(n, k))
            StdOut.print(val + " ");
        StdOut.println();
    }
}
