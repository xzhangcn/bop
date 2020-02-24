import javax.swing.JButton;

/**
 * created:    2020/02/24
 * <p>
 * compilation:  javac MineCell.java
 * <p>
 * a subclass of JButton to represent one cell on the grid, which greatly facilitates and simplifies
 * the game processing.
 *
 * @author Xiaoyu Zhang
 */

@SuppressWarnings("serial")
public class MineCell extends JButton {
    private int row;
    private int col;
    private int adjMines;
    private boolean hasMine;
    private boolean hasFlag;
    private boolean isRevealed;

    public MineCell(int row, int col) {
        this.row = row;
        this.col = col;
        this.adjMines = -1;
    }

    public int getRow() {
        return this.row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public boolean isHasMine() {
        return this.hasMine;
    }

    public void setHasMine(boolean hasMine) {
        this.hasMine = hasMine;
    }

    public boolean isHasFlag() {
        return this.hasFlag;
    }

    public void setHasFlag(boolean hasFlag) {
        this.hasFlag = hasFlag;
    }

    public boolean isRevealed() {
        return this.isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public int getAdjMines() {
        return this.adjMines;
    }

    public void setAdjMines(int adjMines) {
        this.adjMines = adjMines;
    }
}
