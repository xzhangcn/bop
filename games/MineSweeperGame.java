import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
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
    private MineCell btnCells[] = new MineCell[ROWS * COLS];

    // number of mines in this game. Can vary to control the difficulty level.
    private int numMines;

    // number of mines flagged in this game. Can use to check if win or not
    private int numFlags;

    private JTextField statusTF;

    public MineSweeperGame(int numMines) {
        this.numMines = numMines;

        setJMenuBar(createMenuBar());

        Container cp = this.getContentPane();               // JFrame's content-pane
        cp.setLayout(new BorderLayout());

        JPanel minePanel = new JPanel(new GridLayout(ROWS, COLS, 2, 2));
        cp.add(minePanel, BorderLayout.CENTER);

        // add the panel for the status bar showing the total and remaining mines
        JPanel statusPanel = new JPanel(new BorderLayout());
        statusTF = new JTextField(this.numMines + " mines in total; ");
        statusTF.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 15));
        statusTF.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusPanel.add(statusTF);
        cp.add(statusPanel, BorderLayout.SOUTH);

        // "this" JFrame sets its content-pane to a JPanel directly
        // setContentPane(cp);

        // allocate a common instance of listener as the MouseEvent listener for all the JLabels
        CellMouseListener listener = new CellMouseListener();

        // construct 10x10 JLabel and add to the content-pane
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                int pos = row * COLS + col;
                btnCells[pos] = new MineCell(row, col);
                minePanel.add(btnCells[pos]);

                // add MouseEvent listener to process the left/right mouse-click
                btnCells[pos].addMouseListener(listener);
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
                btnCells[pos].setEnabled(true);     // enable label
                btnCells[pos].setForeground(FGCOLOR_NOT_REVEALED);
                btnCells[pos].setBackground(BGCOLOR_NOT_REVEALED);
                btnCells[pos].setFont(FONT_NUMBERS);
                btnCells[pos].setText("");          // display blank
                btnCells[pos].setHasMine(false);    // clear all the mines
                btnCells[pos].setHasFlag(false);    // clear all the flags
                btnCells[pos].setRevealed(false);
            }
        }

        // randomly generated mine grid for each game.
        initGrid();

        this.statusTF.setText(numMines + " mines in total; ");
    }

    /**
     * randomly place mines onto the grid using shufflfing algorithm, and calculate the adjacent
     * mines for each cell that is not a mine.
     */
    private void initGrid() {
        System.out.println("\ninit the grid");

        int size = ROWS * COLS;
        int[] indices = new int[size];

        // randomly place mines onto the grid
        for (int i = 0; i < size; i++)
            indices[i] = i;

        shuffle(indices);

        for (int i = 0; i < this.numMines; i++)
            this.btnCells[indices[i]].setHasMine(true);

        // calculate the adjacent mines
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                MineCell curr = this.btnCells[i * COLS + j];
                if (!curr.isHasMine()) {
                    int count = countMines(i, j);
                    curr.setAdjMines(count);
                    System.out.printf("%d\t", count);
                }
                else System.out.print("\t");
            }
            System.out.println();
        }

    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        MenuItemListener menuItemListener = new MenuItemListener();

        JMenu menu = new JMenu("Game");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);

        JMenuItem menuItemStart = new JMenuItem("Start", KeyEvent.VK_S);
        // menuItemStart.setMnemonic(KeyEvent.VK_S);
        menu.add(menuItemStart);
        menuItemStart.addActionListener(menuItemListener);

        JMenuItem menuItemReset = new JMenuItem("Reset", KeyEvent.VK_R);
        // menuItemReset.setMnemonic(KeyEvent.VK_R);
        menu.add(menuItemReset);
        menuItemReset.addActionListener(menuItemListener);

        return menuBar;
    }

    class MenuItemListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JMenuItem source = (JMenuItem) (e.getSource());
            switch (source.getText()) {
                case "Start":
                    // JOptionPane.showMessageDialog(null, "Start a new game");
                    initGame();
                    break;
                case "Reset":
                    // JOptionPane.showMessageDialog(null, "Reset the game");
                    initGame();
                    break;
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
                if (this.btnCells[i * COLS + j].isHasMine())
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
     * the entry method.
     * <p>
     * Note: javax.swing.SwingUtilities.invokeLater() is a cover for java.awt.EventQueue.invokeLater()
     * (which is used in the NetBeans' Visual GUI Builder).
     * <p>
     * At times, for example in game programming, the constructor or the main() may contains non-GUI
     * codes. Hence, it is a common practice to create a dedicated method called initComponents()
     * (used in NetBeans visual GUI builder) or createAndShowGUI() (used in Swing tutorial) to
     * handle all the GUI codes (and another method called initGame() to handle initialization of
     * the game's objects). This GUI init method shall be run in the event-dispatching thread.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {

        // run GUI codes in Event-Dispatching thread for thread-safety
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MineSweeperGame(16);    // let the constructor do the job
            }
        });
    }

    /**
     * @param x horizontal index
     * @param y vertical index
     */
    private void updateGrid(int x, int y) {
        int pos = x * COLS + y;
        MineCell curr = this.btnCells[pos];

        // if you hit a mine, game over;
        // otherwise, reveal the cell and display the number of surrounding mines
        if (curr.isHasMine()) {
            JOptionPane.showMessageDialog(null, "Game Over!");
            initGame();
            return;
        }

        int count = curr.getAdjMines();
        if (count > 0) {
            curr.setRevealed(true);
            curr.setForeground(FGCOLOR_REVEALED);
            curr.setBackground(BGCOLOR_REVEALED);
            curr.setText(count + "");  // display adjacent mines
        }
        else {
            System.out.println("Recursively update around");
            updateAround(x, y);
        }
    }

    /**
     * Recursively update the mine grid when adjacent mine is 0
     *
     * @param x horizontal index
     * @param y vertical index
     */
    private void updateAround(int x, int y) {
        int pos = x * COLS + y;
        MineCell curr = this.btnCells[pos];
        int count = curr.getAdjMines();

        if (!curr.isRevealed() && !curr.isHasMine()) {
            curr.setRevealed(true);
            curr.setForeground(FGCOLOR_REVEALED);
            curr.setBackground(BGCOLOR_REVEALED);

            if (count == 0) {
                curr.setText("");         // display empty text
                for (int i = Math.max(x - 1, 0); i <= Math.min(i + 1, ROWS - 1); i++) {
                    for (int j = Math.max(y - 1, 0); j <= Math.min(j + 1, COLS - 1); j++)
                        updateAround(i, j);
                }
            }
            else {
                curr.setText(count + "");  // display adjacent mines
            }
        }
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
            for (int row = 0; row < ROWS && !found; row++) {
                for (int col = 0; col < COLS && !found; col++) {
                    if (source.equals(btnCells[row * COLS + col])) {
                        rowSelected = row;
                        colSelected = col;
                        found = true;   // break both inner and outer loops
                    }
                }
            }

            // left click to reveal a cell; right-click to plant orremove the flag.
            if (e.getButton() == MouseEvent.BUTTON1) {          // left-button clicked
                updateGrid(rowSelected, colSelected);
            }
            else if (e.getButton() == MouseEvent.BUTTON3) {     // right-button clicked
                // if the location is flagged, remove the flag;
                // otherwise, plant a flag.
                if (source.isHasFlag()) {
                    source.setHasFlag(false);
                    source.setText("");
                    numFlags--;

                    StringBuilder statusText = new StringBuilder(numMines + " mines in total; ");
                    if (numFlags == 1)
                        statusText.append(numFlags + " mine being marked");
                    else
                        statusText.append(numFlags + " mines being marked");
                    statusTF.setText(statusText.toString());
                }
                else {
                    source.setHasFlag(true);
                    source.setText("X");
                    numFlags++;

                    StringBuilder statusText = new StringBuilder(numMines + " mines in total; ");
                    if (numFlags == 1)
                        statusText.append(numFlags + " mine being marked");
                    else
                        statusText.append(numFlags + " mines being marked");
                    statusTF.setText(statusText.toString());
                }
            }

            // check if the player has won, after revealing this cell.
            if (numFlags == numMines)
                JOptionPane.showMessageDialog(null, "Congratualtions, you won!");
        }
    }
}
