import java.util.Map;
import java.util.HashMap;

/**
 * Design and implement a data structure for Least Recently Used (LRU) cache.
 * It should support the following operations: get and put.
 *
 * get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
 *
 * put(key, value) - Set or insert the value if the key is not already present.
 * When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.
 *
 * The cache is initialized with a positive capacity.
 *
 * Follow up:
 * Could you do both operations in O(1) time complexity?
 *
 * Algorithm:
 * The problem can be solved with a hashtable that keeps track of the keys and its values in the double linked list.
 */
public class LeetLRUCache {

    private int capacity;
    private Map<Integer, Node> map;

    private Node head, tail;            // two pseudo nodes used to mark the boundary, so that no need to check the NULL node during the update.

    public LeetLRUCache(int _capacity) {
        capacity = _capacity;
        map = new HashMap<>();

        head = new Node(0, 0);
        tail = new Node(0, 0);

        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        if(map.containsKey(key)) {
            Node node = map.get(key);

            remove(node);   // remove first
            insert(node);   // then insert into the front

            return node.value;
        } else {
            return -1;
        }
    }

    public void put(int key, int value) {
        if(map.containsKey(key)) {
            remove(map.get(key));       // if key already there, remove first and insert it to the front of the list for LRU
        }

        if(map.size() == capacity) {
            remove(tail.prev);          // remove from the tail, and that node is the least recently used one
        }

        insert(new Node(key, value));   // insert into the front
    }

    private void remove(Node node) {
        map.remove(node.key);           // remove from the HashMap

        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void insert(Node node){
        map.put(node.key, node);        // put into the HashMap

        // insert into the front
        Node headNext = head.next;
        head.next = node;
        node.prev = head;
        headNext.prev = node;
        node.next = headNext;
    }

    // One interesting property about double linked list is that the node can remove itself without other reference.
    // In addition, it takes constant time to add and remove nodes from the head or tail.
    private static class Node{
        Node prev, next;
        int key, value;

        Node(int _key, int _value) {
            key = _key;
            value = _value;
        }
    }

    public static void main(String[] args) {
        LeetLRUCache cache = new LeetLRUCache( 2);

        cache.put(1, 1);
        cache.put(2, 2);
        System.out.printf("%d\n", cache.get(1));       // returns 1

        cache.put(3, 3);                                // evicts key 2

        System.out.printf("%d\n", cache.get(2));       // returns -1 (not found)

        cache.put(4, 4);                                // evicts key 1

        System.out.printf("%d\n", cache.get(1));       // returns -1 (not found)
        System.out.printf("%d\n", cache.get(3));       // returns 3
        System.out.printf("%d\n", cache.get(4));       // returns 4
    }
}
