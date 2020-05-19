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

public class StockSpanner2 {
    /**
     * We should have a stack of a pair of (current  price, maximum number of consecutive days)
     * Since we don't have an access to the indices.
    */
    Stack<int[]> s;

    public StockSpanner2() {
        s = new Stack<>();
    }

   /**
    * Let's trace the algorithm together on [100, 80, 60, 70, 60, 75, 85]
    * 1. calling StockSpanner.next(100) should result in first element in our stack will be (100, 1) (s.size() == 1)
    * 2. calling StockSpanner.next(80) should result in second element in our stack will be (80, 1) (s.size() == 2)
    * 3. calling StockSpanner.next(60) should result in third element in our stack will be (60, 1) (s.size() == 3)
    * 4. Now on calling StockSpanner.next(70) we should add span of (60) + 1 {the current day}
    * and remove it from stack (70, 2) (s.size() == 3)
    * 5. Now on calling StockSpanner.next(60) result in fourth element in our stack will be (60, 1) (s.size() == 4)
    * 6. Now on calling StockSpanner.next(75) we should add span of (60) and (70) + 1 {the current day}
    * and remove it from stack : (75, 4) (s.size() == 3)
    * 7. Now on calling StockSpanner.next(85) we should add span of (75) and (80) + 1 {the current day}
    * and remove it from stack : (85, 6) (s.size() == 2)
    */
    public int next(int price) {
        int span = 1;

        while (!s.isEmpty() && price >= s.peek()[0]) { // If the current price is greater than stack peek.
            span += s.peek()[1];
            s.pop();
        }

        s.push(new int[]{price, span});

        return span;
    }

    public static void main(String[] args) {
        StockSpanner2 stockSpanner = new StockSpanner2();

        System.out.println(stockSpanner.next(100));
        System.out.println(stockSpanner.next(80));
        System.out.println(stockSpanner.next(60));
        System.out.println(stockSpanner.next(70));
        System.out.println(stockSpanner.next(60));
        System.out.println(stockSpanner.next(75));
        System.out.println(stockSpanner.next(80));
    }
}
