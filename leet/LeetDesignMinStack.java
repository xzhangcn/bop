import java.util.ArrayDeque;
import java.util.Deque;

/**
 * created:    2020/03/30
 * <p>
 * This is the Leetcode's official curated list of top classic interview questions for the topic of Design.
 *
 * Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.
 *
 * push(x) -- Push element x onto stack.
 * pop() -- Removes the element on top of the stack.
 * top() -- Get the top element.
 * getMin() -- Retrieve the minimum element in the stack.
 *
 * @author Xiaoyu Zhang
 */
public class LeetDesignMinStack {

    private Node head;

    public void push(int x) {
        if(head == null)
            head = new Node(x, x);
        else
            head = new Node(x, Math.min(x, head.min), head);
    }

    public void pop() {
        head = head.next;
    }

    public int top() {
        return head.val;
    }

    public int getMin() {
        return head.min;
    }

    private class Node {
        int val;
        int min;            // each node in the stack having a minimum value.
        Node next;

        private Node(int val, int min) {
            this(val, min, null);
        }

        private Node(int val, int min, Node next) {
            this.val = val;
            this.min = min;
            this.next = next;
        }
    }

    /*
    private Deque<Integer> stack;
    private int minValue;

    public LeetDesignMinStack() {
        stack = new ArrayDeque<>();
        minValue = Integer.MAX_VALUE;
    }

    public void push(int x) {
        stack.push(x);
        if (x < minValue)
            minValue = x;

    }

    public void pop() {
        if (stack.poll() == minValue) {
            minValue = Integer.MAX_VALUE;
            for (int num : stack) {
                if (num < minValue)
                    minValue = num;
            }
        }
    }

    public int top() {
        return stack.peek();

    }

    public int getMin() {
        return minValue;
    }
     */


    /**
     * Unit tests
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        LeetDesignMinStack minStack = new LeetDesignMinStack();

        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);
        System.out.printf("The min is %d\n", minStack.getMin());

        minStack.pop();
        // minStack.top();
        System.out.printf("The top is %d\n", minStack.top());
        System.out.printf("The min is %d\n", minStack.getMin());
    }
}
