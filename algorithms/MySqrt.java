/**
 * created:    2020/03/02
 * <p>
 * compilation:  javac MySqrt.java
 * <p>
 * execution:    java MySqrt
 * <p>
 * Compute and return the square root of x, where x is guaranteed to be a non-negative integer.
 * <p>
 * Since the return type is an integer, the decimal digits are truncated and only the integer part
 * of the result is returned.
 * <p>
 * It's just a simple demonstration for binary search algorithm.
 *
 * @author Xiaoyu Zhang
 */

public class MySqrt {

    /**
     * Use binary search to computer the square root
     *
     * @param x non-negative integer
     * @return the integer part of the sqaure root of a non-negative integer
     */
    public static int sqrt(int x) {
        if (x == 0)
            return 0;
        int lo = 1, hi = x / 2 + 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (mid < x / mid)
                lo = mid + 1;
            else if (mid > x / mid)
                hi = mid - 1;
            else
                return mid;
        }

        return hi;
    }

    /**
     * Use Newton's method to computer the square root
     *
     * @param x non-negative integer
     * @return the integer part of the sqaure root of a non-negative integer
     */
    public static int sqrt2(int x) {
        if (x == 0)
            return 0;
        long num = x;
        while (num > x / num)
            num = (num + x / num) / 2;

        return (int) num;
    }

    /**
     * Unit tests
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        System.out
                .printf("Binary Search method: the square root of %d is %d.\n", x, MySqrt.sqrt(x));
        System.out.printf("Newton's method: the square root of %d is %d.\n", x, MySqrt.sqrt2(x));
    }
}
