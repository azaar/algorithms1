/****************************************************************************
 *  Compilation:  javac PointSET.java
 *  Execution:    none
 *  Dependencies: algs4.jar
 *
 *  Mutable data type that represents a set of points in the unit square
 *  (all points have x- and y-coordinates between 0 and 1).
 *
 ****************************************************************************/

/**
 *
 * @author Maxim Butyrin
 *
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class PointSET {

    private SET<Point2D> points;

    /**
     * Construct an empty set of points.
     */
    public PointSET() {
        points = new SET<>();

    }

    /**
     * Checks is the set empty.
     *
     * @return true  if set is empty
     *         false otherwise
     */
    public boolean isEmpty() {
        return points.isEmpty();
    }

    /**
     * Number of points in the set.
     *
     * @return int number of points in the set
     */
    public int size() {
        return points.size();
    }

    /**
     * Add the point to the set (if it is not already in the set.
     *
     * @param p point to add to the set
     * @throws NullPointerException if given point p is null
     */
    public void insert(Point2D p)  {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        points.add(p);
    }

    /**
     * Checks if the set contain point p.
     *
     * @param  p     point
     * @return true  if the set contains this point
     *         false otherwise
     * @throws NullPointerException if given point p is null
     */
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        return points.contains(p);
    }

    /**
     * Draw all points to standard draw.
     */
    public void draw() {
        for (Point2D point :points) {
            StdDraw.point(point.x(), point.y());
        }
    }

    /**
     * All points that are inside the rectangle.
     *
     * @param rect given rectangle
     * @return Iterable all points that are inside the rectangle
     * @throws NullPointerException if given rectangle rect is null
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new java.lang.NullPointerException();
        }
        List<Point2D> rectPoints = new ArrayList<>();
        for (Point2D point :points) {
            if (rect.contains(point)) {
                rectPoints.add(point);
            }
        }
        return rectPoints;
    }

    /**
     * Returns nearest neighbor in the set to point p; null if the set is empty.
     *
     * @param p point to find its nearest neighbor
     * @return Point2D  nearest neighbor
     *         null     if the set is empty
     * @throws NullPointerException if given rectangle rect is null
     */
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        if (points.isEmpty()) {
            return null;
        }
        Point2D nearest = null;
        double distance = Double.MAX_VALUE;
        for (Point2D neighbor : points) {
            if (p.distanceTo(neighbor) < distance) {
                nearest = neighbor;
                distance = p.distanceTo(neighbor);
            }

        }
        return nearest;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        Point2D a = new Point2D(0, 0);
        Point2D b = new Point2D(0, 0.11);
        Point2D c = new Point2D(0, 0.12);
        PointSET s = new PointSET();
        s.insert(b);
        s.insert(c);
        System.out.println(s.nearest(a));

    }
}
