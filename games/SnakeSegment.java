import java.awt.Graphics;

/**
 * created:    2020/03/10
 * <p>
 * The {@code SnakeSegment} represents one horizontal or vertical segment of a snake. The "head" of
 * this segment is at (headX, headY). The segment is drawn starting from the "head" and procedding
 * "length" cells in "direction", until it reaches the "tail".
 *
 * @author Xiaoyu Zhang
 */

public class SnakeSegment {

    private int headX, headY;           // The position of the head of this segment
    private int length;                 // length of this segment
    private Snake.Direction direction;  // direction of this segment

    // Construct a new snake segment at given （headX, headY), length and direction
    public SnakeSegment(int headX, int headY, int length, Snake.Direction direction) {
        this.headX = headX;
        this.headY = headY;
        this.length = length;
        this.direction = direction;
    }

    /**
     * Grow by adding one cell to the head of this snake segment
     */
    public void grow() {
        length++;

        // Need to adjust headX and headY
        switch (direction) {
            case LEFT:
                headX--;
                break;
            case RIGHT:
                headX++;
                break;
            case UP:
                headY--;
                break;
            case DOWN:
                headY++;
                break;
        }
    }

    /**
     * Shrink by removing one cell from the tail of the snake segment
     */
    public void shrink() {
        length--;   // no need to change headX and headY
    }

    /**
     * @return the X coordinate of the cell that contains the head of this snake segment
     */
    public int getHeadX() {
        return headX;
    }

    /**
     * @return the Y coordinate of the cell that contains the head of this snake segment
     */
    public int getHeadY() {
        return headY;
    }

    /**
     * @return the length in cells of this snake segment
     */
    public int getLength() {
        return length;
    }

    /**
     * @return the X coordinate of the cell that contains the tail of this snake segment
     */
    private int getTailX() {
        switch (direction) {
            case LEFT:
                return (headX + length - 1);
            case RIGHT:
                return (headX - length + 1);
            default:    // UP or DOWN
                return headX;
        }
    }

    /**
     * @return the Y coordinate of the cell that contains the tail of this snake segment
     */
    private int getTailY() {
        switch (direction) {
            case DOWN:
                return (headY - length + 1);
            case UP:
                return (headY + length - 1);
            default:    // UP or DOWN
                return headY;
        }
    }

    /**
     * @param x horizontal coordinate of a given cell
     * @param y vertical coordinate of a given cell
     * @return true if the snake segment contains the given cell. Used for collision detection.
     */
    public boolean contains(int x, int y) {
        switch (direction) {
            case LEFT:
                return ((y == this.headY) && ((x >= this.headX) && (x <= getTailX())));
            case RIGHT:
                return ((y == this.headY) && ((x <= this.headX) && (x >= getTailX())));
            case UP:
                return ((x == this.headX) && ((y >= this.headY) && (y <= getTailY())));
            case DOWN:
                return ((x == this.headX) && ((y <= this.headY) && (y >= getTailY())));
        }
        return false;
    }

    /**
     * Draw this snake segment
     *
     * @param g Graphics context
     */
    public void draw(Graphics g) {
        int x = headX;
        int y = headY;

        switch (direction) {
            case LEFT:
                for (int i = 0; i < length; ++i) {
                    g.fill3DRect(x * SnakeSetting.CELL_SIZE, y * SnakeSetting.CELL_SIZE,
                                 SnakeSetting.CELL_SIZE - 1, SnakeSetting.CELL_SIZE - 1, true);
                    ++x;
                }
                break;

            case RIGHT:
                for (int i = 0; i < length; ++i) {
                    g.fill3DRect(x * SnakeSetting.CELL_SIZE, y * SnakeSetting.CELL_SIZE,
                                 SnakeSetting.CELL_SIZE - 1, SnakeSetting.CELL_SIZE - 1, true);
                    --x;
                }
                break;

            case UP:
                for (int i = 0; i < length; ++i) {
                    g.fill3DRect(x * SnakeSetting.CELL_SIZE, y * SnakeSetting.CELL_SIZE,
                                 SnakeSetting.CELL_SIZE - 1, SnakeSetting.CELL_SIZE - 1, true);
                    ++y;
                }
                break;

            case DOWN:
                for (int i = 0; i < length; ++i) {
                    g.fill3DRect(x * SnakeSetting.CELL_SIZE, y * SnakeSetting.CELL_SIZE,
                                 SnakeSetting.CELL_SIZE - 1, SnakeSetting.CELL_SIZE - 1, true);
                    --y;
                }
                break;
        }
    }

    @Override
    public String toString() {
        return "SnakeSegment[head=(" + headX + "," + headY + "), tail=(" + getTailX() + ","
                + getTailY() + "), length=" + length + ", dir=" + direction + "]";
    }

}
