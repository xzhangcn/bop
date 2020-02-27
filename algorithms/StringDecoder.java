import java.util.ArrayDeque;
import java.util.Deque;

/**
 * created:    2020/02/27
 * <p>
 * compilation:  javac StringDecoder.java
 * <p>
 * execution:    java StringDecoder
 * <p>
 * Given an encoded string, return its decoded string.
 * <p>
 * The encoding rule is: k[encoded_string], where the encoded_string inside the square brackets is
 * being repeated exactly k times. Note that k is guaranteed to be a positive integer.
 * <p>
 * You may assume that the input string is always valid; No extra white spaces, square brackets are
 * well-formed, etc.
 * <p>
 * Furthermore, you may assume that the original data does not contain any digits and that digits
 * are only for those repeat numbers, k. For example, there won't be input like 3a or 2[4].
 * <p>
 * Example 1: s = "3[a]2[bc]", return "aaabcbc".
 * <p>
 * Example 2: s = "3[a2[c]]", return "accaccacc".
 * <p>
 * Example 3: s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
 * <p>
 *
 * @author Xiaoyu Zhang
 */

public class StringDecoder {

    /**
     * @param s the string to be decoded
     * @return decoded string
     */
    public static String decode(String s) {
        // Stack is deprecated so using double-ended Queue
        Deque<Integer> countStack = new ArrayDeque<>();
        Deque<String> strStack = new ArrayDeque<>();

        // curString is the string for current level. Whenever entering into a '[', meaning
        // entering one level inside. ']' means going outside one level.
        String curString = "";

        // curNum is the number of times for the next adjacent level's string.
        int curNum = 0;

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);

            // If a number encountered, update curNum.
            if (ch >= '0' && ch <= '9') {
                curNum *= 10;
                curNum += (ch - '0');
            }

            // If go inside one level, preserve previous level's information.
            else if (ch == '[') {
                countStack.push(curNum);
                strStack.push(curString);

                curNum = 0;                 // reset
                curString = "";             // reset
            }

            // When get out, update the previous level's string with next level's result.
            else if (ch == ']') {
                int times = countStack.pop();
                StringBuilder subStr = new StringBuilder();

                for (int j = 0; j < times; j++)
                    subStr.append(curString);

                // retreive and update the previous level's curString.
                curString = strStack.pop();
                curString += subStr.toString();
            }

            // If an english character encountered, append it to curString.
            else {
                curString += ch;
            }
        }

        return curString;
    }

    /**
     * unit tests
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        String s1 = "3[a]2[bc]";
        String s2 = "3[a2[c]]";
        String s3 = "2[abc]3[cd]ef";

        System.out.printf("%s decoded -> %s\n", s1, StringDecoder.decode(s1));
        System.out.printf("%s decoded -> %s\n", s2, StringDecoder.decode(s2));
        System.out.printf("%s decoded -> %s\n", s3, StringDecoder.decode(s3));
    }
}
