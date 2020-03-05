import java.util.HashSet;
import java.util.Set;

/**
 * created:    2020/03/05
 * <p>
 * compilation:  javac ListIntersection.java
 * <p>
 * execution:    java ListIntersection
 * <p>
 * The {@code ListIntersection} class implements the algorithms to find the node at which the
 * intersection of two singly linked lists begins.
 *
 * @author Xiaoyu Zhang
 */

public class ListIntersection {

    /**
     * Approach 1: use HashSet.
     * <p>
     * Traverse list A and store the address / reference to each node in a hash set. Then check
     * every node bi in list B: if bi appears in the hash set, then bi is the intersection node.
     * <p>
     * Time complexity : O(m+n).
     * <p>
     * Space complexity : O(m) or O(n).
     *
     * @param headA front of a linked list
     * @param headB front of another linked list
     * @return interesection node if exists, otherwise null
     */
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null)
            return null;

        Set<ListNode> set = new HashSet<>();
        do {
            set.add(headA);
            headA = headA.next;
        } while (headA != null);

        do {
            if (set.contains(headB))
                return headB;
            headB = headB.next;
        } while (headB != null);

        return null;
    }

    /**
     * Approach 2: use two pointers.
     * <p>
     * Maintain two pointers pApA and pBpB initialized at the head of A and B, respectively. Then
     * let them both traverse through the lists, one node at a time.
     * <p>
     * When pApA reaches the end of a list, then redirect it to the head of B (yes, B, that's
     * right.); similarly when pBpB reaches the end of a list, redirect it the head of A.
     * <p>
     * If at any point pA meets pB, then pA/pB is the intersection node.
     * <p>
     * If two lists have intersection, then their last nodes must be the same one. So when pA/pB
     * reaches the end of a list, record the last element of A/B respectively. If the two last
     * elements are not the same one, then the two lists have no intersections.
     * <p>
     * Time complexity : O(m+n).
     * <p>
     * Space complexity : O(1).
     *
     * @param headA front of a linked list
     * @param headB front of another linked list
     * @return interesection node if exists, otherwise null
     */
    public static ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        if (headA == null || headB == null)
            return null;

        ListNode pA = headA;
        ListNode pB = headB;
        ListNode tailA = null;
        ListNode tailB = null;

        while (!pA.equals(pB)) {

            if (pA.next == null) {
                tailA = pA;
                pA = headB;
            }
            else
                pA = pA.next;

            if (pB.next == null) {
                tailB = pB;
                pB = headA;
            }
            else
                pB = pB.next;

            if (tailA != null && tailB != null && !tailA.equals(tailB))
                return null;

        }

        return pA;
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
        ListNode lnA1 = new ListNode(1);
        ListNode lnA2 = new ListNode(3);
        ListNode lnA3 = new ListNode(5);
        ListNode lnA4 = new ListNode(7);
        ListNode lnAB5 = new ListNode(9);
        ListNode lnAB6 = new ListNode(11);

        lnA1.next = lnA2;
        lnA2.next = lnA3;
        lnA3.next = lnA4;
        lnA4.next = lnAB5;
        lnAB5.next = lnAB6;

        ListNode lnB1 = new ListNode(2);
        // ListNode lnB2 = new ListNode(4);

        // lnB1.next = lnB2;
        // lnB2.next = lnAB5;

        System.out.print("List A: ");
        ListIntersection.showList(lnA1);
        System.out.print("List B: ");
        ListIntersection.showList(lnB1);

        ListNode lnFound = ListIntersection.getIntersectionNode2(lnA1, lnB1);

        if (lnFound == null)
            System.out.print("There is no intersection between these two linked list.\n");
        else
            System.out.printf("The intersection node between these two linked list is %d\n",
                              lnFound.getValue());
    }
}
