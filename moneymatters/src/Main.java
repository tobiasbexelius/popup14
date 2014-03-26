public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		int n = io.getInt();
		int m = io.getInt();

		int[] debt = new int[n];
		for (int i = 0; i < n; i++)
			debt[i] = io.getInt();

		DisjointSet ds = new DisjointSet(n);

		for (int i = 0; i < m; i++) {
			int friendA = io.getInt();
			int friendB = io.getInt();
			if (ds.find(friendA) == ds.find(friendB))
				continue;
			int collectiveDebt = debt[ds.find(friendA)] + debt[ds.find(friendB)];
			debt[ds.find(friendA)] = 0;
			debt[ds.find(friendB)] = 0;
			ds.union(friendA, friendB);
			debt[ds.find(friendA)] = collectiveDebt;
		}
		boolean possible = true;
		for (int i = 0; i < n; i++)
			if (debt[i] != 0) {
				possible = false;
				break;
			}

		if (possible)
			io.println("POSSIBLE");
		else
			io.println("IMPOSSIBLE");
		io.flush();
		io.close();

	}

}
