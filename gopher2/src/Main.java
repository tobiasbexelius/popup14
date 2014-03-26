import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		while (io.hasMoreTokens()) {
			int n = io.getInt();
			int m = io.getInt();
			int s = io.getInt();
			int v = io.getInt();
			List<Coordinate> gophers = new ArrayList<>();
			for (int i = 0; i < n; i++) {
				gophers.add(new Coordinate(io.getDouble(), io.getDouble()));
			}

			List<Coordinate> holes = new ArrayList<>();
			for (int i = 0; i < m; i++) {
				holes.add(new Coordinate(io.getDouble(), io.getDouble()));
			}

			List<int[]> edges = new ArrayList<>();

			int source = n + m;
			int sink = n + m + 1;

			// source

			for (int i = 0; i < n; i++) {
				edges.add(new int[] { source, i, 1 });
			}

			// för varje bäver, lägg till kanter till de hål den hinner till
			for (int i = 0; i < n; i++) {
				Coordinate gopher = gophers.get(i);
				for (int j = 0; j < m; j++) {
					Coordinate hole = holes.get(j);
					if (Math.sqrt(Math.pow(Math.abs(gopher.x - hole.x), 2) + Math.pow(Math.abs(gopher.y - hole.y), 2)) <= v * s)
						edges.add(new int[] { i, j + n, 1 });
				}
			}

			// för varje hål, lägg till kan till sink

			for (int i = n; i < n + m; i++) {
				edges.add(new int[] { i, sink, 1 });
			}

			int[][] edgesArray = new int[edges.size()][3];
			for (int i = 0; i < edges.size(); i++) {
				edgesArray[i] = edges.get(i);
			}

			MaxFlow mf = new MaxFlow(n + m + 2, n + m, n + m + 1, edgesArray);
			int safe = mf.getMaxFlow();
			io.println(n - safe);
		}
		io.flush();
		io.close();
	}

	private static class Coordinate {
		public double x;
		public double y;

		public Coordinate(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

}
