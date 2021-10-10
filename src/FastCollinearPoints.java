import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;


public class FastCollinearPoints {
    private int segNum = 0;
    /* use arraylist to store the LineSegment */
//    private ArrayList<LineSegment> segList = new ArrayList<>();

    private SegNode first;
    private int min = -1, max = -1;

    public FastCollinearPoints(Point[] points) {
        // corner case check
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

        Point[] sup = new Point[points.length];

        /* need clone array to do operations (since it's required that the data type is immutable)
        <==> data type should have no side effects unless documented in API */
        Point[] clone = new Point[points.length];
        for (int c = 0; c < points.length; c++) {
            clone[c] = points[c];
        }

/* use hashset to check the corner case (however it's not permitted) */
//        Set<Point> checkSet = new HashSet<>();
//        for (Point p :
//                points) {
//            if (p == null) arguCornerCase();
//            checkSet.add(p);
//        }
//        if (checkSet.size() != points.length) arguCornerCase();


        for (int i = 0; i < points.length; i++) {
            // System.out.println("");  //test

            Point origin = points[i];

            // System.out.println("the origin point is" + origin);  //test

            Arrays.sort(clone, origin.slopeOrder());

            double slope = origin.slopeTo(clone[1]);
            int count = 1;  // important initialization value
            // endPointInx = -1;
            boolean flag = false;

            // clone[0] will be origin itself since the slope w.r.t itself is -infinity

            // System.out.printf("points[0] is %s\n", points[0]);  //test

            for (int j = 2; j < clone.length; j++) {

                // System.out.printf("the current point is %s; the slope is %f;\n", points[j], slope);  //test

                if (Double.compare(slope, origin.slopeTo(clone[j])) == 0) {
                    count++;
                    if (count >= 3) {
                        flag = true;
                        // endPointInx = j;

                        /* vertical case (the slope is positive infinity, at the end) ,
                        * maybe the code block can be optimized, as a method or changing the logical order
                        * here j is the part of the line segment
                        * */
                        if (j + 1 == clone.length) {
                            for (int k = 0; k < count; k++) {
                                sup[k] = clone[j-k];
                            }
                            sup[count] = origin;
                            // Arrays.sort(sup, 0, count+1);  too much cost
                            findMinMax(sup, 0, count+1);

                            // avoid duplicate
                            if (sup[min].compareTo(origin) == 0) {
                                segAdd(new LineSegment(sup[min], sup[max]));

                                // System.out.printf("--Seg Added--: %s\n", first.lineSeg);  // test

                            }
                        }
                    }
                }
                else {  // slope change
                    if (flag) {
                        for (int k = 1; k <= count; k++) {
                            sup[k] = clone[j-k];
                        }
                        sup[0] = origin;
                        // Arrays.sort(sup, 0, count+1); too much cost
                        findMinMax(sup, 0, count+1);

                        // avoid duplicate
                        if (sup[min].compareTo(origin) == 0) {
                            segAdd(new LineSegment(sup[min], sup[max]));

                            // System.out.printf("--Seg Added--: %s\n", first.lineSeg);  // test

                        }
                    }
                    count = 1;
                    flag = false;
                    slope = origin.slopeTo(clone[j]);

                    // System.out.println("slope change, new slope is" + slope);  //test

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

    private void findMinMax(Point[] a, int i, int j) {
        min = max = i;
        for (int k = i+1; k < j; k++) {
            if (a[k].compareTo(a[min]) < 0) min = k;
            if (a[k].compareTo(a[max]) > 0) max = k;
        }
    }

    private void arguCornerCase() {
        throw new IllegalArgumentException();
    }

    public int numberOfSegments() {
        return segNum;
    }

    public LineSegment[] segments() {
        LineSegment[] segArray = new LineSegment[segNum];  // do not set as segNum+1
        SegNode it = first;
        for (int i = 0; i < segArray.length; i++) {
            if (it == null) break;

            // System.out.printf("segM_%d : %s\n", i, it.lineSeg);  // test

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
