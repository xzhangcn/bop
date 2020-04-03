import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/**
 * created:    2019/04/02
 * <p>
 * {@code Leet30April} is the 30-day LeetCoding challenge.
 * <p>
 * A new problem will appear each day, and you will have one day to solve it.
 * <p>
 * The problems below are for the April 2020.
 *
 * @author Xiaoyu Zhang
 */

public class Leet30April {
    /**
     * Problem on the day of 04/01/2020: Single Number.
     * <p>
     * Given a non-empty array of integers, every element appears twice except for one. Find that single one.
     * <p>
     * Note:
     * <p>
     * Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
     *
     * @param nums an array
     * @return the single number
     */
    public int singleNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            if (set.contains(num))
                set.remove(num);
            else
                set.add(num);
        }

        Iterator<Integer> iterator = set.iterator();

        return iterator.next();
    }

    /**
     * Problem on the day of 04/02/2020: Happy Number.
     * <p>
     * Write an algorithm to determine if a number is "happy".
     * <p>
     * A happy number is a number defined by the following process: Starting with any positive integer,
     * replace the number by the sum of the squares of its digits, and repeat the process until the number
     * equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1. Those
     * numbers for which this process ends in 1 are happy numbers.
     *
     * @param n an positive integer
     * @return true if it's a happy number
     */
    public boolean isHappy(int n) {

        // The idea is to use one hash set to record sum of every digit square of every number occurred.
        // Once the current sum cannot be added to set, return false; once the current sum equals 1, return true;

        Set<Integer> set = new HashSet<>();     // store the sums for each iteration

        int squaredSum, remainder = 0;
        do {
            squaredSum = 0;

            while (n != 0) {
                remainder = n % 10;
                squaredSum += remainder * remainder;
                n = n / 10;

                System.out.printf("TRACE: n = %d, remainder = %d, squaredSum = %d\n", n, remainder, squaredSum);
            }

            if (squaredSum == 1)
                return true;

            n = squaredSum;

        } while (set.add(squaredSum));

        return false;
    }

    // Slight different approach to the problem above
    public boolean isHappy2(int n) {

        // The idea is to use one hash set to record sum of every digit square of every number occurred.
        // Once the current sum cannot be added to set, return false; once the current sum equals 1, return true;

        Set<Integer> set = new HashSet<>();     // store the sums for each iteration

        int squaredSum, remainder;
        while (set.add(n)) {
            squaredSum = 0;

            while (n > 0) {
                remainder = n % 10;
                squaredSum += remainder * remainder;
                n = n / 10;
            }

            if (squaredSum == 1)
                return true;
            else
                n = squaredSum;

        }

        return false;
    }

    /**
     * Problem on the day of 04/03/2020: Maximum Subarray.
     * <p>
     * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
     *
     * <p>
     * This problem was discussed by Jon Bentley (Sep. 1984 Vol. 27 No. 9 Communications of the ACM P885)
     * <p>
     * The paragraph below was copied from his paper (with a little modifications)
     * <p>
     * Algorithm that operates on arrays: it starts at the left end (element A[1]) and scans through to the right end (element A[n]),
     * keeping track of the maximum sum subvector seen so far.
     * <p>
     * The maximum is initially A[0]. Suppose we've solved the problem for A[1 .. i - 1]; how can we extend that to A[1 .. i]?
     * The maximum sum in the first I elements is either the maximum sum in the first i - 1 elements (which we'll call MaxSoFar),
     * or it is that of a subvector that ends in position i (which we'll call MaxEndingHere).
     * <p>
     * MaxEndingHere is either A[i] plus the previous MaxEndingHere, or just A[i], whichever is larger.
     *
     * <p>
     * Follow up:
     * If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
     *
     * @param nums an integer array
     * @return the largest sum of a contiguous subarray
     */
    public int maxSubArray(int[] nums) {
        int maxSoFar = nums[0], maxEndingHere = nums[0];

        for (int i = 1; i < nums.length; ++i) {
            maxEndingHere = Math.max(maxEndingHere + nums[i], nums[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }

        return maxSoFar;
    }

    /**
     * Unit tests
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Leet30April leet30April = new Leet30April();

        System.out.println("\n>>> Problem on the day of 04/01/2020: Single Number.");
        int[] p1_nums = {4, 1, 2, 1, 2};
        System.out.printf("The single number in the array is %d\n", leet30April.singleNumber(p1_nums));

        System.out.println("\n>>> Problem on the day of 04/02/2020: Happy Number.");
        int p2_n = 19;
        System.out.printf("Is %d a happy number? %b\n", p2_n, leet30April.isHappy2(p2_n));

        System.out.println("\n>>> Problem on the day of 04/03/2020: Maximum Subarray.");
        int[] p3_nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.printf("The largest sum of contiguous sub-array is %d\n", leet30April.maxSubArray(p3_nums));
    }

}
