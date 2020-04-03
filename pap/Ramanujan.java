/**
 * created:    2019/10/26
 * <p>
 * {@code Ramanujan} is to solve the Ramanujan numbers problem.
 *
 * <p>
 * An integer n is a Ramanujan number if can be expressed as the sum of two positive cubes in two different ways
 *
 * <p>
 * Execution:   java Ramanujan 9223278330318728221
 *
 * @author Xiaoyu Zhang
 */

public class Ramanujan {

    // Is n a Ramanujan number?
    public static boolean isRamanujan(long n)
    {
        int count = 0;

        // two pointers, one from the beginning and one from the end
        long i = 1, j = (long) (Math.cbrt(n));
        long temp;

        // testing purpose
        // StdOut.printf("i = %d, j = %d \n", i, j);

        // long tmpJ = j * j;
        // StdOut.println(tmpJ);

        while (i < j) {

            // temp = (long) Math.pow(i, 3) + (long) Math.pow(j, 3);

            temp = i * i * i + j * j * j;

            if (temp == n) {
                count++;
                i++;    // move front pointer one step forward
                j--;    // move back pointer one step backward
            }
            else if (temp < n)   {
                i++;

            }
            else {
                j--;

            }

            if (count == 2)
                return true;
        }

        return false;
    }

    // Takes a long integer command-line arguments n and prints true if
    // n is a Ramanujan number, and false otherwise.
    public static void main(String[] args)
    {
        long n = Long.parseLong(args[0]);

        // testing purpose
        // if (n < Math.pow(2, 63)) StdOut.println("testing... True");

        StdOut.println(isRamanujan(n));
    }
}
