import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;

/**
 * created:    2019/12/09
 * <p>
 * {@code FastCollinearPoints} Examine 4 points at a time and checks whether they all lie on the same line segment,
 * returning all such line segments.
 *
 * A fast, sort-based solution
 *
 * @author Xiaoyu Zhang
 */

public class FastCollinearPoints {
    private int countLines;           // the number of line segments containing 4 points
    private Queue<LineSegment> connLines;

    /**
     * finds all line segments containing 4 or more points
     *
     * @param points a Point array
     * @throw a java.lang.IllegalArgumentException if the argument to the constructor is null, if
     * any point in the array is null, or if the argument to the constructor contains a repeated
     * point.
     */
    public FastCollinearPoints(Point[] points) {

        // Throw a java.lang.IllegalArgumentException if the argument to the constructor is null,
        // if any point in the array is null, or if the argument to the constructor contains a repeated point.
        if (points == null)
            throw new IllegalArgumentException("points are null");

        int n = points.length;
        Point[] clone = new Point[n];    // not to mutate the argument points by using a copy

        int i, j;

        for (i = 0; i < n; i++) {
            if (points[i] == null)
                throw new IllegalArgumentException("one of points is null");
        }

        for (i = 0; i < n; i++) {
            for (j = (i + 1); j < n; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("two points are identical");
            }

            clone[i] = points[i];
        }

        Arrays.sort(clone);     // natural order sorting

        // Testing purpose: validate the sort operation, which uses natural order
        StdOut.println("*** validate the sort operation, which uses natural order ***");
        for (Point p : clone) {
            StdOut.println(p);
        }


        if (n < 4) return;

        countLines = 0;
        connLines = new LinkedList<>();

        Point curPt = null;
        Point prePt = null;
        for (i = 0; i <= (n - 4); i++) {
            curPt = clone[i];

            // A temporary points array that is used to stores
            // un-examined points whose natural order is bigger than the current point.
            Point[] tempPts = new Point[n - i - 1];

            for (j = i + 1; j < n; j++) {
                tempPts[j - i - 1] = clone[j];
            }

            // Java's system sort should be stable,
            // which preserve the natural order used to sort points previously;
            // Otherwise, the code below will have some defects.
            // Java system sort Arrays.sort() uses tuned quicksort for primitive types;
            // tuned mergesort for objects. And mergesort is stable.
            Arrays.sort(tempPts, curPt.slopeOrder());


            // Testing purpose: validate the sort operation, which uses alternative slope order
            StdOut.println("*** validate the sort operation, which uses slope order ***");
            StdOut.println("current point: " + curPt);
            for (Point p : tempPts) {
                StdOut.println(p);
            }

            // Starting from the current point (curPt),
            // iterate through the temporary points array,
            // and check whether there exists collinear line segments.
            for (j = 0; j <= (tempPts.length - 3); j++) {

                int count = 0;
                int k = j;
                while (k <= (tempPts.length - 2) &&
                        (curPt.slopeTo(tempPts[k]) == curPt.slopeTo(tempPts[k + 1]))) {
                    k++;
                    count++;
                }

                if (count >= 2) {

                    // the condition below is trying to eliminate some sub line segments,
                    // but there is still some bugs.
                    if (countLines == 0 || (prePt != null && prePt.slopeTo(curPt) != curPt.slopeTo(tempPts[k]))) {
                        connLines.add(new LineSegment(curPt, tempPts[k]));
                        countLines++;

                        // If there exists a collinear line segment, skip the next two points.
                        // It's a slight performance improvement.
                        j = j + 2;
                        prePt = curPt;
                    }
                }
            }
        }
    }

    /**
     * the number of line segments
     *
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return countLines;
    }

    /**
     * the line segments
     */
    public LineSegment[] segments() {
        LineSegment[] lines = new LineSegment[countLines];
        int i = 0;

        for (LineSegment line : connLines)
            lines[i++] = line;

        return lines;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        StdDraw.setPenRadius(0.01);
        StdDraw.setPenColor(StdDraw.RED);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        StdDraw.setPenRadius(0.005);
        StdDraw.setPenColor(StdDraw.BLACK);

        FastCollinearPoints collinear = new FastCollinearPoints(points);

        StdOut.println("number of segments: " + collinear.numberOfSegments());

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }
}
