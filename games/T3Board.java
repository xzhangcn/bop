import java.awt.Color;
import java.awt.Graphics;

/**
 * created:    2020/03/04
 * <p>
 * compilation:  javac T3Board.java
 * <p>
 * The {@code T3Board} class models the board of the game board for Tic-tac-toe.
 *
 * @author Xiaoyu Zhang
 */

public class T3Board {

    public T3Cell[][] cells;

    public T3Board() {
        cells = new T3Cell[T3Setting.ROWS][T3Setting.COLS];     // allocate the array
        for (int row = 0; row < T3Setting.ROWS; ++row) {
            for (int col = 0; col < T3Setting.COLS; ++col) {
                cells[row][col] = new T3Cell(row, col);             // allocate element of array
            }
        }
    }

    /**
     * Initialize (or re-initialize) the game board
     */
    public void init() {
        for (int row = 0; row < T3Setting.ROWS; ++row) {
            for (int col = 0; col < T3Setting.COLS; ++col) {
                cells[row][col].clear();
            }
        }
    }

    /**
     * @return true if it is a draw (i.e., no more EMPTY cell), otherwise false.
     */
    public boolean isDraw() {
        for (int row = 0; row < T3Setting.ROWS; ++row) {
            for (int col = 0; col < T3Setting.COLS; ++col) {

                // an empty seed found, not a draw, exit
                if (cells[row][col].getValue() == T3Setting.Seed.EMPTY)
                    return false;
            }
        }
        return true;    // no empty cell, it's a draw
    }


    /**
     * Return true if the player with "seed" has won after placing at (seedRow, seedCol)
     */
    public boolean hasWon(T3Setting.Seed seed, int seedRow, int seedCol) {
        return (cells[seedRow][0].getValue() == seed   // 3-in-the-row
                && cells[seedRow][1].getValue() == seed
                && cells[seedRow][2].getValue() == seed
                || cells[0][seedCol].getValue() == seed // 3-in-the-column
                && cells[1][seedCol].getValue() == seed
                && cells[2][seedCol].getValue() == seed
                || seedRow == seedCol              // 3-in-the-diagonal
                && cells[0][0].getValue() == seed
                && cells[1][1].getValue() == seed
                && cells[2][2].getValue() == seed
                || seedRow + seedCol == 2          // 3-in-the-opposite-diagonal
                && cells[0][2].getValue() == seed
                && cells[1][1].getValue() == seed
                && cells[2][0].getValue() == seed);
    }

    /**
     * Paint itself on the graphics canvas, given the Graphics context
     *
     * @param g graphics context
     */
    public void paint(Graphics g) {
        // Draw the grid-lines
        g.setColor(Color.GRAY);
        for (int row = 1; row < T3Setting.ROWS; ++row) {
            g.fillRoundRect(0, T3Setting.CELL_SIZE * row - T3Setting.GRID_WIDHT_HALF,
                            T3Setting.CANVAS_WIDTH - 1, T3Setting.GRID_WIDTH,
                            GameMain.GRID_WIDTH, T3Setting.GRID_WIDTH);
        }
        for (int col = 1; col < T3Setting.COLS; ++col) {
            g.fillRoundRect(T3Setting.CELL_SIZE * col - T3Setting.GRID_WIDHT_HALF, 0,
                            T3Setting.GRID_WIDTH, T3Setting.CANVAS_HEIGHT - 1,
                            T3Setting.GRID_WIDTH, T3Setting.GRID_WIDTH);
        }

        // Draw all the cells
        for (int row = 0; row < T3Setting.ROWS; ++row) {
            for (int col = 0; col < T3Setting.COLS; ++col) {
                cells[row][col].paint(g);
            }
        }
    }
}
