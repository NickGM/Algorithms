import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

import java.util.LinkedList;

public class Solver {

    private MinPQ<SearchNode> minPQ;
    private MinPQ<SearchNode> twinPQ;
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
    }

    private void solve() {
        boolean solutionFound = false;
        while (!solutionFound) {
            //do each one, if we get to a solution for minPQ then set solution node.
            //if not, leave solution node as null
        }
    }

    public boolean isSolvable() {
        return solution != null;
    }

    public int moves() {

    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;


    }

    private class SearchNode implements Comparable<SearchNode> {

        private int manhattan;
        private int moves;
        private SearchNode prev;

        public SearchNode(Board b, SearchNode previous) {
            if (b == null) throw new IllegalArgumentException("Null constructor argument");
            manhattan = b.manhattan();
            moves = (previous == null) ? 0 : previous.moves+1;
        }

        @Override
        public int compareTo(SearchNode that) {
            return priority() - that.priority();
        }

        private int priority() {
            return manhattan + moves;
        }
    }

    public static void main(String[] args) {

    }
}
