/****************************************************************************
 *  Compilation:  javac Subset.java
 *  Execution:    java Subset k
 *  Dependencies: algs4.jar Deque.java RandomizedQueue.java
 *
 *  Implementation of client program that takes a command-line integer k;
 *  reads in a sequence of N strings from standard input using
 *  StdIn.readString(); and prints out exactly k of them, uniformly at random.
 *  Each item from the sequence can be printed out at most once.
 *  0 ≤ k ≤ n, where N is the number of string on standard input.
 *
 ****************************************************************************/

/**
 *
 * @author Maxim Butyrin
 *
 */

import edu.princeton.cs.algs4.StdIn;

public class Subset {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }
        for (int i = 0; i < k; i++) {
            System.out.println(queue.dequeue());
        }
    }
}

