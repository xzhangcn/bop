/**
 * created:    2020/03/18
 * <p>
 * This is the Leetcode's official curated list of top classic interview questions for the topic of Array.
 *
 * @author Xiaoyu Zhang
 */

public class LeetArray {

    /**
     * Problem 1: remove duplicates from a sorted array.
     * <p>
     * Given a sorted array nums, remove the duplicates in-place such that each element appear only once and return the new length.
     * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
     * It doesn't matter what you leave beyond the returned length.
     *
     * @param nums a sorted integer array
     * @return the new array length after removing the duplicates
     */
    public int removeDuplicates(int[] nums) {
        if (nums.length == 0)
            return 0;

        int j = 1;  // the index of inserting the next element

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                nums[j] = nums[i];
                j++;
            }
        }

        return j;
    }

    /**
     * Approach 2: two pointers approach.
     * One pointer keep track of the current element in the original array, and another one is for just the unique elements.
     * Once an element is encountered, you simply need to bypass its duplicates and move on to the next unique element.
     *
     * @param nums a sorted integer array
     * @return the new array length after removing the duplicates
     */
    public int removeDuplicates2(int[] nums) {
        if (nums.length == 0)
            return 0;

        // keep two pointers i and j, where j is the slow-runner while i is the fast-runner.
        // As long as nums[j] = nums[i], we increment i to skip the duplicate.
        int j = 0;

        for (int i = 1; i < nums.length; i++) {
            // When we encounter nums[i] != nums[j], the duplicate run has ended so we must copy its value to nums[j + 1].
            // j is incremented and we repeat the same process again until i reaches the end of array.
            if (nums[i] != nums[j]) {
                j++;
                nums[j] = nums[i];
            }
        }

        return j + 1;
    }

    /**
     * Problem 2: Best Time to Buy and Sell Stock.
     * <p>
     * Say you have an array for which the ith element is the price of a given stock on day i.
     * <p>
     * Design an algorithm to find the maximum profit. You may complete as many transactions as you like
     * (i.e., buy one and sell one share of the stock multiple times).
     * <p>
     * Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
     *
     * @param prices an array of stock prices
     * @return the max profit
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
                // System.out.printf("i = %d, j = %d, profit = %d\n", i, j, profit);
            }
        }

        profit += (prices[prices.length - 1] - prices[j]);

        return profit;
    }

    /**
     * Approach 2:
     * Go on crawling over the slope and keep on adding the profit obtained from every consecutive transaction.
     *
     * @param prices an array of stock prices
     * @return the max profit
     */
    public int maxProfit2(int[] prices) {
        int profit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1])
                profit += (prices[i] - prices[i-1]);
        }

        return profit;
    }

    /**
     * Problem 3: rotate an array
     * <p>
     * Given an array, rotate the array to the right by k steps, where k is non-negative.
     *
     * @param nums the array
     * @param k steps to be rotated right
     */
    public void rotate(int[] nums, int k) {

    }

    /**
     * Print the array for a given length
     *
     * @param nums an integer array
     * @param len  the number of elements to be printed out
     */
    public void printArray(int[] nums, int len) {
        int i;
        System.out.print("Array: [");
        for (i = 0; i < len - 1; i++)
            System.out.printf("%d, ", nums[i]);
        System.out.printf("%d]\n", nums[i]);
    }

    /**
     * Unit tests
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        LeetArray leetArray = new LeetArray();

        System.out.println("\n>>> Problem 1: remove duplicates from a sorted array.");
        int[] nums_01 = {0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        // int[] nums_01 = {1, 1, 2};
        int len = leetArray.removeDuplicates2(nums_01);
        System.out.printf("The length of new array is %d\n", len);
        leetArray.printArray(nums_01, len);

        System.out.println("\n>>> Problem 2: Best Time to Buy and Sell Stock.");
        int[] nums_02 = {7, 1, 5, 3, 6, 4};
        // int[] nums_02 = {1, 2, 3, 4, 5};
        // int[] nums_02 = {7, 6, 4, 3, 1};
        // int[] nums_02 = {6, 1, 3, 2, 4, 7};
        int profit = leetArray.maxProfit2(nums_02);
        System.out.printf("The max profit is %d\n", profit);
    }
}
