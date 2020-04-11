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

    // Given inorder and postorder traversal of a tree, construct the binary tree.
    public TreeNode<Integer> buildTree(int[] inOrder, int[] postOrder) {
        return buildTree(inOrder, inOrder.length - 1, 0, postOrder, postOrder.length - 1);
    }

    private TreeNode<Integer> buildTree(int[] inOrder, int inStart, int inEnd, int[] postOrder,
                                        int postStart) {
        if (postStart < 0 || inStart < inEnd)
            return null;

        TreeNode<Integer> root = new TreeNode<>(postOrder[postStart]);

        // find root's index from inOrder, iterating from the end
        int rootIndex = inStart;
        for (int i = inStart; i >= inEnd; i--) {
            if (inOrder[i] == postOrder[postStart]) {
                rootIndex = i;
                break;
            }
        }

        // build the left and right subtrees, going through from the end
        root.left = buildTree(inOrder, rootIndex - 1, inEnd, postOrder,
                              postStart - (inStart - rootIndex) - 1);
        root.right = buildTree(inOrder, inStart, rootIndex + 1, postOrder, postStart - 1);

        return root;
    }

    // Given inorder and postorder traversal of a tree, construct the binary tree.
    // Iterative approach
    // This approach is not easy to understand. Better to use recursive one.
    // The key idea for iterative version is:
    //      - Start from the last element of the postorder and inorder array,
    //          and put elements from postorder array to a stack. Each one
    //          is the right child of the last one, until an element in
    //          postorder array is equal to the element on the inorder array.
    //
    //      - Then, pop as many elements as we can from the stack
    //          and decrease the index in inorder array until the peek()
    //          element is not equal to the value at the index or the stack is empty.
    //
    //      - Then, the new element that we are going to retrieve from postorder
    //          array is the left child of the last element we have popped
    //          out from the stack.

    public TreeNode<Integer> buildTree2(int[] inOrder, int[] postOrder) {
        if (inOrder.length == 0)
            return null;

        int len = inOrder.length;

        LinkedStack<TreeNode<Integer>> stack = new LinkedStack<>();
        TreeNode<Integer> root = new TreeNode<>(postOrder[len - 1]);
        stack.push(root);

        // index for inOrder array
        int inIndex = len - 1;

        // index for postOrder array. Root already stored in the stack.
        int postIndex = len - 2;

        while (postIndex >= 0) {
            TreeNode<Integer> curr = stack.peek();

            if (curr.val != inOrder[inIndex]) {
                // as long as the rightmost node is not reached,
                // we can safely follow the right path and attach right child.
                TreeNode<Integer> rightNode = new TreeNode<>(postOrder[postIndex]);
                curr.right = rightNode;
                stack.push(rightNode);
                postIndex--;

            }
            else {
                // find the node on the stack, whose left subtree is not visited yet.
                while (!stack.isEmpty() && stack.peek().val == inOrder[inIndex]) {
                    curr = stack.pop();
                    inIndex--;
                }

                TreeNode<Integer> leftNode = new TreeNode<>(postOrder[postIndex]);
                curr.left = leftNode;
                stack.push(leftNode);
                postIndex--;
            }
        }

        // StdOut.printf("the value on the top of the stack: %d \n", stack.peek().val);

        return root;
    }


    // Given preorder and inorder traversal of a tree, construct the binary tree.
    public TreeNode<Integer> buildTreeFromPreIn(int[] preOrder, int[] inOrder) {
        return buildTreeFromPreIn(preOrder, 0, preOrder.length - 1, inOrder, 0, inOrder.length - 1);
    }

    /*
     * how to get the index of the right child:
     *  - Our aim is to find out the index of right child for current node in
     *      the preorder array.
     *  - We know the index of current node in the preorder array - preStart,
     *      it's the root of a subtree.
     *  - Remember pre order traversal always visit all the node on left
     *      branch before going to the right ( root -> left -> ... -> right),
     *      therefore, we can get the immediate right child index by skipping
     *      all the node on the left branches/subtrees of current node.
     *  - The inorder array has this information exactly. Remember when we
     *      found the root in "inorder" array we immediately know how many
     *      nodes are on the left subtree and how many are on the right subtree.
     *  - Therefore the immediate right child index is preStart + numsOnLeft + 1
     *      (remember in preorder traversal array root is always ahead of
     *      children nodes but you don't know which one is the left child
     *       which one is the right, and this is why we need inorder array)
     *  - numsOnLeft = root - inStart.
     */
    private TreeNode<Integer> buildTreeFromPreIn(int[] preOrder, int preStart, int preEnd,
                                                 int[] inOrder, int inStart, int inEnd) {
        if (preStart > preEnd || inStart > inEnd)
            return null;

        TreeNode<Integer> root = new TreeNode<>(preOrder[preStart]);

        // find root's index from inOrder, iterating from the beginning
        int inIndex = 0;
        for (int i = inStart; i <= inEnd; i++) {
            if (inOrder[i] == preOrder[preStart]) {
                inIndex = i;
                break;
            }
        }

        // build the left and right subtrees, going through from the beginning
        int leftTreeLen = inIndex - inStart;
        root.left = buildTreeFromPreIn(preOrder, preStart + 1, preStart + leftTreeLen,
                                       inOrder, inStart, inIndex - 1);
        root.right = buildTreeFromPreIn(preOrder, preStart + leftTreeLen + 1, preEnd,
                                        inOrder, inIndex + 1, inEnd);

        return root;
    }


    // populating next right pointers in each node for a perfect binary tree.
    // The algorithm below is similar to level order traversal.
    public TreeNode<Item> connectLevelRight(TreeNode<Item> root) {

        LinkedStack<TreeNode<Item>> stack = new LinkedStack<>();
        LinkedStack<TreeNode<Item>> levelStack = new LinkedStack<>();

        stack.push(root);
        while (!stack.isEmpty()) {
            int size = stack.size();    // the number of nodes at each level
            TreeNode<Item> nextNode = null;

            while (size != 0) {
                TreeNode<Item> currNode = stack.pop();

                // StdOut.printf("currNode %d, size %d \n", currNode.val, size);
                levelStack.push(currNode);

                currNode.next = nextNode;
                nextNode = currNode;

                size--;
            }

            while (!levelStack.isEmpty()) {
                TreeNode<Item> currLevelNode = levelStack.pop();
                if (currLevelNode.left != null)
                    stack.push(currLevelNode.left);
                if (currLevelNode.right != null)
                    stack.push(currLevelNode.right);
            }
        }

        return root;
    }

    // Iterative version with two additional pointers
    public TreeNode<Item> connectLevelRight2(TreeNode<Item> root) {

        TreeNode<Item> prev = root;

        while (prev != null && prev.left != null) {
            TreeNode<Item> curr = prev;
            while (curr != null) {
                curr.left.next = curr.right;
                curr.right.next = curr.next == null ? null : curr.next.left;
                curr = curr.next;
            }

            prev = prev.left;
        }

        return root;
    }

    // recursive version
    public TreeNode<Item> connectLevelRight3(TreeNode<Item> root) {
        connectLevelRight3(root, null);
        return root;
    }

    private void connectLevelRight3(TreeNode<Item> curr, TreeNode<Item> next) {
        if (curr == null) return;
        curr.next = next;
        connectLevelRight3(curr.left, curr.right);
        connectLevelRight3(curr.right, curr.next == null ? null : curr.next.left);
    }

    // populating next right pointers in each node for a non-perfect binary tree.
    public TreeNode<Item> connectLevelRight4(TreeNode<Item> root) {

        TreeNode<Item> head = root;     // The left most node in the lower level
        TreeNode<Item> prev = null;     // The previous node in the lower level
        TreeNode<Item> curr = null;     // The current node in the upper level

        while (head != null) {
            curr = head;
            prev = null;
            head = null;

            while (curr != null) {

                if (curr.left != null) {
                    if (prev != null)
                        prev.next = curr.left;
                    else
                        head = curr.left;
                    prev = curr.left;
                }

                if (curr.right != null) {
                    if (prev != null)
                        prev.next = curr.right;
                    else
                        head = curr.right;
                    prev = curr.right;
                }

                curr = curr.next;
            }
        }

        return root;
    }


    // populating next right pointers in each node for a non-perfect binary tree.
    public TreeNode<Item> connectLevelRight5(TreeNode<Item> root) {

        TreeNode<Item> first = null, prev = null, child = null, curr = root;

        while (curr != null) {

            if ((child = curr.left) != null) {
                if (first == null)
                    first = child;
                else
                    prev.next = child;

                prev = child;
            }

            if ((child = curr.right) != null) {
                if (first == null)
                    first = child;
                else
                    prev.next = child;

                prev = child;
            }

            if (curr.next != null)
                curr = curr.next;
            else {
                curr = first;
                first = null;
            }
        }

        return root;
    }

    public TreeNode<Item> lowestCommonAncestor(TreeNode<Item> root, TreeNode<Item> p,
                                               TreeNode<Item> q) {
        // found p or q or touch the ground
        if (root == null || root.equals(p) || root.equals(q))
            return root;

        // search p and q from left and right
        TreeNode<Item> left = lowestCommonAncestor(root.left, p, q);
        TreeNode<Item> right = lowestCommonAncestor(root.right, p, q);

        // from root's left & right we found both p and q, so root is the LCA
        if (left != null && right != null)
            return root;

        // left is not null means from left's left & right we found both q and q
        // so left is the LCA, otherwise, right is the answer
        return left != null ? left : right;
    }

    // iterative version
    // it is based on the classical iterative in-order traversal
    // (the code below the popped node is where we place our in-order processing).
    //
    // When we found p or q, we update the ancestor and ancestor level
    // variables for the found node (2nd if).
    // The ancestor level corresponds to the stack size.
    //
    // We update again those variables if we traverse a node that is an
    // ancestor of the stored ancestor (1st if).
    // We know it is an ancestor because it's level is less than the stored level.
    //
    // We continue the traversal until we meet the other node (p or q).
    // If we have a stored successor, we stop the traversal
    // (break in the 2nd if) and return it.
    public TreeNode<Item> lowestCommonAncestor2(TreeNode<Item> root, TreeNode<Item> p,
                                                TreeNode<Item> q) {
        LinkedStack<TreeNode<Item>> stack = new LinkedStack<>();
        TreeNode<Item> ancestor = null;
        int ancestorLevel = -1;
        TreeNode<Item> node = root;

        while (node != null || !stack.isEmpty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }

            root = stack.pop();
            if (stack.size() < ancestorLevel) {
                ancestor = root;
                ancestorLevel = stack.size();
            }

            if (root.val.equals(p.val) || root.val.equals(q.val)) {
                if (ancestor != null)
                    break;

                ancestor = root;
                ancestorLevel = stack.size();
            }
            node = root.right;
        }
        return ancestor;
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

        StdOut.println("\n>>> Testing: preOrderTraversal");
        BinaryTree<Character> bt = new BinaryTree<>();
        for (Character c : bt.preOrderTraversal(nF))
            StdOut.printf("%c \t", c);
        StdOut.println();

        StdOut.println("\n>>> Testing: inOrderTraversal");
        for (Character c : bt.inOrderTraversal(nF))
            StdOut.printf("%c \t", c);
        StdOut.println();

        StdOut.println("\n>>> Testing: postOrderTraversal");
        for (Character c : bt.postOrderTraversal(nF))
            StdOut.printf("%c \t", c);
        StdOut.println();

        StdOut.println("\n>>> Testing: levelOrderTraversal");
        for (LinkedQueue<Character> list : bt.levelOrderTraversal(nF)) {
            for (Character c : list)
                StdOut.printf("%c \t", c);
            StdOut.println();
        }

        StdOut.println("\n>>> Testing: maxDepth");
        StdOut.printf("The depth of the tree is %d \n", bt.maxDepth(nF));

        StdOut.println("\n>>> Testing: isSymmetric");
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

        StdOut.println("\n>>> Testing: hasPathSum");
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

        StdOut.println(
                "\n>>> Testing: construct binary tree from inOrder and postOrder traversal.");
        int[] inorder = { 9, 3, 15, 20, 7 };
        int[] postorder = { 9, 15, 7, 20, 3 };
        TreeNode<Integer> newRoot = bt2.buildTree2(inorder, postorder);
        StdOut.println("postOrderTraversal for the rebuilt tree");
        for (int num : bt2.postOrderTraversal(newRoot))
            StdOut.printf("%d \t", num);
        StdOut.println();

        StdOut.println(
                "\n>>> Testing: construct binary tree from preOrder and inOrder traversal.");
        int[] preorder = { 3, 9, 20, 15, 7 };
        TreeNode<Integer> newRoot2 = bt2.buildTreeFromPreIn(preorder, inorder);
        StdOut.println("postOrderTraversal for the rebuilt tree");
        for (int num : bt2.postOrderTraversal(newRoot2))
            StdOut.printf("%d \t", num);
        StdOut.println();

        StdOut.println("\n>>> Testing: connectLevelRight for perfect binary tree");
        TreeNode<Integer> n1_5 = new TreeNode<Integer>(1);
        TreeNode<Integer> n2_5 = new TreeNode<Integer>(2);
        TreeNode<Integer> n3_5 = new TreeNode<Integer>(3);
        TreeNode<Integer> n4_5 = new TreeNode<Integer>(4);
        TreeNode<Integer> n5_5 = new TreeNode<Integer>(5);
        TreeNode<Integer> n6_5 = new TreeNode<Integer>(6);
        TreeNode<Integer> n7_5 = new TreeNode<Integer>(7);
        n1_5.left = n2_5;
        n1_5.right = n3_5;
        n2_5.left = n4_5;
        n2_5.right = n5_5;
        n3_5.left = n6_5;
        n3_5.right = n7_5;

        TreeNode<Integer> newRoot3 = bt2.connectLevelRight3(n1_5);
        LinkedStack<TreeNode<Integer>> stack = new LinkedStack<>();
        stack.push(newRoot3);
        while (!stack.isEmpty()) {
            TreeNode<Integer> currNode = stack.pop();

            if (currNode.left != null)
                stack.push(currNode.left);

            StdOut.printf("%d \t", currNode.val);
            while (currNode.next != null) {
                currNode = currNode.next;
                StdOut.printf("%d \t", currNode.val);
            }
            StdOut.printf("# \t");
        }
        StdOut.println();

        StdOut.println("\n>>> Testing: connectLevelRight for non-perfect binary tree");
        TreeNode<Integer> n1_6 = new TreeNode<Integer>(1);
        TreeNode<Integer> n2_6 = new TreeNode<Integer>(2);
        TreeNode<Integer> n3_6 = new TreeNode<Integer>(3);
        TreeNode<Integer> n4_6 = new TreeNode<Integer>(4);
        TreeNode<Integer> n5_6 = new TreeNode<Integer>(5);
        TreeNode<Integer> n7_6 = new TreeNode<Integer>(7);
        n1_6.left = n2_6;
        n1_6.right = n3_6;
        n2_6.left = n4_6;
        n2_6.right = n5_6;
        n3_6.right = n7_6;

        TreeNode<Integer> newRoot4 = bt2.connectLevelRight5(n1_6);
        LinkedStack<TreeNode<Integer>> stack2 = new LinkedStack<>();
        stack2.push(newRoot4);
        while (!stack2.isEmpty()) {
            TreeNode<Integer> currNode = stack2.pop();

            if (currNode.left != null)
                stack2.push(currNode.left);

            StdOut.printf("%d \t", currNode.val);
            while (currNode.next != null) {
                currNode = currNode.next;
                StdOut.printf("%d \t", currNode.val);
            }
            StdOut.printf("# \t");
        }
        StdOut.println();

        StdOut.println("\n>>> Testing: lowestCommonAncestor");
        TreeNode<Integer> node = bt2.lowestCommonAncestor2(n1_6, n4_6, n5_6);
        if (node != null)
            StdOut.printf("The lowest common ancestor between %d and %d is %d \n", n4_6.val,
                          n5_6.val, node.val);

    }
}
