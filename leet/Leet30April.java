import java.util.*;

/**
 * created:    2019/04/02
 * <p>
 * {@code Leet30April} is the 30-day LeetCoding challenge.
 * <p>
 * A new problem will appear each day, and you will have one day to solve it.
 * <p>
 * The problems below are for the April 2020.
 *
 * @author Xiaoyu Zhang
 */

public class Leet30April {
    /**
     * Problem on the day of 04/01/2020: Single Number.
     * <p>
     * Given a non-empty array of integers, every element appears twice except for one. Find that single one.
     * <p>
     * Note:
     * <p>
     * Your algorithm should have a linear runtime complexity. Could you implement it without using extra memory?
     *
     * @param nums an array
     * @return the single number
     */
    public int singleNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            if (set.contains(num))
                set.remove(num);
            else
                set.add(num);
        }

        Iterator<Integer> iterator = set.iterator();

        return iterator.next();
    }

    /**
     * Problem on the day of 04/02/2020: Happy Number.
     * <p>
     * Write an algorithm to determine if a number is "happy".
     * <p>
     * A happy number is a number defined by the following process: Starting with any positive integer,
     * replace the number by the sum of the squares of its digits, and repeat the process until the number
     * equals 1 (where it will stay), or it loops endlessly in a cycle which does not include 1. Those
     * numbers for which this process ends in 1 are happy numbers.
     *
     * @param n an positive integer
     * @return true if it's a happy number
     */
    public boolean isHappy(int n) {

        // The idea is to use one hash set to record sum of every digit square of every number occurred.
        // Once the current sum cannot be added to set, return false; once the current sum equals 1, return true;

        Set<Integer> set = new HashSet<>();     // store the sums for each iteration

        int squaredSum, remainder = 0;
        do {
            squaredSum = 0;

            while (n != 0) {
                remainder = n % 10;
                squaredSum += remainder * remainder;
                n = n / 10;

                System.out.printf("TRACE: n = %d, remainder = %d, squaredSum = %d\n", n, remainder, squaredSum);
            }

            if (squaredSum == 1)
                return true;

            n = squaredSum;

        } while (set.add(squaredSum));

        return false;
    }

    // Slight different approach to the problem above
    public boolean isHappy2(int n) {

        // The idea is to use one hash set to record sum of every digit square of every number occurred.
        // Once the current sum cannot be added to set, return false; once the current sum equals 1, return true;

        Set<Integer> set = new HashSet<>();     // store the sums for each iteration

        int squaredSum, remainder;
        while (set.add(n)) {
            squaredSum = 0;

            while (n > 0) {
                remainder = n % 10;
                squaredSum += remainder * remainder;
                n = n / 10;
            }

            if (squaredSum == 1)
                return true;
            else
                n = squaredSum;

        }

        return false;
    }

    /**
     * Problem on the day of 04/03/2020: Maximum Subarray.
     * <p>
     * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest sum and return its sum.
     *
     * <p>
     * This problem was discussed by Jon Bentley (Sep. 1984 Vol. 27 No. 9 Communications of the ACM P885)
     * <p>
     * The paragraph below was copied from his paper (with a little modifications)
     * <p>
     * Algorithm that operates on arrays: it starts at the left end (element A[1]) and scans through to the right end (element A[n]),
     * keeping track of the maximum sum subvector seen so far.
     * <p>
     * The maximum is initially A[0]. Suppose we've solved the problem for A[1 .. i - 1]; how can we extend that to A[1 .. i]?
     * The maximum sum in the first I elements is either the maximum sum in the first i - 1 elements (which we'll call MaxSoFar),
     * or it is that of a subvector that ends in position i (which we'll call MaxEndingHere).
     * <p>
     * MaxEndingHere is either A[i] plus the previous MaxEndingHere, or just A[i], whichever is larger.
     *
     * <p>
     * Follow up:
     * If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach, which is more subtle.
     *
     * @param nums an integer array
     * @return the largest sum of a contiguous subarray
     */
    public int maxSubArray(int[] nums) {
        int maxSoFar = nums[0], maxEndingHere = nums[0];

        for (int i = 1; i < nums.length; ++i) {
            maxEndingHere = Math.max(maxEndingHere + nums[i], nums[i]);
            maxSoFar = Math.max(maxSoFar, maxEndingHere);
        }

        return maxSoFar;
    }

    /**
     * Problem on the day of 04/04/2020: Move Zeros.
     * <p>
     * Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.
     * <p>
     * Note:
     * <p>
     * You must do this in-place without making a copy of the array.
     * Minimize the total number of operations.
     *
     * @param nums an integer array
     */
    public void moveZeroes(int[] nums) {
        int k = -1;     // index position for non-zero to be inserted

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0 && k != -1) {
                if (i > k) {
                    nums[k] = nums[i];
                    k++;
                    nums[i] = 0;
                }
            } else if (nums[i] == 0) {
                if (k == -1)
                    k = i;
                // else k++;
            }
        }
    }

    // Another approach to the above proglem
    // (Space Optimal, Operation Sub-Optimal)
    public void moveZeroes2(int[] nums) {
        int lastNonZeroFoundAt = 0;
        // If the current element is not 0, then we need to
        // append it just after the last found non zero element.
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[lastNonZeroFoundAt] = nums[i];
                lastNonZeroFoundAt++;
            }
        }
        // After we have finished processing new elements,
        // all the non-zero elements are already at beginning of array.
        // We just need to fill remaining array with 0's.
        for (int i = lastNonZeroFoundAt; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    // Improvement on the solution above
    void moveZeroes3(int[] nums) {
        // The code will maintain the following invariant:
        //    1. All elements before the slow pointer (lastNonZeroFoundAt) are non-zeroes.
        //    2. All elements between the current and slow pointer are zeroes.
        // Therefore, when we encounter a non-zero element, we need to swap elements pointed by current and slow pointer,
        // then advance both pointers. If it's zero element, we just advance current pointer.
        //With this invariant in-place, it's easy to see that the algorithm will work.

        for (int lastNonZeroFoundAt = 0, cur = 0; cur < nums.length; cur++) {
            if (nums[cur] != 0) {
                swap(nums, lastNonZeroFoundAt, cur);
                lastNonZeroFoundAt++;
            }
        }
    }

    /**
     * Problem on the day of 04/05/2020: Best Time to Buy and Sell Stock II.
     * <p>
     * Say you have an array for which the ith element is the price of a given stock on day i.
     * <p>
     * Design an algorithm to find the maximum profit. You may complete as many transactions as you like
     * (i.e., buy one and sell one share of the stock multiple times).
     * <p>
     * Note: You may not engage in multiple transactions at the same time (i.e., you must sell the stock before you buy again).
     *
     * @param prices an array of stock prices
     * @return max profit
     */
    public int maxProfit(int[] prices) {
        if (prices.length == 0)
            return 0;

        int profit = 0;

        int j = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] <= prices[i - 1]) {
                profit += (prices[i - 1] - prices[j]);
                j = i;
                // System.out.printf("TRACE: i = %d, j = %d, profit = %d\n", i, j, profit);
            }
        }

        profit += (prices[prices.length - 1] - prices[j]);

        return profit;
    }

    // Improvement on the solution above.
    public int maxProfit2(int[] prices) {
        int maxprofit = 0;
        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > prices[i - 1])
                maxprofit += prices[i] - prices[i - 1];
        }
        return maxprofit;
    }

    /**
     * Problem on the day of 04/06/2020: Group Anagrams.
     * <p>
     * Given an array of strings, group anagrams together.
     * <p>
     * Note:
     * All inputs will be in lowercase.
     * The order of your output does not matter.
     *
     * @param strs an array of strings
     * @return groups of anagrams
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<>();

        for (String s : strs) {
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String sorted = new String(chars);
            if (map.containsKey(sorted)) {
                List<String> list = map.get(sorted);
                list.add(s);
                map.put(sorted, list);
            } else {
                List<String> list = new ArrayList<>();
                list.add(s);
                map.put(sorted, list);
            }
        }

        return new ArrayList<>(map.values());
    }

    // A more compact way of coding on the solution above
    public List<List<String>> groupAnagrams2(String[] strs) {
        if (strs.length == 0) return new ArrayList<>();

        Map<String, List<String>> ans = new HashMap<>();
        for (String s : strs) {
            char[] ca = s.toCharArray();
            Arrays.sort(ca);
            String key = String.valueOf(ca);
            if (!ans.containsKey(key))
                ans.put(key, new ArrayList<>());

            ans.get(key).add(s);
        }

        return new ArrayList<>(ans.values());
    }

    /**
     * Problem on 04/07/2020: Counting Elements.
     * <p>
     * Given an integer array arr, count element x such that x + 1 is also in arr.
     * <p>
     * If there're duplicates in arr, count them seperately.
     *
     * @param arr an array of integers
     * @return the count
     */
    public int countElements(int[] arr) {

        // Use hashset to store all elements
        Set<Integer> set = new HashSet<>();

        for (int elem : arr) {
            if (!set.contains(elem))
                set.add(elem);
        }

        int count = 0;

        // Loop again to count all valid elements.
        for (int elem : arr) {
            if (set.contains(elem + 1))
                count++;
        }

        return count;
    }

    /**
     * Problem on 04/08/2020: Middle of the Linked List.
     * <p>
     * Given a non-empty, singly linked list with head node head, return a middle node of linked list.
     * <p>
     * If there are two middle nodes, return the second middle node.
     * <p>
     * Note:
     * The number of nodes in the given list will be between 1 and 100.
     *
     * @param head head of a linked list
     * @return the middle node
     */
    public ListNode middleNode(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode fast = head;
        ListNode slow = head;

        // fast pointer moves 2 steps forward every time, until it reaches the end of the linked list.
        // slow pointer moves 1 step forward every time.
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;

            if (fast.next != null)
                fast = fast.next;
        }

        // the node slow pointer points to is the middle node.
        return slow;
    }

    // A more compact way to rewrite the above solution
    public ListNode middleNode2(ListNode head) {
        ListNode slow = head, fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }

    /**
     * Problem on 04/09/2020: Backspace String Compare.
     * <p>
     * Given two strings S and T, return if they are equal when both are typed into empty text editors. # means a backspace character.
     * <p>
     * Note:
     * <p>
     * 1 <= S.length <= 200
     * 1 <= T.length <= 200
     * S and T only contain lowercase letters and '#' characters.
     * <p>
     * Follow up:
     * Can you solve it in O(N) time and O(1) space?
     *
     * @param S one string
     * @param T another string
     * @return true if equal, false otherwise
     */
    public boolean backspaceCompare(String S, String T) {

        Deque<Character> stack1 = new ArrayDeque<>();
        Deque<Character> stack2 = new ArrayDeque<>();

        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if (c == '#') {
                if (!stack1.isEmpty())
                    stack1.pop();
            } else
                stack1.push(c);
        }

        for (int i = 0; i < T.length(); i++) {
            char c = T.charAt(i);
            if (c == '#') {
                if (!stack2.isEmpty())
                    stack2.pop();
            } else
                stack2.push(c);
        }

        while (!stack1.isEmpty() && !stack2.isEmpty()) {
            if (stack1.pop() != stack2.pop())
                return false;
        }

        return stack1.isEmpty() && stack2.isEmpty();
    }

    // A more compact way to rewrite the above solution
    public boolean backspaceCompare2(String S, String T) {
        return build(S).equals(build(T));
    }

    private String build(String S) {
        Stack<Character> ans = new Stack<>();
        for (char c : S.toCharArray()) {
            if (c != '#')
                ans.push(c);
            else if (!ans.isEmpty())
                ans.pop();
        }
        return String.valueOf(ans);
    }

    /**
     * Another approach to the above problem.
     * Intuition:
     * When writing a character, it may or may not be part of the final string depending on how many backspace keystrokes occur in the future.
     * If instead we iterate through the string in reverse, then we will know how many backspace characters we have seen,
     * and therefore whether the result includes our character.
     * <p>
     * Algorithm:
     * Iterate through the string in reverse. If we see a backspace character, the next non-backspace character is skipped.
     * If a character isn't skipped, it is part of the final answer.
     */
    public boolean backspaceCompare3(String S, String T) {
        int i = S.length() - 1, j = T.length() - 1;
        int skipS = 0, skipT = 0;

        while (i >= 0 || j >= 0) { // While there may be chars in build(S) or build (T)

            while (i >= 0) { // Find position of next possible char in build(S)
                if (S.charAt(i) == '#') {
                    skipS++;
                    i--;
                } else if (skipS > 0) {
                    skipS--;
                    i--;
                } else break;
            }

            while (j >= 0) { // Find position of next possible char in build(T)
                if (T.charAt(j) == '#') {
                    skipT++;
                    j--;
                } else if (skipT > 0) {
                    skipT--;
                    j--;
                } else break;
            }

            // If two actual characters are different
            if (i >= 0 && j >= 0 && S.charAt(i) != T.charAt(j))
                return false;

            // If expecting to compare char vs nothing
            if ((i >= 0) != (j >= 0))
                return false;

            i--;
            j--;
        }

        return true;
    }

    /**
     * Problem on 04/10/2020: MinStack.
     */

    /**
     * Problem on 04/11/2020: Diameter of Binary Tree.
     * <p>
     * Given a binary tree, you need to compute the length of the diameter of the tree.
     * The diameter of a binary tree is the length of the longest path between any two nodes in a tree.
     * This path may or may not pass through the root.
     * <p>
     * Note: The length of path between two nodes is represented by the number of edges between them.
     *
     * @param root root of a binary tree
     * @return diameter of the binary tree
     */
    public int diameterOfBinaryTree(TreeNode root) {
        // Iterative method:
        // The idea is to use PostOrder traversal which to ensure the availability of the node until its left and right sub-trees are processed.
        // For this reason, we use peek() method to keep the node on the stack until its left and right sub-trees gets processed.
        // Then for each node, find the maximum-depth of the left and right sub-trees. Using this maximum-depth, we update diameter if required.

        Deque<TreeNode> stack = new ArrayDeque<>();
        Map<TreeNode, Integer> map = new HashMap<>();

        int diameter = 0;

        if (root != null)
            stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();

            if (node.left != null && !map.containsKey(node.left))
                stack.push(node.left);
            else if (node.right != null && !map.containsKey(node.right))
                stack.push(node.right);
            else {
                stack.pop();
                int leftDepth = map.getOrDefault(node.left, 0);
                int rightDepth = map.getOrDefault(node.right, 0);
                map.put(node, 1 + Math.max(leftDepth, rightDepth));
                diameter = Math.max(diameter, leftDepth + rightDepth);
            }
        }

        return diameter;
    }

    // Using recursive to solve the above problem
    private int maxDiameter = 0;

    public int diameterOfBinaryTree2(TreeNode root) {
        maxDepth(root);
        return maxDiameter;
    }

    // We search each node and remember the maximum number of nodes used in some path.
    private int maxDepth(TreeNode root) {
        if (root == null)
            return 0;

        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        maxDiameter = Math.max(maxDiameter, leftDepth + rightDepth);

        return 1 + Math.max(leftDepth, rightDepth);
    }

    /**
     * Problem on 04/12/2020: Last Stone Weight.
     * <p>
     * We have a collection of stones, each stone has a positive integer weight.
     * <p>
     * Each turn, we choose the two heaviest stones and smash them together.  Suppose the stones have weights x and y with x <= y.
     * The result of this smash is:
     * <p>
     * If x == y, both stones are totally destroyed;
     * If x != y, the stone of weight x is totally destroyed, and the stone of weight y has new weight y-x.
     * At the end, there is at most 1 stone left.  Return the weight of this stone (or 0 if there are no stones left.)
     * <p>
     * Note:
     * 1 <= stones.length <= 30
     * 1 <= stones[i] <= 1000
     *
     * @param stones an array of positive integers
     * @return weight of the last stone
     */
    public int lastStoneWeight(int[] stones) {

        PriorityQueue<Integer> pQueue = new PriorityQueue<Integer>(Collections.reverseOrder());

        for (int stone : stones)
            pQueue.add(stone);

        int x = 0, y = 0, z = 0;
        while (pQueue.size() >= 2) {
            x = pQueue.poll();
            y = pQueue.poll();

            z = x - y;

            // System.out.printf("x = %d, y = %d, z = %d\n", x, y, z);

            if (z > 0)
                pQueue.add(z);
        }

        if (!pQueue.isEmpty())
            return pQueue.peek();

        return 0;
    }

    /**
     * A bit more compact version by rewriting the above solution
     * <p>
     * Explanation
     * - Put all elements into a priority queue.
     * - Pop out the two biggest, push back the difference, until there are no more two elements left.
     * <p>
     * Complexity
     * Time:   O(N * logN)
     * Space:  O(N)
     */
    public int lastStoneWeight2(int[] stones) {
        Queue<Integer> maxPq = new PriorityQueue<>(stones.length, Comparator.reverseOrder());

        for (int stone : stones)
            maxPq.add(stone);

        while (maxPq.size() >= 2) {
            int y = maxPq.poll();
            int x = maxPq.poll();

            if (y > x)
                maxPq.add(y - x);
        }

        return maxPq.isEmpty() ? 0 : maxPq.peek();
    }

    /**
     * Problem on 04/13/2020: Contiguous Array.
     * <p>
     * Given a binary array, find the maximum length of a contiguous subarray with equal number of 0 and 1.
     * <p>
     * Note: The length of the given binary array will not exceed 50,000.
     *
     * @param nums a binary array
     * @return maximum length of a contiguous sub-array with equal number of 0 and 1
     */
    public int findMaxLength(int[] nums) {
        // The idea is to change 0 in the original array to -1.
        // Thus, if we find SUM[i, j] == 0 then we know there are even number of -1 and 1 between index i and j.
        // Also put the sum to index mapping to a HashMap to make search faster.

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0)
                nums[i] = -1;
        }

        Map<Integer, Integer> sumToIndex = new HashMap<>();
        sumToIndex.put(0, -1);
        int sum = 0, max = 0;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];

            if (sumToIndex.containsKey(sum)) {
                max = Math.max(max, i - sumToIndex.get(sum));
            } else {
                sumToIndex.put(sum, i);
            }
        }

        return max;
    }

    // More compact code to solve the problem above
    public int findMaxLength2(int[] nums) {
        // Keeping track of the balance (number of ones minus number of zeros)
        // and storing the first index where each balance occurred.

        Map<Integer, Integer> index = new HashMap<>();
        index.put(0, -1);

        int balance = 0, maxlen = 0;

        for (int i = 0; i < nums.length; i++) {
            balance += nums[i] * 2 - 1;
            Integer first = index.putIfAbsent(balance, i);  // use putIfAbsent so I only need one map function call per number.
            if (first != null)
                maxlen = Math.max(maxlen, i - first);
        }

        return maxlen;
    }

    /**
     * Problem on 04/14/2020: Perform String Shifts.
     * <p>
     * You are given a string s containing lowercase English letters, and a matrix shift, where shift[i] = [direction, amount]:
     * <p>
     * direction can be 0 (for left shift) or 1 (for right shift).
     * amount is the amount by which string s is to be shifted.
     * A left shift by 1 means remove the first character of s and append it to the end.
     * Similarly, a right shift by 1 means remove the last character of s and add it to the beginning.
     * Return the final string after all operations.
     *
     * @param s     a string
     * @param shift a shift matrix
     * @return final string after all operations
     */
    public String stringShift(String s, int[][] shift) {

        int left = 0, right = 0;
        for (int[] row : shift) {
            if (row[0] == 0)
                left += row[1];
            else
                right += row[1];
        }

        if (left == right)
            return s;

        int len = s.length();
        int delta = (left - right) % len;

        System.out.printf("TRACE: left = %d, right = %d, delta = %d\n", left, right, delta);

        if (delta > 0)
            return s.substring(delta) + s.substring(0, delta);
        else
            return s.substring(len + delta) + s.substring(0, len + delta);
    }

    /**
     * Problem on 04/15/2020: Product of Array Except Self.
     * <p>
     * Given an array nums of n integers where n > 1,  return an array output such that output[i] is equal to
     * the product of all the elements of nums except nums[i].
     * <p>
     * Constraint: It's guaranteed that the product of the elements of any prefix or suffix of the array
     * (including the whole array) fits in a 32 bit integer.
     * <p>
     * Note: Please solve it without division and in O(n).
     * <p>
     * Follow up:
     * Could you solve it with constant space complexity? (The output array does not count as extra space
     * for the purpose of space complexity analysis.)
     *
     * @param nums an integer array
     * @return an array of product of array except self
     */
    public int[] productExceptSelf(int[] nums) {
        // The length of the input array
        int length = nums.length;

        // The left and right arrays as described in the algorithm
        int[] L = new int[length];
        int[] R = new int[length];

        // Final answer array to be returned
        int[] answer = new int[length];

        // L[i] contains the product of all the elements to the left
        // Note: for the element at index '0', there are no elements to the left,
        // so L[0] would be 1
        L[0] = 1;
        for (int i = 1; i < length; i++) {

            // L[i - 1] already contains the product of elements to the left of 'i - 1'
            // Simply multiplying it with nums[i - 1] would give the product of all
            // elements to the left of index 'i'
            L[i] = nums[i - 1] * L[i - 1];
        }

        // R[i] contains the product of all the elements to the right
        // Note: for the element at index 'length - 1', there are no elements to the right,
        // so the R[length - 1] would be 1
        R[length - 1] = 1;
        for (int i = length - 2; i >= 0; i--) {

            // R[i + 1] already contains the product of elements to the right of 'i + 1'
            // Simply multiplying it with nums[i + 1] would give the product of all
            // elements to the right of index 'i'
            R[i] = nums[i + 1] * R[i + 1];
        }

        // Constructing the answer array
        for (int i = 0; i < length; i++) {
            // For the first element, R[i] would be product except self
            // For the last element of the array, product except self would be L[i]
            // Else, multiple product of all elements to the left and to the right
            answer[i] = L[i] * R[i];
        }

        return answer;
    }

    /**
     * Problem on 04/16/2020: Valid Parenthesis String.
     * <p>
     * Given a string containing only three types of characters: '(', ')' and '*', write a function to check
     * whether this string is valid. We define the validity of a string by these rules:
     * <p>
     * Any left parenthesis '(' must have a corresponding right parenthesis ')'.
     * Any right parenthesis ')' must have a corresponding left parenthesis '('.
     * Left parenthesis '(' must go before the corresponding right parenthesis ')'.
     * '*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string.
     * An empty string is also valid.
     *
     * @param s a string
     * @return true if the string is valid
     */
    public boolean checkValidString(String s) {

        Deque<Integer> leftID = new ArrayDeque<>();
        Deque<Integer> starID = new ArrayDeque<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(')
                leftID.push(i);
            else if (ch == '*')
                starID.push(i);
            else {
                // Once a right bracket comes, pop left bracket stack first if it is not empty.
                // If the left bracket stack is empty, pop the star stack if it is not empty.
                // A false return can be made provided that both stacks are empty.
                if (leftID.isEmpty() && starID.isEmpty()) return false;

                // Greedy algorithm is used here.
                // We always want to use left brackets to balance the right one first as the * symbol is a wild card.
                if (!leftID.isEmpty())
                    leftID.pop();
                else
                    starID.pop();
            }
        }

        // Now attention is paid to the remaining stuff in these two stacks.
        // Note that the left bracket CANNOT appear after the star as there is NO way to balance the bracket.
        // In other words, whenever there is a left bracket index appears after the Last star, a false statement can be made.
        // Otherwise, pop out each from the left bracket and star stack.
        while (!leftID.isEmpty() && !starID.isEmpty()) {
            if (leftID.pop() > starID.pop())
                return false;
        }

        // A correct sequence should have an empty left bracket stack. You don't need to take care of the star stack.
        return leftID.isEmpty();
    }

    /**
     * Another approach to solve the above problem
     * <p>
     * We count the number of ')' we are waiting for,
     * and it's equal to the number of open parenthesis.
     * This number will be in a range and we count it as [cmin, cmax]
     * <p>
     * cmax counts the maximum open parenthesis, which means the maximum number of unbalanced '(' that COULD be paired.
     * cmin counts the minimum open parenthesis, which means the number of unbalanced '(' that MUST be paired.
     * <p>
     * The string is valid for 2 condition:
     * - cmax will never be negative.
     * - cmin is 0 at the end.
     */
    public boolean checkValidString2(String s) {

        int cmin = 0;
        int cmax = 0;

        for (int i = 0; i < s.length(); ++i) {
            char c = s.charAt(i);
            if (c == '(') {
                cmax++;
                cmin++;
            } else if (c == ')') {
                cmax--;
                cmin = Math.max(cmin - 1, 0);
            } else {
                cmax++;
                cmin = Math.max(cmin - 1, 0);
            }

            if (cmax < 0) return false;
        }

        return cmin == 0;
    }

    private int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    /**
     * Problem on 04/17/2020: Number of Islands.
     * <p>
     * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands.
     * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically.
     * You may assume all four edges of the grid are all surrounded by water.
     *
     * @param grid input grid map filled with 0 and 1
     * @return number of islands in this 2D grid map
     */
    public int numIslands(char[][] grid) {
        if (grid.length == 0)
            return 0;

        int m = grid.length;
        int n = grid[0].length;
        int count = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfs(grid, i, j);
                }
            }
        }

        return count;
    }

    /**
     * @param grid input grid map filled with 0 and 1
     * @param i    horizontal position
     * @param j    vertical position
     */
    private void dfs(char[][] grid, int i, int j) {
        int m = grid.length;
        int n = grid[0].length;

        // base case
        if (i < 0 || i >= m || j < 0 || j >= n || grid[i][j] == '0')
            return;

        grid[i][j] = '0';   // Mark the site as visited
        for (int[] dir : directions) {
            int nextX = i + dir[0];
            int nextY = j + dir[1];
            dfs(grid, nextX, nextY);
        }
    }


    // BFS version
    public int numIslands2(char[][] grid) {
        if (grid.length == 0)
            return 0;

        int m = grid.length;
        int n = grid[0].length;

        System.out.printf("m = %d, n = %d \n", m, n);

        // use visited array to avoid changing the values of original grid
        boolean[][] visited = new boolean[m][n];

        Queue<Integer> queue = new LinkedList<Integer>();
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1' && !visited[i][j]) {
                    queue.offer(i * n + j);     // convert 2D coordinates to 1D index
                    visited[i][j] = true;
                    bfs(grid, queue, visited);
                    count++;

                    System.out.printf("pos: (%d, %d), count = %d \n\n", i, j, count);
                }
            }
        }

        return count;
    }

    private void bfs(char[][] grid, Queue<Integer> queue, boolean[][] visited) {
        int m = grid.length;
        int n = grid[0].length;

        while (!queue.isEmpty()) {
            int curr = queue.poll();    // dequeue

            // convert 1D index back to 2D coordinates
            int currX = curr / n;
            int currY = curr - currX * n;

            System.out.printf("curr: %d, curr pos: (%d, %d) \n", curr, currX, currY);

            // iterate through 4 directions
            for (int[] direction : directions) {

                int nextX = currX + direction[0];
                int nextY = currY + direction[1];
                if (nextX < 0 || nextX >= m || nextY < 0 || nextY >= n
                        || visited[nextX][nextY] || grid[nextX][nextY] == '0')
                    continue;

                visited[nextX][nextY] = true;
                queue.offer(nextX * n + nextY);     // enqueue

                System.out.printf("next pos: (%d, %d) \n", nextX, nextY);
            }
        }
    }

    private int[][] directions2 = {{0, 1}, {1, 0}};

    /**
     * Problem on 04/18/2020: Minimum Path Sum.
     * <p>
     * Given a m x n grid filled with non-negative numbers, find a path from top left to bottom right
     * which minimizes the sum of all numbers along its path.
     * <p>
     * Note: You can only move either down or right at any point in time.
     * <p>
     * <p>
     * Algorithm:
     * This is a typical DP problem.
     * Suppose the minimum path sum of arriving at point (i, j) is S[i][j],
     * then the state equation is S[i][j] = min(S[i - 1][j], S[i][j - 1]) + grid[i][j].
     * <p>
     * Well, some boundary conditions need to be handled.
     * The boundary conditions happen on the topmost row (S[i - 1][j] does not exist)
     * and the leftmost column (S[i][j - 1] does not exist).
     * <p>
     * Suppose grid is like [1, 1, 1, 1], then the minimum sum to arrive at each point is simply
     * an accumulation of previous points and the result is [1, 2, 3, 4].
     *
     * @param grid a maxtrix filled with non-negative integer
     * @return the minimum sum connecting the top left corner to the bottom right
     */

    // First version: unoptimized code
    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        int[][] sum = new int[m][n];

        sum[0][0] = grid[0][0];
        // boundary condition on leftmost column
        for (int i = 1; i < m; i++)
            sum[i][0] = sum[i - 1][0] + grid[i][0];

        // boundary condition on topmost row
        for (int j = 1; j < n; j++)
            sum[0][j] = sum[0][j - 1] + grid[0][j];

        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++)
                sum[i][j] = Math.min(sum[i - 1][j], sum[i][j - 1]) + grid[i][j];

        return sum[m - 1][n - 1];
    }

    // Without additional array by reusing the original one
    public int minPathSum2(int[][] grid) {
        int height = grid.length;
        int width = grid[0].length;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == 0 && col == 0)
                    ;
                else if (row == 0)
                    grid[row][col] = grid[row][col] + grid[row][col - 1];
                else if (col == 0)
                    grid[row][col] = grid[row][col] + grid[row - 1][col];
                else
                    grid[row][col] = grid[row][col] + Math.min(grid[row - 1][col], grid[row][col - 1]);
            }
        }

        return grid[height - 1][width - 1];
    }

    // Rewrite the above solution a bit
    public int minPathSum3(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        for (int i = 1; i < n; i++)
            grid[0][i] += grid[0][i - 1];

        for (int i = 1; i < m; i++) {
            grid[i][0] += grid[i - 1][0];

            for (int j = 1; j < n; j++) {
                grid[i][j] += Math.min(grid[i][j - 1], grid[i - 1][j]);
            }
        }

        return grid[m - 1][n - 1];
    }

    // I am trying to use graph dfs to solve it, but it has some bugs
    public int minPathSum4(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        if (m == 1 && n == 1)
            return grid[0][0];

        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == (m - 1) && j == (n - 1))
                    continue;

                map.put(i * n + j, neighbor(i, j, m, n));
            }
        }

        /*
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
        */

        int sum = 0, minSum = Integer.MAX_VALUE;
        Deque<Integer> stack = new ArrayDeque<>();
        int src = 0, dst = (m - 1) * n + n - 1;
        stack.push(src);      // push down the cell on the top left

        sum += grid[0][0];

        while (!stack.isEmpty()) {
            int v = stack.peek();

            // convert 1D index back to 2D coordinates
            int x = v / n;
            int y = v - x * n;

            // sum += grid[x][y];

            List<Integer> neighbors = map.get(v);

            if (neighbors != null & neighbors.size() != 0) {
                int neighbor = neighbors.remove(0);    // or remove?

                int neighborX = neighbor / n;
                int neighborY = neighbor - neighborX * n;
                int value = grid[neighborX][neighborY];
                sum += value;

                if (neighbor == dst) {
                    minSum = Math.min(minSum, sum);

                    System.out.printf(">>> TRACE dst: neighbor = %d, value = %d, sum = %d, minSum = %d\n", neighbor, value, sum, minSum);

                    sum -= value;

                } else {
                    stack.push(neighbor);

                    System.out.printf("TRACE push: neighbor = %d, value = %d, sum = %d\n", neighbor, value, sum);
                }

            } else {
                int popV = stack.pop();

                int popX = popV / n;
                int popY = popV - popX * n;
                int value = grid[popX][popY];

                System.out.printf("TRACE pop: popV = %d, value = %d, sum = %d\n", popV, value, sum);

                sum -= value;
            }
        }

        return minSum;

    }

    private List<Integer> neighbor(int x, int y, int m, int n) {
        List<Integer> neighbors = new LinkedList<>();

        for (int[] direction : directions2) {
            int nextX = x + direction[0];
            int nextY = y + direction[1];
            if (nextX < 0 || nextX >= m || nextY < 0 || nextY >= n)
                continue;

            neighbors.add(nextX * n + nextY);
        }

        return neighbors;
    }

    /**
     * Problem on 04/19/2020: Search in Rotated Sorted Array.
     * <p>
     * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
     * <p>
     * (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
     * <p>
     * You are given a target value to search. If found in the array return its index, otherwise return -1.
     * <p>
     * You may assume no duplicate exists in the array.
     * <p>
     * Your algorithm's runtime complexity must be in the order of O(log n).
     *
     * @param nums   an integer array
     * @param target an integer to search for
     * @return the index of the integer if found, -1 otherwise
     */
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0)
            return -1;

        int lo = 0, hi = nums.length - 1;
        // when we use the condition "left <= right", we do not need to determine if nums[left] == target
        // in outside of loop, because the jumping condition is left > right, we will have the determination
        // condition if(target == nums[mid]) inside of loop
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (target == nums[mid]) {
                return mid;
            }
            // if left part is monotonically increasing, or the pivot point is on the right part
            if (nums[lo] <= nums[mid]) {
                // 2,3,4,5,6,7,0,1
                // must use "<=" at here since we need to make sure target is in the left part,
                // then safely drop the right part
                if (nums[lo] <= target && target < nums[mid]) {
                    hi = mid - 1;
                } else {
                    // right bias
                    lo = mid + 1;
                }
            }

            // if right part is monotonically increasing, or the pivot point is on the left part
            else {
                // 6,7,0,1,2,3,4,5
                // must use "<=" at here since we need to make sure target is in the right part,
                // then safely drop the left part
                if (nums[mid] < target && target <= nums[hi]) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
        }

        return -1;
    }

    /**
     * Problem on 04/20/2020: Construct Binary Search Tree from Preorder Traversal.
     * <p>
     * Return the root node of a binary search tree that matches the given preorder traversal.
     * <p>
     * (Recall that a binary search tree is a binary tree where for every node,
     * any descendant of node.left has a value < node.val, and any descendant of node.right has a value > node.val.
     * Also recall that a preorder traversal displays the value of the node first, then traverses node.left, then traverses node.right.)
     * <p>
     * Note:
     * <p>
     * 1 <= preorder.length <= 100
     * The values of preorder are distinct.
     *
     * @param preorder an integer array
     * @return the root of constructed binary search tree
     */
    public TreeNode bstFromPreorder(int[] preorder) {
        TreeNode root = null;

        for (int value : preorder) {
            root = put(root, value);    // As the input array is preorder, the first array element is the root
        }

        return root;
    }

    private TreeNode put(TreeNode x, int val) {
        if (x == null) return new TreeNode(val);

        int cmp = val - x.val;
        if (cmp < 0) x.left = put(x.left, val);
        else if (cmp > 0) x.right = put(x.right, val);
        else x.val = val;   // It's unnecessary here, because all array elements are distinct.

        return x;
    }

    private interface BinaryMatrix {
        int get(int x, int y);

        List<Integer> dimensions();
    }

    private static class BinMatrix implements BinaryMatrix {
        int[][] matrix;
        int m, n;

        public BinMatrix(int[][] a) {
            m = a.length;
            n = a[0].length;
            matrix = new int[m][n];
            for (int i = 0; i < m; i++)
                for (int j = 0; j < n; j++)
                    matrix[i][j] = a[i][j];
        }

        public int get(int x, int y) {
            return matrix[x][y];
        }

        public List<Integer> dimensions() {
            List<Integer> dimen = new ArrayList<>(2);
            dimen.add(m);
            dimen.add(n);

            return dimen;
        }
    }

    /**
     * Problem on 04/21/2020: Leftmost Column with at Least a One.
     * <p>
     * (This problem is an interactive problem.)
     * <p>
     * A binary matrix means that all elements are 0 or 1. For each individual row of the matrix, this row is sorted in non-decreasing order.
     * <p>
     * Given a row-sorted binary matrix binaryMatrix, return leftmost column index(0-indexed) with at least a 1 in it.
     * If such index doesn't exist, return -1.
     * <p>
     * You can't access the Binary Matrix directly.  You may only access the matrix using a BinaryMatrix interface:
     * <p>
     * BinaryMatrix.get(x, y) returns the element of the matrix at index (x, y) (0-indexed).
     * BinaryMatrix.dimensions() returns a list of 2 elements [n, m], which means the matrix is n * m.
     * Submissions making more than 1000 calls to BinaryMatrix.get will be judged Wrong Answer.
     * Also, any solutions that attempt to circumvent the judge will result in disqualification.
     * <p>
     * For custom testing purposes you're given the binary matrix mat as input in the following four examples.
     * You will not have access the binary matrix directly.
     *
     * @param binaryMatrix an binary matrix
     * @return leftmost column index(0-indexed) with at least a 1 in it
     */
    public int leftMostColumnWithOne(BinaryMatrix binaryMatrix) {
        // For each row do a binary search to find the leftmost one on that row and update the answer.
        List<Integer> dimen = binaryMatrix.dimensions();
        int m = dimen.get(0);
        int n = dimen.get(1);

        int leftCol = Integer.MAX_VALUE;

        for (int i = 0; i < m; i++) {
            int lo = 0, hi = n - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                if (binaryMatrix.get(i, mid) == 1) {
                    hi = mid - 1;
                    leftCol = Math.min(leftCol, mid);
                } else {
                    lo = mid + 1;
                }
            }
        }

        return (leftCol == Integer.MAX_VALUE ? -1 : leftCol);
    }

    /**
     * Swap the elements in an array
     *
     * @param nums   an integer array
     * @param first  index at which array element will be swapped
     * @param second another index at which array element will be swapped with
     */
    private void swap(int[] nums, int first, int second) {
        int temp = nums[first];
        nums[first] = nums[second];
        nums[second] = temp;
    }

    /**
     * Unit tests
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Leet30April leet30April = new Leet30April();

        System.out.println("\n>>> Problem on the day of 04/01/2020: Single Number.");
        int[] p1_nums = {4, 1, 2, 1, 2};
        System.out.printf("The single number in the array is %d\n", leet30April.singleNumber(p1_nums));

        System.out.println("\n>>> Problem on the day of 04/02/2020: Happy Number.");
        int p2_n = 19;
        System.out.printf("Is %d a happy number? %b\n", p2_n, leet30April.isHappy2(p2_n));

        System.out.println("\n>>> Problem on the day of 04/03/2020: Maximum Subarray.");
        int[] p3_nums = {-2, 1, -3, 4, -1, 2, 1, -5, 4};
        System.out.printf("The largest sum of contiguous sub-array is %d\n", leet30April.maxSubArray(p3_nums));

        System.out.println("\n>>> Problem on the day of 04/04/2020: Move Zeros.");
        int[] p4_nums = {0, 1, 0, 3, 12};
        leet30April.moveZeroes2(p4_nums);
        for (int num : p4_nums)
            System.out.printf("%d\t", num);
        System.out.println();

        System.out.println("\n>>> Problem on the day of 04/05/2020: Best Time to Buy and Sell Stock II.");
        int[] p5_nums = {7, 1, 5, 3, 6, 4};
        System.out.printf("Max profit is %d\n", leet30April.maxProfit2(p5_nums));

        System.out.println("\n>>> Problem on the day of 04/06/2020: Group Anagrams.");
        String[] p6_strs = {"eat", "tea", "tan", "ate", "nat", "bat"};

        for (List<String> list : leet30April.groupAnagrams2(p6_strs)) {
            for (String s : list)
                System.out.printf("%s\t", s);

            System.out.println();
        }

        System.out.println("\n>>> Problem on 04/07/2020: Counting Elements.");
        int[] p7_nums = {1, 3, 2, 3, 5, 0};
        System.out.printf("The count of qualifying elements is %d\n", leet30April.countElements(p7_nums));

        System.out.println("\n>>> Problem on 04/08/2020: Middle of the Linked List.");
        ListNode p8_A1 = new ListNode(1);
        ListNode p8_A2 = new ListNode(2);
        ListNode p8_A3 = new ListNode(3);
        ListNode p8_A4 = new ListNode(4);
        ListNode p8_A5 = new ListNode(5);
        ListNode p8_A6 = new ListNode(6);

        p8_A1.next = p8_A2;
        p8_A2.next = p8_A3;
        p8_A3.next = p8_A4;
        p8_A4.next = p8_A5;
        p8_A5.next = p8_A6;

        System.out.printf("The middle node is %d\n", leet30April.middleNode2(p8_A1).val);

        System.out.println("\n>>> Problem on 04/09/2020: Backspace String Compare.");
        // String p9_S = "ab#c", p9_T = "ad#c";
        // String p9_S = "a#c", p9_T = "b";
        String p9_S = "a##c", p9_T = "#a#c";
        // String p9_S = "gtc#uz#", p9_T = "gtcm##uz#";
        System.out.printf("Does string '%s' equal to '%s' ? %b\n", p9_S, p9_T, leet30April.backspaceCompare3(p9_S, p9_T));

        System.out.println("\n>>> Problem on 04/11/2020: Diameter of Binary Tree.");
        TreeNode p11_tn1 = new TreeNode(1);
        TreeNode p11_tn2 = new TreeNode(2);
        TreeNode p11_tn3 = new TreeNode(3);
        TreeNode p11_tn4 = new TreeNode(4);
        TreeNode p11_tn5 = new TreeNode(5);

        p11_tn1.left = p11_tn2;
        p11_tn1.right = p11_tn3;
        p11_tn2.left = p11_tn4;
        p11_tn2.right = p11_tn5;

        System.out.printf("The diameter of this binary tree is %d\n", leet30April.diameterOfBinaryTree2(p11_tn1));

        System.out.println("\n>>> Problem on 04/12/2020: Last Stone Weight.");
        int[] p12_nums = {2, 7, 4, 1, 8, 1};
        System.out.printf("The weight of the last stone is %d\n", leet30April.lastStoneWeight2(p12_nums));

        System.out.println("\n>>> Problem on 04/13/2020: Contiguous Array.");
        // int[] p13_nums = {0, 1};
        int[] p13_nums = {0, 1, 1, 0, 1, 1, 1, 0};
        System.out.printf("The maximum length of a contiguous sub-array with equal number of 0 and 1 is %d\n", leet30April.findMaxLength(p13_nums));

        System.out.println("\n>>> Problem on 04/14/2020: Perform String Shifts.");
        String p14_s = "abc";
        int[][] p14_shift = {{0, 1}, {1, 2}};
        System.out.printf("The shifted string is '%s'\n", leet30April.stringShift(p14_s, p14_shift));

        System.out.println("\n>>> Problem on 04/15/2020: Product of Array Except Self.");
        int[] p15_nums = {1, 2, 3, 4};
        int[] p15_answer = leet30April.productExceptSelf(p15_nums);
        for (int num : p15_answer)
            System.out.printf("%d\t", num);
        System.out.println();

        System.out.println("\n>>> Problem on 04/16/2020: Valid Parenthesis String.");
        String p16_s = "(*))";
        System.out.printf("Is string '%s' a valid parenthesis string? %b\n", p16_s, leet30April.checkValidString2(p16_s));

        System.out.println("\n>>> Problem on 04/17/2020: Number of Islands.");
        char[][] p17_grid = {
                {'1', '1', '0', '0', '0'}, {'1', '1', '0', '0', '0'}, {'0', '0', '1', '0', '0'}, {'0', '0', '0', '1', '1'}
        };

        System.out.printf("Number of islands in the grid is %d\n", leet30April.numIslands2(p17_grid));

        System.out.println("\n>>> Problem on 04/18/2020: Minimum Path Sum.");
        int[][] p18_grid = {{1, 3, 1}, {1, 5, 1}, {4, 2, 1}};
        /*
        int[][] p18_grid = {
                {1, 4, 8, 6, 2, 2, 1, 7},
                {4, 7, 3, 1, 4, 5, 5, 1},
                {8, 8, 2, 1, 1, 8, 0, 1},
                {8, 9, 2, 9, 8, 0, 8, 9},
                {5, 7, 5, 7, 1, 8, 5, 5},
                {7, 0, 9, 4, 5, 6, 5, 6},
                {4, 9, 9, 7, 9, 1, 9, 0}};
        */
        System.out.printf("The minimum path sum is %d\n", leet30April.minPathSum(p18_grid));

        System.out.println("\n>>> Problem on 04/19/2020: Search in Rotated Sorted Array.");
        int[] p19_nums = {4, 5, 6, 7, 0, 1, 2};
        int p19_target = 0;
        System.out.printf("The integer %d in the rotated sorted array is in the index of %d.\n", p19_target, leet30April.search(p19_nums, p19_target));

        System.out.println("\n>>> Problem on 04/20/2020: Construct Binary Search Tree from Preorder Traversal.");
        int[] p20_nums = {8, 5, 1, 7, 10, 12};
        TreeNode p20_root = leet30April.bstFromPreorder(p20_nums);
        LeetTree leetTree = new LeetTree();
        for (List<Integer> list : leetTree.levelOrder(p20_root)) {
            for (int val : list)
                System.out.printf("%d \t", val);
            System.out.println();
        }

        System.out.println("\n>>> Problem on 04/21/2020: leftMostColumnWithOne.");
        int[][] p21_mat = {{0, 0, 0, 1}, {0, 0, 1, 1}, {0, 1, 1, 1}};
        BinMatrix binMatrix = new BinMatrix(p21_mat);
        System.out.printf("The left most column with one is %d\n", leet30April.leftMostColumnWithOne(binMatrix));
    }
}
