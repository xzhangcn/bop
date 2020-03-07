import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * created:    2020/03/04
 * <p>
 * compilation:  javac T3Cell.java
 * <p>
 * The {@code T3Cell} class models each individual cell of the game board for Tic-tac-toe.
 *
 * @author Xiaoyu Zhang
 */

public class T3Cell {

    private int row;
    private int col;
    private T3Setting.Seed value;

    public T3Cell(int row, int col) {
        this.row = row;
        this.col = col;
        clear();
    }

    public void clear() {
        this.value = T3Setting.Seed.EMPTY;
    }

    public T3Setting.Seed getValue() {
        return value;
    }

    public void setValue(T3Setting.Seed value) {
        this.value = value;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public String toString() {
        String res = "";
        switch (this.value) {
            case EMPTY:
                res = "";
                break;
            case CROSS:
                res = "X";
                break;
            case NOUGHT:
                res = "O";
                break;
        }

        return res;
    }

    /**
     * Paint itself on the graphics canvas, given the Graphics context
     *
     * @param g graphics context
     */
    public void paint(Graphics g) {
        // Use Graphics2D which allows us to set the pen's stroke
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(T3Setting.SYMBOL_STROKE_WIDTH,
                                      BasicStroke.CAP_ROUND,
                                      BasicStroke.JOIN_ROUND));

        // Draw the Seed if it is not empty
        int x1 = col * T3Setting.CELL_SIZE + T3Setting.CELL_PADDING;
        int y1 = row * T3Setting.CELL_SIZE + T3Setting.CELL_PADDING;

        if (this.value == T3Setting.Seed.CROSS) {
            g2d.setColor(Color.RED);
            int x2 = (col + 1) * T3Setting.CELL_SIZE - T3Setting.CELL_PADDING;
            int y2 = (row + 1) * T3Setting.CELL_SIZE - T3Setting.CELL_PADDING;
            g2d.drawLine(x1, y1, x2, y2);
            g2d.drawLine(x2, y1, x1, y2);
        }
        else if (this.value == T3Setting.Seed.NOUGHT) {
            g2d.setColor(Color.BLUE);
            g2d.drawOval(x1, y1, T3Setting.SYMBOL_SIZE, T3Setting.SYMBOL_SIZE);
        }
    }
}
