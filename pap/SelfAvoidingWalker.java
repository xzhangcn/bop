/**
 * created:    2020/03/24
 * <p>
 * {@code SelfAvoidingWalker} is an example of Monte Carlo simulation.
 *
 * Simulate a self-avoiding walk in two dimensions. Follow trajectory of 2D random walk until it walks back in on itself or
 * reaches the boundary. Always choose a direction that won't hit itself if possible.
 *
 * <p>
 * Model: a random process in an N-by-N lattice
 *  Start in the middle.
 *  Move to a random neighboring intersection but do not revisit any intersection.
 *  Outcome 1 (escape): reach edge of lattice.
 *  Outcome 2 (dead end): no unvisited neighbors.
 *
 * Q. What are the chances of reaching a dead end?
 * Approach: Use Monte Carlo simulation, recording visited positions in an N-by-N array.
 *
 * <p>
 * Applications:
 *  Model the behavior of solvents and polymers.
 *  Model the physics of magnetic materials.
 *  (many other physical phenomena)
 *
 * <p>
 * Q. What is the probability of reaching a dead end?
 * A. Nobody knows (despite decades of study).
 * A. 99+% for N >100 (clear from simulations).
 *
 * <p>
 * Remarks:
 *     Computer simulation is often the only effective way to study a scientific phenomenon.
 *
 * @author Xiaoyu Zhang
 */

public class SelfAvoidingWalker {

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);              // lattice size
        int trials = Integer.parseInt(args[1]);         // number of trials
        int deadEnds = 0;                               // trials resulting in a dead end

        for (int t = 0; t < trials; t++) {
            boolean[][] visited = new boolean[N][N];    // intersections visited
            int x = N / 2, y = N /2;                    // starting position

            // repeatedly take a random step, unless you've already escaped
            while (x > 0 && x < N - 1 && y > 0 && y < N - 1) {

                // dead-end, so break out of loop
                if (visited[x - 1][y] && visited[x + 1][y] && visited[x][y - 1] && visited[x][y + 1]) {
                    deadEnds++;
                    break;
                }

                // mark (x, y) as visited
                visited[x][y] = true;

                // take a random step to unvisited neighbor
                double r = Math.random();
                if (r < 0.25) {
                    if (!visited[x + 1][y])
                        x++;
                }
                else if (r < 0.5) {
                    if (!visited[x - 1][y])
                        x--;
                }
                else if (r < 0.75) {
                    if (!visited[x][y + 1])
                        y++;
                }
                else if (r < 1.00) {
                    if (!visited[x][y -1])
                        y--;
                }
            }
        }

        System.out.printf("%d percent dead ends.\n", 100 * deadEnds / trials);
    }
}
