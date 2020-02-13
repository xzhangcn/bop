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

    TreeNode(Item val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}
