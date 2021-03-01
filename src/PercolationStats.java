import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] P;  //The private instance (or static) variable 'P' can be made 'final';
    // it is initialized only in the declaration or constructor.
    private final double T;
    private final double CONFIDENCE_95 = 1.96;  //The constant '1.96' appears more than once.
    // Define a constant variable (such as 'CONFIDENCE_95') to hold the constant '1.96'.

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
        T = trials;
        P = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);  // should create exactly one Percolation object per trial
            while (!perc.percolates()) {
                int row = StdRandom.uniform(n)+1;
                int col = StdRandom.uniform(n)+1;
                if (!perc.isOpen(row, col)) perc.open(row, col);
            }
            P[i] = (double) perc.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(P);
    }

    public double stddev() {
        return StdStats.stddev(P);
    }

    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev()) / Math.sqrt(T);
    }

    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev()) / Math.sqrt(T);
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        PercolationStats pst = new PercolationStats(n, t);
        StdOut.printf("mean\t\t\t= %f\n", pst.mean());
        StdOut.printf("stddev\t\t\t= %f\n", pst.stddev());
        StdOut.printf("95%% confidence interval\t=[%f, %f]", pst.confidenceLo(), pst.confidenceHi());
    }

}
