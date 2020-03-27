import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;

/**
 * created:    2020/03/27
 * <p>
 * This is the Leetcode's official curated list of top classic interview questions for the topic of Tree.
 *
 * @author Xiaoyu Zhang
 */

public class LeetTree {

    /**
     * Problem 1: Maximum Depth of Binary Tree.
     * <p>
     * Given a binary tree, find its maximum depth.
     *
     * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
     *
     * Note: A leaf is a node with no children.
     *
     * @param root root node of a binary tree
     * @return the max depth
     */
    public int maxDepth(TreeNode root) {
        if (root == null)   // base case
            return 0;

        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);

        return Math.max(leftDepth, rightDepth) + 1;
    }

    /**
     * Problem 2: Validate Binary Search Tree.
     * <p>
     * Given a binary tree, determine if it is a valid binary search tree (BST).
     *
     * @return true if it is a valid BST, false otherwise.
     */
    public boolean isValidBST(TreeNode root) {

        return isValidBSTHelper(root, null, null);
    }

    /**
     * Helper function for recursive approach.
     * <p>
     * Compares the node value with its upper and lower limits if they are available. Then repeats
     * the same step recursively for left and right subtrees.
     *
     * @param node  root of a binary search tree
     * @param lower lower limit
     * @param upper upper limit
     * @return true if it is a valid BST, false otherwise.
     */
    public static boolean isValidBSTHelper(TreeNode node, Integer lower, Integer upper) {
        if (node == null)
            return true;

        Integer value = (Integer) node.val;
        if (lower != null && value <= lower)
            return false;
        if (upper != null && value >= upper)
            return false;

        if (!isValidBSTHelper(node.left, lower, value))
            return false;
        if (!isValidBSTHelper(node.right, value, upper))
            return false;

        return true;
    }

    /**
     * Iterative approach leveraging inorder traversal.
     *
     * @param root root of a binary search tree
     * @return true if it is a valid BST, false otherwise.
     */
    public static boolean isValidBST2(TreeNode root) {

        Deque<TreeNode> deck = new ArrayDeque<>();
        double min = -Double.MAX_VALUE;

        while (!deck.isEmpty() || root != null) {
            while (root != null) {
                deck.addFirst(root);
                root = root.left;
            }

            root = deck.removeFirst();

            // If next element in inorder traversal is smaller than the previous one, that's not BST.
            if (root.val <= min)
                return false;

            min = root.val;
            root = root.right;
        }

        return true;
    }

    /**
     * @param root root of a BST
     * @return the list of inorder traversal
     */
    public List<Integer> inOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        inOrderTraversalHelper(root, result);
        return result;
    }

    /**
     * @param node root of a BST
     * @param list the list to store of inorder traversal result
     */
    private void inOrderTraversalHelper(TreeNode node, List<Integer> list) {
        if (node == null)   // base case
            return;

        inOrderTraversalHelper(node.left, list);
        list.add(node.val);
        inOrderTraversalHelper(node.right, list);
    }

    /**
     * @param root root of a BST
     */
    public void show(TreeNode root) {
        System.out.print("\n> BST inOrderTraversal:\t");
        for (Integer val : inOrderTraversal(root))
            System.out.printf("%d ", val);
        System.out.println();
    }

    /**
     * Unit tests
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        LeetTree leetTree = new LeetTree();

        System.out.println("\n>>> Problem 1: Maximum Depth of Binary Tree.");
        TreeNode tn1_1 = new TreeNode(3);
        TreeNode tn1_2 = new TreeNode(9);
        TreeNode tn1_3 = new TreeNode(20);
        TreeNode tn1_4 = new TreeNode(15);
        TreeNode tn1_5 = new TreeNode(7);

        tn1_1.left = tn1_2;
        tn1_1.right = tn1_3;
        tn1_3.left = tn1_4;
        tn1_3.right = tn1_5;

        System.out.printf("The max depth of the tree is %d\n", leetTree.maxDepth(tn1_1));

        System.out.println("\n>>> Problem 2: Validate Binary Search Tree.");
        TreeNode tn2_1 = new TreeNode(5);
        TreeNode tn2_2 = new TreeNode(1);
        TreeNode tn2_3 = new TreeNode(4);
        TreeNode tn2_4 = new TreeNode(3);
        TreeNode tn2_5 = new TreeNode(6);

        tn2_1.left = tn2_2;
        tn2_1.right = tn2_3;
        tn2_3.left = tn2_4;
        tn2_3.right = tn2_5;

        leetTree.show(tn2_1);
        System.out.printf("Is this a valid binary search tree? %b\n", leetTree.isValidBST(tn2_1));

    }
}
