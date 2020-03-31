/**
 * created:    2019/09/16
 * <p>
 * {@code Divisors} is to compute the greatest common divisor and related functions on integers:
 *
 * <p>
 * The greatest common divisor (gcd) of two integers a and b is the largest positive integer that is a divisor of both a and b.
 * For example, gcd(1440, 408) = 24 because 24 is a divisor of both 1440 and 408 (1440=24⋅60,408=24⋅17) but no larger integer is a divisor of both.
 * By convention, gcd(0,0)=0.
 *
 * <p>
 * The least common multiple (lcm) of two integers a and b is the smallest positive integer that is a multiple of both a and b.
 * For example, lcm(56, 96) = 672 because 672 is a multiple of both 56 and 96 (672=56⋅7=96⋅12) but no smaller positive number is a multiple of both.
 * By convention, if either a or b is 0, then lcm(a,b)=0.
 *
 * <p>
 * Two integers are relatively prime if they share no positive common divisors (other than 1).
 * For example, 221 and 384 are not relatively prime because 17 is a common divisor.
 *
 * <p>
 * Euler’s totient function ϕ(n) is the number of integers between 1 and n that are relatively prime with n.
 * For example, ϕ(9)=6 because the six numbers 1, 2, 4, 5, 7, and 8 are relatively prime with 9. Note that if n≤0, then ϕ(n)=0.
 *
 * <p>
 * Remark:
 * The greatest common divisor and least common multiple functions arise in a variety of applications, including reducing fractions,
 * modular arithmetic, and cryptography. Euler’s totient function plays an important role in number theory, including Euler’s theorem
 * and cyclotomic polynomials.
 *
 * @author Xiaoyu Zhang
 */

public class Divisors {

    // Returns the greatest common divisor of a and b with Euclid's algorithm
    public static int gcd(int a, int b) {
        int absA = Math.abs(a);
        int absB = Math.abs(b);
        int temp;

        while (absB != 0) {
            temp = absA;
            absA = absB;
            absB = temp % absB;
        }

        return absA;
    }

    // Returns the greatest common divisor of a and b.
    // Below is my own implementation of gcd. Not good, but it shows my initial thought on this problem.
    public static int gcd2(int a, int b) {
        if (a == 0 || b == 0)   return 0;
        if (a == b)             return a;

        int min = a < b ? a : b;        // the smaller number among a and b

        if (a % min == 0 && b % min == 0)   return min;

        int divisor = 2;                // the divisor
        int result = 1;                 // the greatest common divisor

        while (divisor <= (min / 2)) {
            while (a % divisor == 0 && b % divisor == 0) {
                result *= divisor;
                a /= divisor;
                b /= divisor;
            }

            divisor++;
        }

        return result;
    }

    // Returns the least common multiple of a and b.
    public static int lcm(int a, int b) {
        if (a == 0 || b == 0)   return 0;

        return Math.abs(a) / gcd(a, b) * Math.abs(b);
    }

    // Returns true if a and b are relatively prime; false otherwise.
    public static boolean areRelativelyPrime(int a, int b) {
        return gcd(a, b) == 1;
    }

    // Returns the number of integers between 1 and n that are relatively prime with n.
    public static int totient(int n) {
        if (n <= 0)     return 0;

        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (areRelativelyPrime(i, n))
                count++;
        }

        return count;
    }

    // Takes two integer command-line argument a and b and prints each
    // function, evaluated in the format (and order) given below.
    public static void main(String[] args)
    {
        int a = Integer.parseInt(args[0]);
        int b = Integer.parseInt(args[1]);

        StdOut.printf("gcd(%d, %d) = %d\n", a, b, gcd(a, b));
        StdOut.printf("lcm(%d, %d) = %d\n", a, b, lcm(a, b));
        StdOut.printf("areRelativelyPrime(%d, %d) = %b\n", a, b, areRelativelyPrime(a, b));
        StdOut.printf("totient(%d) = %d\n", a, totient(a));
        StdOut.printf("totient(%d) = %d\n", b, totient(b));
    }
}
