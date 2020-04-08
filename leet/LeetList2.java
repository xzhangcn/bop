
/**
 * created:    2020/04/06
 * <p>
 * This is second collection of leetcode problems for the topic of Linked List from Top Interview Questions.
 *
 * @author Xiaoyu Zhang
 */
public class LeetList2 {
    /**
     * Problem 1: Add Two Numbers.
     * <p>
     * You are given two non-empty linked lists representing two non-negative integers. The digits are stored
     * in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
     *
     * You may assume the two numbers do not contain any leading zero, except the number 0 itself.
     *
     * @param l1 one linked list
     * @param l2 another linked list
     * @return linked list being added
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null && l2 == null)
            return null;

        ListNode dummmy = new ListNode(-1);
        ListNode p = dummmy;
        int carry = 0;
        while (l1 != null && l2 != null) {
            int value = l1.val + l2.val + carry;

            p.next = new ListNode(value % 10);
            p = p.next;

            carry = value / 10;

            l1 = l1.next;
            l2 = l2.next;
        }

        while (l1 != null) {
            int value = l1.val + carry;
            p.next = new ListNode(value % 10);
            p = p.next;

            carry = value / 10;
            l1 = l1.next;
        }

        while (l2 != null) {
            int value = l2.val + carry;
            p.next = new ListNode(value % 10);
            p = p.next;
            carry = value / 10;
            l2 = l2.next;
        }

        if (carry == 1)
            p.next = new ListNode(carry);

        return dummmy.next;
    }

    // A more compact way to rewrite the above solution
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;
        int carry = 0;

        while (p != null || q != null) {
            int x = (p != null) ? p.val : 0;
            int y = (q != null) ? q.val : 0;
            int sum = carry + x + y;
            carry = sum / 10;

            curr.next = new ListNode(sum % 10);
            curr = curr.next;

            if (p != null) p = p.next;
            if (q != null) q = q.next;
        }

        if (carry > 0) {
            curr.next = new ListNode(carry);
        }

        return dummyHead.next;
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
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        LeetList2 leetList2 = new LeetList2();

        System.out.println("\n>>> Problem 1: Add Two Numbers.");
        ListNode ln1_A1 = new ListNode(2);
        ListNode ln1_A2 = new ListNode(4);
        ListNode ln1_A3 = new ListNode(3);
        ListNode ln1_B1 = new ListNode(5);
        ListNode ln1_B2 = new ListNode(6);
        ListNode ln1_B3 = new ListNode(4);

        ln1_A1.next = ln1_A2;
        ln1_A2.next = ln1_A3;
        ln1_B1.next = ln1_B2;
        ln1_B2.next = ln1_B3;

        ListNode ln1_head = leetList2.addTwoNumbers(ln1_A1, ln1_B1);
        leetList2.show(ln1_head);
    }
}
