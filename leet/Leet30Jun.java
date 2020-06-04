import java.util.*;

/**
 * created:    2019/06/01
 * <p>
 * {@code Leet30Jun} is the 30-day LeetCoding challenge.
 * <p>
 * A new problem will appear each day, and you will have one day to solve it.
 * <p>
 * The problems below are for the June 2020.
 *
 * @author Xiaoyu Zhang
 */


public class Leet30Jun {
    /**
     * Problem on the day of 06/01/2020: Invert Binary Tree.
     * <p>
     * Invert a binary tree.
     *
     * @param root root of a binary tree
     * @return root of the inverted binary tree
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null)
            return null;

        TreeNode right = invertTree(root.right);
        TreeNode left = invertTree(root.left);
        root.left = right;
        root.right = left;
        return root;
    }

    // Iteative approach to the above problem
    public TreeNode invertTree2(TreeNode root) {
        if (root == null)
            return null;

        java.util.Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;

            if (current.left != null)
                queue.add(current.left);

            if (current.right != null)
                queue.add(current.right);
        }

        return root;
    }

    /**
     * Problem on the day of 06/02/2020: Delete Node in a Linked List.
     * <p>
     * Write a function to delete a node (except the tail) in a singly linked list, given only access to that node.
     *
     * @param node a node in a linked list
     */
    public void deleteNode(ListNode node) {
        node.val = node.next.val;
        node.next = node.next.next;
    }

    /**
     * Problem on the day of 06/03/2020: Two City Scheduling.
     * <p>
     * There are 2N people a company is planning to interview. The cost of flying the i-th person to city A is costs[i][0],
     * and the cost of flying the i-th person to city B is costs[i][1].
     * <p>
     * Return the minimum cost to fly every person to a city such that exactly N people arrive in each city.
     *
     * @param costs an array of costs
     * @return the minimum cost
     */
    public int twoCitySchedCost(int[][] costs) {
        Arrays.sort(costs, (a, b) -> {
            return (a[0] - a[1]) - (b[0] - b[1]);
        });

        int res = 0;
        for (int i = 0; i < costs.length; i++) {
            if (i < costs.length / 2) {
                res += costs[i][0];
            } else
                res += costs[i][1];
        }

        return res;
    }

    /**
     * Problem on the day of 06/04/2020: Reverse String.
     * <p>
     * Write a function that reverses a string. The input string is given as an array of characters char[].
     *
     * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
     *
     * You may assume all the characters consist of printable ascii characters.
     *
     * @param s a string
     */
    public void reverseString(char[] s) {
        int start = 0, end = s.length - 1;

        // The entire logic for reversing a string is based on using the opposite directional two-pointer approach
        while (start < end) {
            char temp = s[start];
            s[start] = s[end];
            s[end] = temp;

            start++;
            end--;
        }
    }

    /**
     * Unit tests
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("\n>>> Problem on the day of 06/01/2020: Invert Binary Tree.");

        System.out.println("\n>>> Problem on the day of 06/02/2020: Delete Node in a Linked List.");

        System.out.println("\n>>> Problem on the day of 06/03/2020: Two City Scheduling.");

        System.out.println("\n>>> Problem on the day of 06/04/2020: Reverse String.");
    }
}
