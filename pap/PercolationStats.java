/**
 * created:    2018/03/19
 * <p>
 * {@code PercolationStats} is the Monte Carlo simulation.
 *
 * To estimate the percolation threshold, consider the following computational experiment:
 *    - Initialize all sites to be blocked.
 *
 *    - Repeat the following until the system percolates:
 *          - Choose a site uniformly at random among all blocked sites.
 *          - Open the site.
 *
 *    - The fraction of sites that are opened when the system percolates provides an estimate of the percolation threshold.
 *
 * @author Xiaoyu Zhang
 */

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;

    private final int trials;

    private final double mean;
    private final double stddev;
    /*
     * Perform trials independent experiments on an n-by-n grid
     */
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0)
            throw new IllegalArgumentException("n and trials should be greater than 0");

        Percolation perc;

        this.trials = trials;

        // Fraction of open sites in one computational experiment
        double[] openSites = new double[trials];

        // Randomly generated indices for row and col
        int row, col;

        // Implement Monte Carlo simulation
        for (int i = 0; i < trials; i++) {
            perc = new Percolation(n);
            while (!perc.percolates()) {
                row = StdRandom.uniform(1, n + 1);
                col = StdRandom.uniform(1, n + 1);
                perc.open(row, col);
            }

            openSites[i] = (double) (perc.numberOfOpenSites()) / (n * n);

            perc = null;
        }

        // Compute the mean and stddev here, 
        // so that only invoke the operations on the class StdStats once
        this.mean = StdStats.mean(openSites);
        this.stddev = StdStats.stddev(openSites);
    }

    /*
     * Sample mean of percolation threshold
     */
    public double mean() {
        return this.mean;
    }

    /*
     * Sample standard deviation of percolation threshold
     */
    public double stddev() {
        return this.stddev;
    }

    /*
     * low endpoint of 95% confidence interval
     */
    public double confidenceLo() {
        return (this.mean - (CONFIDENCE_95 * this.stddev / Math.sqrt(this.trials)));
    }

    /*
     * high endpoint of 95% confidence interval
     */
    public double confidenceHi() {
        return (this.mean + (CONFIDENCE_95 * this.stddev / Math.sqrt(this.trials)));
    }

    public static void main(String[] args) {
        // test client
        if (args.length != 2) {
            StdOut.println("Please provide the grid size n and the number of trials");
            return;
        }

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats ps = new PercolationStats(n, trials);

        StdOut.printf("Mean = %.10f\n", ps.mean());
        StdOut.printf("stddev = %.10f\n", ps.stddev());

        StdOut.printf("95 confidence interval = [%.10f, %.10f]\n", ps.confidenceLo(), ps.confidenceHi());
    }
}