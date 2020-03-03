/**
 * created:    2020/03/03
 * <p>
 * compilation:  javac VersionControl.java
 * <p>
 * execution:    java VersionControl
 * <p>
 * You are a product manager and currently leading a team to develop a new product. Unfortunately,
 * the latest version of your product fails the quality check. Since each version is developed based
 * on the previous version, all the versions after a bad version are also bad.
 * <p>
 * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one, which
 * causes all the following ones to be bad.
 * <p>
 * You are given an API bool isBadVersion(version) which will return whether version is bad.
 * Implement a function to find the first bad version. You should minimize the number of calls to
 * the API.
 * <p>
 * It's just a simple demonstration for binary search algorithm.
 *
 * @author Xiaoyu Zhang
 */

public class VersionControl {

    /**
     * Find the first bad version from a sequence of versions
     *
     * @param versions list of version numbers
     * @return first bad version number
     */
    public static int firstBadVersion(int[] versions) {
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
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int[] versions = { 1, 1, 1, 1, 1, 1, 0, 0, 0 };
        System.out.printf("The first bad version# %d?\n",
                          VersionControl.firstBadVersion(versions));
    }
}
