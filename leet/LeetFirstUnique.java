import java.util.HashMap;
import java.util.Map;

/**
 * You have a queue of integers, you need to retrieve the first unique integer in the queue.
 *
 */

public class LeetFirstUnique {
    private Map<Integer, LeetFirstUnique.Node> map;

    // two pseudo nodes used to mark the boundary, so that no need to check the NULL node during the update.
    private LeetFirstUnique.Node head, tail;

    /**
     * Initializes the object with the numbers in the queue
     * @param nums  an array of integers
     */
    public LeetFirstUnique(int[] nums) {
        map = new HashMap<>();

        head = new LeetFirstUnique.Node(0, 0);
        tail = new LeetFirstUnique.Node(0, 0);

        head.next = tail;
        tail.prev = head;

        for (int num : nums)
            add(num);
    }

    /**
     * @return the value of the first unique integer of the queue, and returns -1 if there is no such integer
     */
    public int showFirstUnique() {
        if(head.next != tail) {
            return head.next.value;
        } else {
            return -1;
        }
    }

    /**
     * insert value to the queue
     * @param value
     */
    public void add(int value) {
        if(map.containsKey(value)) {
            remove(map.get(value));       // if key already there and it's first time non-unique, remove it from the double linked list
        }
        else
            insert(new LeetFirstUnique.Node(value, 1));   // insert into the end
    }

    private void insert(LeetFirstUnique.Node node){
        map.put(node.value, node);        // put into the HashMap

        // insert into the end
        LeetFirstUnique.Node tailPrev = tail.prev;

        tailPrev.next = node;
        node.prev = tailPrev;
        node.next = tail;
        tail.prev = node;
    }

    private void remove(LeetFirstUnique.Node node) {
        // map.remove(node.value);           // don't remove from the HashMap
        if (node.count == 1) {

            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        node.count++;
    }

    // One interesting property about double linked list is that the node can remove itself without other reference.
    // In addition, it takes constant time to add and remove nodes from the head or tail.
    private static class Node{
        LeetFirstUnique.Node prev, next;
        int value, count;

        Node(int _value, int _count) {
            value = _value;
            count = _count;
        }
    }

    public static void main(String[] args) {
        int[] nums = {2,3,5};
        LeetFirstUnique firstUnique = new LeetFirstUnique(nums);

        System.out.println(firstUnique.showFirstUnique()); // return 2

        firstUnique.add(5);            // the queue is now [2,3,5,5]
        System.out.println(firstUnique.showFirstUnique()); // return 2

        firstUnique.add(2);            // the queue is now [2,3,5,5,2]
        System.out.println(firstUnique.showFirstUnique()); // return 3

        firstUnique.add(3);            // the queue is now [2,3,5,5,2,3]
        System.out.println(firstUnique.showFirstUnique()); // return -1

        firstUnique.add(2);            // the queue is now [2,3,5,5,2,3]
        System.out.println(firstUnique.showFirstUnique()); // return -1
    }
}
