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
     *
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
        while (set.add(n)){
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
    }

}
