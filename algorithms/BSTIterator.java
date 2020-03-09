import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * created:    2020/03/09
 * <p>
 * The {@code BSTIterator} implements an iterator over a binary search tree (BST). Your iterator
 * will be initialized with the root node of a BST.
 * <p>
 * Time complexity : O(N)
 * <p>
 * Space complexity : O(N)
 *
 * @author Xiaoyu Zhang
 */

public class BSTIterator {
    private List<Integer> list;
    private Iterator<Integer> iterator;

    public BSTIterator(TreeNode root) {
        this.list = new ArrayList<>();
        inOrderTraversal(root);

        this.iterator = this.list.iterator();
    }

    /**
     * @return the next smallest number
     */
    public int next() {
        return iterator.next();
    }

    /**
     * @return true if there is a next smallest number, false otherwise.
     */
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * @param root root of a BST
     * @return the list of inorder traversal
     */
    private void inOrderTraversal(TreeNode root) {

        if (root == null)   // base case
            return;

        inOrderTraversal(root.left);
        list.add(root.value);
        inOrderTraversal(root.right);
    }

    /**
     * Print the BST.
     */
    public void printBST() {
        System.out.print(">>> BST inOrderTraversal:\t");
        while (hasNext())
            System.out.printf("%d\t", next());

        System.out.println();
        this.iterator = this.list.iterator();
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

        BSTIterator bstIterator = new BSTIterator(tn4);
        bstIterator.printBST();

        System.out.printf("next element: %d\n", bstIterator.next());
        System.out.printf("is there next element? %b\n", bstIterator.hasNext());
        System.out.printf("next element: %d\n", bstIterator.next());
    }
}
