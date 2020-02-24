import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * created:    2020/02/24
 * <p>
 * compilation:  javac MineSweeperGame.java
 * <p>
 * execution:    java MineSweeperGame
 * <p>
 * Minesweeper is a 1960s era video game played on an m-by-n grid of cells. The goal is to deduce
 * which cells contain hidden mines using clues about the number of mines in neighboring cells.
 * <p>
 * Left-click to reveal a cell. Right-click to plant or remove a flag for marking a suspected mine.
 * You win if all the cells not containing mines are revealed. You lose if you reveal a cell
 * containing a mine.
 *
 * @author Xiaoyu Zhang
 */

@SuppressWarnings("serial")
public class MineSweeperGame extends JFrame {
    // constants for the game properties
    public static final int ROWS = 10;
    public static final int COLS = 10;


    // cell width and height, in pixels
    public static final int CELL_SIZE = 60;

    // game board width/height
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS;
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS;

    // constants for UI control (sizes, colors and fonts)
    public static final Color BGCOLOR_NOT_REVEALED = Color.GREEN;
    public static final Color FGCOLOR_NOT_REVEALED = Color.RED;
    public static final Color BGCOLOR_REVEALED = Color.DARK_GRAY;
    public static final Color FGCOLOR_REVEALED = Color.LIGHT_GRAY;
    public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);

    // mine cells for user interaction. Use one dimensional array to represent two dimensional grid
    private MineCell lblCells[] = new MineCell[ROWS * COLS];

    // number of mines in this game. Can vary to control the difficulty level.
    private int numMines;

    // number of mines in this game. Can use to check if win or not
    private int numFlags;

    public MineSweeperGame(int numMines) {
        this.numMines = numMines;

        Container cp = this.getContentPane();               // JFrame's content-pane
        cp.setLayout(new GridLayout(ROWS, COLS, 2, 2));     // in 10x10 GridLayout

        // allocate a common instance of listener as the MouseEvent listener for all the JLabels
        CellMouseListener listener = new CellMouseListener();

        // construct 10x10 JLabel and add to the content-pane
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int pos = row * COLS + col;
                lblCells[pos] = new MineCell(row, col);
                cp.add(lblCells[pos]);

                // add MouseEvent listener to process the left/right mouse-click
                lblCells[pos].addMouseListener(listener);
            }
        }

        // set the size of the content-pane and pack all the components under this container.
        cp.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        pack();

        // handle window-close button.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Minesweeper");

        // show it
        setVisible(true);

        // initialize for a new game
        initGame();
    }

    /**
     * initialize and re-initialize a new game
     */
    private void initGame() {
        this.numFlags = 0;

        // reset cells, mines, and flags
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                // Set all cells to un-revealed
                int pos = row * COLS + col;
                lblCells[pos].setEnabled(true);     // enable label
                lblCells[pos].setForeground(FGCOLOR_NOT_REVEALED);
                lblCells[pos].setBackground(BGCOLOR_NOT_REVEALED);
                lblCells[pos].setFont(FONT_NUMBERS);
                lblCells[pos].setText("");          // display blank
                lblCells[pos].setHasMine(false);    // clear all the mines
                lblCells[pos].setHasFlag(false);    // clear all the flags
            }
        }

        // randomly generated mine grid for each game.
        initGrid();
    }

    /**
     * randomly place mines onto the grid using shufflfing algorithm, and calculate the adjacent
     * mines for each cell that is not a mine.
     */
    private void initGrid() {
        int size = ROWS * COLS;
        int[] indices = new int[size];

        // randomly place mines onto the grid
        for (int i = 0; i < size; i++)
            indices[i] = i;

        shuffle(indices);

        for (int i = 0; i < this.numMines; i++)
            this.lblCells[indices[i]].setHasMine(true);

        // calculate the adjacent mines
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                MineCell curr = this.lblCells[i * COLS + j];
                if (!curr.isHasMine())
                    curr.setAdjMines(countMines(i, j));
            }
        }
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
        for (int i = Math.max(x - 1, 0); i <= Math.min(x + 1, ROWS - 1); i++) {
            for (int j = Math.max(y - 1, 0); j <= Math.min(y + 1, COLS - 1); j++) {
                if (this.lblCells[i * COLS + j].isHasMine())
                    adjMines++;
            }
        }

        return adjMines;
    }

    /**
     * helper function. swaps array elements i and j
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
     * helper function. take as input an array of integers and rearrange them in random order
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
     * the entry method
     *
     * @param args
     */
    public static void main(String[] args) {
        MineSweeperGame msg = new MineSweeperGame(16);
    }

    // define the Listener inner Class used for all cells' MouseEvent listener
    private class CellMouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            // determine the (row, col) of the Minecell that triggered the event
            int rowSelected = -1;
            int colSelected = -1;

            // get the source object that fired the Event
            MineCell source = (MineCell) e.getSource();

            // scan all rows and columns, and match with the source object
            boolean found = false;
            for (int row = 0; row < ROWS && !found; ++row) {
                for (int col = 0; col < COLS && !found; ++col) {
                    if (source.equals(lblCells[row * COLS + col])) {
                        rowSelected = row;
                        colSelected = col;
                        found = true;   // break both inner and outer loops
                    }
                }
            }

            // left click to reveal a cell; right-click to plant orremove the flag.
            if (e.getButton() == MouseEvent.BUTTON1) {          // left-button clicked
                // if you hit a mine, game over;
                // otherwise, reveal the cell and display the number of surrounding mines
                if (source.isHasMine())
                    JOptionPane.showMessageDialog(null, "Game Over!");
                else {
                    source.setRevealed(true);
                    source.setForeground(FGCOLOR_REVEALED);
                    source.setBackground(BGCOLOR_REVEALED);
                    source.setText(source.getAdjMines() + "");  // display adjacent mines
                }
            }
            else if (e.getButton() == MouseEvent.BUTTON3) {     // right-button clicked
                // if the location is flagged, remove the flag;
                // otherwise, plant a flag.
                if (source.isHasFlag()) {
                    source.setHasFlag(false);
                    source.setText("");  // display adjacent mines
                    numFlags--;
                }
                else {
                    source.setHasFlag(true);
                    source.setText("X");  // display adjacent mines
                    numFlags++;
                }
            }

            // check if the player has won, after revealing this cell.
            if (numFlags == numMines)
                JOptionPane.showMessageDialog(null, "Congratualtions, you won!");
        }
    }
}
