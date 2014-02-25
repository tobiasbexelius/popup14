import java.util.ArrayList;
import java.util.List;

public class Main {

	private static final int GAS_TANK_CAPACITY = 200;

	private static int gasStations;
	private static int distance;
	private static int[][] dp;
	private static List<Integer> distances;
	private static List<Integer> prices;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		distance = io.getInt();
		distances = new ArrayList<Integer>();
		prices = new ArrayList<Integer>();
		while (io.hasMoreTokens()) {
			distances.add(io.getInt());
			prices.add(io.getInt());
		}
		distances.add(distance);
		prices.add(Integer.MAX_VALUE);
		gasStations = distances.size();
		dp = new int[gasStations][GAS_TANK_CAPACITY + 1];
		int minCost = getMinimumCost();
		if (minCost != Integer.MAX_VALUE)
			io.println(minCost);
		else
			io.println("Impossible");
		io.flush();
		io.close();
	}

	private static int getMinimumCost() {
		if (gasStations == 0 || distance == 0)
			return 0;

		for (int j = 0; j <= GAS_TANK_CAPACITY; j++) {
			dp[0][j] = Integer.MAX_VALUE;
		}

		if (GAS_TANK_CAPACITY / 2 - distances.get(0) >= 0)
			dp[0][GAS_TANK_CAPACITY / 2 - distances.get(0)] = 0;
		else
			return Integer.MAX_VALUE;

		for (int i = 1; i < gasStations; i++) {
			for (int j = 0; j <= GAS_TANK_CAPACITY; j++) {
				if (GAS_TANK_CAPACITY - (distances.get(i) - distances.get(i - 1)) < j) {
					dp[i][j] = Integer.MAX_VALUE;
					continue;
				}
				int minCost = Integer.MAX_VALUE;
				for (int k = 0; k <= GAS_TANK_CAPACITY; k++) {
					if (dp[i - 1][k] == Integer.MAX_VALUE)
						continue;
					int cost = prices.get(i - 1) * (distances.get(i) - distances.get(i - 1) + j - k);
					if (cost + dp[i - 1][k] < minCost && cost >= 0)
						minCost = cost + dp[i - 1][k];
				}
				dp[i][j] = minCost;
			}
		}

		return dp[gasStations - 1][GAS_TANK_CAPACITY / 2];
	}
}