/******************************************************************************
 * created:    2020/02/22
 * Compilation:  javac-algs4 Minesweeper.java
 * Execution:    java-algs4 Minesweeper
 *
 * Minesweeper is a 1960s era video game played on an m-by-n grid of cells. The goal is to deduce
 * which cells contain hidden mines using clues about the number of mines in neighboring cells.
 *
 * @author Xiaoyu Zhang
 *
 ******************************************************************************/

public class Minesweeper {

    private int rows;           // number of rows
    private int cols;           // number of cols
    private int size;           // number of cells in the grid
    private int mines;          // number of mines
    private int[] grid;         // use one dimensional array to represent the mine grid

    private int[] indices;      // array to store the grid indices for shuffling purpose

    public Minesweeper(int rows, int cols, int mines) {
        this.rows = rows;
        this.cols = cols;
        this.mines = mines;

        this.size = this.rows * this.cols;
        grid = new int[this.size];
        indices = new int[this.size];

        /**
         * How to randomly lay mines elegantly? Use the card-dealing algorithm. Create an array of all
         * the coordinates in your grid, in order. ([0,0], [0,1] .. [0,max], [1,0] .. [max, max]). Then
         * "shuffle the deck" by iterating the list in order and swapping each element with a random
         * element. Then select the first x elements in the list and place mines in those locations.
         *
         * <p>
         * Here, use an additional array indices to facilitate the shuffling. Also, use one dimensional
         * array to represent two dimensinal one. Though space wise, it's not efficient.
         *
         */
        for (int i = 0; i < this.size; i++)
            indices[i] = i;

        shuffle(indices);
        // show(indices);

        for (int i = 0; i < this.mines; i++)
            grid[indices[i]] = 1;   // indicated by a value of 1, if a cell contains a mine
    }

    /**
     * Another way to place mines, but this random selection of indices will cause re-tring if the
     * picked coordinates(index) already have a mine.
     * <p>
     * Currently not used.
     */
    private void placeMines() {
        // generate the mines, uniformly at random
        for (int i = 0; i < this.mines; i++) {
            while (true) {
                int index = (int) (Math.random() * (this.rows * this.cols));
                if (this.grid[index] == 0) {
                    this.grid[index] = 1;
                    break;
                }
            }
        }
    }

    /**
     * swaps array elements i and j
     *
     * @param a array of integer
     * @param i index to be swapped with
     * @param j index to be swapped with
     */
    private void exch(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    /**
     * take as input an array of integers and rearrange them in random order
     *
     * @param a array of integers
     */
    private void shuffle(int[] a) {
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + (int) (Math.random() * (n - i));   // between i and n-1
            exch(a, i, r);
        }
    }

    /**
     * take as input an array of integers and print them out to standard output
     *
     * @param a aray of integers
     */
    private void show(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.printf("%d \t", a[i]);
        }
        System.out.println();
    }

    /**
     * count the number of mines adjacent to the position (x, y)
     *
     * @param x horizontal coordinate of the point
     * @param y vertical coordinate of the point
     * @return the number of mines adjacent
     */
    private int countMines(int x, int y) {
        int adjMines = 0;
        for (int i = Math.max(x - 1, 0); i <= Math.min(x + 1, this.rows - 1); i++) {
            for (int j = Math.max(y - 1, 0); j <= Math.min(y + 1, this.cols - 1); j++) {
                if (this.grid[i * this.cols + j] == 1)
                    adjMines++;
            }
        }

        return adjMines;
    }

    /**
     * A better way to show the grid with mines.
     */
    public void showGrid() {
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (this.grid[i * this.cols + j] == 1)
                    System.out.printf("%s  ", "*");
                else
                    System.out.printf("%d  ", countMines(i, j));
            }

            System.out.println();
        }
    }

    /**
     *
     */
    private void showMineGrid() {
        // print out the mine
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                int minesAround = 0;

                if (this.grid[i * this.cols + j] != 1) {
                    // above
                    if (i - 1 >= 0)
                        minesAround += this.grid[(i - 1) * this.cols + j];

                    // below
                    if (i + 1 < this.rows)
                        minesAround += this.grid[(i + 1) * this.cols + j];

                    // left
                    if (j - 1 >= 0)
                        minesAround += this.grid[i * this.cols + j - 1];

                    // right
                    if (j + 1 < this.cols)
                        minesAround += this.grid[i * this.cols + j + 1];

                    // above and left diagnoal
                    if (i - 1 >= 0 && j - 1 >= 0)
                        minesAround += this.grid[(i - 1) * this.cols + j - 1];

                    // above and right diagnoal
                    if (i - 1 >= 0 && j + 1 < this.cols)
                        minesAround += this.grid[(i - 1) * this.cols + j + 1];

                    // below and left diagnoal
                    if (i + 1 < this.rows && j - 1 >= 0)
                        minesAround += this.grid[(i + 1) * this.cols + j - 1];

                    // below and right diagnoal
                    if (i + 1 < this.rows && j + 1 < this.cols)
                        minesAround += this.grid[(i + 1) * this.cols + j + 1];

                    System.out.print(minesAround + "  ");
                }
                else
                    System.out.print("*  ");
            }

            System.out.println();
        }
    }

    public static void main(String[] args) {
        int m = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);

        Minesweeper ms = new Minesweeper(m, n, k);
        ms.showMineGrid();
        System.out.println();
        ms.showGrid();
    }
}
