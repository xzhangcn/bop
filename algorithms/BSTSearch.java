import java.util.ArrayDeque;
import java.util.Deque;

/**
 * created:    2020/03/10
 * <p>
 * The {@code BSTIterator} implements an algorithm for finding the node in the BST, such that the
 * node's value equals the given value. Return the subtree rooted with that node. If such node
 * doesn't exist, you should return NULL.
 *
 * @author Xiaoyu Zhang
 */

public class BSTSearch {

    /**
     * Recursive approach.
     * <p>
     * Time complexity : O(h), where h is the height of the BST.
     * <p>
     * Space complexity : O(h)
     *
     * @param root root of a BST
     * @param val  a given value to search
     * @return the node if found, null otherwise
     */
    public static TreeNode searchBST(TreeNode root, int val) {
        if (root == null)
            return null;

        if (root.value == val)
            return root;
        else if (root.value > val)
            return searchBST(root.left, val);
        else
            return searchBST(root.right, val);
    }

    /**
     * Iterative approach with the help of Deque.
     * <p>
     * Time complexity : O(h), where h is the height of the BST.
     * <p>
     * Space complexity : O(h)
     *
     * @param root root of a BST
     * @param val  a given value to search
     * @return the node if found, null otherwise
     */
    public static TreeNode searchBST2(TreeNode root, int val) {
        if (root == null)
            return null;

        // As Stack is depreciated, use Deque instead
        Deque<TreeNode> deck = new ArrayDeque<>();
        deck.addFirst(root);
        while (!deck.isEmpty()) {
            TreeNode curr = deck.removeFirst();
            if (curr.value == val)
                return curr;
            else if (curr.value > val && curr.left != null)
                deck.addFirst(curr.left);
            else if (curr.right != null)
                deck.addFirst(curr.right);
            else
                return null;
        }

        return null;
    }

    /**
     * Iterative approach without the help of Deque.
     * <p>
     * Time complexity : O(h), where h is the height of the BST.
     * <p>
     * Space complexity : O(1)
     *
     * @param root root of a BST
     * @param val  a given value to search
     * @return the node if found, null otherwise
     */
    public static TreeNode searchBST3(TreeNode root, int val) {
        while (root != null) {
            if (root.value == val)
                return root;
            else if (root.value > val)
                root = root.left;
            else
                root = root.right;
        }

        return null;
    }

    /**
     * Unit test.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        TreeNode tn1 = new TreeNode(1);
        TreeNode tn2 = new TreeNode(2);
        TreeNode tn3 = new TreeNode(3);
        TreeNode tn4 = new TreeNode(4);
        tn2.left = tn1;
        tn2.right = tn3;
        tn4.left = tn2;

        int val = Integer.parseInt(args[0]);
        TreeNode tnFound = BSTSearch.searchBST3(tn4, val);
        if (tnFound != null)
            System.out.printf("Found the value %d!\n", tnFound.value);
        else
            System.out.println("Not found!");
    }
}
