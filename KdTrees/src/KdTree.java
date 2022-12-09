import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.LinkedList;
import java.util.List;

public class KdTree {

    private Node root;
    private int size;
    private class Node {
        private Point2D p;
        private Node lb, rt;

        int level;

        Node(Point2D p, int level) {
            nullTest(p);
            this.p = p;
            this.level = level;
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
        nullTest(p);
        root = insert(root, p, 0);
    }

    private Node insert(Node n, Point2D p, int lev) {
        if (n == null) {
            size++;
            return new Node(p, lev);
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
    // does the set contain point p?
    public boolean contains(Point2D p) {
        nullTest(p);
        return contains(root, p);
    }

    private boolean contains(Node n, Point2D p) {
        while (n != null) {
            if (n.level % 2 == 0) {
                if (p.x() > n.p.x()) n = n.rt;
                else if (p.x() < n.p.x()) n = n.lb;
                else if (p.y() != n.p.y()) n = n.rt;
                else return true; //else it is equal
            }
            else {
                if (p.y() > n.p.y()) n = n.rt;
                else if (p.y() < n.p.y()) n = n.lb;
                else if (p.x() != n.p.x()) n = n.lb;
                else return true; //else it is equal
            }
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, 0.0, 0.0, 1.0, 1.0);
    }

    //Copied this, probably wont check it
    private void draw(Node n, double xmin, double ymin, double xmax, double ymax) {
        if (n == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        n.p.draw();

        if (n.level % 2 == 0) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            RectHV rect = new RectHV(n.p.x(), ymin, n.p.x(), ymax);
            rect.draw();
            draw(n.rt, n.p.x(), ymin, xmax, ymax);
            draw(n.lb, xmin, ymin, n.p.x(), ymax);
        }

        if (n.level % 2 != 0) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            RectHV rect = new RectHV(xmin, n.p.y(), xmax, n.p.y());
            rect.draw();
            draw(n.rt, xmin, n.p.y(), xmax, ymax);
            draw(n.lb, xmin, ymin, xmax, n.p.y());
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        nullTest(rect);
        LinkedList<Point2D> pointsInside = new LinkedList<Point2D>();
        RectHV rootRect = new RectHV(0.0, 0.0, 1.0, 1.0);
        range(root, rootRect, rect, pointsInside);
        return pointsInside;
    }

    private void range(Node n, RectHV nRect, RectHV queryRect, LinkedList<Point2D> pointsInside) {
        if (n == null) return;
        if (!nRect.intersects(queryRect)) return;

        if (queryRect.contains(n.p)) pointsInside.push(n.p);

        if (n.level %2 == 0) {
            double ymin = nRect.ymin();
            double ymax = nRect.ymax();

            double xmin = n.p.x();
            double xmax = nRect.xmax();
            range(n.rt, new RectHV(xmin, ymin, xmax, ymax), queryRect, pointsInside);

            xmin = nRect.xmin();
            xmax = n.p.x();
            range(n.lb, new RectHV(xmin, ymin, xmax, ymax), queryRect, pointsInside);
        }
        else {
            double xmin = nRect.xmin();
            double xmax = nRect.xmax();

            double ymin = n.p.y();
            double ymax = nRect.ymax();
            range(n.rt, new RectHV(xmin, ymin, xmax, ymax), queryRect, pointsInside);

            ymin = nRect.ymin();
            ymax = n.p.y();
            range(n.lb, new RectHV(xmin, ymin, xmax, ymax), queryRect, pointsInside);
        }
    }

    public Point2D nearest(Point2D p) {
        nullTest(p);
        if (isEmpty()) return null;

        Node nearestN = new Node(root.p, 0);
        nearestN.lb = root.lb;
        nearestN.rt = root.rt;
        nearestN.level = root.level;
        // axis-aligned rectangle corresponding to the root
        RectHV rootRect = new RectHV(0.0, 0.0, 1.0, 1.0);
        nearest(root, rootRect, nearestN, p);
        return nearestN.p;
    }

    private void nearest(Node h, RectHV hRect, Node nearestN, Point2D queryP) {
        if (h == null) return;

        if (queryP.distanceSquaredTo(h.p) < queryP.distanceSquaredTo(nearestN.p)) {
            nearestN.p = h.p;
        }

        double hx = h.p.x();
        double hy = h.p.y();
        double x = queryP.x();
        double y = queryP.y();
        double xmin, xmax, ymin, ymax;
        if (h.level % 2 == 0) {
            ymin = hRect.ymin();
            ymax = hRect.ymax();

            xmin = hx;
            xmax = hRect.xmax();
            RectHV rtRect = new RectHV(xmin, ymin, xmax, ymax);

            xmin = hRect.xmin();
            xmax = hx;
            RectHV lbRect = new RectHV(xmin, ymin, xmax, ymax);

            if (x >= hx) {
                nearest(h.rt, rtRect, nearestN, queryP);
                if (lbRect.distanceSquaredTo(queryP) < queryP.distanceSquaredTo(nearestN.p)) {
                    nearest(h.lb, lbRect, nearestN, queryP);
                }
            } else {
                nearest(h.lb, lbRect, nearestN, queryP);
                if (rtRect.distanceSquaredTo(queryP) < queryP.distanceSquaredTo(nearestN.p)) {
                    nearest(h.rt, rtRect, nearestN, queryP);
                }
            }
        } else {
            xmin = hRect.xmin();
            xmax = hRect.xmax();

            ymin = hy;
            ymax = hRect.ymax();
            RectHV rtRect = new RectHV(xmin, ymin, xmax, ymax);

            ymin = hRect.ymin();
            ymax = hy;
            RectHV lbRect = new RectHV(xmin, ymin, xmax, ymax);

            if (y >= hy) {
                nearest(h.rt, rtRect, nearestN, queryP);
                if (lbRect.distanceSquaredTo(queryP) < queryP.distanceSquaredTo(nearestN.p)) {
                    nearest(h.lb, lbRect, nearestN, queryP);
                }
            } else {
                nearest(h.lb, lbRect, nearestN, queryP);
                if (rtRect.distanceSquaredTo(queryP) < queryP.distanceSquaredTo(nearestN.p)) {
                    nearest(h.rt, rtRect, nearestN, queryP);
                }
            }
        }
    }


    public static void main(String[] args) {

    }
    private void nullTest(Object o) {
        if (o == null) throw new NullPointerException("Null argument");
    }
}