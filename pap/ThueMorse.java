/**
 * created:    2019/09/03
 * <p>
 * {@code ThueMorse} sequence is an infinite sequence of 0s and 1s that is constructed by starting with 0
 * and successively appending the bitwise negation (interchange 0s and 1s) of the existing sequence.

 * To visualize the Thue–Morse sequence, create an n-by-n pattern by printing a + (plus sign) in row and column
 * if bits and in the sequence are equal, and a - (minus sign) if they are different.
 *
 * <p>
 * Note: you may assume that n is a positive integer (but it need not be a power of 2).
 *
 * Remark:
 * The Thue–Morse sequence has many fascinating properties and arises in graphic design and in music composition.
 *
 * @author Xiaoyu Zhang
 */

public class ThueMorse {
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        // Construct the Thue-Morse sequence for the input n.
        // If n is not a power of 2, construct the Thue–Morse sequence for the smallest power of 2 larger than n.
        int exponent = 0;
        while ((int) Math.pow(2, exponent) < n)
            exponent++;

        int[] tm = new int[(int) Math.pow(2, exponent)];     // Thue-Morse sequence
        tm[0] = 0;
        for (int i = 1; i < tm.length; i++) {
            if (i % 2 == 0)
                tm[i] = tm[i / 2];
            else
                tm[i] = 1 - tm[i - 1];
        }

        // Print the Thue-Morse wave
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (!(tm[i] == tm[j]))
                    System.out.print("-");
                else
                    System.out.print("+");

                if (j < (n - 1))
                    System.out.print("  ");
            }
            System.out.println();
        }
    }
}
