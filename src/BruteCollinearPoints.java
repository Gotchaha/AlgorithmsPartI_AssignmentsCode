import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;


public class BruteCollinearPoints {

    private int segNum = 0;
    private SegNode first;

    // Define constructors after static and instance variables but before methods.
    public BruteCollinearPoints(Point[] points) {

        if (points == null) arguCornerCase();
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) arguCornerCase();
        }
        for (int j = 0; j < points.length; j++) {
            for (int k = j+1; k < points.length; k++) {
                if (points[j].compareTo(points[k]) == 0) arguCornerCase();
            }
        }
        if (points.length < 4) return;

        Point[] clone = new Point[points.length];
        for (int c = 0; c < points.length; c++) {
            clone[c] = points[c];
        }

        Arrays.sort(clone);

        for (int i = 0; i < clone.length-3; i++) {
            for (int j = i+1; j < clone.length - 2; j++) {
                double slopeIJ = clone[i].slopeTo(clone[j]);
                for (int k = j+1; k < clone.length - 1; k++) {
                    if (Double.compare(slopeIJ, clone[i].slopeTo(clone[k])) == 0) {
                        double slopeIK = clone[i].slopeTo(clone[k]);
                        for (int m = k+1; m < clone.length; m++) {
                            if (Double.compare(slopeIK, clone[i].slopeTo(clone[m])) == 0) {
                                segAdd(new LineSegment(clone[i], clone[m]));
                            }
                        }
                    }
                }
            }
        }
    }

    private class SegNode {
        LineSegment lineSeg;
        SegNode next;
    }

    private void segAdd(LineSegment e) {
        SegNode oldFirst = first;
        first = new SegNode();
        first.lineSeg = e;
        first.next = oldFirst;
        segNum++;
    }

    private void arguCornerCase() {
        throw new IllegalArgumentException();
    }

    public int numberOfSegments() {
        return segNum;
    }

    public LineSegment[] segments() {
        LineSegment[] segArray = new LineSegment[segNum];
        SegNode it = first;
        for (int i = 0; i < segArray.length; i++) {
            if (it == null) break;
            segArray[i] = it.lineSeg;
            it = it.next;
        }
        return segArray;
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
