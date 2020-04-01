import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * created:    2020/03/30
 * <p>
 * This is the Leetcode's official curated list of top classic interview questions for other various topics.
 *
 * @author Xiaoyu Zhang
 */

public class LeetOthers {

    /**
     * Problem 1: Number of 1 Bits.
     * <p>
     * Write a function that takes an unsigned integer and return the number of '1' bits it has (also known as the Hamming weight).
     * <p>
     * Note:
     * <p>
     * Note that in some languages such as Java, there is no unsigned integer type.
     * In this case, the input will be given as signed integer type and should not affect your implementation,
     * as the internal binary representation of the integer is the same whether it is signed or unsigned.
     * <p>
     * In Java, the compiler represents the signed integers using 2's complement notation.
     * <p>
     * <p>
     * Follow up:
     * If this function is called many times, how would you optimize it?
     *
     * @param n an unsigned integer
     * @return the Hamming weight
     */
    public int hammingWeight(int n) {
        return Integer.bitCount(n);     // Use the java library directly, ^_^
    }

    /**
     * Another way to solve the problem above.
     * <p>
     * The solution is straight-forward. We check each of the 3232 bits of the number. If the bit is 11, we add one to the number of 11-bits.
     * <p>
     * We can check the ith bit of a number using a bit mask. We start with a mask m = 1, because the binary representation of 1 is,
     * 0000 0000 0000 0000 0000 0000 0000 000100000000000000000000000000000001
     * Clearly, a logical AND between any number and the mask 1 gives us the least significant bit of this number.
     * <p>
     * To check the next bit, we shift the mask to the left by one.
     * 0000 0000 0000 0000 0000 0000 0000 001000000000000000000000000000000010
     * <p>
     * And so on.
     */
    public int hammingWeight2(int n) {
        int bits = 0;
        int mask = 1;
        for (int i = 0; i < 32; i++) {
            if ((n & mask) != 0) {
                bits++;
            }
            mask <<= 1;
        }
        return bits;
    }

    /**
     * We can make the previous algorithm simpler and a little faster.
     * Instead of checking every bit of the number, we repeatedly flip the least-significant 1-bit of the number to 0, and add 1 to the sum.
     * As soon as the number becomes 0, we know that it does not have any more 1-bits, and we return the sum.
     * <p>
     * The key idea here is to realize that for any number n, doing a bit-wise AND of n and n - 1 flips the least-significant 1-bit in n to 0.
     */
    public int hammingWeight3(int n) {
        int sum = 0;
        while (n != 0) {
            sum++;
            n &= (n - 1);
        }
        return sum;
    }

    /**
     * Problem 2: Hamming Distance.
     * <p>
     * The Hamming distance between two integers is the number of positions at which the corresponding bits are different.
     *
     * @param x an integer
     * @param y another integer
     * @return the Hamming distance
     */
    public int hammingDistance(int x, int y) {
        int diff = 0;
        int mask = 1;
        for (int i = 0; i < 32; i++) {
            if ((x & mask) != (y & mask))
                diff++;

            mask <<= 1;
        }

        return diff;
    }

    // Another approach to the above problem.
    public int hammingDistance2(int x, int y) {
        int xor = x ^ y;

        return hammingWeight3(xor);
    }

    /**
     * Problem 3: Reverse Bits.
     * <p>
     * Reverse bits of a given 32 bits unsigned integer.
     * <p>
     * Note:
     * Note that in some languages such as Java, there is no unsigned integer type. In this case, both input and output
     * will be given as signed integer type and should not affect your implementation, as the internal binary representation
     * of the integer is the same whether it is signed or unsigned.
     * <p>
     * In Java, the compiler represents the signed integers using 2's complement notation.
     * <p>
     * Follow up:
     * If this function is called many times, how would you optimize it?
     *
     * @param n an unsigned integer
     * @return the reversed integer
     */
    public int reverseBits(int n) {
        if (n == 0) return 0;

        int result = 0;
        for (int i = 0; i < 32; i++) {
            result <<= 1;
            if ((n & 1) == 1) result++;
            n >>= 1;
        }
        return result;
    }

    /**
     * Problem 4: Pascal's Triangle.
     * <p>
     * Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.
     * <p>
     * In Pascal's triangle, each number is the sum of the two numbers directly above it.
     *
     * @param numRows an integer
     * @return first numRows of Pascal's triangle
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> triangle = new ArrayList<List<Integer>>();

        // First base case; if user requests zero rows, they get zero rows.
        if (numRows == 0) {
            return triangle;
        }

        // Second base case; first row is always [1].
        triangle.add(new ArrayList<>());
        triangle.get(0).add(1);

        for (int rowNum = 1; rowNum < numRows; rowNum++) {
            List<Integer> row = new ArrayList<>();
            List<Integer> prevRow = triangle.get(rowNum - 1);

            // The first row element is always 1.
            row.add(1);

            // Each triangle element (other than the first and last of each row)
            // is equal to the sum of the elements above-and-to-the-left and
            // above-and-to-the-right.
            for (int j = 1; j < rowNum; j++) {
                row.add(prevRow.get(j - 1) + prevRow.get(j));
            }

            // The last row element is always 1.
            row.add(1);

            triangle.add(row);
        }

        return triangle;
    }

    /**
     * Problem 5: Valid Parentheses.
     * <p>
     * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
     * <p>
     * An input string is valid if:
     * - Open brackets must be closed by the same type of brackets.
     * - Open brackets must be closed in the correct order.
     * <p>
     * Note that an empty string is also considered valid.
     *
     * @param s a string
     * @return first numRows of Pascal's triangle
     */
    public boolean isValid(String s) {
        if (s.length() % 2 == 1)
            return false;

        char[] charArray = s.toCharArray();
        Map<Character, Character> pairs = new HashMap<>();
        pairs.put('(', ')');
        pairs.put('{', '}');
        pairs.put('[', ']');

        Deque<Character> stack = new ArrayDeque<>();
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
     * Problem 6: Missing Number.
     * <p>
     * Given an array containing n distinct numbers taken from 0, 1, 2, ..., n, find the one that is missing from the array.
     * <p>
     * Note:
     * Your algorithm should run in linear runtime complexity. Could you implement it using only constant extra space complexity?
     *
     * @param nums an array
     * @return the missing number
     */
    public int missingNumber(int[] nums) {
        int len = nums.length;
        int[] a = new int[len + 1];

        for (int i = 0; i < (len + 1); i++)
            a[i] = -1;

        for (int i = 0; i < len; i++)
            a[nums[i]] = nums[i];

        for (int i = 0; i < (len + 1); i++)
            if (a[i] == -1)
                return i;

        return -1;  // Array was not missing any numbers
    }

    /**
     * Another way to solve the above problem.
     * We can compute the sum of nums in linear time, and by Gauss' formula, we can compute the sum of
     * the first n natural numbers in constant time. Therefore, the number that is missing is simply
     * the result of Gauss' formula minus the sum of nums, as nums consists of the first n natural numbers minus some number.
     */
    public int missingNumber2(int[] nums) {
        int expectedSum = nums.length * (nums.length + 1) / 2;
        int actualSum = 0;
        for (int num : nums)
            actualSum += num;

        return expectedSum - actualSum;
    }

    /**
     * Unit tests
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {

        LeetOthers leetOthers = new LeetOthers();

        System.out.println("\n>>> Problem 1: Number of 1 Bits.");
        int others1_n = -3;
        System.out.printf("Number of 1 Bits in %d is %d\n", others1_n, leetOthers.hammingWeight(others1_n));

        System.out.println("\n>>> Problem 2: Hamming Distance.");
        int others2_x = 1;
        int others2_y = 4;
        System.out.printf("The hamming distance between %d and %d is %d\n", others2_x, others2_y, leetOthers.hammingDistance2(others2_x, others2_y));

        System.out.println("\n>>> Problem 3: Reverse Bits.");
        int others3_n = 4;
        System.out.printf("The reversed integer of %d is %d\n", others3_n, leetOthers.reverseBits(others3_n));

        System.out.println("\n>>> Problem 4: Pascal's Triangle.");
        int others4_n = 5;
        List<List<Integer>> others4_result = leetOthers.generate(others4_n);
        for (List<Integer> list : others4_result) {
            for (Integer num : list) {
                System.out.printf("%d \t", num);
            }

            System.out.println();
        }

        System.out.println("\n>>> Problem 5: Valid Parentheses.");
        String others5_s1 = "()[]{}";
        String others5_s2 = "([)]";
        String others5_s3 = "{[]}";
        System.out.printf("%s has maching parentheses? %b\n", others5_s1, leetOthers.isValid(others5_s1));
        System.out.printf("%s has maching parentheses? %b\n", others5_s2, leetOthers.isValid(others5_s2));
        System.out.printf("%s has maching parentheses? %b\n", others5_s3, leetOthers.isValid(others5_s3));

        System.out.println("\n>>> Problem 6: Missing Number.");
        int[] others6_nums = {9, 6, 4, 2, 3, 5, 7, 0, 1};
        System.out.printf("The missing number is %d\n", leetOthers.missingNumber2(others6_nums));
    }
}
