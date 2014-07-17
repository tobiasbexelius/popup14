public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		io.getInt();
		while (io.hasMoreTokens()) {
			int n = io.getInt();
			int m = io.getInt();

			int[] rowSums = new int[m];

			for (int i = 0; i < m; i++)
				rowSums[i] = io.getInt();

			int[] colSums = new int[n];

			for (int i = 0; i < n; i++)
				colSums[i] = io.getInt();

			int k = io.getInt();

			int[][] constraints = new int[k][4];

			for (int i = 0; i < k; i++) {
				constraints[i][Budget.R] = io.getInt();
				constraints[i][Budget.C] = io.getInt();

				char operator = io.getWord().charAt(0);
				switch (operator) {
				case '<':
					constraints[i][Budget.O] = Budget.LRT;
					break;
				case '>':
					constraints[i][Budget.O] = Budget.GRT;
					break;
				case '=':
					constraints[i][Budget.O] = Budget.EQ;
					break;
				}

				constraints[i][Budget.V] = io.getInt();
			}

			int[][] budgetMatrix = new Budget(rowSums, colSums, constraints).getBudgetMatrix();

			if (budgetMatrix == null) {
				io.println("IMPOSSIBLE");
			} else {
				for (int i = 0; i < budgetMatrix.length; i++) {
					for (int j = 0; j < budgetMatrix[i].length; j++) {
						io.print(budgetMatrix[i][j] + " ");
					}
					io.println();
				}
			}

			io.println();

		}
		io.flush();
		io.close();
	}

}
