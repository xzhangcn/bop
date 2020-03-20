import java.util.*;

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
     * <p>
     * Approach 1: brute force
     * The simplest approach is to rotate all the elements of the array in k steps by rotating the elements by 1 unit in each step.
     *
     * <p>
     * Time complexity : O(n*k). All the numbers are shifted by one step(O(n)) k times(O(k)).
     * Space complexity : O(1). No extra space is used.
     *
     * @param nums the array
     * @param k steps to be rotated right
     */
    public void rotate(int[] nums, int k) {
        int len = nums.length;

        int temp;
        k = k % len;
        for (int i = 0; i < k; i++) {
            temp = nums[len - 1];   // save the last element first

            // start from the back of the array
            for (int j = len - 1; j > 0; j--)
                nums[j] = nums[j - 1];

            nums[0] = temp;         // copy the saved element to the front of the array
        }
    }

    /**
     * Approach 2: use an extra array
     * Use an extra array in which we place every element of the array at its correct position.
     * The number at index i in the original array is placed at the index (i+k)%(length of array).
     * Then, we copy the new array to the original one.
     *
     * <p>
     * Time complexity : O(n). One pass is used to put the numbers in the new array. And another pass to copy the new array to the original one.
     * Space complexity : O(1). Another array of the same size is used.
     *
     * @param nums the array
     * @param k steps to be rotated right
     */
    public void rotate2(int[] nums, int k) {
        int len = nums.length;
        int[] a = new int[len];

        for (int i = 0; i < len; i++) {
            a[(i + k) % len] = nums[i];
        }

        for (int i = 0; i < len; i++)
            nums[i] = a[i];
    }

    /**
     * Approach 3: use cyclic replacements
     *
     * <p>
     * Time complexity : O(n). Only one pass is used.
     * Space complexity : O(1). Constant extra space is used.
     *
     * @param nums the array
     * @param k steps to be rotated right
     */
    public void rotate3(int[] nums, int k) {
        int len = nums.length;
        k = k % len;
        int count = 0;

        for (int start = 0; count < len; start++) {
            int curr = start;
            int prev = nums[start];

            do {
                int next = (curr + k) % len;
                int temp = nums[next];
                nums[next] = prev;
                prev = temp;
                curr = next;
                count++;

                System.out.printf("start = %d, count = %d, curr = %d, next = %d, prev = %d, temp = %d\n", start, count, curr, next, prev, temp);
            } while (start != curr);
        }
    }

    /**
     * Approach 4: use reverse
     * <p>
     * This approach is based on the fact that when we rotate the array k times,
     * k elements from the back end of the array come to the front and the rest of the elements from the front shift backwards.
     * <p>
     * In this approach, we firstly reverse all the elements of the array.
     * Then, reversing the first k elements followed by reversing the rest n-k elements gives us the required result.
     * <p>
     * Time complexity : O(n). n elements are reversed a total of three times.
     * Space complexity : O(1). No extra space is used.
     *
     * @param nums the array
     * @param k steps to be rotated right
     */
    public void rotate4(int[] nums, int k) {
        int len = nums.length;
        k = k % len;
        reverse(nums, 0, len - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, len - 1);
    }

    /**
     *
     * Reverse an array from start index to end index
     *
     * @param nums an integer array
     * @param start start array index
     * @param end end array index
     */
    public void reverse(int[] nums, int start, int end) {
        int len = nums.length;

        int temp;
        while (start < end) {
            temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    /**
     * Problem 4: contains duplicate
     * <p>
     * Given an array of integers, find if the array contains any duplicates.
     * Your function should return true if any value appears at least twice in the array,
     * and it should return false if every element is distinct.
     *
     * <p>
     * Time complexity : O(n). We do search() and insert() for n times and each operation takes constant time.
     * Space complexity : O(n). The space used by a hash table is linear with the number of elements in it.
     *
     * @param nums the array
     */
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            if (set.contains(nums[i]))
                return true;
            else
                set.add(nums[i]);
        }

        return false;
    }

    /**
     * Problem 4.2: contains duplicate II
     * <p>
     * Given an array of integers and an integer k, find out whether there are two distinct indices i and j in the array
     * such that nums[i] = nums[j] and the absolute difference between i and j is at most k.
     *
     * @param nums the array
     * @param k the distance between indices
     */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i]) && (i - map.get(nums[i])) <= k)
                return true;
            else
                map.put(nums[i], i);
        }

        return false;
    }

    /**
     * Problem 4.3: contains duplicate III
     * <p>
     * Given an array of integers, find out whether there are two distinct indices i and j in the array
     * such that the absolute difference between nums[i] and nums[j] is at most t and the absolute difference between i and j is at most k.
     *
     * @param nums the array
     * @param k the distance between indices at most
     * @param t the difference between two element at most
     * @return true if contains nearby almost duplicate, false otherwise
     */
    public boolean containsNearbyAlmostDuplicate(int[] nums, int k, int t) {
        int len = nums.length;

        /*
        System.out.printf("Max int = %d\n", Integer.MAX_VALUE);
        System.out.printf("Max long = %d\n", Long.MAX_VALUE);
        System.out.printf("Min long = %d\n", Long.MIN_VALUE);
        */
        for (int i = 0; i <= len - 2; i++) {

            // sliding window with width of k
            for (int j = i + 1; j <= (i + k) && j < len; j++) {
                System.out.printf("i = %d, j = %d, t = %d, valI = %d, valJ = %d, value = %d \n",
                        i, j, t, nums[i], nums[j], (long) ((long) nums[i] - (long) nums[j]));

                if (Math.abs((long) nums[i] - (long) nums[j]) <= t)
                    return true;
            }
        }

        return false;
    }

    // The version below is slightly different from the one above.
    // Just use while loop to replace for loop.
    public boolean containsNearbyAlmostDuplicate2(int[] nums, int k, int t) {

        for(int i = 0; i <= nums.length - 2; i++) {

            int j = i + 1;
            while(j - i <= k && j < nums.length) {
                if(Math.abs((long)nums[i] - (long)nums[j]) <= t)
                    return true;

                j++;
            }
        }

        return false;
    }

    /**
     * Approach 3: sliding window + tree set
     *
     * Maintain the tree set in such a way that it always contains k elements and slide them accordingly
     * (remove 1st element when we reach size >= k, etc..)
     *
     * We can use a TreeSet which offers functinos like floor and ceiling.
     * floor() returns the greatest element in this set less than or equal to the given element, or null if there is no such element.
     * ceiling() returns the least element in this set greater than or equal to the given element, or null if there is no such element.
     *
     * The fact that the absolute diff should always be utmost t means that
     * we need to check the greatest low and lowest high for all the elements.
     * If that satisfies the given condition, return true. eg: if t = 2 and nums[i] = 4,
     * for the condition to be true the greatest element smaller than 4 should be in between 2 and 4,
     * while smallest element greater than 4 should lie between 4 and 6.
     *
     * TC: O(nlogk) - as the size of tree set is always k and n is the size of the array
     */
    private static boolean containsNearbyAlmostDuplicate3(int[] nums, int k, int t) {
        if (nums == null || nums.length <= 1)
            return false;

        // The TreeSet represents a window of k numbers.
        // When the index goes beyond k, we keep on removing (index - k) th number form the TreeSet
        TreeSet<Integer> treeSet = new TreeSet<>();

        for (int i = 0; i < nums.length; i++) {
            Integer low = treeSet.floor(nums[i]);       // get the greatest element smaller than this number
            Integer high = treeSet.ceiling(nums[i]);    // get the smallest element greater than this number

            // check for absolute differences
            if ((low != null && (long) nums[i] - low <= t) || (high != null && (long) high - nums[i] <= t))
                return true;

            treeSet.add(nums[i]);

            // remove the first element when size of the tree set exceeds k
            if (i >= k)
                treeSet.remove(nums[i - k]);
        }

        return false;
    }

    /**
     * Problem 5: Single number
     * <p>
     * Given a non-empty array of integers, every element appears twice except for one. Find that single one.
     *
     * Note:
     * Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
     *
     * <p>
     * Time complexity : O(n). We only iterate through the array.
     * Space complexity : O(1). Only one element being added into the set.
     *
     * @param nums the array
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

        Iterator iterator = set.iterator();

        return (Integer) iterator.next();
    }

    // Approach 2 for the problem above
    // Bit manipulation.
    // If we take XOR of zero and some bit, it will return that bit;
    // If we take XOR of two same bits, it will return 0.
    // we can XOR all bits together to find the unique number.
    public int singleNumber2(int[] nums) {
        int a = 0;
        for (int num : nums) {
            a ^= num;

            System.out.printf("TRACE: a = %d\n", a);
        }
        return a;
    }

    /**
     * Problem 6: Intersection of two arrays II
     * Approach 1: Use HashMap
     *
     * <p>
     * Given two arrays, write a function to compute their intersection.
     *
     * Note:
     * Each element in the result should appear as many times as it shows in both arrays.
     * The result can be in any order.
     *
     * @param nums1 array one
     * @param nums2 array two
     * @return the intersection
     */
    public int[] intersect(int[] nums1, int[] nums2) {

        Map<Integer, Integer> map1 = new HashMap<>();
        Map<Integer, Integer> map2 = new HashMap<>();

        for (int num : nums1) {
            if (map1.containsKey(num))
                map1.put(num, map1.get(num) + 1);
            else
                map1.put(num, 1);
        }

        for (int num : nums2) {
            if (map2.containsKey(num))
                map2.put(num, map2.get(num) + 1);
            else
                map2.put(num, 1);
        }

        List<Integer> list = new ArrayList<>();     // store the result

        for (Map.Entry<Integer, Integer> entry : map1.entrySet()) {
            int key = entry.getKey();
            if (map2.containsKey(key)) {
                int value = Math.min(map1.get(key), map2.get(key));
                for (int i = 0; i < value; i++)
                    list.add(key);
            }
        }

        int[] a = new int[list.size()];
        for (int i = 0; i < list.size(); i++)
            a[i] = list.get(i);

        return a;
    }

    // Same approach as above, but more concise code
    public int[] intersect2(int[] nums1, int[] nums2) {

        ArrayList<Integer> retList = new ArrayList<>();
        Map<Integer, Integer> map1 = new HashMap<>();
        Map<Integer, Integer> map2 = new HashMap<>();

        for (int n: nums1)
            map1.put(n, map1.getOrDefault(n, 0) + 1);

        for (int n: nums2)
            map2.put(n, map2.getOrDefault(n, 0) + 1);

        for (int key: map1.keySet()){
            int val = Math.min(map1.getOrDefault(key, 0), map2.getOrDefault(key, 0));
            while (val > 0) {
                retList.add(key);
                val--;
            }
        }

        return retList.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Problem 7: Plus one
     *
     * <p>
     * Given a non-empty array of digits representing a non-negative integer, plus one to the integer.
     *
     * The digits are stored such that the most significant digit is at the head of the list,
     * and each element in the array contain a single digit.
     *
     * You may assume the integer does not contain any leading zero, except the number 0 itself.
     *
     * @param digits array of digits
     * @return the result array
     */
    public int[] plusOne(int[] digits) {
        List<Integer> list = new ArrayList<>();
        int carry = 1;
        int value = 0;
        for (int i = digits.length - 1; i >= 0; i--) {
            value = carry + digits[i];
            if (value >= 10) {
                list.add(value - 10);
                carry = 1;
            }
            else {
                list.add(value);
                carry = 0;
            }

            // System.out.printf("TRACE: i = %d, carry = %d, value = %d\n", i, carry, value);
        }

        if (carry == 1)
            list.add(carry);

        int[] a = new int[list.size()];
        int j = 0;
        for(int i = list.size() - 1; i >= 0; i--) {
            // System.out.println(list.get(i));
            a[j++] = list.get(i);

        }
        return a;
    }

    /**
     * Problem 8: Move zeros
     *
     * <p>
     * Given an array $nums, write a function to move all 0's to the end of it
     * while maintaining the relative order of the non-zero elements.
     *
     * Note:
     * You must do this in-place without making a copy of the array.
     * Minimize the total number of operations.
     *
     * @param nums an integer array
     */
    public void moveZeros(int[] nums) {
        int k = -1;     // index position for non-zero to be inserted, and initialized to be -1.

        // The algorithm below achieves the time, space and operation optimal, but it's a bit hard to understand.
        // To understand it, you have to walk through a few test cases to see it's correct.
        for (int i = 0; i < nums.length; i++) {

            if (nums[i] != 0 && k != -1) {
                if (i > k) {
                    nums[k] = nums[i];      // only in this case, we need to move the non-zero element forward
                    k++;                    // in this case, k simply increments
                    nums[i] = 0;
                }
            }
            else if (nums[i] == 0) {
                if (k == -1)                // only in this case, k jumps to the current position
                    k = i;
                // else k++;
            }

            if (k != -1)
                System.out.printf("TRACE: i = %d, k = %d, nums[i] = %d, nums[k] = %d\n", i, k, nums[i], nums[k]);
        }
    }

    // Another approach to the problem above.
    // Although both time and space are optimal. However, the total number of operations are not optimal (when all elements are non zeros).
    // But this approach is easier to follow.
    public void moveZeros2(int[] nums) {
        int lastNonZeroFoundAt = 0;

        // This algorithm below will maintain the following invariant:
        // 1. All elements before the slow pointer (lastNonZeroFoundAt) are non-zeroes.
        // 2. All elements between the current and slow pointer are zeroes.
        for (int cur = 0; cur < nums.length; cur++) {
            if (nums[cur] != 0) {
                swap(nums, lastNonZeroFoundAt, cur);
                lastNonZeroFoundAt++;
            }
        }
    }

    /**
     * Swap the elements in an array
     * @param nums an integer array
     * @param first index at which array element will be swapped
     * @param second another index at which array element will be swapped with
     */
    private void swap(int[] nums, int first, int second) {
        int temp = nums[first];
        nums[first] = nums[second];
        nums[second] = temp;
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

        System.out.println("\n>>> Problem 3: Rotate array.");
        int[] nums_03 = {1, 2, 3, 4, 5, 6};
        leetArray.rotate4(nums_03, 3);
        leetArray.printArray(nums_03, nums_03.length);

        System.out.println("\n>>> Problem 4: Contains duplicate.");
        int[] nums_04 = {1, 2, 3, 4, 5, 4, 6};
        System.out.printf("Does the array contain duplicate? %b\n", leetArray.containsDuplicate(nums_04));

        System.out.println("\n>>> Problem 4.2: Contains nearby duplicate.");
        int[] nums_04_02 = {1, 2, 3, 4, 5, 7, 4, 6};
        System.out.printf("Does the array contain duplicate whose distance is at most %d? %b\n", 2, leetArray.containsNearbyDuplicate(nums_04_02, 2));

        System.out.println("\n>>> Problem 4.3: Contains nearby almost duplicate.");
        // int[] nums_04_03 = {-1, -1};
        // int[] nums_04_03 = {1, 5, 9, 1, 5, 9};
        // int[] nums_04_03 = {1, 2, 3, 1};
        // int[] nums_04_03 = {-1, 2147483647};
        int[] nums_04_03 = {2147483647,-2147483647};
        System.out.printf("Does the array contain nearby almost duplicate? %b\n", leetArray.containsNearbyAlmostDuplicate3(nums_04_03, 1, 2147483647));

        System.out.println("\n>>> Problem 5: Single number.");
        // int[] nums_05 = {2, 2, 1};
        int[] nums_05 = {4, 1, 2, 1, 2};
        System.out.printf("The single number in the array is %d.\n", leetArray.singleNumber2(nums_05));

        System.out.println("\n>>> Problem 6: Intersection of two arrays II.");
        // int[] nums_06_01 = {1, 2, 2, 1};
        // int[] nums_06_02 = {2, 2};
        int[] nums_06_01 = {4, 9, 5};
        int[] nums_06_02 = {9, 4, 9, 8, 4};
        int[] result_06 = leetArray.intersect2(nums_06_01, nums_06_02);
        leetArray.printArray(result_06, result_06.length);

        System.out.println("\n>>> Problem 7: Plus one.");
        int[] nums_07 = {1, 2, 3};
        // int[] nums_07 = {9};
        int[] result_07 = leetArray.plusOne(nums_07);
        leetArray.printArray(result_07, result_07.length);

        System.out.println("\n>>> Problem 8: Move zeros.");
        int[] nums_08 = {0, 1, 0, 3, 12};
        // int[] nums_08 = {1, 2};
        // int[] nums_08 = {1, 0, 1};
        leetArray.moveZeros2(nums_08);
        leetArray.printArray(nums_08, nums_08.length);
    }
}
