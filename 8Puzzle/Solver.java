import edu.princeton.cs.algs4.*;

public class Solver {

    private SearchNode resultNode;
    private boolean solvable;

    private class SearchNode implements Comparable<SearchNode>{
        private final Board board;
        private final int moves;
        private final int priority;
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

            priority = board.manhattan() + moves;

        }


        @Override
        public int compareTo(SearchNode that) {
            if (this.priority < that.priority) {
                return -1;
            }
            if (this.priority == that.priority) {
                return this.board.hamming() < that.board.hamming() ? -1 :
                        this.board.hamming() == that.board.hamming() ? 0 : 1;
            }
            return 1;
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {

        MinPQ<SearchNode> originPQ = new MinPQ<>();
        MinPQ<SearchNode> twinPQ = new MinPQ<>();

        SearchNode currentNode = new SearchNode(initial);
        SearchNode currentTwin = new SearchNode(initial.twin());

        originPQ.insert(currentNode);
        twinPQ.insert(currentTwin);

        while(!currentNode.board.isGoal() && !currentTwin.board.isGoal()){

            currentNode = originPQ.delMin();
            currentTwin = twinPQ.delMin();

            for(Board board : currentNode.board.neighbors()) {
                if(!board.equals(currentNode.board))
                    originPQ.insert(new SearchNode(board, currentNode));
            }

            for(Board board : currentTwin.board.neighbors()) {
                if(!board.equals(currentNode.board))
                    twinPQ.insert(new SearchNode(board, currentTwin));
            }
        }

        if (currentNode.board.isGoal()) {
            resultNode = currentNode;
            solvable = true;
        } else {
            resultNode = currentTwin;
            solvable = false;
        }

    }

    // is the initial board solvable?
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (isSolvable()) {
           return resultNode.moves;
        }
        return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) {
            return null;
        }

        Queue<Board> solution = new Queue<Board>();
        solution.enqueue(resultNode.board);
        while (resultNode.parent != null){
            resultNode = resultNode.parent;
            solution.enqueue(resultNode.board);
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

