/****************************************************************************
 *  Compilation:  javac KdTree.java
 *  Execution:    none
 *  Dependencies: algs4.jar
 *
 *  Mutable data type that represents a set of points in the unit square
 *  (all points have x- and y-coordinates between 0 and 1) that uses a 2d-tree.
 *  A 2d-tree is a generalization of a BST to two-dimensional keys.
 *  The idea is to build a BST with points in the  nodes, using the x- and
 *  y-coordinates of the points as keys in strictly alternating sequence.
 *
 ****************************************************************************/

/**
 *
 * @author Maxim Butyrin
 *
 */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;
    private static final RectHV RECTANGLE = new RectHV(0, 0, 1, 1);
    private int size;
    private Node tree;

    private class Node {

        private Point2D point;
        private RectHV rect;
        private Node leftBelow;
        private Node rightAbove;
        private boolean orient;

        Node(Point2D point, RectHV rect, boolean orient) {
            this.point = point;
            this.rect = rect;
            this.orient = orient;
        }
    }

    public KdTree() {
        tree = null;
        size = 0;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p)  {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        tree = insertNode(tree, p, RECTANGLE, VERTICAL);
    }

    private Node insertNode(Node node, Point2D point, RectHV rect, boolean orient) {
        if (node == null) {
            size++;
            return new Node(point, rect, orient);

        }
        if (node.point.equals(point)) {
            return node;
        }

        RectHV r;

        if (orient == VERTICAL) {

            // add point to the right side
            if (point.x() >= node.point.x()) {

                r = new RectHV(node.point.x(), rect.ymin(), rect.xmax(), rect.ymax());
                node.rightAbove = insertNode(node.rightAbove, point, r, !node.orient);

            // add point to the left side
            } else { // point.x() < node.point.x()

                r = new RectHV(rect.xmin(), rect.ymin(), node.point.x(), rect.ymax());
                node.leftBelow = insertNode(node.leftBelow, point, r, !node.orient);
            }

        } else { // orient == HORIZONTAL

            // add point to the top side
            if (point.y() > node.point.y()) {

                r = new RectHV(rect.xmin(), node.point.y(), rect.xmax(), rect.ymax());
                node.rightAbove = insertNode(node.rightAbove, point, r, !node.orient);

            // add point to the bottom side
            } else { // point.y() < node.point.y()

                r = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.point.y());
                node.leftBelow = insertNode(node.leftBelow, point, r, !node.orient);
            }
        }
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }

        Node check = tree;

        while (check != null) {
            if (check.point.equals(p)) {
                return true;
            }
            if (check.orient == VERTICAL && p.x() >= check.point.x() ||
                    check.orient == HORIZONTAL && p.y() > check.point.y()) {

                check = check.rightAbove;
            } else { // (check.orient == VERTICAL && p.x() < check.point.x() ||
                     //  check.orient == HORIZONTAL && p.y() > check.point.y())

                check = check.leftBelow;
            }
        }
        return false;
    }

    // draw all points to standard draw
    public void draw() {
        draw(tree);
    }

    private void draw(Node node) {
        if (node == null)
            return;
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.point.draw();
        StdDraw.setPenRadius();
        if (node.orient == VERTICAL) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }
        draw(node.rightAbove);
        draw(node.leftBelow);
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> points = new Queue<>();
        range(tree, rect, points);
        return points;
    }

    private void range(Node node, RectHV rect, Queue<Point2D> queue) {
        if (node == null) {
            return;
        }
        if (rect.contains(node.point)) {
            queue.enqueue(node.point);
        }
        if (node.rightAbove != null && rect.intersects(node.rightAbove.rect)) {
            range(node.rightAbove, rect, queue);
        }
        if (node.leftBelow != null && rect.intersects(node.leftBelow.rect)) {
            range(node.leftBelow, rect, queue);
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new java.lang.NullPointerException();
        }
        if (isEmpty()) {
            return null;
        }

        return nearest(tree, p, tree.point, VERTICAL);
    }

    private Point2D nearest(Node node, Point2D point, Point2D nearestPoint, boolean orient) {

        if (node == null) {
            return nearestPoint;
        }

        // distance to current nearest point
        double minDistance = point.distanceSquaredTo(nearestPoint);
        // distance to current node point
        double currentDistance = point.distanceSquaredTo(node.point);
        // distance to current node rectangle
        double distRect = node.rect.distanceSquaredTo(point);


        // if distance to rectangle greater than distance to closest point,
        // rectangle cannot contain any closer points
        if (distRect > minDistance) {
            return nearestPoint;
        }

        // check if current node point is closer
        // if so - make it new nearestPoint
        if (minDistance > currentDistance) {
            nearestPoint = node.point;
        }

        Node first;
        Node second;
        // check leftBelow first, rightAbove after
        if (orient == VERTICAL && point.x() < node.point.x() ||
                orient == HORIZONTAL && point.y() < node.point.y()) {

            first = node.leftBelow;
            second = node.rightAbove;
        } else {

            first = node.rightAbove;
            second = node.leftBelow;
        }
        nearestPoint = nearest(first, point, nearestPoint, !node.orient);        
        nearestPoint = nearest(second, point, nearestPoint, !node.orient);

        return nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {

    }
}
