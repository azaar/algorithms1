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

    // construct an empty deque
    public Deque() {
        size = 0;
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
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

    // add the item to the end
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

    // remove and return the item from the front
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

    // remove and return the item from the end
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

    // return an iterator over items in order from front to end
    @Override
    public Iterator<Item> iterator() {
        return new MyListIterator();
    }

    private class MyListIterator implements Iterator<Item> {
        private Node current = sentinel.next;

        @Override
        public boolean hasNext() {
            return current != null;
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
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(0);
        deque.iterator();

    }
}
