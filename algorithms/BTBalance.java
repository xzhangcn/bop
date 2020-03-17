/**
 * created:    2020/03/17
 * <p>
 * Given a binary tree, determine if it is height-balanced.
 * <p>
 * A height-balanced binary tree is defined as: a binary tree in which the left and right subtrees of every node differ in height by no more than 1.
 *
 * @author Xiaoyu Zhang
 */


public class BTBalance {

    /**
     * @param root root of the binary tree
     * @return true if it's balanced, false otherwise
     */
    public static boolean isBalanced(TreeNode root) {
        return helper(root) >= -1;
    }

    /**
     * sBalancedHelper returns the actual depth of tree if the tree is balanced, or -2 if unbalanced.
     * Why -2? The depth of tree is defined as number of edges from root to its deepest leaf node.
     * So if a node is null, its depth of -1, instead of 0. The root with no children has a depth of 0.
     * it is calculated as max depth of its children, which is -1, plus 1. Therefore -1 is considered as a valid depth, while -2 is not.
     * Once a subtree is found unbalanced, return -2 immediately to avoid useless computing.
     * That is why leftDepth and rightDepth should not be calculated together and the comparision is done later,
     * even thought the code would be cleaner in that way.
     *
     * @param node root of the binary tree
     * @return the actual depth of tree if the tree is balanced, or -2 if unbalanced.
     */
    private static int helper(TreeNode node) {
        if (node == null)
            return -1;

        int leftDepth = helper(node.left);
        if (leftDepth == -2)
            return -2;

        int rightDepth = helper(node.right);
        if (rightDepth == -2)
            return -2;

        return Math.abs(leftDepth - rightDepth) > 1 ? -2 : (Math.max(leftDepth, rightDepth) + 1);
    }

    /**
     * Unit tests
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        TreeNode tn1 = new TreeNode(1);
        TreeNode tn2 = new TreeNode(2);
        TreeNode tn3 = new TreeNode(3);
        TreeNode tn4 = new TreeNode(4);
        TreeNode tn5 = new TreeNode(5);
        TreeNode tn6 = new TreeNode(6);
        tn2.left = tn1;
        tn2.right = tn4;
        tn4.left = tn3;
        tn4.right = tn5;
        tn5.right = tn6;

        System.out.printf("Is the tree balanced? %b\n", BTBalance.isBalanced(tn2));
    }
}
