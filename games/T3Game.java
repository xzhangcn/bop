import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * created: 2020/03/08
 * <p>
 * The {@code T3Game} is the graphical implementation for Tic-tac-toe.
 *
 * @author Xiaoyu Zhang
 */
@SuppressWarnings("serial")
public class T3Game extends JPanel {
    private T3Board board;            // the game board
    private T3Setting.GameState currentState;     // the current state of the game
    private T3Setting.Seed currentPlayer;         // the current player
    private JLabel statusBar;           // for displaying status message

    private AIPlayer player;

    /**
     * Setup the UI and game components
     */
    public T3Game() {
        board = new T3Board();   // allocate the game-board
        player = new AIPlayerMinMax(board);

        // Setup the status bar (JLabel) to display status message
        statusBar = new JLabel("         ");
        statusBar.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 14));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.LIGHT_GRAY);

        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.PAGE_END); // same as SOUTH
        setPreferredSize(new Dimension(T3Setting.CANVAS_WIDTH, T3Setting.CANVAS_HEIGHT + 30));
        // account for statusBar in height
        initGame();  // Initialize the game variables

        // This JPanel fires MouseEvent
        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                int[] pos = player.move();
                int rowSelected = pos[0];
                int colSelected = pos[1];

                playGame(rowSelected, colSelected);

                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {  // mouse-pressed handler

                int rowSelected, colSelected;
                int mouseX = e.getX();
                int mouseY = e.getY();
                // Get the row and column clicked
                rowSelected = mouseY / T3Setting.CELL_SIZE;
                colSelected = mouseX / T3Setting.CELL_SIZE;

                playGame(rowSelected, colSelected);
                // Refresh the drawing canvas
                repaint();  // Call-back paintComponent().
            }
        });
    }

    /**
     * Initialize the game-board contents and the current-state
     */
    public void initGame() {
        for (int row = 0; row < T3Setting.ROWS; ++row) {
            for (int col = 0; col < T3Setting.COLS; ++col) {
                board.cells[row][col].setValue(T3Setting.Seed.EMPTY); // all cells empty
            }
        }
        currentState = T3Setting.GameState.PLAYING;  // ready to play
        currentPlayer = T3Setting.Seed.CROSS;        // cross plays first
        player.setSeed(T3Setting.Seed.NOUGHT);
    }

    /**
     * Update the currentState after the player with "theSeed" has placed on (row, col)
     *
     * @param theSeed the value at specific board position
     * @param row     the horizontal index
     * @param col     the vertical index
     */
    public void updateGame(T3Setting.Seed theSeed, int row, int col) {
        if (board.hasWon(theSeed, row, col)) {  // check for win
            currentState = (theSeed == T3Setting.Seed.CROSS) ? T3Setting.GameState.CROSS_WON :
                           T3Setting.GameState.NOUGHT_WON;
        }
        else if (board.isDraw()) {  // check for draw
            currentState = T3Setting.GameState.DRAW;
        }
        // Otherwise, no change to current state (PLAYING).
    }

    /**
     * main routine to play the game
     *
     * @param row horizontal index
     * @param col vertical index
     */
    public void playGame(int row, int col) {
        if (currentState == T3Setting.GameState.PLAYING) {
            if (row >= 0 && row < T3Setting.ROWS && col >= 0 && col < T3Setting.COLS
                    && board.cells[row][col].getValue() == T3Setting.Seed.EMPTY) {
                board.cells[row][col].setValue(currentPlayer);  // move
                updateGame(currentPlayer, row, col);            // update currentState

                // Switch player
                currentPlayer = (currentPlayer == T3Setting.Seed.CROSS) ? T3Setting.Seed.NOUGHT :
                                T3Setting.Seed.CROSS;
            }
        }
        else {              // game over
            initGame();     // restart the game
        }
    }

    /**
     * Custom painting codes on this JPanel
     *
     * @param g Graphics context
     */
    @Override
    public void paintComponent(Graphics g) {  // invoke via repaint()
        super.paintComponent(g);        // fill background
        setBackground(Color.WHITE);     // set its background color

        board.paint(g);  // ask the game board to paint itself

        // Print status-bar message
        if (currentState == T3Setting.GameState.PLAYING) {
            statusBar.setForeground(Color.BLACK);
            if (currentPlayer == T3Setting.Seed.CROSS) {
                statusBar.setText("X's Turn");
            }
            else {
                statusBar.setText("O's Turn");
            }
        }
        else if (currentState == T3Setting.GameState.DRAW) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        }
        else if (currentState == T3Setting.GameState.CROSS_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'X' Won! Click to play again.");
        }
        else if (currentState == T3Setting.GameState.NOUGHT_WON) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    public static void main(String[] args) {
        // Run GUI construction codes in Event-Dispatching thread for thread safety
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame(T3Setting.TITLE);
                // Set the content-pane of the JFrame to an instance of main JPanel
                frame.setContentPane(new T3Game());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null); // center the application window
                frame.setVisible(true);            // show it
            }
        });
    }
}
