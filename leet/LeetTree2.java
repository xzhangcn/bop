import java.util.Queue;
import java.util.LinkedList;

/**
 * created:    2020/04/06
 * <p>
 * This is second collection of leetcode problems for the topic of Tree.
 *
 * @author Xiaoyu Zhang
 */

public class LeetTree2 {
    /**
     * Problem 1: Maximum Width of Binary Tree.
     * <p>
     * Given a binary tree, write a function to get the maximum width of the given tree. The width of a tree is
     * the maximum width among all levels. The binary tree has the same structure as a full binary tree, but some nodes are null.
     * <p>
     * The width of one level is defined as the length between the end-nodes (the leftmost and right most non-null nodes in the level,
     * where the null nodes between the end-nodes are also counted into the length calculation.
     *
     * @param root root node of a binary tree
     * @return the max width
     */
    public int widthOfBinaryTree(TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        Queue<Integer> qIndex = new LinkedList<>();
        q.add(root);
        qIndex.add(1);              //store index, assuming root's index is 1

        int max = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            int start = 0, end = 0; //start and end index for each level

            for (int i = 0; i < size; i++) {
                TreeNode node = q.remove();
                int index = qIndex.remove();

                if (i == 0) start = index;
                if (i == size - 1) end = index;

                if (node.left != null) {
                    q.add(node.left);
                    qIndex.add(2 * index);
                }

                if (node.right != null) {
                    q.add(node.right);
                    qIndex.add(2 * index + 1);
                }
            }

            max = Math.max(max, end - start + 1);
        }

        return max;
    }

    /**
     * Unit tests
     *
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        LeetTree2 leetTree2 = new LeetTree2();

        System.out.println("\n>>> Problem 1: Maximum Width of Binary Tree.");
        TreeNode tn1_1 = new TreeNode(1);
        TreeNode tn1_2 = new TreeNode(3);
        TreeNode tn1_3 = new TreeNode(2);
        TreeNode tn1_4 = new TreeNode(5);
        TreeNode tn1_5 = new TreeNode(9);
        TreeNode tn1_6 = new TreeNode(6);
        TreeNode tn1_7 = new TreeNode(7);

        tn1_1.left = tn1_2;
        tn1_1.right = tn1_3;
        tn1_2.left = tn1_4;
        tn1_3.right = tn1_5;
        tn1_4.left = tn1_6;
        tn1_5.right = tn1_7;

        System.out.printf("The maximum width of the binary tree is %d\n", leetTree2.widthOfBinaryTree(tn1_1));
    }
}
