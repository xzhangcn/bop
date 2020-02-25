import java.util.NoSuchElementException;

/**
 * created:    2020/02/25
 * <p>
 * compilation:  javac CircularQueue.java
 * <p>
 * execution:    java CircularQueue
 * <p>
 * The {@code CircularQueue} is a linear data structure in which the operations are performed based
 * on FIFO (First In First Out) principle and the last position is connected back to the first
 * position to make a circle. It is also called "Ring Buffer".
 * <p>
 * One of the benefits of the circular queue is that we can make use of the spaces in front of the
 * queue. In a normal queue, once the queue becomes full, we cannot insert the next element even if
 * there is a space in front of the queue. But using the circular queue, we can use the space to
 * store new values.
 *
 * @author Xiaoyu Zhang
 */

public class CircularQueue {
    private int front;
    private int tail;
    private int capacity;
    private int size;       // number of elements in the queue
    private int[] a;        // underlying array to hold the elements

    /**
     * Initialize data structure. Set the capacity of the queue to be k.
     *
     * @param k initial size of the queue
     */
    public CircularQueue(int k) {
        this.front = -1;
        this.tail = -1;
        this.capacity = k;
        this.size = 0;
        a = new int[this.capacity];
    }

    /**
     * Insert an element into the circular queue. Return true if the operation is successful.
     *
     * @param value the element to be inserted into the circular queue.
     * @return true if this queue is full; false otherwise
     */
    public boolean enQueue(int value) {
        if (isFull()) {
            System.out.printf("Array is full, and the element of %d cannot be enqueued. \n", value);
            return false;
        }

        this.tail = (this.tail + 1) % this.capacity;

        a[this.tail] = value;

        this.size++;

        // first time enqueue
        if (front == -1)
            front++;

        return true;
    }

    /**
     * Delete an element from the circular queue. Return true if the operation is successful.
     *
     * @return true if dequeued an element successfully; false otherwise.
     */
    public boolean deQueue() {
        if (isEmpty()) {
            System.out.println("Array is empty, and dequeue operation fails.");
            return false;
        }

        this.front = ((this.front + 1) % this.capacity);
        this.size--;

        // reset the front and tail pointers
        if (size == 0) {
            this.front = -1;
            this.tail = -1;
        }
        return true;
    }

    /**
     * Get the front item from the queue.
     *
     * @return the front item of the queue.
     */
    public int front() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");

        return a[this.front];
    }

    /**
     * Get the last item from the queue.
     *
     * @return the last item of the queue.
     */
    public int rear() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");

        return a[this.tail];
    }

    /**
     * Checks whether the circular queue is empty or not.
     *
     * @return true if queue is empty; false otherwise.
     */
    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Checks whether the circular queue is full or not.
     *
     * @return true if this queue is full; false otherwise
     */
    public boolean isFull() {
        return ((this.tail + 1) % this.capacity == this.front);
    }

    /**
     * @return number of elements in the queue
     */
    public int size() {
        return this.size;
    }

    /**
     * Unit test
     *
     * @param args
     */
    public static void main(String[] args) {
        CircularQueue cq = new CircularQueue(5);
        cq.enQueue(1);
        cq.enQueue(2);
        cq.enQueue(3);
        System.out.printf("queue is empty? %b \n", cq.isEmpty());
        System.out.printf("size of the queue? %d \n", cq.size());
        System.out.printf("front of the queue? %d \n\n", cq.front());

        cq.deQueue();
        System.out.printf("front of the queue? %d \n", cq.front());
        System.out.printf("size of the queue? %d \n\n", cq.size());
        cq.enQueue(4);
        cq.enQueue(5);
        cq.enQueue(6);
        System.out.printf("size of the queue? %d \n", cq.size());
        System.out.printf("queue is full? %b \n\n", cq.isFull());

        cq.enQueue(7);

        cq.deQueue();
        cq.deQueue();
        cq.deQueue();
        cq.deQueue();
        System.out.printf("front of the queue? %d \n\n", cq.front());
        System.out.printf("tail of the queue? %d \n\n", cq.rear());
        cq.deQueue();
        cq.deQueue();
    }
}

/**
 * Your MyCircularQueue object will be instantiated and called as such: MyCircularQueue obj = new
 * MyCircularQueue(k); boolean param_1 = obj.enQueue(value); boolean param_2 = obj.deQueue(); int
 * param_3 = obj.Front(); int param_4 = obj.Rear(); boolean param_5 = obj.isEmpty(); boolean param_6
 * = obj.isFull();
 */
