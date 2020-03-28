/**
 * created:    2019/09/13
 * <p>
 * {@code ShannonEntropy} takes a command-line integer m; reads a sequence of integers between 1 and m from standard input;
 * and prints the Shannon entropy to standard output, with 4 digits after the decimal point.
 *
 * Remark:
 * The Shannon entropy is a measure of the rate of information produced by a random source, such as the outcomes of
 * flipping a fair coin or rolling a loaded die. It is a fundamental concept in information theory and data compression.
 *
 * @author Xiaoyu Zhang
 */

public class ShannonEntropy {

    public static void main(String[] args) {

        int m = Integer.parseInt(args[0]);

        int[] intCount = new int[m];		// array storing the frequency of the integers from Stdin
        int idx = 0;						// integer from StdIn, and used as the array index
        int count = 0;						// keep tracking the number of integers from StdIn;
        double p;							// proportion of each integer
        double tmp;							// save the temporary computation result
        double se = 0.0;					// Shannon Entropy number

        while (!StdIn.isEmpty()) {
            idx = StdIn.readInt() - 1;
            intCount[idx]++;
            count++;
        }

        for (int i = 0; i < m; i++) {
            // don't forget to cast the data type to double, because int divide int will still be int
            p = (double) intCount[i] / count;
            tmp = (p == 0) ? 0 : (-1.0 * p * Math.log(p) / Math.log(2));

            se += tmp;
        }

        StdOut.printf("%.4f\n", se);		// format the floating number with 4 digits after the decimal point
    }
}
