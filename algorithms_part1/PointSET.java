/**
 * created:    2018/04/17
 * <p>
 * {@code PointSET} is a data type to represent a set of points in the unit square (all points have x- and y-coordinates between 0 and 1).
 * Use brute-force algorithm to find all of the points contained in a query rectangle and nearest-neighbor search (find a closest point to a query point).
 *
 * @author Xiaoyu Zhang
 */

public class PointSET {
    private final SET<Point2D> ptSet;

    /**
     * construct an empty set of points 
     */
    public PointSET() {
        ptSet = new SET<Point2D>();
    }

    /**
     * is the set empty? 
     */
    public boolean isEmpty() {
        return ptSet.isEmpty();
    }

    /**
     * number of points in the set 
     */
    public int size() {
        return ptSet.size();
    }

    /**
     * add the point to the set (if it is not already in the set)
     */
    public void insert(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();

        ptSet.add(p);
    }

    /**
     * does the set contain point p? 
     */
    public boolean contains(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();

        return ptSet.contains(p);
    }

    /**
     * draw all points to standard draw 
     */
    public void draw() {
        for (Point2D pt : ptSet) {
            pt.draw();
        }
    }

    /**
     * all points that are inside the rectangle (or on the boundary) 
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null)
            throw new IllegalArgumentException();

        SET<Point2D> rangeSet = new SET<Point2D>();

        for (Point2D pt : ptSet) {
            if (rect.contains(pt))
                rangeSet.add(pt);
        }

        return rangeSet;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty 
     */
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();

        if (isEmpty())
            return null;

        Point2D neighbor = null;
        double min = Double.POSITIVE_INFINITY;
        double d;

        for (Point2D pt : ptSet) {
            d = p.distanceSquaredTo(pt);

            if (d < min) {
                min = d;
                neighbor = pt;
            }
        }

        return neighbor;
    }

    public static void main(String[] args) {

        // initialize the two data structures with point from file
        String filename = args[0];
        In in = new In(filename);
        PointSET brute = new PointSET();

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);

            brute.insert(p);
        }

        // process nearest neighbor queries
        StdDraw.enableDoubleBuffering();
        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            brute.draw();

            // draw in red the nearest neighbor (using brute-force algorithm)
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            brute.nearest(query).draw();

            StdDraw.show();
            StdDraw.pause(40);
        }
    }
}
