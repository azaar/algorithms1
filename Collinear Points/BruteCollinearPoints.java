/*************************************************************************
 *  Compilation:  javac BruteCollinearPoints.java
 *  Execution:    none
 *  Dependencies: Point.java
 *
 *  Implementation of program that find every (maximal) line segment that
 *  connects a subset of 4 or more of the points in a given set of n distinct
 *  points in the plane.
 *
 *  Performance requirement. The order of growth of the running time of
 *  program should be N^4 in the worst case and it should use space proportional
 *  to n plus the number of line segments returned.
 *
 *************************************************************************/

/**
 *
 * @author Maxim Butyrin
 *
 */

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private ArrayList<LineSegment> s;


    /**
     * Finds all line segments containing 4 points.
     *
     * @param  points array of points
     * @throws NullPointerException if the array or any point in it is null
     * @throws IllegalArgumentException if any duplicate points in the array
     */
    public BruteCollinearPoints(Point[] points) {

        Point[] temp = points.clone();
        Arrays.sort(temp);
        pointChecker(temp);

        s = new ArrayList<>();

        for (int i = 0; i < temp.length - 3; i++) {
            for (int j = i + 1; j < temp.length - 2; j++) {
                for (int k = j + 1; k < temp.length - 1; k++) {
                    if (temp[i].slopeTo(temp[j]) == temp[i].slopeTo(temp[k])) {
                        for (int l = k + 1; l < temp.length; l++) {
                            if (temp[i].slopeTo(temp[k]) == temp[i].slopeTo(temp[l])) {
                                s.add(new LineSegment(temp[i], temp[l]));
                            }
                        }
                    }
                }
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
