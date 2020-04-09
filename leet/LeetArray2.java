/**
 * created:    2020/04/09
 * <p>
 * This is second collection of leetcode problems for the topic of Array from Top Interview Questions.
 *
 * @author Xiaoyu Zhang
 */

public class LeetArray2 {
    /**
     * Problem 1: Container With Most Water.
     * <p>
     * Given n non-negative integers a1, a2, ..., an , where each represents a point at coordinate (i, ai).
     * n vertical lines are drawn such that the two endpoints of line i is at (i, ai) and (i, 0).
     * Find two lines, which together with x-axis forms a container, such that the container contains the most water.
     *
     * Note: You may not slant the container and n is at least 2.
     *
     * @param height an array of non-negative integers
     * @return linked list being added
     */
    public int maxArea(int[] height) {

        int front = 0, end = height.length - 1; // two pointers
        int maxRegion = Integer.MIN_VALUE;
        int area = 0;
        while (front < end) {
            area = Math.min(height[front], height[end]) * (end - front);
            maxRegion = Math.max(area, maxRegion);

            if (height[front] < height[end])
                front++;
            else
                end--;
        }

        return maxRegion;
    }

    // A more compact version to the above solution.
    public int maxArea2(int[] height) {
        int maxarea = 0, l = 0, r = height.length - 1;

        while (l < r) {
            maxarea = Math.max(maxarea, Math.min(height[l], height[r]) * (r - l));

            if (height[l] < height[r])
                l++;
            else
                r--;
        }

        return maxarea;
    }

    /**
     * Unit tests
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        LeetArray2 leetArray2 = new LeetArray2();

        System.out.println("\n>>> Problem 1: Container With Most Water.");
        // int[] p1_height = {1,8,6,2,5,4,8,3,7};
        int[] p1_height = {3, 9, 3, 4, 7, 2, 12, 6};
        System.out.printf("The max area is %d\n", leetArray2.maxArea(p1_height));
    }
}
