import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * created:    2020/03/08
 * <p>
 * The {@code BSTValidation} class determines if a tree is a valid binary search tree.
 *
 * @author Xiaoyu Zhang
 */

public class BSTValidation {

    /**
     * @param root root of a binary search tree
     * @return true if it is a valid BST, false otherwise.
     */
    public static boolean isValidBST(TreeNode root) {

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

        Integer value = (Integer) node.value;
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
        int min = Integer.MIN_VALUE;

        while (!deck.isEmpty() || root != null) {
            while (root != null) {
                deck.addFirst(root);
                root = root.left;
            }

            root = deck.removeFirst();

            // If next element in inorder traversal is smaller than the previous one,
            // that's not BST.
            if (root.value <= min)
                return false;
            min = root.value;
            root = root.right;
        }

        return true;
    }

    /**
     * @param root root of a BST
     * @return the list of inorder traversal
     */
    public static List<Integer> inOrderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        inOrderTraversalHelper(root, result);
        return result;
    }

    /**
     * @param node root of a BST
     * @param list the list to store of inorder traversal result
     */
    private static void inOrderTraversalHelper(TreeNode node, List<Integer> list) {
        if (node == null)   // base case
            return;

        inOrderTraversalHelper(node.left, list);
        list.add(node.value);
        inOrderTraversalHelper(node.right, list);
    }

    /**
     * @param root root of a BST
     */
    public static void printBST(TreeNode root) {
        System.out.print("\n>>> BST inOrderTraversal:\t");
        for (Integer val : inOrderTraversal(root))
            System.out.printf("%d ", val);
        System.out.println();
    }

    /**
     * Unit tests.
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

        BSTValidation.printBST(tn4);
        System.out.printf("Recursive approach: is it a valid BST? %b\n",
                          BSTValidation.isValidBST(tn4));
        System.out.printf("Iterative approach: is it a valid BST? %b\n",
                          BSTValidation.isValidBST2(tn4));

        TreeNode tn5 = new TreeNode(5);
        TreeNode tn6 = new TreeNode(6);
        TreeNode tn10 = new TreeNode(10);
        TreeNode tn15 = new TreeNode(15);
        TreeNode tn20 = new TreeNode(20);
        tn10.left = tn5;
        tn10.right = tn15;
        tn15.left = tn6;
        tn15.right = tn20;

        BSTValidation.printBST(tn10);
        System.out.printf("Recursive approach: is it a valid BST? %b\n",
                          BSTValidation.isValidBST(tn10));
        System.out.printf("Iterative approach: is it a valid BST? %b\n",
                          BSTValidation.isValidBST2(tn10));
    }
}
