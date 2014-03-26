import java.util.LinkedList;
import java.util.Queue;

public class Main {

	private static final int[] dx = { 1, -1, 0, 0 };
	private static final int[] dy = { 0, 0, -1, 1 };
	private static final int SEA = 2;
	private static final int WATER = 0;
	private static final int LAND = 1;

	private static int[][] map;
	private static int n, m, N, M;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		n = io.getInt();
		m = io.getInt();
		N = n + 2;
		M = m + 2;
		map = new int[N][M];
		for (int i = 1; i <= n; i++) {
			String line = io.getWord();
			for (int j = 1; j <= m; j++) {
				map[i][j] = Character.getNumericValue(line.charAt(j - 1));
			}
		}
		io.println(getCoastLength());
		io.flush();
		io.close();

	}

	private static int getCoastLength() {
		Queue<Coordinate> queue = new LinkedList<Coordinate>();
		Coordinate sea = new Coordinate(0, 0);
		queue.add(sea);
		map[sea.y][sea.x] = SEA;
		while (!queue.isEmpty()) {
			Coordinate current = queue.poll();
			for (int i = 0; i < dx.length; i++) {
				int x = current.x + dx[i];
				int y = current.y + dy[i];
				if (inBounds(y, x) && map[y][x] == WATER) {
					if (map[y][x] == WATER) {
						map[y][x] = SEA;
						queue.add(new Coordinate(y, x));
					}
				}
			}
		}

		int coastLength = 0;

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (map[i][j] == LAND) {
					for (int k = 0; k < dx.length; k++) {
						int y = i + dy[k];
						int x = j + dx[k];
						if (!inBounds(y, x) || map[y][x] == SEA) {
							coastLength++;
						}
					}
				}
			}
		}

		return coastLength;
	}

	private static boolean inBounds(int y, int x) {
		return y >= 0 && x >= 0 && y < N && x < M;
	}

	private static class Coordinate {
		public int y, x;

		public Coordinate(int y, int x) {
			this.y = y;
			this.x = x;
		}

		public boolean equals(Object o) {
			return o instanceof Coordinate && ((Coordinate) o).y == y && ((Coordinate) o).x == x;
		}
	}
}