 import edu.princeton.cs.algs4.In;
 import edu.princeton.cs.algs4.ResizingArrayQueue;
 import edu.princeton.cs.algs4.StdDraw;
 import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private ResizingArrayQueue<LineSegment> segments = new ResizingArrayQueue<>();

    public FastCollinearPoints(Point[] points) {

        Point[] setOfPoints = new Point[points.length];

        if (points == null)
            throw new NullPointerException();

        System.arraycopy(points, 0, setOfPoints, 0, points.length);

        for (int i = 0; i < setOfPoints.length; i++) {
            if (setOfPoints[i] == null)
                throw new NullPointerException();
        }

        Arrays.sort(setOfPoints);
        // duplicate points
        for (int i = 1; i < setOfPoints.length; i++) {
            if (setOfPoints[i].compareTo(setOfPoints[i-1]) == 0)
                throw new IllegalArgumentException();
        }

        Point[] copyPoints = new Point[setOfPoints.length];

        for (int i = 0; i < setOfPoints.length; i++) {
            System.arraycopy(setOfPoints, 0, copyPoints, 0, setOfPoints.length);
            Arrays.sort(copyPoints, setOfPoints[i].slopeOrder()); // nlg n



            for (int j = 1; j < copyPoints.length - 2; j++) { // n
                double slope = setOfPoints[i].slopeTo(copyPoints[j]); // 0 and 2
                int k = j + 1, copy = j;
                int size = segments.size();

                while (k < copyPoints.length &&
                        slope == copyPoints[0].slopeTo(copyPoints[k])) {
                    k++;
                }

                // Check if the sequence length is greater than or equal to 3
                if (k - copy >= 3 && copyPoints[0].compareTo(copyPoints[copy]) < 0) {
                    segments.enqueue(new LineSegment(setOfPoints[i], copyPoints[k-1]));
                }

                if (segments.size() > size) {
                    break;
                }
            } // inner-loop

        } // outer-loop

    }

    public LineSegment[] segments() {
        LineSegment[] ls = new LineSegment[segments.size()];
        int i = 0;
        for (LineSegment seg: segments) {
            ls[i++] = seg;
        }
        return ls;
    }

    public int numberOfSegments() {
        return segments.size();
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
