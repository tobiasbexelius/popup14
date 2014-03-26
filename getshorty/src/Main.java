import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			int n = io.getInt();
			int m = io.getInt();
			if (n == 0 && m == 0)
				break;
			List<List<Edge>> neighbourList = new ArrayList<>();
			for (int i = 0; i < n; i++)
				neighbourList.add(new ArrayList<Edge>());

			for (int i = 0; i < m; i++) {
				int u = io.getInt();
				int v = io.getInt();
				double f = io.getDouble();

				neighbourList.get(u).add(new Edge(u, v, f));
				neighbourList.get(v).add(new Edge(v, u, f));

			}
			Dijkstra d = new Dijkstra(n, neighbourList);
			d.findShortestPaths(0);
			io.printf("%.4f\n", d.getShortestDistance(n - 1));
		}
		io.flush();
		io.close();
	}
}
