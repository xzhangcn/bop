/**
 * created:    2020/03/30
 * <p>
 * This is the Leetcode's official curated list of top classic interview questions for the topic of Design.
 *
 * Shuffle a set of numbers without duplicates.
 *
 * @author Xiaoyu Zhang
 */

public class LeetDesignShuffle {
    private int[] a;

    public LeetDesignShuffle(int[] nums) {
        a = new int[nums.length];
        for (int i = 0; i < nums.length; i++)
            a[i] = nums[i];
    }

    /**
     * Resets the array to its original configuration and return it
     *
     * @return the original configuration of array
     */
    public int[] reset() {
        return a;
    }

    /**
     *
     * @return a random shuffling of the array
     */
    public int[] shuffle() {
        int[] b = new int[a.length];
        for (int i = 0; i < b.length; i++)
            b[i] = a[i];

        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + (int) (Math.random() * (n-i));   // between i and n-1. This uses Knuth's shuffling algorithm.
            exch(b, i, r);
        }

        return b;
    }

    // swaps array elements i and j
    private void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    // take as input an array of integers and print them out to standard output
    public void show(int[] a) {
        for (int i = 0; i < a.length; i++) {
            StdOut.println(a[i]);
        }
    }

    /**
     * Unit tests
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        int[] nums = {1, 2, 3};
        LeetDesignShuffle leetDesignShuffle = new LeetDesignShuffle(nums);
        int[] a = leetDesignShuffle.reset();
        int[] b = leetDesignShuffle.shuffle();

        leetDesignShuffle.show(a);
        leetDesignShuffle.show(b);
    }
}
