public class Budget {

	public static final int R = 0;
	public static final int C = 1;
	public static final int O = 2;
	public static final int V = 3;
	public static final int LRT = 0;
	public static final int EQ = 1;
	public static final int GRT = 2;

	public static final int FROM = 0;
	public static final int TO = 1;
	public static final int WEIGHT = 2;

	private final int[] rowSums;
	private final int[] colSums;
	private final int[][] constraints;

	public Budget(int[] rowSums, int[] colSums, int[][] constraints) {
		this.rowSums = rowSums;
		this.colSums = colSums;
		this.constraints = constraints;

	}

	public int[][] getBudgetMatrix() {
		int rows = rowSums.length;
		int cols = colSums.length;
		int cons = constraints.length;

		int numNodes = 2 + rows + cols;
		int numEdges = rows + cols + 2 * rows * cols;

		int[][] edges = new int[numEdges][3];
		int source = numNodes - 2;
		int sink = numNodes - 1;

		for (int i = 0; i < rows; i++) {
			edges[i][FROM] = source;
			edges[i][TO] = i;
			edges[i][WEIGHT] = rowSums[i];
		}

		for (int i = 0; i < cols; i++) {
			edges[rows + i][FROM] = source;
			edges[rows + i][TO] = i;
			edges[rows + i][WEIGHT] = colSums[i];
		}

		int nextEdge = rows + cols;
		int c = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				edges[nextEdge][FROM] = i;
				edges[nextEdge][TO] = j;
				// edge[nextEdge][WEIGHT]

				if (constraints[i][O] == LRT) {

				} else if (constraints[i][O] == EQ) {

				} else { // GRT

				}
				nextEdge += 2;
			}

		}

		MaxFlow mf = new MaxFlow(numNodes, source, sink, edges);

		return null;
	}
}
