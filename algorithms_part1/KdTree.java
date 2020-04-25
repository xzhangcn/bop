/**
 * created:    2018/04/17
 * <p>
 * {@code KdTree} is a data type to represent a set of points in the unit square (all points have x- and y-coordinates between 0 and 1).
 * Use 2d-tree to find all of the points contained in a query rectangle and nearest-neighbor search (find a closest point to a query point).
 *
 * A 2d-tree is a generalization of a BST to two-dimensional keys. The idea is to build a BST with points in the nodes,
 * using the x- and y-coordinates of the points as keys in strictly alternating sequence.
 *
 * Search and insert.
 * The algorithms for search and insert are similar to those for BSTs, but at the root we use the x-coordinate
 * (if the point to be inserted has a smaller x-coordinate than the point at the root, go left; otherwise go right);
 * then at the next level, we use the y-coordinate (if the point to be inserted has a smaller y-coordinate
 * than the point in the node, go left; otherwise go right); then at the next level the x-coordinate, and so forth.
 *
 * @author Xiaoyu Zhang
 */

public class KdTree {
    private Node root;
    private double xmin, ymin, xmax, ymax;
    private int size;

    private static class Node {
        private final Point2D p;        // the point
        private final RectHV rect;      // the axis-aligned rectangle corresponding to this node
        private Node lb;                // the left/bottom subtree
        private Node rt;                // the right/top subtree

        public Node(Point2D p, RectHV rect) {
            this.p = p;
            this.rect = rect;
            this.lb = null;
            this.rt = null;
        }
    }

    /**
     * construct an empty set of points
     */
    public KdTree() {
        root = null;
        xmin = 0.0;
        ymin = 0.0;
        xmax = 1.0;
        ymax = 1.0;
        size = 0;
    }


    /**
     * is the set empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * number of points in the set
     */
    public int size() {
        return size;
    }

    /**
     * add the point to the tree
     */
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("calls insert() with a null point");

        root = insert(root, p, true);
        // size++;

        // reset to the coordinates of unit square
        xmin = 0.0;
        ymin = 0.0;
        xmax = 1.0;
        ymax = 1.0;
    }

    private Node insert(Node x, Point2D p, boolean vertical) {
        if (x == null) {
            RectHV rect = new RectHV(xmin, ymin, xmax, ymax);
            StdOut.println("point " + size + ": " + p);
            StdOut.println("corresponding orthogonal rectangle: " + rect + "\n");

            size++;
            return new Node(p, rect);
        }

        if (x.p.equals(p)) {
            StdOut.println("inserting the one already in the kdtree: " + p);
            return x;
        }

        if (vertical) {
            if (x.p.x() > p.x()) {
                xmax = x.p.x();
                x.lb = insert(x.lb, p, false);
            }
            else /* if (x.p.x() < p.x()) */ {
                xmin = x.p.x();
                x.rt = insert(x.rt, p, false);
            }
        } else {
            if (x.p.y() > p.y()) {
                ymax = x.p.y();
                x.lb = insert(x.lb, p, true);
            }
            else /* if (x.p.y() < p.y()) */ {
                ymin = x.p.y();
                x.rt = insert(x.rt, p, true);
            }
        }

        return x;
    }


    /**
     * does the set contain point p?
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument to contains() is null");

        return contains(root, p, true);
    }

    private boolean contains(Node x, Point2D p, boolean vertical) {
        if (x == null) return false;

        if (x.p.equals(p))
            return true;

        if (vertical) {
            if (x.p.x() > p.x())
                return contains(x.lb, p, false);
            else /* if (x.p.x() > p.x()) */
                return contains(x.rt, p, false);

        } else {
            if (x.p.y() > p.y())
                return contains(x.lb, p, true);
            else /* if (x.p.y() > p.y()) */
                return contains(x.rt, p, true);
        }


        // return false;
    }

    /**
     * draw all points to standard draw
     */
    public void draw() {
        // draw the unit squre
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        // StdDraw.setPenRadius(0.005);
        RectHV unitRect = new RectHV(0.0, 0.0, 1.0, 1.0);
        unitRect.draw();

        draw(root, true);
    }

    private void draw(Node x, boolean vertical) {
        if (x == null) return;

        // Draw the point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.015);

        // StdOut.println("point: " + x.p);
        // StdOut.println("corresponding orthogonal rectangle: " + x.rect);
        x.p.draw();

        Point2D p1, p2;

        if (vertical) {
            // Draw the line segment
            p1 = new Point2D(x.p.x(), x.rect.ymin());
            p2 = new Point2D(x.p.x(), x.rect.ymax());
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.005);
            p1.drawTo(p2);

            if (x.lb != null)
                draw(x.lb, false);

            if (x.rt != null)
                draw(x.rt, false);
        }
        else {
            // Draw the line segment
            p1 = new Point2D(x.rect.xmin(), x.p.y());
            p2 = new Point2D(x.rect.xmax(), x.p.y());
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.setPenRadius(0.005);
            p1.drawTo(p2);

            if (x.lb != null)
                draw(x.lb, true);

            if (x.rt != null)
                draw(x.rt, true);
        }
    }

    /**
     * all points that are inside the rectangle (or on the boundary)
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        // SET<Point2D> rangeSet = new SET<Point2D>();
        Queue<Point2D> queue = new Queue<Point2D>();

        // range(root, rect, rangeSet);
        queue = range(root, rect, queue);

        // return rangeSet;
        return queue;
    }

    private Queue<Point2D> range(Node x, RectHV rect, /* SET<Point2D> rangeSet */ Queue<Point2D> queue) {
        if (x == null) return queue;

        if (x.rect.intersects(rect)) {
            if (rect.contains(x.p)) {
                // rangeSet.add(x.p);
                queue.enqueue(x.p);
            }

            /*
            range(x.lb, rect, rangeSet);
            range(x.rt, rect, rangeSet);
            */
            queue = range(x.lb, rect, queue);
            queue = range(x.rt, rect, queue);
        }

        return queue;
    }

    /**
     * a nearest neighbor in the set to point p; null if the set is empty
     */
    public Point2D nearest(Point2D p) {
        if (p == null)
            throw new IllegalArgumentException();

        if (isEmpty())
            return null;

        Point2D neighbor;
        double min = Double.POSITIVE_INFINITY;

        neighbor = nearest(root, p, true, null, min);

        return neighbor;
    }

    private Point2D nearest(Node x, Point2D p, boolean vertical, Point2D neighbor, double min) {
        // if the closest point discovered so far is closer than the distance
        // between the query point and the rectangle corresponding to a node,
        // there is no need to explore that node (or its subtrees).
        if (x == null || min < x.rect.distanceSquaredTo(p)) return neighbor;

        double d = p.distanceSquaredTo(x.p);
        if (d < min) {
            min = d;
            neighbor = x.p;
        }

         StdOut.println("current point: " + x.p);
         StdOut.println("current neighbor:" + neighbor);
         StdOut.println("min: " + min);
         StdOut.println("d: " + d + "\n");

        if (vertical) {
            if (x.p.x() > p.x()) {
                neighbor = nearest(x.lb, p, false, neighbor, min);

                min = p.distanceSquaredTo(neighbor);
                neighbor = nearest(x.rt, p, false, neighbor, min);
            }
            else {
                neighbor = nearest(x.rt, p, false, neighbor, min);

                min = p.distanceSquaredTo(neighbor);
                neighbor = nearest(x.lb, p, false, neighbor, min);
            }
        } else {
            if (x.p.y() > p.y()) {
                neighbor = nearest(x.lb, p, true, neighbor, min);

                min = p.distanceSquaredTo(neighbor);
                neighbor = nearest(x.rt, p, true, neighbor, min);
            }
            else {
                neighbor = nearest(x.rt, p, true, neighbor, min);

                min = p.distanceSquaredTo(neighbor);
                neighbor = nearest(x.lb, p, true, neighbor, min);
            }
        }

        return neighbor;
    }


    /**
     * unit testing of the methods (optional)
     */
    public static void main(String[] args) {
        // initialize the two data structures with point from file

        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        StdOut.println("size: " + kdtree.size());

        // Point2D query = new Point2D(0.375, 0.125);
        Point2D query = new Point2D(0.417, 0.362);
        // kdtree.insert(query);
        StdOut.println("size: " + kdtree.size());
        // kdtree.draw();

        if (kdtree.contains(query))
            StdOut.println("kdtree contains the point: " + query);
        else
            StdOut.println("kdtree does not contain the point: " + query);


         // process nearest neighbor queries
         StdDraw.enableDoubleBuffering();

         Point2D query2 = new Point2D(0.2, 0.3);

         // draw all of the points

         StdDraw.clear();
         StdDraw.setPenColor(StdDraw.BLACK);
         StdDraw.setPenRadius(0.01);
         kdtree.draw();

         // draw the query point

         StdDraw.setPenRadius(0.03);
         StdDraw.setPenColor(StdDraw.RED);
         query2.draw();
         StdDraw.setPenRadius(0.02);

         // draw in blue the nearest neighbor (using kd-tree algorithm)
         StdDraw.setPenColor(StdDraw.BLUE);
         kdtree.nearest(query2).draw();
         StdDraw.show();
         StdDraw.pause(40);
    }
}

