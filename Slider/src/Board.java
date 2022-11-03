import edu.princeton.cs.algs4.StdOut;

public class Board {

    private final int[][] myBoard;
    private final int n;
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new NullPointerException();
        }

        myBoard = BoardCopy(tiles);
        n = myBoard.length;
    }

    public String toString() {
        String matrix = new String();
        matrix += (n + "\n");
        for (int i = 0; i < n; i++, matrix += ("\n")) {
            for (int j = 0; j < n; j++)
                matrix += (myBoard[i][j] + "\t");
        }
      return matrix;
    }

    public int dimension() { return n;}

    public int hamming() {
        int hammingval = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                hammingval += hammingDistance(myBoard[i][j], i, j);

        return hammingval;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                manhattan += manhattanDistance(myBoard[i][j], i, j);
        return manhattan;
    }

    private int[][] BoardCopy(int matrix[][]) {
        int boardCopy[][] = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++)
            boardCopy[i] = matrix[i].clone();
        return boardCopy;
    }

    private int hammingDistance(int val, int r, int c) {
        if (val == 0) return val;
        int reqVal = GoalVal(r, c);
        return (val == reqVal) ?  0 : 1;
    }
    private int manhattanDistance(int val, int r, int c) {
        if (val == 0) return val;
        int goalRow = (val - 1)/n;
        int goalCol = (val - 1)%n;
        int dist = Math.abs(r-goalRow) + Math.abs(c-goalCol);
        return dist;
    }

    private int GoalVal(int r, int c) {
        if ((r+1) == n && (c+1) == n) return 0;
        else return (r*n)+c+1;
    }

    public static void main(String[] args) {
        int[][] array = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board myBoard = new Board(array);
        System.out.println(myBoard.toString());

        System.out.println("Hamming value = " + myBoard.hamming() + "\n");
        System.out.println("Manhattan value = " + myBoard.manhattan() + "\n");
    }
}
