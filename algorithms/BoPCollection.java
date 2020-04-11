import edu.princeton.cs.algs4.StdOut;

/******************************************************************************
 *  author:     Rhett Zhang
 *  created:    2020/02/03
 *  Compilation:  javac-algs4 BoPCollection.java
 *  Execution:    java-algs4 BoPCollection
 *  Dependencies: StdOut.java
 *
 *  BoPCollection is a collection of solutions to the small scale problems
 *  in the book of "Beauty of Programming".
 *
 ******************************************************************************/

public class BoPCollection {

    // problem #2.1
    // Count the number of "1" for the binary representation of
    // a unsigned 8-bit integer.
    public static int countBinaryOnes(short num) {
        int count = 0;
        while (num != 0) {
            if (num != 2 * (num >> 1))
                count++;
            num = (short) (num >> 1);
        }

        return count;
    }

    // problem #2.2
    // sub-problem #1
    // count number of 0's at the end of the factorial of N (N!)
    public static int countEndZerosOfFactorial(int N) {

        // how many 10's, 5's, 2's for each number
        int tenCnt = 0, fiveCnt = 0, twoCnt = 0;

        for (int k = N; k >= 2; k--) {

            int i = k;
            while (i >= 1) {
                if (i % 10 == 0) {
                    tenCnt++;
                    i /= 10;

                    // StdOut.println("i = " + i);
                }
                else if (i % 5 == 0) {
                    fiveCnt++;
                    i /= 5;

                    // StdOut.println("i = " + i);
                }
                else if (i % 2 == 0) {
                    twoCnt++;
                    i /= 2;
                    // StdOut.println("i = " + i);
                }
                else break;
            }
        }

        return tenCnt + Math.min(fiveCnt, twoCnt);
    }

    // calcuate n!
    // return long to prevent overflow
    public static long Factorial(int n) {
        if (n == 0) return 1;
        return n * Factorial(n - 1);
    }

    // problem #2.2
    // sub-problem #2
    // find out the position of least significant '1' in binary representation of N!
    public static short getLeastOnePosition(int n) {
        long fact = Factorial(n);
        short pos = 0;

        while (fact >= 0) {

            if ((fact & 0x01) == 1)
                return ++pos;

            pos++;
            fact = fact >> 1;
        }

        return 0;
    }

    // problem #3.1
    // return true if rotated s1 contains s2; false otherwise.
    public static boolean isRotateContain(String s1, String s2) {
        int i;                      // index to iterate through string s1
        int j;                      // index to interate through string s2

        int len1 = s1.length();
        int len2 = s2.length();

        for (i = 0; i < (len1 + len2); i++) {

            // check if s1 contains s2
            if (s1.charAt(i % len1) == s2.charAt(0)) {

                for (j = 0; j < len2; j++) {

                    // break out of the loop, if s1 not contain s2
                    if (s1.charAt((i + j) % len1) != s2.charAt(j))
                        break;
                }

                // s1 contains s2
                if (j == len2)
                    return true;
            }
        }

        return false;
    }

    // problem #3.2
    // find the corrsponding English word for the telephone number
    // note: below implementation from the book is problematic,
    // and I implemented in another separate file PhoneWords.java
    public static void getAllWordsFromPhone(int[] phone) {
        int numDigit = 10;                  // number of digits
        int phoneLength = phone.length;     // length of telephone number

        String[] numToChar = {
                "", "", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ"
        };

        int[] total = new int[numDigit];    // the number of chars for each digit
        for (int i = 0; i < numDigit; i++) {
            total[i] = numToChar[i].length();
            // StdOut.println(total[i]);
        }

        int[] answer = new int[phoneLength];

        while (true) {
            for (int i = 0; i < phoneLength; i++) {
                StdOut.printf("%d: %d, %d :", i, phone[i], answer[i]);
                StdOut.printf("%c \t", numToChar[phone[i]].charAt(answer[i]));
            }

            StdOut.println();

            int k = phoneLength - 1;
            while (k >= 0) {
                if (answer[k] < total[phone[k] - 1]) {
                    answer[k]++;
                    break;
                }
                else {
                    answer[k] = 0;
                    k--;
                }
            }
            if (k < 0)
                break;
        }
    }

    // problem #3.3
    // compute the similarity between two strings
    // note: the detailed implementation is in the file LevenshteinDistance.java

    // problem #3.4
    // delete a node from a linked list without the link to the first node
    public static void deleteRandomNode(Node curr) {
        if (curr == null)
            throw new IllegalArgumentException("Node to be deleted is null!");

        Node next = curr.next;
        if (next != null) {
            curr.next = next.next;
            curr.val = next.val;
        }
    }

    // problem #3.4.1
    // given the pointer to the first node of a linked list, reverse the linked
    // list by only traversing this list once
    public static Node reverseList(Node head) {

        Node prev = null;
        Node curr = head;

        while (curr != null) {
            Node nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }

        return prev;
    }

    // problem #3.4.1
    // recursive approach, which is credited to leetcode
    public static Node reverseList2(Node head) {
        if (head == null || head.next == null)
            return head;
        Node p = reverseList2(head.next);
        head.next.next = head;
        head.next = null;
        return p;
    }

    private static class Node {
        int val;
        Node next;

        public Node() {
            this.val = -1;
            this.next = null;
        }

        public Node(int val) {
            this.val = val;
            this.next = null;
        }

        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    // problem #3.6
    // check whether two linked lists are intersected.
    public static boolean isIntersected(Node head1, Node head2) {
        if (head1 == null || head2 == null)
            return false;

        Node curr = head1.next;
        // traverse through first linked list
        while (curr.next != null)
            curr = curr.next;

        Node tail1 = curr;  // store the end node of first linked list

        curr = head2.next;
        // traverse through second linked list
        while (curr.next != null)
            curr = curr.next;

        return (tail1.equals(curr));    // now curr is the end node of second linked list
    }

    // problem #3.6.1
    // find the intersection node if two linked lists are intersected
    public static Node getIntersectionNode(Node head1, Node head2) {
        if (head1 == null || head2 == null)
            return null;
        Node p1 = head1, p2 = head2;
        while (p1 != p2) {
            if (p1 == null)
                p1 = head2;
            else
                p1 = p1.next;

            if (p2 == null)
                p2 = head1;
            else
                p2 = p2.next;
        }

        return p1;
    }

    /**
     * Unit tests the {@code FileManager} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        // problem #2.1
        short num = 12;
        StdOut.printf("problem #2.1 \n");
        StdOut.printf("there are %d of 1s in %d's binary representation. \n",
                      BoPCollection.countBinaryOnes(num), num);

        // problem #2.2
        // sub-problem #1 #2
        int N = 12;
        long fact = BoPCollection.Factorial(N);
        StdOut.printf("problem #2.2 1 \n");
        StdOut.printf("%d of zeros at end for the factoral of %d \n",
                      BoPCollection.countEndZerosOfFactorial(N), N);
        StdOut.printf("problem #2.2 2 \n");
        StdOut.printf(
                "least significant position of '1' in the binary representation is %d for the factorial of %d \n",
                BoPCollection.getLeastOnePosition(N), N);
        System.out.println(
                N + "! (" + fact + ") 's binary representation is " + Long.toBinaryString(fact)
                        + "\n");

        // problem #3.1
        String s1 = "AABCD";
        String s2 = "CDAA";
        StdOut.printf("problem #3.1 \n");
        StdOut.printf("is %s constains %s: %b \n\n", s1, s2, BoPCollection.isRotateContain(s1, s2));

        //  problem #3.2
        /*
        int[] phone = { 5, 8, 6, 9, 8, 7, 2 };
        StdOut.printf("problem #3.2 \n");
        BoPCollection.getAllWordsFromPhone(phone);
        */

        //  problem #3.4
        StdOut.printf("problem #3.4 \n");

        Node n1 = new Node(1);
        Node n2 = new Node(2);
        Node n3 = new Node(3);
        Node n4 = new Node(4);
        Node n5 = new Node(5);
        Node n6 = new Node(6);
        n1.next = n2;
        n2.next = n3;
        n3.next = n4;
        n4.next = n5;
        n5.next = n6;
        BoPCollection.deleteRandomNode(n4);
        for (Node cur = n1; cur != null; cur = cur.next)
            StdOut.printf("%d \t", cur.val);
        StdOut.println();

        //  problem #3.4.1
        StdOut.printf("problem #3.4.1 \n");
        Node newHead = BoPCollection.reverseList(n1);
        for (Node cur = newHead; cur != null; cur = cur.next)
            StdOut.printf("%d \t", cur.val);
        StdOut.println();

        // problem #3.4.1
        // recursive approach
        StdOut.printf("problem #3.4.1 recursive approach\n");
        Node newHead2 = BoPCollection.reverseList2(newHead);
        for (Node cur = newHead2; cur != null; cur = cur.next)
            StdOut.printf("%d \t", cur.val);
        StdOut.println("\n");

        // problem #3.6
        StdOut.printf("problem #3.6\n");
        Node nn1 = new Node(11);
        Node nn2 = new Node(22);
        nn1.next = nn2;
        nn2.next = n3;

        StdOut.printf("if two linked lists are intersected? %b \n",
                      BoPCollection.isIntersected(n1, nn1));
        StdOut.printf("these two linked lists are intersected at node with value %d \n",
                      BoPCollection.getIntersectionNode(n1, nn1).val);
        StdOut.println("\n");

    }
}
