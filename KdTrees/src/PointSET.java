import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
//import java.util.LinkedList;
//import java.util.List;
import java.util.LinkedList;
import java.util.TreeSet;


public class PointSET {

    private TreeSet<Point2D> points;
    // construct an empty set of points
    public PointSET() {
        points = new TreeSet<Point2D>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return points.isEmpty();
    }

    // number of points in the set
    public int size() {
        return points.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        nullTest(p);
        if (!points.contains(p)) {
            points.add(p);
        }
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        nullTest(p);
        return points.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : points)
            p.draw();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        nullTest(rect);
        double xmin = rect.xmin();
        double ymin = rect.ymin();
        double xmax = rect.xmax();
        double ymax = rect.ymax();

        LinkedList<Point2D> pointsInside = new LinkedList<Point2D>();

        for (Point2D p : points) {
            double x = p.x();
            double y = p.y();
            if (x >= xmin && x <= xmax && y >= ymin && y <= ymax) {
                pointsInside.add(p);
            }
        }
        return pointsInside;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        nullTest(p);
        if (isEmpty()) {
            return null;
        }

        double nearestDist = Double.MAX_VALUE;
        Point2D nearestP = null;
        for (Point2D ourPoint : points) {
            if (ourPoint.distanceSquaredTo(p) < nearestDist) {
                nearestDist = ourPoint.distanceSquaredTo(p);
                nearestP = ourPoint;
            }
        }
        return nearestP;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        //TODO
    }

    private void nullTest(Object o) {
        if (o == null) throw new NullPointerException("Null argument");
    }
}
