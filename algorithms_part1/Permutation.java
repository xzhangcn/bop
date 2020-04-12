import java.util.Iterator;

/**
 * created:    2018/03/22
 * <p>
 * {@code Permutation} is the client program for {@code RandomizedQueue}.
 * It takes an integer k as a command-line argument; reads in a sequence of strings from standard input using StdIn.readString();
 * and prints exactly k of them, uniformly at random. Print each item from the sequence at most once.
 *
 * @author Xiaoyu Zhang
 */

public class Permutation {

    public static void main(String[] args) {

        if (args.length < 1) {
            StdOut.println("Please provide the number of items to be printed out");
            return;
        }

        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }

        Iterator<String> i = queue.iterator();
        while (i.hasNext() && k > 0) {
            String s = i.next();
            StdOut.println(s);
            k--;
        }
    }
}