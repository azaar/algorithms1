/****************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:
 *  Dependencies: algs4.jar
 *
 *  Implementation of double-ended queue with sentinel node.
 *  A double-ended queue or deque is a generalization of a stack and a queue
 *  that supports adding and removing items from either the front or the back
 *  of the data structure.
 *
 *
 ****************************************************************************/

/**
 *
 * @author Maxim Butyrin
 *
 */

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        private Item item;
        private Node next;
        private Node prev;

        public Node(Item i) {
            item = i;
            next = null;
            prev = null;
        }
    }

    private Node sentinel;
    private int size;

    /**
     * Initializes an empty deque.
     */
    public Deque() {
        size = 0;
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    /**
     * Is this deque empty?
     * @return true  if this queue is empty
     *         false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of items in the deque.
     * @return the number of items in the deque
     */
    public int size() {
        return size;
    }

    /**
     * Adds the item to the front of this deque.
     * @param item the item to add
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        Node first = new Node(item);
        first.prev = sentinel;
        first.next = sentinel.next;

        sentinel.next.prev = first;
        sentinel.next = first;
        size++;
    }

    /**
     * Adds the item to the end of this deque.
     * @param item the item to add
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }

        Node last = new Node(item);
        last.prev = sentinel.prev;
        last.next = sentinel;

        sentinel.prev.next = last;
        sentinel.prev = last;
        size++;
    }

    /**
     * Removes and returns item from the front of this deque.
     * @return front item
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item removeFirst() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }

        Node oldfirst = sentinel.next;
        Item olditem = oldfirst.item;
        sentinel.next = oldfirst.next;
        oldfirst.next.prev = sentinel;
        size--;
        oldfirst = null;
        return olditem;
    }

    /**
     * Removes and returns item from the end of this deque.
     * @return end item
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item removeLast() {
        if (size == 0) {
            throw new java.util.NoSuchElementException();
        }

        Node oldlast = sentinel.prev;
        Item olditem = oldlast.item;
        sentinel.prev = oldlast.prev;
        oldlast.prev.next = sentinel;
        size--;
        oldlast = null;
        return olditem;
    }

    /**
     * Returns an iterator over the items from front to end in the deque
     * @return an iterator over items from front to end in the deque
     */
    @Override
    public Iterator<Item> iterator() {
        return new MyListIterator();
    }

    private class MyListIterator implements Iterator<Item> {
        private Node current = sentinel.next;

        @Override
        public boolean hasNext() {
            return current.item != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next() {
            Item item = current.item;
            if (item == null) {
                throw new java.util.NoSuchElementException();
            }
            current = current.next;
            return item;
        }
    }

    // unit testing
    public static void main(String[] args) {

    }
}
