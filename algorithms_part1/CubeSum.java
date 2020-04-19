/******************************************************************************
 *  Compilation:  javac CubeSum.java
 *  Execution:    java CubeSum n
 *  Dependencies: MinPQ.java
 *
 *  Print out integers of the form a^3 + b^3 in sorted order, where
 *  0 <= a <= b <= n.
 *
 *  % java CubeSum 10
 *  0 = 0^3 + 0^3
 *  1 = 0^3 + 1^3
 *  2 = 1^3 + 1^3
 *  8 = 0^3 + 2^3
 *  9 = 1^3 + 2^3
 *  ...
 *  1729 = 9^3 + 10^3
 *  1729 = 1^3 + 12^3
 *  ...
 *  3456 = 12^3 + 12^3
 *
 *  Remarks
 *  -------
 *   - Easily extends to handle sums of the form f(a) + g(b)
 *   - Prints out a sum more than once if it can be obtained
 *     in more than one way, e.g., 1729 = 9^3 + 10^3 = 1^3 + 12^3
 *
 ******************************************************************************/

public class CubeSum implements Comparable<CubeSum> {
    private final int sum;
    private final int i;
    private final int j;

    public CubeSum(int i, int j) {
        this.sum = i*i*i + j*j*j;
        this.i = i;
        this.j = j;
    }

    public int compareTo(CubeSum that) {
        if (this.sum < that.sum) return -1;
        if (this.sum > that.sum) return +1;
        return 0;
    }

    public String toString() {
        return sum + " = " + i + "^3" + " + " + j + "^3";
    }


    public static void main(String[] args) {

        int n = Integer.parseInt(args[0]);

        // initialize priority queue
        MinPQ<CubeSum> pq = new MinPQ<CubeSum>();
        for (int i = 0; i <= n; i++) {
            pq.insert(new CubeSum(i, i));
        }

        // find smallest sum, print it out, and update
        /*
        while (!pq.isEmpty()) {
            CubeSum s = pq.delMin();
            StdOut.println(s);
            if (s.j < n)
                pq.insert(new CubeSum(s.i, s.j + 1));
        }
         */

        // Code below is add by xiaoyu on 2019/12/13
        // to find all distinct integers a, b, c, and d between 0 and 10^6
        // such that a3 + b3 = c3 + d3, e.g., 1729 = 9^3 + 10^3 = 1^3 + 12^3.
        CubeSum pre = pq.delMin();
        while (!pq.isEmpty()) {

            if (pre.j < n)
                pq.insert(new CubeSum(pre.i, pre.j + 1));

            CubeSum cur = pq.delMin();
            if (cur.compareTo(pre) == 0) {
                StdOut.println(pre);
                StdOut.println(cur);
            }

            pre = cur;
        }
    }

}