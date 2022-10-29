import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    // finds all line segments containing 4 points
    final private Point[] pts;
    private int segCount;
    final private ArrayList<LineSegment> Segments = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        pts = new Point[points.length];

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            else pts[i] = points[i];
        }

        Arrays.sort(pts);
        checkDuplicatePoints(pts);


        calcCollinearSegments();
    }

    private void calcCollinearSegments() {
        Point[] subset = new Point[4];
        for (int i = 0; i < pts.length; i++) {
            subset[0] = pts[i];
            for (int j = i + 1; j < pts.length; j++) {
                subset[1] = pts[j];
                for (int k = j + 1; k < pts.length; k++) {
                    subset[2] = pts[k];
                    for (int l = k + 1; l < pts.length; l++) {
                        subset[3] = pts[l];
                        Arrays.sort(subset);
                        double slopeA = subset[0].slopeTo(subset[1]);
                        double slopeB = subset[0].slopeTo(subset[2]);
                        double slopeC = subset[0].slopeTo(subset[3]);
                        if (slopeA == slopeB && slopeB == slopeC) {
                            Segments.add(new LineSegment(subset[0], subset[3]));
                            segCount++;
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return segCount;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] segs = new LineSegment[Segments.size()];

        for (int i = 0; i < segCount; i++)
            segs[i] = Segments.get(i);
        return segs;
    }

    private void checkDuplicatePoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException("Duplicated entries in given points");
        }
    }
}