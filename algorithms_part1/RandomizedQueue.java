import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * created:    2018/03/24
 * <p>
 * {@code RandomizedQueue} is similar to a stack or queue, using resizing array data structure,
 * except that the item removed is chosen uniformly at random from items in the data structure.
 *
 * @author Xiaoyu Zhang
 */

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;         // array of items
    private int n;            // number of elements on randomized queue

    /**
     * Consstruct an empty randomized queue
     */
    public RandomizedQueue() {
        a = (Item[]) new Object[2];
        n = 0;
    }

    /**
     * Is the randomized queue empty
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * Return the number of items on the randomized queue.
     * @return the number of items on the randomized queue
     */
    public int size() {
        return n;
    }

    // resize the underlying array holding the elements
    private void resize(int capacity) {
        assert capacity >= n;

        // textbook implementation
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = a[i];
        }

        a = temp;

        // alternative implementation
        // a = java.util.Arrays.copyOf(a, capacity);
    }

    /**
     * Add the item to this queue
     * @param item the item to add
     * @throws java.util.IllegalArgumentException if item is null
     */
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException("Null argument");

        if (n == a.length) resize(2 * a.length);    // double size of array if necessary
        a[n++] = item;                              // add item

    }

    /**
     * Remove and return a random item
     * @return the item at random
     * @throws java.util.NoSuchElementException if this queue is empty
     */
    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue overflow");

        int randPos = StdRandom.uniform(n);        // generate a pseudo-random integer between 0 and (n-1)
        Item item = a[randPos];
        // StdOut.println("randPos: " + randPos);

        // move one item foward to the end
        /*
        for (int i = (randPos + 1); i < n; i++) {
            a[i - 1] = a[i];
        }
        */

        // When deleting random element, just simply switch it with the last element in the queue,
        // and decrease the queue size. So deletion will take constant time.
        a[randPos] = a[--n];

        a[n] = null;     // to avoid loitering

        if (n == a.length / 4 && n > 0) {
            resize(a.length / 2);
        }

        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue overflow");

        return a[StdRandom.uniform(n)];
    }

    /**
     * Return an independent iterator over items in random order
     * @return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        // if (n <= 0) throw new IllegalArgumentException("argument must be positive");

        return new RandomQueueIterator();

    }

    // an iterator, doesn't implement remove() since it's optional
    private class RandomQueueIterator implements Iterator<Item> {
        private int i;          // initial random starting position for the iterator
        // private int count;      // counter for how many items already been iterated through
        private int[] indexSequence;

        public RandomQueueIterator() {
            /*
            if (n >= 1) {
                i = StdRandom.uniform(n);
            }

            count = 0;
            */
            i = 0;
            indexSequence = new int[n];
            for (int j = 0; j < n; j++) {
                indexSequence[j] = j;
            }

            StdRandom.shuffle(indexSequence);
        }

        public boolean hasNext() {
            // return count < n;
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();

            // i = StdRandom.uniform(n);
            /*
            Item item = a[i];
            
            if (i == (n - 1)) 
                i = 0;
            else
                i++;
            
            count++;
            
            return item;
            */

            return a[indexSequence[i++]];
        }
    }

    /**
     * Unit tests the {@code Deque} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (!item.equals("-")) {
                queue.enqueue(item);
            }
            else if (!queue.isEmpty()) {
                StdOut.print(queue.dequeue() + " ");
            }
        }

        StdOut.println("(" + queue.size() + " left on deque)");

        Iterator<String> i1 = queue.iterator();
        Iterator<String> i2 = queue.iterator();

        for (int i = 0; i < 3; i++ ) {

            StdOut.println("first iterator");
            for (String s1: queue) {
                StdOut.println(s1);
            }

            StdOut.println("\n");
            StdOut.println("second iterator");
            for (String s2: queue) {
                StdOut.println(s2);
            }

            StdOut.println("loop: " + i);
            StdOut.println("\n");
        }
        /*
        while (i.hasNext()) {
            String s = i.next();
            StdOut.println(s);
        }
        */
    }
}