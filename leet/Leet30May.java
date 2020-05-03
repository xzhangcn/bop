import java.util.HashMap;
import java.util.Map;
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
     * <p>
     * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one,
     * which causes all the following ones to be bad.
     * <p>
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
            } else {
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
     * <p>
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
        for (char j : J.toCharArray())
            setJ.add(j);

        for (char s : S.toCharArray())
            if (setJ.contains(s))
                res++;

        return res;
    }

    /**
     * Problem on the day of 05/03/2020: Ransom Note.
     * <p>
     * Given an arbitrary ransom note string and another string containing letters from all the magazines,
     * write a function that will return true if the ransom note can be constructed from the magazines ; otherwise, it will return false.
     * <p>
     * Each letter in the magazine string can only be used once in your ransom note.
     *
     * @param ransomNote a string
     * @param magazine   another string
     * @return true if the ransom note can be constructed from the magazines
     */
    public boolean canConstruct(String ransomNote, String magazine) {
        Map<Character, Integer> map = new HashMap<>();

        // construct the HashMap
        for (int i = 0; i < magazine.length(); i++) {
            char c = magazine.charAt(i);
            if (map.containsKey(c))
                map.put(c, map.get(c) + 1);
            else
                map.put(c, 1);
        }

        // go through the ransomNote
        for (int i = 0; i < ransomNote.length(); i++) {
            char c = ransomNote.charAt(i);
            if (!map.containsKey(c))
                return false;
            else {
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0)
                    map.remove(c);
            }
        }

        return true;
    }

    // Another approach to the above problem
    // The code is fast mainly because it uses the ASCII value of the char in the string as the index of an array,
    // resulting in direct access and thus significantly increases efficiency of the algorithm.
    public boolean canConstruct2(String ransomNote, String magazine) {
        int[] table = new int[26];

        for (char c : magazine.toCharArray())
            table[c - 'a']++;

        for (char c : ransomNote.toCharArray())
            if (--table[c - 'a'] < 0)
                return false;

        return true;
    }

    /**
     * Unit tests
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Leet30May leet30May = new Leet30May();

        System.out.println("\n>>> Problem on the day of 05/01/2020: First Bad Version.");
        int[] p1_versions = {1, 1, 1, 1, 1, 1, 0, 0, 0};
        System.out.printf("The first bad version# is %d\n", leet30May.firstBadVersion(p1_versions));

        System.out.println("\n>>> Problem on the day of 05/02/2020: Jewels and Stones.");
        String p2_J = "aA", p2_S = "aAAbbbb";
        System.out.printf("Number of jewels: %d\n", leet30May.numJewelsInStones(p2_J, p2_S));

        System.out.println("\n>>> Problem on the day of 05/03/2020: Ransom Note.");
        String p3_ransomNote = "aa", p3_maganize = "aab";
        System.out.printf("If the ransom note can be constructed from the magazines? %b\n",
                leet30May.canConstruct(p3_ransomNote, p3_maganize));
    }
}
