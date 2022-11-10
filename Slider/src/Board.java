import edu.princeton.cs.algs4.StdRandom;
import java.util.LinkedList;

public class Board {

    private final int[][] myBoard;
    private final int n;
    private int manhattan;
    private int hamming;
    private int blankpos;

    private final int random1;
    private int random2;
    public Board(int[][] tiles) {
        if (tiles == null) {
            throw new NullPointerException();
        }
        n = tiles[0].length;
        myBoard = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int val = tiles[i][j];
                myBoard[i][j] = val;
                if (val == 0)
                    blankpos = RCtoOneD(i, j);
                else {
                    manhattan += manhattanDistance(val, i, j);
                    hamming += hammingDistance(val, i, j);
                }
            }
        }

        random1 = randomBoardNumber();
        do {
            random2 = randomBoardNumber();
        } while (random1 == random2);
    }

    public String toString() {
        String matrix = "";
        matrix += (n + "\n");
        for (int i = 0; i < n; i++, matrix += ("\n")) {
            for (int j = 0; j < n; j++) {
                matrix += (myBoard[i][j] + "\t");
            }
        }
      return matrix;
    }

    public int dimension() { return n;}

    public int hamming() {
        return hamming;
    }

    public int manhattan() {
        return manhattan;
    }

    public boolean isGoal() {
        return (manhattan == 0 && hamming == 0);
    }

    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) {
            return false;
        }

        Board that = (Board)y;

        if (this.dimension() != that.dimension()) {
            return false;
        }

        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                if (this.myBoard[i][j] != that.myBoard[i][j]) return false;
            }
        return true;
    }

    public Iterable<Board> neighbors() {
        LinkedList<Board> boardList = new LinkedList<>();

        int blankrow = OneDtoRow(blankpos);
        int blankCol = OneDtoCol(blankpos);

        //Swap up if we're not in the first row:
        if (blankrow != 0) {
            int[][] newArr = new int[n][n];
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
            int[][] newArr = new int[n][n];
            for (int i = 0; i < n; i++)
                newArr[i] = myBoard[i].clone();
            int temp = newArr[blankrow+1][blankCol];
            newArr[blankrow+1][blankCol] = 0;
            newArr[blankrow][blankCol] = temp;
            Board newBoard = new Board(newArr);
            boardList.add(newBoard);
        }

        //Swap left if we're not in the first col
        if (blankCol != 0) {
            int[][] newArr = new int[n][n];
            for (int i = 0; i < n; i++)
                newArr[i] = myBoard[i].clone();
            int temp = newArr[blankrow][blankCol-1];
            newArr[blankrow][blankCol-1] = 0;
            newArr[blankrow][blankCol] = temp;
            Board newBoard = new Board(newArr);
            boardList.add(newBoard);
        }

        //Swap right if we're not in the list col
        if (blankCol != (n-1)) {
            int[][] newArr = new int[n][n];
            for (int i = 0; i < n; i++)
                newArr[i] = myBoard[i].clone();
            int temp = newArr[blankrow][blankCol+1];
            newArr[blankrow][blankCol+1] = 0;
            newArr[blankrow][blankCol] = temp;
            Board newBoard = new Board(newArr);
            boardList.add(newBoard);
        }

        return boardList;
    }

    public Board twin() {
        int[][] board = BoardCopy(this.myBoard);
        swap(board, random1, random2);
        return new Board(board);
    }

    private int[][] BoardCopy(int[][] matrix) {
        int[][] boardCopy = new int[matrix.length][];
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
        return Math.abs(r-goalRow) + Math.abs(c-goalCol);

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

    private int randomBoardNumber() {
        int random;
        do {
            random = StdRandom.uniformInt(0, n*n);
        } while (myBoard[OneDtoRow(random)][OneDtoCol(random)] == 0);
        return random;
    }

    private void swap(int[][] board, int swapFrom, int swapTo) {
        int temp = board[OneDtoRow(swapTo)][OneDtoCol(swapTo)];
        board[OneDtoRow(swapTo)][OneDtoCol(swapTo)] = board[OneDtoRow(swapFrom)][OneDtoCol(swapFrom)];
        board[OneDtoRow(swapFrom)][OneDtoCol(swapFrom)] = temp;
    }

    public static void main(String[] args) {
        int[][] array = {{8, 1, 3}, {4, 0, 2}, {7, 6, 5}};
        Board myBoard = new Board(array);
        System.out.println(myBoard);

        System.out.println("\nTesting Hamming and Manhattan:");
        System.out.println("Hamming value = " + myBoard.hamming() + "\n");
        System.out.println("Manhattan value = " + myBoard.manhattan() + "\n");

        LinkedList<Board> neighbors = (LinkedList<Board>) myBoard.neighbors();
        System.out.println("\nTesting Neighbors:");
        for (Board neighbor : neighbors)
            System.out.println("\n"+neighbor.toString());

        System.out.println("\nTesting Twin:");
        Board anotherBoard = myBoard.twin();
        System.out.println(anotherBoard.toString());
    }
}
