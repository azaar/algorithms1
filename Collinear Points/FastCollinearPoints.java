/*************************************************************************
 *  Compilation:  javac FastCollinearPoints.java
 *  Execution:    none
 *  Dependencies: Point.java
 *
 *  Implementation of program that find every (maximal) line segment that
 *  connects a subset of 4 or more of the points in a given set of n distinct
 *  points in the plane.
 *
 *  Performance requirement. The order of growth of the running time of program
 *  should be n^2 log n in the worst case and it should use space proportional
 *  to n plus the number of line segments returned. FastCollinearPoints should
 *  work properly even if the input has 5 or more collinear points.
 *
 *************************************************************************/

/**
 *
 * @author Maxim Butyrin
 *
 */

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> s;

    /**
     * Finds all line segments containing 4 or more points.
     *
     * @param  points array of points
     * @throws NullPointerException if the array or any point in it is null
     * @throws IllegalArgumentException if any duplicate points in the array
     */
    public FastCollinearPoints(Point[] points) {

        s = new ArrayList<>();
        Point[] temp = points.clone();
        Arrays.sort(temp);
        pointChecker(temp);

        for (int i = 0; i < temp.length - 3; i++) {
            Arrays.sort(temp);
            Arrays.sort(temp, temp[i].slopeOrder());

            // points for first slope (their indices in temp[])
            int origin = 0;
            int first = 1;

            for (int last = 2; last < temp.length; last++) {
                // searching for last collinear point
                while (last < temp.length
                        && temp[origin].slopeOrder().compare(temp[first], temp[last]) == 0) {

                    last++;
                }

                // adding segment
                if (last - first >= 3 && temp[origin].compareTo(temp[first]) < 0) {
                    s.add(new LineSegment(temp[origin], temp[last - 1]));
                }

                // for next slope
                first = last;
            }
        }
    }

    // check for null array, null or duplicate points in the sorted array
    private void pointChecker(Point[] points) {
        if (points == null) {
            throw new java.lang.NullPointerException();
        }

        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null) {
                throw new java.lang.NullPointerException();
            }
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
        }
    }

    /**
     * Returns the number of line segments in the array
     * @return the number of line segments in the array
     */
    public int numberOfSegments() {
        return s.size();
    }

    /**
     * Returns an array of the line segments
     * @return array of the line segments
     */
    public LineSegment[] segments() {
        return s.toArray(new LineSegment[s.size()]);
    }
}
