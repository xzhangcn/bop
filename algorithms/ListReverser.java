/**
 * created:    2020/02/27
 * <p>
 * compilation:  javac ListReverser.java
 * <p>
 * execution:    java ListReverser
 * <p>
 * Given a singly linked list, reverse it.
 * <p>
 * It's just a simple demonstration for the usage of recursion.
 * <p>
 *
 * @author Xiaoyu Zhang
 */

public class ListReverser {

    /**
     * The recursive version is slightly trickier and the key is to work backwards. Assume that the
     * rest of the list had already been reversed, now how do I reverse the front part?
     *
     * @param head the front of the linked list
     * @return new head of the reversed linked list
     */
    public ListNode reverse(ListNode head) {
        // base case
        if (head == null || head.next == null)
            return head;

        ListNode p = reverse(head.next);
        head.next.next = head;
        head.next = null;

        return p;
    }

    /**
     * Iterative version. While you are traversing the list, change the current node's next pointer
     * to point to its previous element. Since a node does not have reference to its previous node,
     * you must store its previous element beforehand. You also need another pointer to store the
     * next node before changing the reference. Do not forget to return the new head reference at
     * the end!
     *
     * @param head the front of the linked list
     * @return new head of the reversed linked list
     */
    public ListNode reverse2(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }

        return prev;
    }

    /**
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


    public static void main(String[] args) {
        ListNode ln1 = new ListNode(1);
        ListNode ln2 = new ListNode(2);
        ListNode ln3 = new ListNode(3);
        ListNode ln4 = new ListNode(4);
        ln1.next = ln2;
        ln2.next = ln3;
        ln3.next = ln4;

        ListReverser ps = new ListReverser();
        ps.showList(ln1);

        ListNode newLN = ps.reverse(ln1);
        ps.showList(newLN);

        ListNode newLN2 = ps.reverse2(newLN);
        ps.showList(newLN2);

    }
}
