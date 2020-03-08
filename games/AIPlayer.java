/**
 * Abstract superclass for all AI players with different strategies.
 * <p>
 * To construct an AI player:
 * <p>
 * 1. Construct an instance (of its subclass) with the game Board;
 * <p>
 * 2. Call setSeed() to set the computer's seed;
 * <p>
 * 3. Call move() which returns the next move in an int[2] array of {row, col}.
 * <p>
 * The implementation subclasses need to override abstract method move(). They shall not modify
 * Cell[][], i.e., no side effect expected. Assume that next move is available, i.e., not game-over
 * yet.
 */

public abstract class AIPlayer {
    protected int ROWS = T3Setting.ROWS;    // number of rows
    protected int COLS = T3Setting.COLS;    // number of columns

    protected T3Board board;
    protected T3Setting.Seed mySeed;        // computer's seed
    protected T3Setting.Seed oppSeed;       // opponent's seed

    /**
     * @param board the board
     */
    public AIPlayer(T3Board board) {
        this.board = board;
    }

    /**
     * Set/change the seed used by computer and opponent
     *
     * @param seed the seed
     */
    public void setSeed(T3Setting.Seed seed) {
        this.mySeed = seed;
        oppSeed = (mySeed == T3Setting.Seed.CROSS) ? T3Setting.Seed.NOUGHT : T3Setting.Seed.CROSS;
    }

    /** Abstract method to get next move. Return int[2] of {row, col} */
    abstract int[] move();  // to be implemented by subclasses
}
