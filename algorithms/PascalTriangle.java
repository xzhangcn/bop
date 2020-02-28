import java.util.ArrayList;
import java.util.List;

/**
 * created:    2020/02/28
 * <p>
 * compilation:  javac PascalTriangle.java
 * <p>
 * execution:    java PascalTriangle
 * <p>
 * Given a non-negative index k where k ≤ 33, return the kth index row of the Pascal's triangle.
 * Note that the row index starts from 0. In Pascal's triangle, each number is the sum of the two
 * numbers directly above it.
 * <p>
 *
 * @author Xiaoyu Zhang
 */

public class PascalTriangle {

    /**
     * When generating each row, we can use the previous row directly, so this way we only use O(k)
     * space with k being the number of row. For each new row, we append a 1, letting j iterate from
     * i - 1 backward to 1, and set the jth element as res.set(j, res.get(j-1) + res.get(j)).
     *
     * @param index
     * @return
     */
    public static List<Integer> getRow(int index) {
        List<Integer> ret = new ArrayList<>();
        for (int i = 1; i <= index; i++) {
            ret.add(1);
            for (int j = i - 1; j >= 1; j--) {
                int tmp = ret.get(j - 1) + ret.get(j);
                ret.set(j, tmp);
            }
        }

        return ret;
    }

    /**
     * unit tests
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        System.out.printf("the %dth index row of the Pascal's triangle: \n", k);
        for (int i = 0; i <= k; i++) {
            for (int num : PascalTriangle.getRow(i))
                System.out.printf("%d\t", num);
            System.out.println();
        }
    }
}
