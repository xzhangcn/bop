import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;

/******************************************************************************
 *  author:     Xiaoyu Zhang
 *  created:    2020/02/13
 *  Compilation:  javac-algs4 BinaryTree.java
 *  Execution:    java-algs4 BinaryTree
 *  Dependencies: StdOut.java
 *
 *  This generic class is the collection of classic algorithms for binary tree.
 *  I purposely group together to cement my understanding of tree and recursion.
 *
 ******************************************************************************/

public class BinaryTree<Item> {

    public BinaryTree() {
    }

    public LinkedQueue<Item> preOrderTraversal(TreeNode<Item> root) {
        LinkedQueue<Item> result = new LinkedQueue<>();

        preOrderTraversal(root, result);
        return result;
    }

    private void preOrderTraversal(TreeNode<Item> node, LinkedQueue<Item> list) {
        if (node == null)   // base case
            return;

        list.enqueue(node.val);
        preOrderTraversal(node.left, list);
        preOrderTraversal(node.right, list);
    }

    public LinkedQueue<Item> inOrderTraversal(TreeNode<Item> root) {
        LinkedQueue<Item> result = new LinkedQueue<>();

        inOrderTraversal(root, result);
        return result;
    }

    private void inOrderTraversal(TreeNode<Item> node, LinkedQueue<Item> list) {
        if (node == null)   // base case
            return;

        inOrderTraversal(node.left, list);
        list.enqueue(node.val);
        inOrderTraversal(node.right, list);
    }

    public LinkedQueue<Item> postOrderTraversal(TreeNode<Item> root) {
        LinkedQueue<Item> result = new LinkedQueue<>();

        postOrderTraversal(root, result);
        return result;
    }

    private void postOrderTraversal(TreeNode<Item> node, LinkedQueue<Item> list) {
        if (node == null)   // base case
            return;

        postOrderTraversal(node.left, list);
        postOrderTraversal(node.right, list);
        list.enqueue(node.val);
    }

    // Level order traversal is a special case of BFS.
    public LinkedQueue<LinkedQueue<Item>> levelOrderTraversal(TreeNode<Item> root) {
        LinkedQueue<LinkedQueue<Item>> result = new LinkedQueue<>();
        if (root == null)
            return result;

        LinkedQueue<TreeNode<Item>> queue = new LinkedQueue<>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            int size = queue.size();    // the number of nodes at each level

            // a list created at each level to hold nodes
            LinkedQueue<Item> level = new LinkedQueue<>();

            while (size != 0) {
                TreeNode<Item> curr = queue.dequeue();
                level.enqueue(curr.val);
                if (curr.left != null)
                    queue.enqueue(curr.left);
                if (curr.right != null)
                    queue.enqueue(curr.right);

                size--;
            }

            result.enqueue(level);
        }

        return result;
    }

    // Find the depth of a binary tree with bottom-up approach
    public int maxDepth(TreeNode<Item> root) {
        if (root == null)   // base case
            return 0;

        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);

        return Math.max(leftDepth, rightDepth) + 1;
    }

    // return true if left subtree is a mirror reflection of right subtree.
    public boolean isSymmetric(TreeNode<Item> root) {
        return isMirror(root, root);
    }

    private boolean isMirror(TreeNode<Item> t1, TreeNode<Item> t2) {
        if (t1 == null && t2 == null)
            return true;
        if (t1 == null || t2 == null)
            return false;

        return (t1.val.equals(t2.val))
                && isMirror(t1.right, t2.left)
                && isMirror(t1.left, t2.right);
    }

    // Iterative version with the aid of a queue.
    // It works similarly to BFS, with certain differences.
    public boolean isSymmetric2(TreeNode<Item> root) {
        LinkedQueue<TreeNode<Item>> queue = new LinkedQueue<>();
        queue.enqueue(root);
        queue.enqueue(root);

        while (!queue.isEmpty()) {
            TreeNode<Item> t1 = queue.dequeue();
            TreeNode<Item> t2 = queue.dequeue();
            if (t1 == null && t2 == null)
                continue;
            if (t1 == null || t2 == null)
                return false;
            if (!t1.val.equals(t2.val))
                return false;
            queue.enqueue(t1.left);
            queue.enqueue(t2.right);
            queue.enqueue(t1.right);
            queue.enqueue(t2.left);
        }

        return true;
    }

    // another algorithm to check if a tree is symmetric.
    // run inOrderTraversal, then go through the result list to check
    // the values at the two ends
    public boolean isSymmetric3(TreeNode<Item> root) {
        LinkedQueue<Item> result = inOrderTraversal(root);
        int size = result.size();
        if (size % 2 == 0)
            return false;

        // As java not support generic array, so use ArrayList instead.
        // It may be better if return an ArrayList from inOrderTraversal,
        // such that there is not need to copy elements to an ArrayList here.
        ArrayList<Item> al = new ArrayList<>();
        for (Item item : result) {
            al.add(item);
            // StdOut.println((Object) item);
        }

        for (int i = 0; i < size / 2; i++)
            if (!al.get(i).equals(al.get(size - 1 - i)))
                return false;

        return true;
    }

    // Given a binary tree and a sum, determine if the tree has a root-to-leaf path
    // such that adding up all the values along the path equals the given sum.
    public boolean hasPathSum(TreeNode<Integer> root, int sum) {
        if (root == null)
            return false;
        if (root.left == null && root.right == null)
            return (root.val == sum);

        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }

    // iterative version
    // With the aid of two stacks keeping track of nodes and values, this
    // algorithm is easier to understand compared to other iterative approaches.
    public boolean hasPathSum2(TreeNode<Integer> root, int sum) {
        if (root == null)
            return false;
        LinkedStack<TreeNode<Integer>> nodeStack = new LinkedStack<>();
        LinkedStack<Integer> valueStack = new LinkedStack<>();

        nodeStack.push(root);
        valueStack.push(root.val);
        while (!nodeStack.isEmpty()) {
            TreeNode<Integer> node = nodeStack.pop();
            int value = valueStack.pop();
            if (node.left == null && node.right == null && value == sum)
                return true;
            else {
                if (node.left != null) {
                    nodeStack.push(node.left);
                    valueStack.push(node.left.val + value);
                }

                if (node.right != null) {
                    nodeStack.push(node.right);
                    valueStack.push(node.right.val + value);
                }
            }
        }

        return false;
    }

    /**
     * Unit tests the {@code FileManager} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        TreeNode<Character> nF = new TreeNode<Character>('F');
        TreeNode<Character> nB = new TreeNode<Character>('B');
        TreeNode<Character> nG = new TreeNode<Character>('G');
        TreeNode<Character> nA = new TreeNode<Character>('A');
        TreeNode<Character> nD = new TreeNode<Character>('D');
        TreeNode<Character> nI = new TreeNode<Character>('I');
        TreeNode<Character> nC = new TreeNode<Character>('C');
        TreeNode<Character> nE = new TreeNode<Character>('E');
        TreeNode<Character> nH = new TreeNode<Character>('H');

        nF.left = nB;
        nF.right = nG;
        nB.left = nA;
        nB.right = nD;
        nG.right = nI;
        nD.left = nC;
        nD.right = nE;
        nI.left = nH;

        StdOut.println("\n Testing: preOrderTraversal");
        BinaryTree<Character> bt = new BinaryTree<>();
        for (Character c : bt.preOrderTraversal(nF))
            StdOut.printf("%c \t", c);
        StdOut.println();

        StdOut.println("\n Testing: inOrderTraversal");
        for (Character c : bt.inOrderTraversal(nF))
            StdOut.printf("%c \t", c);
        StdOut.println();

        StdOut.println("\n Testing: postOrderTraversal");
        for (Character c : bt.postOrderTraversal(nF))
            StdOut.printf("%c \t", c);
        StdOut.println();

        StdOut.println("\n Testing: levelOrderTraversal");
        for (LinkedQueue<Character> list : bt.levelOrderTraversal(nF)) {
            for (Character c : list)
                StdOut.printf("%c \t", c);
            StdOut.println();
        }

        StdOut.println("\n Testing: maxDepth");
        StdOut.printf("The depth of the tree is %d \n", bt.maxDepth(nF));

        StdOut.println("\n Testing: isSymmetric");
        StdOut.printf("The tree is symmetric? %b \n", bt.isSymmetric(nF));

        TreeNode<Integer> n1 = new TreeNode<Integer>(1);
        TreeNode<Integer> n2_1 = new TreeNode<Integer>(2);
        TreeNode<Integer> n2_2 = new TreeNode<Integer>(2);
        n1.left = n2_1;
        n1.right = n2_2;
        BinaryTree<Integer> bt2 = new BinaryTree<>();
        StdOut.printf("The tree is symmetric? %b \n", bt2.isSymmetric(n1));
        StdOut.printf("The tree is symmetric? %b \n", bt2.isSymmetric2(n1));
        StdOut.printf("The tree is symmetric? %b \n", bt2.isSymmetric3(n1));

        StdOut.println("\n Testing: hasPathSum");
        TreeNode<Integer> n5 = new TreeNode<Integer>(5);
        TreeNode<Integer> n4 = new TreeNode<Integer>(4);
        TreeNode<Integer> n8 = new TreeNode<Integer>(8);
        TreeNode<Integer> n11 = new TreeNode<Integer>(11);
        TreeNode<Integer> n13 = new TreeNode<Integer>(13);
        TreeNode<Integer> n4_2 = new TreeNode<Integer>(4);
        TreeNode<Integer> n7 = new TreeNode<Integer>(7);
        TreeNode<Integer> n2 = new TreeNode<Integer>(2);
        TreeNode<Integer> n1_3 = new TreeNode<Integer>(1);
        n5.left = n4;
        n5.right = n8;
        n4.left = n11;
        n8.left = n13;
        n8.right = n4_2;
        n11.left = n7;
        n11.right = n2;
        n4.right = n1_3;

        int sum = 22;

        StdOut.printf("recurisve approach: The tree has a path which sums up to %d? %b \n", sum,
                      bt2.hasPathSum(n5, sum));
        StdOut.printf("iterative approach: The tree has a path which sums up to %d? %b \n", sum,
                      bt2.hasPathSum2(n5, sum));
    }
}
