import edu.princeton.cs.algs4.Stack;

import java.lang.reflect.Array;
import java.util.Arrays;

public class BruteCollinearPoints {
    // finds all line segments containing 4 points
    private Point[] pts;
    private int segCount;
    //Should really use a resizing array but too lazy
    private Stack<LineSegment> lineSegStack;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();
        pts = new Point[points.length];
        for (int i = 0; i < points.length; i++) pts[i] = points[i];

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
                            lineSegStack.push(new LineSegment(subset[0], subset[3]));
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
        LineSegment[] segs = new LineSegment[lineSegStack.size()];
        for (int i = 0; i < lineSegStack.size(); i++)
            segs[i] = lineSegStack.pop();
        return segs;
    }
}