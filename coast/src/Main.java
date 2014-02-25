import java.util.LinkedList;
import java.util.Queue;

public class Main {

	private static final int[] dx = { 1, -1, 0, 0 };
	private static final int[] dy = { 0, 0, -1, 1 };
	private static final int SEA = 2;
	private static final int LAKE = 3;
	private static final int UNKNOWN_WATER = 0;
	private static final int LAND = 1;

	private static int[][] map;
	private static boolean[][] visited;
	private static int n;
	private static int m;

	public static void main(String[] args) {
		Kattio io = new Kattio(System.in);
		n = io.getInt();
		m = io.getInt();
		map = new int[n][m];
		visited = new boolean[n][m];
		for (int i = 0; i < n; i++) {
			String line = io.getWord();
			for (int j = 0; j < m; j++) {
				map[i][j] = Character.getNumericValue(line.charAt(j));
			}
		}
		io.println(getCoastLength());
		io.flush();
		io.close();

	}

	private static int getCoastLength() {
		Coordinate sea = getUnmarkedSea();
		while (!sea.equals(Coordinate.FAIL)) {
			Queue<Coordinate> queue = new LinkedList<Coordinate>();
			queue.add(sea);
			map[sea.y][sea.x] = SEA;
			while (!queue.isEmpty()) {
				Coordinate current = queue.poll();
				for (int i = 0; i < dx.length; i++) {
					int x = current.x + dx[i];
					int y = current.y + dy[i];
					if (inBounds(y, x) && !visited[y][x]) {
						visited[y][x] = true;
						if (map[y][x] == UNKNOWN_WATER) {
							map[y][x] = SEA;
							queue.add(new Coordinate(y, x));
						}
					}
				}
			}
			sea = getUnmarkedSea();
		}

		int coastLength = 0;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
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
		return y >= 0 && x >= 0 && y < n && x < m;
	}

	private static int searched = 0;

	private static Coordinate getUnmarkedSea() {
		for (int i = searched; i < n; i++, searched++)
			for (int j = 0; j < m; j++)
				if (map[i][j] == UNKNOWN_WATER && definietlySea(i, j))
					return new Coordinate(i, j);
		return Coordinate.FAIL;
	}

	private static boolean definietlySea(int y, int x) {
		for (int i = 0; i < dx.length; i++) {
			int yDir = dy[i];
			int xDir = dx[i];
			int curY = y;
			int curX = x;
			boolean land = false;
			while (inBounds(curY, curX)) {
				if (map[curY][curX] == LAND) {
					land = true;
					break;
				}

				curY += yDir;
				curX += xDir;
			}
			if (!land)
				return true;
		}
		return false;
	}

	private static class Coordinate {
		public static final Coordinate FAIL = new Coordinate(-1, -1);
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