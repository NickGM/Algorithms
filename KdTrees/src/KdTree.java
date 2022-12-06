import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.LinkedList;
import java.util.List;

public class KdTree {

    private Node root;
    private int size;
    private class Node {
        private final Point2D p;
        private Node lb, rt;

        Node(Point2D p) {
            this.p = p;
        }


    }
    // construct an empty set of points
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        root = insert(root, p, 0);
    }

    private Node insert(Node n, Point2D p, int lev) {
        if (n == null) {
            size++;
            return new Node(p);
        }

        double x = p.x();
        double y = p.y();
        double nx = n.p.x();
        double ny = n.p.y();

        //level is a proxy for vert/horizontal
        if (lev % 2 == 0) {
            if (x > nx) n.rt = insert(n.rt, p, lev++);
            else if (x < nx) n.lb = insert(n.lb, p, lev++);
            else if (y != ny) n.rt = insert(n.rt, p, lev++);//arbitrarily go right if x is equal
        }
        else {
            if (y > ny) n.rt = insert(n.rt, p, lev++);
            else if (y < ny) n.lb = insert(n.lb, p, lev++);
            else if (x != nx) n.lb = insert(n.lb, p, lev++); //arbitrarily go left if y is equal
        }
        return n;
    }

    public           boolean contains(Point2D p)            // does the set contain point p?
    public              void draw()                         // draw all points to standard draw
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty

    public static void main(String[] args)                  // unit testing of the methods (optional)
}