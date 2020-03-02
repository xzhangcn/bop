/**
 * created:    2020/03/02
 * <p>
 * compilation:  javac RotatedArray.java
 * <p>
 * execution:    java RotatedArray
 * <p>
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 * <p>
 * (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
 * <p>
 * You are given a target value to search. If found in the array return its index, otherwise return
 * -1.
 * <p>
 * You may assume no duplicate exists in the array.
 * <p>
 * Your algorithm's runtime complexity must be in the order of O(log n).
 * <p>
 * Example 1: Input: nums = [4,5,6,7,0,1,2], target = 0; Output: 4
 * <p>
 * Example 2: Input: nums = [4,5,6,7,0,1,2], target = 3; Output: -1
 * <p>
 * It's just a simple demonstration for binary search algorithm.
 *
 * @author Xiaoyu Zhang
 */

public class RotatedArray {

    /**
     * Search the target in a rotated sorted array.
     * <p>
     * The idea is that when rotating the array, there must be one half of the array that is still
     * in sorted order. For example, 6 7 1 2 3 4 5, the order is disrupted from the point between 7
     * and 1. So when doing binary search, we can make a judgement that which part is ordered and
     * whether the target is in that range, if yes, continue the search in that half, if not
     * continue in the other half.
     * <p>
     * The main idea is that we need to find some parts of array that we could adopt binary search
     * on that, which means we need to find some completed sorted parts, then determine whether et
     * is in left part or right part. There is at least one segment (left part or right part)
     * onotonically increasing.
     * <p>
     * <ol>If the entire left part is monotonically increasing, which means the pivot point is on the right part
     * <li>If left <= target < mid ------> drop the right half </li>
     * <li>Else ------> drop the left half</li>
     *  </ol>
     * <ol>If the entire right part is monotonically increasing, which means the pivot point is on the left part
     * <li>If mid < target <= right ------> drop the left half</li>
     * <li>Else ------> drop the right</li>
     *  </ol>
     *
     * @param nums   rotated sorted array
     * @param target the integer number to search for
     * @return index if found in the array, otherwise return -1
     */
    public static int search(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return -1;

        int lo = 0, hi = nums.length - 1;
        // when we use the condition "left <= right", we do not need to determine if nums[left] == target
        // in outside of loop, because the jumping condition is left > right, we will have the determination
        // condition if(target == nums[mid]) inside of loop
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (target == nums[mid]) {
                return mid;
            }
            // if left part is monotonically increasing, or the pivot point is on the right part
            if (nums[lo] <= nums[mid]) {
                // 2,3,4,5,6,7,0,1
                // must use "<=" at here since we need to make sure target is in the left part,
                // then safely drop the right part
                if (nums[lo] <= target && target < nums[mid]) {
                    hi = mid - 1;
                }
                else {
                    // right bias
                    lo = mid + 1;
                }
            }

            // if right part is monotonically increasing, or the pivot point is on the left part
            else {
                // 6,7,0,1,2,3,4,5
                // must use "<=" at here since we need to make sure target is in the right part,
                // then safely drop the left part
                if (nums[mid] < target && target <= nums[hi]) {
                    lo = mid + 1;
                }
                else {
                    hi = mid - 1;
                }
            }
        }
        return -1;
    }


    /**
     * Let's say nums looks like this: [12, 13, 14, 15, 16, 17, 18, 19, 0, 1, 2, 3, 4, 5, 6, 7, 8,
     * 9, 10, 11]
     * <p>
     * Because it's not fully sorted, we can't do normal binary search. But here comes the trick:
     * <p>
     * If target is let's say 14, then we adjust nums to this, where "inf" means infinity: [12, 13,
     * 14, 15, 16, 17, 18, 19, inf, inf, inf, inf, inf, inf, inf, inf, inf, inf, inf, inf]
     * <p>
     * If target is let's say 7, then we adjust nums to this: [-inf, -inf, -inf, -inf, -inf, -inf,
     * -inf, -inf, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
     * <p>
     * And then we can simply do ordinary binary search.
     * <p>
     * Of course we don't actually adjust the whole array but instead adjust only on the fly only
     * the elements we look at. And the adjustment is done by comparing both the target and the
     * actual element against nums[0].
     *
     * @param nums   rotated sorted array
     * @param target the integer number to search for
     * @return index if found in the array, otherwise return -1
     */
    public static int search2(int[] nums, int target) {

        int lo = 0, hi = nums.length - 1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            int num = nums[mid];
            // If nums[mid] and target are "on the same side" of nums[0], we just take nums[mid].
            if ((nums[mid] < nums[0]) == (target < nums[0])) {
                num = nums[mid];
            }
            // Otherwise we use -infinity or +infinity as needed.
            else {
                num = target < nums[0] ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }

            if (num < target)
                lo = mid + 1;
            else if (num > target)
                hi = mid - 1;
            else
                return mid;
        }
        return -1;
    }

    /**
     * Unit tests
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int[] nums = { 4, 5, 6, 7, 0, 1, 2 };
        int target = Integer.parseInt(args[0]);

        System.out.printf("First approach: the index is %d in the rotated sorted array.\n",
                          RotatedArray.search(nums, target));

        System.out.printf("Second approach: the index is %d in the rotated sorted array.\n",
                          RotatedArray.search2(nums, target));
    }
}
