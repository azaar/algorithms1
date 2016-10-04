import java.util.Arrays;
import edu.princeton.cs.algs4.Queue;


public class Board {

    private final int [][] blocks;
    private int sizeOfSide;

    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        sizeOfSide = blocks.length;
        this.blocks = copyBlocks(blocks);

    }

    private int[][] copyBlocks(int[][] source) {
        int[][] copy = new int[source.length][];
        for (int i = 0; i < source.length; i++) {
            int[] xs = source[i];
            int xsLength = xs.length;
            copy[i] = new int[xsLength];
            System.arraycopy(xs, 0, copy[i], 0, xsLength);
        }
        return copy;
    }

    // board dimension n
    public int dimension() {
        return sizeOfSide;
    }

    // number of blocks out of place
    public int hamming() {
        int hamming = 0;

        // x-dimension, traversing cols
        for (int x = 0; x < sizeOfSide; x++) {

            // y-dimension, traversing rows
            for (int y = 0; y < sizeOfSide; y++) {

                int block = blocks[x][y];

                // expected "correct block"
                int correctBlock = x * blocks.length + y + 1;

                if (block != correctBlock ) {
                    hamming++;
                }
            }
        }
        // we don't compute Hamming distance for an empty block (0)
        return hamming - 1;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int manhattan = 0;

        // x-dimension, traversing cols
        for (int x = 0; x < sizeOfSide; x++)

            // y-dimension, traversing rows
            for (int y = 0; y < sizeOfSide; y++) {

                int block = blocks[x][y];

                // we don't compute Manhattan distance for an empty block (0)
                if (block != 0) {

                    // expected x-coordinate (col) of the block
                    int targetX = (block - 1) % sizeOfSide;

                    // expected y-coordinate (row) of the block
                    int targetY = (block - 1) / sizeOfSide;

                    // x-distance to expected coordinate
                    int dx = x - targetX;

                    // y-distance to expected coordinate
                    int dy = y - targetY;

                    manhattan += Math.abs(dx) + Math.abs(dy);
                }
            }
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] copy = copyBlocks(this.blocks);

        for (int i = 0; i < sizeOfSide; i++) {
            for (int j = 0; j < sizeOfSide - 1; j++) {
                // finds not empty adjacent blocks
                if (copy[i][j] > 0 && copy[i][j + 1] > 0) {

                    int block = copy[i][j];
                    // swap
                    copy[i][j] = copy[i][j + 1];
                    copy[i][j + 1] = block;
                    break;
                }
            }
        }
        return new Board(copy);
    }

    // does this board equal y?
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

        if (!Arrays.deepEquals(this.blocks, that.blocks)) {
            return false;
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int x = 0;
        int y = 0;

        // searching for blank block position
        for (int i = 0; i < sizeOfSide; i++) {
            for (int j = 0; j < sizeOfSide; j++) {
                if (blocks[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }

        Queue<Board> neighbors = new Queue<>();

        // enqueue left neighbor if it exists
        if (x > 0) {
            int[][] neighborBlocks = copyBlocks(blocks);
            neighborBlocks[x][y] = neighborBlocks[x - 1][y];
            neighborBlocks[x - 1][y] = 0;
            neighbors.enqueue(new Board(neighborBlocks));
        }

        // enqueue right neighbor if it exists
        if (x < sizeOfSide - 1) {
            int[][] neighborBlocks = copyBlocks(blocks);
            neighborBlocks[x][y] = neighborBlocks[x + 1][y];
            neighborBlocks[x + 1][y] = 0;
            neighbors.enqueue(new Board(neighborBlocks));
        }

        // enqueue top neighbor if it exists
        if (y > 0) {
            int[][] neighborBlocks = copyBlocks(blocks);
            neighborBlocks[x][y] = neighborBlocks[x][y - 1];
            neighborBlocks[x][y - 1] = 0;
            neighbors.enqueue(new Board(neighborBlocks));
        }

        // enqueue bottom neighbor if it exists
        if (y < sizeOfSide - 1) {
            int[][] neighborBlocks = copyBlocks(blocks);
            neighborBlocks[x][y] = neighborBlocks[x][y + 1];
            neighborBlocks[x][y + 1] = 0;
            neighbors.enqueue(new Board(neighborBlocks));
        }

        return neighbors;
    }

    // string representation of this board (in the output format specified below)
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        
        s.append(sizeOfSide + "\n");
        for (int i = 0; i < sizeOfSide; i++) {
            for (int j = 0; j < sizeOfSide; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
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

