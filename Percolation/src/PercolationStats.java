import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    final private int testTotal;
    private double[] resultSet;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException("N <= 0 or T <= 0");
        }
        testTotal = T;
        resultSet = new double[testTotal];
        int openedSites = 0;
        for (int exp = 0; exp < testTotal; exp++) {
            Percolation pr = new Percolation(N);
            int openedSite = 0;
            while (!pr.percolates()) {
                int row = StdRandom.uniform(1, N+1);
                int col = StdRandom.uniform(1, N+1);
                pr.open(row, col);
            }
            double fraction = (double) pr.numberOfOpenSites() / (N * N);
            resultSet[exp] = fraction;
        }
    }

    // sample mean of percolation threshold
    public double mean() { return StdStats.mean(resultSet);}

    // sample standard deviation of percolation threshold
    public double stddev() { return StdStats.stddev(resultSet);}

    // low endpoint of 95% confidence interval
    public double confidenceLo()
    {
        return mean() - (1.96 * stddev() / Math.sqrt(testTotal));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi()
    {
        return mean() + (1.96 * stddev() / Math.sqrt(testTotal));
    }

    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats pStats = new PercolationStats(N, T);

        String confidence = pStats.confidenceLo() + ", "
                + pStats.confidenceHi();
        StdOut.println("mean                    = " + pStats.mean());
        StdOut.println("stddev                  = " + pStats.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }

}
