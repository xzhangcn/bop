/**
 * created:    2019/10/20
 * <p>
 * {@code RevesPuzzle} is identical to the towers of Hanoi problem, except that there are 4 poles (instead of 3).
 * The task is to move n discs of different sizes from the starting pole to the destination pole, while obeying the following rules:
 *    - Move only one disc at a time.
 *    - Never place a larger disc on a smaller one.
 *
 *  This program takes an integer command-line argument n and prints a solution to Reve’s puzzle. Assume that the the discs
 *  are labeled in increasing order of size from 1 to n and that the poles are labeled A, B, C, and D, with A representing
 *  the starting pole and D representing the destination pole.
 *
 *  Note: you may assume that n is a positive integer.
 *
 *  Recall that for the towers of Hanoi problem, the minimum number of moves for a 64-disc problem is 2^64 - 1.
 *  With the addition of a fourth pole, the minimum number of moves for a 64-disc problem is reduced to 18,433.
 * @author Xiaoyu Zhang
 */


public class RevesPuzzle {

    private static int count = 0;       // number of moves

    // move n smallest discs from one pole to another, using the temp1 and temp2 poles
    private static void reves(int n, String from, String temp1, String temp2, String to)
    {
        if (n == 0) return;

        int k = (int) Math.round((n + 1 - Math.sqrt(2*n + 1)));

        // StdOut.printf("reves_4: k = (%d) \n", k);

        // Transfer (recursively) the k smallest discs to a single pole other than the start or destination poles.
        reves(k, from, to, temp1, temp2);

        // Transfer the remaining n−k disks to the destination pole (without using the pole that now contains the smallest k discs).
        // To do so, use the algorithm for the 3-pole towers of Hanoi problem.
        hanoi(k, n-k, from, temp1, to);

        // Transfer (recursively) the k smallest discs to the destination pole.
        reves(k, temp2, from, temp1, to);
    }

    // move k discs from one pole to another, using the temp pole
    private static void hanoi(int n, int k, String from, String temp, String to)
    {
        if (k == 0) return;
        // StdOut.printf("reves_3: k = (%d) \n", k);

        hanoi(n, k-1, from, to, temp);

        StdOut.println("Move disc " + (n+k) + " from " + from + " to " + to);
        count++;

        hanoi(n, k-1, temp, from, to);
    }

    public static void printCount(int n) {
        System.out.printf("Total %d moves to move %d different discs\n", count, n);
    }


    public static void main(String[] args)
    {
        int n = Integer.parseInt(args[0]);

        // StdOut.printf("n = (%d) \n", n);

        reves(n, "A", "B", "C", "D");
        printCount(n);
    }
}
