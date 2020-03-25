/**
 * created:    2019/09/10
 * <p>
 * {@code Birthday} is t is a 1960s era video game played on an m-by-n grid of cells. The goal is to deduce
 * which cells contain hidden mines using clues about the number of mines in neighboring cells.
 *
 * This program takes three integer command-line arguments m, n, and k and prints an m-by-n grid of cells with k mines,
 * using asterisks for mines and integers for the neighboring mine counts.

 * To do so,
 *    - Generate an m-by-n grid of cells, with exactly k of the mn cells containing mines, uniformly at random.
 *    - For each cell not containing a mine, count the number of neighboring mines (above, below, left, right, or diagonal).
 *
 * @author Xiaoyu Zhang
 */

public class Minesweeper {

    public static void main(String[] args) {

        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);

        int[][] grid = new int[m][n];   // all initialized to 0
        int mineTotal;                  // total number of mines around

        int i, j;                       // index to access the array

        int idx;                        // index for generating the mines

        int randomNum;                  // uniformly generated random number

        // generate the k mines, uniformly at random
        for (idx = 0; idx < k; idx++)
        {

            while (true)
            {
                randomNum = (int) (Math.random() * (m * n));

                i = randomNum / n;
                j = randomNum % n;

                if (grid[i][j] == 0)
                {
                    grid[i][j] = 1;
                    break;
                }

            }
        }

        // print out the mine
        for (i = 0; i < m; i++)
        {
            for (j = 0; j < n; j++)
            {
                mineTotal = 0;

                if (grid[i][j] != 1)
                {
                    if (i - 1 >= 0)                 mineTotal += grid[i-1][j];      // above
                    if (i + 1 < m)                  mineTotal += grid[i+1][j];      // below
                    if (j - 1 >= 0)                 mineTotal += grid[i][j-1];      // left
                    if (j + 1 < n)                  mineTotal += grid[i][j+1];      // right
                    if (i - 1 >= 0 && j - 1 >= 0)   mineTotal += grid[i-1][j-1];    // above and left diagnoal
                    if (i - 1 >= 0 && j + 1 < n)    mineTotal += grid[i-1][j+1];    // above and right diagnoal
                    if (i + 1 < m && j - 1 >= 0)    mineTotal += grid[i+1][j-1];    // below and left diagnoal
                    if (i + 1 < m && j + 1 < n)     mineTotal += grid[i+1][j+1];    // below and right diagnoal

                    System.out.print(mineTotal + "  ");
                }
                else
                    System.out.print("*  ");
            }

            System.out.println();
        }
    }
}

