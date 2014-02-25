public class Main {

	private static final int NUM_OUTCOMES = 8;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int n = io.getInt();
		for (int i = 0; i < n; i++) {
			int m = io.getInt();
			int[][] preferences = new int[m][NUM_OUTCOMES];
			for (int j = 0; j < m; j++)
				for (int k = 0; k < NUM_OUTCOMES; k++)
					preferences[j][k] = io.getInt();
			io.println(getOutcome(preferences, m));
		}
		io.flush();
		io.close();
	}

	private static String getOutcome(int[][] preferences, int priests) {
		int[][] dp = new int[priests + 1][NUM_OUTCOMES];

		for (int j = 0; j < NUM_OUTCOMES; j++) {
			dp[priests][j] = j;
		}

		for (int i = priests - 1; i >= 0; i--) {
			for (int j = 0; j < NUM_OUTCOMES; j++) {
				int state1 = dp[i + 1][j ^ 1];
				int state2 = dp[i + 1][j ^ 2];
				int state3 = dp[i + 1][j ^ 4];
				int best = preferences[i][state1];
				int bestState = state1;
				if (best > preferences[i][state2]) {
					best = preferences[i][state2];
					bestState = state2;
				}
				if (best > preferences[i][state3]) {
					best = preferences[i][state3];
					bestState = state3;
				}
				dp[i][j] = bestState;
			}
		}
		switch (dp[0][0]) {
		case 0:
			return "NNN";
		case 1:
			return "NNY";
		case 2:
			return "NYN";
		case 3:
			return "NYY";
		case 4:
			return "YNN";
		case 5:
			return "YNY";
		case 6:
			return "YYN";
		case 7:
			return "YYY";
		}

		return "error";
	}
}