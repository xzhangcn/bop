import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import java.util.HashSet;

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
     * <p>
     * Write a function to delete a node (except the tail) in a singly linked list, given only access to that node.
     *
     * <p>
     * Note:
     * - The linked list will have at least two elements.
     * - All of the nodes' values will be unique.
     * - The given node will not be the tail and it will always be a valid node of the linked list.
     * - Do not return anything from your function.
     * <p>
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
     * <p>
     * Given a linked list, remove the n-th node from the end of list and return its head.
     *
     * <p>
     * Note:
     * Given n will always be valid.
     *
     * @param head first node of a linked list
     * @param n    nth from the end of list
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
     * <p>
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
     * Problem 4: Merge Two Sorted Lists.
     * <p>
     * Merge two sorted linked lists and return it as a new list. The new list should be made by splicing together the nodes of the first two lists.
     *
     * @param l1 first node of first sorted linked list
     * @param l2 first node of second sorted linked list
     * @return the head of merged list
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null)
            return l2;
        if (l2 == null)
            return l1;

        ListNode head = null;       // head of the list to return

        // find first element (can use dummy node to put this part inside of the loop)
        if (l1.val <= l2.val) {
            head = l1;
            l1 = l1.next;
        } else {
            head = l2;
            l2 = l2.next;
        }

        ListNode curr = head;       // pointer to form new list

        do {
            if (l1 != null && l2 != null) {

                if (l1.val <= l2.val) {
                    curr.next = l1;
                    l1 = l1.next;
                } else {
                    curr.next = l2;
                    l2 = l2.next;
                }

                curr = curr.next;

            }
        } while (l1 != null && l2 != null);

        // add the rest of the tail
        if (l1 == null)
            curr.next = l2;
        if (l2 == null)
            curr.next = l1;

        return head;
    }

    // Rewrite the code above to make it more concise
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2) {

        ListNode dummy = new ListNode(0);   // dummy head
        ListNode curr = dummy;                  // pointer to traverse the list

        while (l1 != null && l2 != null) {

            if (l1.val <= l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }

            curr = curr.next;
        }

        // add the rest of the tail
        if (l1 != null)
            curr.next = l1;
        if (l2 != null)
            curr.next = l2;

        return dummy.next;
    }

    // Recursive approach to the problem above.
    // This approach may cause overflow, because the length of a linked list could be much longer than we expected.
    // In that case the recursive approach is likely to introduce a stack overflow. (imagine a file system)
    public ListNode mergeTwoLists3(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        ListNode head;
        if (l1.val <= l2.val) {
            head = l1;
            head.next = mergeTwoLists3(l1.next, l2);
        } else {
            head = l2;
            head.next = mergeTwoLists3(l1, l2.next);
        }

        return head;
    }

    // Rewrite the recursive code
    public ListNode mergeTwoLists4(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;

        if (l1.val < l2.val) {
            l1.next = mergeTwoLists4(l1.next, l2);
            return l1;
        } else {
            l2.next = mergeTwoLists4(l1, l2.next);
            return l2;
        }
    }

    /**
     * Problem 5: Palindrome Linked List.
     * <p>
     * Given a singly linked list, determine if it is a palindrome.
     *
     * @param head first node of the linked list
     * @return true if it is a palindrome, false otherwise
     */
    public boolean isPalindrome(ListNode head) {
        Deque<Integer> stack = new ArrayDeque<>();

        ListNode curr = head;
        while (curr != null) {
            stack.push(curr.val);
            curr = curr.next;
        }

        while (!stack.isEmpty()) {
            if (stack.pop() != head.val)
                return false;

            head = head.next;
        }

        return true;
    }

    // Reverse the first half, then compare
    public boolean isPalindrome2(ListNode head) {
        if (head == null)
            return true;

        // Reverse the first half while finding the middle.
        ListNode slow = head, fast = head, rev = null;
        while (fast != null && fast.next != null) {
            ListNode temp = rev;
            rev = slow;
            slow = slow.next;
            fast = fast.next.next;
            rev.next = temp;
        }

        if (fast != null)
            slow = slow.next;

        // Compare the reversed first half with the second half.
        while (rev != null) {
            if (slow.val != rev.val) {
                return false;
            }
            slow = slow.next;
            rev = rev.next;
        }
        return true;
    }

    // Same as the above, but while comparing the two halves, restore the list to its original state by reversing the first half back.
    public boolean isPalindrome3(ListNode head) {
        if (head == null)
            return true;

        ListNode slow = head, fast = head, rev = null;
        while (fast != null && fast.next != null) {
            ListNode temp = rev;
            rev = slow;
            slow = slow.next;
            fast = fast.next.next;
            rev.next = temp;
        }

        ListNode tail = fast != null ? slow.next : slow;
        while (rev != null) {
            if (tail.val != rev.val)
                return false;

            ListNode temp = slow;
            slow = rev;
            tail = tail.next;
            rev = rev.next;
            slow.next = temp;
        }

        return true;
    }

    /**
     * Problem 5: Linked List Cycle.
     * <p>
     * Given a linked list, determine if it has a cycle in it.
     *
     * To represent a cycle in the given linked list, we use an integer pos which represents the position (0-indexed)
     * in the linked list where tail connects to. If pos is -1, then there is no cycle in the linked list.
     *
     * @param head first node of the linked list
     * @return true if it is a palindrome, false otherwise
     */
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null)
            return false;

        // Two pointers approach.
        // Considering two pointers at different speed - a slow pointer and a fast pointer.
        // The slow pointer moves one step at a time while the fast pointer moves two steps at a time.
        // If there is no cycle in the list, the fast pointer will eventually reach the end and we can return false in this case.
        // Now consider a cyclic list and imagine the slow and fast pointers are two runners racing around a circle track.
        // The fast runner will eventually meet the slow runner.
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

    // Another approach to the problem above.
    public boolean hasCycle2(ListNode head) {
        // We go through each node one by one and record each node's reference (or memory address) in a hash table.
        // If the current node is null, we have reached the end of the list and it must not be cyclic.
        // If current node’s reference is in the hash table, then return true.

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

        System.out.println("\n>>> Problem 4: Merge Two Sorted Lists.");
        ListNode ln4_A1 = new ListNode(1);
        ListNode ln4_A2 = new ListNode(2);
        ListNode ln4_A3 = new ListNode(4);
        ln4_A1.next = ln4_A2;
        ln4_A2.next = ln4_A3;

        ListNode ln4_A4 = new ListNode(1);
        ListNode ln4_A5 = new ListNode(3);
        ListNode ln4_A6 = new ListNode(4);
        ln4_A4.next = ln4_A5;
        ln4_A5.next = ln4_A6;

        System.out.print("Before merge: ");
        leetList.show(ln4_A1);
        leetList.show(ln4_A4);

        System.out.print("After merge: ");
        ListNode ln4_head = leetList.mergeTwoLists4(ln4_A1, ln4_A4);
        leetList.show(ln4_head);

        System.out.println("\n>>> Problem 5: Palindrome Linked List.");
        ListNode ln5_A1 = new ListNode(1);
        ListNode ln5_A2 = new ListNode(2);
        ListNode ln5_A3 = new ListNode(2);
        ListNode ln5_A4 = new ListNode(1);
        ln5_A1.next = ln5_A2;
        ln5_A2.next = ln5_A3;
        ln5_A3.next = ln5_A4;

        leetList.show(ln5_A1);
        System.out.printf("Is it a palindrome linked list ? %b \n", leetList.isPalindrome3(ln5_A1));

        System.out.println("\n>>> Problem 6: Linked List Cycle.");
        ListNode ln6_A1 = new ListNode(3);
        ListNode ln6_A2 = new ListNode(2);
        ListNode ln6_A3 = new ListNode(0);
        ListNode ln6_A4 = new ListNode(-4);
        ln6_A1.next = ln6_A2;
        ln6_A2.next = ln6_A3;
        ln6_A3.next = ln6_A4;
        ln6_A4.next = ln6_A2;
        System.out.printf("Does this linked list have cycle ? %b\n ", leetList.hasCycle(ln6_A1));
    }
}
