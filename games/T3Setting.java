/**
 * created:    2020/03/04
 * <p>
 * compilation:  javac T3Setting.java
 * <p>
 * The {@code T3Setting} class bundles all the constants used for Tic-tac-toe.
 *
 * @author Xiaoyu Zhang
 */

public class T3Setting {

    /**
     * Not instantiate this class
     */
    private T3Setting() {
    }

    // constants for the game board
    public static final int ROWS = 3;                           // ROWS by COLS cells
    public static final int COLS = 3;
    public static final String TITLE = "Tic Tac Toe";

    // constants for the various dimensions used for graphics drawing
    public static final int CELL_SIZE = 100;                    // cell width and height (square)
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;    // the drawing canvas
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;

    public static final int GRID_WIDTH = 8;                     // Grid-line's width
    public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;   // Grid-line's half-width

    // Symbols (cross/nought) are displayed inside a cell, with padding from border
    public static final int CELL_PADDING = CELL_SIZE / 6;
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2;
    public static final int SYMBOL_STROKE_WIDTH = 8;            // pen's stroke width

    public enum Seed {EMPTY, CROSS, NOUGHT}
}
