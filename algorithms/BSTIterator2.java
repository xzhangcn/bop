import java.util.ArrayDeque;
import java.util.Deque;

/**
 * created:    2020/03/09
 * <p>
 * The {@code BSTIterator} implements an iterator over a binary search tree (BST). Your iterator
 * will be initialized with the root node of a BST.
 * <p>
 * Time complexity : O(N)
 * <p>
 * Space complexity : O(h), where h is the height of the tree
 *
 * @author Xiaoyu Zhang
 */

public class BSTIterator2 {
    // use Deque in place of Stack for the recursion simulation
    Deque<TreeNode> deck;

    public BSTIterator2(TreeNode root) {
        this.deck = new ArrayDeque<>();
        leftMostInOrder(root);
    }

    /**
     * @param node node to be processed
     */
    private void leftMostInOrder(TreeNode node) {

        // for a give node, add all the nodes of its left subtree.
        while (node != null) {
            this.deck.addFirst(node);
            node = node.left;
        }
    }

    /**
     * @return the next smallest element
     */
    public int next() {
        // Node at the top of the stack is the next smallest element
        TreeNode top = this.deck.removeFirst();

        // Need to maintain the invariant. If the node has a right child,
        // call the helper function for the right child
        if (top.right != null)
            leftMostInOrder(top.right);

        return top.value;
    }

    /**
     * @return true if there is a next smallest element, false otherwise.
     */
    public boolean hasNext() {
        return this.deck.size() > 0;
    }

    public static void main(String[] args) {
        TreeNode tn1 = new TreeNode(1);
        TreeNode tn2 = new TreeNode(2);
        TreeNode tn3 = new TreeNode(3);
        TreeNode tn4 = new TreeNode(4);
        tn2.left = tn1;
        tn2.right = tn3;
        tn4.left = tn2;

        BSTIterator bstIterator = new BSTIterator(tn4);

        System.out.printf("next element: %d\n", bstIterator.next());
        System.out.printf("is there next element? %b\n", bstIterator.hasNext());
        System.out.printf("next element: %d\n", bstIterator.next());
    }
}
