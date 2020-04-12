import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * created:    2018/03/22
 * <p>
 * {@code Deque} is a generalization of a stack and a queue that supports adding and removing items
 * from either the front or the back of the data structure.
 *
 * @author Xiaoyu Zhang
 */

public class Deque<Item> implements Iterable<Item> {

    private int n;             // size of the deque
    private Node first;        // top of the deque
    private Node last;         // end of the deque

    // helper double linked list class
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    /**
     * Construct an empty deque
     */
    public Deque() {
        n = 0;
        first = null;
        last = null;

        assert check();
    }

    /**
     * Is the deque empty?
     * @return true if this deque is empty; false otherwise
     */
    public boolean isEmpty() {
        return (first == null && last == null);
    }

    /**
     * Return the number of items on the deque
     * @return the number of items on the deque
     */
    public int size() {
        return n;
    }

    /**
     * Add the item to the front
     * @param item the item to add
     * @throws java.util.IllegalArgumentException if item is null
     */
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException("Null argument");

        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;

        if (oldfirst != null) {
            oldfirst.prev = first;
        }
        else {
            last = first;
        }

        n++;

        assert check();
    }

    /**
     * Add the item to the end
     * @param item the item to add
     * @throws java.util.IllegalArgumentException if item is null
     */
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException("Null argument");

        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;

        if (oldlast != null) {
            last.prev = oldlast;
            oldlast.next = last;
        }
        else {
            first = last;
        }

        n++;

        assert check();
    }

    /**
     * Remove and return item from the front
     * @return the item from the front
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque overflow");

        Item item = first.item;              // save item to return
        first = first.next;                  // delete first node

        if (first != null) {
            first.prev = null;
        }
        else {
            last = null;
        }

        n--;

        assert check();

        return item;
    }

    /**
     * Remove and return the item from the end
     * @return the item from the end
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque overflow");

        Item item = last.item;              // save item to return

        if (last.prev == null) {
            first = null;
            last = null;
        }
        else {
            last = last.prev;
            last.next = null;                   // delete last node
        }

        n--;

        assert check();

        return item;

    }

    /**
     * Return an iterator over items in order from front to end
     * @return an iterator to this deque that iterates through the items from front to end
     */
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            Item item = current.item;
            current = current.next;

            return item;
        }
    }

    // check interval invariants
    private boolean check() {
        // check a few properties of instance variable 'first'
        if (n < 0) {
            return false;
        }

        if (n == 0) {
            if (first != null) return false;
        }
        else if (n == 1) {
            if (first == null) return false;
            if (first.next != null) return false;
        }
        else {
            if (first == null) return false;
            if (first.next == null) return false;
        }

        return true;
    }

    /**
     * Unit tests the {@code Deque} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                deque.addLast(item);
            }
            else if (!deque.isEmpty()) {
                StdOut.print(deque.removeLast() + " ");
            }
        }

        StdOut.println("(" + deque.size() + " left on deque)");

        for (String s : deque)
            StdOut.println(s);
    }
}