import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * created:    2020/03/21
 * <p>
 * This is the Leetcode's official curated list of top classic interview questions for the topic of String.
 *
 * @author Xiaoyu Zhang
 */

public class LeetString {

    /**
     * Problem 1: Reverse String.
     *
     * Two pointers approach.
     *
     * <p>
     * Write a function that reverses a string. The input string is given as an array of characters char[].
     *
     * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
     *
     * You may assume all the characters consist of printable ascii characters.
     *
     * @param s an array of characters
     */
    public void reverseString(char[] s) {

        int start = 0, end = s.length - 1;


        while (start < end) {
            char temp = s[start];
            s[start] = s[end];
            s[end] = temp;

            start++;
            end--;
        }
    }

    // Recursive approach to solve the problem above
    public void reverseString2(char[] s) {
        helper(s, 0, s.length - 1);
    }

    private void helper(char[] s, int left, int right) {
        if (left >= right)
            return;

        char temp = s[left];
        s[left++] = s[right];
        s[right--] = temp;

        helper(s, left, right);
    }

    /**
     * Problem 2: Reverse Integer.
     *
     * <p>
     * Given a 32-bit signed integer, reverse digits of an integer.
     *
     * Note:
     * Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−2^31,  2^31 − 1].
     * For the purpose of this problem, assume that your function returns 0 when the reversed integer overflows.
     *
     * @param x a 32-bit signed integer
     */
    public int reverse(int x) {

        int pop, rev = 0;

        // Algorithm:
        // Repeatedly "pop" the last digit off of x and "push" it to the back of the rev. In the end, rev will be the reverse of the x.
        // To "pop" and "push" digits without the help of some auxiliary stack/array, we can use math.
        // However, this approach is dangerous, because the statement rev = rev * 10 + pop can cause overflow.
        // Luckily, it is easy to check beforehand whether or this statement would cause an overflow.
        while (x != 0) {
            pop = x % 10;
            x = x / 10;

            // overflow
            if (rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && pop > 7))
                return 0;

            // overflow
            if (rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && pop < -8))
                return 0;

            rev = rev * 10 + pop;

            System.out.printf("TRACE: x = %d, pop = %d, rev = %d\n", x, pop, rev);
        }

        return rev;
    }

    /**
     * Problem 3: First Unique Character in a String.
     *
     * <p>
     * Given a string, find the first non-repeating character in it and return it's index. If it doesn't exist, return -1.
     *
     * Note: You may assume the string contain only lowercase letters.
     *
     * @param s a string
     */
    public int firstUniqChar(String s) {
        if (s.length() == 0)
            return -1;

        char letter;
        Map<Character, Integer> map = new HashMap<>();

        // build hash map : character and how often it appears
        for (int i = 0; i < s.length(); i++) {
            letter = s.charAt(i);

            if (!map.containsKey(letter))
                map.put(letter, 1);
            else
                map.put(letter, map.get(letter) + 1);
        }

        Set<Character> set = new HashSet<>();
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            letter = entry.getKey();
            if (map.get(letter) == 1)
                set.add(letter);
        }

        // find the index
        for (int i = 0; i < s.length(); i++)
            if (set.contains(s.charAt(i)))
                return i;

        return -1;
    }

    // Similar approach to the one above, but more concise and without using HashSet
    public int firstUniqChar2(String s) {
        HashMap<Character, Integer> count = new HashMap<>();
        int n = s.length();

        // build hash map : character and how often it appears
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            count.put(c, count.getOrDefault(c, 0) + 1);
        }

        // find the index
        for (int i = 0; i < n; i++) {
            if (count.get(s.charAt(i)) == 1)
                return i;
        }

        return -1;
    }

    /**
     * Problem 4: Valid Anagram.
     *
     * <p>
     * Given two strings s and t , write a function to determine if t is an anagram of s.
     *
     * Note:
     * You may assume the string contains only lowercase alphabets.
     *
     * Follow up:
     * What if the inputs contain unicode characters? How would you adapt your solution to such case?
     *
     * @param s a string
     * @param t another string
     */
    public boolean isAnagram(String s, String t) {
        int len1 = s.length();
        int len2 = t.length();

        if (len1 != len2)
            return false;

        HashMap<Character, Integer> map1 = new HashMap<>();
        HashMap<Character, Integer> map2 = new HashMap<>();

        for (int i = 0; i < len1; i++) {
            char c = s.charAt(i);
            map1.put(c, map1.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < len2; i++) {
            char c = t.charAt(i);
            map2.put(c, map2.getOrDefault(c, 0) + 1);
        }

        for (Map.Entry<Character, Integer> entry : map1.entrySet()) {
            char c = entry.getKey();
            if (!map2.containsKey(c) || !map2.get(c).equals(map1.get(c))) {

                // System.out.printf("TRACE: c = %c, count1 = %d, count2 = %d\n", c, map1.get(c), map2.get(c));

                return false;
            }
        }

        return true;
    }

    // Another approach to solve the problem above.
    // But this approach only works for the strings that contains only lowercase English characters.
    // A hash table is a more generic solution and could adapt to any range of characters.
    public boolean isAnagram2(String s, String t) {
        if (s.length() != t.length())
            return false;

        // To examine if tt is a rearrangement of ss, we can count occurrences of each letter in the two strings and compare them.
        // Since both ss and tt contain only letters from a-za−z, a simple counter table of size 26 is suffice.
        // Do we need two counter tables for comparison? Actually no, because we could increment the counter for each letter in s
        // and decrement the counter for each letter in t, then check if the counter reaches back to zero.
        int[] counter = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counter[s.charAt(i) - 'a']++;
            counter[t.charAt(i) - 'a']--;
        }

        for (int count : counter) {
            if (count != 0)
                return false;
        }

        return true;
    }

    /**
     * Problem 5: Valid Palindrome.
     *
     * <p>
     * Given a string, determine if it is a palindrome, considering only alphanumeric characters and ignoring cases.
     *
     * Note:
     * For the purpose of this problem, we define empty string as valid palindrome.
     *
     * @param s a string
     */
    public boolean isPalindrome(String s) {
        int left = 0, right = s.length() - 1;

        while (left < right) {

            // Keep moving to the right if the characters that's not letter or digit
            while (left < right && !Character.isLetterOrDigit(s.charAt(left)))
                left++;

            // Keep moving to the left if the characters that's not letter or digit
            while (left < right && !Character.isLetterOrDigit(s.charAt(right)))
                right--;

            if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
                // System.out.printf("TRACE 3: left = %d, right = %d, char1 = %c, char2 = %c\n", left, right, s.charAt(left), s.charAt(right));
                return false;
            }

            left++;
            right--;
        }

        return true;
    }

    /**
     * Problem 6: String to Integer (atoi).
     *
     * <p>
     * Implement atoi which converts a string to an integer.
     *
     * The function first discards as many whitespace characters as necessary until the first non-whitespace character is found.
     * Then, starting from this character, takes an optional initial plus or minus sign followed by as many numerical digits as possible,
     * and interprets them as a numerical value.
     *
     * The string can contain additional characters after those that form the integral number,
     * which are ignored and have no effect on the behavior of this function.
     *
     * If the first sequence of non-whitespace characters in str is not a valid integral number, or if no such sequence exists
     * because either str is empty or it contains only whitespace characters, no conversion is performed.
     *
     * If no valid conversion could be performed, a zero value is returned.
     *
     * Note:
     * Only the space character ' ' is considered as whitespace character.
     * Assume we are dealing with an environment which could only store integers within the 32-bit signed integer range: [−2^31,  2^31 − 1].
     * If the numerical value is out of the range of representable values, INT_MAX (2^31 − 1) or INT_MIN (−2^31) is returned.
     *
     * @param str a string
     */
    public int myAtoi(String str) {
        int rev = 0;

        for (int i = 0; i < str.length(); i++) {

        }
        return 0;
    }

    /**
     * Unit test.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        LeetString leetString = new LeetString();

        System.out.println("\n>>> Problem 1: Reverse String.");
        char[] s_01 = {'h','e','l','l','o'};
        leetString.reverseString2(s_01);
        System.out.println(s_01);

        System.out.println("\n>>> Problem 2: Reverse Integer.");
        int x_02 = -123;
        System.out.println(leetString.reverse(x_02));

        System.out.println("\n>>> Problem 3: First Unique Character in a String.");
        // String s_03 = "leetcode";
        String s_03 = "loveleetcode";
        System.out.printf("The index of the first unique character is %d\n", leetString.firstUniqChar2(s_03));

        System.out.println("\n>>> Problem 4: Valid Anagram.");
        String s_04 = "anagram";
        String t_04 = "nagaram";
        System.out.printf("Is this a anagram? %b\n", leetString.isAnagram2(s_04, t_04));
        // System.out.printf("%s is the anagram of %s ? %b \n", s_04, t_04, leetString.isAnagram(s_04, t_04));

        System.out.println("\n>>> Problem 5: Valid Palindrome.");
        String s_05 = "A man, a plan, a canal: Panama";
        // String s_05 = "race a car";
        // String s_05 = ".,";
        System.out.printf("Is this a valid palindrome? %b\n", leetString.isPalindrome(s_05));
    }
}
