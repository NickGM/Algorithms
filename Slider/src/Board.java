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
        for (int i = 0; i < n; i++, matrix += ("\n")) {
            for (int j = 0; j < n; j++)
                matrix += (myBoard[i][j] + "\t");
        }
      return matrix;
    }

    private int[][] BoardCopy(int matrix[][]) {
        int boardCopy[][] = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++)
            boardCopy[i] = matrix[i].clone();
        return boardCopy;
    }

    public static void main(String[] args) {
        int[][] array = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        Board myBoard = new Board(array);
        System.out.println(myBoard.toString());
    }
}
