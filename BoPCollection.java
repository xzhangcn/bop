import edu.princeton.cs.algs4.StdOut;

/******************************************************************************
 *  author:     Xiaoyu Zhang
 *  created:    2020/02/03
 *  Compilation:  javac-algs4 BoPCollection.java
 *  Execution:    java-algs4 BoPCollection
 *  Dependencies: StdOut.java
 *
 *  BoPCollection is a collection of solutions to the small scale problems
 *  in the book of "Beauty of Programming".
 *
 ******************************************************************************/

public class BoPCollection {

    // problem #2.1
    // Count the number of "1" for the binary representation of
    // a unsigned 8-bit integer.
    public static int countBinaryOnes(short num) {
        int count = 0;
        while (num != 0) {
            if (num != 2 * (num >> 1))
                count++;
            num = (short) (num >> 1);
        }

        return count;
    }

    // problem #2.2
    // sub-problem #1
    // count number of 0's at the end of the factorial of N (N!)
    public static int countEndZerosOfFactorial(int N) {

        // how many 10's, 5's, 2's for each number
        int tenCnt = 0, fiveCnt = 0, twoCnt = 0;

        for (int k = N; k >= 2; k--) {

            int i = k;
            while (i >= 1) {
                if (i % 10 == 0) {
                    tenCnt++;
                    i /= 10;

                    // StdOut.println("i = " + i);
                }
                else if (i % 5 == 0) {
                    fiveCnt++;
                    i /= 5;

                    // StdOut.println("i = " + i);
                }
                else if (i % 2 == 0) {
                    twoCnt++;
                    i /= 2;
                    // StdOut.println("i = " + i);
                }
                else break;
            }
        }

        return tenCnt + Math.min(fiveCnt, twoCnt);
    }

    // calcuate n!
    // return long to prevent overflow
    public static long Factorial(int n) {
        if (n == 0) return 1;
        return n * Factorial(n - 1);
    }

    // problem #2.2
    // sub-problem #2
    // find out the position of least significant '1' in binary representation of N!
    public static short getLeastOnePosition(int n) {
        long fact = Factorial(n);
        short pos = 0;

        while (fact >= 0) {

            if ((fact & 0x01) == 1)
                return ++pos;

            pos++;
            fact = fact >> 1;
        }

        return 0;
    }

    // problem #3.1
    // return true if rotated s1 contains s2; false otherwise.
    public static boolean isRotateContain(String s1, String s2) {
        int i;                      // index to iterate through string s1
        int j;                      // index to interate through string s2

        int len1 = s1.length();
        int len2 = s2.length();

        for (i = 0; i < (len1 + len2); i++) {

            // check if s1 contains s2
            if (s1.charAt(i % len1) == s2.charAt(0)) {

                for (j = 0; j < len2; j++) {

                    // break out of the loop, if s1 not contain s2
                    if (s1.charAt((i + j) % len1) != s2.charAt(j))
                        break;
                }

                // s1 contains s2
                if (j == len2)
                    return true;
            }
        }

        return false;
    }

    /**
     * Unit tests the {@code FileManager} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        // problem #2.1
        short num = 12;
        StdOut.printf("problem #2.1 \n");
        StdOut.printf("there are %d of 1s in %d's binary representation. \n",
                      BoPCollection.countBinaryOnes(num), num);

        // problem #2.2
        // sub-problem #1 #2
        int N = 12;
        long fact = BoPCollection.Factorial(N);
        StdOut.printf("problem #2.2 1 \n");
        StdOut.printf("%d of zeros at end for the factoral of %d \n",
                      BoPCollection.countEndZerosOfFactorial(N), N);
        StdOut.printf("problem #2.2 2 \n");
        StdOut.printf(
                "least significant position of '1' in the binary representation is %d for the factorial of %d \n",
                BoPCollection.getLeastOnePosition(N), N);
        System.out.println(
                N + "! (" + fact + ") 's binary representation is " + Long.toBinaryString(fact)
                        + "\n");

        // problem #3.1
        String s1 = "AABCD";
        String s2 = "CDAA";
        StdOut.printf("problem #3.1 \n");
        StdOut.printf("is %s constains %s: %b \n\n", s1, s2, BoPCollection.isRotateContain(s1, s2));
    }
}
