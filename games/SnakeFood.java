import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * created:    2020/03/10
 * <p>
 * The {@code SnakeFood} is a food item that the snake can eat. It is placed randomly in the pit.
 *
 * @author Xiaoyu Zhang
 */

public class SnakeFood {

    private int x, y;                       // current food location (x, y) in cells
    private Color color = Color.BLUE;       // color for display
    private Random rand = new Random();     // For randomly placing the food

    public SnakeFood() {
        // place outside the pit, so that it will not be "displayed".
        x = -1;
        y = -1;
    }

    /**
     * Regenerate a food item. Randomly place inside the pit (slightly off the edge).
     */
    public void regenerate() {
        x = rand.nextInt(SnakeSetting.COLUMNS - 4) + 2;
        y = rand.nextInt(SnakeSetting.ROWS - 4) + 2;
    }

    /**
     * @return x coordinate of the cell that contains this food item.
     */
    public int getX() {
        return x;
    }

    /**
     * @return y coordinate of the cell that contains this food item.
     */
    public int getY() {
        return y;
    }


    /**
     * Draw itself
     *
     * @param g Graphics context
     */
    public void draw(Graphics g) {
        g.setColor(color);
        g.fill3DRect(x * SnakeSetting.CELL_SIZE, y * SnakeSetting.CELL_SIZE,
                     SnakeSetting.CELL_SIZE, SnakeSetting.CELL_SIZE, true);
    }

}
