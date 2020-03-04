import java.util.HashSet;
import java.util.Set;

/**
 * created:    2020/03/04
 * <p>
 * compilation:  javac ListCycle.java
 * <p>
 * execution:    java ListCycle
 * <p>
 * The {@code ListCycle} class implements the algorithms to determine if a linked list has cycle.
 *
 * @author Xiaoyu Zhang
 */

public class ListCycle {

    /**
     * Two pointers approach.
     * <p>
     * The space complexity can be reduced to O(1) by considering two pointers at different speed -
     * a slow pointer and a fast pointer. The slow pointer moves one step at a time while the fast
     * pointer moves two steps at a time.
     * <p>
     * If there is no cycle in the list, the fast pointer will eventually reach the end and we can
     * return false in this case.
     * <p>
     * Now consider a cyclic list and imagine the slow and fast pointers are two runners racing
     * around a circle track. The fast runner will eventually meet the slow runner.
     * <p>
     * Time complexity: O(n).
     * <p>
     * Space complexity: O(1). We only use two nodes (slow and fast).
     *
     * @param head the front of linked list
     * @return true if there is a cycle, false otherwise.
     */
    public static boolean hasCycle(ListNode head) {
        if (head == null || head.next == null)
            return false;

        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null)
                return false;

            slow = slow.next;
            fast = fast.next.next;
        }

        return true;
    }

    /**
     * We go through each node one by one and record each node's reference (or memory address) in a
     * hash table. If the current node is null, we have reached the end of the list and it must not
     * be cyclic. If current node’s reference is in the hash table, then return true.
     * <p>
     * Time complexity : O(n). We visit each of the nn elements in the list at most once. Adding a
     * node to the hash table costs only O(1)O(1) time.
     * <p>
     * Space complexity: O(n). The space depends on the number of elements added to the hash table,
     * which contains at most n elements.
     *
     * @param head the front of linked list
     * @return true if there is a cycle, false otherwise.
     */
    public static boolean hasCycle2(ListNode head) {
        Set<ListNode> set = new HashSet<>();
        while (head != null) {
            if (set.contains(head))
                return true;
            else
                set.add(head);

            head = head.next;
        }

        return false;
    }

    /**
     * Unit tests.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        ListNode ln1 = new ListNode(1);
        ListNode ln2 = new ListNode(2);
        ListNode ln3 = new ListNode(3);
        ListNode ln4 = new ListNode(4);
        ListNode ln5 = new ListNode(5);
        ListNode ln6 = new ListNode(6);
        ln1.next = ln2;
        ln2.next = ln3;
        ln3.next = ln4;
        ln4.next = ln5;
        ln5.next = ln6;
        ln6.next = ln2;

        System.out.printf("The linked list has a cycle? %b\n", ListCycle.hasCycle(ln1));
        System.out.printf("The linked list has a cycle? %b\n", ListCycle.hasCycle2(ln1));
    }
}
