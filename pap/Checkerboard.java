/**
 * created:    2019/09/13
 * <p>
 * {@code Checkerboard} takes a command-line integer n and plots an n-by-n checkerboard pattern to standard drawing.
 * Color the squares blue and light gray, with the bottom-left square blue.
 *
 * @author Xiaoyu Zhang
 */

public class Checkerboard {

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        StdDraw.setScale(0, n);

        StdDraw.enableDoubleBuffering();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if ((i + j) % 2 == 0)
                    StdDraw.setPenColor(StdDraw.BLUE);
                else
                    StdDraw.setPenColor(StdDraw.LIGHT_GRAY);

                StdDraw.filledSquare(0.5 + j, 0.5 + i, 0.5);
            }
        }

        StdDraw.show();
    }
}
