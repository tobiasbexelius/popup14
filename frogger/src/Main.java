import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	private static final int[] dx = { 1, -1, 0, 0, 0 };
	private static final int[] dy = { 0, 0, -1, 1, 0 };
	private static final char CAR = 'X';
	private static final char SPACE = 'O';
	private static final char START = 'F';
	private static final char GOAL = 'G';

	private static int n;
	private static int m;
	private static int N;
	private static char[][] map;
	private static Map<Integer, List<Integer>> holes;
	private static int[][] edges;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		io.getInt();
		while (io.hasMoreTokens()) {
			int rounds = io.getInt();
			n = io.getInt();
			m = io.getInt();
			N = n + 2;

			map = new char[N][m];
			int start = -1;
			int goal = -1;

			for (int i = 0; i < N; i++) {
				String line = io.getWord();
				for (int j = 0; j < m; j++) {
					map[i][j] = line.charAt(j);
					if (map[i][j] == START)
						start = i * m + j;
					else if (map[i][j] == GOAL)
						goal = i * m + j;

				}
			}

			fillHoles();

			int numEdges = 0;// = n * m * 4 - (2 * n + 2 * m);
			numEdges += 2 * (4 * m - 2);
			for (int i = 1; i < N - 1; i++) {
				int numHoles = holes.get(i).size();
				numEdges += numHoles * (5 * m - 2);
			}

			edges = new int[numEdges][5];
			int c = 0;
			for (int i = 0; i < N; i++) {
				for (int j = 0; j < m; j++) {
					for (int k = 0; k < dx.length; k++) {
						int y = i + dy[k];
						int x = j + dx[k];
						if (inBounds(y, x)) {
							int u = y * m + x;
							int v = i * m + j;
							if (i == 0 || i == N - 1) {
								edges[c][0] = u;
								edges[c][1] = v;
								edges[c][2] = 0;
								edges[c][3] = 1;
								edges[c][4] = 1;
								c++;
							} else {
								List<Integer> currentHoles = holes.get(i);
								for (int hole : currentHoles) {
									edges[c][0] = u;
									edges[c][1] = v;
									edges[c][2] = (m - hole + j) % m;
									edges[c][3] = m;
									edges[c][4] = 1;
									c++;
								}
							}
						}
					}
				}
			}
			// for (int i = 0; i < edges.length; i++) {
			// System.out.println(Arrays.toString(edges[i]));
			// }

			Dijkstra d = new Dijkstra(N * m, numEdges);
			d.setGraph(N * m, edges, numEdges);
			d.initializeTimedDijkstra(start);
			int minCost = d.findShortestTimePath(goal);
			System.out.println(Arrays.toString(d.getParent()));
			System.out.println("[0, 1, 2, 3, 4, 5, 6,  7,  8, 9, 10, 11]");

			if (minCost > rounds)
				io.println("The problem has no solution.");
			else
				io.println("The minimum number of turns is " + minCost + ".");
		}
		io.flush();
		io.close();
	}

	private static void fillHoles() {
		holes = new HashMap<>();
		for (int i = 1; i < N - 1; i++) {
			List<Integer> currentHoles = new ArrayList<>();
			for (int j = 0; j < m; j++) {
				if (map[i][j] == SPACE) {
					currentHoles.add(j);
				}
			}
			holes.put(i, currentHoles);
		}
	}

	private static boolean inBounds(int y, int x) {
		return y >= 0 && x >= 0 && y < N && x < m;
	}
}
