/**
 * created:    2020/02/28
 * <p>
 * compilation:  javac ListNode.java
 * <p>
 * execution:    java ListNode
 * <p>
 * The {@code ListNode} class represents a node for a singly linked list.
 *
 * @author Xiaoyu Zhang
 */

public class ListNode {
    private int value;
    public ListNode next;   // make it public for easy use

    public ListNode(int x) {
        value = x;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}

