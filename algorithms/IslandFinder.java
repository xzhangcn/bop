import java.util.LinkedList;
import java.util.Queue;

/**
 * created:    2020/02/25
 * <p>
 * compilation:  javac IslandFinder.java
 * <p>
 * execution:    java IslandFinder
 * <p>
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is
 * surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You
 * may assume all four edges of the grid are all surrounded by water.
 * <p>
 * Example 1: Input: {11110, 11010, 11000, 00000}, Output: 1.
 * <p>
 * Example 2: Input: {11000 11000 00100 00011}, Output: 3.
 * <p>
 * Two solutions (both BFS and DFS) given here, to demonstrate the two ways of solving this kind of
 * searching problems.
 *
 * @author Xiaoyu Zhang
 */

public class IslandFinder {

    // 4 directions {east, south, west, north}
    private static int[][] directions = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };

    /**
     * BFS version
     *
     * @param grid input grid map filled with 0 and 1
     * @return number of islands in this 2D grid map
     */
    public static int numIslandsBFS(int[][] grid) {
        if (grid.length == 0)
            return 0;

        int m = grid.length;
        int n = grid[0].length;

        System.out.printf("m = %d, n = %d \n", m, n);

        // use visited array to avoid changing the values of original grid
        boolean visited[][] = new boolean[m][n];

        Queue<Integer> queue = new LinkedList<Integer>();
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && !visited[i][j]) {
                    queue.offer(i * n + j);     // convert 2D coordinates to 1D index
                    visited[i][j] = true;
                    bfs(grid, queue, visited);
                    count++;

                    System.out.printf("pos: (%d, %d), count = %d \n\n", i, j, count);
                }
            }
        }

        return count;
    }

    /**
     * @param grid
     * @param queue
     * @param visited
     */
    private static void bfs(int[][] grid, Queue<Integer> queue, boolean[][] visited) {
        int m = grid.length;
        int n = grid[0].length;

        while (!queue.isEmpty()) {
            int curr = queue.poll();    // dequeue

            // convert 1D index back to 2D coordinates
            int currX = curr / n;
            int currY = curr - currX * n;

            System.out.printf("curr: %d, curr pos: (%d, %d) \n", curr, currX, currY);

            // iterate through 4 directions
            for (int[] direction : directions) {

                int nextX = currX + direction[0];
                int nextY = currY + direction[1];
                if (nextX < 0 || nextX >= m || nextY < 0 || nextY >= n
                        || visited[nextX][nextY] || grid[nextX][nextY] == 0)
                    continue;

                visited[nextX][nextY] = true;
                queue.offer(nextX * n + nextY);     // enqueue

                System.out.printf("next pos: (%d, %d) \n", nextX, nextY);
            }
        }
    }

    /**
     * DFS version
     *
     * @param grid input grid map filled with 0 and 1
     * @return number of islands in this 2D grid map
     */
    public static int numIslandsDFS(int[][] grid) {
        if (grid.length == 0)
            return 0;

        int m = grid.length;
        int n = grid[0].length;
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    count++;
                    dfs(grid, i, j);
                }
            }
        }

        return count;
    }

    /**
     * @param grid input grid map filled with 0 and 1
     * @param i    horizontal position
     * @param j    vertical position
     */
    private static void dfs(int[][] grid, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;

        // base case
        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] == 0)
            return;

        grid[i][j] = 0;
        for (int[] dir : directions) {
            int nextX = i + dir[0];
            int nextY = j + dir[1];
            dfs(grid, nextX, nextY);
        }
    }

    public static void main(String[] args) {
        int[][] grid1 = {
                { 1, 1, 1, 1, 0 }, { 1, 1, 0, 1, 0 }, { 1, 1, 0, 0, 0 }, { 0, 0, 0, 0, 0 }
        };

        int[][] grid2 = {
                { 1, 1, 0, 0, 0 }, { 1, 1, 0, 0, 0 }, { 0, 0, 1, 0, 0 }, { 0, 0, 0, 1, 1 }
        };

        System.out.printf("%d islands found in grid1. \n", IslandFinder.numIslandsBFS(grid1));
        System.out.printf("%d islands found in grid2. \n", IslandFinder.numIslandsDFS(grid2));
    }
}
