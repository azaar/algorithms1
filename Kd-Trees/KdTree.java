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

public class KdTree {

    private static final boolean VERTICAL = true;
    private static final boolean HORIZONTAL = false;
    private static final RectHV RECTANGLE = new RectHV(0, 0, 1, 1);
    private int size;
    private Node tree;

    private class Node {
        private Point2D point;
        private RectHV rect;
        private Node leftBelow, rightAbove;
        int N;
        boolean orient;

        Node(Point2D point, RectHV rect, boolean orient) {
            this.point = point;
            this.rect = RECTANGLE;
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
        if (!contains(p)) {
            tree = insertNode(tree, p, true);
            size++;
        }
    }

    private Node insertNode(Node node, Point2D point, boolean orient) {
        if (node == null) {
            return new Node(point, RECTANGLE, VERTICAL);
        }
        if (node.point.equals(point)) {
            return node;
        }
        if (node.orient == VERTICAL && point.x() >= node.point.x() ||
                node.orient == HORIZONTAL && point.y() > node.point.y()) {

            node.rightAbove = insertNode(node.rightAbove, point, !node.orient);

        } else {

            node.leftBelow = insertNode(node.leftBelow, point, !node.orient);
            }
        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        Node check = tree;

        while (check != null) {
            if (check.point.equals(p)) {
                return true;
            }
            if (check.orient == VERTICAL && p.x() >= check.point.x() ||
                    check.orient == HORIZONTAL && p.y() > check.point.y()) {

                check = check.rightAbove;
            } else {
//            if (check.orient == VERTICAL && p.x() < check.point.x() ||
//                    check.orient == HORIZONTAL && p.y() > check.point.y()) {

                check = check.leftBelow;
            }
        }

        return false;
    }

    // draw all points to standard draw
    public void draw() {

    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        Queue<Point2D> range = new Queue<>();

        return range;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        return null;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        Point2D a = new Point2D(0.1, 0.1);
        Point2D b = new Point2D(0.2, 0.2);
        Point2D c = new Point2D(0.3, 0.3);
        Point2D d = new Point2D(0.1, 0.4);
        KdTree tree = new KdTree();
        tree.insert(d);
        tree.insert(a);
        tree.insert(c);
        tree.insert(c);
        System.out.println("a " + tree.contains(a));
        System.out.println("b " + tree.contains(b));
        System.out.println("c " + tree.contains(c));
        System.out.println("d " + tree.contains(d));
        System.out.println("size " + tree.size());

    }

}
