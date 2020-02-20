/******************************************************************************
 *  author:     Xiaoyu Zhang
 *  created:    2020/02/13
 *  Compilation:  javac-algs4 TreeNode.java
 *  Execution:    java-algs4 TreeNode
 *  Dependencies: StdOut.java
 *
 *  This class is the definition for the binary tree node.
 *
 ******************************************************************************/

public class TreeNode<Item> {
    public Item val;
    public TreeNode<Item> left;
    public TreeNode<Item> right;

    // point to the next node on the same level
    // This is only used for populating next right pointers
    // for a perfect binary tree, in the method connectLevelRight
    // in the class BinaryTree
    public TreeNode<Item> next;

    TreeNode(Item val) {
        this.val = val;
        this.left = null;
        this.right = null;
        this.next = null;
    }
}
