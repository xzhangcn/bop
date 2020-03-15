/**
 * created:    2020/03/15
 * <p>
 * Given a binary search tree (BST), find the lowest common ancestor (LCA) of two given nodes in the
 * BST.
 * <p>
 * The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has
 * both p and q as descendants (where we allow a node to be a descendant of itself).
 *
 * @author Xiaoyu Zhang
 */

public class BSTCommonAncestor {

    /**
     * Recursive approach
     * <p>
     * Time Complexity: O(N), where N is the number of nodes in the BST. In the worst case we might
     * be visiting all the nodes of the BST.
     * <p>
     * Space Complexity: O(N). This is because the maximum amount of space utilized by the recursion
     * stack would be N since the height of a skewed BST could be N.
     *
     * @param root root of the BST
     * @param p    one node in the BST
     * @param q    another node in the BST
     * @return the lowest common ancestor
     */
    public static TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        int parentVal = root.value;
        int pVal = p.value;
        int qVal = q.value;

        if (pVal > parentVal && qVal > parentVal)
            return lowestCommonAncestor(root.right, p, q);
        else if (pVal < parentVal && qVal < parentVal)
            return lowestCommonAncestor(root.left, p, q);
        else
            return root;
    }

    /**
     * Iterative approach
     * <p>
     * Time Complexity : O(N), where N is the number of nodes in the BST. In the worst case we might
     * be visiting all the nodes of the BST.
     * <p>
     * Space Complexity : O(1).
     *
     * @param root root of the BST
     * @param p    one node in the BST
     * @param q    another node in the BST
     * @return the lowest common ancestor
     */
    public static TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        int pVal = p.value;
        int qVal = q.value;
        TreeNode curr = root;

        while (curr != null) {
            int parentVal = curr.value;

            if (pVal > parentVal && qVal > parentVal)
                curr = curr.right;
            else if (pVal < parentVal && qVal < parentVal)
                curr = curr.left;
            else
                return curr;
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
        TreeNode tn5 = new TreeNode(5);
        tn2.left = tn1;
        tn2.right = tn4;
        tn4.left = tn3;
        tn4.right = tn5;

        BSTValidation.printBST(tn2);

        System.out.printf("The lowest common ancestor between {%d} and {%d} is {%d}.\n", tn3.value,
                          tn5.value, BSTCommonAncestor.lowestCommonAncestor2(tn2, tn1, tn5).value);

    }
}
