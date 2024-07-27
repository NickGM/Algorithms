import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private KdNode root;
    private int size;
    private static class KdNode {
        private Point2D p;
        private RectHV r;     // Axis aligned rectangle
        private KdNode left;
        private KdNode right;

        public KdNode(Point2D p, RectHV r) {
            this.p = p;
            this.r = r;
        }

        public boolean EqualsPt(Point2D p) {
            return (this.x() == p.x() && this.y() == p.y());
        }

        public double x() {
            return p.x();
        }

        public double y() {
            return p.y();
        }
    }

    public KdTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException("Null point!");
        root = insert(root, p, 0.0, 0.0, 1.0, 1.0, true);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException("Null point!");
        return contains(root, p, true);
    }

    public void draw() {
        draw(root, true);
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.IllegalArgumentException("Null rect!");
        Queue<Point2D> q = new Queue<Point2D>();
        range(root, rect, q);
        return q;
    }
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException("Null point!");
        if (root == null) return null;
        return nearest(root, p, root.p, true);
    }

    private KdNode insert(KdNode node, Point2D p, double x0, double y0, double x1, double y1, boolean vert)
    {
        if (node == null) {
            RectHV r = new RectHV(x0, y0, x1, y1);
            size++;
            return new KdNode(p, r);
        }

        if (node.EqualsPt(p)) return node;

        // if vertical, then we are comparing x's
        // Understanding how the rects are made makes the whole problem easy.
        if (vert) {
            if (p.x() < node.x())
                node.left = insert(node.left, p, x0, y0, node.x(), y1, !vert);
            else
                node.right = insert(node.right, p, node.x(), y0, x1, y1, !vert);
        } else {
            if (p.y() < node.y())
                node.left = insert(node.left, p, x0, y0, x1, node.y(), !vert);
            else
                node.right = insert(node.right, p, x0, node.y(), x1, y1, !vert);
        }
        return node;
    }

    private boolean contains(KdNode node, Point2D p, boolean vert) {
        //We got to the bottom and didn't find it
        if (node == null) return false;
        else if (node.EqualsPt(p)) return true;
        else {
            if (goLeft(node, p, vert))
                return contains(node.left, p, !vert);
            else
                return contains(node.right, p, !vert);
        }
    }

    private void draw(KdNode node, boolean vert) {
        if (node == null) return;
        // Draw point
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.1);
        node.p.draw();
        // Draw vertical line
        if (vert) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(node.x(), node.r.ymin(), node.x(), node.r.ymax());
        }
        // Draw horizontal line
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(node.r.xmin(), node.y(), node.r.xmax(), node.y());
        }
        // Draw subtrees
        draw(node.left, !vert);
        draw(node.right, !vert);
    }

    private void range(KdNode node, RectHV rec, Queue<Point2D> q) {
        if (node == null) return;
        // If the current point is in the input rectangle, enqueue that point
        if (rec.contains(node.p)) {
            q.enqueue(node.p);
        }

        if (node.r.intersects(rec)) {
            range(node.left, rec, q);
            range(node.right, rec, q);
        }
    }

    private Point2D nearest(KdNode node, Point2D p, Point2D b, boolean vert) {
        Point2D best = b;
        // If there are no more nodes, return the best point found so far
        if (node == null) return best;
        // If the current point is closer than the best point found so far,
        // update the best point
        if (node.p.distanceSquaredTo(p) < best.distanceSquaredTo(p))
            best = node.p;
        // If the current rectangle is closer to p than the best point, check its
        // subtrees
        if (node.r.distanceSquaredTo(p) < best.distanceSquaredTo(p)) {
            // Find which subtree the p is in
            KdNode near;
            KdNode far;
            if (goLeft(node, p, vert)) {
                near = node.left;
                far = node.right;
            }
            else {
                near = node.right;
                far = node.left;
            }
            // Check subtree on the same side as p
            best = nearest(near, p, best, !vert);
            best = nearest(far, p, best, !vert);
        }
        return best;
    }

    private boolean goLeft(KdNode node, Point2D p, boolean vert) {
        return (vert && (p.x() < node.x())) || (!vert && (p.y() < node.y()));
    }

    public static void main(String[] args)
    {
        //- performs incorrect traversal of k-d tree during call to range()
        //    - query rectangle = [0.13, 0.65] x [0.05, 0.45]
        //    - sequence of points inserted:
        //      A  0.7 0.2
        //      B  0.5 0.4
        //      C  0.2 0.3
        //      D  0.4 0.7
        //      E  0.9 0.6
        //    - student k-d tree nodes involved in calls to Point2D methods:
        //      A B C D E
        //    - reference k-d tree nodes involved in calls to Point2D methods:
        //      A B C D
        //    - failed on trial 1 of 1000

        KdTree t = new KdTree();
        t.insert(new Point2D(0.7, 0.2));
        t.insert(new Point2D(0.5, 0.4));
        t.insert(new Point2D(0.2, 0.3));
        t.insert(new Point2D(0.4, 0.7));
        t.insert(new Point2D(0.9, 0.6));

        t.insert(null);
    }
}