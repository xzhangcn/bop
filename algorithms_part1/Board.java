import java.awt.Font;
import java.util.LinkedList;
import java.util.Queue;

/**
 * created:    2018/04/06
 * modified:   2019/12/18, adding the draw method to visualize the board
 * <p>
 * {@code Board} is an immutable data type that models an n-by-n board with sliding tiles.
 *
 * @author Xiaoyu Zhang
 */

public class Board /* implements Comparable<Board> */ {

    private final int size;
    private int blankSquare;        // index position for the blank square
    private int hamming;            // cache the hamming priority value
    private int manhattan;          // cache the manhattan priority value
    private final int[] tiles;      // one dimensional array to store two dimensional tiles data
    private Board twinBoard;
    // to make the Board object immutable, so return the same twinBoard for the same Board instance

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        size = tiles.length;
        this.tiles = new int[size * size];
        twinBoard = null;
        hamming = -1;
        manhattan = -1;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.tiles[i * size + j] = tiles[i][j];

                if (tiles[i][j] == 0)
                    blankSquare = i * size + j;
            }
        }
    }

    // draw the board
    public void draw() {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setXscale(-0.05 * size, 1.05 * size);
        StdDraw.setYscale(-0.05 * size, 1.05 * size);   // leave a border to write text

        // StdDraw.filledSquare(size / 2.0, size / 2.0, size / 2.0);

        // draw the grid lines
        for (int idx = 0; idx <= size; idx++) {
            StdDraw.line(0, idx, size, idx);    // horizontal lines
            StdDraw.line(idx, 0, idx, size);    // vertical lines
        }

        // draw n-by-n grid
        StdDraw.setFont(new Font("SansSerif", Font.PLAIN, 50));

        for (int row = 1; row <= size; row++) {
            for (int col = 1; col <= size; col++) {

                if (tiles[(row - 1) * size + (col - 1)] == 0)
                    StdDraw.setPenColor(StdDraw.GRAY);          // draw the blank square in GRAY
                else
                    StdDraw.setPenColor(StdDraw.BLUE);

                StdDraw.filledSquare(col - 0.5, size - row + 0.5, 0.45);

                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.text(col - 0.5, size - row + 0.5, Integer.toString(tiles[(row - 1) * size + (col - 1)]));
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");

        for (int i = 0; i < size * size; i++) {
            s.append(String.format("%2d ", tiles[i]));

            if ((i + 1) % size == 0)
                s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        if (hamming != -1)
            return hamming;

        hamming = 0;

        for (int i = 0; i < size * size; i++) {
            // blank square doesn't count
            if (tiles[i] != 0 && tiles[i] != (i + 1))
                hamming++;
        }

        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        // return the already computed manhattan value
        if (manhattan != -1)
            return manhattan;

        int curX, curY;        // convert 2D position from 1D for current index
        int destX, destY;      // convert 2D position from 1D for the current tile's goal position
        manhattan = 0;

        for (int i = 0; i < size * size; i++) {
            // blank square doesn't count
            if (tiles[i] != 0 && tiles[i] != (i + 1)) {
                curX = i / size;
                curY = i % size;
                destX = (tiles[i] - 1) / size;
                destY = (tiles[i] - 1) % size;
                manhattan = manhattan + (Math.abs(curX - destX) + Math.abs(curY - destY));
            }
        }

        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return (hamming() == 0);
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (twinBoard == null) {
            int randPos1, randPos2;

            // Calculate two random positions for exchanging any pair of tiles
            randPos1 = StdRandom.uniform(size * size);
            randPos2 = StdRandom.uniform(size * size);
            if (tiles[randPos1] == 0)
                randPos1 = (randPos1 + 1) % (size * size);
            if (tiles[randPos2] == 0)
                randPos2 = (randPos2 + 1) % (size * size);

            if (randPos1 == randPos2) {
                randPos2 = (randPos2 + 1) % (size * size);

                // Need to check against the blank tile again for the position of randPos2
                if (tiles[randPos2] == 0)
                    randPos2 = (randPos2 + 1) % (size * size);
            }

            // Get the twin tiles
            int[][] twinTiles = new int[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++)
                    twinTiles[i][j] = tiles[i * size + j];
            }
            twinTiles[randPos1 / size][randPos1 % size] = tiles[randPos2];
            twinTiles[randPos2 / size][randPos2 % size] = tiles[randPos1];

            twinBoard = new Board(twinTiles);
        }

        return twinBoard;
    }

    /**
     * Compare this board to the specified board
     *
     * @param other the other board
     * @return {@code true} if this board equals {@code other}; {@code false} otherwise
     */
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;

        Board that = (Board) other;

        if (size != that.dimension()) return false;

        // return this.toString().equals(that.toString());
        // return (this.hamming() == that.hamming() && this.manhattan() == that.manhattan());
        for (int i = 0; i < size * size; i++) {
            // can access the object that' private array tiles directly
            if (this.tiles[i] != that.tiles[i])
                return false;
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new LinkedList<Board>();
        Board neighborBoard;

        int curX, curY;                 // convert to 2D coordinates for blank square
        curX = blankSquare / size;
        curY = blankSquare % size;

        int[][] blocks = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                blocks[i][j] = tiles[i * size + j];
        }

        // Get the neighbor boards in 4 directions
        if (curX - 1 >= 0) {
            blocks[curX - 1][curY] = 0;
            blocks[curX][curY] = tiles[(curX - 1) * size + curY];
            neighborBoard = new Board(blocks);
            neighbors.add(neighborBoard);

            // restore the state to the search node
            blocks[curX][curY] = 0;
            blocks[curX - 1][curY] = tiles[(curX - 1) * size + curY];
        }

        if (curX + 1 <= (size - 1)) {
            blocks[curX + 1][curY] = 0;
            blocks[curX][curY] = tiles[(curX + 1) * size + curY];
            neighborBoard = new Board(blocks);
            neighbors.add(neighborBoard);

            // restore the state to the search node
            blocks[curX][curY] = 0;
            blocks[curX + 1][curY] = tiles[(curX + 1) * size + curY];
        }

        if (curY - 1 >= 0) {
            blocks[curX][curY - 1] = 0;
            blocks[curX][curY] = tiles[curX * size + (curY - 1)];
            neighborBoard = new Board(blocks);
            neighbors.add(neighborBoard);

            // restore the state to the search node
            blocks[curX][curY] = 0;
            blocks[curX][curY - 1] = tiles[curX * size + (curY - 1)];
        }

        if (curY + 1 <= (size - 1)) {
            blocks[curX][curY + 1] = 0;
            blocks[curX][curY] = tiles[curX * size + (curY + 1)];
            neighborBoard = new Board(blocks);
            neighbors.add(neighborBoard);

            // restore the state to the search node
            blocks[curX][curY] = 0;
            blocks[curX][curY + 1] = tiles[curX * size + (curY + 1)];
        }

        return neighbors;
    }


    /**
     * unit tests (not graded)
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] squares = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                squares[i][j] = in.readInt();
        Board initial = new Board(squares);
        StdOut.println(initial);
        StdOut.println("Manhattan value: " + initial.manhattan());

        // turn on animation mode
        StdDraw.enableDoubleBuffering();

        initial.draw();

        StdDraw.show();

        /*
        Board initialCopy = new Board(squares);
        StdOut.println(initialCopy);
        if (initial.equals(initialCopy))
            StdOut.println("There are two identical boards.");

        StdOut.println("Hamming value: " + initial.hamming());
        StdOut.println("Manhattan value: " + initial.manhattan());

        StdOut.println("Generating twin board......");
        Board twinBoard = initial.twin();
        StdOut.println(twinBoard);
        if (twinBoard.isGoal())
            StdOut.println("The twin board is the goal!");

        for (Board board : initial.neighbors())
            StdOut.println(board);
        */
    }
}
