import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdOut;


/******************************************************************************
 *  author:     Xiaoyu Zhang
 *  created:    2020/02/19
 *  Compilation:  javac-algs4 IntBinaryTree.java
 *  Execution:    java-algs4 IntBinaryTree
 *  Dependencies: StdOut.java
 *
 *  This class is the definition for the binary tree with integer value.
 *
 ******************************************************************************/

public class IntBinaryTree {
    public IntBinaryTree() {
    }

    // use typical BFS method to handle binary tree
    public String serialize(IntTreeNode root) {
        if (root == null)
            return "";

        LinkedQueue<IntTreeNode> queue = new LinkedQueue<>();
        StringBuilder res = new StringBuilder();

        int count = 0;
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            IntTreeNode node = queue.dequeue();
            if (node == null) {
                res.append("# ");
                count++;
                continue;
            }
            res.append(node.val + " ");
            count++;

            queue.enqueue(node.left);
            queue.enqueue(node.right);
        }

        return res.toString();
    }

    // assign left and right child for each non-null node,
    // and add non-null children to the queue, waiting to be handled later
    public IntTreeNode deserialize(String data) {
        if (data.equals(""))
            return null;

        LinkedQueue<IntTreeNode> queue = new LinkedQueue<>();
        String[] values = data.split(" ");
        IntTreeNode root = new IntTreeNode(Integer.parseInt(values[0]));
        queue.enqueue(root);

        int i = 1;
        while (i < values.length) {
            IntTreeNode parent = queue.dequeue();

            // handle left child
            if (!values[i].equals("#")) {
                IntTreeNode left = new IntTreeNode(Integer.parseInt(values[i]));
                parent.left = left;
                queue.enqueue(left);
            }

            // handle right child
            i++;
            if (!values[i].equals("#")) {
                IntTreeNode right = new IntTreeNode(Integer.parseInt(values[i]));
                parent.right = right;
                queue.enqueue(right);
            }

            i++;
        }

        return root;
    }


    public LinkedQueue<Integer> inOrderTraversal(IntTreeNode root) {
        LinkedQueue<Integer> result = new LinkedQueue<>();

        inOrderTraversal(root, result);
        return result;
    }

    private void inOrderTraversal(IntTreeNode node, LinkedQueue<Integer> list) {
        if (node == null)   // base case
            return;

        inOrderTraversal(node.left, list);
        list.enqueue(node.val);
        inOrderTraversal(node.right, list);
    }

    public static void main(String[] args) {
        IntTreeNode n1 = new IntTreeNode(1);
        IntTreeNode n2 = new IntTreeNode(2);
        IntTreeNode n3 = new IntTreeNode(3);
        IntTreeNode n4 = new IntTreeNode(4);
        IntTreeNode n5 = new IntTreeNode(5);

        n1.left = n2;
        n1.right = n3;
        n3.left = n4;
        n3.right = n5;

        IntBinaryTree ibt = new IntBinaryTree();
        String res = ibt.serialize(n1);
        StdOut.printf("%s \n", res);

        IntTreeNode root = ibt.deserialize(res);
        StdOut.println("\n>>> Testing: inOrderTraversal");
        for (int val : ibt.inOrderTraversal(root))
            StdOut.printf("%d \t", val);
        StdOut.println();
    }
}
