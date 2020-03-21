/**
 * created:    2019/08/31
 * <p>
 * Random Walkers, an example of Monte Carlo simulation.
 *
 * This program takes two integer command-line arguments r and trials. In each of trials independent experiments,
 * simulate a random walk until the random walker is at Manhattan distance r from the starting point.
 * Print the average number of steps.
 *
 * <p>
 * As r increases, we expect the random walker to take more and more steps. But how many more
 * steps? Use RandomWalkers.java to formulate a hypothesis as to how the average number of
 * steps grows as a function of r.
 *
 * <p>
 * Estimating an unknown quantity by generating random samples and aggregating the results is
 * an example of Monte Carlo simulation—a powerful computational technique that is used
 * widely in statistical physics, computational finance, and computer graphics.
 *
 * @author Xiaoyu Zhang
 */

public class RandomWalkers {

    public static void main(String[] args) {

        int r = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        int i = 0, j = 0, steps = 0;
        double totalSteps = 0.0;
        double direction;

        for (int n = 0; n < trials; n++) {
            while (steps < r || ((Math.abs(i) + Math.abs(j)) < r)) {
                direction = Math.random();
                if (direction <= 0.25)
                    j = j + 1;
                else if (direction <= 0.5)
                    i = i + 1;
                else if (direction <= 0.75)
                    j = j - 1;
                else
                    i = i - 1;

                steps = steps + 1;

                // System.out.println("(" + i + ", " + j + ")");
            }

            totalSteps = totalSteps + steps;

            i = 0;
            j = 0;
            steps = 0;
        }

        System.out.println("average number of steps = " + (totalSteps/trials));
    }
}
