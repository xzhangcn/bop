import java.util.*;

/**
 * created:    2019/05/01
 * <p>
 * {@code Leet30May} is the 30-day LeetCoding challenge.
 * <p>
 * A new problem will appear each day, and you will have one day to solve it.
 * <p>
 * The problems below are for the April 2020.
 *
 * @author Xiaoyu Zhang
 */

public class Leet30May {
    /**
     * Problem on the day of 05/01/2020: First Bad Version.
     * <p>
     * You are a product manager and currently leading a team to develop a new product.
     * Unfortunately, the latest version of your product fails the quality check.
     * Since each version is developed based on the previous version, all the versions after a bad version are also bad.
     * <p>
     * Suppose you have n versions [1, 2, ..., n] and you want to find out the first bad one,
     * which causes all the following ones to be bad.
     * <p>
     * You are given an API bool isBadVersion(version) which will return whether version is bad.
     * Implement a function to find the first bad version. You should minimize the number of calls to the API.
     *
     * @param versions list of version numbers
     * @return first bad version number
     */
    public int firstBadVersion(int[] versions) {
        int left = 1;
        int right = versions.length;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (isBadVersion(versions, mid)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    /**
     * @param versions list of version numbers
     * @param num      version number
     * @return true if the verson number is a bad one, false otherwise.
     */
    private boolean isBadVersion(int[] versions, int num) {
        return versions[num] == 0;
    }

    /**
     * Problem on the day of 05/02/2020: Jewels and Stones.
     * <p>
     * You're given strings J representing the types of stones that are jewels, and S representing the stones you have.
     * Each character in S is a type of stone you have.  You want to know how many of the stones you have are also jewels.
     * <p>
     * The letters in J are guaranteed distinct, and all characters in J and S are letters. Letters are case sensitive,
     * so "a" is considered a different type of stone from "A".
     *
     * @param J string of jewels
     * @param S string of stones
     * @return number of jewels
     */
    public int numJewelsInStones(String J, String S) {
        Set<Character> jewels = new HashSet<>();

        // read J and build jewels hash set.
        for (int i = 0; i < J.length(); i++)
            jewels.add(J.charAt(i));

        int count = 0;

        // read S and count jewels.
        for (int i = 0; i < S.length(); i++)
            if (jewels.contains(S.charAt(i)))
                count++;

        return count;
    }

    // A more compact version of above solution
    public int numJewelsInStones2(String J, String S) {
        int res = 0;
        Set<Character> setJ = new HashSet<>();
        for (char j : J.toCharArray())
            setJ.add(j);

        for (char s : S.toCharArray())
            if (setJ.contains(s))
                res++;

        return res;
    }

    /**
     * Problem on the day of 05/03/2020: Ransom Note.
     * <p>
     * Given an arbitrary ransom note string and another string containing letters from all the magazines,
     * write a function that will return true if the ransom note can be constructed from the magazines ; otherwise, it will return false.
     * <p>
     * Each letter in the magazine string can only be used once in your ransom note.
     *
     * @param ransomNote a string
     * @param magazine   another string
     * @return true if the ransom note can be constructed from the magazines
     */
    public boolean canConstruct(String ransomNote, String magazine) {
        Map<Character, Integer> map = new HashMap<>();

        // construct the HashMap
        for (int i = 0; i < magazine.length(); i++) {
            char c = magazine.charAt(i);
            if (map.containsKey(c))
                map.put(c, map.get(c) + 1);
            else
                map.put(c, 1);
        }

        // go through the ransomNote
        for (int i = 0; i < ransomNote.length(); i++) {
            char c = ransomNote.charAt(i);
            if (!map.containsKey(c))
                return false;
            else {
                map.put(c, map.get(c) - 1);
                if (map.get(c) == 0)
                    map.remove(c);
            }
        }

        return true;
    }

    // Another approach to the above problem
    // The code is fast mainly because it uses the ASCII value of the char in the string as the index of an array,
    // resulting in direct access and thus significantly increases efficiency of the algorithm.
    public boolean canConstruct2(String ransomNote, String magazine) {
        int[] table = new int[26];

        for (char c : magazine.toCharArray())
            table[c - 'a']++;

        for (char c : ransomNote.toCharArray())
            if (--table[c - 'a'] < 0)
                return false;

        return true;
    }

    /**
     * Problem on the day of 05/04/2020: Number Complement.
     * <p>
     * Given a positive integer, output its complement number. The complement strategy is to flip the bits of its binary representation.
     *
     * @param num a positive integer
     * @return the complement number
     */
    public int findComplement(int num) {
        int X = 1;
        while (num > X)
            X = X * 2 + 1;

        return X - num;
    }

    /**
     * Problem on the day of 05/05/2020: First Unique Character in a String.
     * <p>
     * Given a string, find the first non-repeating character in it and return it's index. If it doesn't exist, return -1.
     *
     * @param s a string
     * @return the index of first non-repeating character in a string
     */
    public int firstUniqChar(String s) {
        if (s.length() == 0)
            return -1;

        char letter;
        Map<Character, Integer> map = new HashMap<>();

        // build hash map : character and how often it appears
        for (int i = 0; i < s.length(); i++) {
            letter = s.charAt(i);

            if (!map.containsKey(letter))
                map.put(letter, 1);
            else
                map.put(letter, map.get(letter) + 1);
        }

        Set<Character> set = new HashSet<>();
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            letter = entry.getKey();
            if (map.get(letter) == 1)
                set.add(letter);
        }

        // find the index
        for (int i = 0; i < s.length(); i++)
            if (set.contains(s.charAt(i)))
                return i;

        return -1;
    }

    // Similar approach to the one above, but more concise and without using HashSet
    public int firstUniqChar2(String s) {
        HashMap<Character, Integer> count = new HashMap<>();
        int n = s.length();

        // build hash map : character and how often it appears
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            count.put(c, count.getOrDefault(c, 0) + 1);
        }

        // find the index
        for (int i = 0; i < n; i++) {
            if (count.get(s.charAt(i)) == 1)
                return i;
        }

        return -1;
    }

    /**
     * Problem on the day of 05/06/2020: Majority Element.
     * <p>
     * Given an array of size n, find the majority element. The majority element is the element that appears more than ⌊ n/2 ⌋ times.
     * <p>
     * You may assume that the array is non-empty and the majority element always exist in the array.
     *
     * @param nums an array of integers
     * @return the majority element
     */
    public int majorityElement(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        for (int num : nums) {
            counts.put(num, counts.getOrDefault(num, 0) + 1);
        }

        Map.Entry<Integer, Integer> majorityEntry = null;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            if (majorityEntry == null || entry.getValue() > majorityEntry.getValue())
                majorityEntry = entry;
        }

        return majorityEntry.getKey();
    }

    /**
     * Problem on the day of 05/07/2020: Cousins in Binary Tree.
     * <p>
     * In a binary tree, the root node is at depth 0, and children of each depth k node are at depth k+1.
     * <p>
     * Two nodes of a binary tree are cousins if they have the same depth, but have different parents.
     * <p>
     * We are given the root of a binary tree with unique values, and the values x and y of two different nodes in the tree.
     * <p>
     * Return true if and only if the nodes corresponding to the values x and y are cousins.
     *
     * @param root root of a binary tree
     * @param x    one value
     * @param y    another value
     * @return true if and only if the nodes corresponding to the values x and y are cousins
     */
    public boolean isCousins(TreeNode root, int x, int y) {
        if (root == null)
            return false;

        // run bfs on the binary tree
        java.util.Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {

            int size = queue.size();
            boolean isAexist = false;
            boolean isBexist = false;

            for (int i = 0; i < size; i++) {
                TreeNode cur = queue.poll();
                if (cur.val == x)
                    isAexist = true;
                if (cur.val == y)
                    isBexist = true;

                if (cur.left != null && cur.right != null) {
                    if (cur.left.val == x && cur.right.val == y)
                        return false;

                    if (cur.left.val == y && cur.right.val == x)
                        return false;
                }

                if (cur.left != null)
                    queue.offer(cur.left);

                if (cur.right != null)
                    queue.offer(cur.right);
            }

            if (isAexist && isBexist)
                return true;
        }

        return false;
    }

    /**
     * Problem on the day of 05/08/2020: Check If It Is a Straight Line.
     * <p>
     * You are given an array coordinates, coordinates[i] = [x, y], where [x, y] represents the coordinate of a point.
     * Check if these points make a straight line in the XY plane.
     *
     * @param coordinates an array of coordinates
     * @return true if all coordinates are in a straight line
     */
    public boolean checkStraightLine(int[][] coordinates) {
        if (coordinates == null || coordinates.length < 3 || coordinates[0].length == 0)
            return false;

        int[] p = coordinates[0];
        int[] q = coordinates[1];

        for (int i = 2; i < coordinates.length; i++) {
            int[] curr = coordinates[i];

            if ((curr[0] - p[0]) * (q[1] - p[1]) != (curr[1] - p[1]) * (q[0] - p[0]))
                return false;
        }

        return true;
    }

    /**
     * Problem on the day of 05/09/2020: Valid Perfect Square.
     * <p>
     * Given a positive integer num, write a function which returns True if num is a perfect square else False.
     * <p>
     * Note: Do not use any built-in library function such as sqrt.
     *
     * @param num a positive integer
     * @return true if num is a perfect square else false
     */
    public boolean isPerfectSquare(int num) {
        int i = 1;
        while (num > 0) {
            num -= i;
            i += 2;
        }
        return num == 0;
    }

    // Use binary search
    public boolean isPerfectSquare2(int num) {
        if (num <= 0)
            return false;

        int lo = 1, hi = num / 2;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;

            if (num / mid > mid)
                lo = mid + 1;
            else
                hi = mid;
        }

        return lo * lo == num;
    }

    /**
     * Problem on the day of 05/10/2020: Find the Town Judge.
     * <p>
     * In a town, there are N people labelled from 1 to N.  There is a rumor that one of these people is secretly the town judge.
     * <p>
     * If the town judge exists, then:
     * <p>
     * The town judge trusts nobody.
     * Everybody (except for the town judge) trusts the town judge.
     * There is exactly one person that satisfies properties 1 and 2.
     * You are given trust, an array of pairs trust[i] = [a, b] representing that the person labelled a trusts the person labelled b.
     * <p>
     * If the town judge exists and can be identified, return the label of the town judge.  Otherwise, return -1.
     * <p>
     * Intuition:
     * Consider trust as a graph, all pairs are directed edge.
     * The point who has no out-degree and in-degree == N - 1 is the judge.
     * <p>
     * Explanation:
     * Count the degree, and check at the end.
     *
     * @param N     number of people
     * @param trust an array of trust pair
     * @return the label of the town judge, otherwise return -1.
     */
    public int findJudge(int N, int[][] trust) {
        int[] count = new int[N + 1];

        for (int[] t : trust) {
            count[t[0]]--;
            count[t[1]]++;
        }

        for (int i = 1; i <= N; i++) {
            if (count[i] == N - 1)
                return i;
        }

        return -1;
    }

    /**
     * Problem on the day of 05/11/2020: Flood Fill.
     * <p>
     * An image is represented by a 2-D array of integers, each integer representing the pixel value of the image (from 0 to 65535).
     * <p>
     * Given a coordinate (sr, sc) representing the starting pixel (row and column) of the flood fill,
     * and a pixel value newColor, "flood fill" the image.
     * <p>
     * To perform a "flood fill", consider the starting pixel, plus any pixels connected 4-directionally
     * to the starting pixel of the same color as the starting pixel, plus any pixels connected 4-directionally
     * to those pixels (also with the same color as the starting pixel), and so on. Replace the color of all of
     * the aforementioned pixels with the newColor.
     * <p>
     * At the end, return the modified image.
     *
     * @param image    a 2-D array of integers
     * @param sr       source row
     * @param sc       source col
     * @param newColor new color to flood
     * @return the modified image.
     */
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if (image[sr][sc] == newColor)
            return image;

        int color = image[sr][sc];
        dfs(image, sr, sc, newColor, color);

        return image;
    }

    private void dfs(int[][] image, int sr, int sc, int newColor, int color) {
        // base case
        if (sr < 0 || sr >= image.length || sc < 0 || sc >= image[0].length || image[sr][sc] != color)
            return;

        image[sr][sc] = newColor;
        // Initiate a directions array
        int[][] dirs = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

        for (int[] dir : dirs) {
            int i = dir[0] + sr;
            int j = dir[1] + sc;

            dfs(image, i, j, newColor, color);  // recurse on all neighbors.
        }
    }

    // use bfs solution to the above problem
    public int[][] floodFill2(int[][] image, int sr, int sc, int newColor) {
        if (image[sr][sc] == newColor)
            return image;

        java.util.Queue<int[]> q = new LinkedList<>();
        int color = image[sr][sc];
        boolean[][] visited = new boolean[image.length][image[0].length];

        // put your starting cell in the queue
        q.add(new int[]{sr, sc});
        visited[sr][sc] = true;

        int[][] dirs = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            image[curr[0]][curr[1]] = newColor;

            // iterate on all possible directions
            for (int[] dir : dirs) {
                int i = dir[0] + curr[0];
                int j = dir[1] + curr[1];
                if (i >= 0 && i < image.length && j >= 0 && j < image[0].length && image[i][j] == color && !visited[i][j]) {
                    q.add(new int[]{i, j});
                    visited[i][j] = true;
                }
            }
        }

        return image;
    }

    /**
     * Problem on the day of 05/12/2020: Single Element in a Sorted Array.
     * <p>
     * You are given a sorted array consisting of only integers where every element appears exactly twice,
     * except for one element which appears exactly once. Find this single element that appears only once.
     *
     * @param nums an array of integers
     * @return the single element
     */
    public int singleNonDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            if (set.contains(num))
                set.remove(num);
            else
                set.add(num);
        }

        Iterator iterator = set.iterator();

        return (Integer) iterator.next();
    }

    /**
     * Approach 2 for the problem above
     * Bit manipulation.
     * If we take XOR of zero and some bit, it will return that bit;
     * If we take XOR of two same bits, it will return 0.
     * we can XOR all bits together to find the unique number.
     */
    public int singleNonDuplicate2(int[] nums) {
        int a = 0;
        for (int num : nums)
            a ^= num;

        return a;
    }

    /**
     * Problem on the day of 05/13/2020: Remove K Digits.
     * <p>
     * Given a non-negative integer num represented as a string, remove k digits from the number so that the new number is the smallest possible.
     * <p>
     * Note:
     * The length of num is less than 10002 and will be ≥ k.
     * The given num does not contain any leading zero.
     * <p>
     * Useful resource:
     * https://leetcode.com/problems/sum-of-subarray-minimums/discuss/178876/stack-solution-with-very-detailed-explanation-step-by-step
     *
     * @param num a non-negative integer
     * @param k   number of digits to be removed
     * @return the smallest possible integer
     */
    public String removeKdigits(String num, int k) {
        int len = num.length();

        if (k >= len)
            return "0";

        Deque<Character> stack = new ArrayDeque<>();
        int i = 0;
        while (i < num.length()) {
            // whenever meet a digit which is less than the previous digit, discard the previous one
            while (k > 0 && !stack.isEmpty() && stack.peek() > num.charAt(i)) {
                stack.pop();
                k--;
            }

            stack.push(num.charAt(i));
            i++;
        }

        // 1. in case of 11111 (duplicate)
        // 2. in case of 12345 (ascending order number)
        while (k > 0) {
            stack.pop();
            k--;
        }

        // construct the number from the stack
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty())
            sb.append(stack.pop());

        // remove all the 0 at the tail
        while (sb.length() > 1 && sb.charAt(sb.length() - 1) == '0')
            sb.deleteCharAt(sb.length() - 1);

        return sb.reverse().toString();
    }

    /**
     * Problem on the day of 05/14/2020: Implement Trie (Prefix Tree).
     */

    /**
     * Problem on the day of 05/15/2020: Maximum Sum Circular Subarray.
     * <p>
     * Given a circular array C of integers represented by A, find the maximum possible sum of a non-empty subarray of C.
     * <p>
     * Here, a circular array means the end of the array connects to the beginning of the array.
     * (Formally, C[i] = A[i] when 0 <= i < A.length, and C[i+A.length] = C[i] when i >= 0.)
     * <p>
     * Also, a subarray may only include each element of the fixed buffer A at most once.
     * (Formally, for a subarray C[i], C[i+1], ..., C[j], there does not exist i <= k1, k2 <= j with k1 % A.length = k2 % A.length.)
     *
     * @param A an array of integer
     * @return maximum possible sum of a non-empty subarray of circular array
     */
    public int maxSubarraySumCircular(int[] A) {
        int total = 0, maxSum = -30000, curMax = 0, minSum = 30000, curMin = 0;

        for (int a : A) {
            curMax = Math.max(curMax + a, a);
            maxSum = Math.max(maxSum, curMax);
            curMin = Math.min(curMin + a, a);
            minSum = Math.min(minSum, curMin);
            total += a;
        }
        return maxSum > 0 ? Math.max(maxSum, total - minSum) : maxSum;
    }

    /**
     * Unit tests
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Leet30May leet30May = new Leet30May();

        System.out.println("\n>>> Problem on the day of 05/01/2020: First Bad Version.");
        int[] p1_versions = {1, 1, 1, 1, 1, 1, 0, 0, 0};
        System.out.printf("The first bad version# is %d\n", leet30May.firstBadVersion(p1_versions));

        System.out.println("\n>>> Problem on the day of 05/02/2020: Jewels and Stones.");
        String p2_J = "aA", p2_S = "aAAbbbb";
        System.out.printf("Number of jewels: %d\n", leet30May.numJewelsInStones(p2_J, p2_S));

        System.out.println("\n>>> Problem on the day of 05/03/2020: Ransom Note.");
        String p3_ransomNote = "aa", p3_maganize = "aab";
        System.out.printf("If the ransom note can be constructed from the magazines? %b\n",
                leet30May.canConstruct(p3_ransomNote, p3_maganize));

        System.out.println("\n>>> Problem on the day of 05/04/2020: Number Complement.");
        int p4_num = 5;
        System.out.printf("The complement of %d is %d\n", p4_num, leet30May.findComplement(p4_num));

        System.out.println("\n>>> Problem on the day of 05/05/2020: First Unique Character in a String.");
        String p5_s = "loveleetcode";
        System.out.printf("The index of first unique character in '%s' is %d\n", p5_s, leet30May.firstUniqChar(p5_s));

        System.out.println("\n>>> Problem on the day of 05/06/2020: Majority Element.");
        int[] p6_nums = {2, 2, 1, 1, 1, 2, 2};
        System.out.printf("The majority element is %d\n", leet30May.majorityElement(p6_nums));

        System.out.println("\n>>> Problem on the day of 05/07/2020: Cousins in Binary Tree.");
        TreeNode p7_tn1 = new TreeNode(1);
        TreeNode p7_tn2 = new TreeNode(2);
        TreeNode p7_tn3 = new TreeNode(3);
        TreeNode p7_tn4 = new TreeNode(4);
        TreeNode p7_tn5 = new TreeNode(5);

        p7_tn1.left = p7_tn2;
        p7_tn1.right = p7_tn3;
        p7_tn2.right = p7_tn4;
        p7_tn3.right = p7_tn5;

        int p7_x = 4, p7_y = 5;
        System.out.printf("Are nodes with value %d and %d cousin? %b\n",
                p7_x, p7_y, leet30May.isCousins(p7_tn1, p7_x, p7_y));

        System.out.println("\n>>> Problem on the day of 05/08/2020: Check If It Is a Straight Line.");
        int[][] p8_coordinates = {{1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 7}};
        System.out.printf("All coordinates are on a straight line? %b\n", leet30May.checkStraightLine(p8_coordinates));

        System.out.println("\n>>> Problem on the day of 05/09/2020: Valid Perfect Square.");
        int p9_num = 16;
        System.out.printf("Is %d a perfect square? %b\n", p9_num, leet30May.isPerfectSquare2(p9_num));

        System.out.println("\n>>> Problem on the day of 05/10/2020: Find the Town Judge.");
        int p10_N = 3;
        int[][] p10_trust = {{1, 3}, {2, 3}, {3, 1}};
        System.out.printf("The town judge is %d\n", leet30May.findJudge(p10_N, p10_trust));

        System.out.println("\n>>> Problem on the day of 05/11/2020: Flood Fill.");
        int[][] p11_image = {{1, 1, 1}, {1, 1, 0}, {1, 0, 1}};
        int p11_sr = 1, p11_sc = 1, p11_newColor = 2;
        int[][] p12_newImage = leet30May.floodFill2(p11_image, p11_sr, p11_sc, p11_newColor);
        System.out.println(Arrays.deepToString(p12_newImage));

        System.out.println("\n>>> Problem on the day of 05/12/2020: Single Element in a Sorted Array.");
        int[] p12_nums = {1, 1, 2, 3, 3, 4, 4, 8, 8};
        System.out.printf("The single element in th array is %d\n", leet30May.singleNonDuplicate(p12_nums));

        System.out.println("\n>>> Problem on the day of 05/13/2020: Remove K Digits.");
        String p13_num = "1432219";
        int p13_k = 3;
        System.out.printf("The smallest possible integer after removing %d digits is %s\n",
                p13_k, leet30May.removeKdigits(p13_num, p13_k));

        System.out.println("\n>>> Problem on the day of 05/15/2020: Maximum Sum Circular Subarray.");
        int[] p15_nums = {5, -3, 5};
        System.out.printf("Maximum Sum Circular Subarray is %d\n", leet30May.maxSubarraySumCircular(p15_nums));
    }
}
