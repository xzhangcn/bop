import java.util.Deque;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;


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
     * <p>
     * The maximum depth is the number of nodes along the longest path from the root node down to the farthest leaf node.
     * <p>
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
     * Problem 3: Symmetric Tree.
     * <p>
     * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
     *
     * @param root root of a binary search tree
     * @return true if left subtree is a mirror reflection of right subtree, false otherwise.
     */
    public boolean isSymmetric(TreeNode root) {
        return isMirror(root, root);
    }

    // helper function to isSymmetric
    private boolean isMirror(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null)
            return true;
        if (t1 == null || t2 == null)
            return false;

        return (t1.val == t2.val) && isMirror(t1.right, t2.left) && isMirror(t1.left, t2.right);
    }

    // Iterative version with the aid of a queue.
    // It works similarly to BFS, with some differences.
    public boolean isSymmetric2(TreeNode root) {
        // Instead of recursion, we can also use iteration with the aid of a queue.
        // Each two consecutive nodes in the queue should be equal, and their subtrees a mirror of each other.
        // Initially, the queue contains root and root. Then the algorithm works similarly to BFS, with some key differences.
        // Each time, two nodes are extracted and their values compared.
        // Then, the right and left children of the two nodes are inserted in the queue in opposite order.
        // The algorithm is done when either the queue is empty, or we detect that the tree is not symmetric
        // (i.e. we pull out two consecutive nodes from the queue that are unequal).

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode t1 = queue.poll();
            TreeNode t2 = queue.poll();
            if (t1 == null && t2 == null)
                continue;
            if (t1 == null || t2 == null)
                return false;
            if (t1.val != t2.val)
                return false;
            queue.add(t1.left);
            queue.add(t2.right);
            queue.add(t1.right);
            queue.add(t2.left);
        }

        return true;
    }

    /**
     * Problem 4: Binary Tree Level Order Traversal.
     * <p>
     * Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
     *
     * @param root root of a binary search tree
     * @return true the level order traversal of its nodes' values.
     */
    public List<List<Integer>> levelOrder(TreeNode root) {

        // Level order traversal is a special case of BFS.
        List<List<Integer>> result = new LinkedList<>();
        if (root == null)
            return result;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();                    // the number of nodes at each level
            List<Integer> level = new LinkedList<>();   // a list created at each level to hold nodes

            while (size != 0) {
                TreeNode curr = queue.poll();
                level.add(curr.val);
                if (curr.left != null)
                    queue.add(curr.left);
                if (curr.right != null)
                    queue.add(curr.right);

                size--;
            }

            result.add(level);
        }

        return result;
    }

    /**
     * Problem 5: Convert Sorted Array to Binary Search Tree.
     * <p>
     * Given an array where elements are sorted in ascending order, convert it to a height balanced BST.
     * <p>
     * For this problem, a height-balanced binary tree is defined as a binary tree
     * in which the depth of the two subtrees of every node never differ by more than 1.
     *
     * @param nums a sorted array
     * @return root of the BST.
     */
    public TreeNode sortedArrayToBST(int[] nums) {

        return sortedArrayToBSTHelper(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayToBSTHelper(int[] nums, int left, int right) {
        if (left > right)
            return null ;

        // if (left == right)
            // return new TreeNode(nums[left]);

        int mid = left + (right - left) / 2;

        System.out.printf("TRACE: left = %d, right = %d, mid = %d\n", left, right, mid);

        TreeNode node = new TreeNode(nums[mid]);
        node.left = sortedArrayToBSTHelper(nums, left, mid - 1);
        node.right = sortedArrayToBSTHelper(nums, mid + 1, right);

        return node;
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

        System.out.println("\n>>> Problem 3: Symmetric Tree.");
        /*
        TreeNode tn3_1 = new TreeNode(1);
        TreeNode tn3_2 = new TreeNode(2);
        TreeNode tn3_3 = new TreeNode(2);
        TreeNode tn3_4 = new TreeNode(3);
        TreeNode tn3_5 = new TreeNode(4);
        TreeNode tn3_6 = new TreeNode(4);
        TreeNode tn3_7 = new TreeNode(3);

        tn3_1.left = tn3_2;
        tn3_1.right = tn3_3;
        tn3_2.left = tn3_4;
        tn3_2.right = tn3_5;
        tn3_3.left = tn3_6;
        tn3_3.right = tn3_7;

         */
        TreeNode tn3_1 = new TreeNode(1);
        TreeNode tn3_2 = new TreeNode(2);
        TreeNode tn3_3 = new TreeNode(2);
        TreeNode tn3_4 = new TreeNode(2);
        TreeNode tn3_5 = new TreeNode(2);

        tn3_1.left = tn3_2;
        tn3_1.right = tn3_3;
        tn3_2.left = tn3_4;
        tn3_3.left = tn3_5;

        leetTree.show(tn3_1);
        System.out.printf("Is this a symmetric tree? %b\n", leetTree.isSymmetric(tn3_1));

        System.out.println("\n>>> Problem 4: Binary Tree Level Order Traversal.");
        for (List<Integer> list : leetTree.levelOrder(tn1_1)) {
            for (int val : list)
                StdOut.printf("%d \t", val);
            StdOut.println();
        }

        System.out.println("\n>>> Problem 5: Convert Sorted Array to Binary Search Tree.");
        int[] tn5_nums = {-10, -3, 0, 5, 9};
        TreeNode tn5_root = leetTree.sortedArrayToBST(tn5_nums);
        for (List<Integer> list : leetTree.levelOrder(tn5_root)) {
            for (int val : list)
                StdOut.printf("%d \t", val);
            StdOut.println();
        }
    }
}
