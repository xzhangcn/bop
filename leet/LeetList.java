/**
 * created:    2020/03/25
 * <p>
 * This is the Leetcode's official curated list of top classic interview questions for the topic of Linked List.
 *
 * @author Xiaoyu Zhang
 */

public class LeetList {

    /**
     * Problem 1: Delete Node in a Linked List.
     *
     * Write a function to delete a node (except the tail) in a singly linked list, given only access to that node.
     *
     * <p>
     * Note:
     *    - The linked list will have at least two elements.
     *    - All of the nodes' values will be unique.
     *    - The given node will not be the tail and it will always be a valid node of the linked list.
     *    - Do not return anything from your function.
     *
     * Remark:
     * Be honestly, this leetcode problem is not a good one, because its description is confusing and it's not clear how to test it.
     *
     * @param node a list node
     */
    public void deleteNode(ListNode node) {

        // Since we do not have access to the node before the one we want to delete, we cannot modify the next pointer of that node in any way.
        // Instead, we have to replace the value of the node we want to delete with the value in the node after it, and then delete the node after it.
        node.val = node.next.val;
        node.next = node.next.next;
    }

    /**
     * Problem 2: Remove Nth Node From End of List.
     *
     * Given a linked list, remove the n-th node from the end of list and return its head.
     *
     * <p>
     * Note:
     * Given n will always be valid.
     *
     * @param head first node of a linked list
     * @param n nth from the end of list
     * @return the head of modified list
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
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

        if (first == null) {
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
     * Another way to approach this problem. With the aid of a dummy node, the code is cleaner and more concise.
     * <p>
     * The first pointer advances the list by n+1 steps from the beginning, while the second pointer starts from the beginning of the list.
     * Now, both pointers are exactly separated by n nodes apart. We maintain this constant gap by advancing both pointers together
     * until the first pointer arrives past the last node. The second pointer will be pointing at the nth node counting from the last.
     * We relink the next pointer of the node referenced by the second pointer to point to the node's next next node.
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
     * Problem 3: Reverse Linked List.
     *
     * Reverse a singly linked list.
     *
     * @param head first node of a linked list
     * @return the head of reversed list
     */
    public ListNode reverseList(ListNode head) {
        // base case
        if (head == null || head.next == null)
            return head;

        ListNode p = reverseList(head.next);
        head.next.next = head;
        head.next = null;

        return p;
    }

    /**
     * Iterative version. While you are traversing the list, change the current node's next pointer to point to its previous element.
     * Since a node does not have reference to its previous node, you must store its previous element beforehand.
     * You also need another pointer to store the next node before changing the reference.
     * Do not forget to return the new head reference at the end!
     */
    public ListNode reverseList2(ListNode head) {
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
    public void show(ListNode head) {
        while (head != null && head.next != null) {
            System.out.printf("%d -> ", head.val);
            head = head.next;
        }

        if (head != null)
            System.out.printf("%d\n", head.val);
    }

    /**
     * Unit tests
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        LeetList leetList = new LeetList();

        System.out.println("\n>>> Problem 2: Remove Nth Node From End of List.");
        ListNode ln1_A1 = new ListNode(1);
        ListNode ln1_A2 = new ListNode(2);
        ListNode ln1_A3 = new ListNode(3);
        ListNode ln1_A4 = new ListNode(4);
        ListNode ln1_A5 = new ListNode(5);

        ln1_A1.next = ln1_A2;
        ln1_A2.next = ln1_A3;
        ln1_A3.next = ln1_A4;
        ln1_A4.next = ln1_A5;

        System.out.print("Before removing: ");
        leetList.show(ln1_A1);

        int ln1_n = 2;
        ListNode ln1_head = leetList.removeNthFromEnd(ln1_A1, ln1_n);
        System.out.printf("After removing %dth node from the end: ", ln1_n);
        leetList.show(ln1_head);

        System.out.println("\n>>> Problem 3: Reverse Linked List.");
        ListNode ln2_A1 = new ListNode(1);
        ListNode ln2_A2 = new ListNode(2);
        ListNode ln2_A3 = new ListNode(3);
        ListNode ln2_A4 = new ListNode(4);
        ListNode ln2_A5 = new ListNode(5);

        ln2_A1.next = ln2_A2;
        ln2_A2.next = ln2_A3;
        ln2_A3.next = ln2_A4;
        ln2_A4.next = ln2_A5;

        System.out.print("Before reverse: ");
        leetList.show(ln2_A1);

        ListNode ln2_head = leetList.reverseList(ln2_A1);
        System.out.print("After reverse the linked list: ");
        leetList.show(ln2_head);
    }
}
