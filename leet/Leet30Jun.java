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
     * Unit tests
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("\n>>> Problem on the day of 06/01/2020: Invert Binary Tree.");
    }
}
