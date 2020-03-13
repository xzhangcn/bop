import java.util.PriorityQueue;

/**
 * created:    2020/03/13
 * <p>
 * The {@code KthLargest} is a class to find the kth largest element in a stream. Note that it is
 * the kth largest element in the sorted order, not the kth distinct element
 * <p>
 * Your KthLargest class will have a constructor which accepts an integer k and an integer array
 * nums, which contains initial elements from the stream. For each call to the method
 * KthLargest.add, return the element representing the kth largest element in the stream.
 *
 * <p>
 * One way to solve this problem is to sort the array. However, you would need to sort the array
 * everytime. Another way is to use a BST that is efficient. However, the most common and popular
 * way is to use a heap (priority queue). Some folks may think about using a max priority queue for
 * this problem since it asks for the kth max. However, that is a wrong choice since the max heap
 * keeps the max value at the top and the kth max may be somewhere at the bottom of the heap. If you
 * keep a min heap of size k then the kth max will stay at the top and very easy to extract.
 * <p>
 * Another benefit of using a min heap is we just need to store k elements out of n given elements
 * from the array. If you are worried about loosing any elements then remember the rejected elements
 * are not in top k so they will never be a candidate since we don't remember any elements.
 *
 * @author Xiaoyu Zhang
 */

public class KthLargest {

    private PriorityQueue<Integer> minHeap = new PriorityQueue<>();
    private int k;

    /**
     * @param k    the kth largest
     * @param nums an initial integer array
     */
    public KthLargest(int k, int[] nums) {
        this.k = k;
        for (Integer num : nums) {
            minHeap.add(num);
            if (minHeap.size() > this.k)
                minHeap.remove();
        }
    }

    /**
     * @param val the value to be added
     * @return the element representing the kth largest element in the stream.
     */
    public int add(int val) {
        minHeap.add(val);
        if (minHeap.size() > this.k)
            minHeap.remove();

        return minHeap.peek();
    }

    public static void main(String[] args) {
        int k = 3;
        int[] nums = { 4, 5, 8, 2 };
        KthLargest kthLargest = new KthLargest(k, nums);

        System.out.println(kthLargest.add(3));
        System.out.println(kthLargest.add(5));
        System.out.println(kthLargest.add(10));
        System.out.println(kthLargest.add(9));
        System.out.println(kthLargest.add(4));
    }
}
