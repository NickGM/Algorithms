import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Stack;

public class Solver {

    private final MinPQ<SearchNode> minPQ;
    private final MinPQ<SearchNode> twinPQ;
    private SearchNode solution;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException("Null constructor argument");

        solution  = null;

        SearchNode root = new SearchNode(initial, null);
        minPQ = new MinPQ<>();
        minPQ.insert(root);

        //Create twin to check for solvable
        SearchNode twin = new SearchNode(initial.twin(), null);
        twinPQ = new MinPQ<>();
        twinPQ.insert(twin);

        solve();
    }

    private void solve() {
        while (true) {
            if (processLevel(minPQ) != null)
                break;
            if (processLevel(twinPQ) != null) {
                solution = null;
                break;
            }
        }
    }

    private SearchNode processLevel(MinPQ<SearchNode> pq) {
        SearchNode minNode = pq.delMin();
        if (minNode.isGoal()) {
            solution = minNode;
            return minNode;
        }

        minNode.neighborsFiltered();
        Iterable<Board> neighbors = minNode.neighborsFiltered();
        for (Board neighbor : neighbors) {
            SearchNode node = new SearchNode(neighbor, minNode);
            pq.insert(node);
        }
        return null;
    }

    public boolean isSolvable() {
        return (solution != null);
    }

    public int moves() {
        if (!isSolvable()) {
            return -1;
        }
        return solution.moves;
    }

    public Iterable<Board> solution() {
        if (solution == null) return null;

        Stack<Board> solutionsTrace = new Stack<>();
        SearchNode node = solution;
        while (node != null) {
            Board board = node.board;
            solutionsTrace.push(board);
            node = node.prev;
        }
        return solutionsTrace;
    }

    private class SearchNode implements Comparable<SearchNode> {

        private final int manhattan;
        private final int moves;
        private final SearchNode prev;
        private final Board board;

        public SearchNode(Board b, SearchNode previous) {
            if (b == null) throw new IllegalArgumentException("Null constructor argument");
            manhattan = b.manhattan();
            moves = (previous == null) ? 0 : previous.moves+1;
            board = b;
            prev = previous;
        }

        @Override
        public int compareTo(SearchNode that) {
            return priority() - that.priority();
        }

        private int priority() {
            return manhattan + moves;
        }

        private Iterable<Board> neighborsFiltered() {
            Iterable<Board> neighbors = board.neighbors();
            Queue<Board> neighborsFiltered = new Queue<>();
            if (prev != null) {
                for (Board neighbor : neighbors) {
                    if (neighbor.equals(prev.board)) {
                        continue;
                    }
                    neighborsFiltered.enqueue(neighbor);
                }
                return neighborsFiltered;
            }
            return neighbors;
        }

        public boolean isGoal() {
            return this.board.isGoal();
        }
    }

    public static void main(String[] args) {
// create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

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
