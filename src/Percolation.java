import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private byte[] status;  // store the status of each node with 3 bits. connect to bottom, connect to top, open.
    private final int N;  // variable 'N' can be made 'final'; it is initialized only in the declaration or constructor.
    private boolean isPercolate = false;
    private int openSites = 0;
    private byte[][] next = {  // right,down,left,up
                            {0, 1},
                            {1, 0},
                            {0, -1},
                            {-1, 0}};
    private WeightedQuickUnionUF uf;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        N = n;
        int sitesNumber = n * n + 1;  // use only one virtual to avoid backwash
        uf = new WeightedQuickUnionUF(sitesNumber);  // 0 is top virtual node
        status = new byte[n * n + 1];  // The API specifies that valid row and column indices are between 1 and n. 0 <--> blocked
    }

    public void open(int row, int col) {
        validate(row, col);
        int id = xyTo1D(row, col);
        if (status[id] != 0) return;
        status[id] = 1; // 1 <--> open
        openSites++;

        if (row == 1) {
            uf.union(id, 0);
            status[id] |= 2;  // 010 <--> connect to the top
        }
        if (row == N) {
            status[id] |= 4;  // 100 <--> connect to the bottom
        }
        byte state = status[id];

        for (int i = 0; i < 4; i++) {
            int nextRow = row + next[i][0];
            int nextCol = col + next[i][1];

            if (nextRow <= 0 || nextRow > N || nextCol <= 0 || nextCol > N) continue;
            if (!isOpen(nextRow, nextCol)) continue;

            int nextId = xyTo1D(nextRow, nextCol);
            int nextRoot = uf.find(nextId);
            uf.union(id, nextId);

            state |= status[nextRoot];

        }
        status[uf.find(id)] = state;
        if (state == 7) isPercolate = true;
    }

    private int xyTo1D(int row, int col) {
        return (row-1) * N + col;
    }

    private void validate(int row, int col) {
        if (row <= 0 || row > N || col <= 0 || col > N) throw new IllegalArgumentException();
    }

    public boolean isOpen(int row, int col) {
        validate(row, col);
        int id = xyTo1D(row, col);

        return status[id] != 0;
    }

    public boolean isFull(int row, int col) {
        validate(row, col);
        int id = xyTo1D(row, col);
        int root = uf.find(id);
        return (status[root] & 2) == 2;
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return isPercolate;
    }
}
