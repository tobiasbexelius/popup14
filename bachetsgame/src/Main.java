public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			int n = io.getInt();
			boolean[] dp = new boolean[n + 1];
			int m = io.getInt();
			int[] moves = new int[m];
			for (int i = 0; i < m; i++)
				moves[i] = io.getInt();

			dp[0] = false;
			for (int i = 1; i < n + 1; i++) {
				dp[i] = false;
				for (int j = 0; j < m; j++) {
					if (i >= moves[j] && !dp[i - moves[j]]) {
						dp[i] = true;
						break;
					}
				}
			}
			String res = dp[n] ? "Stan wins" : "Ollie wins";
			io.println(res);
		}
		io.flush();
		io.close();
	}
}
