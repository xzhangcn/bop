/**
 * created:    2020/03/12
 * <p>
 * Given a root node reference of a BST and a key, delete the node with the given key in the BST.
 * Return the root node reference (possibly updated) of the BST.
 * <p>
 * Basically, the deletion can be divided into two stages: Search for a node to remove; If the node
 * is found, delete the node.
 *
 * @author Xiaoyu Zhang
 */

public class BSTDeletion {

    /**
     * Iterative approach
     *
     * @param root root of a BST
     * @param key  value to be deleted
     * @return root node of the updated BST
     */
    public static TreeNode deleteNode2(TreeNode root, int key) {
        TreeNode target = root, parent = null;

        // Search Node
        while (target != null && target.value != key) {
            parent = target;
            if (key > target.value)
                target = target.right;
            else target = target.left;
        }

        // not found
        if (target == null) return root;

        // Case 1 : No children
        if (target.left == null && target.right == null) {
            if (parent == null) return null;

            if (target.equals(parent.left))
                parent.left = null;
            else
                parent.right = null;

            return root;
        }

        // Case 2 : One Child
        // Case 2.1 : No right child
        if (target.right == null) {
            if (parent == null)
                return target.left;     // If target node is root itself

            if (target.equals(parent.left))
                parent.left = target.left;
            else
                parent.right = target.left;

            return root;
        }

        // Case 2.2 : No left child
        if (target.left == null) {
            if (parent == null)
                return target.right;

            if (target.equals(parent.left))
                parent.left = target.right;
            else
                parent.right = target.right;
            return root;
        }

        // Case 3 : Both the child nodes present
        {
            /* Whenever we delete a node with two child then we replace that node with the
			   leftmost element from the right subtree because to hold the property of BST
			   all the element to the right of the new node will be greater than it and all the
			   left ones will be lesser than it
            */
            // Trace to the leftmost element in Right subtree
            TreeNode prev = target, p = target.right;
            while (p.left != null) {
                prev = p;
                p = p.left;
            }

            target.value = p.value;
            if (p.equals(prev.left))
                prev.left = p.right;
            else
                prev.right = p.right;

            return root;
        }
    }


    /**
     * Recursive approach 1
     *
     * @param root root of a BST
     * @param key  value to be deleted
     * @return root node of the updated BST
     */
    public static TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;

        if (key < root.value) {
            root.left = deleteNode(root.left, key);
            return root;
        }

        if (key > root.value) {
            root.right = deleteNode(root.right, key);
            return root;
        }

        // root.val == key : it's this node.

        // no children
        if (root.left == null && root.right == null) return null;

        //  one child
        if (root.left == null) return root.right;
        if (root.right == null) return root.left;


        // two children: splice in predecessor
        TreeNode prev = root.left;
        while (prev.right != null) prev = prev.right;

        deleteNode(root, prev.value);
        prev.right = root.right;
        prev.left = root.left;
        return prev;
    }

    /**
     * Recursive approach 2
     *
     * @param root root of a BST
     * @param key  value to be deleted
     * @return root node of the updated BST
     */
    public static TreeNode deleteNode3(TreeNode root, int key) {
        if (root == null) return null;
        if (root.value > key) {
            root.left = deleteNode(root.left, key);
        }
        else if (root.value < key) {
            root.right = deleteNode(root.right, key);
        }
        else {
            if (root.left == null || root.right == null) {
                root = (root.left == null) ? root.right : root.left;
            }
            else {
                TreeNode curNode = root.right;
                while (curNode.left != null) curNode = curNode.left;
                root.value = curNode.value;
                root.right = deleteNode(root.right, curNode.value);
            }
        }

        return root;
    }

    public static void main(String[] args) {
        int[] values = { 4, 2, 7, 1, 3 };

        TreeNode node = null;
        for (int i = 0; i < values.length; i++) {
            node = BSTInsertion.insertIntoBST2(node, values[i]);
        }

        BSTValidation.printBST(node);

        TreeNode newRoot = BSTDeletion.deleteNode3(node, 1);
        BSTValidation.printBST(newRoot);
    }
}
