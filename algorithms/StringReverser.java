/**
 * created:    2020/02/27
 * <p>
 * compilation:  javac StringReverser.java
 * <p>
 * execution:    java StringReverser
 * <p>
 * Given a string, reverse it.
 * <p>
 * It's just a simple demonstration for the usage of recursion.
 *
 * @author Xiaoyu Zhang
 */

public class StringReverser {

    /**
     * Print out the reversed string.
     *
     * @param str a string to be reversed
     */
    public static void printReverse(char[] str) {
        helper(0, str);
    }

    /**
     * Helper function for printReverse method.
     *
     * @param index index of current character
     * @param str   the string to be reversed
     */
    private static void helper(int index, char[] str) {
        // base case
        if (str == null || index >= str.length)
            return;

        helper(index + 1, str);
        System.out.print(str[index]);
    }

    /**
     * Return the reversed string.
     *
     * @param str the string to be reversed
     * @return reversed string
     */
    public static String reverse(String str) {
        StringBuilder sg = new StringBuilder();
        helper2(sg, 0, str);
        return sg.toString();
    }

    /**
     * Helper function for reverse method
     *
     * @param sg    StringBuilder for building up the reversed string
     * @param index index of current character
     * @param str   the string to be reversed
     */
    private static void helper2(StringBuilder sg, int index, String str) {
        if (str == null || index >= str.length())
            return;

        helper2(sg, index + 1, str);
        sg.append(str.charAt(index));
    }

    /**
     * another way to reverse the string.
     *
     * @param str string to be reversed
     * @return reversed string
     */
    public static String reverseString(String str) {
        if (str.isEmpty())
            return str;

        return reverseString(str.substring(1)) + str.charAt(0);
    }

    /**
     * unit test
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        String s1 = "tiger";
        StringReverser.printReverse(s1.toCharArray());
        System.out.println();

        String s2 = "elephant";
        System.out.printf("%s reversed -> %s \n", s2, StringReverser.reverse(s2));

        String s3 = "lion";
        System.out.printf("%s reversed -> %s \n", s3, StringReverser.reverseString(s3));
    }
}
