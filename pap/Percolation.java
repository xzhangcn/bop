/**
 * created:    2018/03/19
 * <p>
 * {@code Percolation} is to model a percolation system.
 *
 * What is a percolation?
 * Given a composite systems comprised of randomly distributed insulating and metallic materials:
 * what fraction of the materials need to be metallic so that the composite system is an electrical conductor?
 * Given a porous landscape with water on the surface (or oil below), under what conditions will the water be
 * able to drain through to the bottom (or the oil to gush through to the surface)? Scientists have defined an
 * abstract process known as percolation to model such situations.
 *
 * The model.
 * We model a percolation system using an n-by-n grid of sites. Each site is either open or blocked.
 * A full site is an open site that can be connected to an open site in the top row via a chain of neighboring (left,
 * right, up, down) open sites. We say the system percolates if there is a full site in the bottom row. In other
 * words, a system percolates if we fill all open sites connected to the top row and that process fills some open
 * site on the bottom row. (For the insulating/metallic materials example, the open sites correspond to metallic
 * materials, so that a system that percolates has a metallic path from top to bottom, with full sites conducting.
 * For the porous substance example, the open sites correspond to empty space through which water might flow,
 * so that a system that percolates lets water fill open sites, flowing from top to bottom.)
 *
 * @author Xiaoyu Zhang
 */

public class Percolation {
    // Store the grid in 1D format
    private boolean[] grid;

    // Utilize the weighted quick union find algorithm to determine the full open sites
    private final WeightedQuickUnionUF ufFull;

    // Utilize the weighted quick union find algorithm to determine if percolate
    // Though it's not that efficient for memory usage by using two UF data structure, 
    // but it allows constant number of UF operations and avoid backwash.
    private final WeightedQuickUnionUF ufPerc;

    // grid size
    private final int size;
    // number of opened sites
    private int openCount;

    // virtual site for the top row
    private final int virtualTop;

    // virtual site for the bottom row
    private final int virtualBottom;

    /*
     * Create n-by-n grid, with all sites blocked
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n should be greater than 0");

        this.size = n;
        this.grid = new boolean[n * n];

        // Initialize sites to be blocked 
        for (int i = 0; i < n * n; i++) {
            this.grid[i] = false;
        }

        this.openCount = 0;

        // '+ 1' for 1 additional virtual top site, which are placed at the end
        this.ufFull = new WeightedQuickUnionUF(n * n + 1);

        // '+ 2' for 2 additional virtual top and bottom sites, which are placed at the end
        this.ufPerc = new WeightedQuickUnionUF(n * n + 2);

        this.virtualTop = n * n;
        this.virtualBottom = n * n + 1;
    }

    /*
     * Open site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        if (isOpen(row, col))
            return;

        int index = xyTo1D(row, col);
        grid[index] = true;
        openCount++;

        // Merge the virtual top site if open the site on the first row
        if (row == 1) {
            ufFull.union(index, virtualTop);
            ufPerc.union(index, virtualTop);
        }

        // Merge the virtual top site if open the site on the bottom row                               
        if (row == this.size)
            ufPerc.union(index, virtualBottom);

        // Merge the component above
        if (isValid(row - 1, col) && isOpen(row - 1, col)) {
            ufFull.union(index, xyTo1D(row - 1, col));
            ufPerc.union(index, xyTo1D(row - 1, col));
            // return;
        }

        // Merge the component on the right
        if (isValid(row, col + 1) && isOpen(row, col + 1)) {
            ufFull.union(index, xyTo1D(row, col + 1));
            ufPerc.union(index, xyTo1D(row, col + 1));
            // return;
        }

        // Merge the component below
        if (isValid(row + 1, col) && isOpen(row + 1, col)) {
            ufFull.union(index, xyTo1D(row + 1, col));
            ufPerc.union(index, xyTo1D(row + 1, col));
            // return;
        }

        // Merge the component on the left
        if (isValid(row, col - 1) && isOpen(row, col - 1)) {
            ufFull.union(index, xyTo1D(row, col - 1));
            ufPerc.union(index, xyTo1D(row, col - 1));
            // return;
        }
    }

    /*
     * Is site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException("row and col should be between 1 and n");

        return grid[xyTo1D(row, col)];
    }

    /*
     * Is site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException("row and col should be between 1 and n");

        return ufFull.connected(xyTo1D(row, col), virtualTop);
    }

    /*
     * Number of open sites
     */
    public int numberOfOpenSites() {
        return openCount;
    }

    /*
     * Does the system percolate?
     */
    public boolean percolates() {
        return ufPerc.connected(virtualTop, virtualBottom);
    }

    /*
     * Map 2D cooridinates to 1D coordinates
     * The coordinate (row, col) already being validated before calling this helper function
     */
    private int xyTo1D(int row, int col) {
        return (row - 1) * this.size + (col - 1);

    }

    /*
     * Check if the coordinate(row, col) is valid
     */
    private boolean isValid(int row, int col) {
        return (row >= 1 && row <= this.size && col >= 1 && col <= this.size);
    }

    /*
     * Test client
     */
    public static void main(String[] args) {
        // Use PercolationVisualizer and InteractivePercolationVisualizer to test the functionality of Percolation
    }

}