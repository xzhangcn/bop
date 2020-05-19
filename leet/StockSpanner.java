/**
 * Problem on the day of 05/19/2020: Online Stock Span.
 *
 * Write a class StockSpanner which collects daily price quotes for some stock, and returns the span
 * of that stock's price for the current day.
 *
 * The span of the stock's price today is defined as the maximum number of consecutive days (starting
 * from today and going backwards) for which the price of the stock was less than or equal to today's price.
 *
 * For example, if the price of a stock over the next 7 days were [100, 80, 60, 70, 60, 75, 85],
 * then the stock spans would be [1, 1, 1, 2, 1, 4, 6].
 */

class StockSpanner {
    private Node head, tail;

    public StockSpanner() {
        tail = new Node(-1);    // dummy tail
        head = tail;
    }

    public int next(int price) {
        Node x = new Node(price);
        x.next = head;
        head = x;

        int count = 0;
        Node p = head;
        while (p.val != -1 && p.val <= price) {
            count++;
            p = p.next;
        }

        return count;
    }

    class Node {
        int val;
        Node next;

        public Node(int val) {
            this.val = val;
            next = null;
        }
    }

    public static void main(String[] args) {
        StockSpanner stockSpanner = new StockSpanner();
        System.out.println(stockSpanner.next(32));
        System.out.println(stockSpanner.next(82));
        System.out.println(stockSpanner.next(73));
        System.out.println(stockSpanner.next(99));
        System.out.println(stockSpanner.next(91));
    }
}