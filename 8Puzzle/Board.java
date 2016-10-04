/****************************************************************************
 *  Compilation:  javac Board.java
 *  Execution:    none
 *  Dependencies: algs4.jar
 *
 *  Data structure representing the state of a two-dimensional NxN board for
 *  solving 8-puzzle problem.
 *  Each block of the board has an int value, and 0 represents an empty block.
 *
 ****************************************************************************/

/**
 *
 * @author Maxim Butyrin
 *
 */

import java.util.Arrays;
import edu.princeton.cs.algs4.Queue;


public class Board {

    private final int sizeOfSide;   // size of the board side
    private final int[] blocks;     // array containing board blocks

    private int hamming = -1;       // initial value to enforce calculation
    private int manhattan = -1;     // initial value to enforce calculation


    /**
     * Construct a board from an N-by-N array of blocks
     * (where blocks[i][j] = block in row i, column j)
     * for memory optimization given 2d array converts to 1d array.
     *
     * @param blocks 2d int array representing each block of the board
     */
    public Board(int[][] blocks) {
        sizeOfSide = blocks.length;
        this.blocks = copyBlocks(blocks);
    }

    /**
     * Private constructor for creation twins and neighbors of the board.
     *
     * @param blocks      1d int array representing each block of the board
     * @param sizeOfSide  int size of the board side
     */
    private Board(int[] blocks, int sizeOfSide) {
        this.blocks = copyBlocks(blocks);
        this.sizeOfSide = sizeOfSide;
    }

    /*
     * helper for copying arrays
     */
    private int[] copyBlocks(int[][] source) {
        int[] copy = new int[source.length * source.length];
        for (int i = 0; i < source.length; i++) {
            int[] xs = source[i];
            int xsLength = xs.length;
            System.arraycopy(xs, 0, copy, i * source.length, xsLength);
        }
        return copy;
    }

    /*
     * helper for copying arrays
     */
    private int[] copyBlocks(int[] source) {
        int[] copy = new int[source.length];
        System.arraycopy(source, 0, copy, 0, source.length);
        return copy;
    }


    /**
     * Size of the side of this board.
     * @return size of the side of this board
     */
    public int dimension() {
        return sizeOfSide;
    }

    /**
     * Number of blocks in the wrong position.
     * Used for calculating Hamming priority function.
     *
     * @return int number of blocks in the wrong position
     */
    public int hamming() {

        if (hamming >= 0) { return hamming; }

        hamming = 0;

        for (int i = 0; i < blocks.length - 1; i++) {
            if (blocks[i] != i + 1) {
                hamming++;
            }
        }
        return hamming;
    }

    /**
     * Sum of Manhattan distances between blocks of this board and goal board.
     * Used for calculating Manhattan priority function.
     *
     * @return int sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {

        if (manhattan >= 0) { return manhattan; }

        manhattan = 0;

        // x-dimension, traversing cols
        for (int i = 0; i < sizeOfSide; i++)

            // y-dimension, traversing rows
            for (int j = 0; j < sizeOfSide; j++) {

                // we don't compute Manhattan distance for an empty block (0)
                if (blocks[i * sizeOfSide + j] != 0) {

                    int x = (blocks[i * sizeOfSide + j] - 1) / sizeOfSide;
                    int y = (blocks[i * sizeOfSide + j] - 1) - x * sizeOfSide;

                    // x-distance to expected coordinate
                    int dx = x - i;

                    // y-distance to expected coordinate
                    int dy = y - j;

                    manhattan += Math.abs(dx) + Math.abs(dy);
                }
            }
        return manhattan;
    }

    /**
     * Checks if this board is a goal board.
     *
     * @return true   if goal is reached
     *         false  otherwise
     */
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[] copy = copyBlocks(blocks);


        if (copy[0] > 0 && copy[1] > 0) {

            int block = copy[0];
            // swap
            copy[0] = copy[1];
            copy[1] = block;

        } else {
            int block = copy[sizeOfSide];
            // swap
            copy[sizeOfSide] = copy[sizeOfSide + 1];
            copy[sizeOfSide + 1] = block;
        }
        return new Board(copy, sizeOfSide);
    }

    /**
     * Checks if given object is equal to this board
     * @param y the Object (Board) to compare to
     * @return true  if given object is equal to this board
     *         false otherwise
     */
    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null) {
            return false;
        }

        if (this.getClass() != y.getClass()) {
            return false;
        }

        Board that = (Board) y;

        return Arrays.equals(this.blocks, that.blocks);
    }

    /**
     * Generates all neighboring boards.
     * @return Iterable object to loop through all neighbor boards
     */
    public Iterable<Board> neighbors() {
        int x = 0;
        int y = 0;

        // searching for blank block position
        for (int i = 0; i < sizeOfSide; i++) {
            for (int j = 0; j < sizeOfSide; j++) {
                if (blocks[i * sizeOfSide + j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }

        Queue<Board> neighbors = new Queue<>();

        // enqueue left neighbor if it exists
        if (x > 0) {
            int[] neighborBlocks = copyBlocks(blocks);
            neighborBlocks[x * sizeOfSide + y] = neighborBlocks[(x - 1) * sizeOfSide + y];
            neighborBlocks[(x - 1) * sizeOfSide + y] = 0;
            neighbors.enqueue(new Board(neighborBlocks, sizeOfSide));
        }

        // enqueue right neighbor if it exists
        if (x < sizeOfSide - 1) {
            int[] neighborBlocks = copyBlocks(blocks);
            neighborBlocks[x * sizeOfSide + y] = neighborBlocks[(x + 1)* sizeOfSide + y];
            neighborBlocks[(x + 1)* sizeOfSide + y] = 0;
            neighbors.enqueue(new Board(neighborBlocks, sizeOfSide));
        }

        // enqueue top neighbor if it exists
        if (y > 0) {
            int[] neighborBlocks = copyBlocks(blocks);
            neighborBlocks[x * sizeOfSide + y] = neighborBlocks[x * sizeOfSide + (y - 1)];
            neighborBlocks[x * sizeOfSide + (y - 1)] = 0;
            neighbors.enqueue(new Board(neighborBlocks, sizeOfSide));
        }

        // enqueue bottom neighbor if it exists
        if (y < sizeOfSide - 1) {
            int[] neighborBlocks = copyBlocks(blocks);
            neighborBlocks[x * sizeOfSide + y] = neighborBlocks[x * sizeOfSide + (y + 1)];
            neighborBlocks[x * sizeOfSide + (y + 1)] = 0;
            neighbors.enqueue(new Board(neighborBlocks, sizeOfSide));
        }

        return neighbors;
    }

    /**
     * Returns String representation of this board.
     * @return String representation of this board
     */
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        
        s.append(sizeOfSide + "\n");
        for (int i = 0; i < sizeOfSide; i++) {
            for (int j = 0; j < sizeOfSide; j++) {
                s.append(String.format("%2d ", blocks[i * sizeOfSide + j]));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {

    }
}
