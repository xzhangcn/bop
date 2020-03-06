/**
 * created:    2020/03/06
 * <p>
 * compilation:  javac ListRemoveNodeFromEnd.java
 * <p>
 * execution:    java ListRemoveNodeFromEnd
 * <p>
 * The {@code ListRemoveNodeFromEnd} class implements the algorithms to remov the n-th node from the
 * end of list and return its head.
 *
 * @author Xiaoyu Zhang
 */

public class ListRemoveNodeFromEnd {

    /**
     * Two pointers approach.
     * <p>
     * Time complexity : O(L). The algorithm makes one traversal of the list of LL nodes.
     * <p>
     * Space complexity : O(1). We only used constant extra space.
     *
     * @param head the front of the linked list
     * @param n    nth from the end of the list
     * @return the head of modified list
     */
    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode first = head;
        ListNode second = head;
        ListNode prev = head;

        n--;
        while (first != null && n >= 0) {
            first = first.next;
            n--;
        }

        if (n > 0)
            return head;

        if (n <= 0 && first == null) {
            return head.next;
        }

        while (first != null) {
            prev = second;
            second = second.next;
            first = first.next;
        }

        prev.next = second.next;

        return head;
    }

    /**
     * Another way to approach this problem. With the aid of a dummy node, the code is clearner and
     * succinct.
     * <p>
     * The first pointer advances the list by n+1 steps from the beginning, while the second pointer
     * starts from the beginning of the list. Now, both pointers are exactly separated by n nodes
     * apart. We maintain this constant gap by advancing both pointers together until the first
     * pointer arrives past the last node. The second pointer will be pointing at the nth node
     * counting from the last. We relink the next pointer of the node referenced by the second
     * pointer to point to the node's next next node.
     * <p>
     * Time complexity : O(L). The algorithm makes one traversal of the list of LL nodes.
     * <p>
     * Space complexity : O(1). We only used constant extra space.
     *
     * @param head the front of the linked list
     * @param n    nth from the end of the list
     * @return the head of modified list
     */
    public static ListNode removeNthFromEnd2(ListNode head, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode first = dummy;
        ListNode second = dummy;

        // Advances first pointer so that the gap between first and second is n nodes apart
        for (int i = 1; i <= n + 1; i++) {
            first = first.next;
        }

        // Move first to the end, maintaining the gap
        while (first != null) {
            first = first.next;
            second = second.next;
        }

        second.next = second.next.next;
        return dummy.next;
    }

    /**
     * @param head the front of the linked list
     */
    public static void showList(ListNode head) {
        while (head != null && head.next != null) {
            System.out.printf("%d -> ", head.getValue());
            head = head.next;
        }

        if (head != null)
            System.out.printf("%d\n", head.getValue());
    }

    /**
     * Unit tests
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);

        ListNode lnA1 = new ListNode(1);
        ListNode lnA2 = new ListNode(3);
        ListNode lnA3 = new ListNode(5);
        ListNode lnA4 = new ListNode(7);
        ListNode lnA5 = new ListNode(9);
        ListNode lnA6 = new ListNode(11);

        lnA1.next = lnA2;
        lnA2.next = lnA3;
        lnA3.next = lnA4;
        lnA4.next = lnA5;
        lnA5.next = lnA6;

        ListRemoveNodeFromEnd.showList(lnA1);

        System.out.printf("after removing %dth node from the list.\n", n);
        ListRemoveNodeFromEnd.showList(ListRemoveNodeFromEnd.removeNthFromEnd(lnA1, n));
    }
}
