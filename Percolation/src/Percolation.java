import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    final private boolean[][] opened;
    final private int size;
    final private int topIndex = 0;
    final private int btmIndex;
    final private WeightedQuickUnionUF qf;
    private int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n)
    {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0.");
        }
        opened = new boolean [n][n];
        size = n;
        qf = new WeightedQuickUnionUF(size * size + 2);
        btmIndex = size * size + 1;
        openSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col)
    {
        if (row < 1 && row > size) throw new IllegalArgumentException("row is out of bounds.");
        if (col < 1 && col > size) throw new IllegalArgumentException("column is out of bounds.");

        if (isOpen(row, col)) return;
        opened[row - 1][col - 1] = true;

        int currentIndex = qfIndex(row, col);

        if (row == 1) {
            qf.union(topIndex, currentIndex);
        }
        if (row == size) {
            qf.union(btmIndex, currentIndex);
        }

        //Everything but edge cases
        if (row > 1 && isOpen(row - 1, col))
            qf.union(currentIndex, qfIndex(row - 1, col));
        if (row < size && isOpen(row + 1, col))
            qf.union(currentIndex, qfIndex(row + 1, col));
        if (col > 1 && isOpen(row, col - 1))
            qf.union(currentIndex, qfIndex(row, col - 1));
        if (col < size && isOpen(row, col + 1))
            qf.union(currentIndex, qfIndex(row, col + 1));

        openSites++;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col)
    {
        if (row < 1 && row > size) throw new IllegalArgumentException("row is out of bounds.");
        if (col < 1 && col > size) throw new IllegalArgumentException("column is out of bounds.");
        return opened[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)
    {
        if (row < 1 && row > size) throw new IllegalArgumentException("row is out of bounds.");
        if (col < 1 && col > size) throw new IllegalArgumentException("column is out of bounds.");

        return qf.find(qfIndex(row, col)) == qf.find(topIndex);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {return openSites;}

    // does the system percolate?
    public boolean percolates()
    {
        int top = qf.find(topIndex);
        int bot = qf.find(btmIndex);
        return top == bot;
    }

    private int qfIndex(int row, int col) {
        return size*(row-1) + col;
    }

    // test client (optional)
    public static void main(String[] args)
    {
        int n = 20;
        Percolation p = new Percolation(n);
        while (!p.percolates())
        {
            int randomRow = StdRandom.uniform(1, n + 1);
            int randomCol = StdRandom.uniform(1, n + 1);
            p.open(randomRow, randomCol);
        }

        StdOut.println("\nA total of " + p.numberOfOpenSites() + " sites were opened.");
        StdOut.println("Percolation at: " + (double)p.numberOfOpenSites()/(n * n));
    }

}
