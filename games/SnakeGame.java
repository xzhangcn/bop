import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * created:    2020/03/10
 * <p>
 * The {@code SnakeGame} is the main class for the snake game.
 *
 * @author Xiaoyu Zhang
 */
@SuppressWarnings("serial")
public class SnakeGame extends JPanel {

    static final String TITLE = "SNAKE";

    // width and height of the game screen
    public static final int CANVAS_WIDTH = SnakeSetting.COLUMNS * SnakeSetting.CELL_SIZE;
    public static final int CANVAS_HEIGHT = SnakeSetting.ROWS * SnakeSetting.CELL_SIZE;

    public static final int GRID_WIDTH = 2;                     // Grid-line's width
    public static final int GRID_WIDHT_HALF = GRID_WIDTH / 2;   // Grid-line's half-width

    static final int UPDATES_PER_SEC = 3;                   // number of game update per second
    static final long UPDATE_PERIOD_NSEC = 1000000000L / UPDATES_PER_SEC;   // nanoseconds

    private final Color COLOR_PIT = Color.LIGHT_GRAY;

    // Enumeration for the states of the game.
    static enum GameState {
        INITIALIZED, PLAYING, PAUSED, GAMEOVER, DESTROYED
    }

    private GameState state;   // current state of the game

    // Define instance variables for the game objects
    private SnakeFood food;
    private Snake snake;

    // Handle for the custom drawing panel
    private GameCanvas pit;
    private ControlPanel control;
    private JLabel lblScore;
    private JLabel lblSnakeState;
    private int score;          // number of food eaten
    private String strSnakeState;

    // Constructor to initialize the UI components and game objects
    public SnakeGame() {
        // Initialize the game objects
        gameInit();

        // UI components
        setLayout(new BorderLayout());
        pit = new GameCanvas();
        pit.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));
        add(pit, BorderLayout.CENTER);
        control = new ControlPanel();
        add(control, BorderLayout.SOUTH);
        // Start the game.
        // gameStart();
    }

    // ------ All the game related codes here ------

    // Initialize all the game objects, run only once in the constructor of the main class.
    public void gameInit() {
        // Allocate a new snake and a food item, do not regenerate.
        snake = new Snake();
        food = new SnakeFood();
        state = GameState.INITIALIZED;
        score = 0;
        strSnakeState = "Position: (" + snake.getHeadX() + "," + snake.getHeadY() + ");   Length: "
                + snake.getLength() + "        ";
    }

    // Shutdown the game, clean up code that runs only once.
    public void gameShutdown() {
    }

    // To start and re-start the game.
    public void gameStart() {
        // Create a new thread
        Thread gameThread = new Thread() {

            // Override run() to provide the running behavior of this thread.
            @Override
            public void run() {
                gameLoop();
            }
        };

        // Start the thread. start() calls run(), which in turn calls gameLoop().
        gameThread.start();
    }

    // Run the game loop here.
    private void gameLoop() {
        // Regenerate and reset the game objects for a new game
        if (state == GameState.INITIALIZED || state == GameState.GAMEOVER) {
            score = 0;
            lblScore.setText("      Score: " + score);

            // Generate a new snake and a food item
            snake.regenerate();
            int x, y;
            do {
                food.regenerate();
                x = food.getX();
                y = food.getY();
            } while (snake.contains(x, y)); // regenerate if food placed under the snake
            state = GameState.PLAYING;
        }

        // Game loop
        long beginTime, timeTaken, timeLeft;   // in msec
        while (state != GameState.GAMEOVER) {
            beginTime = System.nanoTime();
            if (state == GameState.PLAYING) {
                // Update the state and position of all the game objects,
                // detect collisions and provide responses.
                gameUpdate();
            }
            // Refresh the display
            repaint();
            // Delay timer to provide the necessary delay to meet the target rate
            timeTaken = System.nanoTime() - beginTime;
            timeLeft = (UPDATE_PERIOD_NSEC - timeTaken) / 1000000;  // in milliseconds
            if (timeLeft < 10) timeLeft = 10;  // set a minimum
            try {
                // Provides the necessary delay and also yields control so that other thread can do work.
                Thread.sleep(timeLeft);
            }
            catch (InterruptedException ex) {
            }
        }
    }

    // Update the state and position of all the game objects,
    // detect collisions and provide responses.
    public void gameUpdate() {
        snake.update();
        processCollision();
    }

    // Collision detection and response
    public void processCollision() {
        // check if this snake eats the food item
        int headX = snake.getHeadX();
        int headY = snake.getHeadY();

        if (headX == food.getX() && headY == food.getY()) {

            System.out.println(snake);

            score++;
            lblScore.setText("      Score: " + score);
            // food eaten, regenerate one
            int x, y;
            do {
                food.regenerate();
                x = food.getX();
                y = food.getY();
            } while (snake.contains(x, y));
        }
        else {
            // not eaten, shrink the tail
            snake.shrink();
        }

        // Check if the snake moves out of bounds
        if (!pit.contains(headX, headY)) {
            state = GameState.GAMEOVER;
            return;
        }

        // Check if the snake eats itself
        if (snake.eatItself()) {
            state = GameState.GAMEOVER;
            return;
        }
    }

    // Refresh the display. Called back via rapaint(), which invoke the paintComponent().
    private void gameDraw(Graphics g) {
        // draw game objects
        snake.draw(g);
        food.draw(g);
        // game info
        g.setFont(new Font("Dialog", Font.PLAIN, 14));
        g.setColor(Color.BLACK);

        // g.drawString("Snake: (" + snake.getHeadX() + "," + snake.getHeadY() + "); length: " + snake.getLength(), 5, 25);

        strSnakeState = "Position: (" + snake.getHeadX() + "," + snake.getHeadY() + ");   Length: "
                + snake.getLength() + "        ";
        lblSnakeState.setText(strSnakeState);

        if (state == GameState.GAMEOVER) {
            g.setFont(new Font("Verdana", Font.BOLD, 30));
            g.setColor(Color.RED);
            g.drawString("GAME OVER!", 200, CANVAS_HEIGHT / 2);
        }
    }

    // Process a key-pressed event. Update the current state.
    public void gameKeyPressed(int keyCode) {
        switch (keyCode) {
            case KeyEvent.VK_UP:
                snake.setDirection(Snake.Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
                snake.setDirection(Snake.Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
                snake.setDirection(Snake.Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
                snake.setDirection(Snake.Direction.RIGHT);
                break;
        }
    }


    public static void main(String[] args) {
        // Use the event dispatch thread to build the UI for thread-safety.
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame(TITLE);
                frame.setContentPane(new SnakeGame());  // main JPanel as content pane
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();
                frame.setLocationRelativeTo(null); // center the application window
                frame.setVisible(true);            // show it
            }
        });
    }

    // Custom drawing panel, written as an inner class.
    class GameCanvas extends JPanel implements KeyListener {

        public GameCanvas() {
            setFocusable(true);     // so that can receive key-events
            requestFocus();
            addKeyListener(this);
        }

        // Override paintComponent to do custom drawing.
        // Called back by repaint().
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);    // paint background
            setBackground(COLOR_PIT);   // may use an image for background

            // Draw the grid-lines
            g.setColor(Color.BLACK);
            for (int row = 1; row < SnakeSetting.ROWS; row++) {
                g.fillRoundRect(0, SnakeSetting.CELL_SIZE * row - GRID_WIDHT_HALF,
                                CANVAS_WIDTH - 1, GRID_WIDTH,
                                GRID_WIDTH, GRID_WIDTH);
            }
            for (int col = 1; col < SnakeSetting.COLUMNS; col++) {
                g.fillRoundRect(SnakeSetting.CELL_SIZE * col - GRID_WIDHT_HALF, 0,
                                GRID_WIDTH, CANVAS_HEIGHT - 1,
                                GRID_WIDTH, GRID_WIDTH);
            }

            // Draw the game objects
            gameDraw(g);
        }

        // KeyEvent handlers
        @Override
        public void keyPressed(KeyEvent e) {
            gameKeyPressed(e.getKeyCode());
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyTyped(KeyEvent e) {
        }

        // Check if this pit contains the given (x, y), for collision detection
        public boolean contains(int x, int y) {
            if ((x < 0) || (x >= SnakeSetting.ROWS)) {
                return false;
            }
            if ((y < 0) || (y >= SnakeSetting.COLUMNS)) {
                return false;
            }
            return true;
        }
    }

    // Game Control Panel with Start, Stop, Pause and Mute buttons, designed as an inner class.
    class ControlPanel extends JPanel {
        private JButton btnStartPause;
        private JButton btnStop;

        private ImageIcon iconStart = new ImageIcon(
                getClass().getClassLoader().getResource("images/play-button.png"),
                "START");
        private ImageIcon iconPause = new ImageIcon(
                getClass().getClassLoader().getResource("images/pause-button.png"),
                "PAUSE");
        private ImageIcon iconStop = new ImageIcon(
                getClass().getClassLoader().getResource("images/stop-button.png"), "STOP");


        public ControlPanel() {
            this.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

            lblSnakeState = new JLabel(strSnakeState);
            lblSnakeState.setHorizontalAlignment(JLabel.LEFT);
            add(lblSnakeState);

            btnStartPause = new JButton(iconStart);
            btnStartPause.setEnabled(true);
            add(btnStartPause);

            btnStop = new JButton(iconStop);
            btnStop.setEnabled(false);
            add(btnStop);

            lblScore = new JLabel("      Score: " + score);
            lblScore.setHorizontalAlignment(JLabel.RIGHT);
            add(lblScore);

            btnStartPause.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    switch (state) {
                        case INITIALIZED:
                        case GAMEOVER:
                            btnStartPause.setIcon(iconPause);
                            gameStart();
                            break;
                        case PLAYING:
                            state = GameState.PAUSED;
                            btnStartPause.setIcon(iconStart);
                            break;
                        case PAUSED:
                            state = GameState.PLAYING;
                            btnStartPause.setIcon(iconPause);
                            break;
                    }
                    btnStop.setEnabled(true);
                    pit.requestFocus();
                }
            });
            btnStop.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    state = GameState.GAMEOVER;
                    btnStartPause.setIcon(iconStart);
                    btnStartPause.setEnabled(true);
                    btnStop.setEnabled(false);
                }
            });
        }

        // Reset control for a new game
        public void reset() {
            btnStartPause.setIcon(iconStart);
            btnStartPause.setEnabled(true);
            btnStop.setEnabled(false);
        }
    }
}
