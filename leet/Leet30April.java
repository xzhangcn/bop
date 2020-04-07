import java.util.*;

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
     * Problem on the day of 04/04/2020: Move Zeros.
     * <p>
     * Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.
     * <p>
     * Note:
     * <p>
     * You must do this in-place without making a copy of the array.
     * Minimize the total number of operations.
     *
     * @param nums an integer array
     */
    public void moveZeroes(int[] nums) {
        int k = -1;     // index position for non-zero to be inserted

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0 && k != -1) {
                if (i > k) {
                    nums[k] = nums[i];
                    k++;
                    nums[i] = 0;
                }
            } else if (nums[i] == 0) {
                if (k == -1)
                    k = i;
                // else k++;
            }
        }
    }

    // Another approach to the above proglem
    // (Space Optimal, Operation Sub-Optimal)
    public void moveZeroes2(int[] nums) {
        int lastNonZeroFoundAt = 0;
        // If the current element is not 0, then we need to
        // append it just after the last found non zero element.
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[lastNonZeroFoundAt] = nums[i];
                lastNonZeroFoundAt++;
            }
        }
        // After we have finished processing new elements,
        // all the non-zero elements are already at beginning of array.
        // We just need to fill remaining array with 0's.
        for (int i = lastNonZeroFoundAt; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    // Improvement on the solution above
    void moveZeroes3(int[] nums) {
        // The code will maintain the following invariant:
        //    1. All elements before the slow pointer (lastNonZeroFoundAt) are non-zeroes.
        //    2. All elements between the current and slow pointer are zeroes.
        // Therefore, when we encounter a non-zero element, we need to swap elements pointed by current and slow pointer,
        // then advance both pointers. If it's zero element, we just advance current pointer.
        //With this invariant in-place, it's easy to see that the algorithm will work.

        for (int lastNonZeroFoundAt = 0, cur = 0; cur < nums.length; cur++) {
            if (nums[cur] != 0) {
                swap(nums, lastNonZeroFoundAt, cur);
                lastNonZeroFoundAt++;
            }
        }
    }

    /**
     * Problem on the day of 04/05/2020: Best Time to Buy and Sell Stock II.
     * <p>
     * Say you have an array for which the ith element is the price of a given stock on day i.
     * <p>
     * Design an algorithm to find the maximum profit. You may complete as many transactions as you like
     * (i.e., buy one and sell one share of the stock multiple times).
     * <p>
     * Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
     *
     * @param prices an array of stock prices
     * @return max profit
     */
    public int maxProfit(int[] prices) {
        if (prices.length == 0)
            return 0;

        int profit = 0;

        int j = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] <= prices[i - 1]) {
                profit += (prices[i - 1] - prices[j]);
                j = i;
                // System.out.printf("TRACE: i = %d, j = %d, profit = %d\n", i, j, profit);
            }
        }

        profit += (prices[prices.length - 1] - prices[j]);

        return profit;
    }

    // Improvement on the solution above.
    public int maxProfit2(int[] prices) {
        int maxprofit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1])
                maxprofit += prices[i] - prices[i - 1];
        }
        return maxprofit;
    }

    /**
     * Problem on the day of 04/06/2020: Group Anagrams.
     * <p>
     * Given an array of strings, group anagrams together.
     * <p>
     * Note:
     * All inputs will be in lowercase.
     * The order of your output does not matter.
     *
     * @param strs an array of strings
     * @return groups of anagrams
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);
            if (map.containsKey(sorted)) {
                List<String> list = map.get(sorted);
                list.add(s);
                map.put(sorted, list);
            } else {
                List<String> list = new ArrayList<>();
                list.add(s);
                map.put(sorted, list);
            }
        }

        return new ArrayList<>(map.values());
    }

    // A more compact way of coding on the solution above
    public List<List<String>> groupAnagrams2(String[] strs) {
        if (strs.length == 0) return new ArrayList<>();

        Map<String, List<String>> ans = new HashMap<>();
        for (String s : strs) {
            char[] ca = s.toCharArray();
            Arrays.sort(ca);
            String key = String.valueOf(ca);
            if (!ans.containsKey(key))
                ans.put(key, new ArrayList<>());

            ans.get(key).add(s);
        }

        return new ArrayList<>(ans.values());
    }

    /**
     * Problem on 04/07/2020: Counting Elements.
     * <p>
     * Given an integer array arr, count element x such that x + 1 is also in arr.
     *
     * If there're duplicates in arr, count them seperately.
     *
     * @param arr an array of integers
     * @return the count
     */
    public int countElements(int[] arr) {

        // Use hashset to store all elements
        Set<Integer> set = new HashSet<>();

        for (int elem : arr) {
            if (!set.contains(elem))
                set.add(elem);
        }

        int count = 0;

        // Loop again to count all valid elements.
        for (int elem :arr) {
            if (set.contains(elem + 1))
                count++;
        }

        return count;
    }

    /**
     * Swap the elements in an array
     *
     * @param nums   an integer array
     * @param first  index at which array element will be swapped
     * @param second another index at which array element will be swapped with
     */
    private void swap(int[] nums, int first, int second) {
        int temp = nums[first];
        nums[first] = nums[second];
        nums[second] = temp;
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

        System.out.println("\n>>> Problem on the day of 04/04/2020: Move Zeros.");
        int[] p4_nums = {0, 1, 0, 3, 12};
        leet30April.moveZeroes2(p4_nums);
        for (int num : p4_nums)
            System.out.printf("%d\t", num);
        System.out.println();

        System.out.println("\n>>> Problem on the day of 04/05/2020: Best Time to Buy and Sell Stock II.");
        int[] p5_nums = {7, 1, 5, 3, 6, 4};
        System.out.printf("Max profit is %d\n", leet30April.maxProfit2(p5_nums));

        System.out.println("\n>>> Problem on the day of 04/06/2020: Group Anagrams.");
        String[] p6_strs = {"eat", "tea", "tan", "ate", "nat", "bat"};

        for (List<String> list : leet30April.groupAnagrams2(p6_strs)) {
            for (String s : list)
                System.out.printf("%s\t", s);

            System.out.println();
        }

        System.out.println("\n>>> Problem on 04/07/2020: Counting Elements.");
        int[] p7_nums = {1,3,2,3,5,0};
        System.out.printf("The count of qualifying elements is %d\n", leet30April.countElements(p7_nums));
    }

}
