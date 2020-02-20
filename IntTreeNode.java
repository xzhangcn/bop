/******************************************************************************
 *  author:     Xiaoyu Zhang
 *  created:    2020/02/19
 *  Compilation:  javac-algs4 TreeNode.java
 *  Execution:    java-algs4 TreeNode
 *  Dependencies: StdOut.java
 *
 *  This class is the definition for the binary tree node with integer value.
 *
 ******************************************************************************/

public class IntTreeNode {
    public int val;
    public IntTreeNode left;
    public IntTreeNode right;

    IntTreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}
