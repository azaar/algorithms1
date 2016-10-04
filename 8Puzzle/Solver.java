/****************************************************************************
 *  Compilation:  javac Solver.java
 *  Execution:    java Solver input.txt
 *  Dependencies: Board.java, algs4.jar
 *
 * Solver for the 8-puzzle problem.
 * This implementation uses the A* search algorithm with Manhattan priority
 * function.
 *
 ****************************************************************************/

/**
 *
 * @author Maxim Butyrin
 *
 */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class Solver {

    private boolean solvable;
    private SearchNode resultNode;
    private Stack<Board> solution;

    /*
     * Private data structure that tracks and store information about priority
     * and number of moves.
     */
    private class SearchNode implements Comparable<SearchNode> {

        private final Board board;
        private final int moves;
        private final int priorityManhattan;
        private final int priorityHamming;
        private final SearchNode parent;

        public SearchNode(Board board) {
            this(board, null);
        }

        public SearchNode(Board board, SearchNode parent) {
            this.board = board;
            this.parent = parent;

            if (parent == null) {
                moves = 0;
            } else {
                moves =  parent.moves + 1;
            }

            priorityManhattan = board.manhattan() + moves;
            priorityHamming = board.hamming() + moves;

        }

        /*
         * Defines SearchNode ordering by Manhattan priority function;
         * ties are solved by Hamming priority function.
         */
        @Override
        public int compareTo(SearchNode that) {
            if (this.priorityManhattan < that.priorityManhattan) {
                return -1;
            }
            if (this.priorityManhattan == that.priorityManhattan) {
                return this.priorityHamming < that.priorityHamming ? -1 :
                        this.priorityHamming == that.priorityHamming ? 0 : 1;
            }
            return 1;
        }
    }


    /**
     * Constructs the Solver and finds a solution to the initial board
     * using the A* algorithm
     *
     * @param initial board for solving
     */
    public Solver(Board initial) {

        solution = new Stack<>();

        MinPQ<SearchNode> originPQ = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();

        SearchNode currentNode = new SearchNode(initial);
        SearchNode currentTwin = new SearchNode(initial.twin());

        originPQ.insert(currentNode);
        twinPQ.insert(currentTwin);
        Board prevBoard;

        while (!currentNode.board.isGoal() && !currentTwin.board.isGoal()) {

            currentNode = originPQ.delMin();
            currentTwin = twinPQ.delMin();

            if (currentNode.parent == null) {
                prevBoard = null;
            } else {
                prevBoard = currentNode.parent.board;
            }

            for (Board board : currentNode.board.neighbors()) {
                if (!board.equals(prevBoard))
                    originPQ.insert(new SearchNode(board, currentNode));
            }

            if (currentTwin.parent == null) {
                prevBoard = null;
            } else {
                prevBoard = currentTwin.parent.board;
            }

            for (Board board : currentTwin.board.neighbors()) {
                if (!board.equals(prevBoard))
                    twinPQ.insert(new SearchNode(board, currentTwin));
            }
        }

        if (currentNode.board.isGoal()) {
            solvable = true;
            resultNode = currentNode;
            solution.push(currentNode.board);
            while (currentNode.parent != null) {
                currentNode = currentNode.parent;
                solution.push(currentNode.board);
            }

        } else {
            solvable = false;
        }


    }

    /**
     * Checks if this board is solvable.
     *
     * @return true  if board is solvable
     *         false otherwise
     */
    public boolean isSolvable() {
        return solvable;
    }

    /**
     * Returns the minimum number of moves to solve the initial board
     * or -1 if board is unsolvable.
     *
     * @return int minimum number of moves to solve the initial board
     */
    public int moves() {
        if (isSolvable()) {
            return resultNode.moves;
        }
        return -1;
    }

    /**
     * Returns the sequence of Boards from the initial board to the solution.
     * Returns null if initial board unsolvable.
     *
     * @return Iterable object to loop through the solution chain
     */
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }
        return solution;
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
