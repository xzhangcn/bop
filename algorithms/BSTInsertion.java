/**
 * created:    2020/03/11
 * <p>
 * Given the root node of a binary search tree (BST) and a value to be inserted into the tree,
 * insert the value into the BST. Return the root node of the BST after the insertion. It is
 * guaranteed that the new value does not exist in the original BST.
 *
 * @author Xiaoyu Zhang
 */

public class BSTInsertion {

    /**
     * Iterative approach version 1
     *
     * @param root root of the BST
     * @param val  value to be inserted
     * @return root node of the BST after the insertion
     */
    public static TreeNode insertIntoBST(TreeNode root, int val) {
        TreeNode curr = root;
        TreeNode prev = null;
        boolean isLeft = false;

        while (curr != null) {
            prev = curr;
            if (curr.value > val) {
                isLeft = true;
                curr = curr.left;
            }
            else {
                isLeft = false;
                curr = curr.right;
            }
        }

        TreeNode newNode = new TreeNode(val);
        if (prev == null)
            return newNode;
        else {
            if (isLeft)
                prev.left = newNode;
            else
                prev.right = newNode;
        }

        return root;
    }

    /**
     * Iterative approach version 2. A bit more succint compare to version 1, and no need to use the
     * prev node.
     *
     * @param root root of the BST
     * @param val  value to be inserted
     * @return root node of the BST after the insertion
     */
    public static TreeNode insertIntoBST2(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);

        TreeNode curr = root;
        while (true) {
            if (curr.value <= val) {
                if (curr.right != null)
                    curr = curr.right;
                else {
                    curr.right = new TreeNode(val);
                    break;
                }
            }
            else {
                if (curr.left != null)
                    curr = curr.left;
                else {
                    curr.left = new TreeNode(val);
                    break;
                }
            }
        }

        return root;
    }

    /**
     * Recursive approach
     *
     * @param root root of the BST
     * @param val  value to be inserted
     * @return root node of the BST after the insertion
     */
    public static TreeNode insertIntoBST3(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);

        if (root.value > val)
            root.left = insertIntoBST3(root.left, val);
        else
            root.right = insertIntoBST3(root.right, val);

        return root;
    }

    /**
     * Unit tests
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        int[] values = { 4, 2, 7, 1, 3 };

        TreeNode node = null;
        for (int i = 0; i < values.length; i++) {
            node = BSTInsertion.insertIntoBST2(node, values[i]);
        }

        BSTValidation.printBST(node);
    }
}
