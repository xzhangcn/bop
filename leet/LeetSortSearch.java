/**
 * created:    2020/03/28
 * <p>
 * This is the Leetcode's official curated list of top classic interview questions for the topic of Sorting and Searching.
 *
 * @author Xiaoyu Zhang
 */

public class LeetSortSearch {

    /**
     * Problem 1: Merge Sorted Array.
     * <p>
     * Given two sorted integer arrays nums1 and nums2, merge nums2 into nums1 as one sorted array.
     *
     * Note:
     * The number of elements initialized in nums1 and nums2 are m and n respectively.
     * You may assume that nums1 has enough space (size that is greater or equal to m + n) to hold additional elements from nums2.
     *
     * Example:
     * Input:
     * nums1 = [1,2,3,0,0,0], m = 3
     * nums2 = [2,5,6],       n = 3
     *
     * Output: [1,2,2,3,5,6]
     *
     * @param nums1 a sorted integer array
     * @param m number of elements initialized in num1
     * @param nums2 a sorted integer array
     * @param n number of elements initialized in num2
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int p1 = 0, p2 = 0;
        int[] a = new int[nums1.length];

        for (int i = 0; i < a.length; i++) {
            if (p1 < m && p2 < n) {
                if (nums1[p1] <= nums2[p2]) {
                    a[i] = nums1[p1];
                    p1++;
                } else {
                    a[i] = nums2[p2];
                    p2++;

                }
            }
            else if (p1 < m) {
                a[i] = nums1[p1];
                p1++;
            }
            else if (p2 < n) {
                a[i] = nums2[p2];
                p2++;
            }
        }

        for (int i = 0; i < a.length; i++)
            nums1[i] = a[i];
    }

    // Another approach to the problem above.
    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        // insert from the m+n-1 position at the bigger array
        // keep 3 pointers: one at the insertion point, one at the end of nums1 and one at the end of nums2
        int insert = m + n - 1;
        int p1 = m - 1;
        int p2 = n - 1;

        while (p1 >= 0 && p2 >= 0) {
            if (nums1[p1] > nums2[p2])
                nums1[insert--] = nums1[p1--];
            else
                nums1[insert--] = nums2[p2--];

        }

        while (p2 >= 0)
            nums1[insert--] = nums2[p2--];
    }

    /**
     * Problem 2: First Bad Version.
     * <p>
     * You are a product manager and currently leading a team to develop a new product.
     * Unfortunately, the latest version of your product fails the quality check.
     * Since each version is developed based on the previous version, all the versions after a bad version are also bad.
     *
     * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which causes all the following ones to be bad.
     *
     * You are given an API bool isBadVersion(version) which will return whether version is bad.
     * Implement a function to find the first bad version. You should minimize the number of calls to the API.
     *
     * @param versions the array of versions
     * @return first bad version number
     */
    public int firstBadVersion(int[] versions) {
        int left = 1;
        int right = versions.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (isBadVersion(versions, mid)) {
                right = mid;
            }
            else {
                left = mid + 1;
            }
        }

        return left;
    }

    /**
     * @param versions list of version numbers
     * @param num      version number
     * @return true if the verson number is a bad one, false otherwise.
     */
    private static boolean isBadVersion(int[] versions, int num) {
        return versions[num] == 0;
    }

    /**
     * Unit tests
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        LeetSortSearch leetSortSearch = new LeetSortSearch();

        System.out.println("\n>>> Problem 1: Merge Sorted Array.");
        int[] nums1 = {1,2,3,0,0,0};
        int[] nums2 = {2,5,6};
        int m = 3, n = 3;
        leetSortSearch.merge(nums1, m, nums2, n);
        for (int num : nums1)
            System.out.printf("%d\t", num);
        System.out.println();

        System.out.println("\n>>> Problem 2: First Bad Version.");
        int[] versions = { 1, 1, 1, 1, 1, 1, 0, 0, 0 };
        System.out.printf("The first bad version# %d\n", leetSortSearch.firstBadVersion(versions));
    }
}
