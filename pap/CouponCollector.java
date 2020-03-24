/**
 * created:    2020/03/24
 * <p>
 * {@code CouponCollector} is an example of Monte Carlo simulation.
 *
 * Given n distinct card types, how many random cards do you need do collect before you have (at least) one of each type?
 * This program simulates this random process.
 *
 * <p>
 * Coupon collector problem
 *    M different types of coupons;
 *    Collector acquires random coupons, one at a time, each type equally likely.
 *
 * Q. What is the expected number of coupons needed to acquire a full collection?
 * (known via mathematical analysis for centuries) About (M * ln M + .57721 * M)
 *
 * <p>
 * Remarks:
 *    Computer simulation can help validate mathematical analysis.
 *    Computer simulation can also validate software behavior. (Example: Is Math.random() simulating randomness?)
 *
 * @author Xiaoyu Zhang
 */

public class CouponCollector {
    public static void main(String[] args) {
        int M = Integer.parseInt(args[0]);          // number of card types
        int trials = Integer.parseInt(args[1]);
        int cards = 0;                              // total number of cards collected
        boolean[] found;                            // found[i] = true if card i has been collected

        for (int i = 0; i < trials; i++) {
            int distinct = 0;                       // number of distinct cards
            found = new boolean[M];

            // repeatedly choose a random card and check whether it's a new one
            while (distinct < M) {
                int r = (int) (Math.random() * M);  // random card between 0 and M-1
                cards++;                            // we collected one more card
                if (!found[r]) {
                    distinct++;
                    found[r] = true;
                }
            }
        }

        System.out.printf("Average %d cards bought to collect %d distinct cards.\n", cards/trials, M);
    }
}
