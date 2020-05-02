import java.util.HashSet;
import java.util.Set;

/**
 * created:    2019/05/01
 * <p>
 * {@code Leet30May} is the 30-day LeetCoding challenge.
 * <p>
 * A new problem will appear each day, and you will have one day to solve it.
 * <p>
 * The problems below are for the April 2020.
 *
 * @author Xiaoyu Zhang
 */

public class Leet30May {
    /**
     * Problem on the day of 05/01/2020: First Bad Version.
     * <p>
     * You are a product manager and currently leading a team to develop a new product.
     * Unfortunately, the latest version of your product fails the quality check.
     * Since each version is developed based on the previous version, all the versions after a bad version are also bad.
     *
     * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one,
     * which causes all the following ones to be bad.
     *
     * You are given an API bool isBadVersion(version) which will return whether version is bad.
     * Implement a function to find the first bad version. You should minimize the number of calls to the API.
     *
     * @param versions list of version numbers
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
    private boolean isBadVersion(int[] versions, int num) {
        return versions[num] == 0;
    }

    /**
     * Problem on the day of 05/02/2020: Jewels and Stones.
     * <p>
     * You're given strings J representing the types of stones that are jewels, and S representing the stones you have.
     * Each character in S is a type of stone you have.  You want to know how many of the stones you have are also jewels.
     *
     * The letters in J are guaranteed distinct, and all characters in J and S are letters. Letters are case sensitive,
     * so "a" is considered a different type of stone from "A".
     *
     * @param J string of jewels
     * @param S string of stones
     * @return number of jewels
     */
    public int numJewelsInStones(String J, String S) {
        Set<Character> jewels = new HashSet<>();

        // read J and build jewels hash set.
        for (int i = 0; i < J.length(); i++)
            jewels.add(J.charAt(i));

        int count = 0;

        // read S and count jewels.
        for (int i = 0; i < S.length(); i++)
            if (jewels.contains(S.charAt(i)))
                count++;

        return count;
    }

    // A more compact version of above solution
    public int numJewelsInStones2(String J, String S) {
        int res = 0;
        Set<Character> setJ = new HashSet<>();
        for (char j: J.toCharArray())
            setJ.add(j);

        for (char s: S.toCharArray())
            if (setJ.contains(s))
                res++;

        return res;
    }

    /**
     * Unit tests
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Leet30May leet30May = new Leet30May();

        System.out.println("\n>>> Problem on the day of 05/01/2020: First Bad Version.");
        int[] p1_versions = { 1, 1, 1, 1, 1, 1, 0, 0, 0 };
        System.out.printf("The first bad version# is %d\n", leet30May.firstBadVersion(p1_versions));

        System.out.println("\n>>> Problem on the day of 05/02/2020: Jewels and Stones.");
        String p2_J = "aA", p2_S = "aAAbbbb";
        System.out.printf("Number of jewels: %d\n", leet30May.numJewelsInStones(p2_J, p2_S));
    }
}
