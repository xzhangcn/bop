/**
 * created:    2020/02/28
 * <p>
 * compilation:  javac PairSwapper.java
 * <p>
 * execution:    java PairSwapper
 * <p>
 * Given a linked list, swap every two adjacent nodes and return its head.
 * <p>
 * It's another simple demonstration for the usage of recursion.
 * <p>
 * swap the rest of the list following the first two nodes, and attach the returned head of the
 * sub-list with the two nodes swapped previously to form a new linked list.
 *
 * @author Xiaoyu Zhang
 */

public class PairSwapper {

    /**
     * Given a linked list, swap every two adjacent nodes and return its head.
     *
     * @param head the front of a linked list
     * @return the head of swapped linked list
     */
    public ListNode swapPairs(ListNode head) {
        // base case
        if (head == null || head.next == null)
            return head;

        // swap the first two nodes in the list
        ListNode second = head.next;
        ListNode third = second.next;
        second.next = head;

        // swap the rest of the list following the first two nodes, and attach the returned head of the
        // sub-list with the two nodes swapped previously to form a new linked list.
        head.next = swapPairs(third);

        return second;
    }

    /**
     * Print out the linked list.
     *
     * @param head the front of the linked list
     */
    public void showList(ListNode head) {
        while (head != null && head.next != null) {
            System.out.printf("%d -> ", head.getValue());
            head = head.next;
        }

        if (head != null)
            System.out.printf("%d\n", head.getValue());
    }

    /**
     * unit test
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        ListNode ln1 = new ListNode(1);
        ListNode ln2 = new ListNode(2);
        ListNode ln3 = new ListNode(3);
        ListNode ln4 = new ListNode(4);
        ln1.next = ln2;
        ln2.next = ln3;
        ln3.next = ln4;

        PairSwapper ps = new PairSwapper();
        ps.showList(ln1);

        ListNode newLN = ps.swapPairs(ln1);
        ps.showList(newLN);
    }
}
