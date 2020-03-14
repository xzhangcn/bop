/**
 * created:    2020/03/11
 * <p>
 * The {@code SnakeSetting} is a place to put all the game common settings.
 * <p>
 * Note: originally these constants are in the {@code SnakeGame} class. But {@code Snake} needs to
 * use them, at the mean time, {@code SnakeGame} need the {@code Snake} class to compile. Is it like
 * a situation of Deadlock? So I have to put these constants in a separate file. There should be
 * better way to do it.
 *
 * @author Xiaoyu Zhang
 */

public class SnakeSetting {
    static final int ROWS = 40;      // number of rows (in cells)
    static final int COLUMNS = 40;   // number of columns (in cells)
    static final int CELL_SIZE = 15; // Size of a cell (in pixels)
}
