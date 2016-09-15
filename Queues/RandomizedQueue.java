/****************************************************************************
 *  Compilation:  javac RandomizedQueue.java
 *  Execution:
 *  Dependencies: algs4.jar
 *
 *  Implementation of RandomizedQueue
 *  A randomized queue is similar to items stack or queue, except that the item
 *  removed is chosen uniformly at random from items in the data structure.
 *
 ****************************************************************************/

/**
 *
 * @author Maxim Butyrin
 *
 */

import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;     // array of items
    private int n;            // number of items in queue

    /**
     * Initializes an empty queue.
     */
    public RandomizedQueue() {
        items = (Item[]) new Object[2];
        n = 0;
    }

    /**
     * Is this queue empty?
     * @return true  if this queue is empty
     *         false otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Returns the number of items in the queue.
     * @return the number of items in the queue
     */
    public int size() {
        return n;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= n;

        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = items[i];
        }
        items = temp;
    }

    /**
     * Adds the item to this queue.
     * @param item the item to add
     */
    public void enqueue(Item item) {
        if (item == null) {
            throw new java.lang.NullPointerException();
        }
        if (n == items.length) resize(2 * items.length);    // double size of array if necessary
        items[n++] = item;                            // add item
    }

    /**
     * Removes and returns a random item from the queue.
     * @return random item
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item dequeue() {
        // choose random item to return
        if (isEmpty()) throw new NoSuchElementException();
        int randomIndex = StdRandom.uniform(n);
        Item randomItem = items[randomIndex];
        // fill the gap with last item
        Item lastItem = items[n - 1];
        items[randomIndex] = lastItem;

        items[n - 1] = null;                              // to avoid loitering
        n--;
        // shrink size of array if necessary
        if (n > 0 && n == items.length/4) resize(items.length/2);
        return randomItem;
    }

    /**
     * Returns (but does not remove) a random item from the queue.
     * @return random item
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        int randomIndex = StdRandom.uniform(n);
        return items[randomIndex];
    }

    /**
     * Returns an independent iterator over the items in random order.
     * @return an iterator over the items in random order.
     */
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class RandomizedQueueIterator implements Iterator<Item> {
        private int index;
        private int[] random;

        public RandomizedQueueIterator() {
            random = new int[n];
            for (int i = 0; i < random.length; i++) {
                random[i] = i;
            }
            StdRandom.shuffle(random);
        }

        public boolean hasNext() {
            return index < random.length;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int randomIndex = random[index];
            index++;
            return items[randomIndex];
        }
    }

    public static void main(String[] args) {
    }
}
