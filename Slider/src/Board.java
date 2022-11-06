import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.LinkedList;

public class Board {

    private final int[][] myBoard;
    private final int n;

    private int blankpos = 999;
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
            for (int j = 0; j < n; j++) {
                matrix += (myBoard[i][j] + "\t");
                if (myBoard[i][j] == 0)
                    blankpos = RCtoOneD(i, j);
            }
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

    public boolean isGoal() {
        return (hamming() == 0);
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (!(y instanceof Board)) return false;

        Board b = (Board)y;

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (myBoard[i][j] != b.myBoard[i][j]) return false;
            }
        return true;
    }

    public Iterable<Board> neighbors() {
        LinkedList<Board> boardList = new LinkedList<>();

        int blankrow = OneDtoRow(blankpos);
        int blankCol = OneDtoCol(blankpos);

        //Swap up if we're not in the first row:
        if (blankrow != 0) {
            int newArr[][] = new int[n][n];
            for (int i = 0; i < n; i++)
                newArr[i] = myBoard[i].clone();
            int temp = newArr[blankrow-1][blankCol];
            newArr[blankrow-1][blankCol] = 0;
            newArr[blankrow][blankCol] = temp;
            Board newBoard = new Board(newArr);
            boardList.add(newBoard);
        }

        //Swap down if we're not in the last row
        if (blankrow != (n-1)) {
            int newArr[][] = new int[n][n];
            for (int i = 0; i < n; i++)
                newArr[i] = myBoard[i].clone();
            int temp = newArr[blankrow+1][blankCol];
            newArr[blankrow+1][blankCol] = 0;
            newArr[blankrow][blankCol] = temp;
            Board newBoard = new Board(newArr);
            boardList.add(newBoard);
        }

        //If we're not in the first col

        //If we're not in the list col

        return boardQueue;
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
        int goalRow = OneDtoRow(val - 1);
        int goalCol = OneDtoCol(val - 1);
        int dist = Math.abs(r-goalRow) + Math.abs(c-goalCol);
        return dist;
    }

    private int GoalVal(int r, int c) {
        if ((r+1) == n && (c+1) == n) return 0;
        else return RCtoOneD(r, c)+1;
    }

    private int RCtoOneD (int r, int c) {
        return (r*n)+c;
    }

    private int OneDtoRow(int oneD) {
        return oneD/n;
    }

    private int OneDtoCol(int oneD) {
        return oneD%n;
    }

    public static void main(String[] args) {
        int[][] array = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board myBoard = new Board(array);
        System.out.println(myBoard.toString());

        System.out.println("Hamming value = " + myBoard.hamming() + "\n");
        System.out.println("Manhattan value = " + myBoard.manhattan() + "\n");
    }
}
