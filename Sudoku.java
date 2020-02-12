import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

/******************************************************************************
 *  author:     Xiaoyu Zhang
 *  created:    2020/02/11
 *  Compilation:  javac-algs4 Sudoku.java
 *  Execution:    java-algs4 Sudoku
 *  Dependencies: StdOut.java
 *
 *  Sudoku is a logic-based, combinatorial number-placement puzzle.
 *  The objective is to fill a 9×9 grid with digits so that each column,
 *  each row, and each of the nine 3×3 subgrids that compose the grid
 *  contain all of the digits from 1 to 9.
 *
 *  The puzzle setter provides a partially completed grid,
 *  which for a well-posed puzzle has a single solution.
 *
 ******************************************************************************/

public class Sudoku {
    private final short SIZE = 9;
    private Cell[][] grid;

    public Sudoku() {
        grid = new Cell[SIZE][SIZE];
    }

    // fill in the central subgrid with numbers 1 to 9 randomly
    private void fillCentralSubGrid() {
        int[] a = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        StdRandom.shuffle(a);

        /*
        for (int i = 0; i < 9; i++)
            StdOut.printf("%d \t", a[i]);
        StdOut.println();
        */

        int subgridIndex = 5;
        int k = 0;
        for (int i = 3; i <= 5; i++)
            for (int j = 3; j <= 5; j++)
                grid[i][j] = new Cell(i, j, a[k++], subgridIndex);

        /*
        for (int i = 3; i <= 5; i++) {
            for (int j = 3; j <= 5; j++)
                StdOut.printf("%s \t", grid[i][j]);
            StdOut.println();
        }
        */
    }

    // fill in the sub grid left to the central one
    private void fillLeftToCentralSubGrid() {
        int subGridIndex = 4;

        for (int j = 0; j <= 2; j++)
            grid[3][j] = new Cell(3, j, grid[5][j + 3].value, subGridIndex);

        for (int j = 0; j <= 2; j++)
            grid[4][j] = new Cell(4, j, grid[3][j + 3].value, subGridIndex);

        for (int j = 0; j <= 2; j++)
            grid[5][j] = new Cell(5, j, grid[4][j + 3].value, subGridIndex);
    }

    // fill in the sub grid right to the central one
    private void fillRightToCentralSubGrid() {
        int subGridIndex = 6;

        for (int j = 6; j <= 8; j++)
            grid[3][j] = new Cell(3, j, grid[4][j - 3].value, subGridIndex);

        for (int j = 6; j <= 8; j++)
            grid[4][j] = new Cell(4, j, grid[5][j - 3].value, subGridIndex);

        for (int j = 6; j <= 8; j++)
            grid[5][j] = new Cell(5, j, grid[3][j - 3].value, subGridIndex);
    }

    // fill in the sub grid above to the central one
    private void fillAboveToCentralSubGrid() {
        int subGridIndex = 2;

        for (int i = 0; i <= 2; i++)
            grid[i][3] = new Cell(i, 3, grid[i + 3][5].value, subGridIndex);

        for (int i = 0; i <= 2; i++)
            grid[i][4] = new Cell(i, 4, grid[i + 3][3].value, subGridIndex);

        for (int i = 0; i <= 2; i++)
            grid[i][5] = new Cell(i, 5, grid[i + 3][4].value, subGridIndex);
    }

    // fill in the sub grid below to the central one
    private void fillBelowToCentralSubGrid() {
        int subGridIndex = 6;

        for (int i = 6; i <= 8; i++)
            grid[i][3] = new Cell(i, 3, grid[i - 3][4].value, subGridIndex);

        for (int i = 6; i <= 8; i++)
            grid[i][4] = new Cell(i, 4, grid[i - 3][5].value, subGridIndex);

        for (int i = 6; i <= 8; i++)
            grid[i][5] = new Cell(i, 5, grid[i - 3][3].value, subGridIndex);
    }

    // fill in the sub grid above to the grid 4
    private void fillAboveTo4thSubGrid() {
        int subGridIndex = 1;

        for (int i = 0; i <= 2; i++)
            grid[i][0] = new Cell(i, 0, grid[i + 3][2].value, subGridIndex);

        for (int i = 0; i <= 2; i++)
            grid[i][1] = new Cell(i, 1, grid[i + 3][0].value, subGridIndex);

        for (int i = 0; i <= 2; i++)
            grid[i][2] = new Cell(i, 2, grid[i + 3][1].value, subGridIndex);
    }

    // fill in the sub grid below the grid 4
    private void fillBelowTo4thSubGrid() {
        int subGridIndex = 7;

        for (int i = 6; i <= 8; i++)
            grid[i][0] = new Cell(i, 0, grid[i - 3][1].value, subGridIndex);

        for (int i = 6; i <= 8; i++)
            grid[i][1] = new Cell(i, 1, grid[i - 3][2].value, subGridIndex);

        for (int i = 6; i <= 8; i++)
            grid[i][2] = new Cell(i, 2, grid[i - 3][0].value, subGridIndex);
    }

    // fill in the sub grid above to the grid 6
    private void fillAboveTo6thSubGrid() {
        int subGridIndex = 3;

        for (int i = 0; i <= 2; i++)
            grid[i][6] = new Cell(i, 6, grid[i + 3][8].value, subGridIndex);

        for (int i = 0; i <= 2; i++)
            grid[i][7] = new Cell(i, 7, grid[i + 3][6].value, subGridIndex);

        for (int i = 0; i <= 2; i++)
            grid[i][8] = new Cell(i, 8, grid[i + 3][7].value, subGridIndex);
    }

    // fill in the sub grid below the grid 6
    private void fillBelowTo6thSubGrid() {
        int subGridIndex = 9;

        for (int i = 6; i <= 8; i++)
            grid[i][6] = new Cell(i, 6, grid[i - 3][7].value, subGridIndex);

        for (int i = 6; i <= 8; i++)
            grid[i][7] = new Cell(i, 7, grid[i - 3][8].value, subGridIndex);

        for (int i = 6; i <= 8; i++)
            grid[i][8] = new Cell(i, 8, grid[i - 3][6].value, subGridIndex);
    }

    private void generateGame() {
        // fill in the central subgrid with numbers 1 to 9 randomly
        fillCentralSubGrid();
        fillLeftToCentralSubGrid();
        fillRightToCentralSubGrid();

        fillAboveToCentralSubGrid();
        fillBelowToCentralSubGrid();

        fillAboveTo4thSubGrid();
        fillBelowTo4thSubGrid();

        fillAboveTo6thSubGrid();
        fillBelowTo6thSubGrid();
    }

    public void drawBoard() {
        generateGame();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                StdOut.printf("%s \t", grid[i][j].value);
            }
            StdOut.println();
        }
    }

    private class Cell {
        public int row;
        public int col;
        public int value;
        public int subgrid;  // 3x3 subgrid number, ranging from 1 to 9

        public Cell() {
            this.row = 0;
            this.col = 0;
            this.value = 0;
            this.subgrid = 0;
        }

        public Cell(int row, int col, int value, int subgrid) {
            this.row = row;
            this.col = col;
            this.value = value;
            this.subgrid = subgrid;
        }

        @Override
        public String toString() {
            return "(" + this.row + "," + this.col + "; " + this.value + "; " + this.subgrid + ")";
        }
    }

    public static void main(String[] args) {
        Sudoku sdk = new Sudoku();
        sdk.drawBoard();
    }
}
