import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * created:    2020/02/27
 * <p>
 * compilation:  javac ParenthesesMatcher.java
 * <p>
 * execution:    java ParenthesesMatcher
 * <p>
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the
 * input string is valid.
 * <p>
 * An input string is valid if:
 * <ol>
 *     <li>Open brackets must be closed by the same type of brackets.</li>
 *     <li>Open brackets must be closed in the correct order.</li>
 * </ol>
 * <p>
 * Example 1: Input: "()", Output: true.
 * <p>
 * Example 2: Input: "()[]{}", Output: true.
 * <p>
 * Example 3: Input: "(]", Output: false.
 * <p>
 * Example 4: Input: "([)]", Output: false.
 * <p>
 * Example 5: Input: "{[]}", Output: true.
 * <p>
 * Note:
 * <ol>
 *     <li>An interesting property about a valid parenthesis expression is that a
 * sub-expression of a valid expression should also be a valid expression. (Not every
 * sub-expression)</li>
 *     <li>What if whenever we encounter a matching pair of parenthesis in the
 * expression, we simply remove it from the expression? This would keep on shortening the
 * expression</li>
 *     <li>The stack data structure can come in handy here in representing this
 * recursive structure of the problem. We can't really process this from the inside out because we
 * don't have an idea about the overall structure. But, the stack can help us process this
 * recursively i.e. from outside to inwards.</li>
 * </ol>
 *
 * @author Xiaoyu Zhang
 */

public class ParenthesesMatcher {

    /**
     * @param str a string to be checked
     * @return true if the string has matching parentheses, false otherwise.
     */
    public static boolean isValid(String str) {
        if (str.length() % 2 == 1)
            return false;

        char[] charArray = str.toCharArray();
        Map<Character, Character> pairs = new HashMap<>();
        pairs.put('(', ')');
        pairs.put('{', '}');
        pairs.put('[', ']');

        Stack<Character> stack = new Stack<>();
        for (char c : charArray) {
            if (pairs.containsKey(c))
                stack.push(pairs.get(c));
            else {
                if (stack.isEmpty() || c != stack.pop())
                    return false;
            }
        }

        return stack.isEmpty();

    }

    /**
     * unit tests
     *
     * @param args
     */
    public static void main(String[] args) {
        String s1 = "()[]{}";
        String s2 = "([)]";
        String s3 = "{[]}";

        System.out.printf("%s has maching parentheses? %b\n", s1, ParenthesesMatcher.isValid(s1));
        System.out.printf("%s has maching parentheses? %b\n", s2, ParenthesesMatcher.isValid(s2));
        System.out.printf("%s has maching parentheses? %b\n", s3, ParenthesesMatcher.isValid(s3));
    }
}
