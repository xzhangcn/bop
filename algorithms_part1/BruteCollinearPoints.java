import java.util.Arrays;

/**
 * created:    2018/04/07
 * <p>
 * {@code BruteCollinearPoints} Examine 4 points at a time and checks whether they all lie on the same line segment,
 * returning all such line segments.
 *
 * To check whether the 4 points p, q, r, and s are collinear, check whether the three slopes between p and q,
 * between p and r, and between p and s are all equal.
 *
 * @author Xiaoyu Zhang
 */

public class BruteCollinearPoints {
    private int countLines;           // the number of line segments containing 4 points
    private Node first;               // the first node of the linked line segments

    /**
     * finds all line segments containing 4 points
     * @param points a Point array
     * @throw a java.lang.IllegalArgumentException if the argument to the constructor is null, 
     * if any point in the array is null, or if the argument to the constructor contains a repeated point.
     */
    public BruteCollinearPoints(Point[] points) {

        if (points == null)
            throw new IllegalArgumentException("points are null");

        int n = points.length;
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
        }

        if (n < 4)
            return;

        int k, l;
        double slope1, slope2, slope3;
        Point[] tempPoints = new Point[4];

        countLines = 0;
        first = null;           // Initialize the linked list

        for (i = 0; i < n; i++) {
            for (j = (i + 1); j < n; j++) {
                for (k = (j + 1); k < n; k++) {

                    slope1 = points[i].slopeTo(points[j]);
                    slope2 = points[i].slopeTo(points[k]);
                    if (slope1 != slope2)
                        continue;      // no need to go into the inner loop for the slope3 computation

                    for (l = (k + 1); l < n; l++) {
                        slope3 = points[i].slopeTo(points[l]);

                        if (slope1 == slope3) {
                            tempPoints[0] = points[i];
                            tempPoints[1] = points[j];
                            tempPoints[2] = points[k];
                            tempPoints[3] = points[l];

                            Arrays.sort(tempPoints);

                            addLine(tempPoints[0], tempPoints[3]);

                            break;   // for brute search, assume there is no 5 or more points on the same line
                        }
                    }
                }
            }
        }
    }

    /**
     * the number of line segments
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return countLines;
    }

    /**
     * Add the line segment to the linked line list
     * @param p, q
     * @return void
     */
    private void addLine(Point p, Point q) {
        LineSegment line = new LineSegment(p, q);

        Node oldfirst = first;
        first = new Node();
        first.item = line;
        first.next = oldfirst;
        countLines++;

        return;
    }


    /**
     * the line segments
     */
    public LineSegment[] segments() {
        LineSegment[] lines = new LineSegment[countLines];
        Node cur = first;

        for (int i = 0; i < countLines; i++) {
            lines[i] = cur.item;
            cur = cur.next;
        }

        return lines;
    }

    private class Node {
        LineSegment item;
        Node next;
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
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}