import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    final private ArrayList<LineSegment> jSegments = new ArrayList<>();

    public FastCollinearPoints(Point[] points) {
        // check corner cases
        if (points == null)
            throw new IllegalArgumentException("Null Argument");

        Point[] ptsCopy = new Point[points.length];
        for (int n = 0; n < points.length; n++) {
            if (points[n] == null) throw new IllegalArgumentException("Null Data");
            ptsCopy[n] = points[n];
        }



        if (hasDuplicate(ptsCopy)) {
            throw new IllegalArgumentException("Duplicate points");
        }

        for (int i = 0; i < ptsCopy.length - 3; i++) {

            Arrays.sort(ptsCopy, ptsCopy[i].slopeOrder());

            for (int p = 0, first = 1, last = 2; last < ptsCopy.length; last++) {
                // find last collinear to p point
                while (last < ptsCopy.length
                        && Double.compare(ptsCopy[p].slopeTo(ptsCopy[first]), ptsCopy[p].slopeTo(ptsCopy[last])) == 0) {
                    last++;
                }
                // if found at least 3 elements, make segment if it's unique
                if (last - first >= 3 && ptsCopy[p].compareTo(ptsCopy[first]) < 0) {
                    jSegments.add(new LineSegment(ptsCopy[p], ptsCopy[last - 1]));
                }
                // Try to find next
                first = last;
            }
        }
        // finds all line segments containing 4 or more points
    }

    // the number of line segments
    public int numberOfSegments() {
        return jSegments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return jSegments.toArray(new LineSegment[jSegments.size()]);
    }

    // test the whole array fo duplicate points
    private boolean hasDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                return true;
            }
        }
        return false;
    }

}