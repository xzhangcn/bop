import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

/**
 * created:    2020/02/26
 * <p>
 * compilation:  javac LockOpener.java
 * <p>
 * execution:    java LockOpener
 * <p>
 * You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots: '0', '1', '2',
 * '3', '4', '5', '6', '7', '8', '9'. The wheels can rotate freely and wrap around: for example we
 * can turn '9' to be '0', or '0' to be '9'. Each move consists of turning one wheel one slot.
 * <p>
 * The lock initially starts at '0000', a string representing the state of the 4 wheels.
 * <p>
 * You are given a list of deadends dead ends, meaning if the lock displays any of these codes, the
 * wheels of the lock will stop turning and you will be unable to open it.
 * <p>
 * Given a target representing the value of the wheels that will unlock the lock, return the minimum
 * total number of turns required to open the lock, or -1 if it is impossible.
 * <p>
 * Example 1: Input: deadends = ["0201","0101","0102","1212","2002"], target = "0202"; Output: 6.
 * Explanation: A sequence of valid moves would be "0000" -> "1000" -> "1100" -> "1200" -> "1201" ->
 * "1202" -> "0202". Note that a sequence like "0000" -> "0001" -> "0002" -> "0102" -> "0202" would
 * be invalid, because the wheels of the lock become stuck after the display becomes the dead end
 * "0102".
 * <p>
 * Example 2: Input: deadends = ["8888"], target = "0009"; Output: 1. Explanation: We can turn the
 * last wheel in reverse to move from "0000" -> "0009".
 * <p>
 * Example 3: Input: deadends = ["8887","8889","8878","8898","8788","8988","7888","9888"], target =
 * "8888"; Output: -1. Explanation: We can't reach the target without getting stuck.
 * <p>
 * Example 4: Input: deadends = ["0000"], target = "8888"; Output: -1.
 *
 * @author Xiaoyu Zhang
 */

public class LockOpener {

    /**
     * open the lock if the target can be reached from initial state.
     * <p>
     * This problem can be considered as a standard BFS problem from start node "0000" to target
     * node with the following settings:
     * <p>
     * <ol>
     * <li>Each node is a 4-digit string s; </li>
     * <li>Two nodes differing by a single wrapped around adjacent digit are connected nodes in the
     * graph. </li>
     * <li>All deadend nodes can be considered as visited nodes (i.e., no longer visitable) </li>
     * </ol>
     * <p>
     * BFS:
     * starting at "0000", enqueue all possible moves ("1000", "9000", "0100", "0900", ...)
     * except for the ones in deadends or already used. Then increment step count.
     * Repeat while the queue is not empty and return step count once we found the target.
     * <p>
     * Beware that only increment step count after we dequeued all the moves from last move.
     *
     * @param deadEnds a list of deadeds
     * @param target   the value to open the lock
     * @return the minimum total number of turns required to open the lock, or -1 if it is
     * impossible.
     */
    public static int openLock(String[] deadEnds, String start, String target) {

        // map the String array of deadEnds to a hashSet
        Set<String> deadSet = new HashSet<>(Arrays.asList(deadEnds));

        if (deadSet.contains(start))
            return -1;

        Queue<String> queue = new LinkedList<String>(); // used for BFS algorithm
        queue.add(start);

        // used to map each state to its previous state, such that we can trace back if the lock is opened.
        HashMap<String, String> map = new HashMap<>();

        int step = 0;                                   // minimum steps to reach the target state

        Set<String> visited = new HashSet<>();          // a hashSet to track the visited status
        visited.add(start);
        while (!queue.isEmpty()) {
            int size = queue.size();

            // process all queued items at each depth before continuing onto process the next depth
            for (int i = 0; i < size; i++) {
                String curr = queue.poll();

                assert curr != null;

                // return the current depth if the target is found
                if (curr.equals(target)) {

                    // build up the string of unlocking steps and print out those steps
                    Stack<String> stack = getUnlockSteps(start, target, map);
                    showUnlockSteps(stack);

                    return step;
                }

                // process the neighbors
                for (String neighbor : getNeighbors(curr, deadSet)) {
                    if (visited.contains(neighbor))
                        continue;

                    queue.add(neighbor);
                    visited.add(neighbor);

                    // build the mapping from neighbor to its predecessor
                    map.put(neighbor, curr);
                }
            }

            step++;
        }

        return -1;
    }

    /**
     * get the possible next states from current state.
     * <p>
     * neighbors are created by iteratively moving each of the four dials up/down by one move.
     *
     * @param state    current string
     * @param deadEnds a set of deadeds
     * @return a list of possible next states
     */
    public static List<String> getNeighbors(String state, Set<String> deadEnds) {

        List<String> neighbors = new ArrayList<>();
        char[] charArray = state.toCharArray();

        // build up the list of neighbors
        for (int i = 0; i < state.length(); i++) {
            int idx = state.charAt(i) - '0';

            // increase by 1
            // charArray[i] = (char) ((idx + 1) % 10 + '0');
            charArray[i] = Character.forDigit((idx + 1) % 10, 10);
            String next = new String(charArray);
            if (!deadEnds.contains(next))
                neighbors.add(next);

            // decrease by 1
            // charArray[i] = (char) ((idx - 1) % 10 + '0');
            charArray[i] = Character.forDigit((idx + 9) % 10, 10);
            next = new String(charArray);
            if (!deadEnds.contains(next))
                neighbors.add(next);

            // restore the original character
            // charArray[i] = (char) (idx + '0');
            charArray[i] = Character.forDigit(idx, 10);
        }

        /* Testing code
        if (state.equals("0000")) {
            for (String s : neighbors)
                System.out.printf("%s \t", s);

            System.out.println();
        }
        */

        return neighbors;
    }


    /**
     * @param start  initial state
     * @param target end state to unlock
     * @param map    mapping from current state to its previous state
     * @return stack of strings of unlocking steps
     */
    public static Stack<String> getUnlockSteps(String start, String target,
                                               HashMap<String, String> map) {
        Stack<String> stack = new Stack<>();

        String curr = map.get(target);
        stack.push(target);
        stack.push(curr);

        // System.out.printf("map size: %d, curr: %s \n", map.size(), curr);

        while (!curr.equals(start)) {
            curr = map.get(curr);
            stack.push(curr);
        }

        return stack;
    }

    /**
     * @param stack strings of unlocking steps
     */
    public static void showUnlockSteps(Stack<String> stack) {
        int size = stack.size();
        while (size != 1) {
            System.out.printf("%s -> ", stack.pop());
            size--;
        }

        System.out.printf("%s\n", stack.pop());
    }

    /**
     * unit tests
     *
     * @param args
     */
    public static void main(String[] args) {
        String[] deadends1 = { "0201", "0101", "0102", "1212", "2002" };
        String[] deadends2 = { "8887", "8889", "8878", "8898", "8788", "8988", "7888", "9888" };
        String[] deadends3 = { "8888" };
        String start = "0000";
        String target1 = "0202";
        String target2 = "8888";
        String target3 = "0009";

        System.out.printf("%d steps to open the lock1. \n\n",
                          LockOpener.openLock(deadends1, start, target1));

        System.out.printf("%d steps to open the lock2. \n\n",
                          LockOpener.openLock(deadends2, start, target2));
        System.out.printf("%d steps to open the lock3. \n\n",
                          LockOpener.openLock(deadends3, start, target3));
    }
}
