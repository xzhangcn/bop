/**
 * created:    2019/09/13
 * <p>
 * {@code WorldMap} reads boundary information of a country (or other geographic entity) from standard input and plots
 * the results to standard drawing. A country consists of a set of regions (e.g., states, provinces, or other administrative divisions),
 * each of which is described by a polygon.
 *
 * @author Xiaoyu Zhang
 */

public class WorldMap {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        int width = StdIn.readInt();				// canvas width
        int	height = StdIn.readInt();				// canvas height
        String state;								// name of each state
        int size;									// size of the polygon for each state
        int i;										// index to iterate the coordinates array

        StdDraw.enableDoubleBuffering();

        StdDraw.setCanvasSize(width, height);
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);

        while (!StdIn.isEmpty()) {
            state = StdIn.readString();
            size = StdIn.readInt();

            double[] xVertex = new double[size];		// array to store the x-coordinates of each vertex
            double[] yVertex = new double[size];		// array to store the y-coordinates of each vertex

            for (i = 0; i < size; i++) {
                xVertex[i] = StdIn.readDouble();
                yVertex[i] = StdIn.readDouble();
            }

            StdDraw.polygon(xVertex, yVertex);
        }

        StdDraw.show();
    }
}
