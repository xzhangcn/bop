import java.util.HashMap;
import java.util.Map;

/**
 * created:    2020/02/28
 * <p>
 * compilation:  javac StairClimber.java
 * <p>
 * execution:    java StairClimber
 * <p>
 * You are climbing a stair case. It takes n steps to reach to the top.
 * <p>
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 * <p>
 * It's just a simple demonstration for the usage of recursion.
 *
 * @author Xiaoyu Zhang
 */

public class StairClimber {

    /**
     * Recursive version.
     *
     * @param n number of stairs
     * @return distinct ways to climb the stairs
     */
    public static int climbStairs(int n) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;

        return climbStairs(n - 1) + climbStairs(n - 2);
    }

    /**
     * Dynamic programming version.
     *
     * @param n number of stairs
     * @return distinct ways to climb the stairs
     */
    public static int climbStairs2(int n) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;

        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++)
            dp[i] = dp[i - 1] + dp[i - 2];

        return dp[n];
    }

    /**
     * Recursive version with memoization
     *
     * @param n number of stairs
     * @return distinct ways to climb the stairs
     */
    public static int climbStairs3(int n) {

        Map<Integer, Integer> memo = new HashMap<>();
        return climbStair3(n, memo);
    }

    /**
     * helper function
     *
     * @param n    number of stairs
     * @param memo hashMap to store the computed values
     * @return distinct ways to climb the stairs
     */
    private static int climbStair3(int n, Map<Integer, Integer> memo) {
        if (n == 0)
            return 0;
        if (n == 1)
            return 1;
        if (n == 2)
            return 2;

        if (memo.containsKey(n))
            return memo.get(n);
        int count = climbStair3(n - 1, memo) + climbStair3(n - 2, memo);
        memo.put(n, count);

        return count;
    }

    /**
     * Unit tests.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        System.out.printf("Recusive approach: %d ways to climb up %d stairs.\n",
                          StairClimber.climbStairs(n), n);
        System.out.printf("Dynamic programming: %d ways to climb up %d stairs.\n",
                          StairClimber.climbStairs2(n), n);
        System.out.printf("Recursive approach with memoization: %d ways to climb up %d stairs.\n",
                          StairClimber.climbStairs3(n), n);
    }
}
