import java.util.Arrays;

public class FastCollinearPoints {
    private LineSegment[] lineSegmentArray;
    private Point[] pts;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new java.lang.NullPointerException();

        // Null check
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException();
            else pts[i] = points[i];
        }

        //Duplicate check needed


        for (int i = 0; i < pts.length; i++) {
            // Sort pts array using comparator, according to the next point in ptsNullCheck.
            Arrays.sort(pts, pts[i].slopeOrder());
        }


    }

    // the number of line segments
    public int numberOfSegments() {
        return 0;
    }

    // the line segments
    public LineSegment[] segments() {
        return new LineSegment[1];
    }
}