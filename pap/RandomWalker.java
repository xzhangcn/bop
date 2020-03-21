/**
 * created:    2019/08/31
 * <p>
 * Random Walker.
 *
 * A Java programmer begins walking aimlessly. At each time step, she takes one
 * step in a random direction (either north, east, south, or west), each with probability 25%. She
 * stops once she is at Manhattan distance r from the starting point. How many steps will the
 * random walker take? This process is known as a two-dimensional random walk.
 *
 * <p>
 * This program takes an integer command-line argument r and simulates the motion of a random walk
 * until the random walker is at Manhattan distance r from the starting point.
 * Print the coordinates at each step of the walk (including the starting and ending points),
 * treating the starting point as (0, 0). Also, print the total number of steps taken.
 *
 * <p>
 * This process is a discrete version of a natural phenomenon known as Brownian motion. It
 * serves as a scientific model for an astonishing range of physical processes from the dispersion
 * of ink flowing in water, to the formation of polymer chains in chemistry, to cascades of neurons
 * firing in the brain.
 *
 * @author Xiaoyu Zhang
 */

public class RandomWalker {

    public static void main(String[] args) {

        int r = Integer.parseInt(args[0]);
        int i = 0, j = 0, steps = 0;
        double direction;

        System.out.println("(" + i + ", " + j + ")");

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

            System.out.println("(" + i + ", " + j + ")");
        }

        System.out.println("steps = " + steps);
    }
}
