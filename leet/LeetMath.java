import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * created:    2020/03/30
 * <p>
 * This is the Leetcode's official curated list of top classic interview questions for the topic of Math.
 *
 * @author Xiaoyu Zhang
 */

public class LeetMath {
    /**
     * Problem 1: Fizz Buzz.
     * <p>
     * Write a program that outputs the string representation of numbers from 1 to n.
     * <p>
     * But for multiples of three it should output “Fizz” instead of the number and for the multiples of five output “Buzz”.
     * For numbers which are multiples of both three and five output “FizzBuzz”.
     *
     * @param n an integer
     * @return list of the string representation
     */
    public List<String> fizzBuzz(int n) {
        List<String> result = new LinkedList<>();

        for (int i = 1; i <= n; i++) {
            if (i % 3 == 0 && i % 5 == 0)
                result.add("FizzBuzz");
            else if (i % 3 == 0)
                result.add("Fizz");
            else if (i % 5 == 0)
                result.add("Buzz");
            else
                result.add(i + "");
        }

        return result;
    }

    // Use HashMap to solve the problem above.
    // Put all these mappings in a Hash Table.
    // This way you can add/delete mappings to/from to the hash table and not worry about changing the code.
    public List<String> fizzBuzz2(int n) {

        // ans list
        List<String> ans = new ArrayList<String>();

        // Hash map to store all fizzbuzz mappings.
        HashMap<Integer, String> fizzBizzDict =
                new HashMap<>() {
                    {
                        put(3, "Fizz");
                        put(5, "Buzz");
                    }
                };

        for (int num = 1; num <= n; num++) {

            String numAnsStr = "";

            for (Integer key : fizzBizzDict.keySet()) {

                // If the num is divisible by key,
                // then add the corresponding string mapping to current numAnsStr
                if (num % key == 0) {
                    numAnsStr += fizzBizzDict.get(key);
                }
            }

            if (numAnsStr.equals("")) {
                // Not divisible by 3 or 5, add the number
                numAnsStr += Integer.toString(num);
            }

            // Append the current answer str to the ans list
            ans.add(numAnsStr);
        }

        return ans;
    }

    /**
     * Problem 2: Count Primes.
     * <p>
     * Count the number of prime numbers less than a non-negative number, n.
     *
     * @param n a non-negative integer
     * @return the number of prime numbers less than a non-negative number
     */
    public int countPrimes(int n) {
        int count = 0;
        for (int i = 1; i < n; i++) {
            if (isPrime(i)) count++;
        }
        return count;
    }

    private boolean isPrime(int num) {
        if (num <= 1)
            return false;

        // Loop's ending condition is i * i <= num instead of i <= sqrt(num)
        // to avoid repeatedly calling an expensive function sqrt().
        for (int i = 2; i * i <= num; i++) {
            if (num % i == 0)
                return false;
        }

        return true;
    }

    // The Sieve of Eratosthenes is one of the most efficient ways to find all prime numbers up to n.
    public int countPrimes2(int n) {
        boolean[] isPrime = new boolean[n];
        for (int i = 2; i < n; i++)
            isPrime[i] = true;

        // Loop's ending condition is i * i < n instead of i < sqrt(n)
        // to avoid repeatedly calling an expensive function sqrt().
        for (int i = 2; i * i < n; i++) {
            if (!isPrime[i]) continue;
            for (int j = i * i; j < n; j += i) {
                isPrime[j] = false;
            }
        }

        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime[i]) count++;
        }
        return count;
    }

    /**
     * Problem 3: Power of Three.
     * <p>
     * Given an integer, write a function to determine if it is a power of three.
     *
     * @param n an integer
     * @return true if it's power of three, false otherwise
     */
    public boolean isPowerOfThree(int n) {
        if (n < 1)
            return false;

        while (n % 3 == 0)
            n /= 3;

        return n == 1;
    }

    /**
     * Problem 4: Roman to Integer.
     * <p>
     * Roman numerals are represented by seven different symbols: I, V, X, L, C, D and M.
     *
     * Symbol       Value
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     *
     * For example, two is written as II in Roman numeral, just two one's added together. Twelve is written as, XII, which is simply X + II.
     * The number twenty seven is written as XXVII, which is XX + V + II.
     *
     * Roman numerals are usually written largest to smallest from left to right. However, the numeral for four is not IIII.
     * Instead, the number four is written as IV. Because the one is before the five we subtract it making four.
     * The same principle applies to the number nine, which is written as IX. There are six instances where subtraction is used:
     *
     * I can be placed before V (5) and X (10) to make 4 and 9.
     * X can be placed before L (50) and C (100) to make 40 and 90.
     * C can be placed before D (500) and M (1000) to make 400 and 900.
     *
     * Given a roman numeral, convert it to an integer. Input is guaranteed to be within the range from 1 to 3999.
     *
     * @param s a roman numeral
     * @return integer converted
     */
    public int romanToInt(String s) {
        // Hash map to store all roman to integer mappings.
        HashMap<Character, Integer> romanMap =
                new HashMap<>() {
                    {
                        put('I', 1);
                        put('V', 5);
                        put('X', 10);
                        put('L', 50);
                        put('C', 100);
                        put('D', 500);
                        put('M', 1000);
                    }
                };

        int sum = 0;

        char prevChar = ' ';
        for (int i = 0; i < s.length(); i++) {
            char currChar = s.charAt(i);
            sum += romanMap.get(currChar);

            if (currChar == 'V' || currChar == 'X') {
                if (prevChar == 'I')
                    sum -= 2;
            }

            if (currChar == 'L' || currChar == 'C') {
                if (prevChar == 'X')
                    sum -= 20;
            }

            if (currChar == 'D' || currChar == 'M') {
                if (prevChar == 'C')
                    sum -= 200;
            }

            prevChar = currChar;
        }

        return sum;
    }

    /**
     * Unit tests
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        LeetMath leetMath = new LeetMath();

        System.out.println("\n>>> Problem 1: Fizz Buzz.");
        int math1_n = 15;
        for (String s : leetMath.fizzBuzz2(math1_n))
            System.out.println(s);

        System.out.println("\n>>> Problem 2: Count Primes.");
        int math2_n = 10;
        System.out.printf("The number of prime numbers less than %d is %d\n", math2_n, leetMath.countPrimes(math2_n));

        System.out.println("\n>>> Problem 3: Power of Three.");
        int math3_n = 27;
        System.out.printf("Is %d the power of three? %b\n", math3_n, leetMath.isPowerOfThree(math3_n));

        System.out.println("\n>>> Problem 4: Roman to Integer.");
        // String math4_s = "MCMXCIV";
        String math4_s = "LVIII";
        System.out.printf("Roman \"%s\" is the integer of %d\n", math4_s, leetMath.romanToInt(math4_s));
    }
}
