/**
 * created:    2020/03/29
 * <p>
 * This is the Leetcode's official curated list of top classic interview questions for the topic of Dynamic Programming.
 *
 * @author Xiaoyu Zhang
 */

public class LeetDP {
    /**
     * Problem 1: Climbing Stairs.
     * <p>
     * You are climbing a stair case. It takes n steps to reach to the top.
     * <p>
     * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
     * <p>
     * Note: Given n will be a positive integer.
     *
     * @param n number of steps to reach to the top
     * @return distinct way to the top
     */
    public int climbStairs(int n) {
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;

        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    // Another way to solve the problem above using Dynamic programming
    public int climbStairs2(int n) {
        if (n == 1)
            return 1;

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++)
            dp[i] = dp[i - 1] + dp[i - 2];

        return dp[n];
    }

    // Another way to solve the problem above using Fibonacci number
    public int climbStairs3(int n) {
        if (n == 1)
            return 1;

        int first = 1, second = 2;
        for (int i = 3; i <= n; i++) {
            int third = first + second;
            first = second;
            second = third;
        }

        return second;
    }

    /**
     * Problem 2: Best Time to Buy and Sell Stock.
     * <p>
     * Say you have an array for which the ith element is the price of a given stock on day i.
     * <p>
     * If you were only permitted to complete at most one transaction (i.e., buy one and sell one share of the stock),
     * design an algorithm to find the maximum profit.
     * <p>
     * Note that you cannot sell a stock before you buy one.
     *
     * @param prices an array of stock prices
     * @return max profit
     */
    public int maxProfit(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int profit = 0;

        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minPrice)
                minPrice = prices[i];
            else if (prices[i] - minPrice > profit)
                profit = prices[i] - minPrice;
        }

        return profit;
    }

    /**
     * Problem 3: Maximum Sub-array.
     * <p>
     * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
     * <p>
     * Follow up:
     * <p>
     * If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
     *
     * @param nums an array of stock prices
     * @return largest sum of the contiguous sub-array
     */
    public int maxSubArray(int[] nums) {
        if (nums == null || nums.length == 0)
            return 0;

        int n = nums.length;
        int[] dp = new int[n];      // dp[i] means the maximum sub-array ending with A[i]

        dp[0] = nums[0];
        int result = dp[0];

        for (int i = 1; i < n; ++i) {
            dp[i] = Math.max(dp[i - 1], 0) + nums[i];
            result = Math.max(result, dp[i]);
        }

        return result;
    }

    /**
     * An improvement way to the above solution.
     *
     * This problem was discussed by Jon Bentley (Sep. 1984 Vol. 27 No. 9 Communications of the ACM P885)
     *
     * The paragraph below was copied from his paper (with a little modifications)
     *
     * Algorithm that operates on arrays:
     * It starts at the left end (element A[1]) and scans through to the right end (element A[n]),
     * keeping track of the maximum sum subvector seen so far. The maximum is initially A[0].
     * Suppose we've solved the problem for A[1 .. i - 1]; how can we extend that to A[1 .. i]?
     * The maximum sum in the first i elements is either the maximum sum in the first i - 1 elements
     * (which we'll call MaxSoFar), or it is that of a subvector that ends in position i (which we'll call MaxEndingHere).
     *
     * MaxEndingHere is either A[i] plus the previous MaxEndingHere, or just A[i], whichever is larger.
     *
     * @param nums an array of stock prices
     * @return largest sum of the contiguous sub-array
     */
    public int maxSubArray2(int[] nums) {
        int maxSoFar = nums[0], maxEndingHere = nums[0];

        for (int i = 1; i < nums.length; ++i) {
            maxEndingHere = Math.max(maxEndingHere + nums[i], nums[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }

        return maxSoFar;
    }

    /**
     * Problem 4: Hourse Robber.
     * <p>
     * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed,
     * the only constraint stopping you from robbing each of them is that adjacent houses have security system connected
     * and it will automatically contact the police if two adjacent houses were broken into on the same night.
     *
     * Given a list of non-negative integers representing the amount of money of each house,
     * determine the maximum amount of money you can rob tonight without alerting the police.
     *
     * @param nums the amount of money of each house,
     * @return the maximum amount of money
     */
    public int rob(int[] nums) {

    }


    /**
     * Unit tests
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        LeetDP leetDP = new LeetDP();

        System.out.println("\n>>> Problem 1: Climbing Stairs.");
        int dp1_n = 5;
        System.out.printf("%d ways to climb %d steps of stairs.\n", leetDP.climbStairs2(dp1_n), dp1_n);

        System.out.println("\n>>> Problem 2: Best Time to Buy and Sell Stock.");
        int[] dp2_prices = {7, 1, 5, 3, 6, 4};
        System.out.printf("The max profit is %d\n", leetDP.maxProfit(dp2_prices));

        System.out.println("\n>>> Problem 3: Maximum Sub-array.");
        int[] dp3_nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.printf("The largest sum of the contiguous sub-array is %d\n", leetDP.maxSubArray(dp3_nums));
    }
}
