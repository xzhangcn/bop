import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdOut;

/******************************************************************************
 *  author:     Xiaoyu Zhang
 *  created:    2020/02/13
 *  Compilation:  javac-algs4 BinaryTree.java
 *  Execution:    java-algs4 BinaryTree
 *  Dependencies: StdOut.java
 *
 *  This class is the collection of classic algorithms for binary tree.
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

        BinaryTree<Character> bt = new BinaryTree<>();
        for (Character c : bt.preOrderTraversal(nF))
            StdOut.printf("%c \t", c);
        StdOut.println();

        for (Character c : bt.inOrderTraversal(nF))
            StdOut.printf("%c \t", c);
        StdOut.println();

        for (Character c : bt.postOrderTraversal(nF))
            StdOut.printf("%c \t", c);
        StdOut.println();
    }
}
